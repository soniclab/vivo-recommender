package edu.northwestern.sonic.dataaccess.vivo.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import edu.northwestern.sonic.dataaccess.test.Katritzky;
import edu.northwestern.sonic.dataaccess.vivo.Authorship;
import edu.northwestern.sonic.dataaccess.vivo.Researcher;
import edu.northwestern.sonic.model.User;

/*
 * author: Anup
 */

public class AuthorshipTest {
	private static final Researcher researcher = new Researcher();
	private static final Authorship authorship = new Authorship();

	@Test
	public void testGetArticles() {
		int[] actual = authorship.getArticles(Katritzky.VIVO_URI);
		assertEquals("number of articles", Katritzky.PMIDS.length, actual.length);
		assertArrayEquals("articles", Katritzky.PMIDS, actual);
	}

	@Test
	public void testGetArticlesQualified() throws URISyntaxException {
		// Katritzky articles with keyword "Organic Chemistry"
		final Set<URI> expected = new TreeSet<URI>(Arrays.asList(new URI[]{
			new URI("http://vivo.ufl.edu/individual/n1275465881"), 
			new URI("http://vivo.ufl.edu/individual/n1411613245"),
			new URI("http://vivo.ufl.edu/individual/n1425383161"),
			new URI("http://vivo.ufl.edu/individual/n1551845005"),
			new URI("http://vivo.ufl.edu/individual/n1568507043"), 
			new URI("http://vivo.ufl.edu/individual/n1715751949"), 
			new URI("http://vivo.ufl.edu/individual/n533829642")
		})); 
		Set<URI> actual = authorship.getArticles(Katritzky.VIVO_URI, "organic");
		assertEquals("number of articles", expected.size(), actual.size());
		assertEquals("articles", expected, actual);
	}

	@Test
	public void testGetAuthors() throws URISyntaxException {
		final URI[] expected = {
			new URI("http://vivo.ufl.edu/individual/n1083315624"),	// Tatham
			new URI("http://vivo.ufl.edu/individual/n139140470"),	// Lomaka
			new URI("http://vivo.ufl.edu/individual/n1521641392"),	// Fara
			new URI("http://vivo.ufl.edu/individual/n1855538095"),	// Karelson
			new URI("http://vivo.ufl.edu/individual/n1856088751"),	// Petrukhin
			new URI("http://vivo.ufl.edu/individual/n3622"),		// Katritzky
			new URI("http://vivo.ufl.edu/individual/n753652860")	// Maran
			};
		URI[] authors = authorship.getAuthors(12470284);
		assertEquals("number of authors", expected.length, authors.length);
		assertArrayEquals("authors", expected, authors);
	}

	@Test
	public void testGetCoAuthors() throws URISyntaxException {
		final User user = researcher.getUser("stephen.grobmyer@surgery.ufl.edu");
		assertEquals("Number of CoAuthors", 20, authorship.getCoAuthors(user.getUri()).size());
	}
		
}
