package edu.northwestern.sonic.dataaccess.vivo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.TreeSet;

import edu.northwestern.sonic.util.ArraysUtil;
import edu.northwestern.sonic.util.StringUtil;
/**
 * Authorship and co-authorship accessors;
 * combines citation data from PubMed with authorship data from VIVO.
 *
 * @author Hugh
 * 
 */
public class Authorship extends Article {
	
	// Authorship

	/**
	 * authorship;
	 * get the PubMed identifiers of VIVO articles by an author
	 * @param URI an author 
	 * @return list of pubmed ids of papers by a particular author
	 */
	public TreeSet<Integer> getArticlesSet(URI author) {
		final String whereClause = 
			StringUtil.wrap(author) + " vivo:authorInAuthorship ?cn ." + "\n" +
			"?cn vivo:linkedInformationResource ?pub ." + "\n" +
			"?pub bibo:pmid ?X .";
		return getDistinctSortedIntegers(whereClause);
	}
	
	/**
	 * authorship;
	 * get the PubMed identifiers of VIVO articles by an author
	 * @param URI an author 
	 * @return list of pubmed ids of papers by a particular author
	 */
	public int[] getArticles(URI author) {
		return ArraysUtil.toArrayInt(getArticlesSet(author));
	}
	
	/**
	 * authorship;
	 * get the set of authors by an article's pmid
	 * @param pubMedId an article 
	 * @return set of URIs of authors of a particular paper
	 * @throws URISyntaxException 
	 */
	private Set<URI> getAuthorsSet(int pubMedId) { 
		final String whereClause =
			"?X vivo:authorInAuthorship ?cn ." + "\n" +
			"?cn vivo:linkedInformationResource ?pub ." + "\n" +
			"?pub bibo:pmid '" + pubMedId + "' .";
		return getDistinctSortedURIs(whereClause);
	}
	
	/**
	 * authorship;
	 * get the authors by an article's pmid
	 * @param pubMedId an article 
	 * @return list of URIs of authors of a particular paper
	 * @throws URISyntaxException 
	 */
	public URI[] getAuthors(int pubMedId) { 
		return getAuthorsSet(pubMedId).toArray(new URI[0]);
	}
		
	/**
	 * authorship;
	 * get the authors of a list of articles
	 * @param pubMedIds an article 
	 * @return set of URIs of authors of articles
	 * @throws URISyntaxException 
	 */
	public Set<URI> getAuthorsSet(int[] pubMedIds) { 
		TreeSet<URI> returnValue = new TreeSet<URI>();
		for(int pubMedId : pubMedIds)
			returnValue.addAll(getAuthorsSet(pubMedId));
		return returnValue;	
	}
		
	/**
	 * authorship;
	 * get the authors of a list of articles
	 * @param pubMedIds an article 
	 * @return array of URIs of authors of articles
	 * @throws URISyntaxException 
	 */
	public URI[] getAuthors(int[] pubMedIds) { 
		return getAuthorsSet(pubMedIds).toArray(new URI[0]);	
	}
		
	/**
	 * authorship qualified by a concept;
	 * get the VIVO URIs of articles by an author with a keyword
	 * @param URI an author 
	 * @param keyword a concept
	 * @return set of pubmed ids of papers by a particular author
	 * @throws URISyntaxException 
	 */
	public Set<URI> getArticles(URI author, String keyword) {
		StringBuffer whereClause = new StringBuffer(StringUtil.wrap(author));
		whereClause.append("vivo:authorInAuthorship ?cn .\n");
		whereClause.append("?cn vivo:linkedInformationResource ?X .\n");
		whereClause.append("?X vivo:freetextKeyword ?keyword .\n");
		whereClause.append("FILTER (regex(?keyword, \"");
		whereClause.append(keyword);
		whereClause.append("\",\"i\"))");
		return getDistinctSortedURIs(whereClause.toString());
	}
	
	// Co-authorship
	
	/**
	 * get the co-authors of an author
	 * @param author the VIVO URI of an author
	 * @return the set of URIs of co-authors of author
	 */
	public Set<URI> getCoAuthors(URI author) {
		StringBuffer whereClause = new StringBuffer("<" + author.toString() + "> vivo:authorInAuthorship ?cn .\n");
		whereClause.append("?cn vivo:linkedInformationResource ?pub .\n");
		whereClause.append("?pub vivo:informationResourceInAuthorship ?cn2 .\n");
		whereClause.append("?cn2 vivo:linkedAuthor ?X .\n");
		whereClause.append("FILTER (<" + author.toString() + "> != ?X)");
		return getDistinctSortedURIs(whereClause.toString());
	}

}
