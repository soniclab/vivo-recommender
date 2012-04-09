package edu.northwestern.sonic.dataaccess.vivo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;

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
public class AuthorAuthorCitation extends Authorship {

	private final ArticleArticleCitation medline = new ArticleArticleCitation();

	/**
	 * authorship;
	 * get the articles by an author;
	 * relates VIVO authors to Medline PubMed identifiers;
	 * the semantic bridge between VIVO and Medline
	 * 
	 * @param URI an author 
	 * @return set of pubmed ids of papers by a particular author
	 */
	private Set<Integer> getArticlesSet(URI author) {
		final String whereClause = 
			StringUtil.wrap(author) + " vivo:authorInAuthorship ?cn ." + "\n" +
			"?cn vivo:linkedInformationResource ?pub ." + "\n" +
			"?pub bibo:pmid ?X .";
		return getDistinctSortedIntegers(whereClause);
	}
	
	/**
	 * authorship;
	 * get the articles by an author;
	 * relates VIVO authors to Medline PubMed identifiers;
	 * the semantic bridge between VIVO and Medline
	 * 
	 * @param URI an author 
	 * @return list of pubmed ids of papers by a particular author
	 */
	public int[] getArticles(URI author) {
		return ArraysUtil.toArrayInt(getArticlesSet(author));
	}
	
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
	 * get the authors cited by an author
	 * A -> X, given the left-hand side, find the right-hand side 
	 * @param uri URI of an author 
	 * @return list of URIs of authors cited by an author
	 * @throws URISyntaxException 
	 */
	public Set<URI> getAuthorAuthorCitationFromSet(URI author) throws URISyntaxException { 
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
	public URI[] getAuthorAuthorCitationTo(URI author) throws URISyntaxException { 
		return getAuthors(medline.getArticleArticleCitationTo(getArticles(author)));	
	}

	/**
	 * author-author citation;
	 * get the authors that cite an author
	 * X -> A, given the right-hand side, find the left-hand side 
	 * @param uri URI of an author 
	 * @return list of URIs of authors that cite by an author
	 * @throws URISyntaxException 
	 */
	public Set<URI> getAuthorAuthorCitationToSet(URI author) throws URISyntaxException { 
		return getAuthorsSet(medline.getArticleArticleCitationTo(getArticles(author)));	
	}

	/**
	 * author-author cocitation;
	 * get the authors of a list of articles
	 * @param uri URI of an author 
	 * @return array of URIs of authors cocited with author
	 * @throws URISyntaxException 
	 */
	public URI[] getAuthorAuthorCoCitation(URI author) throws URISyntaxException { 
		return getAuthors(medline.getArticleArticleCoCitation(getArticles(author)));	
	}

	/**
	 * author-author cocitation;
	 * get the authors of a list of articles
	 * @param uri URI of an author 
	 * @return list of URIs of authors cocited with author
	 * @throws URISyntaxException 
	 */
	public Set<URI> getAuthorAuthorCoCitationSet(URI author) throws URISyntaxException { 
		return getAuthorsSet(medline.getArticleArticleCoCitation(getArticles(author)));	
	}

	/**
	 * Hirsh index;
	 * "A scientist has index h if h of his/her N papers have at least h citations each,
	 * and the other (N - h) papers have no more than h citations each."
	 * Hirsch, J. E. (15 November 2005). "An index to quantify an individual's scientific research output";
	 * PNAS 102 (46): 16569�16572;
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
		int[] articles = getArticles(author);
		if(articles.length == 0)
			return 0; // no articles
		int[] citations = new int[articles.length];
		for(int i = 0; i < articles.length; i++)
			citations[i] = medline.getArticleArticleCitationTo(articles[i]).length;
		Arrays.sort(citations);
		ArrayUtils.reverse(citations);
		if(citations[0] == 0)
			return 0; // no citations
		for(int i = 0; i < citations.length; i++) {
			if(citations[i] <= i)
				return i;
		}
		return citations.length; // all highly cited articles
	}
	
}
