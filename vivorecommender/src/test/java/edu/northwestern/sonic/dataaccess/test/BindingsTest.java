/**
 * unit tests for sparql bindings lists
 */
package edu.northwestern.sonic.dataaccess.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.northwestern.sonic.dataaccess.Bindings;
import edu.northwestern.sonic.util.ArraysUtil;

/**
 * @author Hugh
 *
 */
public class BindingsTest {
	int[] one = { 1 };
	int[] three = { 1 , 2 , 3 };

	@Test
	public void testFilterIntArray() {
		String expected1 = "BINDINGS ?X {\n" +
				"(1)\n" +
				"}";
		assertEquals("array of one", expected1, Bindings.bindings(one));
		String expected2 = "BINDINGS ?X {\n" +
				"(1)\n" +
				"(2)\n" +
				"(3)\n" +
				"}";
		assertEquals("array of three", expected2, Bindings.bindings(three));
	}

	@Test
	public void testFilterStringArray() {
		String expected1 = "BINDINGS ?X {\n" +
				"('1')\n" +
				"}";
		assertEquals("array of one string", expected1, Bindings.bindings(ArraysUtil.toString(one)));
		String expected2 = "BINDINGS ?X {\n" +
				"('1')\n" +
				"('2')\n" +
				"('3')\n" +
				"}";
		assertEquals("array of three strings", expected2, Bindings.bindings(ArraysUtil.toString(three)));
	}

	@Test
	public void testFilterIntArrayString() {
		String expected1 = "BINDINGS ?Y {\n" +
				"(1)\n" +
				"}";
		assertEquals("array of one", expected1, Bindings.bindings(one, "Y"));
		String expected2 = "BINDINGS ?Y {\n" +
				"(1)\n" +
				"(2)\n" +
				"(3)\n" +
				"}";
		assertEquals("array of three", expected2, Bindings.bindings(three, "Y"));
	}

}
