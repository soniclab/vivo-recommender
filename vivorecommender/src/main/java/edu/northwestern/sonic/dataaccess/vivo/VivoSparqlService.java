/**
 * 
 */
package edu.northwestern.sonic.dataaccess.vivo;

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
public class VivoSparqlService extends SparqlService {
	
	private static URL url = null;
	static {
		try {
			url = new URL("http://ciknow1.northwestern.edu:3030/UF-VIVO/query");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private final static String[][] queryPrefixDescriptions = {
		{"rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#"},
		{"rdfs", "http://www.w3.org/2000/01/rdf-schema#"},
		{"xsd", "http://www.w3.org/2001/XMLSchema#"},
		{"owl", "http://www.w3.org/2002/07/owl#"},
		{"swrl", "http://www.w3.org/2003/11/swrl#"},
		{"swrlb", "http://www.w3.org/2003/11/swrlb#"},
		{"vitro", "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#"},
		{"far", "http://vitro.mannlib.cornell.edu/ns/reporting#"},
		{"acti", "http://vivoweb.org/ontology/activity-insight#"},
		{"aktp", "http://www.aktors.org/ontology/portal#"},
		{"bibo", "http://purl.org/ontology/bibo/"},
		{"cce", "http://vivoweb.org/ontology/cornell-cooperative-extension#"},
		{"hr", "http://vivo.cornell.edu/ns/hr/0.9/hr.owl#"},
		{"dcterms", "http://purl.org/dc/terms/"},
		{"dcelem", "http://purl.org/dc/elements/1.1/"},
		{"event", "http://purl.org/NET/c4dm/event.owl#"},
		{"foaf", "http://xmlns.com/foaf/0.1/"},
		{"geo", "http://aims.fao.org/aos/geopolitical.owl#"},
		{"mann", "http://vivo.cornell.edu/ns/mannadditions/0.1#"},
		{"ospcu", "http://vivoweb.org/ontology/cu-vivo-osp#"},
		{"pvs", "http://vivoweb.org/ontology/provenance-support#"},
		{"pubmed", "http://vitro.mannlib.cornell.edu/ns/pubmed#"},
		{"rdfs", "http://www.w3.org/2000/01/rdf-schema#"},
		{"rdfsyn", "http://www.w3.org/1999/02/22-rdf-syntax-ns#"},
		{"ero", "http://purl.obolibrary.org/obo/"},
		{"scires", "http://vivoweb.org/ontology/scientific-research#"},
		{"skos", "http://www.w3.org/2004/02/skos/core#"},
		{"socsci", "http://vivo.library.cornell.edu/ns/vivo/socsci/0.1#"},
		{"stars", "http://vitro.mannlib.cornell.edu/ns/cornell/stars/classes#"},
		{"wos", "http://vivo.mannlib.cornell.edu/ns/ThomsonWOS/0.1#"},
		{"vitro-public", "http://vitro.mannlib.cornell.edu/ns/vitro/public#"},
		{"vivo", "http://vivoweb.org/ontology/core#"},
		{"vivoc", "http://vivo.library.cornell.edu/ns/0.1#"}
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
		
	public VivoSparqlService(final URL url) {
		super(url, queryPrefixes);
	}

	public VivoSparqlService() {
		super(url, queryPrefixes);
	}


}
