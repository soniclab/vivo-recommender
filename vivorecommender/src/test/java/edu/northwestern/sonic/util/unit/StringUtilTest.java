/**
 * Tests for string handling utilities
 */
package edu.northwestern.sonic.util.unit;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import edu.northwestern.sonic.util.StringUtil;

/**
 * @author Hugh
 *
 */
public class StringUtilTest {
	String s1;
	String prefix;
	String postfix;
	String s;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		s1 = "test";
		prefix = "prefix";
		postfix = "postfix";
		s = prefix + "X" + s1 + "X" + postfix;
	}

	/**
	 * Test method for {@link nucats.StringUtil#wrap(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testWrap() {
		assertEquals("wrap test - 1 arg", "<" + s1 + ">", StringUtil.wrap(s1));
		assertEquals("wrap test - 3 args", prefix + s1 + postfix, StringUtil.wrap(prefix, s1, postfix));
	}

	/**
	 * Test method for {@link nucats.StringUtil#wrap(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testUnwrap() {
		assertEquals("unwrap test", s1, StringUtil.unwrap(StringUtil.wrap(s1)));
	}

	@Test
	public void testAfterLast() {
		assertEquals("after last test", postfix, StringUtil.afterLast(s, "X"));
	}

}
