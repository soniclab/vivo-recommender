/**
 * Wrapper for citation data
 * served by the University of Iowa Medline endpoint
 */
package edu.northwestern.sonic.medline;

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;


/**
 * @author Hugh
 *
 */
public class CitationServer {
	
	/**
	 * get the citations from a pubmed article
	 * @param pmid, a pubmed id
	 * @return list of pubmed ids of papers cited by pmid
	 */
	public List<Integer> getCitations(int pmid) {
		final String remoteQueryString = 
			"PREFIX ml: <http://research.icts.uiowa.edu/ontology/medline.rdf#> " + 
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
			"SELECT DISTINCT ?X " +
			"WHERE { " +
				"?a ml:article_pmid '" + pmid + "'^^xsd:int . " +
				"?cc ml:comments_corrections_pmid ?a . " +
				"?cc ml:comments_corrections_ref_type 'Cites' . " +
				"?cc ml:comments_corrections_ref_pmid ?X } " +
			"ORDER BY ?X";
		final String service = "http://research.icts.uiowa.edu/MedlineEndpoint/sparql";
		Query query = QueryFactory.create(remoteQueryString);
		QueryExecution  queryExecution = QueryExecutionFactory.sparqlService(service, query);
		ResultSet resultSet = queryExecution.execSelect();
		List<Integer> returnValue = new ArrayList<Integer>();
		while(resultSet.hasNext()) {
			QuerySolution querySolution = resultSet.nextSolution();
			Literal literal = querySolution.getLiteral("X");
			returnValue.add(new Integer(literal.getInt()));
	    }
		return returnValue;
	}
}
