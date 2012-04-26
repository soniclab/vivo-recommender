package edu.northwestern.sonic.model;

import java.net.URI;
import java.util.NavigableSet;

import edu.northwestern.sonic.dataaccess.vivo.Authorship;

/**
 * @author Hugh
 * 2012-04-26 HJD refactor accessors for qualified authors
 *
 */
public class Identification {
	
	Authorship authorship = new Authorship();

	/**
	 * identify experts by research area of articles
	 * @param subject a research area
	 * @return set of URIs of qualified experts
	 */
	public NavigableSet<URI> identifyExpertsByResearchArea(String subject) {
		return authorship.getAuthorsByResearchArea(subject);
	}
	
	/**
	 * identify experts by free text keywords of articles
	 * @param subject an article free text keyword
	 * @return set of URIs of qualified experts
	 */
	public NavigableSet<URI> identifyExpertsByKeyword(String subject) {
		return authorship.getAuthorsByKeyword(subject);
	}
	
	/**
	 * identify experts by subject areas of articles
	 * @param subject an article subject area
	 * @return set of URIs of qualified experts
	 */
	public NavigableSet<URI> identifyExpertsBySubjectArea(String subject) {
		return authorship.getAuthorsBySubjectArea(subject);
	}
	
}
