package com.testathon.automation.keywords;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.testathon.automation.core.Config;
import com.testathon.automation.core.DriverFactory;
import com.testathon.automation.logging.LoggingManager;
import com.testathon.automation.utils.ReadFileUtil;

/**
 * This class contains the methods that read the element locators and their values
 * from ObjectRepository.xml file.
 */
public class MKeywords {
	private static DriverFactory instance = DriverFactory.getInstance();	
	private static int invalidLinksCount;
	private static int invalidImageCount;
	// driver instance
	/**
	 * Get page the element.
	 *
	 * @param locatorValue	value of locator.
	 * @param locatorType	type of locator.
	 * @return By			returns By data type.
	 * @throws Exception	on error.
	 * @see	   Exception
	 */
	public static By GetElement(String locatorValue, String locatorType) throws Exception {
		switch (locatorType.toLowerCase()) {
		case "id":
			return By.id(locatorValue);
		case "name":
			return By.name(locatorValue);
		case "classname":
		case "class":
			return By.className(locatorValue);
		case "tagname":
			return By.tagName(locatorValue);
		case "linktext":
			return By.linkText(locatorValue);
		case "partiallinktext":
			return By.partialLinkText(locatorValue);
		case "cssselector":
			return By.cssSelector(locatorValue);
		case "xpath":
			return By.xpath(locatorValue);
		default:
			throw new Exception("DOM FINDER : did not find the correct dom finder type in the file for locator value: "
					+ locatorValue);
		}
	}
	
	/**
	 * Finds the page element.
	 *
	 * @param PageName		page name tag in ObjectRepository.xml file.
	 * @param objectName	name of element.
	 * @return By			returns By data type.
	 */
	public static By findElement(String PageName, String objectName) {
		By locator=null;
		try {
			LoggingManager.getInstance().setCurrentElementPath(objectName);
			String xmlPath = Config.locatorsFile;
			List<String> listLocator = ReadFileUtil.getXmlValue(objectName, xmlPath, PageName);
			if(!listLocator.isEmpty())
				locator = GetElement(listLocator.get(0), listLocator.get(1));
			else {
				LoggingManager.getReportLogger().error("NameOfElement or SectionName is given wrong in ObjectRepository.xml file OR PageName in Page Object Class is wrong");
			}
		} catch (Exception ex) {
			LoggingManager.getReportLogger().log(Status.INFO, "Failed to read data from XML file" + ex);
			LoggingManager.getConsoleLogger().info("Failed to read data from XML file" + ex);
		}
		return locator;
	}

	/**
	 * Pause the execution of test for defined time.
	 *
	 * @param milliseconds	time in milliseconds.
	 */
	public static void Sleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (Exception ex) {
			LoggingManager.getReportLogger().log(Status.FAIL, "exception Occured while waiting " + ex);
			LoggingManager.getConsoleLogger().error("exception Occured while waiting" + ex);
		}
	}

	/**
	 * Runs auto IT script.
	 *
	 * @param fileName		name of the file.
	 * @throws IOException	on input error.
	 * @see	   IOException
	 */
	public static void runAutoITScript(String fileName) {
		try {
			Runtime.getRuntime().exec(Config.autoITPath + fileName);
		}catch (IOException ex) {
			LoggingManager.getReportLogger().log(Status.FAIL, "Exception Occured on running autoIT script: " + ex);
			LoggingManager.getConsoleLogger().error("Exception Occured on running autoIT script: " + ex);
		}catch (Exception ex) {
			LoggingManager.getReportLogger().log(Status.FAIL, "Exception Occured on running autoIT script: " + ex);
			LoggingManager.getConsoleLogger().error("Exception Occured on running autoIT script: " + ex);
		}
	}

	
	/**
	 * Get Absolute path to file
	 * 
	 * @param log4jPath		log4jPath
	 * @return String		path.
	 */
	public static String getAbsolutePath(String log4jPath) {
		File file = new File(log4jPath);
		String path = file.getAbsolutePath();
		return path;
	}
	
	/**
	 * Validate invalid links 1.
	 */
	public static void validateInvalidLinks1() {

		try {
			invalidLinksCount = 0;
			List<WebElement> anchorTagsList = instance.getDriver().findElements(By.tagName("a"));
			System.out.println("Total no. of links are " + anchorTagsList.size());
			for (WebElement anchorTagElement : anchorTagsList) {
				if (anchorTagElement != null) {
					String url = anchorTagElement.getAttribute("href");
					if (url != null && !url.contains("javascript")) {
						verifyURLStatus(url);
					} else {
						invalidLinksCount++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Verify URL status.
	 *
	 * @param url the url
	 */
	public static void verifyURLStatus(String url) {

		HttpClient client = HttpClientBuilder.create().build();
		System.out.println("URL is " + url);
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = client.execute(request);
			// verifying response code and The HttpStatus should be 200 if not,
			// increment invalid link count
			//// We can also check for 404 status code like
			// response.getStatusLine().getStatusCode() == 404
			if (response.getStatusLine().getStatusCode() != 200)
			{
				invalidLinksCount++;
			}
			else
			{
				LoggingManager.getReportLogger().log(Status.PASS, "\"" + "URL acessed " + "\"" + url + "\"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Validate invalid images.
	 */
	public static void validateInvalidImages() {
		try {
			invalidImageCount = 0;
			List<WebElement> imagesList = instance.getDriver().findElements(By.tagName("img"));
			System.out.println("Total no. of images are " + imagesList.size());
			for (WebElement imgElement : imagesList) {
				if (imgElement != null) {
					verifyImageActive(imgElement);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Verify image active.
	 *
	 * @param imgElement the img element
	 */
	public static void verifyImageActive(WebElement imgElement) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(imgElement.getAttribute("src"));
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() != 200)
			{     
				invalidImageCount++;
				LoggingManager.getReportLogger().log(Status.FAIL, "\"" + " Image Accessed --->" + "\"" + request.getURI() + "\"");
			}
			else
				LoggingManager.getReportLogger().log(Status.PASS, "\"" + " Image Accessed --->" + "\"" + request.getURI() + "\"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This will read the PDF and parse it.
	 * @param reqTextInPDF String to be matched.
	 * @return whether the required text is matched with the PDF content
	 */
	public static boolean verifyPDFContent(String reqTextInPDF, int startPage, int endPage) {

		boolean flag = false;

		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		String parsedText = null;

		try {
			URL url = new URL(instance.getDriver().getCurrentUrl());
			BufferedInputStream file = new BufferedInputStream(url.openStream());
			PDFParser parser = new PDFParser(file);

			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdfStripper.setStartPage(startPage);
			pdfStripper.setEndPage(endPage);

			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
		} catch (MalformedURLException e2) {
			LoggingManager.getReportLogger().error("URL string could not be parsed "+e2.getMessage());
		} catch (IOException e) {
			LoggingManager.getReportLogger().error("Unable to open PDF Parser. " + e.getMessage());
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}

		LoggingManager.getReportLogger().info(" ++++++++++++++PDF Content starts+++++++++++++++++");
		LoggingManager.getReportLogger().info(parsedText);
		LoggingManager.getReportLogger().info("+++++++++++++++++ PDF content ends++++++++++++++++");

		if(parsedText.contains(reqTextInPDF)) {
			flag=true;
		}

		return flag;
	}


}
