package edu.northwestern.sonic.dataaccess;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
/**
 * Thin wrapper for Jena SPARQL endpoints;
 * provides prefixes
 * this class depends on Jena but is relatively independent of our current application
 *
 * @author Hugh
 * 
 */
public class SparqlService {
	public static final SparqlService MEDLINE = new SparqlService(
			"http://research.icts.uiowa.edu/MedlineEndpoint/sparql",
			"PREFIX ml: <http://research.icts.uiowa.edu/ontology/medline.rdf#> " + "\n" +
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " + "\n"
			);
		
	public static final SparqlService UFLVIVO = new SparqlService(
			"http://ciknow1.northwestern.edu:3030/UF-VIVO/query",
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
			"PREFIX vivoc: <http://vivo.library.cornell.edu/ns/0.1#>"+ "\n"
			);
		
	private String url;
	private String queryPrefix;
	
	/**
	 * @param url
	 * @param queryPrefix
	 */
	public SparqlService(String url, String queryPrefix) {
		this.url = url;
		this.queryPrefix = queryPrefix;
	}

	/**
	 * @return the url
	 */
	private String getUrl() {
		return url;
	}

	/**
	 * @return the queryPrefix
	 */
	private String getQueryPrefix() {
		return queryPrefix;
	}

	private QueryExecution getQueryExecution(Query query) {
		return QueryExecutionFactory.sparqlService(getUrl(), query);
	}
	
	private QueryExecution getQueryExecution(String queryString) {
		Query query = LoggingQueryFactory.create(getQueryPrefix() + queryString);
		return getQueryExecution(query);
	}

	private static String distinctQuery(String whereClause) {
		final String DISTINCT_PREFIX =
				"SELECT DISTINCT ?X " +  "\n" +
				"WHERE { " +  "\n";
		StringBuffer queryStringBuffer = new StringBuffer(DISTINCT_PREFIX);
		queryStringBuffer.append(whereClause);
		queryStringBuffer.append("\n}");
		return queryStringBuffer.toString();
	}

	public Set<Integer> getDistinctSortedIntegers(String whereClause) {
		QueryExecution  queryExecution = getQueryExecution(distinctQuery(whereClause));
		ResultSet resultSet = queryExecution.execSelect();
		TreeSet<Integer> returnValue = new TreeSet<Integer>();
		while(resultSet.hasNext()) {
			QuerySolution querySolution = resultSet.nextSolution();
			Literal literal = querySolution.getLiteral("X");
			returnValue.add(new Integer(literal.getInt()));
	    }
		return returnValue;
	}
	
	public Set<URI> getDistinctSortedURIs(String whereClause) throws URISyntaxException {
		QueryExecution  queryExecution = getQueryExecution(distinctQuery(whereClause));
		ResultSet resultSet = queryExecution.execSelect();
		TreeSet<URI> returnValue = new TreeSet<URI>();
		while(resultSet.hasNext()) {
			QuerySolution querySolution = resultSet.nextSolution();
			Resource resource = querySolution.getResource("X");
			returnValue.add(new URI(resource.getURI()));
	    }
		return returnValue;
	}

	public boolean ask(String askClause){
		StringBuffer queryStringBuffer = new StringBuffer("ASK {\n");
		queryStringBuffer.append(askClause);
		queryStringBuffer.append("\n}");
		QueryExecution qe = getQueryExecution(queryStringBuffer.toString());
		return qe.execAsk();
	}
	
	/*
	 * Get a list of the results of a query as strings
	 * for simple queries by convention our free variable is X
	 */
	public List<String> getStrings(String query) {
		List<String[]> results = getStrings(query, new String[]{"X"});
		ArrayList<String> result = new ArrayList<String>();
		for(String[] arr : results)
			result.add(arr[0]);
		return result;
	}

	/*
	 * Get a list of the results of a query as strings
	 *
	 * @param query Sparql query as a String, minus the prefixes
	 * @param variables list of unbound variables used in the query parameter
	 * @return a list of arrays of String, each array of same length as the variables parameter
	 */
	public List<String[]> getStrings(String query, String[] variables) {
		Set<String[]> results = new HashSet<String[]>(); // no dups
		    QueryExecution qe = getQueryExecution(query);
		    ResultSet rs = qe.execSelect();
		    try {
				while (rs.hasNext()) {
					QuerySolution sol = rs.nextSolution();
					String[] result = new String[variables.length];
					for(int i=0; i < variables.length; i++) {
						RDFNode node = sol.get("?" + variables[i]);
						result[i] = (node==null ? null : node.toString());
					} // end for
					results.add(result);
				} // end while
		    } finally { 
		    	qe.close();
		    } // end try
		return new ArrayList<String[]>(results);
	}
	
}
