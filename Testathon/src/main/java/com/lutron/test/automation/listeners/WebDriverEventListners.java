package com.lutron.test.automation.listeners;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

/**
 * The Class WebDriverEventListners.
 *
 */
public class WebDriverEventListners implements WebDriverEventListener {

	static Logger log;

	/**
	 * Instantiates a new web driver event listeners.
	 */
	public WebDriverEventListners() {
		log = Logger.getLogger(WebDriverEventListners.class);
	}

	/**
	 * After change value of.
	 *
	 * @param arg0 		arg 0
	 * @param arg1 		arg 1
	 * @param arg2 		arg 2
	 */
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
		log.debug(": afterChangeValueOf :: Webelement :" + arg0);
	}

	/**
	 * After click on.
	 *
	 * @param arg0 		arg 0
	 * @param arg1 		arg 1
	 */
	public void afterClickOn(WebElement arg0, WebDriver arg1) {
		log.debug(": afterClickOn :: After clicking on the element :" + arg0);
	}

	/**
	 * After find by.
	 *
	 * @param arg0 		arg 0
	 * @param arg1 		arg 1
	 * @param arg2 		arg 2
	 */
	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		log.debug(": afterFindBy :: After finding the element : " + arg0);
	}

	/**
	 * After navigate back.
	 *
	 * @param arg0 		arg 0
	 */
	public void afterNavigateBack(WebDriver arg0) {
		log.debug(": afterNavigateBack :: After navigating back");
	}

	/**
	 * After navigate forward.
	 *
	 * @param arg0 		arg 0
	 */
	public void afterNavigateForward(WebDriver arg0) {
		log.debug(": afterNavigateForward :: After navigating forward");
	}

	/**
	 * After navigate refresh.
	 *
	 * @param arg0 		arg 0
	 */
	public void afterNavigateRefresh(WebDriver arg0) {
		log.debug(": afterNavigateRefresh :: After navigating refresh");
	}

	/**
	 * After navigate to.
	 *
	 * @param arg0 		arg 0
	 * @param arg1 		arg 1
	 */
	public void afterNavigateTo(String arg0, WebDriver arg1) {
		log.debug(": afterNavigateTo :: " + arg0);
	}

	/**
	 * After script.
	 *
	 * @param arg0 		arg 0
	 * @param arg1 		arg 1
	 */
	public void afterScript(String arg0, WebDriver arg1) {
		log.debug(": afterScript ::");
	}
	
	/**
	 * After switch to window.
	 *
	 * @param arg0 		arg 0
	 * @param arg1 		arg 1
	 */
	public void afterSwitchToWindow(String arg0, WebDriver arg1) {
		log.debug(": afterSwitchToWindow ::");
	}

	/**
	 * Before change value of.
	 *
	 * @param arg0 		arg 0
	 * @param arg1 		arg 1
	 * @param arg2 		arg 2
	 */
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
		log.debug(": beforeChangeValueOf :: Before changing value of " + arg0);
	}

	/**
	 * Before click on.
	 *
	 * @param arg0 		arg 0
	 * @param arg1 		arg 1
	 */
	public void beforeClickOn(WebElement arg0, WebDriver arg1) {
		log.debug(": beforeClickOn :: " + arg0);
	}

	/**
	 * Before find by.
	 *
	 * @param arg0 		arg 0
	 * @param arg1 		arg 1
	 * @param arg2 		arg 2
	 */
	public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		log.debug(": beforeFindBy :: element : " + arg0);
	}

	/**
	 * Before navigate back.
	 *
	 * @param arg0 		arg 0
	 */
	public void beforeNavigateBack(WebDriver arg0) {
		log.debug(": beforeNavigateBack ::");
	}

	/**
	 * Before navigate forward.
	 *
	 * @param arg0 		arg 0
	 */
	public void beforeNavigateForward(WebDriver arg0) {
		log.debug(": beforeNavigateForward ::");
	}

	/**
	 * Before navigate refresh.
	 *
	 * @param arg0 		arg 0
	 */
	public void beforeNavigateRefresh(WebDriver arg0) {
		log.debug(": beforeNavigateRefresh ::");
	}

	/**
	 * Before navigate to.
	 *
	 * @param arg0 		arg 0
	 * @param arg1 		arg 1
	 */
	public void beforeNavigateTo(String arg0, WebDriver arg1) {
		log.debug(": beforeNavigateTo ::" + arg0);
	}

	/**
	 * Before script.
	 *
	 * @param arg0 		arg 0
	 * @param arg1 		arg 1
	 */
	public void beforeScript(String arg0, WebDriver arg1) {
		log.debug(": beforeScript ::");
	}
	
	/**
	 * Before switch to window.
	 *
	 * @param arg0 		arg 0
	 * @param arg1 		arg 1
	 */
	public void beforeSwitchToWindow(String arg0, WebDriver arg1) {
		log.debug(": beforeSwitchToWindow ::");
	}

	/**
	 * On exception.
	 *
	 * @param arg0 		arg 0
	 * @param arg1 		arg 1
	 */
	public void onException(Throwable arg0, WebDriver arg1) {
		log.info(": onException :: " + arg0.getMessage());
	}

	/**
	 * After alert accept.
	 *
	 * @param arg0 		arg 0
	 */
	@Override
	public void afterAlertAccept(WebDriver arg0) {

	}

	/**
	 * After alert dismiss.
	 *
	 * @param arg0 		arg 0
	 */
	@Override
	public void afterAlertDismiss(WebDriver arg0) {

	}

	/**
	 * Before alert accept.
	 *
	 * @param arg0 the arg 0
	 */
	@Override
	public void beforeAlertAccept(WebDriver arg0) {

	}

	/**
	 * Before alert dismiss.
	 *
	 * @param arg0 		arg 0
	 */
	@Override
	public void beforeAlertDismiss(WebDriver arg0) {

	}
}
