package com.lutron.test.automation.listeners;


import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.testathon.automation.logging.LoggingManager;

/**
 * 
 * This listener will inform the reporting manager on test results (failure,
 * success, skip) which occurred during test execution.
 * 
 */
public class TestResultLoggerTestNGListener implements ITestListener {

	/**
	 * Report the fail status in Logging Manager on test case failure.
	 * 
	 * @param result
	 *            Test Result instance
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		LoggingManager.getConsoleLogger().info("Test case FAILED");
		LoggingManager.getInstance().logTestStatus(result);
	}

	/**
	 * Report the skip status in Logging Manager on test case skipped.
	 * 
	 * @param result
	 *            Test Result instance
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
		LoggingManager.getConsoleLogger().info("Test case SKIPPED");
		LoggingManager.getInstance().logTestStatus(result);
	}

	/**
	 * Report the success status in Logging Manager on test case success.
	 * 
	 * @param result
	 *            Test Result instance
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		LoggingManager.getConsoleLogger().info("Test case PASSED");
		LoggingManager.getInstance().logTestStatus(result);
	}

	@Override
	public void onFinish(ITestContext context) {
		LoggingManager.getConsoleLogger().info("Test Suite executed -> " + context.getName());
	}

	@Override
	public void onStart(ITestContext context) {
		LoggingManager.getConsoleLogger().info("Test Suite started -> " + context.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
	}

	@Override
	public void onTestStart(ITestResult result) {
		LoggingManager.getConsoleLogger().info("Test Method started -> " + result.getName());
	}

}
