package edu.northwestern.sonic.util.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.northwestern.sonic.util.SetUtil;

/**
 * tests for set utilities 
 * @author Hugh
 *
 */
public class SetUtilTest {
	private static final Set<Integer> empty1 = new TreeSet<Integer>();
	private static final Set<Integer> empty2 = new HashSet<Integer>( );
	private static final Set<Integer> oneTwoThree = new TreeSet<Integer>(Arrays.asList((new Integer[]{1, 2, 3})));
	private static final Set<Integer> twoThreeFour = new HashSet<Integer>(Arrays.asList((new Integer[]{2, 3, 4})));
	private static final Set<String> empty1String = new TreeSet<String>();
	private static final Set<String> empty2String = new HashSet<String>( );
	private static final Set<String> oneTwoThreeString = new TreeSet<String>(Arrays.asList((new String[]{"one", "two", "three"})));
	private static final Set<String> twoThreeFourString = new HashSet<String>(Arrays.asList((new String[]{"two", "three", "four"})));

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * Test method for {@link edu.northwestern.sonic.util.SetUtil#jaccard(java.util.Set, java.util.Set)}.
	 */
	@Test
	public void testJaccardEmpty() {
		assertEquals("empty set", 0.0, SetUtil.jaccard(empty1, empty1), 0.0001);
		assertEquals("empty set", 0.0, SetUtil.jaccard(empty2, empty2), 0.0001);
		assertEquals("empty set", 0.0, SetUtil.jaccard(empty1, empty2), 0.0001);
		assertEquals("empty set", 0.0, SetUtil.jaccard(empty2, empty1), 0.0001);
		assertEquals("empty set", 0.0, SetUtil.jaccard(oneTwoThree, empty1), 0.0001);
		assertEquals("empty set", 0.0, SetUtil.jaccard(empty1, oneTwoThree), 0.0001);
		assertEquals("empty set", 0.0, SetUtil.jaccard(twoThreeFour, empty1), 0.0001);
		assertEquals("empty set", 0.0, SetUtil.jaccard(empty1String, twoThreeFourString), 0.0001);
		assertEquals("empty set", 0.0, SetUtil.jaccard(empty1String, empty1String), 0.0001);
		assertEquals("empty set", 0.0, SetUtil.jaccard(empty2String, empty2String), 0.0001);
		assertEquals("empty set", 0.0, SetUtil.jaccard(empty1String, empty2String), 0.0001);
		assertEquals("empty set", 0.0, SetUtil.jaccard(empty2String, empty1String), 0.0001);
		assertEquals("empty set", 0.0, SetUtil.jaccard(oneTwoThreeString, empty1String), 0.0001);
		assertEquals("empty set", 0.0, SetUtil.jaccard(empty1String, oneTwoThreeString), 0.0001);
		assertEquals("empty set", 0.0, SetUtil.jaccard(twoThreeFourString, empty1String), 0.0001);
		assertEquals("empty set", 0.0, SetUtil.jaccard(empty1String, twoThreeFourString), 0.0001);
	}

	/**
	 * Test method for {@link edu.northwestern.sonic.util.SetUtil#jaccard(java.util.Set, java.util.Set)}.
	 */
	@Test
	public void testJaccard() {
		assertEquals("empty set", 1.0, SetUtil.jaccard(oneTwoThree, oneTwoThree), 0.0001);
		assertEquals("empty set", 1.0, SetUtil.jaccard(twoThreeFour, twoThreeFour), 0.0001);
		assertEquals("empty set", 0.5, SetUtil.jaccard(oneTwoThree, twoThreeFour), 0.0001);
		assertEquals("empty set", 0.5, SetUtil.jaccard(twoThreeFour, oneTwoThree), 0.0001);
		assertEquals("empty set", 1.0, SetUtil.jaccard(oneTwoThreeString, oneTwoThreeString), 0.0001);
		assertEquals("empty set", 1.0, SetUtil.jaccard(twoThreeFourString, twoThreeFourString), 0.0001);
		assertEquals("empty set", 0.5, SetUtil.jaccard(oneTwoThreeString, twoThreeFourString), 0.0001);
		assertEquals("empty set", 0.5, SetUtil.jaccard(twoThreeFourString, oneTwoThreeString), 0.0001);
	}

}
