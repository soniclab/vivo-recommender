/**
 * Unit tests for safe (no exception) URL construction
 */
package edu.northwestern.sonic.util.test;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.northwestern.sonic.util.UrlUtil;

/**
 * @author Hugh
 *
 */
public class UrlUtilTest {
	private static final String url = "http://www.example.com";
	private static final String notUrl = "^";
	private static URL expectedUrl = null;

	@BeforeClass
	public static void setUp() throws MalformedURLException {
		expectedUrl = new URL(url);
	}

	@Test(expected=MalformedURLException.class)
	public void testUnsafeUri() throws MalformedURLException {
		@SuppressWarnings("unused")
		URL url = new URL(notUrl);
	}

	/**
	 * Test method for {@link edu.northwestern.sonic.util.UriUtil#safeUriFactory(java.lang.String)}.
	 */
	@Test
	public void testSafeUriFactory() {
		assertEquals("safe uri", expectedUrl, UrlUtil.safeUrlFactory(url));
		assertEquals("not a uri", null, UrlUtil.safeUrlFactory(notUrl));
	}

}
