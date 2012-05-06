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

import edu.northwestern.sonic.util.ArraysUtil;
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

	private static String distinctQuery(final String whereClause) {
		final String DISTINCT_PREFIX =
				"SELECT DISTINCT ?X\n" +
				"WHERE {\n";
		StringBuffer returnValue = new StringBuffer(DISTINCT_PREFIX);
		returnValue.append(whereClause);
		returnValue.append("\n}");
		return returnValue.toString();
	}
	
	private static String distinctQuery(final String whereClause, final String bindings) {
		StringBuffer returnValue = new StringBuffer(distinctQuery(whereClause));
		returnValue.append("\n");
		returnValue.append(bindings);
		return returnValue.toString();
	}
	
	/**
	 * log the number of rows in a result set
	 * @param resultSet
	 */
	private void logNumberRows(ResultSet resultSet) {
		log.info("ResultSet rows = " + (resultSet.getRowNumber() - 1));		
	}

	/**
	 * get the results of a single free variable query as Integers
	 * @param whereClause
	 * @return sorted set of results as Integers, empty set if not found
	 */
	private NavigableSet<Integer> getDistinctSortedIntegers(final QueryExecution queryExecution) {
		ResultSet resultSet = queryExecution.execSelect();
		TreeSet<Integer> returnValue = new TreeSet<Integer>();
		while(resultSet.hasNext()) {
			QuerySolution querySolution = resultSet.nextSolution();
			Literal literal = querySolution.getLiteral("X");
			returnValue.add(new Integer(literal.getInt()));
	    }
		logNumberRows(resultSet);
		queryExecution.close();
		return returnValue;
	}
	
	/**
	 * get the results of a single free variable query as Integers
	 * @param whereClause
	 * @return sorted set of results as Integers, empty set if not found
	 */
	public NavigableSet<Integer> getDistinctSortedIntegers(final String whereClause, final URI[] bindings) {
		QueryExecution queryExecution = getQueryExecution(distinctQuery(whereClause, Bindings.bindings(bindings, "Y")));
		return getDistinctSortedIntegers(queryExecution);
	}
	
	/**
	 * get the results of a single free variable query as Integers
	 * @param whereClause
	 * @return sorted set of results as Integers, empty set if not found
	 */
	public NavigableSet<Integer> getDistinctSortedIntegers(final String whereClause) {
		QueryExecution queryExecution = getQueryExecution(distinctQuery(whereClause));
		return getDistinctSortedIntegers(queryExecution);
	}
	
	/**
	 * get the results of a single free variable query as URIs
	 * @param whereClause
	 * @return sorted set of results as URIs, empty set if not found
	 */
	private NavigableSet<URI> getDistinctSortedURIs(final QueryExecution  queryExecution) {
		ResultSet resultSet = queryExecution.execSelect();
		TreeSet<URI> returnValue = new TreeSet<URI>();
		while(resultSet.hasNext()) {
			QuerySolution querySolution = resultSet.nextSolution();
			Resource resource = querySolution.getResource("X");
			URI uri = UriUtil.safeUriFactory(resource.getURI()); // log parse errors
			if(uri != null)
				returnValue.add(uri);
	    }
		logNumberRows(resultSet);
		queryExecution.close();
		return returnValue;
	}

	/**
	 * get the results of a single free variable query as URIs
	 * @param whereClause
	 * @return sorted set of results as URIs, empty set if not found
	 */
	public NavigableSet<URI> getDistinctSortedURIs(final String whereClause, int[] bindings) {
		QueryExecution  queryExecution = getQueryExecution(distinctQuery(whereClause, Bindings.bindings(ArraysUtil.toString(bindings), "Y")));
		return getDistinctSortedURIs(queryExecution);
	}

	/**
	 * get the results of a single free variable query as URIs
	 * @param whereClause
	 * @return sorted set of results as URIs, empty set if not found
	 */
	public NavigableSet<URI> getDistinctSortedURIs(final String whereClause) {
		QueryExecution  queryExecution = getQueryExecution(distinctQuery(whereClause));
		return getDistinctSortedURIs(queryExecution);
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
		QueryExecution queryExecution = getQueryExecution(queryStringBuffer.toString());
		boolean returnValue = queryExecution.execAsk();
		queryExecution.close();
		return returnValue;
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
		while (resultSet.hasNext()) {
			QuerySolution querySolution = resultSet.nextSolution();
			String[] result = new String[variables.length];
			for(int i = 0; i < variables.length; i++) {
				RDFNode rdfNode = querySolution.get("?" + variables[i]);
				result[i] = (rdfNode==null ? null : rdfNode.toString());
			} // end for
			returnValue.add(result);
		} // end while
		logNumberRows(resultSet);
    	queryExecution.close();
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
