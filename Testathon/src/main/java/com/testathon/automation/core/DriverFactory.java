package com.testathon.automation.core;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.lutron.test.automation.listeners.WebDriverEventListners;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class DriverFactory {

	private static final String POPUP_ENABLE_COMMAND = "REG ADD \"HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\New Windows\" /F /V \"PopupMgr\" /T REG_SZ /D \"no\"";
	public static final String CHROME = "chrome";
	public static final String FIREFOX = "firefox";
	public static final String INTERNET_EXPLORER = "internet explorer";
	public static final String SAFARI = "safari";
	public static final String WINDOWS = "windows";
	public static final String EDGE = "edge";
	public static final String LINUX = "linux";
	public static final String UNIX = "unix";
	private static Logger log = Logger.getLogger(DriverFactory.class);
	private final ThreadLocal<WebDriver> driver; // thread local driver object for webdriver

	public static EventFiringWebDriver eDriver;
	public static AndroidDriver<?> adriver;
	
	public static String macip;
	public static DesiredCapabilities cap ;
	public static AppiumServiceBuilder serviceBuilder;
	public static  AppiumDriverLocalService appiumService;
	public static String  platformType;
	public static String  isHeadless;

	/**
	 * 
	 * Helper class to create singleton instance of {@link DriverFactory}
	 *
	 */
	private static class LazyHolder {
		private static final DriverFactory INSTANCE = new DriverFactory();
	}

	public static DriverFactory getInstance()
	{
		return LazyHolder.INSTANCE;
	}
	
	private DriverFactory() {
		driver = new ThreadLocal<WebDriver>(); 
	}

	// call this method to set the driver using browser value in case of no grid configuration and open the browser
	public void setDriverWithoutGrid(DriverOptions driverOptions) throws FrameworkException 
	{	
		try {
			log.info(" : setDriverNoGrid Method Called");
			DesiredCapabilities capability = null;

			isHeadless = Config.Headless;
			platformType = driverOptions.platformType;

			if(driverOptions.platformType.equalsIgnoreCase(WINDOWS))
			{
				System.setProperty("webdriver.chrome.driver", Config.ChromeDriverPathWindows);
				System.setProperty("webdriver.ie.driver",  Config.IEDriverPath);
				System.setProperty("webdriver.gecko.driver", Config.FirefoxDriverPath);
				System.setProperty("webdriver.edge.driver", Config.EdgeDriverPath);
			}

			else if(driverOptions.platformType.equalsIgnoreCase(LINUX)||driverOptions.platformType.equalsIgnoreCase(UNIX))
			{
				System.setProperty("webdriver.chrome.driver", Config.ChromeDriverPathLinuxUnix);
				System.setProperty("webdriver.ie.driver",  Config.IEDriverPath);
				System.setProperty("webdriver.gecko.driver", Config.FirefoxDriverPath);
				System.setProperty("webdriver.edge.driver", Config.EdgeDriverPath);
			}

			if(driverOptions.browser.equalsIgnoreCase(CHROME) && isHeadless.equalsIgnoreCase("true"))
			{
				capability = DesiredCapabilities.chrome();
				ChromeOptions option = DriverFactory.setChromeToDownloadFileAtSpecifiedPath(capability);
				option.addArguments("--headless");
				driver.set(new ChromeDriver(option));
			}
			else if(driverOptions.browser.equalsIgnoreCase(CHROME) && isHeadless.equalsIgnoreCase("false"))
			{
				capability = DesiredCapabilities.chrome();
				ChromeOptions option = new ChromeOptions();
				option = DriverFactory.setChromeToDownloadFileAtSpecifiedPath(capability);
				driver.set(new ChromeDriver(option));
			}
			else if(driverOptions.browser.equalsIgnoreCase(SAFARI))
			{
				driver.set(new SafariDriver());
			}

			else if(driverOptions.browser.equalsIgnoreCase(FIREFOX))
			{
				setFirefoxCapabilities();
			}
			else if(driverOptions.browser.equalsIgnoreCase(INTERNET_EXPLORER))
			{
				setIECapabilities();
			}
			else if(driverOptions.browser.equalsIgnoreCase(EDGE))
			{
				driver.set(new EdgeDriver());
			}
			else
			{
				throw new FrameworkException("Invalid browser option chosen for local execution : "+ driverOptions.browser);
			}
			WebDriver currentDriver = DriverFactory.getInstance().getDriver();
			currentDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			currentDriver.manage().window().maximize();
			currentDriver.get(driverOptions.url);
			log.info(" : Application Opened");
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new FrameworkException(e.getMessage());
		}
	}

	/**
	 * Sets the desired capabilities of firefox.
	 */
	private void setFirefoxCapabilities() {
		DesiredCapabilities capability = DesiredCapabilities.firefox();
		capability.setCapability("marionatte", true);
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.merge(capability);
		// Creating firefox profile
		FirefoxProfile profile = new FirefoxProfile();
		// Instructing firefox to use custom download location
		profile.setPreference("browser.download.folderList", 2);
		// Setting custom download directory
		profile.setPreference("browser.download.dir", Config.ExportFilePath);
		// Skipping Save As dialog box for types of files with their MIME
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv");
		// Creating FirefoxOptions to set profile
		firefoxOptions.setProfile(profile);  

		driver.set(new FirefoxDriver(firefoxOptions));
	}

	public void setIECapabilities() {
		InternetExplorerOptions ieOptions = new InternetExplorerOptions();
		ieOptions.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
		ieOptions.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING,true);
		ieOptions.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		ieOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		ieOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		ieOptions.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
		ieOptions.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR,UnexpectedAlertBehaviour.DISMISS);
		ieOptions.setCapability("browserstack.ie.enablePopups", "true"); 
		try {
			Runtime.getRuntime().exec(POPUP_ENABLE_COMMAND);
		} catch (Exception e) {
			log.info("Error ocured!");
		}
		driver.set(new InternetExplorerDriver(ieOptions));
	}

	// Call this method to set the driver using values set via testng.xml in case of grid configuration and open the browser
	public void setDriverWithGrid(DriverOptions driverOptions) throws FrameworkException, IOException, Exception {
		log.info(" : setDriverWithGrid Method Called");

		System.setProperty("webdriver.chrome.driver",Config.ChromeDriverPathWindows);
		System.setProperty("webdriver.ie.driver",Config.IEDriverPath);
		System.setProperty("webdriver.gecko.driver",Config.FirefoxDriverPath);

		try {
			org.openqa.selenium.Platform platform;
			DesiredCapabilities capability = null;
			platform = DriverFactory.setPlatform(driverOptions.platformType);				
			capability = DriverFactory.setBrowser(driverOptions.browser);
			platformType = driverOptions.platformType;

			switch (driverOptions.browser.toLowerCase()) {
			case CHROME:
				DriverFactory.setChromeToDownloadFileAtSpecifiedPath(capability);
				break;
			case FIREFOX:
				capability = DesiredCapabilities.firefox();
				capability.setCapability("marionatte", false);
				log.info("Setting up firefox browser");
				break;
			case INTERNET_EXPLORER:
				setIECapabilities();
				break;
			case SAFARI:
				capability.setCapability("version", "10");
				log.info("Setting up Safari browser");
				break;
			default :
				throw new FrameworkException("Invalid browser option chosen for excution on grid : "+ driverOptions.browser);
			}
			if(null != capability){
				capability.setPlatform(platform);
			}else{
				throw new FrameworkException("Unable to set capabilities for BROWSER: "+ driverOptions.browser);
			}
			driver.set(new RemoteWebDriver(new URL("http://"+ driverOptions.webdriverHost + ":" + driverOptions.webdriverPort + "/wd/hub"),capability));
			WebDriver driver = DriverFactory.getInstance().getDriver();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(driverOptions.url);
			log.info(" : Application Opened");}
		catch (UnreachableBrowserException ex) {
			throw new FrameworkException("Browser not reachable, either HUB/NODE not started or webdriver port is wrong."+ex.getMessage());				
		}catch(MalformedURLException ex){
			throw new FrameworkException("Issue in setting newly created driver - Malformed URL: "+ driverOptions.url);
		}catch(WebDriverException ex){
			throw new FrameworkException("WebDriver exception occured. Unable to start driver due to exception:\n"+ex.getMessage());
		}catch(FrameworkException ex){
			throw ex;
		}catch(Exception ex){
			throw ex;
		}
	}

	/**
	 * Sets the desired capabilities of chrome to download any file at specified path.
	 */
	private static ChromeOptions setChromeToDownloadFileAtSpecifiedPath(DesiredCapabilities cap) {
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", Config.ExportFilePath);

		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--test-type");
		options.addArguments("--disable-extensions"); //to disable browser extension pop-up

		cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		return options;
	}

	/**
	 * Sets the desired capabilities of specified browser.
	 */
	public static DesiredCapabilities setBrowser(String browser){

		if(browser.equalsIgnoreCase("firefox")) {
			return DesiredCapabilities.firefox();
		}else if(browser.equalsIgnoreCase("chrome")) {
			return DesiredCapabilities.chrome();
		}else if(browser.equalsIgnoreCase("internet explorer")) {
			return DesiredCapabilities.internetExplorer();					
		}else if(browser.equalsIgnoreCase("android")) {
			log.info("[Debug] Android.....");
			return DesiredCapabilities.android();
		}else if(browser.equalsIgnoreCase("ipad")) {
			return DesiredCapabilities.ipad();
		}else if(browser.equalsIgnoreCase("iphone")) {
			return DesiredCapabilities.iphone();
		}else if(browser.equalsIgnoreCase("safari")) {
			return DesiredCapabilities.safari();
		}else if(browser.equalsIgnoreCase("htmlunit")) {
			return DesiredCapabilities.htmlUnit();
		}
		return null;
	}

	/**
	 * Sets the platform on which user wants to run the test cases.
	 */
	public static Platform setPlatform(String platform){

		if(platform.equalsIgnoreCase("LINUX")){
			return Platform.LINUX;
		}else if(platform.equalsIgnoreCase("WINDOWS")) {
			return Platform.WINDOWS;
		}else if(platform.equalsIgnoreCase("MAC")){
			return Platform.MAC;
		}else if(platform.equalsIgnoreCase("ANDROID")){
			return Platform.ANDROID;
		}else if(platform.equalsIgnoreCase("WIN8")){
			return Platform.WIN8;
		}else if(platform.equalsIgnoreCase("XP")){
			return Platform.XP;
		}else if(platform.equalsIgnoreCase("VISTA")){
			return Platform.VISTA;
		}else if(platform.equalsIgnoreCase("UNIX")){
			return Platform.UNIX;
		}
		return Platform.ANY;
	}

	/**
	 * Gets the driver.
	 *
	 * @return the driver
	 */
	public WebDriver getDriver() // call this method to get the driver object and launch the browser
	{
		if (platformType.equalsIgnoreCase("mobile"))
			return adriver;
		else
			return getInstance().driver.get();
	}

	/**
	 * Removes the driver.
	 */
	public void removeDriver() // Quits the driver and closes the browser
	{
		getDriver().quit();
		getInstance().driver.remove();
	}
	


	/**
	 * Sets the driver for mobile.
	 *
	 * @param abc the abc
	 * @param platform the platform
	 */
	public void setDriverForMobile(AppiumDriverLocalService abc, String platform) {
		platformType = platform;
		log.info(" setDriverForMobile:  Method Called");
		WebDriverEventListners handler = new WebDriverEventListners();
		if(driver==null || adriver == null)
		{	
			cap = new DesiredCapabilities();
			switch(Config.ApplicationType)
			{
			case "AndroidNativeApp":
			{
				File appPathValueonLocalsystem=new File(Config.APKPath);
				cap.setCapability("deviceName", Config.androidDeviceName);
				if(Config.APKfreshInstall.equalsIgnoreCase("true"))
					cap.setCapability(MobileCapabilityType.FULL_RESET, true);
				else
					cap.setCapability(MobileCapabilityType.FULL_RESET, false);
				cap.setCapability("platform", "LINUX");
				cap.setCapability("platformVersion", Config.androidPlatformVersion);
				cap.setCapability("platformName", "Android"); 
				cap.setCapability("newCommandTimeout", 60 * 5);

				//Added by Raghav Bajoria for handling location pop-up after app launch for first time
				cap.setCapability("autoGrantPermissions", true);

				if(Config.APKfreshInstall.equalsIgnoreCase("false"))
				{
					cap.setCapability("app", appPathValueonLocalsystem.getAbsolutePath());		
				}
				else
				{
					cap.setCapability("appPackage",Config.appPackage );
					if(Config.appActivity.trim().length() > 0)
					{
						cap.setCapability("appActivity", Config.appActivity);
					}

				}

				adriver=new AndroidDriver<WebElement>(abc, cap);

				break;
			}
			case "AndroidWebApp":
			{

				// Name of mobile web browser to automate. Safari for iOS and Chrome
				// or Browser for Android
				cap.setCapability("browsername", "Browser");

				// The kind of mobile device or emulator to use - iPad Simulator, iPhone
				// Retina 4-inch, Android Emulator, Galaxy S4 etc
				cap.setCapability("deviceName", Config.androidDeviceName);

				// Which mobile OS platform to use - iOS, Android, or FirefoxOS
				cap.setCapability("platformName", "Android");

				// Java package of the Android app you want to run- Ex:
				// com.example.android.myApp

				cap.setCapability("appPackage", "com.android.chrome");

				// Activity name for the Android activity you want to launch from your
				// package
				cap.setCapability("appActivity", "com.google.android.apps.chrome.Main");

				adriver=new AndroidDriver<WebElement>(abc, cap);

				eDriver = new EventFiringWebDriver(adriver);
				driver.set(eDriver);
				eDriver.register(handler);
				eDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				eDriver.get(Config.ApplicationURL);
				break;
			}
			case "iOSWebApp":
			{

				try
				{
					//Initialize the capabilities object 
					//DesiredCapabilities capabilities = new DesiredCapabilities();	
					//Set the appium version in capabilities
					cap.setCapability("platformName", "iOS");
					//Set the appium version in capabilities
					cap.setCapability("platformVersion", Config.iOSPlatformVersion);
					//Set the appium version in capabilities
					cap.setCapability("deviceName", Config.iOSDeviceName);	
					cap.setCapability("platform", "Mac");
					//Set the browser name
					cap.setCapability(CapabilityType.BROWSER_NAME, Config.iOSBrowserName);	
					macip = "http://" + Config.macIP + ":4723/wd/hub";
					AppiumDriver<IOSElement> driver = new IOSDriver<IOSElement>(new URL(macip), cap);
					eDriver = new EventFiringWebDriver(driver);
					eDriver.register(handler);
					eDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					eDriver.get(Config.ApplicationURL);
				}
				catch (Exception ex)
				{
					System.out.println("set up exception is ...." ); ex.printStackTrace();

				}
				catch (Error e)
				{
					System.out.println("set up error is ...." ); e.printStackTrace();

				}		
				break;
			}
			case "iOSNativeApp":
			{

				try
				{
					//Initialize the capabilities object 
					//DesiredCapabilities capabilities = new DesiredCapabilities();	
					//Set the appium version in capabilities
					cap.setCapability("platformName", "iOS");
					//Set the appium version in capabilities
					cap.setCapability("platformVersion", Config.iOSPlatformVersion);
					//Set the appium version in capabilities
					cap.setCapability("deviceName", Config.iOSDeviceName);	
					cap.setCapability("platform", "Mac");
					//Set the browser name
					//cap.setCapability("app","settings");	
					cap.setCapability(CapabilityType.BROWSER_NAME, Config.iOSBrowserName);	
					cap.setCapability("app", "/Users/nagarro/Desktop/P51.ipa");		
					//Launch the appium driver with the above required capabilities---
					macip = "http://" + Config.macIP + ":4723/wd/hub";
					AppiumDriver<IOSElement> driver = new IOSDriver<IOSElement>(new URL(macip), cap);
					eDriver = new EventFiringWebDriver(driver);
					eDriver.register(handler);
					eDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					eDriver.get(Config.ApplicationURL);
				}
				catch (Exception ex)
				{
					System.out.println("set up exception is ...." ); ex.printStackTrace();

				}
				catch (Error e)
				{
					System.out.println("set up error is ...." ); e.printStackTrace();

				}		
				break;
			}

			}

		}

	}

}

