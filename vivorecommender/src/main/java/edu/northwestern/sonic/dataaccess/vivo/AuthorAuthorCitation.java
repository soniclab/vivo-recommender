package edu.northwestern.sonic.dataaccess.vivo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;

import edu.northwestern.sonic.dataaccess.medline.ArticleArticleCitation;
import edu.northwestern.sonic.util.ArraysUtil;
/**
 * Wrapper for author-author citation;
 * combines citation data from PubMed with authorship data from VIVO.
 *
 * @author Hugh
 * 
 */
public class AuthorAuthorCitation extends Authorship {

	private final ArticleArticleCitation medline = new ArticleArticleCitation();

	/**
	 * article author citation;
	 * get the articles that cite an author
	 * 
	 * @param URI an author 
	 * @return list of pubmed ids of papers by that cite a particular author
	 */
	public Set<Integer> getArticleAuthorCitationTo(URI author) {
		return medline.getArticleArticleCitationToSet(getArticlesSet(author));
	}
	
	/**
	 * author-author citation;
	 * get the authors cited by an author
	 * A -> X, given the left-hand side, find the right-hand side 
	 * @param uri URI of an author 
	 * @return list of URIs of authors cited by an author
	 * @throws URISyntaxException 
	 */
	public URI[] getAuthorAuthorCitationFrom(URI author) { 
		return getAuthors(medline.getArticleArticleCitationFrom(getArticles(author)));	
	}

	/**
	 * author-author citation;
	 * get the authors cited by an author
	 * A -> X, given the left-hand side, find the right-hand side 
	 * @param uri URI of an author 
	 * @return list of URIs of authors cited by an author
	 * @throws URISyntaxException 
	 */
	public Set<URI> getAuthorAuthorCitationFromSet(URI author) { 
		return getAuthorsSet(medline.getArticleArticleCitationFrom(getArticles(author)));	
	}

	/**
	 * author-author citation;
	 * get the authors that cite an author
	 * X -> A, given the right-hand side, find the left-hand side 
	 * @param uri URI of an author 
	 * @return list of URIs of authors that cite by an author
	 * @throws URISyntaxException 
	 */
	public URI[] getAuthorAuthorCitationTo(URI author) { 
		return getAuthors(medline.getArticleArticleCitationTo(getArticles(author)));	
	}

	/**
	 * author-author citation;
	 * get the articles by one author that cite another author
	 * @param authorFrom URI of an author, the citing author 
	 * @param articlesTo set of PubMed ids of articles by the cited author 
	 * @return set of PubMed ids of articles by authorFrom that cite articlesTo
	 */
	public Set<Integer> getAuthorAuthorCitation(URI authorFrom, Set<Integer> articlesTo) {
		Set<Integer> returnValue = medline.getArticleArticleCitationToSet(articlesTo);
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
	public Set<Integer> getAuthorAuthorCitation(URI authorFrom, URI authorTo) {
		return getAuthorAuthorCitation(authorFrom, getArticlesSet(authorTo));	
	}

	/**
	 * author-author citation;
	 * get the authors that cite an author
	 * X -> A, given the right-hand side, find the left-hand side 
	 * @param uri URI of an author 
	 * @return list of URIs of authors that cite by an author
	 * @throws URISyntaxException 
	 */
	public Set<URI> getAuthorAuthorCitationToSet(URI author) { 
		return getAuthorsSet(medline.getArticleArticleCitationTo(getArticles(author)));	
	}

	/**
	 * author-author cocitation;
	 * get the authors of a list of articles
	 * @param uri URI of an author 
	 * @return array of URIs of authors cocited with author
	 * @throws URISyntaxException 
	 */
	public URI[] getAuthorAuthorCoCitation(URI author) { 
		return getAuthors(medline.getArticleArticleCoCitation(getArticles(author)));	
	}

	/**
	 * author-author cocitation;
	 * get the authors of a list of articles
	 * @param uri URI of an author 
	 * @return list of URIs of authors cocited with author
	 * @throws URISyntaxException 
	 */
	public Set<URI> getAuthorAuthorCoCitationSet(URI author) { 
		return getAuthorsSet(medline.getArticleArticleCoCitation(getArticles(author)));	
	}

	/**
	 * Hirsh index; auxiliary function
	 * 
	 * @param citations an array of citation counts for an author
	 * @return h-index
	 */
	private int getHIndex(int[] citations) {
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
	 * PNAS 102 (46): 16569–16572;
	 * arXiv:physics/0508025;
	 * Bibcode 2005PNAS..10216569H;
	 * doi:10.1073/pnas.0507655102;
	 * PMC 1283832;
	 * PMID 16275915
	 * 
	 * @param author URI of an author
	 * @return h-index
	 */
	public int getHIndex(URI author) {
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
	public int getHIndex(URI author, String keyword) {
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
			return 0; // no citations from qualified articles to a qualified article
		return getHIndex(ArraysUtil.toArrayInt(citations));
	}
	
}
