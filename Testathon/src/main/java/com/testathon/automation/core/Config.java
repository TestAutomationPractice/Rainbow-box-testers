package com.testathon.automation.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * This class reads the "app.properties" file.
 */
public class Config {
	
	private static Logger log = Logger.getLogger(Config.class);
	
	private static String relativeChromeDriverWindowsPath="/HubNodeConfig/drivers/chromedriver.exe";
	private static String relativeChromeDriverPathLinuxUnix="/HubNodeConfig/drivers/chromedriver";
	private static String relativeFirefoxDriverPath="/HubNodeConfig/drivers/geckodriver.exe";
	private static String relativeIEDriverPath="/HubNodeConfig/drivers/IEDriverServer.exe";
	private static String relativeEdgeDriverPath="/HubNodeConfig/drivers/MicrosoftWebDriver.exe";
    private static String relativelog4jPath="/HubNodeConfig/log4j.properties";
			
	/** The Chrome driver exe file path for Windows. */
	public static String ChromeDriverPathWindows;
	
	/** The Chrome driver exe file path for Linux and Unix. */
	public static String ChromeDriverPathLinuxUnix;
	
	/** The Firefox driver exe file path. */
	public static String FirefoxDriverPath;
	
	/** The Edge driver exe file path. */
	public static String EdgeDriverPath;
	
	/** The IE driver exe file path. */
	public static String IEDriverPath;
	
	public static String log4jPath;

	/** The path where extent report file will be generated. */
	public static String ExtentReportsPath;
	
	/** The path where test reports will be generated. */
	public static String TestReportFolder;
		
	/** The extent report file title. */
	public static String ReportTitle;
	
	/** The heading of extent report. */
	public static String ReportName;
	
	/** The path where screen shots will be generated. */
	public static String ScreenShotsPath;
	
	/** The URL of the application under test. */
	public static String ApplicationURL;
	
	/** The name of the browser (firefox, chrome, ie, safari) used to open the application URL. */
	public static String Browser;
	
	/** The application environment (like RC, QA, STAGING, DEV) */
	public static String Environment;
	
	/** The DB connection string used to connect to database */
	public static String DBConnectionString;
	
	/** The Max retry count on test failure. */
	public static String MaxRetryCountOnTestFailure;
	
	/** The path of CSV file in which test data is present. */
	public static String dataInputFile;
	
	/** The path of file in which element locators are present. */
	public static String locatorsFile;
	
	/** The path where test recording will be generated. */
	public static String TestVideoPath;
	
	/** If 'true', test will be recorded. */
	public static String SetVideoRecording;
	
	/** Defines the maximum size of recorded video. */
	public static String MaxSizeVideoFilesGB;
	
	/** The ofile input stream. */
	public static FileInputStream ofileInputStream;
	
	/** The path where test report archive file be generated. */
	public static String ZipPath;
	
	/** The e-mail id of the person sending the test report. */
	public static String mailFrom;
	
	/** The e-mail id of the person you want to send the test report. */
	public static String mailTo;
	
	/** The username of the account where mail is sent. */
	public static String mailUserName;
	
	/** The password of the account where mail is sent. */
	public static String mailPassword;
	
	/** The mail host. */
	public static String mailHost;
	
	/** If 'true' test report will be mailed. */
	public static String setReportEmail;
	
	/** The auto IT script file path. */
	public static String autoITPath;
	
	/** The path of test data file. */
	public static String testData;
	
	/** If 'true' screenshot will be taken when test case is failed. */
	public static String screenshotOnFailure;
	
	/** If 'true' screenshot will be taken when test case is skipped. */
	public static String screenshotOnSkip;
	
	/** If 'true' screenshot will be taken when test case is passed. */
	public static String screenshotOnPass;
	
	/**The Hub IP of the Application under test. */
	public static String HubIP;

	public static String runOnGrid;
	
	public static String ExportFilePath;
	
	public static String UploadFilePath;
	
	
	/** To Specify if we want to run our browser headless or not. */
	public static String Headless;
	
	/**Path for Firmware need to test against current one*/
	public static String FirmwarePath;
	
	/**The Ethernet URL of the Application under test. */
	public static String EthernetURL = null;
	
	/** Used for Appium. */
	public static String APKPath;
	public static String APKfreshInstall;
	public static String Appium_Node_Path;
	public static String Appium_JS_Path,path;
	public static String AppiumServer_url;
	public static String androidDeviceName;
	public static String appPackage;
	public static String appActivity;
	public static String androidPlatformVersion;
	public static String TimeoutValueInSeconds;
	public static String ApplicationType;
	public static String MobileBrowserType;
	public static String deviceType;
	public static String iOSappType;
	public static String iOSPlatformVersion;
	public static String iOSDeviceName;
	public static String iOSBrowserName;
	public static String userType;
	public static String macIP;
	public static String macUserName;
	public static String macPwd;
	public static String gspecFilePath;
	public static String SikuliImagePath;

	/** Used for Coproc. */
	public static String keyFile;
	public static String pLinkFile;
	public static String WifiName;
	public static Boolean ByCoProc;
	public static String schUserName;
	public static String schPassword;

	/** For API testing. */
	public static String inputFileAPITestData;
	public static String ClientId;
	public static String ClientSecret;
	public static String RedirectUri;
	public static String Username;
	public static String Password;
	public static String UMUri; 
	
	/** Desktop application path. */
	public static String DesktopApp;
		
	/**
	 * Sole constructor. (For invocation by subclass 
	 * constructors, typically implicit.)
	 */
	public Config() {
	
	}

	/**
	 * Reads the app.properties file and store the values in above defined fields.
	 */
	public static void initConstants(String configPath, String targetPathClient) {
		
		String pathAppConfigClient = System.getProperty("user.dir")+ configPath;
		Properties prop = new Properties();
		
		ChromeDriverPathWindows = System.getProperty("user.dir") + relativeChromeDriverWindowsPath;
		ChromeDriverPathLinuxUnix = System.getProperty("user.dir") + relativeChromeDriverPathLinuxUnix;
		FirefoxDriverPath = System.getProperty("user.dir") + relativeFirefoxDriverPath;
		IEDriverPath = System.getProperty("user.dir") + relativeIEDriverPath;
		EdgeDriverPath = System.getProperty("user.dir") + relativeEdgeDriverPath;
		log4jPath = System.getProperty("user.dir") + relativelog4jPath;
		
		try {
			ofileInputStream = new FileInputStream(pathAppConfigClient);
			if (ofileInputStream != null) {
				prop.load(ofileInputStream);
				dataInputFile = System.getProperty("user.dir") + prop.getProperty("CSVFile");
				locatorsFile = System.getProperty("user.dir") + prop.getProperty("ORXmlFile");
				Browser = prop.getProperty("browser");
				ExtentReportsPath = System.getProperty("user.dir") + prop.getProperty("ExtentReportsPath");
				ReportTitle = prop.getProperty("ReportTitle");
				ReportName = prop.getProperty("ReportName");
				MaxRetryCountOnTestFailure = prop.getProperty("MaxRetryCountOnTestFailure");
				Headless = prop.getProperty("Headless");
				TestReportFolder = System.getProperty("user.dir") + prop.getProperty("TestReports");
				ScreenShotsPath = System.getProperty("user.dir") + prop.getProperty("ScreenShotsPath");
				Environment = prop.getProperty("Environment");
				TestVideoPath = System.getProperty("user.dir") + prop.getProperty("TestVideoPath");
				SetVideoRecording = prop.getProperty("SetVideoRecording");
				MaxSizeVideoFilesGB = prop.getProperty("MaxSizeVideoFilesGB");
				ZipPath = System.getProperty("user.dir") + prop.getProperty("ZipPath");
				mailFrom = prop.getProperty("mailFrom");
				mailTo = prop.getProperty("mailTo");
				mailUserName = prop.getProperty("mailUserName");
				mailPassword = prop.getProperty("mailPassword");
				mailHost = prop.getProperty("mailHost");
				setReportEmail = prop.getProperty("SetReportEmail");
				autoITPath = System.getProperty("user.dir") + prop.getProperty("AutoITScriptPath");
				testData = System.getProperty("user.dir") + prop.getProperty("TestDataPath");
				screenshotOnFailure = prop.getProperty("ScreenshotOnFailure");
				screenshotOnSkip = prop.getProperty("ScreenshotOnSkip");
				screenshotOnPass = prop.getProperty("ScreenshotOnPass");
				ExportFilePath = System.getProperty("user.dir") + prop.getProperty("ExportFilePath");
				UploadFilePath = System.getProperty("user.dir") + prop.getProperty("UploadFilePath");
				runOnGrid = prop.getProperty("RunOnGrid");
				
				ApplicationURL = prop.getProperty(GenerateAppSettingKey("URL_", Environment));
				DBConnectionString = prop.getProperty(GenerateAppSettingKey("DBServer", Environment));
				
				// Mobile related properties
				APKPath = prop.getProperty("APKPath");
				APKfreshInstall = prop.getProperty("APKFreshInstall");
				Appium_Node_Path = prop.getProperty("Appium_Node_Path");
				Appium_JS_Path = prop.getProperty("Appium_JS_Path");
				ApplicationType = prop.getProperty("ApplicationType");
				MobileBrowserType = prop.getProperty("MobileBrowserType");
				
				AppiumServer_url = prop.getProperty("AppiumServer_url");
				androidDeviceName = prop.getProperty("androidDeviceName");
				appPackage = prop.getProperty("appPackage");
				appActivity = prop.getProperty("appActivity");
				androidPlatformVersion = prop.getProperty("androidPlatformVersion");
				TimeoutValueInSeconds = prop.getProperty("TimeoutValueInSeconds");
				
				deviceType = prop.getProperty("deviceType");
				iOSappType = prop.getProperty("iOS_appType");
				iOSPlatformVersion = prop.getProperty("iOSPlatformVersion");
				iOSDeviceName = prop.getProperty("iOS_Device_Name");
				iOSBrowserName = prop.getProperty("iOS_BrowserName");
				macIP = prop.getProperty("MACIP");
				macUserName = prop.getProperty("MACUserName");
				macPwd = prop.getProperty("MACPwd");
				
				//For co proc
				WifiName = prop.getProperty("wifiName");
				ByCoProc = Boolean.parseBoolean(prop.getProperty("ByCoProc"));
				HubIP = prop.getProperty("HubIP");
				schUserName = prop.getProperty("schUserName");
				schPassword = prop.getProperty("schPassword");
				
				//for API Testing
				inputFileAPITestData = System.getProperty("user.dir") + prop.getProperty("APITestData");
				ClientId = prop.getProperty("ClientId");
				ClientSecret = prop.getProperty("ClientSecret");
				RedirectUri = prop.getProperty("RedirectUri");
				Username = prop.getProperty("Username");
				Password = prop.getProperty("Password");
				UMUri = prop.getProperty("UMUri"); 
				
				//Desktop Application Path
				DesktopApp = prop.getProperty("DesktopApp");
				
				//Getting Firmware Path
				FirmwarePath=System.getProperty("user.dir") + prop.getProperty("FirmwarePath");
				
				//Getting Ethernet Path to test
				EthernetURL = "https://" + HubIP;
				
				/**/
				
				if (Environment.equalsIgnoreCase("Wifi")) {
					ApplicationURL = prop.getProperty("URL_QA");
					DBConnectionString = prop.getProperty("DBServerQA");
				}
				else if(Environment.equalsIgnoreCase("LAN")) {
					ApplicationURL = prop.getProperty("EthernetURL");
				}
				else if(ApplicationURL == null  || DBConnectionString == null ) {
					log.info("Environment " + Environment + " is not supported by test suite.");	
				}
				
			}

		} catch (IOException e) {
			System.err.println("Cannot find the app.properties file at " + pathAppConfigClient);
		} finally {
			if (ofileInputStream != null) {
				try {
					ofileInputStream.close();
				} catch (IOException e) {
					System.err.println("Cannot close the app.properties file instance.");
				}
			}
		}

	}
	
	/**
	 * Generates the application settings key by using the prefix and environment key
	 * If Prefix is "URL" and environment is "QA", key generated would be "URL_QA"
	 */
	public static String GenerateAppSettingKey(String prefix, String environmentName) {
		StringBuilder stringBuilder = new StringBuilder(prefix);
		stringBuilder.append(environmentName);
		
		return stringBuilder.toString();
	}

	

}