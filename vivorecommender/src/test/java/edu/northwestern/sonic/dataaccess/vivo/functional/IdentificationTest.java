package edu.northwestern.sonic.dataaccess.vivo.functional;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import org.junit.Test;

import edu.northwestern.sonic.dataaccess.vivo.Identification;

/*
 * author: Anup
 */

public class IdentificationTest {

	static Identification identification = new Identification();
	
	private final int expectedCount = 18;
	private final int expectedCountByKeyword = 1796;
	private final int expectedCountBySubjectArea = 0;
	
	@Test
	public void testIdentifyExperts() throws URISyntaxException {
		assertEquals("Number of experts (ByResearchArea)", expectedCount, identification.identifyExpertsByResearchArea("Entomology").size());
		assertEquals("Number of experts (ByKeyword)", expectedCountByKeyword, identification.identifyExpertsByKeyword("BRAIN").size());
		assertEquals("Number of experts (BySubjectArea)", expectedCountBySubjectArea, identification.identifyExpertsBySubjectArea("BRAIN").size());
	}
	
}
