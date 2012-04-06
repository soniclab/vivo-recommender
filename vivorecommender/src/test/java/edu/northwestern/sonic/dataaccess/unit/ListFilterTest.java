/**
 * unit tests for sparql filter lists
 */
package edu.northwestern.sonic.dataaccess.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.northwestern.sonic.dataaccess.ListFilter;

/**
 * @author Hugh
 *
 */
public class ListFilterTest {
	int[] one = { 1 };
	int[] three = { 1 , 2 , 3 };

	/**
	 * Test method for {@link edu.northwestern.sonic.dataaccess.ListFilter#filter(int[], java.lang.String)}.
	 */
	@Test
	public void testFilterIntArrayString() {
		assertEquals("array of one", "FILTER(?Y=1)", ListFilter.filter(one, "Y"));
		assertEquals("array of three", "FILTER(?Y=1||?Y=2||?Y=3)", ListFilter.filter(three, "Y"));
	}

	/**
	 * Test method for {@link edu.northwestern.sonic.dataaccess.ListFilter#filter(int[])}.
	 */
	@Test
	public void testFilterIntArray() {
		assertEquals("array of one", "FILTER(?X=1)", ListFilter.filter(one));
		assertEquals("array of three", "FILTER(?X=1||?X=2||?X=3)", ListFilter.filter(three));
	}

}