package edu.northwestern.sonic.util;

import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.StringConverter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.math.linear.ArrayRealVector;
/**
 * array processing utilities
 *
 * @author Hugh
 * 
 */
public class ArraysUtil {

	/**
	 * overload for default 0 start
	 * @param size
	 * @param start
	 * @return an array of int containing size sequential values starting at start
	 */
	public static int[] fillSequential(final int size) {
		return fillSequential(size, 0);
	}

	/**
	 * @param size
	 * @param start
	 * @return an array of int containing size sequential values starting at start
	 */
	public static int[] fillSequential(final int size, final int start) {
		Integer[] arr = new Integer[size];
		fillSequential(arr, start);
		return ArrayUtils.toPrimitive(arr);
	}

	/**
	 * like Arrays.fill, but sequential
	 * 
	 * @param arr
	 * @param start
	 */
	public static void fillSequential(final Integer[] arr, final int start) {
		for (int i = 0; i < arr.length; i++)
			arr[i] = start + i;
	}

	/**
	 * overload for default 0 start
	 * 
	 * @param arr
	 */
	public static void fillSequential(final Integer[] arr) {
		fillSequential(arr, 0);
	}

	/**
	 * like Arrays.fill, but with dynamic allocation
	 * 
	 * @param size
	 * @param value
	 */
	public static String[] fill(final int size, final String value) {
		String[] result = new String[size];
		Arrays.fill(result, value);
		return result;
	}

	/**
	 * like Arrays.fill, but with dynamic allocation
	 * 
	 * @param size
	 * @param value
	 */
	public static int[] fill(final int size, final int value) {
		int[] result = new int[size];
		Arrays.fill(result, value);
		return result;
	}

	/**
	 * @param arr, array of Integer
	 * @return array of int
	 */
	public static Integer[] toInteger(final int[] arr) {
		final ArrayConverter arrayConverter = new ArrayConverter(int[].class, new IntegerConverter());
		return (Integer[]) arrayConverter.convert(Integer[].class, arr);
	}

	/**
	 * @param arr, array of Integer
	 * @return array of int
	 */
	public static Double[] toDouble(final int[] arr) {
		final ArrayConverter arrayConverter = new ArrayConverter(int[].class, new DoubleConverter());
		return (Double[]) arrayConverter.convert(Double[].class, arr);
	}

	/**
	 * @param arr, array of Integer
	 * @return array of int
	 */
	public static String[] toString(final int[] arr) {
		final ArrayConverter arrayConverter = new ArrayConverter(int[].class, new StringConverter());
		return (String[]) arrayConverter.convert(String[].class, arr);
	}

	/**
	 * normalize array of doubles to the interval -1.0 .. 1.0
	 * @param arr
	 * @return
	 */
	public static double[] normalizeToUnitInterval(final double[] arr) {
		// largest absolute magnitude
		double divisor = Math.max(Math.abs(NumberUtils.min(arr)), Math.abs(NumberUtils.max(arr)));
		return divide(arr, divisor);
	}

	/**
	 * normalize array of ints to the interval -1.0 .. 1.0
	 * @param arr
	 * @return
	 */
	public static double[] normalizeToUnitInterval(final int[] arr) {
		// largest absolute magnitude
		double divisor = Math.max(Math.abs(NumberUtils.min(arr)), Math.abs(NumberUtils.max(arr)));
		return divide(ArrayUtils.toPrimitive(toDouble(arr)), divisor);
	}

	/**
	 * calculate the multiplicative inverse of each element
	 * @param arr
	 * @return
	 */
	public static double[] divide(final double[] arr, double divisor) {
		return new ArrayRealVector(arr).mapDivide(divisor).toArray();
	}

	/**
	 * calculate the multiplicative inverse of each element
	 * @param arr
	 * @return
	 */
	public static double[] inverse(final int[] arr) {
		return new ArrayRealVector(toDouble(arr)).mapInv().toArray();
	}

	/**
	 * add corresponding elements in 2 arrays
	 * @param arr1 addend
	 * @param arr2 addend
	 * @return sum
	 */
	public static double[] add(final double[] arr1, final double[] arr2){
		return new ArrayRealVector(arr1).add(new ArrayRealVector(arr2)).toArray();
	}
	
	/**
	 * concatentate arrays of String
	 * extend org.apache.commons.lang3.ArrayUtils.addAll to 3 arrays of String
	 * @param a
	 * @param b
	 * @param c
	 * @return concatenaton
	 */
	public static String[] addAll(final String[] a, final String[] b, final String[] c) {
		return ArrayUtils.addAll(ArrayUtils.addAll(a, b), c);
	}
	
	/**
	 * concatentate arrays of String
	 * extend org.apache.commons.lang3.ArrayUtils.addAll to 4 arrays of String
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @return concatenaton
	 */
	public static String[] addAll(final String[] a, final String[] b, final String[] c, final String[] d) {
		return ArrayUtils.addAll(ArrayUtils.addAll(ArrayUtils.addAll(a, b), c), d);
	}
	
	public static String[] toArrayString(Collection<?> collection) {
		return (String[]) collection.toArray(new String[0]);
	}
	
	public static int[] toArrayInt(Collection<?> collection) {
		return ArrayUtils.toPrimitive(collection.toArray(new Integer[0]));
	}
	
	public static long[] toArrayLong(Collection<?> collection) {
		return ArrayUtils.toPrimitive(collection.toArray(new Long[0]));
	}
	
	public static double[] toArrayDouble(Collection<?> collection) {
		return ArrayUtils.toPrimitive(collection.toArray(new Double[0]));
	}
	
	public static boolean[] toArrayBoolean(Collection<?> collection) {
		return ArrayUtils.toPrimitive(collection.toArray(new Boolean[0]));
	}
	
}