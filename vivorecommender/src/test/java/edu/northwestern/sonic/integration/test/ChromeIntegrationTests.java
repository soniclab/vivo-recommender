package edu.northwestern.sonic.integration.test;

import org.junit.BeforeClass;

/**
 * Chrome test suite
 * run with path to chrome driver on system path or with parameter to jvm, for example
 * -Dwebdriver.chrome.driver="C:\Program Files (x86)\Google\Chrome\Application\chromedriver.exe"
 * 
 * @author Hugh
 * 
 */
	
public class ChromeIntegrationTests extends IntegrationTests {
	
	@BeforeClass
	public static void beforeClassChrome() {
		IntegrationTest.setDefaultWebDriver(IntegrationTest.WEBDRIVER.CHROME);
	}
	
}