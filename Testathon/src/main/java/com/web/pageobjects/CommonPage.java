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
}
