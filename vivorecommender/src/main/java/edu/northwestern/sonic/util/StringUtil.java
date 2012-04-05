package edu.northwestern.sonic.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String utility functions
 *
 * @author Hugh
 * 
 */
public class StringUtil {

	/**
	 * prefix and suffix a string
	 * @param prefix
	 * @param s
	 * @param postfix
	 * @return
	 */
	public static String wrap(final String prefix, final String s, final String postfix){
		return prefix + s + postfix;
	}
	
	/**
	 * overload wrap for default characters less than and greater than
	 * @param obj
	 * @return
	 */
	public static String wrap(final Object obj){
		return wrap("<", obj.toString(), ">");
	}
	
	/**
	 * strip prefix and suffix from a string
	 * @param prefix
	 * @param s
	 * @param postfix
	 * @return
	 */
	public static String unwrap(final String prefix, final String s, final String postfix){
		Pattern p = Pattern.compile(prefix + "(.*)" + postfix);
		Matcher m = p.matcher(s);
		if(m.find())
			return m.group(1);
		return null;
	}
	
	/**
	 * overload unwrap for default prefix and suffix, less than and greater than
	 * @param s
	 * @return
	 */
	public static String unwrap(final String s){
		return unwrap("<", s, ">");
	}
	
	/**
	 * overload afterLast for default separator forward slash
	 * @param s
	 * @param regex
	 * @return string after forward slash
	 */
	public static String afterLast(final String s){
		return afterLast(s, "/");
	}
	
	/**
	 * tail of a string
	 * @param s
	 * @param regex
	 * @return string after last occurrence of regular expression
	 */
	public static String afterLast(final String s, final String regex){
		String[] arr = s.split(regex);
		return arr[arr.length-1];
	}
	
}
