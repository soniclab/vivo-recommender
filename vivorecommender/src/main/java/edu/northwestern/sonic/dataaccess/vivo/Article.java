package edu.northwestern.sonic.dataaccess.vivo;

import java.net.URI;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import edu.northwestern.sonic.util.StringUtil;
/**
 * Accessors for VIVO article data;
 *
 * @author Hugh
 * 
 */
public class Article extends VivoSparqlService {
	
	/**
	 * get the PubMed Id for a VIVO article
	 * 
	 * @param article URI an article in VIVO 
	 * @return the pubmed id of article, 0 if not found
	 */
	public Integer getArticle(final URI article) {
		final StringBuffer whereClause = new StringBuffer(StringUtil.wrap(article));
		whereClause.append("  bibo:pmid ?X .");
		Set<Integer> returnValue = getDistinctSortedIntegers(whereClause.toString());
		if(returnValue.size()==0)
			return new Integer(0);
		return returnValue.iterator().next(); // first (only)
	}
	
	/**
	 * get the PubMed Ids for the VIVO articles in a list
	 * 
	 * @param articles set of URIs of VIVO articles
	 * @return set of pubmed ids of articles
	 */
	protected NavigableSet<Integer> getArticles(final Set<URI> articles) {
		NavigableSet<Integer> returnValue = new TreeSet<Integer>();
		for(URI article : articles) {
			Integer pmid = getArticle(article);
			if(pmid != null)
				returnValue.add(pmid);			
		}
		return returnValue;
	}
	
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
		if(returnValue.size()==0)
			return null;
		return returnValue.iterator().next(); // first (only)
	}
	
}
