package edu.northwestern.sonic.integration.test;

import org.junit.BeforeClass;

/**
 * Chrome test suite
 * run with jvm parameter path to chrome driver, for example
 * -Dwebdriver.chrome.driver="C:\Program Files (x86)\Google\Chrome\Application\chromedriver.exe"
 * 
 * @author Hugh
 * 
 */
	
public class ChromeIntegrationTests extends IntegrationTests {
	
	@BeforeClass
	public static void beforeClass() {
		IntegrationTest.setDefaultWebDriver(IntegrationTest.WEBDRIVER.CHROME);
	}
	
}