package edu.northwestern.sonic.integration.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Holder for URL of web application under test
 * default to IE and the live server on ciknow1
 * web driver with implicit waiting
 * 
 * @author Hugh
 *
 */
public abstract class IntegrationTest {
	private static WEBDRIVER defaultWebDriver = WEBDRIVER.INTERNET_EXPLORER;
	public static enum WEBDRIVER { 
		INTERNET_EXPLORER,
		CHROME
	}
	
	private static URL deployedWebAppUrl = null;
	private static URL localWebAppUrl = null;
	static {
		try {
			deployedWebAppUrl = new URL("http://ciknow1.northwestern.edu/vivorecommender/");
			localWebAppUrl = new URL("http://localhost:8080/vivorecommender/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	public static enum WEBAPPURL { 
		LOCAL(localWebAppUrl),
		DEPLOYED(deployedWebAppUrl);
		
		private final URL url;
		
		WEBAPPURL(URL url) {
			this.url = url;
		}

		/**
		 * @return the url
		 */
		public URL getUrl() {
			return url;
		}
	}
	private static URL defaultWebAppUrl = WEBAPPURL.LOCAL.getUrl();
	
	private URL webAppUrl = null;
	private WebDriver webDriver = null;
	
	/**
	 * constructor
	 * @param webAppUrl
	 * @param webDriver
	 */
	public IntegrationTest(final URL webAppUrl, final WebDriver webDriver) {
		setWebAppUrl(webAppUrl);
		setWebDriver(webDriver);
	}

	/**
	 * constructor
	 * @param webDriver
	 */
	public IntegrationTest(final WebDriver webDriver) {
		this(defaultWebAppUrl, webDriver);
	}

	/**
	 * constructor
	 * @param webAppUrl
	 */
	public IntegrationTest(final URL webAppUrl) {
		this(webAppUrl, webDriverFactory());
	}

	/**
	 * no-argument constructor
	 */
	public IntegrationTest() {
		this(defaultWebAppUrl);
	}
	
	/**
	 * @param webDriver
	 * @return web driver based on parameter
	 */
	private static WebDriver webDriverFactory(final WEBDRIVER webDriver) {
		switch (webDriver) {
			case INTERNET_EXPLORER:
				return new InternetExplorerDriver();
			case CHROME:
				return new ChromeDriver();
			default:
				return null;
		}
	}

	/**
	 * @return web driver based on static setting
	 */
	private static WebDriver webDriverFactory() {
		return webDriverFactory(defaultWebDriver);
	}

	/**
	 * Set the base application URL
	 * @param webAppUrl as a String
	 * @throws MalformedURLException
	 */
	private void setWebAppUrl(final URL url) {
		webAppUrl = url; 
	}

	/**
	 * Set the default base application URL
	 * @param webAppUrl as a String
	 * @throws MalformedURLException
	 */
	protected static void setDefaultWebAppUrl(final URL url) {
		IntegrationTest.defaultWebAppUrl = url; 
	}

	/**
	 * Set the default base application URL
	 * @param webAppUrl as a String
	 * @throws MalformedURLException
	 */
	public static void setDefaultWebAppUrl(WEBAPPURL url) {
		setDefaultWebAppUrl(url.getUrl()); 
	}

	/**
	 * @return the application webAppUrl
	 */
	protected URL getWebAppUrl() {
		return webAppUrl;
	}

    /**
	 * @param webDriver the webDriver to set
	 */
	private void setWebDriver(final WebDriver webDriver) {
		this.webDriver = webDriver;
		this.webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

    /**
	 * @param webDriver the webDriver to set
	 */
	public static void setDefaultWebDriver(final WEBDRIVER webDriver) {
		IntegrationTest.defaultWebDriver = webDriver; 
	}

	/**
	 * @return the webDriver
	 */
	protected WebDriver getWebDriver() {
		return webDriver;
	}

	/**
	 * stop the web driver
	 */
	@After
	public void after() {
		getWebDriver().quit();
	}

	/**
	 * for use with wait until
	 * @param by
	 * @return a new is visible condition
	 */
	protected ExpectedCondition<WebElement> isDisplayed(final By by) {
        return new ExpectedCondition<WebElement>() {
          public WebElement apply(WebDriver driver) {
            WebElement element = driver.findElement(by);
            return element.isDisplayed() ? element : null;
          }
        };
      }

}
