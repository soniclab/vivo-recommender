/**
 * 
 */
package edu.northwestern.sonic.dataaccess.medline;

import java.net.MalformedURLException;
import java.net.URL;

import edu.northwestern.sonic.dataaccess.SparqlService;

/**
 * @author Hugh
 *
 */
public class MedlineSparqlService extends SparqlService {
	
	private static URL url = null;
	static {
		try {
			url = new URL("http://research.icts.uiowa.edu/MedlineEndpoint/sparql");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private final static String queryPrefix =
		"PREFIX ml: <http://research.icts.uiowa.edu/ontology/medline.rdf#>" + "\n" +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
	
	public MedlineSparqlService(URL url, String queryPrefix) {
		super(url, queryPrefix);
	}

	public MedlineSparqlService() {
		super(url, queryPrefix);
	}


}
