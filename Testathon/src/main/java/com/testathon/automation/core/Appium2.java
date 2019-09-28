package com.testathon.automation.core;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class Appium2 {

	private AppiumDriverLocalService service;
	private AppiumServiceBuilder builder;
	private DesiredCapabilities cap;
	public static Logger log = Logger.getLogger(Appium2.class);
	
	public AppiumDriverLocalService startServer() {
		//Set Capabilities
		cap = new DesiredCapabilities();
		cap.setCapability("noReset", "false");
		
		//Build the Appium service
		builder = new AppiumServiceBuilder();
		builder.withIPAddress("127.0.0.1");
		builder.usingPort(4723);
		builder.withCapabilities(cap);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
		
		//Start the server with the builder
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
		log.info("Appium Server Started");
		return service;
	}
	
	public void stopServer() {
		service.stop();
	}

	  public void stopAppiumServer()
	    {
	    	System.out.println("Stopping Appium Server......");
	    	if(service.isRunning()==true)
	    	{
			     service.stop();
			     System.out.println("Appium Server Stopped......");
	    	}
	    	else
			{
			    System.out.println("Appium Serveris already Stopped......");
			}
	    }
	
	  
	  public boolean AppiumServerStatus()
		 {
			 return service.isRunning();
		 }
	  
	  
	public boolean checkIfServerIsRunnning(int port) {
		
		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			//If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}	


}
