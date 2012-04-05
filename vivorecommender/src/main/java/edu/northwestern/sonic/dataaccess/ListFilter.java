package edu.northwestern.sonic.dataaccess;
/**
 * SPARQL FILTER clase from a list
 *
 * @author Hugh
 *
 */
public class ListFilter {
	
	private static String filter(String s){
		return (new StringBuffer("FILTER(")).append(s).append(")").toString();
	}
	
	/**
	 * build a FILTER clause from a list
	 * @param <E>
	 * @param list a list
	 * @return a SPARQL fragment to filter results by a list of URIs
	 */
	public static String filter(int[] ints, String var) {
		StringBuffer buf = new StringBuffer();
		String sparqlVar = "?" + var + "=";
		for(int i=0; i < ints.length; i++) {
			buf.append(sparqlVar).append(ints[i]);
			if(i < ints.length - 1)
				buf.append("||");			
		}
		return filter(buf.toString());
	}

	/**
	 * default variable name is X
	 */
	public static String filter(int[] ints) {
		return filter(ints, "X");
	}

}
