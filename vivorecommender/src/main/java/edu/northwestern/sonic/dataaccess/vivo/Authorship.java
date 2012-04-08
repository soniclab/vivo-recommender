package edu.northwestern.sonic.dataaccess.vivo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.TreeSet;

import edu.northwestern.sonic.util.ArraysUtil;
import edu.northwestern.sonic.util.StringUtil;
/**
 * Wrapper for authorship;
 * combines citation data from PubMed with authorship data from VIVO.
 *
 * @author Hugh
 * 
 */
public class Authorship extends VivoSparqlService {

	/**
	 * authorship;
	 * get the articles by an author
	 * @param URI an author 
	 * @return list of pubmed ids of papers by a particular author
	 */
	public int[] getArticles(URI author) {
		final String whereClause = 
			StringUtil.wrap(author) + " vivo:authorInAuthorship ?cn ." + "\n" +
			"?cn vivo:linkedInformationResource ?pub ." + "\n" +
			"?pub bibo:pmid ?X .";
		return ArraysUtil.toArrayInt(getDistinctSortedIntegers(whereClause));
	}
	
	/**
	 * authorship;
	 * get the set of authors by an article's pmid
	 * @param pubMedId an article 
	 * @return set of URIs of authors of a particular paper
	 * @throws URISyntaxException 
	 */
	private Set<URI> getAuthorsSet(int pubMedId) throws URISyntaxException { 
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
	public URI[] getAuthors(int pubMedId) throws URISyntaxException { 
		return getAuthorsSet(pubMedId).toArray(new URI[0]);
	}
		
	/**
	 * authorship;
	 * get the authors of a list of articles
	 * @param pubMedIds an article 
	 * @return set of URIs of authors of articles
	 * @throws URISyntaxException 
	 */
	public Set<URI> getAuthorsSet(int[] pubMedIds) throws URISyntaxException { 
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
	public URI[] getAuthors(int[] pubMedIds) throws URISyntaxException { 
		return getAuthorsSet(pubMedIds).toArray(new URI[0]);	
	}
		
	public Set<URI> getCoAuthors(URI expertURI) throws URISyntaxException {
		StringBuffer whereClause = new StringBuffer("<" + expertURI.toString() + "> vivo:authorInAuthorship ?cn .");
		whereClause.append("?cn vivo:linkedInformationResource ?pub .");
		whereClause.append("?pub vivo:informationResourceInAuthorship ?cn2 .");
		whereClause.append("?cn2 vivo:linkedAuthor ?X .");
		whereClause.append("FILTER (<" + expertURI.toString() + "> != ?X)");
		return getDistinctSortedURIs(whereClause.toString());
	}
	
	public Set<URI> getCoAuthors(Set<URI> uris) throws URISyntaxException {
		TreeSet<URI> returnValue = new TreeSet<URI>();
		for(URI uri : uris)
			returnValue.addAll(getCoAuthors(uri));
		return returnValue;	
	}
	
}
