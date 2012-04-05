package edu.northwestern.sonic.sparql;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.RDFNode;

import edu.northwestern.sonic.bean.PropertyBean;

public class ReasonerEngine {
	
	Logger log = Logger.getLogger(ReasonerEngine.class);
	
	private static ReasonerEngine reasonerEngine = null;
	
	private static String service = null;
	
	private static final String prefix = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
			 " PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#> " +
			 " PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#> " +
			 " PREFIX owl:   <http://www.w3.org/2002/07/owl#> " +
			 " PREFIX swrl:  <http://www.w3.org/2003/11/swrl#> " +
			 " PREFIX swrlb: <http://www.w3.org/2003/11/swrlb#> " +
			 " PREFIX vitro: <http://vitro.mannlib.cornell.edu/ns/vitro/0.7#> " +
			 " PREFIX far: <http://vitro.mannlib.cornell.edu/ns/reporting#> " +
			 " PREFIX acti: <http://vivoweb.org/ontology/activity-insight#> " +
			 " PREFIX aktp: <http://www.aktors.org/ontology/portal#> " +
			 " PREFIX bibo: <http://purl.org/ontology/bibo/> " +
			 " PREFIX cce: <http://vivoweb.org/ontology/cornell-cooperative-extension#> " +
			 " PREFIX hr: <http://vivo.cornell.edu/ns/hr/0.9/hr.owl#> " +
			 " PREFIX dcterms: <http://purl.org/dc/terms/> " +
			 " PREFIX dcelem: <http://purl.org/dc/elements/1.1/> " +
			 " PREFIX event: <http://purl.org/NET/c4dm/event.owl#> " +
			 " PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
			 " PREFIX geo: <http://aims.fao.org/aos/geopolitical.owl#> " +
			 " PREFIX mann: <http://vivo.cornell.edu/ns/mannadditions/0.1#> " +
			 " PREFIX ospcu: <http://vivoweb.org/ontology/cu-vivo-osp#> " +
			 " PREFIX pvs: <http://vivoweb.org/ontology/provenance-support#> " +
			 " PREFIX pubmed: <http://vitro.mannlib.cornell.edu/ns/pubmed#> " +
			 " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
			 " PREFIX rdfsyn: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
			 " PREFIX ero: <http://purl.obolibrary.org/obo/> " +
			 " PREFIX scires: <http://vivoweb.org/ontology/scientific-research#> " +
			 " PREFIX skos: <http://www.w3.org/2004/02/skos/core#> " +
			 " PREFIX socsci: <http://vivo.library.cornell.edu/ns/vivo/socsci/0.1#> " +
			 " PREFIX stars: <http://vitro.mannlib.cornell.edu/ns/cornell/stars/classes#> " +
			 " PREFIX wos: <http://vivo.mannlib.cornell.edu/ns/ThomsonWOS/0.1#> " +
			 " PREFIX vitro-public: <http://vitro.mannlib.cornell.edu/ns/vitro/public#> " +
			 " PREFIX vivo: <http://vivoweb.org/ontology/core#> " +
			 " PREFIX vivoc: <http://vivo.library.cornell.edu/ns/0.1#> ";

	private ReasonerEngine(){
		service = PropertyBean.getInstance().getService();
	}
	
	public static ReasonerEngine getInstance(){
		if(reasonerEngine == null){
			reasonerEngine = new ReasonerEngine();
		}
		return reasonerEngine;
	}
	
	public QueryExecution getQueryExecution(Query query){
		return QueryExecutionFactory.sparqlService(service, query);
	}
	
	/*
	 * Prefix and create query from string
	 */
	private Query getQuery(String query){
		log.info("QUERY:\n" + query);
		return QueryFactory.create(prefix + query, Syntax.syntaxARQ); // HJD 2011-02-21 for COUNT
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
	 */
	public List<String[]> getStrings(String query, String[] variables) {
		Set<String[]> results = new HashSet<String[]>(); // no dups
		Query q = getQuery(query);
		
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
		
		return new ArrayList<String[]>(results);
	}
	
	public boolean ask(String query){
		Query q = getQuery(query);
		QueryExecution qe = getQueryExecution(q);
		return qe.execAsk();
	}
	
	
}
