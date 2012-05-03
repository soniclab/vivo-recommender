package edu.northwestern.sonic.dataaccess;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.log4j.Logger;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.northwestern.sonic.util.LogUtil;
import edu.northwestern.sonic.util.UriUtil;
/**
 * Thin wrapper for Jena SPARQL endpoints;
 * provides prefixes
 * this class depends on Jena but is relatively independent of our current application
 *
 * @author Hugh
 * 
 */
public class SparqlService {
	private static Logger log = LogUtil.log;
	private URL url;
	private String queryPrefix;
	
	/**
	 * @param url
	 * @param queryPrefix
	 */
	public SparqlService(final URL url, final List<ImmutablePair<String, URI>> queryPrefixes) {
		this.url = url;
		StringBuffer queryPrefixBuffer = new StringBuffer();
		for(ImmutablePair<String,URI> queryPrefix : queryPrefixes) {
			queryPrefixBuffer.append("PREFIX ");
			queryPrefixBuffer.append(queryPrefix.getLeft());
			queryPrefixBuffer.append(": <");
			queryPrefixBuffer.append(queryPrefix.getRight().toString());
			queryPrefixBuffer.append(">\n");
		}
		this.queryPrefix = queryPrefixBuffer.toString();
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

	private QueryExecution getQueryExecution(final Query query) {
		return QueryExecutionFactory.sparqlService(getUrl().toString(), query);
	}
	
	/**
	 * prefixing service
	 * 
	 * @param queryString without prefix
	 * @return Jena QueryExecution
	 */
	private QueryExecution getQueryExecution(final String queryString) {
		StringBuffer queryStringBuffer = new StringBuffer(getQueryPrefix());
		queryStringBuffer.append(queryString);
		String s = queryStringBuffer.toString();
		LogUtil.printStackTrace();
		log.info("QUERY:\n" + s);
		Query query = QueryFactory.create(s);
		return getQueryExecution(query);
	}

	private static String distinctQuery(final String whereClause, final String bindings) {
		final String DISTINCT_PREFIX =
				"SELECT DISTINCT ?X\n" +
				"WHERE {\n";
		StringBuffer queryStringBuffer = new StringBuffer(DISTINCT_PREFIX);
		queryStringBuffer.append(whereClause);
		queryStringBuffer.append("\n}");
		if(!bindings.isEmpty()) {
			queryStringBuffer.append(bindings);
		}
		return queryStringBuffer.toString();
	}

	/**
	 * get the results of a single free variable query as Integers
	 * @param whereClause
	 * @return sorted set of results as Integers, empty set if not found
	 */
	public NavigableSet<Integer> getDistinctSortedIntegers(final String whereClause, final String bindings) {
		QueryExecution  queryExecution = getQueryExecution(distinctQuery(whereClause, bindings));
		ResultSet resultSet = queryExecution.execSelect();
		TreeSet<Integer> returnValue = new TreeSet<Integer>();
		while(resultSet.hasNext()) {
			QuerySolution querySolution = resultSet.nextSolution();
			Literal literal = querySolution.getLiteral("X");
			returnValue.add(new Integer(literal.getInt()));
	    }
		return returnValue;
	}
	
	/**
	 * get the results of a single free variable query as Integers
	 * @param whereClause
	 * @return sorted set of results as Integers, empty set if not found
	 */
	public NavigableSet<Integer> getDistinctSortedIntegers(final String whereClause) {
		return getDistinctSortedIntegers(whereClause, "");
	}
	
	/**
	 * get the results of a single free variable query as URIs
	 * @param whereClause
	 * @return sorted set of results as URIs, empty set if not found
	 */
	public NavigableSet<URI> getDistinctSortedURIs(final String whereClause, final String bindings) {
		QueryExecution  queryExecution = getQueryExecution(distinctQuery(whereClause, bindings));
		ResultSet resultSet = queryExecution.execSelect();
		TreeSet<URI> returnValue = new TreeSet<URI>();
		while(resultSet.hasNext()) {
			QuerySolution querySolution = resultSet.nextSolution();
			Resource resource = querySolution.getResource("X");
			URI uri = UriUtil.safeUriFactory(resource.getURI()); // log parse errors
			if(uri != null)
				returnValue.add(uri);
	    }
		return returnValue;
	}

	/**
	 * get the results of a single free variable query as URIs
	 * @param whereClause
	 * @return sorted set of results as URIs, empty set if not found
	 */
	public NavigableSet<URI> getDistinctSortedURIs(final String whereClause) {
		return getDistinctSortedURIs(whereClause, "");
	}

	/**
	 * wrapper for ask clause
	 * @param askClause
	 * @return true or false
	 */
	public boolean ask(final String askClause){
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
	public List<String> getStrings(final String query) {
		List<String[]> results = getStrings(query, new String[]{"X"});
		ArrayList<String> result = new ArrayList<String>();
		for(String[] arr : results)
			result.add(arr[0]);
		return result;
	}

	/*
	 * Get a set of the results of a query as strings
	 *
	 * @param query SPARQL query as a String, minus the prefixes
	 * @param variables list of unbound variables used in the query parameter
	 * @return a set of arrays of String, each array of same length as the variables parameter
	 */
	public Set<String[]> getStringsSet(final String query, final String[] variables) {
		Set<String[]> returnValue = new HashSet<String[]>(); // no dups
	    QueryExecution queryExecution = getQueryExecution(query);
	    ResultSet resultSet = queryExecution.execSelect();
	    try {
			while (resultSet.hasNext()) {
				QuerySolution querySolution = resultSet.nextSolution();
				String[] result = new String[variables.length];
				for(int i = 0; i < variables.length; i++) {
					RDFNode rdfNode = querySolution.get("?" + variables[i]);
					result[i] = (rdfNode==null ? null : rdfNode.toString());
				} // end for
				returnValue.add(result);
			} // end while
	    } finally { 
	    	queryExecution.close();
	    } // end try
		return returnValue;
	}
	
	/*
	 * Get a list of the results of a query as strings
	 *
	 * @param query SPARQL query as a String, minus the prefixes
	 * @param variables list of unbound variables used in the query parameter
	 * @return a list of arrays of String, each array of same length as the variables parameter
	 */
	public List<String[]> getStrings(final String query, final String[] variables) {
		return new ArrayList<String[]>(getStringsSet(query, variables));
	}
	
}
