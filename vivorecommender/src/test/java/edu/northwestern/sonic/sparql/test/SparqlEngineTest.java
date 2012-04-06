package edu.northwestern.sonic.sparql.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.northwestern.sonic.dataaccess.vivo.SparqlEngine;
import edu.northwestern.sonic.model.User;

/*
 * author: Anup
 */

public class SparqlEngineTest {

	static SparqlEngine sparqlEngine;
	private final int expectedCount = 18;
	private final int expectedCountByKeyword = 2247;
	private final int expectedCountBySubjectArea = 0;
	private final int expectedCoauthors = 20;
	private final String emailid = "mconlon@ufl.edu";
	private final String uri = "http://vivo.ufl.edu/individual/n25562";
	private final String name = "Conlon, Michael";
	private final String department = "Health Outcomes and Policy";
	private final String departmentURI = "http://vivo.ufl.edu/individual/n183020";
	
	@Before
	public void setUp() throws Exception {
		sparqlEngine = new SparqlEngine();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIdentifyExperts() {
		assertEquals("Number of experts", expectedCount,sparqlEngine.identifyExpertsByResearchArea("Entomology").size());
		assertEquals("Number of experts", expectedCountByKeyword,sparqlEngine.identifyExpertsByKeyword("BRAIN").size());
		assertEquals("Number of experts", expectedCountBySubjectArea,sparqlEngine.identifyExpertsBySubjectArea("BRAIN").size());
	}
	
	@Test
	public void testGetUser(){
		User user = sparqlEngine.getUser("mconlon@ufl.edu");
		assertEquals("Email id", emailid,user.getEmail());
		assertEquals("URI", uri, user.getUri());
		assertEquals("Name", name, user.getName());
		assertEquals("Department", department, user.getDepartment());
		assertEquals("DepartmentURI",departmentURI,user.getDepartmentURI());
	}

	@Test
	public void testShouldAffiliate(){
		User user = sparqlEngine.getUser("menden@shands.ufl.edu");
		User expert = sparqlEngine.getUser("bhoppe@floridaproton.org");
		assertEquals("Should Affiliate", true, sparqlEngine.shouldAffiliate(user.getUri(),expert.getUri(), user.getDepartmentURI()));
	}
	
	@Test 
	public void testGetCoAuthors(){
		User user = sparqlEngine.getUser("stephen.grobmyer@surgery.ufl.edu");
		assertEquals("Number of CoAuthors", expectedCoauthors, sparqlEngine.getCoAuthors(user.getUri()).size());
	}
	
	@Test 
	public void testGetImage(){
		User user = sparqlEngine.getUser("eabuss@ufl.edu");
		assertEquals("Image url :", "http://vivo.ufl.edu/file/n24027/Eileen2.jpg", sparqlEngine.getImage(user.getUri()));
	}
	
}
