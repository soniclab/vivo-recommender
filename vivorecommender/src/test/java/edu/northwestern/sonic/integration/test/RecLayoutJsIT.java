package edu.northwestern.sonic.integration.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;

public class RecLayoutJsIT extends IntegrationTest {
	
	private Object actual(String script) {
		return ((JavascriptExecutor)getWebDriver()).executeScript(script);
	}

	@Before
	public void before() {
		getWebDriver().get(getWebAppUrl().toString() + "/vis.html");
	}

	@Test
	public void testHomePage() {
        assertEquals("title", "Rec Layout", getWebDriver().getTitle());
		assertEquals("title", "Rec Layout", (String)actual("return document.title"));
		assertEquals("w", new Long(1000), actual("return w"));
		assertEquals("h", new Long(600), actual("return h"));
	}

}
