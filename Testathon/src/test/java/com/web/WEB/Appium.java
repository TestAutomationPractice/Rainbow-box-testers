package com.web.WEB;


import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class Appium {
		

	     static AppiumDriver<MobileElement> driver;
	     
	    

	    @Test 
		public static void launchApplication() throws Exception {
	    	 
	        	
		DesiredCapabilities cap = new DesiredCapabilities();
	
		cap.setCapability("deviceName", "Nokia 6.1 plus");
		cap.setCapability("udId","DRGID18091702382");
		cap.setCapability("platformName", "Android");
		cap.setCapability ("platformVersions", "9.0");
		cap.setCapability ("appPackage", "com.lutron.mmw");
		cap.setCapability("appActivity", "com.lutron.mmw.activity.MyHomeActivity");
		
		URL url = new URL("http://127.0.0.1:4723/wd/hub");
		driver = new AppiumDriver<MobileElement>(url,cap);
			
		System.out.println("The system has started");
		MobileElement AgreeButton = driver.findElement(By.id("com.lutron.mmw:id/btn_accept"));
		AgreeButton.click();
		
		MobileElement ContinueButton = driver.findElement(By.id("com.lutron.mmw:id/btn_continue"));
		ContinueButton.click();
		
		MobileElement SignInButton = driver.findElement(By.id("com.lutron.mmw:id/btn_sign_in"));
		SignInButton.click();
		
		Thread.sleep(8000);
			
		
	//	MobileElement EnterEmail = driver.findElement(By.id("user_email"));
		
	//	driver.findElement(By.id("user_email")).sendKeys("dbisht@lutron.com");
		
		MobileElement EnterEmail = driver.findElement(By.xpath("//*[@resource-id='user_email']"));
		
    	EnterEmail.sendKeys("dbisht@lutron.com");
		
	//	MobileElement EnterPassword = driver.findElement(By.id("user_password"));
		
		driver.findElement(By.id("user_password")).sendKeys("password");
	//	EnterPassword.sendKeys("password");
		
	//	driver.quit();
	}
	
} 