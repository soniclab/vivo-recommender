package edu.northwestern.sonic.integration.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Holder for URL of web application under test
 * default to IE and the live server on ciknow1
 * web driver with implicit waiting
 * 
 * @author Hugh
 *
 */
public abstract class IntegrationTest {
	private static URL defaultWebAppUrl = null;
	public static enum WEBDRIVER { 
		INTERNETEXPLORER,
		CHROME
	}
	private static WEBDRIVER defaultWebDriver = WEBDRIVER.INTERNETEXPLORER;
	private URL webAppUrl = null;
	private WebDriver webDriver = null;
	
	static {
		try {
			defaultWebAppUrl = new URL("http://ciknow1.northwestern.edu/vivorecommender/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * constructor
	 * @param webAppUrl
	 * @param webDriver
	 */
	public IntegrationTest(URL webAppUrl, WebDriver webDriver) {
		setWebAppUrl(webAppUrl);
		setWebDriver(webDriver);
	}

	/**
	 * constructor
	 * @param webDriver
	 */
	public IntegrationTest(WebDriver webDriver) {
		this(defaultWebAppUrl, webDriver);
	}

	/**
	 * constructor
	 * @param webAppUrl
	 */
	public IntegrationTest(URL webAppUrl) {
		this(webAppUrl, webDriverFactory());
	}

	/**
	 * constructor
	 */
	public IntegrationTest() {
		this(defaultWebAppUrl);
	}
	
	private static WebDriver webDriverFactory(WEBDRIVER webDriver) {
		switch (webDriver) {
			case INTERNETEXPLORER:
				return new InternetExplorerDriver();
			case CHROME:
				return new ChromeDriver();
			default:
				return null;
		}
	}

	private static WebDriver webDriverFactory() {
		return webDriverFactory(defaultWebDriver);
	}

	/**
	 * Set the base application URL
	 * @param webAppUrl as a String
	 * @throws MalformedURLException
	 */
	private void setWebAppUrl(URL url) {
		webAppUrl = url; 
	}

	/**
	 * Set the default base application URL
	 * @param webAppUrl as a String
	 * @throws MalformedURLException
	 */
	public static void setDefaultWebAppUrl(URL url) {
		IntegrationTest.defaultWebAppUrl = url; 
	}

	/**
	 * @return the application webAppUrl
	 */
	protected String getWebAppUrl() {
		return webAppUrl.toString();
	}

    /**
	 * @param webDriver the webDriver to set
	 */
	private void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
		this.webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

    /**
	 * @param webDriver the webDriver to set
	 */
	public static void setDefaultWebDriver(WEBDRIVER webDriver) {
		IntegrationTest.defaultWebDriver = webDriver; 
	}

	/**
	 * @return the webDriver
	 */
	protected WebDriver getWebDriver() {
		return webDriver;
	}

	@After
	public void after() {
		getWebDriver().close();
	}

}
