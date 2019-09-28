package com.testathon.automation.keywords;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.testathon.automation.core.DriverFactory;
import com.testathon.automation.core.TestBase;
import com.testathon.automation.logging.LoggingManager;
import com.testathon.automation.utils.CommonUtil;

/**
 * This class contains all the keywords created using methods provided by Selenium Webdriver API.
 */
public class SeKeywords {

	private static DriverFactory instance = DriverFactory.getInstance();
	public static boolean isIeBrowser = TestBase.BROWSER_VALUE.equalsIgnoreCase(DriverFactory.INTERNET_EXPLORER);
	public static boolean isSafariBrowser = TestBase.BROWSER_VALUE.equalsIgnoreCase(DriverFactory.SAFARI);
	public static boolean isFirefoxBrowser = TestBase.BROWSER_VALUE.equalsIgnoreCase(DriverFactory.FIREFOX);

	/**
	 *  This method checks the presence of element on web page.
	 *
	 * @param element	element on web page.
	 * @return true, if web element is present.
	 */
	public static boolean isElementPresent(By element){
		boolean result = false;
		try{
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(element));
//			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Element found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
//			LoggingManager.getConsoleLogger().info("Element found -> " + LoggingManager.getInstance().getCurrentElementPath());
			result = true;
		} catch (NoSuchElementException e) {
//			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
//			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		} catch (TimeoutException e) {
//			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
//			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		} catch (Exception e) {
//			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
//			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		}
		return result;
	}
	
	/**
	 * This method waits for visibility of the element present on web page for specified time.
	 *
	 * @param element	element on web page.
	 * @param secs 		time to wait in seconds.
	 */
	public static boolean isElementPresent(By element , int secs){
		boolean result = false;
		try
		{
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), secs);
			wait.until(ExpectedConditions.presenceOfElementLocated(element));
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Element found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("Element found -> " + LoggingManager.getInstance().getCurrentElementPath());
			result = true;
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().info("Timeout. Element not found." + e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		}
		return result;
	}
	
	/**
	 * checks whether an element has the expected class
	 * @param element
	 * @param expectedClass
	 * @return
	 */
	public static boolean hasClass(By element, String expectedClass) {
		boolean result = false;
		try {
			SeKeywords.moveToElement(element);
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(element));
			result = hasClass(instance.getDriver().findElement(element), expectedClass);
			
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Element found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("Element found -> " + LoggingManager.getInstance().getCurrentElementPath());
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().info("Timeout. Element not found." + e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		}
		return result;
	}
	
	/**
	 * Checks whether the specific WebElement has the expectedClass
	 * @param webElement
	 * @param expectedClass
	 * @return
	 */
	public static boolean hasClass(WebElement webElement, String expectedClass) {
		String[] classes = webElement.getAttribute("class").split(" ");
		return ArrayUtils.contains(classes, expectedClass);
	}

	/**
	 * Checks if a given image source pattern is present within the src attribute of ANY image element underneath a given container element.
	 * 
	 * @param element 		the element whose sub-elements to check for image elements, whose src attribute will be searched
	 * @param imgPattern	the pattern to search within the src attributes
	 */
	public static boolean isImagePatternPresent(By element, String imgPattern) {
		boolean result = false;
		try {
			WebElement parentElement = instance.getDriver().findElement(element);
			List<WebElement> images = parentElement.findElements(By.tagName("img"));
			for (WebElement img : images) {
				// Retry in case the DOM changes during test execution and we get stale elements
				int attempts = 0;
				while (attempts < 5) {
					try {
						String src = img.getAttribute("src");
						if (src != null) {
							if (src.contains(imgPattern)) {
								result = true;
								break;
							}
						}
					} catch (StaleElementReferenceException e) {
						LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Stale Exception retrying for : " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
						LoggingManager.getConsoleLogger().info("Stale Exception retrying..." + e);
					}
					attempts++;
				}
			}
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Image pattern found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("Image pattern found -> " + LoggingManager.getInstance().getCurrentElementPath());
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.INFO,"\"" + "No element found to determine presence of image for " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No element found to determine presence of image." + e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().info("TimeOut. Element not found" + e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		}
		
		return result;
	}
	
	/**
	 * Checks if a given string pattern is present within the src attribute of ALL images found in the given HTML element.
	 * 
	 * @param element 		the element whose sub-elements to check for image elements, whose src attributes will be searched
	 * @param imgPattern	the pattern to search within the src attributes
	 * 
	 */
	public static boolean allImagesContainPattern(By element, String imgPattern, String... exceptions) {
		boolean foundException;
		boolean result = false;
		try {
			WebElement parentElement = instance.getDriver().findElement(element);
			List<WebElement> images = parentElement.findElements(By.tagName("img"));
			for (WebElement img : images) {
				// Retry in case the DOM changes during test execution and we get stale elements
				int attempts = 0;
				while (attempts < 5) {
					try {
						String src = img.getAttribute("src");
						if (src != null) {
							foundException = false;
							if (!src.contains(imgPattern)) {
								for (String exception : exceptions) {
									if (src.contains(exception)) {
										foundException = true;
										break;
									}
								}
								if (!foundException) {
									result = false;
									break;
								}
								break;
							}
						}
					} catch (StaleElementReferenceException e) {
						LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Stale Exception retrying for : " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
						LoggingManager.getConsoleLogger().info("Stale Exception retrying..." + e);
					}
					attempts++;
				}
			}
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "All Images Contain Pattern found for: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("Image pattern found -> " + LoggingManager.getInstance().getCurrentElementPath());
			
		}
		catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "No element found to determine presence of image. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\""+ " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("No element found to determine presence of image." + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("TimeOut. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
		result = true;
		return result;
	}
	
	/**
	 * Returns the number of IMG elements under the given DOM element.
	 * 
	 * @param element	element.
	 * @return int	 image count.
	 */
	public static int countImagesPresent(By element) {
		int result = 0;

		try {
			WebElement parentElement = instance.getDriver().findElement(element);
			List<WebElement> images = parentElement.findElements(By.tagName("img"));
			result = images.size();
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Image count verified for " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\""+ "found to be " + "\""+ result + "\"");
			LoggingManager.getConsoleLogger().info("Image count verified for " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\""+ "found to be " + "\""+ result + "\"");
		}
		catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "No element found to determine presence of image. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\""+ " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("No element found to determine presence of image." + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("TimeOut. Element not found" + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}	
		return result;
	}
	
	/**
	 * Expands all fabric families within a given fabric section.
	 * 
	 * @param fabricSection		Fabric Section.
	 */
	public static void expandAllFabricFamilies(By fabricSection) {
		try {
			Actions act = new Actions(instance.getDriver());

			WebElement parentElement = instance.getDriver().findElement(fabricSection);
			List<WebElement> subDivs = parentElement.findElements(By.tagName("div"));
			for (WebElement subDiv : subDivs) {
				// Retry in case the DOM changes during test execution and we get stale elements
				int attempts = 0;
				while (attempts < 5) {
					try {
						String familyName = subDiv.getAttribute("data-fabricfamilywithopenness");
						if (familyName != null) {
							List<WebElement> swatchDivs = subDiv.findElements(By.tagName("div"));
							if (swatchDivs.isEmpty()) {
								act.moveToElement(subDiv).click().build().perform();
								break;
							}
						}
					}
					catch (StaleElementReferenceException e) {
						LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Stale Exception retrying for : " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
						LoggingManager.getConsoleLogger().info("Stale Exception retrying..." + e);
					}
					catch(TimeoutException e) {
						LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\""+ " not found. " + "\"");
						LoggingManager.getConsoleLogger().error("TimeOut. Element not found" + e);
						throw(e);

					}
					catch(Exception e) {
						LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
						LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
						throw(e);

					}
					attempts++;
				}
			}
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Fabric family expanded for: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		}catch(NoSuchElementException e){
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "No element found during expansion/collapse of fabric families: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No element found during expansion/collapse of fabric families:" + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);

		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);

		}
	}

	
	/**
	 * Expands one fabric families within a given fabric section.
	 * 
	 * @param fabricSection 	fabricSection.
	 */
	public static void expandOneFabricFamily(By fabricSection) {
		try {
			Actions act = new Actions(instance.getDriver());

			WebElement parentElement = instance.getDriver().findElement(fabricSection);
			List<WebElement> subDivs = parentElement.findElements(By.tagName("div"));
			for (WebElement subDiv : subDivs) {
				// Retry in case the DOM changes during test execution and we get stale elements
				int attempts = 0;
				boolean clicked = false;
				while (attempts < 5) {
					try {
						String familyName = subDiv.getAttribute("data-fabricfamilywithopenness");
						if (familyName != null) {
							List<WebElement> swatchDivs = subDiv.findElements(By.tagName("div"));
							if (swatchDivs.isEmpty()) {
								act.moveToElement(subDiv).click().build().perform();
								clicked = true;
								break;
							}
						}
					} catch (StaleElementReferenceException e) {
						LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Stale Exception retrying for : " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
						LoggingManager.getConsoleLogger().info("Stale Exception retrying..." + e);
					}
					attempts++;
				}
				if (clicked) {
					break;
				}
			}
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Fabric family expanded for: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		}

		catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL,
					"\"" + "No element found during expansion/collapse of fabric family: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No element found during expansion/collapse of fabric family:" + e);
			throw(e);

		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);

		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);

		}
	}
	
	/**
	 * Selects any selectable fabric
	 * 
	 * @param fabricSection		fabricSection.
	 */
	public static void selectAnyFabric(By fabricSection) {
		try {
			Actions act = new Actions(instance.getDriver());

			WebElement parentElement = instance.getDriver().findElement(fabricSection);
			List<WebElement> subDivs = parentElement.findElements(By.tagName("div"));
			for (WebElement subDiv : subDivs) {
				String familyName = subDiv.getAttribute("data-fabricfamilywithopenness");
				if (familyName != null) {
					List<WebElement> swatchDivs = subDiv.findElements(By.tagName("div"));
					if (!swatchDivs.isEmpty()) {
						for (WebElement swatchDiv : swatchDivs) {
							// Retry in case the DOM changes during test execution and we get stale elements
							int attempts = 0;
							while (attempts < 5) {
								try {
									if (!swatchDiv.getAttribute("innerText").toLowerCase().contains("tbd")) {
										act.moveToElement(swatchDiv).click().build().perform();
										break;
									}
								} catch (StaleElementReferenceException e) {
									LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Stale Exception retrying for : " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
									LoggingManager.getConsoleLogger().info("Stale Exception retrying..." + e);
								}
								attempts++;
							}
						}
						break;
					}
				}
			}
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Fabric selected: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		}

		catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "No element found during selection of fabric: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No element found during selection of fabric:" + e);
			throw(e);

		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);

		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);

		}
	}
	
	/**
	 *  This method checks the enable state of element on web page.
	 *
	 * @param element	element on page.
	 * @return true, 	if element is enabled.
	 */
	public static boolean isElementEnabled(By element){
		boolean result = false;
		try{
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(element));
			if (instance.getDriver().findElement(element).isEnabled()) {
				LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Element enabled: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
				LoggingManager.getConsoleLogger().info("Element enabled -> " + LoggingManager.getInstance().getCurrentElementPath());
				result = true;
			} else {
				LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not enabled: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
				LoggingManager.getConsoleLogger().info("Element not enabled -> " + LoggingManager.getInstance().getCurrentElementPath());
				result = false;
			}
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found " + e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().info("Timeout. Element not found." + e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found " + e);
		}
		return result;
	}
	
	/**
	 * Returns a value indicating whether a given attribute is present for a given element
	 * 
	 * @param element	WebElement to search for a given attribute
	 * @param attribute	Attribute name to test for existence on element
	 */
	public static boolean isAttributePresent(WebElement element, String attribute) {
		boolean result = false;
		try {
			String attrValue = element.getAttribute(attribute);
			if (attrValue != null) {
				result = true;
			}
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Attribute found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("Attribute found -> " + LoggingManager.getInstance().getCurrentElementPath());
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Attribute element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().info("Timeout. Element not found." + e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		}
		return result;
	}
	
	/**
	 *  Moves the mouse to the specified element.
	 *
	 * @param element	element on page.
	 */
	public static void moveToElement(By element){
		try{
			Actions a=new Actions(instance.getDriver());
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(element));
			if(SeKeywords.isFirefoxBrowser||SeKeywords.isIeBrowser) {
				JavaScriptKeywords.scrollElementIntoView(element, true);
			}else {
				a.moveToElement(instance.getDriver().findElement(element)).perform();
			}
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Scrolled to " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("Scrolled to -> " + LoggingManager.getInstance().getCurrentElementPath());
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);

		}
	}

	/**
	 * This method get the access on window opened.
	 *
	 * @param element	element on web page.
	 */
	public static void switchTowindow(By element){
		try{
			Set<String> windows = instance.getDriver().getWindowHandles();
			for (String currentWindowHandle : windows) {
				instance.getDriver().switchTo().window(currentWindowHandle);
			}
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Switched to window: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("Switched to window -> "+ LoggingManager.getInstance().getCurrentElementPath());
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);

		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);

		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);

		}
	}


	/**
	 *  This method get the text of web element.
	 *
	 * @param element	element on web page.
	 * @return string,	web text.
	 */
	public static String getText(By element) {
		String text = null;
		try {
			SeKeywords.moveToElement(element);
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(element));
			text = instance.getDriver().findElement(element).getText().trim();
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + LoggingManager.getInstance().getCurrentElementPath() + " is: " + text + "\"");
			LoggingManager.getConsoleLogger().info(LoggingManager.getInstance().getCurrentElementPath() + " text is -> " + text );
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "getTextElement not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("getTextElement not found" + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
		return text;
	}
	
	/**
	 * Gets an attribute without verifying that it's present.
	 * 
	 * NOTE: ExpectedConditions.presenceOfElementLocated does not always work for children of DIVs with class="hide"
	 * @param element element on the DOM
	 * @param attribute name of an attribute of the element
	 * @return The value of the attribute for the given element
	 */
	public static String getAttributeUnverified(By element, String attribute) {
		String attrValue = null;
		try {
			attrValue = instance.getDriver().findElement(element).getAttribute(attribute);
			LoggingManager.getReportLogger().log(Status.PASS,
					"\"" + "Attribute " + "\"" + attribute + "\"" + " value is: " + "\"" + attrValue + "\"");
			LoggingManager.getConsoleLogger().info("Attribute " + "\"" + attribute + "\"" + " value is: " + "\"" + attrValue + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
		return attrValue;
	}

	/**
	 *  This method get the specified attribute of web element.
	 *
	 * @param element			element on page.
	 * @param attribute			name of the attribute whose value user wants to get.
	 * @return String 			the attribute value.
	 */
	public static String getAttribute(By element, String attribute) {
		String attrValue = null;
		try {
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(element));
			attrValue = instance.getDriver().findElement(element).getAttribute(attribute);
//			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Attribute " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " value is: " + "\"" + attrValue + "\"");
//			LoggingManager.getConsoleLogger().info("Attribute " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " value is: " + "\"" + attrValue + "\"");
		} catch (NoSuchElementException e) {
//			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element with " + "\"" + attribute + "\"" + " attribute not found for " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
//			LoggingManager.getConsoleLogger().error("Element with " + attribute + " attribute not found" + e);
			throw(e);
		} catch (TimeoutException e) {
//			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
//			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
//			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
//			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
		return attrValue;
	}

	/**
	 * This method checks whether the string is empty or not.
	 *
	 * @param text		text to verify.
	 * @return true, 	if not empty.
	 */
	public static boolean notEmpty(String text) {
		boolean result = false;
		try {
			if (!text.equals("")) {
				LoggingManager.getReportLogger().log(Status.PASS, "\"" + text + " is present" + "\"" + " for " + "\"" + LoggingManager.getInstance().getCurrentElementPath());
				LoggingManager.getConsoleLogger().info(text + " is present" + "\"" + " for " + "\"" + LoggingManager.getInstance().getCurrentElementPath());
				result = true;
			}
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().info("Timeout. Element not found." + e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		}
		return result;
	}

	/**
	 * This method clear the text entered in the text box.
	 *
	 * @param textBox 	the text box element.
	 */
	public static void clear(By textBox) {
		try {
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 10);
			MKeywords.Sleep(1000);
			wait.until(ExpectedConditions.elementToBeClickable(textBox));
			instance.getDriver().findElement(textBox).clear();
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + " Text Box Cleared ---> " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info(" Text Box Cleared ---> " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Text Box to be Cleared not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Text Box to be Cleared not found" + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
	}

	/**
	 *  This method tick the check box.
	 *
	 * @param checkbox	check box element.
	 */
	public static void selectCheckbox(By checkbox) {
		try{
			if (!instance.getDriver().findElement(checkbox).isSelected()) {
				if (isIeBrowser) {
					JavaScriptKeywords.clickUsingJs(instance.getDriver().findElement(checkbox));
				}else {
					instance.getDriver().findElement(checkbox).click();
				}
			}
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Ticked CheckBox " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("Ticked CheckBox " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Ticked CheckBox not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Ticked CheckBox not found" + e);
			throw(e);

		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);

		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
	}

	/**
	 *  This method get the text written on the pop-up opened.
	 *
	 * @param modal		pop-up box element.
	 * @param message 	message element.
	 * @return string,	text on the pop up.
	 */
	public static String getModalPopMessage(By modal, By message) {
		String text = null;
		try {
			instance.getDriver().switchTo().activeElement();
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(modal));
			wait.until(ExpectedConditions.visibilityOfElementLocated(modal));
			text = instance.getDriver().findElement(message).getText().trim();
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + " Pop up message is: " + "\"" + text + "\"");
			LoggingManager.getConsoleLogger().info(" Pop up message is: " + "\"" + text + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Modal Pop Message not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Modal Pop Message not found" + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
		return text;
	}

	/**
	 *  This method click on the button present on pop up.
	 *
	 * @param modal 	 pop-up box element.
	 * @param buttonName button name on pop-up.
	 */
	public static void clickModalPopButton(By modal, String buttonName){
		try{
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 10);
			instance.getDriver().switchTo().activeElement();
			WebElement modalbuttons = instance.getDriver().findElement(modal);
			List<WebElement> options = modalbuttons.findElements(By.className("btn"));
			for (WebElement option : options) {
				if (buttonName.equalsIgnoreCase(option.getText())){
					MKeywords.Sleep(2000);
					wait.until(ExpectedConditions.elementToBeClickable(option));
					Actions action_obj=new Actions(instance.getDriver());
					action_obj.moveToElement(option).click().build().perform();
					LoggingManager.getReportLogger().log(Status.PASS, "Clicked on the modal pop up button"  + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
					LoggingManager.getConsoleLogger().info("Clicked on the modal pop up button"  + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
					break;
				}
			}
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Modal Pop Button not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Modal Pop Button not found" + e);
			throw(e);

		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);

		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
	}
	
	/**
	 *  This method click on the 'Close' present on the pop up.
	 *
	 * @param closeButton 	 close button element on pop-up.
	 */
	public static void clickModalCloseButton(By closeButton){
		try{
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 10);
			instance.getDriver().switchTo().activeElement();
			instance.getDriver().findElement(closeButton).click();
			LoggingManager.getReportLogger().log(Status.PASS, "Clicked on the modal pop up close button" + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("Clicked on the modal pop up close button" + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Close Button not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Close Button not found" + e);
			throw(e);

		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);

		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);

		}
	}

	/**
	 * This method waits for visibility of the element present on web page.
	 *
	 * @param element	element on web page.
	 * @param secs 		time to wait in seconds.
	 */
	public static boolean waitForElementVisibility(By element , int secs){
		boolean result = false;
		try
		{
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), secs);
			wait.until(ExpectedConditions.presenceOfElementLocated(element));
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
//			LoggingManager.getReportLogger().log(Status.PASS, "Presence of element visibilty verified for" + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
//			LoggingManager.getConsoleLogger().info("Presence of element visibilty verified for" + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			result = true;
		} catch (NoSuchElementException e) {
//			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
//			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		} catch (TimeoutException e) {
//			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
//			LoggingManager.getConsoleLogger().info("Timeout. Element not found." + e);
		} catch (Exception e) {
//			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
//			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		}
		return result;
	}
	
	/**
	 * This method wait for element to be clickable on web page.
	 *
	 * @param element 	web element to wait for.
	 * @param secs 		number of seconds to wait.
	 * 
	 */
	public static void waitForElementToBeClickable(By element , int secs){
		try
		{
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), secs);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			LoggingManager.getReportLogger().log(Status.PASS, "Element to be clicked verified: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("Element to be clicked verified: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Clickable Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("Clickable Element not found" + e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().info("Timeout. Element not found." + e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		}
	}

	/**
	 * This method waits for the invisibility of the element present on web page. 
	 *
	 * @param element	element on web page.
	 * @param secs 		number of second to wait.
	 */
	public static boolean waitForElementInVisibility(By element , int secs){
		boolean result = false;
		try
		{
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), secs);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
			LoggingManager.getReportLogger().log(Status.PASS, "Element invisibility verified for " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("Element invisibility verified for " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			result = true;
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().info("Timeout. Element not found." + e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		}
		return result;
	}
	
	/**
	 * This method wait for staleness of element.
	 *
	 * @param element 	web element to wait for.
	 * @param secs 		number of seconds to wait.
	 * 
	 */
	public static void waitForStalenessOfElement(By element , int secs){
		try
		{
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), secs);
			wait.until(ExpectedConditions.stalenessOf(instance.getDriver().findElement(element)));
			LoggingManager.getReportLogger().log(Status.PASS, "Staleness of element is verified: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("Staleness of element is verified: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("Element not found" + e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().info("Timeout. Element not found." + e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		}
	}

	/**
	 * This method enter the value in text box.
	 *
	 * @param textBox	text box element.
	 * @param value 	value user wants to enter in the text box.
	 */
	public static void setText(By textBox, String value) {
		try{
			
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(textBox));
			instance.getDriver().findElement(textBox).clear();
			SeKeywords.scrollAndClick(textBox);
			sendKeys(textBox, value);
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + value + "\"" + " is entered in text box " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("\"" + value + "\"" + " is entered in text box " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Textbox to enter text not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Textbox to enter text not found" + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);

		}
	}

	/**
	 * This method set the focus on text box and enter value in it.
	 *
	 * @param textBox	text box element.
	 * @param value 	value user wants to enter in the text box.
	 */
	public static void focusSetText(By textBox, String value) {
		try{
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(textBox));
			Actions action = new Actions(instance.getDriver()); 
			action.sendKeys(Keys.TAB).build().perform();
			action.sendKeys(value).build().perform(); 
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + value + "\"" + " is entered in text box " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info(value + "\"" + " is entered in text box " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element to be focused not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Element to be focused not found" + e);
			throw(e);

		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);

		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);

		}
	}
	
	/**
	 * This method enter value in text box and select the element appears from the drop-down list.
	 *
	 * @param textBox 	text box element.
	 * @param list 		list element.
	 * @param value 	value user wants to select.
	 */
	public static void searchAndSelect(By textBox , By list , String value){
		try {
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(textBox));
			SeKeywords.setText(textBox, value);
			MKeywords.Sleep(3000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(list));
			instance.getDriver().findElement(textBox).sendKeys(Keys.TAB);
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + value + "\"" + " searched & selected from " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info(value + "\"" + " searched & selected from " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Search Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Search Element not found" + e);
			throw(e);

		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);

		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
	}

	/**
	 *  This method enter the random generated string in the text box.
	 *
	 * @param textBox 	text box to enter generated string.
	 * @return String	unique text.
	 */
	public static String setUniqueText(By textBox) {
		String uniqueText = null;
		try{
			uniqueText = CommonUtil.generateUniqueName();
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(textBox));
			instance.getDriver().findElement(textBox).clear();
			sendKeys(textBox, uniqueText);
			LoggingManager.getReportLogger().log(Status.PASS, "Unique text is entered in the text box " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("Unique text is entered in the text box " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Textbox to enter text not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Textbox to enter text not found" + e);
			throw(e);

		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);

		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);

		}
		return uniqueText;
	}

	/**
	 *  This method click on specified element on web page.
	 *
	 * @param element	element to click on web page.
	 */
	public static void click(By element) {

		try {
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 20);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			if(isIeBrowser || isFirefoxBrowser) {
				JavaScriptKeywords.clickUsingJs(instance.getDriver().findElement(element));
			}else {
				instance.getDriver().findElement(element).click();
			}
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + " Clicked " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("\"" + " Clicked " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Clickable Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Clickable Element not found" + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
	}
	
	/**
	 *  This method click on specified element on web page.
	 *
	 * @param element	element to click on web page.
	 */
	public static void seleniumClick(By element) {

		try {
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 30);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			if(isIeBrowser) {
				JavaScriptKeywords.clickUsingJs(instance.getDriver().findElement(element));
			}else {
				instance.getDriver().findElement(element).click();
			} 
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + " Clicked " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("\"" + " Clicked " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Clickable Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Clickable Element not found" + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
	}

	/**
	 * This method click on the link present on web page.
	 *
	 * @param link 		link element on web page.
	 * @param linkText 	text of the link.
	 */
	public static void clickLinkText(By link, String linkText) {
		try{
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 20);
			wait.until(ExpectedConditions.elementToBeClickable(link));
			if(isIeBrowser) {
				JavaScriptKeywords.clickUsingJs(instance.getDriver().findElement(link));
			}else {
				Actions a=new Actions(instance.getDriver());
				a.moveToElement(instance.getDriver().findElement(link)).build().perform();
				instance.getDriver().findElement(By.linkText(linkText)).click();
			}
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + " Clicked " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("\"" + " Clicked " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Linktext not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No LinkText Found" + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
	}

	/**
	 * This method scroll the web page and click on web element.
	 *
	 * @param element	element on user wants to click.
	 */
	public static void scrollAndClick(By element) {
		try{
			if(SeKeywords.isFirefoxBrowser||SeKeywords.isIeBrowser) {
				JavaScriptKeywords.scrollElementIntoView(element, true);
				JavaScriptKeywords.clickUsingJs(element);
			}else {
				Actions actionDriver=new Actions(instance.getDriver());
				waitForElementVisibility(element, 2);
				actionDriver.moveToElement(instance.getDriver().findElement(element)).build().perform();
				actionDriver.click().perform();
			}
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Scrolled & Clicked " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("\"" + "Scrolled & Clicked " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
	}

	/**
	 *  This method press the ENTER key of keyboard.
	 *
	 * @param textBox	text box element.
	 */

	public static void keyPressEnter(By textBox) {
		try {
			instance.getDriver().findElement(textBox).sendKeys(Keys.ENTER);
			LoggingManager.getReportLogger().log(Status.PASS, "Press Enter key action called for " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("Press Enter key action called for " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element for Enter key action not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Element for Enter key action not found" + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
	}

	/**
	 * This method select the value from drop down list.
	 *
	 * @param dropdown 	drop down web list.
	 * @param value 	value user wants to select from the dropdown list.
	 */
	public static void selectDropdownValue(By dropdown, String value) {
		try{
			Select drpdown = new Select(instance.getDriver().findElement(dropdown));
			drpdown.selectByVisibleText(value);
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + " Selected " + "\"" + value + "\"" + "from Dropdown");
			LoggingManager.getConsoleLogger().info("\"" + " Selected " + "\"" + value + "\"" + "from Dropdown");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Dropdown Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Dropdown Element Found" + e);
			throw(e);

		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
	}

	/**
	 *  This method get the title of web page.
	 *
	 * @return string,	title of web page.
	 */
	public static String getPageTitle() {
		String title = null;
		try {
			MKeywords.Sleep(1000);
			 title = instance.getDriver().getTitle();
			LoggingManager.getReportLogger().log(Status.PASS, "Title for " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + "is" + title + "\"");
			LoggingManager.getConsoleLogger().info( "Title for " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + "is" + title + "\"");
			
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Title not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Title not found" + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
		return title;
	}

	/**
	 * This method verify the presence of specified text on the page.
	 *
	 * @param text		text value user want to verify on web page.
	 * @return true,	if text is present on the web page.
	 */
	public static boolean verifyAnyTextOnPage(String text) {
		boolean result = false;
		try {
			if (instance.getDriver().getPageSource().contains(text)) {
				LoggingManager.getReportLogger().log(Status.PASS, "\"" + text + "\"" + " is present. " + "\"");
				LoggingManager.getConsoleLogger().info("\"" + text + "\"" + " is present. " + "\"");
				result = true;
			} else {
				LoggingManager.getReportLogger().log(Status.INFO, "\"" + text + "\"" + " is not found. " + "\"");
				LoggingManager.getConsoleLogger().info("\"" + text + "\"" + " is not found. " + "\"");
				result = false;
			}
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + text + "\"" + " is not found on Page " + "\"");
			LoggingManager.getConsoleLogger().info("Text is not found on Page " + e);
		}
		return result;
	}
	
	/**
	 * This method verify the visibility of element on web page.
	 *
	 * @param element the element
	 * @return true, if is element visible
	 */
	public static boolean isElementVisible(By element) {
		boolean result = false;
		try {
			if (instance.getDriver().findElement(element).isDisplayed()) {
				result = true;
//				LoggingManager.getReportLogger().log(Status.PASS, "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " is visible");
//				LoggingManager.getConsoleLogger().info( "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " is visible");
			}else {
//				LoggingManager.getReportLogger().log(Status.INFO, "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " is not visible");
//				LoggingManager.getConsoleLogger().info( "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " is not visible");
			}
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not visible: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().info("Timeout. Element not found." + e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		}
		return result;	
	}

	/**
	 * This method get the css value of attribute.
	 *
	 * @param element		the web element.
	 * @param attribute		attribute name whose css value user wants to get.
	 * @return String		 css value of attribute.
	 */
	public static String getCssValueOfAttribute(By element, String attribute) {
		String attrValue = null;
		try {
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(element));
			attrValue = instance.getDriver().findElement(element).getCssValue(attribute);
			LoggingManager.getReportLogger().log(Status.PASS,"\"" + "Attribute " + "\"" + attribute + "\"" + " value is: " + "\"" + attrValue + "\"");
		    LoggingManager.getConsoleLogger().info("\"" + "Attribute " + "\"" + attribute + "\"" + " value is: " + "\"" + attrValue + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Attribute not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Attribute Found" + e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().info("Timeout. Attribute not found." + e);

		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Attribute not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Attribute Found" + e);
		}
		return attrValue;
	}

	/**
	 * This method delete all the files present in the folder.
	 *
	 * @param folderPath	path to the folder
	 */
	public static void deleteExistingFilesFromFolder(String folderPath) {
		try {
			File folder = new File(folderPath);
			File[] files = folder.listFiles();
			int noOfFiles = files.length;
			if (noOfFiles > 0) {
				for (int i = 0; i < noOfFiles; i++) {
					if (files[i].isFile()) {
						files[i].delete();
						LoggingManager.getConsoleLogger().info("Filed deleted from the folder at path -> " + folderPath);
						LoggingManager.getReportLogger().log(Status.INFO, "Filed deleted from the folder at path -> " + folderPath);
					} else {
						LoggingManager.getReportLogger().log(Status.INFO, "There are no files to delete");
						LoggingManager.getConsoleLogger().info("There are no files to delete");
					}
				}
			} else {
				LoggingManager.getReportLogger().log(Status.INFO, "There are no files to delete");
				LoggingManager.getConsoleLogger().info("There are no files to delete");
			}
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + folderPath + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
	}

	/**
	 * This method scrolls page up using page coordinates.
	 * 
	 * @param scrollBarElement		scroll-bar element.
	 */
	public static void scrollUpUsingCoordinates(By scrollBarElement) {
		boolean result = false;
		try {
			result = SeKeywords.waitForElementVisibility(scrollBarElement, 5);
			if(result) {
			WebElement scrollbar = instance.getDriver().findElement(scrollBarElement);
			Actions dragger = new Actions(instance.getDriver());
			// drag upward
			int pixelsToDrag = -10;
			for (int i = 250; i > 10; i = i + pixelsToDrag) {
				dragger.moveToElement(scrollbar).clickAndHold().moveByOffset(0, pixelsToDrag).release().perform();
			}
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Page scrolled up. " + "\"");
			LoggingManager.getConsoleLogger().info( "\"" + "Page scrolled up. " + "\"");
			}else {
				LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Scrollbar not present. " + "\"");
				LoggingManager.getConsoleLogger().info("\"" + "Scrollbar not present. " + "\"");
				result = false;
			}
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "ScrollBar Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("ScrollBar Element Found" + e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "TimeOut.  " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().info("Timeout. ScrollBar Element not found." + e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		}
	}

	/**
	 * This method scrolls page down using page coordinates.
	 * 
	 * @param scrollBarElement		 scroll-bar element.
	 */
	public static boolean scrollDownUsingCoordinates(By scrollBarElement) {
		boolean result = false;
		try {
			result = SeKeywords.waitForElementVisibility(scrollBarElement, 5);
			if(result) {
				WebElement scrollbar = instance.getDriver().findElement(scrollBarElement);
				Actions dragger = new Actions(instance.getDriver());
				// drag upward
				int pixelsToDrag = 10;
				for (int i = 10; i < 300; i = i + pixelsToDrag) {
					dragger.moveToElement(scrollbar).clickAndHold().moveByOffset(0, pixelsToDrag).release().perform();
				}
				LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Page scrolled down. " + "\"");
				LoggingManager.getConsoleLogger().info("\"" + "Page scrolled down. " + "\"");
			}else {
				LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Scrollbar not present. " + "\"");
				LoggingManager.getConsoleLogger().info("\"" + "Scrollbar not present. " + "\"");
				result = false;
			}
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "ScrollBar Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("ScrollBar Element Found" + e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "TimeOut.  " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().info("Timeout. ScrollBar Element not found." + e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		}
		return result;
	}

	/**
	 * Key press tab.
	 *
	 * @param textBox       the text box
	 */
	public static void keyPressTab(By textBox) {
		try {
			instance.getDriver().findElement(textBox).sendKeys(Keys.TAB);
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Press Tab key action called for " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("\"" + "Press Tab key action called for " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
	}

	
	/**
	 * Verify if file is present or not.
	 * 
	 * @param fileName	filename.
	 * @return true,	if file is present.
	 */
	public static boolean isFilePresent(String fileName) {
		boolean result = false;
		try {
			File file = new File(fileName);
			if (file.exists()) {
				LoggingManager.getReportLogger().log(Status.PASS, "\"" + "File " + "\"" + fileName + "\"" + " found " + "\"");
				LoggingManager.getConsoleLogger().info("\"" + "File " + "\"" + fileName + "\"" + " found " + "\"");
				result = true;
			} else {
				LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "File " + "\"" + fileName + "\"" + " not found " + "\"");
				LoggingManager.getConsoleLogger().error("\"" + "File " + "\"" + fileName + "\"" + " not found " + "\"");
				LoggingManager.getConsoleLogger().error("\"" + "File " + "\"" + fileName + "\"" + " not found " + "\"");
			}
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "File not found: " + "\"" + fileName + "\"");
			LoggingManager.getConsoleLogger().error("File not found:" + e);
			throw(e);
		}
		return result;
	}

	
	/**
	 * Get File name.
	 * 
	 * @param folderPath	path to folder.
	 * @return String, 		path to folder
	 */
	public static String getFileName(String folderPath) {
		String fileName = null;
		try {
			if(isIeBrowser) {
				String home = System.getProperty("user.home");
				Path downloadDir = Paths.get(home+"/Downloads/"); 
				Optional<File> latestFilePath = null;
				latestFilePath =  Arrays.stream(downloadDir.toFile().listFiles()).max((f1, f2) -> Long.compare(f1.lastModified(),f2.lastModified()));
				if (latestFilePath.isPresent()) {
				    File mostRecent = latestFilePath.get();
					fileName = mostRecent.getAbsolutePath();
					LoggingManager.getReportLogger().log(Status.PASS, "\"" + "File " + "\"" + fileName + "\"" + " found " + "\"");
					LoggingManager.getConsoleLogger().info( "\"" + "File " + "\"" + fileName + "\"" + " found " + "\"");
				} else {
					LoggingManager.getReportLogger().log(Status.INFO, "\"" + "File " + "\"" + fileName + "\"" + " not found " + "\"");
					LoggingManager.getConsoleLogger().info("\"" + "File " + "\"" + fileName + "\"" + " not found " + "\"");
				}
			} else {
			File folder = new File(folderPath);
			File[] files = folder.listFiles();
			int noOfFiles = files.length;
			fileName = null;
			if (noOfFiles > 0) {
				for (int i = 0; i < noOfFiles; i++) {
					if (files[i].isFile()) {
						fileName = files[i].getAbsolutePath();
						LoggingManager.getReportLogger().log(Status.PASS, "\"" + "File " + "\"" + fileName + "\"" + " found " + "\"");
						LoggingManager.getConsoleLogger().info( "\"" + "File " + "\"" + fileName + "\"" + " found " + "\"");
					} else {
						LoggingManager.getReportLogger().log(Status.INFO, "\"" + "File " + "\"" + fileName + "\"" + " not found " + "\"");
						LoggingManager.getConsoleLogger().info("\"" + "File " + "\"" + fileName + "\"" + " not found " + "\"");
					}
				}
			}
			}
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "File not found: " + "\"" + fileName + "\"");
			LoggingManager.getConsoleLogger().error("File not found: " + e);
			throw(e);
		}
		return fileName;
	}

	/**
	 * This Method returns the current page URL
	 * 
	 * @return current URL
	 */
	public static String getCurrentURL() {
		String URL = null;
		try {
			URL = instance.getDriver().getCurrentUrl();
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Current URL is:  " + "\"" + URL + "\"");
			LoggingManager.getConsoleLogger().info( "\"" + "Current URL is:  " + "\"" + URL + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "URL not found. " + "\"");
			LoggingManager.getConsoleLogger().error("URL not found." + e);
			throw(e);

		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. URL not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);

		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
		return URL;
	}

	/**
	 * This method checks whether checkbox is selected or not
	 * 
	 * @param checkbox		Check Box
	 * @return true,		If selected
	 */
	public static boolean isCheckBoxSelected(By checkbox) {
		boolean isCheckBoxSelected = false;
		try {
			isCheckBoxSelected = instance.getDriver().findElement(checkbox).isSelected();
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Checkbox found:  " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("\"" + "Checkbox found:  " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Checkbox not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.INFO,
					"\"" + "TimeOut. Checkbox not found " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().info("TimeOut. Checkbox not found." + e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("No Such Element Found" + e);
		}
		return isCheckBoxSelected;
	}

	/**
	 * Gets the URL for PDF window.
	 *
	 * @return String  the URL for PDF window
	 */
	public static String getURLForPDFWindow() {
		String pdfWindowURL = null;
		try {
			ArrayList<String> tabs = new ArrayList<String>(instance.getDriver().getWindowHandles());
			MKeywords.Sleep(2000);
			if(isSafariBrowser) {
				instance.getDriver().switchTo().window(tabs.get(0));
			}		
			instance.getDriver().switchTo().window(tabs.get(1));
			MKeywords.Sleep(3000);
			pdfWindowURL = instance.getDriver().getCurrentUrl();
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Swtiched to child window ");
			LoggingManager.getConsoleLogger().info( "Swtiched to child window ");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Could not find child window. " + "\"");
			LoggingManager.getConsoleLogger().error("Could not find child window." + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("TimeOut. Element not found." + e);
			throw(e);
		} catch (IndexOutOfBoundsException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"" + e);
			throw(e);
		}catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw(e);
		}
		return pdfWindowURL;
	}
	
	/**
	 * This method waits for the visibility of web page Title
	 *
	 * @param pageTitle	pageTitle on web page.
	 * @param secs 		number of second to wait.
	 */
	public static void waitForPageTitle(String pageTitle , int secs){
		try
		{
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), secs);
			wait.until(ExpectedConditions.titleContains(pageTitle));
			LoggingManager.getReportLogger().log(Status.PASS, "page title " +pageTitle + " is verified." + "\"");
			LoggingManager.getConsoleLogger().info("page title " +pageTitle + " is verified." + "\"");
		}catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "page title " +pageTitle + " is not verified." + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. page title " +pageTitle + " is not verified." + e);
			throw(e);
		}catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "page title " +pageTitle + " is not verified." + "\"");
			LoggingManager.getConsoleLogger().error("Page title " +pageTitle + " is not verified." + e);
			throw(e);
		}
	}
	
	/**
     * Fetch any date, past or future from specified date.
     *
     * @param inputDate							date user want to input	  	
     * @param dateFormat    					format of date.
     * @param dateType							type of date : Calendar Day, Month or Year.
     * @param numberOfDateTypeToSubtractAdd		number of Date type (Day, Month or Year) user wants to subtract or add from specified date.
     * @return String							Result date
     * @throws Exception 
     */
     public static String getAnyDateFromSpecifiedDate(String inputDate, String dateFormat, int dateType, int numberOfDateTypeToSubtractAdd) throws Exception {
           String newDate = null;           
           try {
                  SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                  Date myDate = sdf.parse(inputDate);
                  Calendar cal = Calendar.getInstance();
                  cal.setTime(myDate);
                  cal.add(dateType, numberOfDateTypeToSubtractAdd);
                  newDate = sdf.format(cal.getTime());
                  LoggingManager.getReportLogger().log(Status.PASS, "\"" + newDate + "\"" + " One month back date from current date is selected from " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
           }catch (NoSuchElementException e) {
                  LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
                  LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
                  throw(e);
           } catch (TimeoutException e) {
                  LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
                  LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
                  throw(e);
           } catch (Exception e) {
                  LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
                  LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
                  throw(e);
           }
           return newDate;
     }
     
     /**
      * Gets the selected dropdown value.
      *
      * @param dropdown the dropdown selector
      * @return the selected dropdown value
      */
     public static String getSelectedDropdownValue(By dropdown) {
    	 String value = null;
 		try{
 			Select drpdown = new Select(instance.getDriver().findElement(dropdown));
 			value = drpdown.getFirstSelectedOption().getText();
 			LoggingManager.getReportLogger().log(Status.PASS, "\"" +  "DropDown " + "\"" +  " value is: " + "\"" + value + "\"");
 		} catch (NoSuchElementException ex) {
	            LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
	            LoggingManager.getConsoleLogger().error("No Such Element Found" + ex);
	            throw(ex);
	     } catch (TimeoutException ex) {
	            LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
	            LoggingManager.getConsoleLogger().error("Timeout. Element not found." + ex);
	            throw(ex);
	     } catch (Exception ex) {
	            LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
	            LoggingManager.getConsoleLogger().error("No Such Element Found" + ex);
	            throw(ex);
	     }
 		
 		return value;
 	}
     
     /**
      * accept alert box.
      */
     public static void acceptAlert() {
 		int count=0;
 		   while(count++ < 5)
 		   {
 		        try
 		        {
 		        	instance.getDriver().switchTo().alert().accept();
 		            break;
 		        }
 		        catch(Exception ex)
 		        {
 		        	MKeywords.Sleep(10000);
 		           continue;
 		        }
 		   }
 	}
     
     /**
      * Switch to particular frame Id.
      * @param frameID the frame ID to be switched to
      */
     public static void switchToFrame(String frameID) {
 		instance.getDriver().switchTo().frame(frameID);
 	}
     

/**
	 * Method is used to drag and drop element to a location by setting x and y
	 * pixel offset
	 * 
	 * @param element
	 *            Locate By
	 * @param xOffsetPixels
	 *            Offset in x direction
	 * @param yOffsetPixels
	 *            Offset in y direction
	 */
	public static void dragAndDropElementByPixels(By element, int xOffsetPixels, int yOffsetPixels) {
		try {
			Actions action = new Actions(instance.getDriver());
			action.dragAndDropBy(instance.getDriver().findElement(element), xOffsetPixels, yOffsetPixels).build().perform();
			LoggingManager.getReportLogger().log(Status.PASS,"\"" + "Element dragged to xOffset by: "+"\""+xOffsetPixels + "\"" + "pixels" + "\""  + "and yOffset by: "+"\""+yOffsetPixels + "\"" + "pixels"+ "\"");
		} catch (NoSuchElementException ex) {
			LoggingManager.getReportLogger().log(Status.FAIL,"\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + ex);
			throw (ex);
		} catch (TimeoutException ex) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\""+ LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + ex);
			throw (ex);
		} catch (Exception ex) {
			LoggingManager.getReportLogger().log(Status.FAIL,"\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + ex);
			throw (ex);
		}
	}
  
    /**
     * @param element
     * @param input
     */
    public static void sendKeys(By element,String input) {
		instance.getDriver().findElement(element).sendKeys(input);
	}
    
    /**
     * Clear text using backspace.
     *
     * @param textBox the text box
     */
    public static void clearTextUsingBackSpace(By textBox) {
		try {

			WebDriverWait wait = new WebDriverWait(instance.getDriver(), 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(textBox));
			int length = instance.getDriver().findElement(textBox).getAttribute("value").length();
			for (int i = 0; i <= length; i++) {
				instance.getDriver().findElement(textBox).sendKeys(Keys.BACK_SPACE);
			}
			LoggingManager.getReportLogger().log(Status.PASS,"\"" + " TextBox Cleared" + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().info("\"" + " TextBox Cleared" + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
		} catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Textbox to enter text not found: " + "\""+ LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Textbox to enter text not found" + e);
			throw (e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\""+ LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw (e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" +" Exception found at " + "\""+ LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw (e);
		}
	}
    
    /**
	 * This method get the first word of specified string separated with spaces.
	 *
	 * @return String, 	value of string after split.
	 */
	public static String getFirstWordOfString(String savedEventText) {
		String text = null;
		text = savedEventText.split(" ")[0].replace("\"", "");	
		return text;	
	}

	/**
	 * This method splits the string based on delimiter and return the string on specified index of array.
	 *
	 * @param textToSplit 		 text to split
	 * @param delimiter			 delimiter by which user wants to split the string
	 * @param index				 index of array after string splits
	 * @return String			 string after split.
	 */
	public static String splitString(String textToSplit, String delimiter, int index) {
		String[] textArray = textToSplit.split(delimiter);
		String text = textArray[index];
		LoggingManager.getReportLogger().info("Text after split: "+text);
		return text;
	}

	/** This method converts input time into specified time format.
	 *
	 * @param time			time user want to format
	 * @param timeFormat	format on which user wants to convert the input time.
	 * @return string	 	formatted time.
	 */
	public static String getTimeInSpecifiedFormat(String time, String timeFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
		Date dateObj = null;
		try {
			dateObj = sdf.parse(time);
		} catch (ParseException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Error occurred while parsing the date. " + "\"" + e);
			LoggingManager.getConsoleLogger().error("\"" + "Error occurred while parsing the date." + "\"" + e);
		}
		time = sdf.format(dateObj);
		return time;
	}
	
	/**
	 * Fetch any date, past or future in specified date format.
	 *
	 * @param dateFormat    					format of date.
	 * @param dateType 							type of date : Calendar Day, Month or Year.
	 * @param numberOfDateTypeToSubtractAdd 	number of Date type (Day, Month or Year) user wants to subtract or add from specified date.
	 * @return String							Result date
	 */
	public static String getDateInSpecifiedFormat(String dateFormat, int dateType, int numberOfDateTypeToSubtractAdd) {
		String newDate=null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(dateType, numberOfDateTypeToSubtractAdd);
			newDate = new SimpleDateFormat(dateFormat).format(calendar.getTime());
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "New Date: " + newDate + "\"");
			LoggingManager.getConsoleLogger().info("\"" + "New Date: " + newDate + "\"");
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Error occurred while getting new date. " + "\"" + e);
			LoggingManager.getConsoleLogger().error("\"" + "Error occurred while getting new date." + "\"" + e);
            throw(e);
     }
		return newDate;
	}
}
