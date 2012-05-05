package edu.northwestern.sonic.integration.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

public class HomePageIT extends IntegrationTest {

	@Before
	public void before() {
		getWebDriver().get(getWebAppUrl().toString());
	}

	@Test
	public void testHomePage() {
		assertEquals("title", "VIVO Recommender", getWebDriver().getTitle());
		getWebDriver().findElement(By.name("search")).sendKeys("Entomology\t");
		getWebDriver().findElement(new ByChained(By.className("form-search"), By.tagName("button"))).click();
		assertEquals("title", "VIVO Recommender", getWebDriver().getTitle());
		// e-mail prompt?
		assertNotNull(getWebDriver().findElement(By.name("userEmail")));
	}

}
