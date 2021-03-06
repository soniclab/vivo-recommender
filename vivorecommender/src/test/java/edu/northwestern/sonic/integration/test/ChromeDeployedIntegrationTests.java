package edu.northwestern.sonic.integration.test;

import org.junit.BeforeClass;

import edu.northwestern.sonic.integration.test.IntegrationTest.WEBAPPURL;

/**
 * local Chrome test suite
 * run with path to chrome driver on system path or with parameter to jvm, for example
 * -Dwebdriver.chrome.driver="C:\Program Files (x86)\Google\Chrome\Application\chromedriver.exe"
 * 
 * @author Hugh
 * 
 */

public class ChromeDeployedIntegrationTests extends ChromeIntegrationTests {
	
	@BeforeClass
	public static void beforeClassLocal() {
		IntegrationTest.setDefaultWebAppUrl(WEBAPPURL.DEPLOYED);
	}
	
}