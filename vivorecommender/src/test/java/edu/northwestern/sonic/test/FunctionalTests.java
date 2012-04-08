package edu.northwestern.sonic.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import edu.northwestern.sonic.dataaccess.medline.test.ArticleArticleCitationTest;
import edu.northwestern.sonic.dataaccess.medline.test.MedlineSparqlServiceTest;
import edu.northwestern.sonic.dataaccess.vivo.test.AuthorAuthorCitationTest;
import edu.northwestern.sonic.dataaccess.vivo.test.IdentificationTest;
import edu.northwestern.sonic.dataaccess.vivo.test.ResearcherTest;
import edu.northwestern.sonic.dataaccess.vivo.test.VivoSparqlServiceTest;
import edu.northwestern.sonic.model.test.RecommendTest;

/**
 * VIVO functional test suite
 * 
 * @author Hugh
 * 
 */

@RunWith(Suite.class)

@Suite.SuiteClasses( { 
	MedlineSparqlServiceTest.class,
	VivoSparqlServiceTest.class,
	ArticleArticleCitationTest.class,
	AuthorAuthorCitationTest.class,
	ResearcherTest.class,
	IdentificationTest.class,
	RecommendTest.class,
	})
	
public class FunctionalTests {
	// the class remains completely empty,
	// being used only as a holder for the above annotations
}