package edu.northwestern.sonic.integration.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;

public class LocalResultsPageOrganicKatritzkyIT extends IntegrationTest {

	@BeforeClass
	public static void beforeClass() {
		IntegrationTest.setDefaultWebAppUrl(LocalWebApp.getUrl());
	}
	
	@Before
	public void before() {
		getWebDriver().get(getWebAppUrl().toString());
		getWebDriver().findElement(By.name("search")).sendKeys("Organic\t");
		getWebDriver().findElement(new ByChained(By.className("form-search"), By.tagName("button"))).click();
        WebElement userEmail = getWebDriver().findElement(By.name("userEmail"));
        userEmail.sendKeys("katritzky@chem.ufl.edu");
        userEmail.submit();
	}

	@Test
	public void testOrganicKaritzkyResultsPage() {
        assertEquals("title", "VIVO Recommender", getWebDriver().getTitle());
        assertTrue("first name", getWebDriver().getPageSource().contains("Alan"));
        assertTrue("last name", getWebDriver().getPageSource().contains("Katritzky"));
	}

}
