/**
 * 
 */
package edu.northwestern.sonic.dataaccess.vivo.functional;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.northwestern.sonic.data.Katritsky;
import edu.northwestern.sonic.dataaccess.vivo.AuthorAuthorCitation;

/**
 * @author Hugh
 *
 */
public class AuthorAuthorCitationTest {
	
	private final static AuthorAuthorCitation authorAuthorCitation = new AuthorAuthorCitation();
	
	private static URI KATRITSKY_URI = null;
	
	@BeforeClass
	public static void setUp() throws URISyntaxException  {
		Arrays.sort(Katritsky.PMIDS);
		KATRITSKY_URI = new URI(Katritsky.URI_STRING);
	}

	@Test
	public void testGetPmids() throws URISyntaxException {
		int[] pmids = authorAuthorCitation.getArticles(KATRITSKY_URI);
		assertEquals("number of articles", 160, Katritsky.PMIDS.length);
		assertArrayEquals("articles", Katritsky.PMIDS, pmids);
	}

	@Test
	public void testGetAuthors() throws URISyntaxException {
		final URI[] expected = {
				new URI("http://vivo.ufl.edu/individual/n1083315624"),	// Tatham
				new URI("http://vivo.ufl.edu/individual/n139140470"),	// Lomaka
				new URI("http://vivo.ufl.edu/individual/n1521641392"),	// Fara
				new URI("http://vivo.ufl.edu/individual/n1855538095"),	// Karelson
				new URI("http://vivo.ufl.edu/individual/n1856088751"),	// Petrukhin
				new URI("http://vivo.ufl.edu/individual/n3622"),		// Katritsky
				new URI("http://vivo.ufl.edu/individual/n753652860")	// Maran
		};
		URI[] authors = authorAuthorCitation.getAuthors(12470284);
		assertEquals("number of authors", expected.length, authors.length);
		assertArrayEquals("authors", expected, authors);
	}

	@Test
	public void testGetAuthorAuthorCitationFrom() throws URISyntaxException {
		URI[] authors = authorAuthorCitation.getAuthorAuthorCitationFrom(KATRITSKY_URI);
		assertEquals("number of authors", 20, authors.length);
	}

	@Test
	public void testGetAuthorAuthorCitationTo() throws URISyntaxException {
		URI[] authors = authorAuthorCitation.getAuthorAuthorCitationTo(KATRITSKY_URI);
		assertEquals("number of authors", 10, authors.length);
	}

	@Test
	public void testGetAuthorAuthorCoCitation() throws URISyntaxException {
		final URI[] expected = {
			new URI("http://vivo.ufl.edu/individual/n1036139417"),
			new URI("http://vivo.ufl.edu/individual/n1059068413"), 
			new URI("http://vivo.ufl.edu/individual/n1086632404"),
			new URI("http://vivo.ufl.edu/individual/n1122951033"),
			new URI("http://vivo.ufl.edu/individual/n1147347079"),
			new URI("http://vivo.ufl.edu/individual/n1226654863"),
			new URI("http://vivo.ufl.edu/individual/n125164361"),
			new URI("http://vivo.ufl.edu/individual/n1286859852"),
			new URI("http://vivo.ufl.edu/individual/n134958132"),
			new URI("http://vivo.ufl.edu/individual/n1368411649"),
			new URI("http://vivo.ufl.edu/individual/n1368805336"),
			new URI("http://vivo.ufl.edu/individual/n1379783214"),
			new URI("http://vivo.ufl.edu/individual/n1417165492"),
			new URI("http://vivo.ufl.edu/individual/n1488309974"),
			new URI("http://vivo.ufl.edu/individual/n1537618697"),
			new URI("http://vivo.ufl.edu/individual/n1581616190"),
			new URI("http://vivo.ufl.edu/individual/n1596245405"),
			new URI("http://vivo.ufl.edu/individual/n1643934363"),
			new URI("http://vivo.ufl.edu/individual/n1656008197"),
			new URI("http://vivo.ufl.edu/individual/n1870095931"),
			new URI("http://vivo.ufl.edu/individual/n1984932778"),
			new URI("http://vivo.ufl.edu/individual/n2017963066"),
			new URI("http://vivo.ufl.edu/individual/n2062288258"),
			new URI("http://vivo.ufl.edu/individual/n2069577143"),
			new URI("http://vivo.ufl.edu/individual/n2110697151"),
			new URI("http://vivo.ufl.edu/individual/n2114633007"),
			new URI("http://vivo.ufl.edu/individual/n215189951"),
			new URI("http://vivo.ufl.edu/individual/n3622"),
			new URI("http://vivo.ufl.edu/individual/n384466447"),
			new URI("http://vivo.ufl.edu/individual/n568361591"),
			new URI("http://vivo.ufl.edu/individual/n596180705"),
			new URI("http://vivo.ufl.edu/individual/n598723679"),
			new URI("http://vivo.ufl.edu/individual/n662572311"),
			new URI("http://vivo.ufl.edu/individual/n679790557"),
			new URI("http://vivo.ufl.edu/individual/n745007493"),
			new URI("http://vivo.ufl.edu/individual/n777167997"),
			new URI("http://vivo.ufl.edu/individual/n794607081"),
			new URI("http://vivo.ufl.edu/individual/n813362719"),
			new URI("http://vivo.ufl.edu/individual/n820209376"),
			new URI("http://vivo.ufl.edu/individual/n824659658"),
			new URI("http://vivo.ufl.edu/individual/n87195454"),
			new URI("http://vivo.ufl.edu/individual/n874722241")
		};
		URI[] authors;
		authors = authorAuthorCitation.getAuthorAuthorCoCitation(KATRITSKY_URI);
		assertEquals("number of authors (from URI)", expected.length, authors.length);
		assertArrayEquals("authors (from URI)", expected, authors);
		authors = authorAuthorCitation.getAuthorAuthorCoCitation(Katritsky.URI_STRING);
		assertEquals("number of authors (from String)", expected.length, authors.length);
		assertArrayEquals("authors (from String)", expected, authors);
	}

}
