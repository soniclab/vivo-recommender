/**
 * 
 */
package edu.northwestern.sonic.dataaccess.medline;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
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
	
	private final static String[][] queryPrefixDescriptions = {
		{"ml", "http://research.icts.uiowa.edu/ontology/medline.rdf#"},
		{"xsd", "http://www.w3.org/2001/XMLSchema#"},
	};
	private final static List<ImmutablePair<String, URI>> queryPrefixes = new ArrayList<ImmutablePair<String, URI>>();
	static {
		for(String[] queryPrefixDescription : queryPrefixDescriptions) 
			try {
				queryPrefixes.add(ImmutablePair.of(queryPrefixDescription[0], new URI(queryPrefixDescription[1])));
			} catch (URISyntaxException e) {
				e.printStackTrace();
				System.exit(0);
			}
	}
		
	public MedlineSparqlService(final URL url) {
		super(url, queryPrefixes);
	}

	public MedlineSparqlService() {
		super(url, queryPrefixes);
	}


}
