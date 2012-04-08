package edu.northwestern.sonic.dataaccess.vivo.test;

import static org.junit.Assert.assertEquals;
import java.net.URISyntaxException;

import org.junit.Test;

import edu.northwestern.sonic.dataaccess.vivo.Authorship;
import edu.northwestern.sonic.dataaccess.vivo.Researcher;
import edu.northwestern.sonic.model.User;

/*
 * author: Anup
 */

public class AuthorshipTest {
	private static final Researcher researcher = new Researcher();
	private static final Authorship authorship = new Authorship();

	@Test 
	public void testGetCoAuthors() throws URISyntaxException {
		final User user = researcher.getUser("stephen.grobmyer@surgery.ufl.edu");
		assertEquals("Number of CoAuthors", 20, authorship.getCoAuthors(user.getUri()).size());
	}
		
}
