package edu.northwestern.sonic.integration.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.ByChained;

public class HomePageITest extends ITest {
	static WebDriver ie = new InternetExplorerDriver();

	@Test
	public void testHomePage() {
		ie.get(getUrl().toString());
        assertEquals("title", "VIVO Recommender", ie.getTitle());
        ie.findElement(By.name("search")).sendKeys("Entomology");
        ie.findElement(new ByChained(By.className("form-search"), By.tagName("button"))).click();
        assertEquals("title", "VIVO Recommender", ie.getTitle());
        assertNotNull(ie.findElement(By.name("userEmail"))); //e-mail prompt?
	}

}
