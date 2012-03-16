package edu.northwestern.sonic.sparql.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.northwestern.sonic.sparql.SparqlEngine;

public class SparqlEngineTest {

	static SparqlEngine sparqlEngine;
	private final int expectedCount = 34;
	
	@Before
	public void setUp() throws Exception {
		sparqlEngine = new SparqlEngine();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIdentifyExperts() {
		assertEquals("Number of experts", expectedCount,sparqlEngine.identifyExperts("BRAIN").size());
	}

}
