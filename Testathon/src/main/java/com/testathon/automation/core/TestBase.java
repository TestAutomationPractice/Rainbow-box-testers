package com.testathon.automation.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.testathon.automation.logging.LoggingManager;
import com.testathon.automation.utils.CommonUtil;

import atu.testrecorder.exceptions.ATUTestRecorderException;
/*import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;*/
import io.appium.java_client.service.local.AppiumDriverLocalService;

/**
 * This is the base class in which all the initialization required to run the
 * test cases occurs. This class basically includes: 1) Initialization of
 * webdriver, extent reporting and logging. 2) Closing of browser and webdriver.
 */
public class TestBase {
	protected CommonUtil util;
	//private static ATUTestRecorder recorder;
	public static String BROWSER_VALUE = null;
	protected DriverOptions driverOptions;
	public static String platformValue = null;
	protected static SoftAssert softAssert;
	
	/**
	 * Instantiates a new test base.
	 * @param targetPathClient 
	 */
	public TestBase(String configPath, String targetPathClient) {
		
		Config.initConstants("/Config/app.properties", targetPathClient);
		String log4jPath = Config.log4jPath;
		PropertyConfigurator.configure(log4jPath);
		driverOptions = new DriverOptions();
		driverOptions.url = Config.ApplicationURL;
	}

	/**
	 * This method: 1) creates screenshot folder
	 * path if not exists. 2) creates ZipPath folder if not exists. 4) start video
	 * recording if set as 'true'.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ATUTestRecorderException
	 *             the ATU test recorder exception
	 * @throws InterruptedException 
	 */
	@BeforeSuite(alwaysRun = true)
	protected void BeforeSuite()
			throws IOException, InterruptedException {
		LoggingManager.getConsoleLogger().info("-----------------EXECUTION START----------------------");
		LoggingManager.getConsoleLogger().info(" : TestBase - BeforeSuite called");
		CommonUtil.isFolderExistAtPath(Config.TestReportFolder);
		CommonUtil.isFolderExistAtPath(Config.ScreenShotsPath);
		CommonUtil.isFolderExistAtPath(Config.ZipPath);
		CommonUtil.isFolderExistAtPath(Config.ExportFilePath);
		FileUtils.cleanDirectory(new File(Config.ScreenShotsPath));
		/*if (Config.SetVideoRecording.equalsIgnoreCase("True")) {
			recorder = CommonUtil.StartVideoRecording();
		}*/
	}

	/**
	 * This method creates extent reporting parent class instance.
	 */
	@BeforeClass(alwaysRun = true)
	protected void BeforeClass() {
		LoggingManager.getConsoleLogger()
				.info(" : TesTBase - BeforeClass called " + "Thread-ID : " + Thread.currentThread().getId());
		LoggingManager.getInstance().createParentTestNode(getClass().getName());
	}

	@BeforeTest(alwaysRun = true)
	protected void BeforeTest() {
		LoggingManager.getConsoleLogger().info(" : TesTBase - BeforeTest called");
	}

	/**
	 * Method executes the prerequisites for test execution example: creating driver
	 * instance, launching browser with application URL. This will also execute the
	 * extent report child node creation steps
	 * 
	 * @param webdriverHost
	 *            IP of the hub machine
	 * @param webdriverPort
	 *            Port on which grid is running
	 * @param browser
	 *            browser for AUT
	 * @param platform
	 *            OS value example: WINDOWS, MAC etc
	 * @param method
	 *            test method to be invoked
	 * 
	 * @throws FrameworkException
	 *             User defined exception instance
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "webdriverHost", "webdriverPort", "browser", "platform" })
	protected void BeforeMethod(String webdriverHost, int webdriverPort, String browser, String platform, Method method)
			throws FrameworkException {
		try {
			softAssert =  new SoftAssert();
			BROWSER_VALUE = browser;
			platformValue = platform;
			LoggingManager.getConsoleLogger().info(" : TestBase - BeforeMethod called");
			LoggingManager.getConsoleLogger().info(" Browser called -> " + browser);
			LoggingManager.getConsoleLogger().info("Test Environment -> " + Config.Environment);
			String runOnGrid = Config.runOnGrid;

			AppiumDriverLocalService abc = null;
			
			/* Check Internet connectivity is available or not */
			/*
			 * Process process = java.lang.Runtime.getRuntime().exec("ping www.google.com");
			 * if(process.waitFor() == 1){ throw new
			 * FrameworkException("Internet not available"); }
			 */
			setDriverOptions(webdriverHost, webdriverPort, browser, platform);
			
			if((platform.equalsIgnoreCase("WINDOWS") && runOnGrid.equalsIgnoreCase("Yes")))
			{
				DriverFactory.getInstance().setDriverWithGrid(driverOptions);
			}
			else if ((platform.equalsIgnoreCase("WINDOWS") && runOnGrid.equalsIgnoreCase("No")))
			{
				DriverFactory.getInstance().setDriverWithoutGrid(driverOptions);
			}
			else if(((platform.equalsIgnoreCase("LINUX")) || (platform.equalsIgnoreCase("UNIX"))) && runOnGrid.equalsIgnoreCase("Yes"))
			{
				DriverFactory.getInstance().setDriverWithGrid(driverOptions);
			}
			else if(((platform.equalsIgnoreCase("LINUX")) || (platform.equalsIgnoreCase("UNIX"))) && runOnGrid.equalsIgnoreCase("No"))
			{
				DriverFactory.getInstance().setDriverWithoutGrid(driverOptions);
			}
			else if (platform.equalsIgnoreCase("REST"))
			{
				LoggingManager.getConsoleLogger().info("Executing rest cases");
			} 
			else if (platform.equalsIgnoreCase("mobile"))
			{
				if(Config.deviceType.equalsIgnoreCase("android"))
				{ 
					abc = new Appium2().startServer();
				}
				else
				{
					System.out.println("Write code to start appium on macbook");
				}
				DriverFactory.getInstance().setDriverForMobile(abc,platform);
			}
			LoggingManager.getInstance().createChildTestNode(method.getName());
			//LoggingManager.getReportLogger().info("Test Environment -> " + Config.Environment);
			//LoggingManager.getReportLogger().info("Browser called -> " + browser);
		} catch (FrameworkException e) {
			LoggingManager.getConsoleLogger().error(e);
			throw e;
		} catch (Exception e) {
			LoggingManager.getConsoleLogger().error(e);
		}
	}

	/**
	 *  Sets the driver options based on the parameters received 
	 *  
	 * @param webdriverHost
	 *            IP of the hub machine
	 * @param webdriverPort
	 *            Port on which grid is running
	 * @param browser
	 *            browser for AUT
	 * @param platform
	 *            OS value example: WINDOWS, MAC etc
	 */
	private void setDriverOptions(String webdriverHost, int webdriverPort, String browser, String platform) {
		driverOptions.browser = browser;
		driverOptions.platformType = platform;
		driverOptions.webdriverHost = webdriverHost;
		driverOptions.webdriverPort = webdriverPort;
	}

	/**
	 * This method closes the browser after test execution
	 *
	 * @param result
	 *            gives user the result of test case.
	 */
	@AfterMethod(alwaysRun = true)
	protected void AfterMethod(ITestResult result) {
		LoggingManager.getConsoleLogger().info(" : TestBase - AfterMethod called");
		LoggingManager.getConsoleLogger().info(" Test Method completed -> " + result.getName());
		quitBrowser();
	}

	/**
	 * Clear the extent report parent instance after test class is executed
	 */
	@AfterClass(alwaysRun = true)
	protected void AfterClass() {
	try 
	{
		quitBrowser();
		if(platformValue.equalsIgnoreCase("Mobile"))
		{
			Appium2 app2 = new Appium2();
			if(app2.AppiumServerStatus())
				app2.stopAppiumServer();
		}
		
	}catch (Exception e) {
		LoggingManager.getConsoleLogger().error(e.getStackTrace());
	}
		
	LoggingManager.getConsoleLogger().info(" : TestBase - AfterClass called");
	LoggingManager.getInstance().clearParentTestNode();
	}

	@AfterTest(alwaysRun = true)
	protected void AfterTest() {
		LoggingManager.getConsoleLogger().info(" : TestBase - AfterTest called");
	}

	/**
	 * This method: 1) closes the extent reporting and flushes the report to an HTML file. 2) creates the zip folder of
	 * screenshots. 3) Email the test report if set as true. 4) stops recording if
	 * set as true.
	 *
	 * @throws Exception
	 *             on error.
	 */
	@AfterSuite(alwaysRun = true)
	protected void afterSuite() throws Exception {
		LoggingManager.getConsoleLogger().info(" : TestBase - AfterSuite called");
		LoggingManager.getInstance().writeTestReport();
		CommonUtil.setScreenshotRelativePath();
		String zipFilePath = Config.ZipPath + CommonUtil.generateUniqueName() + ".zip";
		CommonUtil.zipFolder(Paths.get(Config.ScreenShotsPath), Paths.get(zipFilePath));
		if (Config.setReportEmail.equalsIgnoreCase("True")) {
			CommonUtil.SendMail(zipFilePath);
		}

		/*if (Config.SetVideoRecording.equalsIgnoreCase("True")) {
			recorder.stop();
		}*/
		LoggingManager.getConsoleLogger().info("---------------EXECUTION COMPLETED--------------------");
	}

	/**
	 * Closes the browser instance
	 */
	public void quitBrowser() {
		try {
			if(BROWSER_VALUE.equalsIgnoreCase(DriverFactory.FIREFOX) && platformValue.equalsIgnoreCase("windows")){
				Runtime.getRuntime().exec("Taskkill /IM firefox.exe /F");
			}else if (BROWSER_VALUE.equalsIgnoreCase(DriverFactory.FIREFOX) && platformValue.equalsIgnoreCase("linux")){
				Runtime.getRuntime().exec("killall -9 firefox.exe");
			}else {
				DriverFactory.getInstance().removeDriver();
			}
		}catch(IOException e) {
			LoggingManager.getConsoleLogger().error("Error ocurred while closing this browser." + e);
		}
	}

}
