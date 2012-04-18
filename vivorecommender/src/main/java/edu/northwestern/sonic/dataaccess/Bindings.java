package edu.northwestern.sonic.dataaccess;

import java.net.URI;

import edu.northwestern.sonic.util.ArraysUtil;

/**
 * SPARQL BINDINGS clause from a list
 *
 * @author Hugh
 *
 */
public class Bindings {
	
	private static final String DEFAULT_VARIABLE_NAME = "X";
	
	private static String bindings(String bindingsList, String variableName) {
		return (new StringBuffer("BINDINGS ?")).append(variableName).append(" {\n").append(bindingsList).append("}").toString();
	}
	
	private static String bindingsList(Object[] objects, String prefix, String suffix) {
		StringBuffer returnValue = new StringBuffer();
		for(Object object : objects)
			returnValue.append("(").append(prefix).append(object).append(suffix).append(")\n");
		return returnValue.toString();
	}
	
	private static String bindingsList(Object[] objects, String delimiter){
		return bindingsList(objects, delimiter, delimiter);		
	}
	
	private static String bindingsList(Object[] objects){
		return bindingsList(objects, "", "");		
	}
	
	/**
	 * build a FILTER clause from a list
	 * @param ints a list of ints
	 * @param var a variable name
	 * @return a SPARQL fragment to filter results by a list of ints
	 */
	public static String bindings(int[] ints, String var) {
		return bindings(bindingsList(ArraysUtil.toString(ints)), var);
	}

	/**
	 * default variable name is X
	 */
	public static String bindings(int[] ints) {
		return bindings(ints, DEFAULT_VARIABLE_NAME);
	}

	/**
	 * build a FILTER clause from a list
	 * @param strings a list of string values of var
	 * @param var a variable name
	 * @return a SPARQL fragment to filter results by a list of strings
	 */
	public static String bindings(String[] strings, String var) {
		return bindings(bindingsList(strings, "'"), var);
	}

	/**
	 * default variable name is X
	 */
	public static String bindings(String[] strings) {
		return bindings(strings, DEFAULT_VARIABLE_NAME);
	}

	/**
	 * build a FILTER clause from a list
	 * @param strings a list of URIs
	 * @param var a variable name
	 * @return a SPARQL fragment to filter results by a list of strings
	 */
	public static String bindings(URI[] uris, String var) {
		return bindings(bindingsList(uris, "<", ">"), var);
	}

	/**
	 * default variable name is X
	 */
	public static String bindings(URI[] uris) {
		return bindings(uris, DEFAULT_VARIABLE_NAME);
	}

}
