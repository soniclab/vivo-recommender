package edu.northwestern.sonic.sparql;

import java.util.List;

import org.apache.log4j.Logger;

public class SparqlEngine {

	Logger log = Logger.getLogger(SparqlEngine.class);
	
	private ReasonerEngine reasonerEngine = null;

	public SparqlEngine(){
		reasonerEngine = ReasonerEngine.getInstance();
	}
	
	public List<String> identifyExperts(String subject){
		log.info("IN identification (single keyword)");
		
		StringBuffer query = new StringBuffer("SELECT DISTINCT ?X WHERE " + "{");
		query.append(" ?X vivo:hasResearchArea ?ResearchArea .");
		query.append(" ?ResearchArea rdfs:label ?area .");
		query.append(" filter regex(?area, \"" + subject + "\",\"i\") .");
		query.append(" optional { ");
		query.append(" ?X vivo:authorInAuthorship ?Y .");
		query.append(" ?Y vivo:linkedInformationResource ?Z .");
		query.append(" ?Z vivo:freetextKeyword ?keyword .");
		query.append(" filter regex(?keyword, \"" + subject + "\",\"i\") .");
	    query.append(" }} ");
		 
		return reasonerEngine.getStrings(query.toString());
	}
	
}
