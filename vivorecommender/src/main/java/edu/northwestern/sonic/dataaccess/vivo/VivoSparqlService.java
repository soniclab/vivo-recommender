/**
 * 
 */
package edu.northwestern.sonic.dataaccess.vivo;

import java.net.MalformedURLException;
import java.net.URL;

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
	
	private final static String queryPrefix =
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + "\n" +
		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + "\n" +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + "\n" +
		"PREFIX owl: <http://www.w3.org/2002/07/owl#>" + "\n" +
		"PREFIX swrl: <http://www.w3.org/2003/11/swrl#>" + "\n" +
		"PREFIX swrlb: <http://www.w3.org/2003/11/swrlb#>" + "\n" +
		"PREFIX vitro: <http://vitro.mannlib.cornell.edu/ns/vitro/0.7#>" + "\n" +
		"PREFIX far: <http://vitro.mannlib.cornell.edu/ns/reporting#>" + "\n" +
		"PREFIX acti: <http://vivoweb.org/ontology/activity-insight#>" + "\n" +
		"PREFIX aktp: <http://www.aktors.org/ontology/portal#>" + "\n" +
		"PREFIX bibo: <http://purl.org/ontology/bibo/>" + "\n" +
		"PREFIX cce: <http://vivoweb.org/ontology/cornell-cooperative-extension#>" + "\n" +
		"PREFIX hr: <http://vivo.cornell.edu/ns/hr/0.9/hr.owl#>" + "\n" +
		"PREFIX dcterms: <http://purl.org/dc/terms/>" + "\n" +
		"PREFIX dcelem: <http://purl.org/dc/elements/1.1/>" + "\n" +
		"PREFIX event: <http://purl.org/NET/c4dm/event.owl#>" + "\n" +
		"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" + "\n" +
		"PREFIX geo: <http://aims.fao.org/aos/geopolitical.owl#>" + "\n" +
		"PREFIX mann: <http://vivo.cornell.edu/ns/mannadditions/0.1#>" + "\n" +
		"PREFIX ospcu: <http://vivoweb.org/ontology/cu-vivo-osp#>" + "\n" +
		"PREFIX pvs: <http://vivoweb.org/ontology/provenance-support#>" + "\n" +
		"PREFIX pubmed: <http://vitro.mannlib.cornell.edu/ns/pubmed#>" + "\n" +
		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + "\n" +
		"PREFIX rdfsyn: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + "\n" +
		"PREFIX ero: <http://purl.obolibrary.org/obo/>" + "\n" +
		"PREFIX scires: <http://vivoweb.org/ontology/scientific-research#>" + "\n" +
		"PREFIX skos: <http://www.w3.org/2004/02/skos/core#>" + "\n" +
		"PREFIX socsci: <http://vivo.library.cornell.edu/ns/vivo/socsci/0.1#>" + "\n" +
		"PREFIX stars: <http://vitro.mannlib.cornell.edu/ns/cornell/stars/classes#>" + "\n" +
		"PREFIX wos: <http://vivo.mannlib.cornell.edu/ns/ThomsonWOS/0.1#>" + "\n" +
		"PREFIX vitro-public: <http://vitro.mannlib.cornell.edu/ns/vitro/public#>" + "\n" +
		"PREFIX vivo: <http://vivoweb.org/ontology/core#>" + "\n" +
		"PREFIX vivoc: <http://vivo.library.cornell.edu/ns/0.1#>"+ "\n";
	
	public VivoSparqlService(URL url, String queryPrefix) {
		super(url, queryPrefix);
	}

	public VivoSparqlService() {
		super(url, queryPrefix);
	}


}
