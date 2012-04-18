package edu.northwestern.sonic.dataaccess.vivo.test;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import org.junit.Test;

import edu.northwestern.sonic.dataaccess.vivo.Article;
/**
 * 
 * @author Hugh
 *
 */
public class ArticleTest {
	
	private final static Article article = new Article();
	
	@Test
	public void testGetArticle() throws URISyntaxException {
		final URI articleUri = new URI("http://vivo.ufl.edu/individual/n368979405");
		final int articlePubMedId = 12790603;
		assertEquals("article not found by URI", 0, article.getArticle(new URI("http://www.example.com/")).intValue());
		assertEquals("article not found by PubMed id", null, article.getArticle(0));
		assertEquals("article by VIVO URI", articlePubMedId, article.getArticle(articleUri).intValue());
		assertEquals("article by PubMed id", articleUri, article.getArticle(articlePubMedId));
	}

}
