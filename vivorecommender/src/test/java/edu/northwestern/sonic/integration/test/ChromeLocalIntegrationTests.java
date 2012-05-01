package edu.northwestern.sonic.integration.test;

import org.junit.BeforeClass;

/**
 * local Chrome test suite
 * run with path to chrome driver
 * -Dwebdriver.chrome.driver="C:\Program Files (x86)\Google\Chrome\Application\chromedriver.exe"
 * 
 * @author Hugh
 * 
 */

public class ChromeLocalIntegrationTests extends LocalIntegrationTests {
	
	@BeforeClass
	public static void beforeClass() {
		IntegrationTest.setDefaultWebDriver(IntegrationTest.WEBDRIVER.CHROME);
		IntegrationTest.setDefaultWebAppUrl(LocalWebApp.getUrl());
	}
	
}