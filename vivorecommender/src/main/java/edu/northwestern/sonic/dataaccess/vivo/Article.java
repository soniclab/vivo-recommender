package edu.northwestern.sonic.dataaccess.vivo;

import java.net.URI;
import java.util.NavigableSet;
import java.util.Set;

import edu.northwestern.sonic.dataaccess.Bindings;
/**
 * Accessors for VIVO article data;
 *
 * @author Hugh
 * 
 */
public class Article extends VivoSparqlService {
	
	// VIVO article URI to PubMed identifier
	
	/**
	 * get the PubMed Ids for the VIVO articles in a list
	 * 
	 * @param articles set of URIs of VIVO articles
	 * @return set of pubmed ids of articles
	 */
	protected NavigableSet<Integer> getArticles(final URI[] articles) {
		final StringBuffer whereClause = new StringBuffer("?Y  bibo:pmid ?X .");
		return getDistinctSortedIntegers(whereClause.toString(), Bindings.bindings(articles, "Y"));
	}
	
	/**
	 * get the PubMed Ids for the VIVO articles in a list
	 * 
	 * @param articles set of URIs of VIVO articles
	 * @return set of pubmed ids of articles
	 */
	protected NavigableSet<Integer> getArticles(final Set<URI> articles) {
		return getArticles(articles.toArray(new URI[0]));
	}
	
	/**
	 * get the PubMed Id for a VIVO article
	 * 
	 * @param article URI an article in VIVO 
	 * @return the pubmed id of article, 0 if not found
	 */
	public Integer getArticle(final URI article) {
		Set<Integer> returnValue = getArticles(new URI[]{article});
		if(returnValue.isEmpty())
			return new Integer(0);
		return returnValue.iterator().next(); // first (only)
	}
	
	// PubMed identifier to VIVO article URI
	
	/**
	 * get the VIVO URI for a PubMed article
	 * 
	 * @param article a PubMed identifier
	 * @return the URI of article, null if not found
	 */
	public URI getArticle(final int article) {
		final StringBuffer whereClause = new StringBuffer("?X bibo:pmid '");
		whereClause.append(article);
		whereClause.append("'");
		Set<URI> returnValue = getDistinctSortedURIs(whereClause.toString());
		if(returnValue.isEmpty())
			return null;
		return returnValue.iterator().next(); // first (only)
	}
	
}
