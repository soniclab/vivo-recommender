package edu.northwestern.sonic.integration.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPageITest extends ITest {
	static WebDriver ie = new InternetExplorerDriver();
	
	@Before
	public void setUp() throws Exception {
		ie.get(getUrl().toString() + "/login");
	}

	@Test
	public void testLoginPageNotFound() {
        assertEquals("title", "VIVO Recommender", ie.getTitle());
        WebElement userEmail = ie.findElement(By.name("userEmail"));
        assertNotNull("e-mail prompt", userEmail);
        userEmail.sendKeys("foo@bar.com");
        userEmail.submit();
        assertTrue("Sorry", ie.getPageSource().contains("Sorry"));
	}

	@Test
	public void testLogin() {
        assertEquals("title", "VIVO Recommender", ie.getTitle());
        WebElement userEmail = ie.findElement(By.name("userEmail"));
        assertNotNull("e-mail prompt", userEmail);
        userEmail.sendKeys("eabuss@ufl.edu");
        userEmail.submit();
        assertFalse("Sorry", ie.getPageSource().contains("Sorry"));
	}

}
