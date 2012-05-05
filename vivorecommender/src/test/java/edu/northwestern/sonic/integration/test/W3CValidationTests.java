package edu.northwestern.sonic.integration.test;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

public class W3CValidationTests extends IntegrationTest {
	private static URL W3C_VALIDATOR = null;
	static {
		try {
			W3C_VALIDATOR = new URL("http://validator.w3.org/check?charset=%28detect+automatically%29&doctype=Inline&group=0&uri=");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}	

	@Test
	public void testHomePage() {
		URL target = getWebAppUrl();
		getWebDriver().get(W3C_VALIDATOR.toString() + target.toString());
		assertEquals("title", "[Valid] Markup Validation of " + target.toString() + " - W3C Markup Validator", getWebDriver().getTitle());
        assertTrue("valid", getWebDriver().getPageSource().contains("This document was successfully checked"));
	}

}
