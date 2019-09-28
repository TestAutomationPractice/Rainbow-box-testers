package com.testathon.automation.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.testathon.automation.core.Config;

/**
 * This class will contain all the initialization and declaration of variables that are applicable for Questd.
 */
public class ConfigQuestD {
	private static Logger log = Logger.getLogger(ConfigQuestD.class);
	
	/** The ofile input stream. */
	public static FileInputStream ofileInputStream;
	public static void initConstants(String configPath) {

		String path = System.getProperty("user.dir") + configPath;
		Properties prop = new Properties();

		try {
			ofileInputStream = new FileInputStream(path);
			if (ofileInputStream != null) {
				prop.load(ofileInputStream);
				

				if(Config.ApplicationURL == null  || Config.DBConnectionString == null ) {
					log.info("Environment " + Config.Environment + " is not supported by test suite.");	
				}
			}

		} catch (IOException e) {
			System.err.println("Cannot find the app.properties file at " + path);
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

}
