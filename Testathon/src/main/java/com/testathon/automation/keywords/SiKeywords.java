package com.testathon.automation.keywords;

import org.sikuli.basics.Settings;
import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;

import com.aventstack.extentreports.Status;
import com.testathon.automation.core.Config;
import com.testathon.automation.logging.LoggingManager;

public class SiKeywords {

	private static String SikuliImagePath = Config.SikuliImagePath;
	
	/**
	 * Click on a particular image.
	 *
	 * @param Path of the image.
	 */
	public static void click(String imageName)
	{	  
		try
		{
			if(imageName != null && !SikuliImagePath.isEmpty())
			{
			String imagePath = SikuliImagePath + "//" + imageName;
			Pattern image = new Pattern(imagePath);
			Screen screen=new Screen();
			screen.find(image);
			screen.click(image);
			LoggingManager.getConsoleLogger().info(imageName + " image is clicked");
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Clicked the image " + "\"" + imageName + "\"");
			}
			else
				LoggingManager.getConsoleLogger().error("ImageName is not mentioned in test method");
		}catch(FindFailed ex){
			LoggingManager.getConsoleLogger().error("Either file is not available on the path given or file path is wrong " + ex);
		}

	}


	/**
	 * Capture a screenshot and save the file on a particular path.
	 *
	 * @param pathToSave path to save the image.
	 * @param fileName filename for the image file.
	 */
	public static void captureScreen(String fileName)
	{	  
		String absPath = null;
		try
		{
			if(SikuliImagePath != null && !SikuliImagePath.isEmpty())
			{
				Screen screen=new Screen();
				ScreenImage img = screen.capture();
				if(fileName != null && !fileName.isEmpty())
				{
					absPath = MKeywords.getAbsolutePath(SikuliImagePath);	
					img.save(absPath,fileName);
					LoggingManager.getConsoleLogger().info(fileName + " is saved at a location " + absPath);
				}
				else
					LoggingManager.getConsoleLogger().info("default file name is given to image and saved at " + SikuliImagePath);
			}

		}
		catch(Exception ex){
			LoggingManager.getConsoleLogger().error("Failed to read data from sikuli file" + ex);
		}

	}

	/**
	 * Search a text on a screen and click on it.
	 *
	 * @param inputText  Text to be searched.
	 * @param event either singleclick or doubleclick
	 */

	public static void searchAndClickText(String inputText, String event )
	{

		Settings.OcrTextRead = true; // to switch on the Region.text() function
		Settings.OcrTextSearch = true; // to switch on finding text with find("some text")
		Screen screen=new Screen();
		try {


			if(inputText != null && !inputText.isEmpty())
			{
				Match str = screen.findText(inputText);
				if(event.equalsIgnoreCase("doubleclick"))
				{
					screen.doubleClick();
					LoggingManager.getConsoleLogger().info("Double click has been performed on text " + inputText);
					LoggingManager.getReportLogger().log(Status.PASS, "\"" + "DoubleClicked the text " + "\"" + inputText + "\"");
				}
				else if (event.equalsIgnoreCase("singleclick"))
				{
					screen.click(str);
					LoggingManager.getConsoleLogger().info("Single click has been performed on text " + inputText);
					LoggingManager.getReportLogger().log(Status.PASS, "\"" + "SingleClicked the text " + "\"" + inputText + "\"");
				}
				else
					LoggingManager.getConsoleLogger().error("Either action is not mentioned or input action is wrong. Action should be either single click or double click");
			}
			else
				LoggingManager.getConsoleLogger().error("Text is not mentioned which need to be searched and clicked");

		} catch (FindFailed ex) {
			// TODO Auto-generated catch block
			LoggingManager.getConsoleLogger().error("Some error occured in identfying text on UI" + ex);
		}
	}



	/**
	 * Double click on a particular image.
	 *
	 * @param Path of the image.
	 */
	public static void doubleClick(String imageName)
	{	  
		try
		{
			String imagePath = SikuliImagePath + "//" + imageName;
			if(imagePath != null && !imagePath.isEmpty())
			{
			Pattern image = new Pattern(imagePath);
			Screen screen=new Screen();
			screen.find(image);
			screen.doubleClick(image);
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Double clicked the image " + "\"" + imageName + "\"");
		}
			else
				LoggingManager.getConsoleLogger().error("Either Imagepath is not given or image is not present on the given path" + imagePath);
		}
		catch(Exception ex){
			LoggingManager.getConsoleLogger().error("Some error occured in identfying image on UI" + ex);
		}

	}

	/**
	 * Enter text in a text box identified by a image.
	 *
	 * @param imagePath Path of the image.
	 * @param Text text which need to be entered
	 */
	public static void enterText(String imageName,String text)
	{	  
		try
		{
			String imagePath = SikuliImagePath + "//" + imageName;
			if(imagePath != null && !imagePath.isEmpty())
			{
			Pattern image = new Pattern(imagePath);
			Screen screen=new Screen();
			screen.find(image);
			screen.type(image, text);
			LoggingManager.getConsoleLogger().info(text + " text is entered in the object " + imageName);
			LoggingManager.getReportLogger().log(Status.PASS, "\"" + "Entered Text " + text + " in an object identified by the image " + "\"" + imageName + "\"");
		
			}
			else
				LoggingManager.getConsoleLogger().error("Either Imagepath is not given or image is not present on the given path" + imagePath);
		}
		catch(Exception ex){
			LoggingManager.getConsoleLogger().error("Some error occured in identfying image on UI" + ex);
		}

	}

	/**
	 * To check whether an image is present on the UI or not.
	 *
	 * @param imagePath path of the image.
	 */

	public static boolean isImagePresent(String imageName)
	{	
		boolean presence = false;  
		try
		{	
			String imagePath = SikuliImagePath + "//" + imageName;
			if(imagePath != null && !imagePath.isEmpty())
			{
				Pattern image = new Pattern(imagePath);
				Screen screen=new Screen();
				Match img= screen.exists(image);
				if(img != null)
				{
					presence= true;
					LoggingManager.getConsoleLogger().info(imageName + " image is present on UI");
					LoggingManager.getReportLogger().log(Status.PASS, "\"" + imageName + " image is present on UI " + "\"");
				}
				else
				{
					presence= false;
					LoggingManager.getConsoleLogger().info(imageName + " image is not present on UI");
					LoggingManager.getReportLogger().log(Status.FAIL, "\"" + imageName + " image is not present on UI " + "\"");
				} 
			}
			else
				LoggingManager.getConsoleLogger().error("Either Imagepath is not given or image is not present on the given path" + imagePath);

		}
		catch(Exception ex){
			LoggingManager.getConsoleLogger().error("Some error occured in identfying image on UI" + ex);
		}
		return presence;

	}

	/**
	 * Move the screen either upward or downward as per mouse wheel movement.
	 *
	 * @param direction either up or down.
	 * @param stepCount No. of steps to move
	 */

	public static void screenScrollMouseWheel(String direction, int stepCount)
	{
		try{
			Screen screen=new Screen();
			if(direction.equalsIgnoreCase("up"))
			{screen.wheel(Button.WHEEL_UP,stepCount);
			LoggingManager.getConsoleLogger().info("Screen is moved up for a step count " + stepCount);}
			else if(direction.equalsIgnoreCase("down"))
			{screen.wheel(Button.WHEEL_DOWN,stepCount);
			LoggingManager.getConsoleLogger().info("Screen is moved down for a step count " + stepCount);}
			else
				LoggingManager.getConsoleLogger().error("Entered direction is wrong");
		}
		catch(Exception ex){
			LoggingManager.getConsoleLogger().error("Failed to read data from sikuli file" + ex);
		}
	}

	/**
	 * To Capture text from an image and verify it with input.
	 *
	 * @param filePath imageURL.
	 * @return true if text is found in the image
	 */
	public static boolean captureTextFromImage(String filePath, String text)
	{
		String parsedText = null;
		boolean flag = false;
		try
		{
			Settings.OcrTextRead = true; // to switch on the Region.text() function
			Settings.OcrTextSearch = true; // to switch on finding text with find("some text")
			Screen screen=new Screen();
			parsedText = screen.find(filePath).text();
			LoggingManager.getConsoleLogger().info("following text is extracted from the image" + parsedText);
			if(parsedText.contains(text)) 
			{
				flag=true;
			}
		}catch(FindFailed ex)
		{
			LoggingManager.getConsoleLogger().error("Either file is not available on the path given or file path is wrong " + ex);
		}

		return flag;

	}
}