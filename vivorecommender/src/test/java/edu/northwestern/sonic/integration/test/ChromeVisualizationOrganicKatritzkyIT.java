package edu.northwestern.sonic.integration.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChromeVisualizationOrganicKatritzkyIT extends IntegrationTest {
	
	@BeforeClass
	public static void beforeClassChrome() {
		IntegrationTest.setDefaultWebDriver(IntegrationTest.WEBDRIVER.CHROME);
	}
	
	@Before
	public void before() {
		getWebDriver().get(getWebAppUrl().toString());
		getWebDriver().findElement(By.name("search")).sendKeys("Organic\t");
		getWebDriver().findElement(new ByChained(By.className("form-search"), By.tagName("button"))).click();
        WebElement userEmail = getWebDriver().findElement(By.name("userEmail"));
        userEmail.sendKeys("katritzky@chem.ufl.edu");
        userEmail.submit();
        getWebDriver().findElement(By.linkText("Results as graph")).click();
	}

	@Test
	public void testOrganicKaritzkyVisualization() {
        assertEquals("title", "Rec Layout", getWebDriver().getTitle());
		getWebDriver().findElement(By.tagName("svg"));
        Wait<WebDriver> wait = new WebDriverWait(getWebDriver(), 60);
        wait.until(isDisplayed(By.tagName("g")));
		assertEquals("nodes", 2318, getWebDriver().findElements(By.tagName("line")).size());
	}

}
