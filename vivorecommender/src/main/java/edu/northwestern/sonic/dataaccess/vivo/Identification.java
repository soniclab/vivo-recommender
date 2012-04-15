package edu.northwestern.sonic.dataaccess.vivo;

import java.net.URI;
import java.util.Set;

public class Identification extends VivoSparqlService {

	private String subjectFilter(String subject) {
		StringBuffer returnValue = new StringBuffer("filter regex(?subject, \"");
		returnValue.append(subject);
		returnValue.append("\",\"i\")");
		return returnValue.toString();
	}

	/**
	 * @param subject a research area
	 * @return set of URIs of qualified experts
	 */
	public Set<URI> identifyExpertsByResearchArea(String subject) {
		StringBuffer query = new StringBuffer("?X vivo:hasResearchArea ?ResearchArea .\n");
		query.append("?ResearchArea rdfs:label ?subject .\n");
		query.append(subjectFilter(subject));
		return getDistinctSortedURIs(query.toString());
	}
	
	/**
	 * @param subject an article free text keyword
	 * @return set of URIs of qualified experts
	 */
	public Set<URI> identifyExpertsByKeyword(String subject) {
		StringBuffer query = new StringBuffer("?X vivo:authorInAuthorship ?pub .\n");
		query.append("?pub vivo:linkedInformationResource ?keyword .\n");
		query.append("?keyword vivo:freetextKeyword ?subject .\n");
		query.append(subjectFilter(subject));
		return getDistinctSortedURIs(query.toString());
	}
	
	/**
	 * @param subject an article subject area
	 * @return set of URIs of qualified experts
	 */
	public Set<URI> identifyExpertsBySubjectArea(String subject) {
		StringBuffer query = new StringBuffer("?X vivo:authorInAuthorship ?pub .\n");
		query.append("?pub vivo:hasSubjectArea ?area .\n");
		query.append("?area rdfs:label ?subject .\n");
		query.append(subjectFilter(subject));
		return getDistinctSortedURIs(query.toString());
	}
	
}
