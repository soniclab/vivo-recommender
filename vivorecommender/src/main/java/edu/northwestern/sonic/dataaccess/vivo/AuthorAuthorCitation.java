package edu.northwestern.sonic.dataaccess.vivo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.TreeSet;

import edu.northwestern.sonic.dataaccess.medline.ArticleArticleCitation;
import edu.northwestern.sonic.util.ArraysUtil;
import edu.northwestern.sonic.util.StringUtil;
/**
 * Wrapper for author-author citation;
 * combines citation data from PubMed with authorship data from VIVO.
 *
 * @author Hugh
 * 
 */
public class AuthorAuthorCitation extends VivoSparqlService {

	private final ArticleArticleCitation medline = new ArticleArticleCitation();

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
	 * @return list of URIs of authors of articles
	 * @throws URISyntaxException 
	 */
	public URI[] getAuthors(int[] pubMedIds) throws URISyntaxException { 
		TreeSet<URI> returnValue = new TreeSet<URI>();
		for(int pubMedId : pubMedIds)
			returnValue.addAll(getAuthorsSet(pubMedId));
		return returnValue.toArray(new URI[0]);	
	}
		
	/**
	 * author-author citation;
	 * get the authors cited by an author
	 * A -> X, given the left-hand side, find the right-hand side 
	 * @param uri URI of an author 
	 * @return list of URIs of authors cited by an author
	 * @throws URISyntaxException 
	 */
	public URI[] getAuthorAuthorCitationFrom(URI author) throws URISyntaxException { 
		return getAuthors(medline.getArticleArticleCitationFrom(getArticles(author)));	
	}

	/**
	 * author-author citation;
	 * get the authors that cite an author
	 * X -> A, given the right-hand side, find the left-hand side 
	 * @param uri URI of an author 
	 * @return list of URIs of authors that cite by an author
	 * @throws URISyntaxException 
	 */
	public URI[] getAuthorAuthorCitationTo(URI author) throws URISyntaxException { 
		return getAuthors(medline.getArticleArticleCitationTo(getArticles(author)));	
	}

	/**
	 * author-author cocitation;
	 * get the authors of a list of articles
	 * @param uri URI of an author 
	 * @return list of URIs of authors cocited with author
	 * @throws URISyntaxException 
	 */
	public URI[] getAuthorAuthorCoCitation(URI author) throws URISyntaxException { 
		return getAuthors(medline.getArticleArticleCoCitation(getArticles(author)));	
	}

}
