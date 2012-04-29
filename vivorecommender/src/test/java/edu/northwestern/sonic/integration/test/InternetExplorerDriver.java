package edu.northwestern.sonic.integration.test;

import java.util.concurrent.TimeUnit;

/**
 * IE Driver with an implicit wait
 * 
 * @author Hugh
 *
 */
public class InternetExplorerDriver extends org.openqa.selenium.ie.InternetExplorerDriver {
	
	InternetExplorerDriver() {
		super();
		manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

}
