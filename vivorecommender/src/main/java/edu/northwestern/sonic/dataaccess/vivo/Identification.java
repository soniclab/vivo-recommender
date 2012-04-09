package edu.northwestern.sonic.dataaccess.vivo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

public class Identification extends VivoSparqlService {

	public Set<URI> identifyExpertsByResearchArea(String subject) throws URISyntaxException {
		StringBuffer query = new StringBuffer("?X vivo:hasResearchArea ?ResearchArea .\n");
		query.append("?ResearchArea rdfs:label ?subject .\n");
		query.append("filter regex(?subject, \"" + subject + "\",\"i\")");
		return getDistinctSortedURIs(query.toString());
	}
	
	public Set<URI> identifyExpertsByKeyword(String subject) throws URISyntaxException {
		StringBuffer query = new StringBuffer("?X vivo:authorInAuthorship ?pub .\n");
		query.append("?pub vivo:linkedInformationResource ?keyword .\n");
		query.append("?keyword vivo:freetextKeyword ?subject .\n");
		query.append("filter regex(?subject, \"" + subject + "\",\"i\")");
		return getDistinctSortedURIs(query.toString());
	}
	
	public Set<URI> identifyExpertsBySubjectArea(String subject) throws URISyntaxException {
		StringBuffer query = new StringBuffer("?X vivo:authorInAuthorship ?pub .\n");
		query.append("?pub vivo:hasSubjectArea ?area .\n");
		query.append("?area rdfs:label ?subject .\n");
		query.append("filter regex(?subject, \"" + subject + "\",\"i\")");
		return getDistinctSortedURIs(query.toString());
	}
	
}
