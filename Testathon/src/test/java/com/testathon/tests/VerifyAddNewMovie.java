package com.testathon.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.testathon.automation.core.DriverFactory;
import com.testathon.automation.core.TestBaseQuestD;
import com.web.pageobjects.CommonPage;

public class VerifyAddNewMovie extends TestBaseQuestD{
	static DriverFactory instance = DriverFactory.getInstance();

	@Test(groups = {"Sanity"})
	public void verifyMoviePage() {
		
		SoftAssert softAssert = new SoftAssert();
		CommonPage.clickLoginLink();
		
		CommonPage.enterUsername();
		CommonPage.enterPassword();
		CommonPage.clickLogin();
		CommonPage.addMovie(); 
		
		
		 softAssert.assertAll();
		
	}
}

