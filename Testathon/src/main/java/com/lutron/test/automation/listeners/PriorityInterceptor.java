package com.lutron.test.automation.listeners;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

/**
 * This class implements IMethodInterceptor interface.
 */
public class PriorityInterceptor implements IMethodInterceptor {
	
	
  	 /** A method of IMethodInterceptor interface which is used to alter/modify the list of test methods to be executed by TestNG.
  	  * TestNG runs the test methods in the same order found in the returned value.
  	  * 
  	  * @param methods		of type List<IMethodInstance>	
	  * @param context		of type ITestContext
	  * @return, list of IMethodInstance which TestNG runs.
	  */
	  public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
	    Comparator<IMethodInstance> comparator = new Comparator<IMethodInstance>() {
	    	
	      private int getPriority(IMethodInstance mi) {
	      int result = 0;
	      Method method = mi.getMethod().getMethod();
	      Priority priority = method.getAnnotation(Priority.class);
	      if (priority != null) {
	        result = priority.value();
	      } else {
	        Class cls = method.getDeclaringClass();
	        Priority classPriority = (Priority) cls.getAnnotation(Priority.class);
	        if (classPriority != null) {
	          result = classPriority.value();
	        }
	      }
	      return result;
	    }
	 
	    /*
	   	 * A method of comparator<T> interface.
	     */
	    public int compare(IMethodInstance m1, IMethodInstance m2) {
	      return getPriority(m1) - getPriority(m2);
	    }
	  };
	 
	  IMethodInstance[] array = methods.toArray(new IMethodInstance[methods.size()]);
	  Arrays.sort(array, comparator);
	  return Arrays.asList(array);
	}	  
}
	  
	  

