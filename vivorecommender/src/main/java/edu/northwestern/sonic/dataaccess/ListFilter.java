package edu.northwestern.sonic.dataaccess;

import java.net.URI;

import edu.northwestern.sonic.util.ArraysUtil;

/**
 * SPARQL FILTER class from a list
 *
 * @author Hugh
 *
 */
public class ListFilter {
	
	private static final String DEFAULT_VARIABLE_NAME = "X";
	
	private static String filter(String s){
		return (new StringBuffer("FILTER(")).append(s).append(")").toString();
	}
	
	private static String filterAuxiliary(Object[] objects, String var, String prefix, String suffix){
		StringBuffer returnValue = new StringBuffer();
		String sparqlVar = (new StringBuffer("?")).append(var).append("=").append(prefix).toString();
		for(int i=0; i < objects.length; i++) {
			returnValue.append(sparqlVar).append(objects[i]).append(suffix);
			if(i < objects.length - 1)
				returnValue.append("||");			
		}
		return filter(returnValue.toString());		
	}
	
	private static String filterAuxiliary(Object[] objects, String var, String delimiter){
		return filterAuxiliary(objects, var, delimiter, delimiter);		
	}
	
	private static String filterAuxiliary(Object[] objects, String var){
		return filterAuxiliary(objects, var, "", "");		
	}
	
	/**
	 * build a FILTER clause from a list
	 * @param ints a list of ints
	 * @param var a variable name
	 * @return a SPARQL fragment to filter results by a list of ints
	 */
	public static String filter(int[] ints, String var) {
		return filterAuxiliary(ArraysUtil.toString(ints), var);
	}

	/**
	 * default variable name is X
	 */
	public static String filter(int[] ints) {
		return filter(ints, DEFAULT_VARIABLE_NAME);
	}

	/**
	 * build a FILTER clause from a list
	 * @param strings a list of string values of var
	 * @param var a variable name
	 * @return a SPARQL fragment to filter results by a list of strings
	 */
	public static String filter(String[] strings, String var) {
		return filterAuxiliary(strings, var, "'");
	}

	/**
	 * default variable name is X
	 */
	public static String filter(String[] strings) {
		return filter(strings, DEFAULT_VARIABLE_NAME);
	}

	/**
	 * build a FILTER clause from a list
	 * @param strings a list of URIs
	 * @param var a variable name
	 * @return a SPARQL fragment to filter results by a list of strings
	 */
	public static String filter(URI[] uris, String var) {
		return filterAuxiliary(uris, var, "<", ">");
	}

	/**
	 * default variable name is X
	 */
	public static String filter(URI[] uris) {
		return filter(uris, DEFAULT_VARIABLE_NAME);
	}

}
