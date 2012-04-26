package edu.northwestern.sonic.model.test;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import org.junit.Test;

import edu.northwestern.sonic.model.Identification;

/*
 * author: Anup
 * HJD 2012-04-26 separate tests to see individual timings
 */

public class IdentificationTest {

	static Identification identification = new Identification();
	
	private final int expectedCount = 18;
	private final int expectedCountByKeyword = 1796;
	private final int expectedCountBySubjectArea = 0;
	
	@Test
	public void testIdentifyExpertsByResearchArea() throws URISyntaxException {
		assertEquals("Number of experts (ByResearchArea)", expectedCount, identification.identifyExpertsByResearchArea("Entomology").size());
	}
	
	@Test
	public void testIdentifyExpertsByKeyword() throws URISyntaxException {
		assertEquals("Number of experts (ByKeyword)", expectedCountByKeyword, identification.identifyExpertsByKeyword("BRAIN").size());
	}
	
	@Test
	public void testIdentifyExpertsBySubjectArea() throws URISyntaxException {
		assertEquals("Number of experts (BySubjectArea)", expectedCountBySubjectArea, identification.identifyExpertsBySubjectArea("BRAIN").size());
	}
	
}
