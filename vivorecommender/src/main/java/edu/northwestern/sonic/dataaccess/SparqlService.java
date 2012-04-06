package edu.northwestern.sonic.dataaccess;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
	private URL url;
	private String queryPrefix;
	
	/**
	 * @param url
	 * @param queryPrefix
	 */
	public SparqlService(URL url, String queryPrefix) {
		this.url = url;
		this.queryPrefix = queryPrefix;
	}

	/**
	 * @return the url
	 */
	private URL getUrl() {
		return url;
	}

	/**
	 * @return the queryPrefix
	 */
	private String getQueryPrefix() {
		return queryPrefix;
	}

	private QueryExecution getQueryExecution(Query query) {
		return QueryExecutionFactory.sparqlService(getUrl().toString(), query);
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
