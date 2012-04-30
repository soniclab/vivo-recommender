package edu.northwestern.sonic.integration.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Holder for URL of web application under test
 * 
 * @author Hugh
 *
 */
public abstract class IntegrationTest {
	private static URL defaultWebAppUrl = null;
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
	 * @param webAppUrl
	 * @param webDriver
	 */
	public IntegrationTest(URL webAppUrl, WebDriver webDriver) {
		setWebAppUrl(webAppUrl);
		setWebDriver(webDriver);
	}

	/**
	 * @param webDriver
	 */
	public IntegrationTest(WebDriver webDriver) {
		this(defaultWebAppUrl, webDriver);
	}

	/**
	 * @param webAppUrl
	 */
	public IntegrationTest(URL webAppUrl) {
		this(webAppUrl, new InternetExplorerDriver());
	}

	/**
	 * 
	 */
	public IntegrationTest() {
		this(new InternetExplorerDriver());
	}

	/**
	 * Set the base application URL
	 * @param webAppUrl as a String
	 * @throws MalformedURLException
	 */
	protected void setWebAppUrl(URL url) {
		webAppUrl = url; 
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
	protected void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
		this.webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
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
