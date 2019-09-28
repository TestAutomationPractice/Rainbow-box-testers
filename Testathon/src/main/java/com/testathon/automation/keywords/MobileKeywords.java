package com.testathon.automation.keywords;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.testathon.automation.core.DriverFactory;
import com.testathon.automation.logging.LoggingManager;

public class MobileKeywords
{
	static	DriverFactory instance  = DriverFactory.getInstance();
	/** 
	 * @author mohitmaliwal
	 * @throws InterruptedException 
	 */

	public static void click(By button) throws InterruptedException {
		try
		{			
			WebElement abc = instance.getDriver().findElement(button);
			abc.click();	
			LoggingManager.getReportLogger().log(Status.PASS, "\"" +" Button Clicked " + "\"" + button + "\"");		
		}
		catch (NoSuchElementException ex){
			LoggingManager.getConsoleLogger().error("No Element Found to click" + ex);
		}
	}

	public static void waitForSpinnerInvisibility() throws InterruptedException
	{
		try 
		{
			boolean isProgressPresent = MobileKeywords.exists(MKeywords.findElement("NativeGeneral", "progress"));
			if(isProgressPresent)
			{
				WebDriverWait wait = new WebDriverWait(instance.getDriver(), 60);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(MKeywords.findElement("NativeGeneral","progress")));
				LoggingManager.getReportLogger().log(Status.PASS, "\"" +" Spinner is Here" + "\"");
			}
		} 
		catch (NoSuchElementException ex)
		{
			System.err.format("No Element Found to enter text" + ex);
		}
	}

	public static void setText(By textBox, String value) {
		try{			
			instance.getDriver().findElement(textBox).sendKeys(value);
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + value + "\"" + " is entered in text box " + "\"" + textBox + "\"");
		}
		catch (NoSuchElementException ex){
			LoggingManager.getConsoleLogger().error("No Element Found to enter text" + ex);
		}
	}

	public static String getText(By textBox) 
	{
		String myText = null;
		try
		{			
			myText = instance.getDriver().findElement(textBox).getText();
		}
		catch (NoSuchElementException ex)
		{
			LoggingManager.getConsoleLogger().error("No Element Found to enter text" + ex);
		}
		return myText;
	}

	public static boolean checkElementEnabled(By button)
	{
		boolean elementEnabled = false;
		try 
		{
			elementEnabled = instance.getDriver().findElement(button).isEnabled();
		}
		catch(NoSuchElementException ex)
		{
			LoggingManager.getConsoleLogger().error("No Element Found to enter text" + ex);
		}
		return elementEnabled;

	}



	public static void allowAppPermission()
	{	 

		while(instance.getDriver().findElements(By.xpath("//*[@class='android.widget.Button'][2]")).size() > 0)
		{
			WebElement abc = instance.getDriver().findElement(By.xpath("//*[@class='android.widget.Button'][2]"));
			abc.click();
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + abc + "\"" + "is clicked to give the permission");
		}	 
	}

	public static void clickNext(String objectName,String pageName)
	{

	}
	public static void clickSelect(String objectName,String pageName)
	{

	}
	public static void waitForElementVisibility(By element , int secs){
		try
		{
			MKeywords.Sleep(10000);
			WebDriverWait wait = new WebDriverWait(instance.getDriver(), secs);
			wait.until(ExpectedConditions.presenceOfElementLocated(element));
		}
		catch (Exception ex)
		{
			LoggingManager.getConsoleLogger().error("No Element Found to enter text" + ex);
		}
	}
	public WebElement returnElement(By description,String value)
	{
		WebElement 	retElement=null;
		retElement=instance.getDriver().findElement(description.id(value));
		return retElement;
	}
	public static void type(WebElement buttonObj,String value)
	{
		buttonObj.sendKeys(value);
	}
	public static boolean exists(By userName) 
	{
		return instance.getDriver().findElement(userName).isDisplayed();
	}

	public static WebElement checkVisibility()
	{
		WebDriverWait wait = null;
		return wait.until(ExpectedConditions.visibilityOf(instance.getDriver()
				.findElement(
						By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']"))));

	}

}
