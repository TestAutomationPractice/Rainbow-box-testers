package com.testathon.automation.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.testathon.automation.core.Config;
import com.testathon.automation.logging.LoggingManager;

/**
 * This class contains methods that re-execute the entire test case by specified number of times.
 *
 */
public class RetryUtil implements IRetryAnalyzer{
	
	private int retryCount = 0;
	private int maxRetryCount;

	/**
	 * Instantiates a new retry.
	 */
	public RetryUtil() {
	}

	/**
	 * Below method returns 'true' if the test method has to be retried else
	 * 'false' and it takes the 'Result' as parameter of the test method that
	 * just ran.
	 *
	 * @param result 	ItestResult.
	 * @return true, 	if successful
	 */
	public boolean retry(ITestResult result) {
		boolean status = false;
		maxRetryCount = Integer.parseInt(Config.MaxRetryCountOnTestFailure);
		if (retryCount < maxRetryCount) {
			LoggingManager.getConsoleLogger().info(" :Retrying test method -- " + result.getName() + " -- with status " + getResultStatusName(result.getStatus()) + " for the " + (retryCount + 1) + " time(s).");
			LoggingManager.getReportLogger().log(Status.INFO, "\"" + "Retry count: " +(retryCount + 1) + ", Test method: " +result.getName() + ", Status: " + getResultStatusName(result.getStatus()) + "\"");
			retryCount++;
			status = true;
		}
		return status;
	}

	/**
	 * Gets the result status name.
	 *
	 * @param status 	status of test result.
	 * @return String	result status name.
	 */
	public String getResultStatusName(int status) {
		String resultName = null;
		if (status == 1)
			resultName = "SUCCESS";
		if (status == 2)
			resultName = "FAILURE";
		if (status == 3)
			resultName = "SKIP";
		return resultName;
	}
}
