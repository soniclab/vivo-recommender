package edu.northwestern.sonic.test;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.BeforeClass;
import edu.northwestern.sonic.dataaccess.vivo.VivoSparqlService;

/**
 * Live Florida VIVO functional test suite
 * 
 * @author Hugh
 * 
 */

public class FloridaFunctionalTests extends FunctionalTests {
	
	@BeforeClass
	public static void beforeClass() throws MalformedURLException {
		VivoSparqlService.setUrl(new URL("http://sparql.vivo.ufl.edu/sparql"));
	}
	
}