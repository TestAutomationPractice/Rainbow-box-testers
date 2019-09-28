package com.web.pageobjects;


import java.util.List;

import org.openqa.selenium.WebElement;

import com.testathon.automation.core.DriverFactory;
import com.testathon.automation.keywords.MKeywords;
import com.testathon.automation.keywords.SeKeywords;

public class HomePage {
	private static DriverFactory instance = DriverFactory.getInstance();

	private static final String pageName = "HomePage";

	public static void clickViewMoreByMovieName(String name) {

		List<WebElement> MoviesName = instance.getDriver().findElements(MKeywords.findElement(pageName, "MoviesName"));
		int i=0;
		for(WebElement movieName: MoviesName) {
			if(movieName.getText().replaceAll(" ", "").trim().equals(name.replaceAll(" ", "").trim())) {
				instance.getDriver().findElements(MKeywords.findElement(pageName, "ViewMoreLinks")).get(i).click();;
				break;
			}
			i++;
		}
		
//		By controlImagesLevel1 = MKeywords.findElement(pageName, "ControlsImagesLevel1");
//		SeKeywords.isElementPresent(controlImagesLevel1);
//		WebElement ele = instance.getDriver().findElement(MKeywords.findElement(pageName, "AcceptCookies"));

	}
	
	
}
