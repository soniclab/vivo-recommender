/**
 * 
 */
package edu.northwestern.sonic.dataaccess.vivo.functional;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.northwestern.sonic.dataaccess.vivo.VivoSparqlService;


/**
 * @author Hugh
 *
 */
public class VivoSparqlServiceTest {
	private VivoSparqlService vivo = new VivoSparqlService();

	/**
	 * Test availability of VIVO SPARQL endpoint
	 * Test method for {@link edu.northwestern.sonic.dataaccess.SparqlService#ask(java.lang.String)}.
	 */
	@Test
	public void testAsk() {
		assertTrue("Available", vivo.ask("?X a vivo:FacultyMember"));
	}

}
