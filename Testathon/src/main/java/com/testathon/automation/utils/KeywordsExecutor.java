package com.testathon.automation.utils;


import java.util.NoSuchElementException;
import java.util.function.Supplier;

import org.openqa.selenium.TimeoutException;

import com.aventstack.extentreports.Status;
import com.testathon.automation.logging.LoggingManager;

/**
 * @author atripathi@lutron.com
 * 
 * Class which executes the keywords methods
 *
 */
public class KeywordsExecutor {

	/**
	 * @param expression
	 */
	public  static void Execute(Runnable expression) {
		try {
			expression.run();
		}catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw (e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw (e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw (e);
		}
	}
	
	/**
	 * @param expression
	 * @return
	 */
	public static <T> T Execute(Supplier<T> expression) {
		try {
			return expression.get();
		}catch (NoSuchElementException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw (e);
		} catch (TimeoutException e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "TimeOut. " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"" + " not found. " + "\"");
			LoggingManager.getConsoleLogger().error("Timeout. Element not found." + e);
			throw (e);
		} catch (Exception e) {
			LoggingManager.getReportLogger().log(Status.FAIL, "\"" + "Element not found: " + "\"" + LoggingManager.getInstance().getCurrentElementPath() + "\"");
			LoggingManager.getConsoleLogger().error("No Such Element Found" + e);
			throw (e);
		}
	}
	
	
}
