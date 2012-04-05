/**
 * A facade on Jena models featuring locking queries
 */
package edu.northwestern.sonic.dataaccess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hp.hpl.jena.assembler.Assembler;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.util.FileManager;

import edu.northwestern.sonic.util.LogUtil;
import edu.northwestern.sonic.util.StringUtil;

/**
 * @author Hugh
 *
 */
public class ThreadSafeModel {
	private Model model;

	/**
	 * decorate a model with thread-safe query methods
	 * @param model
	 */
	public ThreadSafeModel(Model model) {
		super();
		this.model = model;
	}

	/**
	 * create a thread-safe model from an assembler
	 * @param assemblerPath
	 */
	public ThreadSafeModel(String assemblerPath) {
		super();
		Model specification = FileManager.get().loadModel(assemblerPath);
		Resource root = specification.createResource(specification.expandPrefix("sonic:citationModel"));
		Model model = Assembler.general.openModel(root);
		this.model = model;
	}

	/**
	 * lock for read as per Jena concurrency how-to
	 * see http://incubator.apache.org/jena/documentation/notes/concurrency-howto.html
	 */
	private void lock() {
		model.enterCriticalSection(Lock.READ);
	}

	/**
	 * unlock as per Jena concurrency how-to
	 * see http://incubator.apache.org/jena/documentation/notes/concurrency-howto.html
	 */
	private void unlock() {
		model.leaveCriticalSection();
	}

	/*
	 * Prefix and create query from string
	 */
	private Query getQuery(String query){
		LogUtil.log.info("QUERY:\n" + query);
		return QueryFactory.create(getNameSpacePrefix() + query, Syntax.syntaxARQ); // HJD 2011-02-21 for COUNT
	}
	
	/**
	 * obtain a query execution environment
	 * @param query
	 * @return
	 */
	private QueryExecution getQueryExecution(Query query){
		return QueryExecutionFactory.create(query, model); 
	}
	
	/**
	 * get a local copy of the name space map from the model
	 * @return the name spaces as a SPARQL fragment of PREFIX declarations
	 */
	public String getNameSpacePrefix() {
		StringBuffer result = new StringBuffer("");
		Map<String, String> nameSpaceMap = model.getNsPrefixMap();
		Iterator<Map.Entry<String, String>> it = (Iterator<Map.Entry<String, String>>)nameSpaceMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			String key = entry.getKey();
			if(key.length()==0)
				continue;
			result.append("PREFIX " + key + ": " + StringUtil.wrap(entry.getValue()) + "\n");
		}
		return result.toString();
	}

	/*
	 * Get a list of the results of a query as strings
	 * for simple queries
	 * by convention our free variable is X
	 * @param query SPARQL query as a String
	 * @return a list of the results of a query as strings
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
	 */
	public List<String[]> getStrings(String query, String[] variables) {
		Set<String[]> results = new HashSet<String[]>(); // no dups
		Query q = getQuery(query);
		lock();
		try {
		    QueryExecution qe = getQueryExecution(q);
		    ResultSet rs = qe.execSelect();
		    try {
				while (rs.hasNext()) {
					QuerySolution sol = rs.nextSolution();
					String[] result = new String[variables.length];
					for(int i=0; i < variables.length; i++) {
						RDFNode node = sol.get("?" + variables[i]);
						result[i] = (node==null ? null : node.toString());
					}// end for
					results.add(result);
				} // end while
		    } finally { 
		    	qe.close();
		    } // end inner try
		} finally {
			unlock();
		} // end outer try
		return new ArrayList<String[]>(results);
	}

}
