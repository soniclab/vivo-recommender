/**
 * Unit tests for safe (no exception) uri construction
 */
package edu.northwestern.sonic.util.test;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.northwestern.sonic.util.UriUtil;

/**
 * @author Hugh
 *
 */
public class UriUtilTest {
	private static final String uri = "http://www.example.com";
	private static final String notUri = "^";
	private static URI expectedUri = null;

	@BeforeClass
	public static void setUp() throws URISyntaxException {
		expectedUri = new URI(uri);
	}

	@Test(expected=URISyntaxException.class)
	public void testUnsafeUri() throws URISyntaxException {
		@SuppressWarnings("unused")
		URI uri = new URI(notUri);
	}

	/**
	 * Test method for {@link edu.northwestern.sonic.util.UriUtil#safeUriFactory(java.lang.String)}.
	 */
	@Test
	public void testSafeUriFactory() {
		assertEquals("safe uri", expectedUri, UriUtil.safeUriFactory(uri));
		assertEquals("not a uri", null, UriUtil.safeUriFactory(notUri));
	}

}
