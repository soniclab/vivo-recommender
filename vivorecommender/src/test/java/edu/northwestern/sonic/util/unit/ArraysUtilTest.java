/**
 * Tests for array handling utilities
 */
package edu.northwestern.sonic.util.unit;

import static org.junit.Assert.*;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import edu.northwestern.sonic.util.ArraysUtil;

/**
 * @author Hugh
 *
 */
public class ArraysUtilTest {
	Integer[] arrInteger = new Integer[3];
	int[] arrInt;
	int[] arrInt2, arrInt3;
	double d1=-0.9, d2=0.5, d3=1.2;
	double[] arrDouble = {d2, d3, d1};
	String[] arr;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ArraysUtil.fillSequential(arrInteger);
		arrInt = ArrayUtils.toPrimitive(arrInteger);
		arrInt2 = ArraysUtil.fillSequential(4);
		arrInt3 = ArraysUtil.fillSequential(4,1);
		arr = ArraysUtil.fill(4, "foo");
	}

	/**
	 * Test method for {@link nucats.ArraysUtil#fillSequential(java.lang.Integer[])}.
	 */
	@Test
	public void testFillSequential() {
		assertEquals("first element", 0, arrInteger[0].intValue());
		assertEquals("second element", 1, arrInteger[1].intValue());
		assertEquals("third element", 2, arrInteger[2].intValue());
		assertEquals("second element with start", 2, arrInt3[1]);
	}

	/**
	 * Test method for {@link nucats.ArraysUtil#fill}.
	 */
	@Test
	public void testFill() {
		assertEquals("first element", "foo", arr[0]);
		assertEquals("last element", "foo", arr[arr.length - 1]);
	}

	/**
	 * Test method for {@link nucats.ArraysUtil#toInt(java.lang.Integer[])}.
	 */
	@Test
	public void testToInt() {
		assertEquals("length", 3, arrInt.length);
		assertEquals("first element as int", 0, arrInt[0]);
		assertEquals("second element as int", 1, arrInt[1]);
		assertEquals("third element as int", 2, arrInt[2]);
	}

	/**
	 * Test method for {@link nucats.ArraysUtil#toString(int[])}.
	 */
	@Test
	public void testToString() {
		String[] arr = ArraysUtil.toString(arrInt);
		assertEquals("length", arrInt.length, arr.length);
		assertEquals("first element as String", "0", arr[0]);
		assertEquals("second element as String", "1", arr[1]);
		assertEquals("third element as String", "2", arr[2]);
	}

	@Test
	public void testNormalizeToSignedUnitInterval() {
		double[] arrNormalized = ArraysUtil.normalizeToUnitInterval(arrDouble);
		assertEquals("first element normalized", 0.416, arrNormalized[0], 0.001);
		assertEquals("second element normalized", 1.0, arrNormalized[1], 0.0001);
		assertEquals("third element normalized", -0.75, arrNormalized[2], 0.001);
	}

	@Test
	public void testReciprocals() {
		double[] arrReciprocals = ArraysUtil.inverse(arrInt2);
		assertTrue("first element reciprocal is infinite", Double.isInfinite(arrReciprocals[0]));
		assertEquals("second element reciprocal", 1.0, arrReciprocals[1], 0.01);
		assertEquals("third element reciprocal", 0.5, arrReciprocals[2], 0.01);
		assertEquals("fourth element reciprocal", 0.33, arrReciprocals[3], 0.01);
	}

	@Test
	public void testToDouble() {
		Double[] arrDouble = ArraysUtil.toDouble(arrInt);
		assertEquals("first element", 0.0, arrDouble[0], 0.01);
		assertEquals("second element", 1.0, arrDouble[1], 0.01);
		assertEquals("third element", 2.0, arrDouble[2], 0.01);
	}

	@Test
	public void testAdd() {
		double[] arrSum = {0.0, 1.0, 2.0};
		arrSum = ArraysUtil.add(arrSum, arrDouble);
		assertEquals("first element of sum", 0.5, arrSum[0], 0.01);
		assertEquals("second element of sum", 2.2, arrSum[1], 0.01);
		assertEquals("third element of sum", 1.1, arrSum[2], 0.01);
	}

}
