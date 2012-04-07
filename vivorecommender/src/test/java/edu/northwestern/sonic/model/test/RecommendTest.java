package edu.northwestern.sonic.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.northwestern.sonic.data.Katritzky;
import edu.northwestern.sonic.dataaccess.vivo.Identification;
import edu.northwestern.sonic.dataaccess.vivo.Researcher;
import edu.northwestern.sonic.model.Recommend;
import edu.northwestern.sonic.model.User;

public class RecommendTest {
	static Researcher researcher;
	static Identification identification;
	private final int fofListSize = 4;
	private final int affListSize = 1;
	private Set<URI> experts = null;
	private User seeker = null;
	private Recommend recommend = null;
	
	@Before
	public void setUp() throws Exception {
		researcher = new Researcher();
		identification = new Identification();
		experts = identification.identifyExpertsByResearchArea("Entomology");
		seeker = researcher.getUser("mahoy@ifas.ufl.edu");
		recommend = new Recommend();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFriendOfFriend() throws URISyntaxException {
		List<URI> fOFList = recommend.friendOfFriend(experts, seeker);
		assertEquals("List size : ", fofListSize, fOFList.size());
	}
	
	@Test
	public void testAffiliation(){
		List<URI> affList = recommend.affiliation(experts, seeker);
		assertEquals("List size : ", affListSize, affList.size());
	}
	
	@Test
	public void testCocitation() throws URISyntaxException {
		final URI BENNER_URI = new URI("http://vivo.ufl.edu/individual/n794607081"); // Steven Albert Benner (stub)
		final Set<URI> experts = identification.identifyExpertsByKeyword("organic");
		Set<URI> actual = recommend.cocitation(experts, Katritzky.VIVO_URI);
		assertEquals("count", 1, actual.size());
		assertTrue("uri", actual.contains(BENNER_URI));
	}
}
