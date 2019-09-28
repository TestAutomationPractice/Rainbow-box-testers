package com.testathon.automation.keywords;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.testathon.automation.core.DriverFactory;
import com.testathon.automation.logging.LoggingManager;

/**
 * This class contains all the keywords created using Java script.
 */
public class JavaScriptKeywords {

	private static DriverFactory instance = DriverFactory.getInstance();

	/**
	 * This method click on the last button present on the pop up using java script.
	 */
	public static void jsClickOnPopupLastButton(){
		((JavascriptExecutor) instance.getDriver()).executeScript(" $('.modal-dialog').filter(':visible').find('.btn').last().click()");
		LoggingManager.getReportLogger().log(Status.PASS, "Button Clicked");
	}

	/**
	 * This method click on the first button present on the pop up using java script.
	 */
	public static void jsClickOnPopupFirstButton(){
		((JavascriptExecutor) instance.getDriver()).executeScript(" $('.modal-dialog').filter(':visible').find('.btn').first().click()");
		LoggingManager.getReportLogger().log(Status.PASS, "Button Clicked");
	}

	/**
	 * This method click on element using java script by a By element.
	 * 
	 * @param byElement - element on page
	 */
	public static void clickUsingJs(By byElement) {
		WebElement webElement = instance.getDriver().findElement(byElement);
		clickUsingJs(webElement);
	}
	
	/**
	 * This method click on element using java script by a WebElement.
	 *
	 * @param element	element on page.
	 */
	public static void clickUsingJs(WebElement element){			
		JavascriptExecutor executor = (JavascriptExecutor)instance.getDriver();
		MKeywords.Sleep(1000);
		executor.executeScript("arguments[0].click();", element);
	}
	
	/**
	 * This method waits for 60 seconds max for ajax call to complete.
	 */
	public static void waitForAjax() {
		int seconds = 10;
		try {
		LoggingManager.getConsoleLogger().info("Number of ajax connections found: " + ((JavascriptExecutor) instance.getDriver()).executeScript("return jQuery.active"));
		if((Long) ((JavascriptExecutor) instance.getDriver()).executeScript("return jQuery.active") > 0) {
			LoggingManager.getConsoleLogger().info("Waiting for ajax call to complete...");
	        new WebDriverWait(instance.getDriver(), seconds).until(new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver driver) {	
	            	Boolean isAjaxCallComplete = (Boolean) ((JavascriptExecutor) instance.getDriver()).executeScript("return jQuery.active == 0");
	            	if(isAjaxCallComplete) {
	            		LoggingManager.getConsoleLogger().info("\"" + "Ajax call completed." + "\"");
	            	}
					return isAjaxCallComplete;
	            }
	        });
		}
        MKeywords.Sleep(2000);
		}catch(TimeoutException e) {
			LoggingManager.getConsoleLogger().info("\"" + "Ajax call did not complete. Waited for " + seconds + " seconds. " + e + "\"");
		}catch(Exception e) {
			LoggingManager.getConsoleLogger().info("\"" + "Ajax call did not complete." + e + "\"");
		}
    }
	
	/**
	 * This method scroll the page to view upward.
	 *
	 * @param element	 element user wants to see into view.
	 * @param scrollDown		 true if element user scrolling to is below the where user currently are or
	 * 						 false if element user scrolling to is above where user currently are.
	 */
	public static void scrollElementIntoView(WebElement element, boolean scrollDown){			
		JavascriptExecutor executor = (JavascriptExecutor)instance.getDriver();
		executor.executeScript("arguments[0].scrollIntoView("+scrollDown+");", element);
//		LoggingManager.getReportLogger().log(Status.INFO, "\"" + "scrolled to element: " + "\"" + element.getText() + "\"");
//		LoggingManager.getConsoleLogger().info("\"" + "scrolled to element: " + "\"" + element.getText() + "\"");
	}
	
	/**
	 * This method scroll the page to view upward.
	 *
	 * @param element	 		 element user wants to see into view.
	 * @param scrollDown		 true if element user scrolling to is below the where user currently are or
	 * 						 false if element user scrolling to is above where user currently are.
	 */
	public static void scrollElementIntoView(By element, boolean scrollDown){	
		WebElement webElement = instance.getDriver().findElement(element);
		JavascriptExecutor executor = (JavascriptExecutor)instance.getDriver();
		executor.executeScript("arguments[0].scrollIntoView("+scrollDown+");", webElement);
//		LoggingManager.getReportLogger().log(Status.INFO, "\"" + "scrolled to element: " + "\"" + webElement.getText() + "\"");
//		LoggingManager.getConsoleLogger().info("\"" + "scrolled to element: " + "\"" + webElement.getText() + "\"");
	}

	/**
	 * This method scroll the page to view upward.
	 */
	public static void scrollPageUP(){			
		JavascriptExecutor executor = (JavascriptExecutor)instance.getDriver();
		executor.executeScript("scroll(0, -100);");
	}

	/**
	 * This method scroll the page to view downward.
	 */
	public static void scrollPageDOWN(){			
		JavascriptExecutor executor = (JavascriptExecutor)instance.getDriver();
		executor.executeScript("scroll(0, 250);");
	}

	/**
	 * This method gets the text of element by attribute using JS.
	 *
	 * @param attrName 		the name of attribute
	 * @param attrValue 	the value of attribute
	 * @return String 		text of element found
	 */
	public static String getTextByAttributeUsingJS(String attrName, String attrValue){
		Object obj = ((JavascriptExecutor) instance.getDriver())
				.executeScript("return " + "document.querySelectorAll('["+ attrName +"=\""+attrValue+"\"]')[0].value");
		return obj.toString();
	}

	/**
	 * Get the text by element Id using JS.
	 *
	 * @param controlId 		the name of control
	 * @return String 	text of element found
	 */
	public static String getTextByElementIdUsingJS(String controlId){
		Object obj = ((JavascriptExecutor) instance.getDriver())
				.executeScript("return " + "document.getElementById(\"" + controlId + "\").value");
		return obj.toString();
	}

	/**
	 * Get the text for dropdown by attribute using JS.
	 *
	 * @param key 		the name of attribute
	 * @param value 	the value of attribute
	 * @return String 	text of element found
	 */
	public static String getTextForDropDownByAttributeUsingJS(String key, String value){

		String js = "function getSelectedText(elementId) {" + 
				"    var elt = document.querySelectorAll('["+ key +"=\""+value+"\"]')[0];" + 				
				"    if (elt.selectedIndex == -1)" + 
				"        return null;" + 
				"    return elt.options[elt.selectedIndex].text;" + 
				"}" + 
				"return getSelectedText('" + value + "');";

		Object obj = ((JavascriptExecutor) instance.getDriver())
				.executeScript(js);

		return obj.toString();
	}
	
	
	/**
	 * Verify info icon heading using JS
	 * 
	 * @param headingPath		heading Path.
	 * @param textToVerify		Text user want to verify.
	 * @return true,			if Successful.
	 */
	public static boolean verifyInfoHeading(By headingPath, String textToVerify) {
		boolean result = false;
        WebElement element = instance.getDriver().findElement(headingPath);
        String theTextIWant = (String) ((JavascriptExecutor) instance.getDriver()).executeScript("return arguments[0].innerHTML;",element);
        if(theTextIWant.contains(textToVerify)) {
        	result = true;
        }
		return result;
	}
	
	/**
	 * Verify label of image under info icon using JS
	 * 
	 * @param element		element.
	 * @param textToVerify	List of Text to verify.
	 * @return true, 		if successful.
	 */
	public static boolean verifyImageInfoLabel(By element, List<String> textToVerify){
		boolean result = false;
		try {
			ArrayList<String> runtimeValues = new ArrayList<>();
			ArrayList<String> values = new ArrayList<>();
			WebElement childList = instance.getDriver().findElement(element);
	        List<WebElement> options = childList.findElements(By.className("thumbsnail"));
	        for (WebElement option : options) {
	               String optionText = (String) ((JavascriptExecutor) instance.getDriver()).executeScript("return arguments[0].innerText;",option);
	               runtimeValues.add(optionText.trim());
	        }
	        for(String item:textToVerify) {
	        	values.add(item);
	        }
	        if(runtimeValues.containsAll(values) || runtimeValues.equals(values)) {
				LoggingManager.getReportLogger().log(Status.PASS, "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " is verified successfully" + "\"");
				result = true;		
			}
		}catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Element not found: " + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Exception: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Exception: " + e);
			throw(e);
		}	
        return result;
	}
	
	/**
	 * Verify content under info icon using JS
	 * 
	 * @param element		element.
	 * @param textToVerify	list of text to verify
	 * @return true,		if successful.
	 */
	public static boolean verifyListInfoContent(By element, List<String> textToVerify){
		boolean result = false;
		try {
			ArrayList<String> runtimeValues = new ArrayList<String>();
			ArrayList<String> values = new ArrayList<String>();
			WebElement childList = instance.getDriver().findElement(element);
	        List<WebElement> options = childList.findElements(By.tagName("li"));
	        for (WebElement option : options) {
	        	WebElement childHeading = option.findElement(By.tagName("h5"));
	               String optionText = (String) ((JavascriptExecutor) instance.getDriver()).executeScript("return arguments[0].innerText;",childHeading);
	               runtimeValues.add(optionText.trim());
	        }
	        for(String item:textToVerify) {
	        	values.add(item);
	        }
	        if(runtimeValues.containsAll(values) || runtimeValues.equals(values)) {
				LoggingManager.getReportLogger().log(Status.PASS, "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " is verified successfully" + "\"");
				result = true;	
			}
		}catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Element not found: " + e);
			throw(e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw(e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Exception: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("Exception: " + e);
			throw(e);
		}	
        return result;
	}
	
	/**
	 * Count dom elements by selector.
	 *
	 * @param selector the selector
	 * @return the count of dom elements
	 */
	public static int countDomElementsBySelector(String selector){
		MKeywords.Sleep(5000);
		String script = "return document.querySelectorAll('" + selector + "').length;";
		Object obj = ((JavascriptExecutor) instance.getDriver()).executeScript(script);

		return Integer.parseInt(obj.toString());
	}
}
