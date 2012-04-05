package edu.northwestern.sonic.dataaccess.vivo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.TreeSet;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.northwestern.sonic.dataaccess.SparqlService;
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
public class AuthorAuthorCitation {

	private SparqlService sparqlService = new SparqlService(
		"http://ciknow1.northwestern.edu:3030/UF-VIVO/query",
		"PREFIX bibo: <http://purl.org/ontology/bibo/>" + "\n" +
		"PREFIX vivo: <http://vivoweb.org/ontology/core#>" + "\n");
	
	private final static String queryPrefix =
		"SELECT DISTINCT ?X" +  "\n" +
		"WHERE {";
	
	private final static ArticleArticleCitation medline = new ArticleArticleCitation();

	/**
	 * authorship;
	 * get the articles by an author
	 * @param URI an author 
	 * @return list of pubmed ids of papers by a particular author
	 */
	public int[] getArticles(URI author) {
		final String queryString = queryPrefix + "\n" +
			StringUtil.wrap(author) + " vivo:authorInAuthorship ?cn ." + "\n" +
			"?cn vivo:linkedInformationResource ?pub ." + "\n" +
			"?pub bibo:pmid ?X ." + "\n" +
			"}";
		QueryExecution  queryExecution = sparqlService.getQueryExecution(queryString);
		ResultSet resultSet = queryExecution.execSelect();
		TreeSet<Integer> returnValue = new TreeSet<Integer>();
		while(resultSet.hasNext()) {
			QuerySolution querySolution = resultSet.nextSolution();
			Literal literal = querySolution.getLiteral("X");
			returnValue.add(new Integer(literal.getInt()));
	    }
		return ArraysUtil.toArrayInt(returnValue);	
	}
	
	/**
	 * authorship;
	 * get the set of authors by an article's pmid
	 * @param pubMedId an article 
	 * @return set of URIs of authors of a particular paper
	 * @throws URISyntaxException 
	 */
	private Set<URI> getAuthorsSet(int pubMedId) throws URISyntaxException { 
		final String queryString = queryPrefix + "\n" +
			"?X vivo:authorInAuthorship ?cn ." + "\n" +
			"?cn vivo:linkedInformationResource ?pub ." + "\n" +
			"?pub bibo:pmid '" + pubMedId + "' ." + "\n" +
			"}";
		QueryExecution  queryExecution = sparqlService.getQueryExecution(queryString);
		ResultSet resultSet = queryExecution.execSelect();
		TreeSet<URI> returnValue = new TreeSet<URI>();
		while(resultSet.hasNext()) {
			QuerySolution querySolution = resultSet.nextSolution();
			Resource resource = querySolution.getResource("X");
			returnValue.add(new URI(resource.getURI()));
	    }
		return returnValue;
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

	/**
	 * author-author cocitation;
	 * get the authors of a list of articles
	 * @param uri of an author as a String 
	 * @return list of URIs of authors cocited with author
	 * @throws URISyntaxException 
	 */
	public URI[] getAuthorAuthorCoCitation(String author) throws URISyntaxException {
		return getAuthorAuthorCoCitation(new URI(author));
	}
		
}
