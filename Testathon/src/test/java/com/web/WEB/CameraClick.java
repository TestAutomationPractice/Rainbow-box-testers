package com.web.WEB;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.testathon.automation.core.DriverFactory;
import com.testathon.automation.core.TestBaseQuestD;
import com.testathon.automation.keywords.MKeywords;

public class CameraClick extends TestBaseQuestD{
	static DriverFactory instance = DriverFactory.getInstance();

	@Test(groups = {"Sanity"})
	public void clickCamera() throws FindFailed, IOException
	{
		SoftAssert softAssert = new SoftAssert();
		
		//Process process1  = Runtime.getRuntime().exec("cmd /c start cmd.exe /K start microsoft.windows.camera:");
		Runtime.getRuntime().exec("cmd /c start cmd.exe /K " + "start microsoft.windows.camera:");
		//Process process  = Runtime.getRuntime().exec("explorer.exe Shell:::{2559a1f3-21d7-11d4-bdaf-00c04f60b9f0}");
		Screen s = new Screen();
		
		//s.type("C:\\Users\\prabhatkumar03\\Desktop\\run_enterTextbox.png","microsoft.windows.camera:");
		//s.click("C:\\Users\\prabhatkumar03\\Desktop\\run_clickOk.png");
		MKeywords.Sleep(2000);
		
		s.click("C:\\Users\\prabhatkumar03\\Desktop\\Camera_button.png");
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH_mm_ss");  
		   LocalDateTime now = LocalDateTime.now();  
		   System.out.println(dtf.format(now));
		   String x = "WIN_20190922_"+dtf.format(now)+"_Pro";
		
		   s.click("C:\\Users\\prabhatkumar03\\Desktop\\cancel_camerabutton.png");
			   
//			instance.getDriver().findElement(By.cssSelector("[type=\"email\"]")).sendKeys("prabhat.kumar03@nagarro.com");
//			instance.getDriver().findElement(By.cssSelector("[type=\"password\"]")).click();
//			
//			instance.getDriver().findElement(By.cssSelector("[type=\"password\"]")).sendKeys("jaimatadi123#");
//			instance.getDriver().findElement(By.cssSelector("[type=\"submit\"]")).click();
		   
		   
		//s.type("C:\\Users\\prabhatkumar03\\Desktop\\open_upload.png","C:\\Users\\prabhatkumar03\\Pictures\\Camera Roll\\"+x);
		//s.click("C:\\Users\\prabhatkumar03\\Desktop\\open_file.png");

		   softAssert.assertAll();
	}
}
