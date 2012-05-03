package edu.northwestern.sonic.dataaccess.vivo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;

import edu.northwestern.sonic.dataaccess.medline.ArticleArticleCitation;
import edu.northwestern.sonic.util.ArraysUtil;
/**
 * Wrapper for author-author citation;
 * combines citation data from PubMed with authorship data from VIVO.
 * Methods returning sets do the real work, methods returning arrays are for convenience
 *
 * @author Hugh
 * 
 */
public class AuthorAuthorCitation extends Authorship {

	private final ArticleArticleCitation medline = new ArticleArticleCitation();

	/**
	 * author-author citation;
	 * get the authors cited by an author;
	 * A -> X, given the left-hand side, find the right-hand side 
	 * @param uri URI of an author 
	 * @return set of URIs of authors cited by an author, empty if no citations
	 */
	public NavigableSet<URI> getAuthorAuthorCitationFromSet(final URI author) { 
		NavigableSet<URI> returnValue = new TreeSet<URI>();
		int[] articles = getArticles(author);
		if(articles.length == 0) // no articles with PubMed identifiers?
			return returnValue; // no citations
		int[] articlesCitedFrom = medline.getArticleArticleCitationFrom(articles);
		if(articlesCitedFrom.length == 0) // no citations in the PubMed articles?
			return returnValue; // no citations
		returnValue = getAuthorsSet(articlesCitedFrom);	
		return returnValue;	
	}

	/**
	 * author-author citation;
	 * get the authors cited by an author;
	 * A -> X, given the left-hand side, find the right-hand side 
	 * @param uri URI of an author 
	 * @return array of URIs of authors cited by an author, empty if no citations
	 */
	public URI[] getAuthorAuthorCitationFrom(final URI author) { 
		return getAuthorAuthorCitationFromSet(author).toArray(new URI[0]);
	}

	/**
	 * author-author citation;
	 * get the authors that cite an author;
	 * X -> A, given the right-hand side, find the left-hand side 
	 * @param uri URI of an author 
	 * @return set of URIs of authors that cite an author, empty if no citations
	 */
	public NavigableSet<URI> getAuthorAuthorCitationToSet(final URI author) { 
		NavigableSet<URI> returnValue = new TreeSet<URI>();
		int[] articles = getArticles(author);
		if(articles.length==0) // no articles with PubMed identifiers?
			return returnValue; // no citations
		int[] articlesCitedTo = medline.getArticleArticleCitationTo(articles);
		if(articlesCitedTo.length == 0) // no citations to the PubMed articles?
			return returnValue; // no citations
		returnValue = getAuthorsSet(articlesCitedTo);	
		return returnValue;	
	}

	/**
	 * author-author citation;
	 * get the authors that cite an author;
	 * X -> A, given the right-hand side, find the left-hand side 
	 * @param uri URI of an author 
	 * @return array of URIs of authors that cite an author, empty if no citations
	 */
	public URI[] getAuthorAuthorCitationTo(final URI author) { 
		return getAuthorAuthorCitationToSet(author).toArray(new URI[0]);
	}

	/**
	 * author-author citation;
	 * get the articles by one author that cite another author
	 * @param authorFrom URI of an author, the citing author 
	 * @param articlesTo set of PubMed ids of articles by the cited author 
	 * @return set of PubMed ids of articles by authorFrom that cite articlesTo
	 */
	public NavigableSet<Integer> getAuthorAuthorCitation(final URI authorFrom, final Set<Integer> articlesTo) {
		NavigableSet<Integer> authorFromArticles = getArticlesSet(authorFrom);
		if(authorFromArticles.isEmpty()) // no articles by authorFrom
			return authorFromArticles;
		NavigableSet<Integer> returnValue = medline.getArticleArticleCitationToSet(articlesTo);
		if(returnValue.isEmpty()) // no articles that cite any articles in the articleTo list
			return returnValue;
		returnValue.retainAll(getArticlesSet(authorFrom));
		return returnValue;	
	}

	/**
	 * author-author citation;
	 * get the articles by one author that cite another author
	 * @param authorFrom URI of an author, the citing author 
	 * @param authorTo URI of an author, the cited author 
	 * @return set of PubMed ids of articles by authorFrom that cite authorTo
	 */
	public NavigableSet<Integer> getAuthorAuthorCitation(final URI authorFrom, final URI authorTo) {
		NavigableSet<Integer> returnValue = new TreeSet<Integer>();
		NavigableSet<Integer> authorToArticles = getArticlesSet(authorTo);
		if(authorToArticles.isEmpty()) // no articles by authorTo
			return returnValue; // no citations
		return getAuthorAuthorCitation(authorFrom, authorToArticles);	
	}

	/**
	 * author-author co-citation;
	 * get the authors of a list of articles
	 * @param uri URI of an author 
	 * @return set of URIs of authors co-cited with author, empty if no citations
	 * @throws URISyntaxException 
	 */
	public NavigableSet<URI> getAuthorAuthorCoCitationSet(final URI author) { 
		NavigableSet<URI> returnValue = new TreeSet<URI>();
		int[] articles = getArticles(author);
		if(articles.length==0) // no articles with PubMed identifiers?
			return returnValue; // no citations
		returnValue = getAuthorsSet(medline.getArticleArticleCoCitation(articles));	
		return returnValue;	
	}

	/**
	 * author-author co-citation;
	 * get the authors of a list of articles
	 * @param uri URI of an author 
	 * @return array of URIs of authors co-cited with author
	 * @throws URISyntaxException 
	 */
	public URI[] getAuthorAuthorCoCitation(final URI author) { 
		return getAuthorAuthorCoCitationSet(author).toArray(new URI[0]);
	}

	/**
	 * Hirsh index; auxiliary function
	 * 
	 * @param citations an array of citation counts for an author
	 * @return h-index
	 */
	private int getHIndex(final int[] citations) {
		Arrays.sort(citations); // ascending
		ArrayUtils.reverse(citations); // descending
		if(citations[0] == 0)
			return 0; // no citations
		for(int i = 0; i < citations.length; i++) {
			if(citations[i] <= i)
				return i; // remaining papers have no more than citations[i] citations each
		}
		return citations.length; // all highly cited articles
	}
	
	/**
	 * Hirsh index;
	 * "A scientist has index h if h of his/her N papers have at least h citations each,
	 * and the other (N - h) papers have no more than h citations each."
	 * Hirsch, J. E. (15 November 2005). "An index to quantify an individual's scientific research output";
	 * PNAS 102 (46): 16569ñ16572;
	 * arXiv:physics/0508025;
	 * Bibcode 2005PNAS..10216569H;
	 * doi:10.1073/pnas.0507655102;
	 * PMC 1283832;
	 * PMID 16275915
	 * 
	 * @param author URI of an author
	 * @return h-index
	 */
	public int getHIndex(final URI author) {
		// array of article PubMed identifiers
		int[] articles = getArticles(author);
		if(articles.length == 0)
			return 0; // no articles
		int[] citations = new int[articles.length]; // array of citation counts
		for(int i = 0; i < articles.length; i++)
			citations[i] = medline.getArticleArticleCitationTo(articles[i]).length;
		return getHIndex(citations);
	}

	/**
	 * Hirsh index; qualify the citing as well as the cited papers by a concept
	 * 
	 * @param author URI of an author
	 * @return h-index
	 */
	public int getHIndex(final URI author, final String keyword) {
		Set<URI> articles = getArticles(author, keyword);
		if(articles.isEmpty())
			return 0; // no qualified articles
		Set<Integer> pubMedIds = getArticles(articles);
		if(pubMedIds.isEmpty())
			return 0; // no qualified articles with PubMed identifiers
		Set<Integer> citations = new TreeSet<Integer>(); // array of citation counts
		for(Integer pubMedId : pubMedIds)
			citations.add(medline.getArticleArticleCitationTo(pubMedId).length);
		if(citations.isEmpty())
			return 0; // no citations to a qualified article
		return getHIndex(ArraysUtil.toArrayInt(citations));
	}
	
}
