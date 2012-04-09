package edu.northwestern.sonic.dataaccess.medline.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.northwestern.sonic.dataaccess.medline.MedlineSparqlService;

/**
 * Test availability of Medline SPARQL endpoint
 *
 * @author Hugh
 *
 */
public class MedlineSparqlServiceTest {
	private MedlineSparqlService medline = new MedlineSparqlService();

	/**
	 * Test availability of Medline SPARQL endpoint
	 * Test method for {@link edu.northwestern.sonic.dataaccess.SparqlService#ask(java.lang.String)}.
	 */
	@Test
	public void testAsk() {
		assertTrue("Available", medline.ask("?X a ml:article"));
	}

}
