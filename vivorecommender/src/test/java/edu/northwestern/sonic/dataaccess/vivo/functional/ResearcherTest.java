package edu.northwestern.sonic.dataaccess.vivo.functional;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.northwestern.sonic.dataaccess.vivo.Researcher;
import edu.northwestern.sonic.model.Identification;
import edu.northwestern.sonic.model.User;

/*
 * author: Anup
 */

public class ResearcherTest {

	static Researcher researcher = new Researcher();
	private final int expectedCoauthors = 20;
	private final String emailid = "mconlon@ufl.edu";
	private final String uri = "http://vivo.ufl.edu/individual/n25562";
	private final String name = "Conlon, Michael";
	private final String department = "Health Outcomes and Policy";
	private final String departmentURI = "http://vivo.ufl.edu/individual/n183020";
	
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetUser() throws URISyntaxException {
		User user = researcher.getUser("mconlon@ufl.edu");
		assertEquals("Email id", emailid,user.getEmail());
		assertEquals("URI", uri, user.getUri().toString());
		assertEquals("Name", name, user.getName());
		assertEquals("Department", department, user.getDepartment());
		assertEquals("DepartmentURI",departmentURI,user.getDepartmentURI().toString());
	}

	@Test
	public void testShouldAffiliate() throws URISyntaxException{
		User user = researcher.getUser("menden@shands.ufl.edu");
		User expert = researcher.getUser("bhoppe@floridaproton.org");
		assertEquals("Should Affiliate", true, researcher.shouldAffiliate(user.getUri(),expert.getUri(), user.getDepartmentURI()));
	}
	
	@Test 
	public void testGetImage() throws URISyntaxException {
		User user = researcher.getUser("eabuss@ufl.edu");
		assertEquals("Image url :", "http://vivo.ufl.edu/file/n24027/Eileen2.jpg", researcher.getImage(user.getUri()));
	}
	
	@Test
	public void testGetUserByURI() throws URISyntaxException {
		User user = researcher.getUser(new URI(uri));
		assertEquals("Email : ", emailid, user.getEmail());
	}
	
	@Test
	public void testGetUsers() throws URISyntaxException {
		Identification identification = new Identification();
		Set<URI> identifiedExperts = identification.identifyExpertsByResearchArea("Entomology");
		List<User> experts = researcher.getUsers(identifiedExperts);
		assertEquals("List size : ", 18, experts.size());
	}
	
}
