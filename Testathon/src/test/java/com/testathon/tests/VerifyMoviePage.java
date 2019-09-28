package com.testathon.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.testathon.automation.core.DriverFactory;
import com.testathon.automation.core.TestBaseQuestD;
import com.web.pageobjects.HomePage;
import com.web.pageobjects.MoviePage;

public class VerifyMoviePage extends TestBaseQuestD{
	static DriverFactory instance = DriverFactory.getInstance();

	@Test(groups = {"Sanity"})
	public void verifyMoviePage() {
		
		SoftAssert softAssert = new SoftAssert();
		
		
		System.out.println(MoviePage.verifyMovieName("Avatar"));
		
		 softAssert.assertAll();
		
	}
}

