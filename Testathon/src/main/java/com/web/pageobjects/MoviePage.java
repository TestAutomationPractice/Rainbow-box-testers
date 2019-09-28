package com.web.pageobjects;


import com.testathon.automation.core.DriverFactory;
import com.testathon.automation.keywords.MKeywords;

public class MoviePage {
	private static DriverFactory instance = DriverFactory.getInstance();

	private static final String pageName = "MoviePage";

	public static boolean verifyMovieName(String name) {
		boolean value=false;
		HomePage.clickViewMoreByMovieName(name);
		String movieHeader = instance.getDriver().findElement(MKeywords.findElement(pageName, "MoviesName")).getText();
		
		if(movieHeader.replaceAll(" ", "").trim().equals(name.replaceAll(" ", "").trim())) {
			value=true;
		}
		
		
		return value;
	}
}
