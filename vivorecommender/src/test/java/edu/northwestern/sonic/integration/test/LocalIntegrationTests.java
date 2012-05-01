package edu.northwestern.sonic.integration.test;

import org.junit.BeforeClass;

/**
 * local IE test suite
 * 
 * @author Hugh
 * 
 */
	
public class LocalIntegrationTests extends IntegrationTests {
	
	@BeforeClass
	public static void beforeClass() {
		IntegrationTest.setDefaultWebAppUrl(LocalWebApp.getUrl());
	}
	
}