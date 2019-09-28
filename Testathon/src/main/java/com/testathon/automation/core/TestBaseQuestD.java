package com.testathon.automation.core;

import com.testathon.automation.core.TestBase;

/**
 * This is the base class in which the initialization required to run the test cases occurs. 
 */
public class TestBaseQuestD extends  TestBase {
	protected static String configPath = "/Config/app.properties";
	private static String targetPathClient = "";
	
	public TestBaseQuestD() {
		super(configPath, targetPathClient);
		ConfigQuestD.initConstants(configPath);
	}
}
