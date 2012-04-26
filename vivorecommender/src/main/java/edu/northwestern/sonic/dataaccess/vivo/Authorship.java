package edu.northwestern.sonic.dataaccess.vivo;

import java.net.URI;
import java.util.NavigableSet;

import edu.northwestern.sonic.dataaccess.Bindings;
import edu.northwestern.sonic.util.ArraysUtil;
import edu.northwestern.sonic.util.StringUtil;
/**
 * Authorship and co-authorship accessors;
 * combines citation data from PubMed with authorship data from VIVO.
 * Methods returning sets do the real work, methods returning arrays are for convenience
 *
 * @author Hugh
 * HJD 2012-04-26 add accessors for authors for identification
 */
public class Authorship extends Article {
	
	// Authorship

	/**
	 * authorship;
	 * get the PubMed identifiers of VIVO articles by an author
	 * @param URI an author 
	 * @return list of pubmed ids of papers by a particular author
	 */
	public NavigableSet<Integer> getArticlesSet(final URI author) {
		final StringBuffer whereClause = new StringBuffer(StringUtil.wrap(author));
		whereClause.append(" vivo:authorInAuthorship ?cn .\n");
		whereClause.append("?cn vivo:linkedInformationResource ?pub .\n");
		whereClause.append("?pub bibo:pmid ?X .");
		return getDistinctSortedIntegers(whereClause.toString());
	}
	
	/**
	 * authorship qualified by a concept;
	 * get the VIVO URIs of articles by an author with a keyword
	 * @param URI an author 
	 * @param keyword a concept
	 * @return set of pubmed ids of papers by a particular author
	 */
	public NavigableSet<URI> getArticles(final URI author, final String keyword) {
		StringBuffer whereClause = new StringBuffer(StringUtil.wrap(author));
		whereClause.append(" vivo:authorInAuthorship ?cn .\n");
		whereClause.append("?cn vivo:linkedInformationResource ?X .\n");
		whereClause.append("?X vivo:freetextKeyword ?keyword .\n");
		whereClause.append("FILTER (regex(?keyword, \"");
		whereClause.append(keyword);
		whereClause.append("\",\"i\"))");
		return getDistinctSortedURIs(whereClause.toString());
	}
	
	/**
	 * authorship;
	 * get the PubMed identifiers of VIVO articles by an author
	 * @param URI an author 
	 * @return list of pubmed ids of papers by a particular author
	 */
	public int[] getArticles(final URI author) {
		return ArraysUtil.toArrayInt(getArticlesSet(author));
	}
	
	/**
	 * authorship;
	 * get the authors of a list of articles
	 * @param pubMedIds an article 
	 * @return set of URIs of authors of articles
	 */
	public NavigableSet<URI> getAuthorsSet(final int[] pubMedIds) { 
		final StringBuffer whereClause = new StringBuffer("?X vivo:authorInAuthorship ?cn .\n");
		whereClause.append("?cn vivo:linkedInformationResource ?pub .\n");
		whereClause.append("?pub bibo:pmid ?Y .\n");
		return getDistinctSortedURIs(whereClause.toString(), Bindings.bindings(ArraysUtil.toString(pubMedIds), "Y"));
	}
		
	/**
	 * authorship;
	 * get the set of authors by an article's pmid
	 * @param pubMedId an article 
	 * @return set of URIs of authors of a particular paper
	 */
	private NavigableSet<URI> getAuthorsSet(final int pubMedId) { 
		return getAuthorsSet(new int[]{pubMedId});	
	}
	
	/**
	 * authorship;
	 * get the authors of a list of articles
	 * @param pubMedIds array of PubMed identifiers of articles 
	 * @return array of URIs of authors of articles
	 */
	public URI[] getAuthors(final int[] pubMedIds) { 
		return getAuthorsSet(pubMedIds).toArray(new URI[0]);	
	}
		
	/**
	 * authorship;
	 * get the authors of an article by an article's pmid
	 * @param pubMedId an article 
	 * @return array of URIs of authors of a particular paper
	 */
	public URI[] getAuthors(final int pubMedId) { 
		return getAuthorsSet(pubMedId).toArray(new URI[0]);
	}
		
	// Co-authorship
	
	/**
	 * get the co-authors of an author
	 * @param author the VIVO URI of an author
	 * @return the set of URIs of co-authors of author
	 */
	public NavigableSet<URI> getCoAuthors(final URI author) {
		StringBuffer whereClause = new StringBuffer("<" + author.toString() + "> vivo:authorInAuthorship ?cn .\n");
		whereClause.append("?cn vivo:linkedInformationResource ?pub .\n");
		whereClause.append("?pub vivo:informationResourceInAuthorship ?cn2 .\n");
		whereClause.append("?cn2 vivo:linkedAuthor ?X .\n");
		whereClause.append("FILTER (");
		whereClause.append(StringUtil.wrap(author.toString()));
		whereClause.append(" != ?X)");
		return getDistinctSortedURIs(whereClause.toString());
	}

	// identification phase support
	// get authors by keywords
	
	/**
	 * case-insensitive subject filter regular expression
	 * @param subject
	 * @return
	 */
	private String caseInsensitiveFilter(String subject) {
		StringBuffer returnValue = new StringBuffer("filter regex(?subject, \"");
		returnValue.append(subject);
		returnValue.append("\",\"i\")");
		return returnValue.toString();
	}
	
	/**
	 * add subject filter to query
	 * @param query
	 * @param subject
	 * @return set of URIs of qualified experts
	 */
	private NavigableSet<URI> getAuthorsBySubject(final StringBuffer query, final String subject) {
		query.append(caseInsensitiveFilter(subject));
		return getDistinctSortedURIs(query.toString());		
	}

	/**
	 * get authors of articles by research area
	 * @param subject a research area (MeSH subject heading description)
	 * @return set of URIs of qualified experts
	 */
	public NavigableSet<URI> getAuthorsByResearchArea(final String subject) {
		StringBuffer query = new StringBuffer("?X vivo:hasResearchArea ?ResearchArea .\n");
		query.append("?ResearchArea rdfs:label ?subject .\n");
		return getAuthorsBySubject(query, subject);
	}

	/**
	 * get authors of articles with associated free text keywords
	 * @param subject an article free text keyword
	 * @return set of URIs of qualified experts
	 */
	public NavigableSet<URI> getAuthorsByKeyword(final String subject) {
		StringBuffer query = new StringBuffer("?X vivo:authorInAuthorship ?pub .\n");
		query.append("?pub vivo:linkedInformationResource ?keyword .\n");
		query.append("?keyword vivo:freetextKeyword ?subject .\n");
		return getAuthorsBySubject(query, subject);
	}
	
	/**
	 * get authors of articles with associated VIVO subject areas
	 * @param subject an article subject area
	 * @return set of URIs of qualified experts
	 */
	public NavigableSet<URI> getAuthorsBySubjectArea(final String subject) {
		StringBuffer query = new StringBuffer("?X vivo:authorInAuthorship ?pub .\n");
		query.append("?pub vivo:hasSubjectArea ?area .\n");
		query.append("?area rdfs:label ?subject .\n");
		return getAuthorsBySubject(query, subject);
	}
	

}
