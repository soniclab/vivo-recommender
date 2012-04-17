package edu.northwestern.sonic.dataaccess.medline;

import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import edu.northwestern.sonic.dataaccess.ListFilter;
import edu.northwestern.sonic.util.ArraysUtil;
/**
 * Wrapper for citation data;
 * served by the University of Iowa Medline endpoint.
 * Citation is directed; methods for fetching one side given the other.
 * TreeSets used to remove duplicates and sort results.
 * Herein citation is directed FROM citer TO cited (reverse chronological)
 *
 * @author Hugh
 *
 */
public class ArticleArticleCitation extends MedlineSparqlService {
		
	// Citation

	/**
	 * get the citations to or from a pubmed article
	 * @param queryString
	 * @return sorted set of pubmed ids
	 */
	private NavigableSet<Integer> getArticleArticleCitation(final String queryString) {
		return getDistinctSortedIntegers(queryString);
	}
	
	// FROM citation
	
	/**
	 * get the citations FROM a list of pubmed articles
	 * get the articles cited by any of a list of articles
	 * A -> X, given the left-hand side, find the right-hand side 
	 * @param pubMedId, a Pubmed id
	 * @return sorted set of pubmed ids of papers cited by pubMedId
	 */
	private NavigableSet<Integer> getArticleArticleCitationFromSet(final int[] pubMedIds) {
		final StringBuffer queryStringBuffer = new StringBuffer( 
				"?a ml:article_pmid ?Y . " +  "\n" + // source
				"?cc ml:comments_corrections_pmid ?a . " +  "\n" +
				"?cc ml:comments_corrections_ref_type 'Cites' . " +  "\n" +
				"?cc ml:comments_corrections_ref_pmid ?X . " +  "\n" //destination
				);
		queryStringBuffer.append(ListFilter.filter(pubMedIds, "Y"));
		return getArticleArticleCitation(queryStringBuffer.toString());
	}
		
	/**
	 * get the citations FROM a pubmed article
	 * get the articles cited by an article
	 * A -> X, given the left-hand side, find the right-hand side 
	 * @param pubMedId, a Pubmed id
	 * @return sorted set of pubmed ids of papers cited by pubMedId
	 */
	private NavigableSet<Integer> getArticleArticleCitationFromSet(final int pubMedId) {
		return getArticleArticleCitationFromSet(new int[]{pubMedId});
	}
		
	/**
	 * get the citations FROM a pubmed article
	 * get the set of articles cited by any of a set of articles
	 * A -> X, given the left-hand side, find the right-hand side 
	 * @param pubMedId, a Pubmed id
	 * @return sorted set of pubmed ids of papers cited by pubMedId
	 */
	private NavigableSet<Integer> getArticleArticleCitationFromSet(final Set<Integer> pubMedIds) {
		return getArticleArticleCitationFromSet(ArraysUtil.toArrayInt(pubMedIds));
	}
		
	/**
	 * get the articles cited by the articles in a list;
	 * get the articles that cite an article
	 * X -> A, given the right-hand side, find the left-hand side 
	 * @param pubMedIds, an array of pubmed ids, for example, a list of articles by one particular author
	 * @return list of pubmed ids of papers cited by the papers in pubMedIds
	 */
	public int[] getArticleArticleCitationFrom(final int[] pubMedIds) {
		return ArraysUtil.toArrayInt(getArticleArticleCitationFromSet(pubMedIds));	
	}
	
	/**
	 * get the citations FROM a pubmed article;
	 * get the articles cited by an article;
	 * A -> X, given the left-hand side, find the right-hand side 
	 * @param pubMedId, a Pubmed id
	 * @return sorted set of pubmed ids of papers cited by pubMedId
	 */
	public int[] getArticleArticleCitationFrom(final int pubMedId) {
		return ArraysUtil.toArrayInt(getArticleArticleCitationFromSet(pubMedId));
	}
	
	// TO citation
	
	/**
	 * get the articles that cite the articles in a list;
	 * get the articles that cite any of the article in a list;
	 * X -> A, given the right-hand side, find the left-hand side 
	 * @param pubMedIds, an array of pubmed ids, for example, a list of articles by one particular author
	 * @return list of pubmed ids of papers that cite the papers in pubMedIds
	 */
	private NavigableSet<Integer> getArticleArticleCitationToSet(final int[] pubMedIds) {
		final StringBuffer queryStringBuffer = new StringBuffer( 
				"?cc ml:comments_corrections_ref_pmid ?Y . " +  "\n" + //destination
				"?cc ml:comments_corrections_ref_type 'Cites' . " +  "\n" +
				"?cc ml:comments_corrections_pmid ?a . " +  "\n" +
				"?a ml:article_pmid ?X . " +  "\n" // source
				);
		queryStringBuffer.append(ListFilter.filter(pubMedIds, "Y"));
		return getArticleArticleCitation(queryStringBuffer.toString());
	}
	
	/**
	 * get the citations TO a pubmed article;
	 * get the set of articles that cite an article;
	 * X -> A, given the right-hand side, find the left-hand side 
	 * @param pubMedId, a Pubmed id
	 * @return sorted set of pubmed ids of papers that cite pubMedId
	 */
	private NavigableSet<Integer> getArticleArticleCitationToSet(final int pubMedId) {
		return getArticleArticleCitationToSet(new int[]{pubMedId});
	}
	
	/**
	 * get the citations TO a pubmed article;
	 * get the articles that cite an article;
	 * X -> A, given the right-hand side, find the left-hand side 
	 * @param pubMedId, a Pubmed id
	 * @return sorted set of pubmed ids of papers that cite pubMedId
	 */
	public int[] getArticleArticleCitationTo(final int pubMedId) {
		return ArraysUtil.toArrayInt(getArticleArticleCitationToSet(pubMedId));
	}
	
	/**
	 * get the articles that cite the articles in a list;
	 * get the articles that cite any of the article in a list;
	 * X -> A, given the right-hand side, find the left-hand side 
	 * @param pubMedIds, an array of pubmed ids, for example, a list of articles by one particular author
	 * @return list of pubmed ids of papers that cite the papers in pubMedIds
	 */
	public NavigableSet<Integer> getArticleArticleCitationToSet(final Set<Integer> pubMedIds) {
		return getArticleArticleCitationToSet(ArraysUtil.toArrayInt((pubMedIds)));	
	}
	
	/**
	 * get the articles that cite the articles in a list;
	 * get the articles that cite any of the article in a list;
	 * X -> A, given the right-hand side, find the left-hand side 
	 * @param pubMedIds, an array of pubmed ids, for example, a list of articles by one particular author
	 * @return list of pubmed ids of papers that cite the papers in pubMedIds
	 */
	public int[] getArticleArticleCitationTo(final int[] pubMedIds) {
		return ArraysUtil.toArrayInt(getArticleArticleCitationToSet(pubMedIds));	
	}
	
	// Cocitation
	// Cocitation is undirected
	
	/**
	 * get the set of articles co-cited with an article;
	 * get the articles that cite an article, then get all the articles cited by those articles
	 * @param pubMedId, a Pubmed id
	 * @return sorted set of pubmed ids of papers co-cited with pubMedId
	 */
	private NavigableSet<Integer> getArticleArticleCoCitationSet(final int pubMedId) {
		NavigableSet<Integer> returnValue = new TreeSet<Integer>();
		Set<Integer> pubMedIds = getArticleArticleCitationToSet(pubMedId);
		if(pubMedIds.isEmpty()) // no citations
			return returnValue; // empty
		returnValue = getArticleArticleCitationFromSet(pubMedIds);
		returnValue.remove(pubMedId);
		return returnValue;
	}

	/**
	 * get the articles co-cited with a list of articles;
	 * get the articles that cite an article, then get all the articles cited by those articles
	 * @param pubMedIds, a list of Pubmed ids
	 * @return sorted array of pubmed ids of papers co-cited with the pubMedIds
	 */
	public int[] getArticleArticleCoCitation(final int[] pubMedIds) {
		TreeSet<Integer> returnValue = new TreeSet<Integer>();
		for(int pubMedId : pubMedIds)
			returnValue.addAll(getArticleArticleCoCitationSet(pubMedId));
		return ArraysUtil.toArrayInt(returnValue);	
	}

	/**
	 * get the articles co-cited with an article;
	 * get the articles that cite an article, then get all the articles cited by those articles
	 * @param pubMedId, a Pubmed id
	 * @return sorted array of pubmed ids of papers co-cited with pubMedId
	 */
	public int[] getArticleArticleCoCitation(final int pubMedId) {
		return getArticleArticleCoCitation(new int[]{pubMedId});
	}
	
	// Co-citation metrics

	/**
	 * get the co-citation frequency of two articles;
	 * 
	 * after
	 * H. G. Small (1973). 
	 * "Co-citation in the scientific literature: a new measure of the relationship between two documents."
	 * Journal of the American Society for Information Science, 24, 265-269.
	 * 
	 * @param pubMedId1, pubMedId1 Pubmed ids
	 * @return cardinality of intersection of citation sets
	 */
	public int getArticleArticleCoCitationFrequency(final int pubMedId1, final int pubMedId2) {
		Set<Integer> set1 = getArticleArticleCitationToSet(pubMedId1);
		Set<Integer> set2 = getArticleArticleCitationToSet(pubMedId2);
		Set<Integer> intersection = new TreeSet<Integer>(set1);
		intersection.retainAll(set2);
		return intersection.size();
	}

	/**
	 * get the relative co-citation frequency of two articles;
	 * 1.0 = all articles that cite one cite both;
	 * ratio of size of the intersection to the size of the union;
	 * 
	 * after
	 * H. G. Small (1973). 
	 * "Co-citation in the scientific literature: a new measure of the relationship between two documents."
	 * Journal of the American Society for Information Science, 24, 265-269.
	 * 
	 * @param pubMedId1, pubMedId1 Pubmed ids
	 * @return cardinality of intersection of citation sets
	 */
	public double getArticleArticleCoCitationRelativeFrequency(final int pubMedId1, final int pubMedId2) {
		Set<Integer> set1 = getArticleArticleCitationToSet(pubMedId1);
		if(set1.isEmpty())
			return 0.0;
		Set<Integer> set2 = getArticleArticleCitationToSet(pubMedId2);
		if(set2.isEmpty())
			return 0.0;
		Set<Integer> union = new TreeSet<Integer>(set1);
		union.addAll(set2);
		Set<Integer> intersection = new TreeSet<Integer>(set1);
		intersection.retainAll(set2);
		if(intersection.isEmpty())
			return 0.0;
		return (double) intersection.size() / (double) union.size();
	}

}
