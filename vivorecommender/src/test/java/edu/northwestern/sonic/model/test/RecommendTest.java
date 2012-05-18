package edu.northwestern.sonic.model.test;

import static org.junit.Assert.assertEquals;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import org.junit.*;

import edu.northwestern.sonic.dataaccess.test.Katritzky;
import edu.northwestern.sonic.dataaccess.vivo.Researcher;
import edu.northwestern.sonic.model.Identification;
import edu.northwestern.sonic.model.Recommend;
import edu.northwestern.sonic.model.User;

public class RecommendTest {
	private Researcher researcher;
	private Identification identification;
	private Recommend recommend = null;
	private final int fofListSize = 4;
	private final int affListSize = 9;
	private Set<URI> experts = null;
	private User ego = null;
	private String keyword = "Entomology";
	
	@Before
	public void setUp() throws Exception {
		researcher = new Researcher();
		identification = new Identification();
		experts = identification.identifyExpertsByResearchArea(keyword);
		ego = researcher.getUser("mahoy@ifas.ufl.edu");
		recommend = new Recommend();
	}

	@Test
	public void testFriendOfFriend() throws URISyntaxException {
		List<User> fOFList = recommend.friendOfFriend(experts, ego);
		assertEquals("List size : ", fofListSize, fOFList.size());
	}
	
	@Test
	public void testAffiliation() throws URISyntaxException{
		List<User> affList = recommend.affiliation(experts, ego);
		assertEquals("List size : ", affListSize, affList.size());
	}
	
	@Ignore
	public void testBirdsOfFeather() throws URISyntaxException{
		List<User> bof = recommend.birdsOfFeather(experts, ego);
		assertEquals("List size : ", 13, bof.size());
	}
	
	@Test
	public void testCocitation() throws URISyntaxException {
		final URI BENNER_URI = new URI("http://vivo.ufl.edu/individual/n794607081"); // Steven Albert Benner (stub)
		final Set<URI> experts = identification.identifyExpertsByKeyword("organic");
		List<User> actual = recommend.cocitation(experts, Katritzky.VIVO_URI);
		assertEquals("count", 1, actual.size());
		User expert = new Researcher().getUser(BENNER_URI);
		assertEquals("uri",expert.getUri().toString(),actual.get(0).getUri().toString());
	}
	
	@Test
	public void testMostQualified() throws URISyntaxException {
		List<User> actual = recommend.mostQualified(experts, ego, keyword);
		assertEquals("count", 0, actual.size());
	}
	
	@Test
	public void testMostQualifiedOrganicKatritzky() throws URISyntaxException {
		String keyword = "organic";
		final Set<URI> experts = identification.identifyExpertsByKeyword(keyword);
		User ego = researcher.getUser(Katritzky.VIVO_URI);
		List<User> actual = recommend.mostQualified(experts, ego, keyword);
		assertEquals("count", 54, actual.size());
	}
	
	@Test
	public void testFollowTheCrowd() throws URISyntaxException {
		String keyword = "organic";
		final Set<URI> experts = identification.identifyExpertsByKeyword(keyword);
		User ego = researcher.getUser(Katritzky.VIVO_URI);
		List<User> actual = recommend.followTheCrowd(experts, ego);
		assertEquals("count", 96, actual.size());
	}
}
