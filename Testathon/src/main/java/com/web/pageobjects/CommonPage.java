package com.web.pageobjects;


import org.openqa.selenium.By;

import com.testathon.automation.core.DriverFactory;
import com.testathon.automation.keywords.MKeywords;
import com.testathon.automation.keywords.SeKeywords;

public class CommonPage {
	private static DriverFactory instance = DriverFactory.getInstance();

	private static final String pageName = "CommonPage";

	public static void clickProfileLink() {
		By ele = MKeywords.findElement(pageName, "ProfileLink");
		SeKeywords.click(ele);
	}
	
	public static void clickLoginLink() {
		By ele = MKeywords.findElement(pageName, "LoginLink");
		SeKeywords.click(ele);
	}
	
	public static void enterUsername() {
		By ele = MKeywords.findElement(pageName, "UserName");
		SeKeywords.sendKeys(ele, "admin");
	}
		
	public static void enterPassword() {
		By ele = MKeywords.findElement(pageName, "Password");
		SeKeywords.sendKeys(ele, "password");
	}
	
	public static void clickLogin() {
		By ele = MKeywords.findElement(pageName, "Login");
		SeKeywords.click(ele);
	}
	
	public static void addMovie() {
		By ele = MKeywords.findElement(pageName, "Add_Movie");
		if(SeKeywords.isElementPresent(ele)) {
			SeKeywords.click(ele);		
		}
		By ele1 = MKeywords.findElement(pageName, "Enter_Title");
		SeKeywords.sendKeys(ele1, "Dabangg");
		By ele2 = MKeywords.findElement(pageName, "Enter_Director");
		SeKeywords.sendKeys(ele2, "Salman");
		By ele3 = MKeywords.findElement(pageName, "Enter_Description");
		SeKeywords.sendKeys(ele3, "Action_Movie");
		By ele4 = MKeywords.findElement(pageName, "Select_Category");
		SeKeywords.click(ele4);
		By ele5 = MKeywords.findElement(pageName, "Enter_URL");
		SeKeywords.sendKeys(ele5, "Movie_URL");
		By ele6 = MKeywords.findElement(pageName, "Select_Rating");
		SeKeywords.click(ele6);
		By ele7 = MKeywords.findElement(pageName, "Select_Save_Button");
		SeKeywords.click(ele7);
	}
	
	
}
