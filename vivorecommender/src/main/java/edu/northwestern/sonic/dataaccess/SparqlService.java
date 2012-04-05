package edu.northwestern.sonic.dataaccess;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
/**
 * Thin wrapper for Jena SPARQL endpoints;
 * provides prefixes
 *
 * @author Hugh
 *
 */
public class SparqlService {
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
	public String getUrl() {
		return url;
	}

	/**
	 * @return the queryPrefix
	 */
	public String getQueryPrefix() {
		return queryPrefix;
	}

	private QueryExecution getQueryExecution(Query query) {
		return QueryExecutionFactory.sparqlService(url, query);
	}
	
	public QueryExecution getQueryExecution(String queryString) {
		Query query = LoggingQueryFactory.create(getQueryPrefix() + queryString);
		return getQueryExecution(query);
	}
}
