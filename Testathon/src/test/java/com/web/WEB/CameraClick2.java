package com.web.WEB;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.FindFailed;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.testathon.automation.core.DriverFactory;
import com.testathon.automation.core.TestBaseQuestD;
import com.testathon.automation.keywords.MKeywords;
import com.testathon.automation.keywords.SeKeywords;

public class CameraClick2 extends TestBaseQuestD{
	static DriverFactory instance = DriverFactory.getInstance();

	@Test(groups = {"Sanity"})
	public void clickCamera() throws FindFailed, IOException
	{
		SoftAssert softAssert = new SoftAssert();
		
		//instance.getDriver().findElement(By.cssSelector("#\\31 2775619 > div > div.clearfix.m-top-16 > div.button.view-seats.fr")).click();
		
		Actions a = new Actions(instance.getDriver());
		
		for(int i=229; i<= 782; i++) {
			for(int j=116; j<=272; j++) {
				WebElement ele = instance.getDriver().findElement(By.cssSelector(".lower-canvas [data-type=\"lower\"]"));
				System.out.println(ele.getSize().getHeight());
				System.out.println(ele.getSize().getWidth());
				a.moveToElement(ele).moveByOffset(i, j).perform();
				if(SeKeywords.isElementPresent(By.cssSelector(".lower-canvas [data-type=\"lower\"].pointer"))) {
					SeKeywords.click(By.cssSelector(".lower-canvas [data-type=\"lower\"].pointer"));
					System.out.println("DONE......");
					MKeywords.Sleep(5000);
					break;
				}
		}
		softAssert.assertAll();
	}
}}
