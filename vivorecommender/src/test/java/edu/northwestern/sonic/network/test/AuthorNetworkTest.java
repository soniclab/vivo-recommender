package edu.northwestern.sonic.network.test;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import edu.northwestern.sonic.dataaccess.test.Katritzky;
import edu.northwestern.sonic.network.AuthorNetwork;
import edu.northwestern.sonic.network.Network;

/**
 * Test network factory methods 
 * @author Hugh
 *
 */
public class AuthorNetworkTest {
	
	private static final Set<URI> authors = new TreeSet<URI>(Arrays.asList(new URI[]{Katritzky.VIVO_URI})); 

	/**
	 * Test method for {@link edu.northwestern.sonic.network.AuthorNetwork#getCoAuthorship(java.util.Set)}.
	 */
	@Test
	public void testGetCoAuthorship() {
		Network actual = AuthorNetwork.coAuthorshipNetworkFactory(authors);
		assertEquals("co-authorship network vertices count", 615, actual.getVertices().size());
		assertEquals("co-authorship network edge count", 1228, actual.getEdges().size());
	}

	/**
	 * Test method for {@link edu.northwestern.sonic.network.AuthorNetwork#getCitation(java.util.Set)}.
	 */
	@Test
	public void testGetCitation() {
		Network actual = AuthorNetwork.citationNetworkFactory(authors);
		assertEquals("citation network vertices count", 29, actual.getVertices().size());
		assertEquals("citation network edge count", 29, actual.getEdges().size());
	}

	/**
	 * Test method for {@link edu.northwestern.sonic.network.AuthorNetwork#getCoCitation(java.util.Set)}.
	 */
	@Test
	public void testGetCoCitation() {
		Network actual = AuthorNetwork.coCitationNetworkFactory(authors);
		assertEquals("co-citation network vertices count", 5, actual.getVertices().size());
		assertEquals("co-citation network edge count", 8, actual.getEdges().size());
	}

}
