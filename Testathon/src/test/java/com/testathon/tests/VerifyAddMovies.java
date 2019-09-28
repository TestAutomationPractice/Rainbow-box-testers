package com.testathon.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.testathon.automation.core.DriverFactory;
import com.testathon.automation.core.TestBaseQuestD;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
public class VerifyAddMovies extends TestBaseQuestD{
	static DriverFactory instance = DriverFactory.getInstance();

	final static String ROOT_URI = "http://autothon-nagarro-backend-x03.azurewebsites.net";

	private static String payload = "{\r\n" + 
			"	\"categories\": {\r\n" + 
			"		\"type\": \"array\",\r\n" + 
			"		\"items\": {\r\n" + 
			"			\"type\": \"string\",\r\n" + 
			"			\"enum\": [\"Comedy\", \"Thriller\", \"Drama\"]\r\n" + 
			"		}\r\n" + 
			"	},\r\n" + 
			"	\"description\": \"jnsdckjdnckjndslknd\",\r\n" + 
			"	\"director\": \"cncdbjkdskjdsnkj\",\r\n" + 
			"	\"image\": \"http://autothon-nagarro-backend-x03.azurewebsites.net/movie\",\r\n" + 
			"	\"rating\": 5,\r\n" + 
			"	\"rented\": false,\r\n" + 
			"	\"reviews\": [],\r\n" + 
			"	\"title\": \"dsdccdscsdcdscd\"\r\n" + 
			"}";

	@Test(groups = {"Sanity"})
	public void verifyAddMovies() {

		SoftAssert softAssert = new SoftAssert();
		RestAssured.baseURI = ROOT_URI;
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("user", "undefined");

		httpRequest.body(payload);
		Response response = httpRequest.post("/movies");
		int statusCode = response.getStatusCode();

		softAssert.assertEquals(statusCode, "200", "Failed to add movies");
		System.out.println(statusCode);
		softAssert.assertAll();

	}

}

