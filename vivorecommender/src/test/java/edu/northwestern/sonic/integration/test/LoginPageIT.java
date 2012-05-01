package edu.northwestern.sonic.integration.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPageIT extends IntegrationTest {

	@Before
	public void before() {
		getWebDriver().get(getWebAppUrl().toString() + "/login");
	}

	@Test
	public void testLoginPage() {
        assertEquals("title", "VIVO Recommender", getWebDriver().getTitle());
        WebElement userEmail = getWebDriver().findElement(By.name("userEmail"));
        assertNotNull("e-mail prompt", userEmail);
	}
	
	@Test
	public void testLoginPageNotFound() {
        WebElement userEmail = getWebDriver().findElement(By.name("userEmail"));
        userEmail.sendKeys("foo@bar.com");
        userEmail.submit();
        assertTrue("Sorry", getWebDriver().getPageSource().contains("Sorry"));
	}

	@Test
	public void testLogin() {
        WebElement userEmail = getWebDriver().findElement(By.name("userEmail"));
        userEmail.sendKeys("eabuss@ufl.edu");
        userEmail.submit();
        assertFalse("not Sorry", getWebDriver().getPageSource().contains("Sorry"));
	}

}
