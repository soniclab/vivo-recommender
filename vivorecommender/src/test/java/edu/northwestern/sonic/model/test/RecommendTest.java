package edu.northwestern.sonic.model.test;

import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.northwestern.sonic.dataaccess.vivo.Researcher;
import edu.northwestern.sonic.model.Recommend;
import edu.northwestern.sonic.model.User;
import static org.junit.Assert.*;

public class RecommendTest {
	static Researcher researcher;
	private final int fofListSize = 4;
	private final int affListSize = 1;
	private List<String> experts = null;
	private User seeker = null;
	private Recommend recommend = null;
	
	@Before
	public void setUp() throws Exception {
		researcher = new Researcher();
		experts = researcher.identifyExpertsByResearchArea("Entomology");
		seeker = researcher.getUser("mahoy@ifas.ufl.edu");
		recommend = new Recommend();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFriendOfFriend() {
		List<String> fOFList = recommend.friendOfFriend(experts, seeker);
		assertEquals("List size : ", fofListSize, fOFList.size());
	}
	
	@Test
	public void testAffiliation(){
		List<String> affList = recommend.affiliation(experts, seeker);
		assertEquals("List size : ", affListSize, affList.size());
	}
}
