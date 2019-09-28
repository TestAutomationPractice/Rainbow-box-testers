package com.lutron.test.automation.listeners;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import com.testathon.automation.utils.CommonUtil;
import com.testathon.automation.utils.RetryUtil;

/**
 * The Class TestNGListeners.
 */
public class TestNGListeners implements IAnnotationTransformer {

	protected CommonUtil util;
	static Logger log = Logger.getLogger(TestNGListeners.class);

	/**
	 * Instantiates a new test NG listeners.
	 *
	 * @author mohitmaliwal
	 */
	public TestNGListeners() {
		log.info(" : TestNGListeners Constructor called");
	}

	/**
	 * Transform.
	 *
	 * @param testannotation 	test annotation.
	 * @param testClass 		test class.
	 * @param testConstructor 	test constructor.
	 * @param testMethod 		test method.
	 */
	@Override
	public void transform(ITestAnnotation testannotation, Class testClass, Constructor testConstructor, Method testMethod) {
		IRetryAnalyzer retry = testannotation.getRetryAnalyzer();

		if (retry == null) {
			testannotation.setRetryAnalyzer(RetryUtil.class);
		}
	}

}
