package edu.northwestern.sonic.integration.test;

import org.junit.BeforeClass;

import edu.northwestern.sonic.integration.test.IntegrationTest.WEBAPPURL;

/**
 * local IE test suite
 * 
 * @author Hugh
 * 
 */
	
public class DeployedIntegrationTests extends IntegrationTests {
	
	@BeforeClass
	public static void beforeClass() {
		IntegrationTest.setDefaultWebAppUrl(WEBAPPURL.DEPLOYED);
	}
	
}