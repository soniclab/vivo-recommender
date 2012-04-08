/**
 * Test interface to U. of Iowa medline SPARQL endpoint
 */
package edu.northwestern.sonic.dataaccess.medline.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.*;

import edu.northwestern.sonic.dataaccess.medline.ArticleArticleCitation;

/**
 * @author Hugh
 * 
 */
public class ArticleArticleCitationTest {

	final static ArticleArticleCitation medline = new ArticleArticleCitation();
	
//	PMID 21145524; PMC 3084596
//	PM R. 2010 Dec;2(12):1119-26.
//	Central versus lower body obesity distribution and the association with lower limb physical function and disability.
//	Foster NA, Segal NA, Clearfield JS, Lewis CE, Keysor J, Nevitt MC, Torner JC.

	final static int[] CITED_BY_21145524 = { 10904454, 8484377, 11747140, 11773208, 11988456, 12028181,
			12117699, 12145369, 12410898, 14747216, 15199035, 15314627, 15341561, 15356666,
			15809534, 16231023, 16339115, 16636211, 17403720, 17494056, 17510091, 18364384,
			18830075, 18987271, 8144820, 8176141, 8419667, 8784698, 8838836, 9270413, 9481598,
			9514371, 9663405, 9842379, 9806310, 11982670, 14708033, 16998083, 17678660, 19121597,
			4110024, 7712363, 8151851, 8478855, 11347782 }; // checked via PMC; 3 refs in article w/o pmids
	
	@BeforeClass
	public static void classSetUp()  {
		Arrays.sort(CITED_BY_21145524);
	}

	@Test
	public void testGetArticleArticleCitationFromNone() {
		int[] actual = medline.getArticleArticleCitationFrom(0);
		assertEquals("no citations", 0, actual.length);
	}

	@Test
	public void testGetArticleArticleCitationToNone() {
		int[] actual = medline.getArticleArticleCitationTo(0);
		assertEquals("no citations", 0, actual.length);
	}

	@Test
	public void testGetArticleArticleCitationFrom() {
		int[] actual = medline.getArticleArticleCitationFrom(21145524);
		assertEquals("number of citations", CITED_BY_21145524.length, actual.length);
		assertArrayEquals("list of citations", CITED_BY_21145524, actual);
	}

//	Pubmed id 814844
//	Angew Chem Int Ed Engl. 1976 Jan;15(1):1-9.
//	Synthetic applications of heteroaromatic betaines with six-membered rings.
//	Dennis N, Katritzky AR, Takeuchi Y.
	
//	Pubmed id 16018651
//	Org Lett. 2005 Jul 21;7(15):3323-5.
//	Enantioselective approach to the hetisine alkaloids. Synthesis of the 3-methyl-1-aza-tricyclo[5.2.1.0(3,8)]decane core via intramolecular dipolar cycloaddition.
//	Peese KM, Gin DY.
	
//	Pubmed id 16819859
//	J Am Chem Soc. 2006 Jul 12;128(27):8734-5.
//	Efficient synthetic access to the hetisine C20-diterpenoid alkaloids. A concise synthesis of nominine via oxidoisoquinolinium-1,3-dipolar and dienamine-Diels-Alder cycloadditions.
//	Peese KM, Gin DY.
	
	@Test
	public void testGetArticleArticleCitationTo() {
		final int[] CITES_814844 = { 16018651, 16819859 }; // ok; no false positives; many additional on Wiley
		int[] actual = medline.getArticleArticleCitationTo(814844);
		assertEquals("number of citations", CITES_814844.length, actual.length);
		assertArrayEquals("list of citations", CITES_814844, actual);
	}

	@Test
	public void testArticleArticleCoCitationNone() {
		int[] actual = medline.getArticleArticleCoCitation(0);
		assertEquals("number of citations", 0, actual.length);
	}

	@Test
	public void testGetArticleArticleCoCitation() {
		final int[] COCITED_WITH_814844 = {
			// each of these should be cited by one or both of 16018651 or 16819859
			3336035,	// cited by 16819859
			7359514,	// cited by 16819859
			8699181,	// cited by 16018651
			9331980,	// cited by 16018651
			9514608,	// cited by 16018651
			10823674,	// cited by 16018651
			10836058,	// cited by 16819859
			11472177,	// cited by 16018651 and 16819859
			12967309,	// cited by 16018651 and 16819859
			16018651	// cited by 16819859
		};
		int[] actual = medline.getArticleArticleCoCitation(814844);
		assertEquals("number of citations", COCITED_WITH_814844.length, actual.length);
		assertArrayEquals("list of citations", COCITED_WITH_814844, actual);
	}

	@Test
	public void testGetArticleArticleCoCitationFrequency() {
		int actual = 0;
		actual = medline.getArticleArticleCoCitationFrequency(0, 0);
		assertEquals("2 articles not found", 0, actual);
		actual = medline.getArticleArticleCoCitationFrequency(0, 3336035);
		assertEquals("1st article not found", 0, actual);
		actual = medline.getArticleArticleCoCitationFrequency(3336035, 0);
		assertEquals("2nd article not found", 0, actual);
		actual = medline.getArticleArticleCoCitationFrequency(3336035, 814844);
		assertEquals("2 articles found", 1, actual);
		actual = medline.getArticleArticleCoCitationFrequency(814844, 3336035);
		assertEquals("symmetric", 1, actual);
		actual = medline.getArticleArticleCoCitationFrequency(814844, 11472177);
		assertEquals("2 instances of cocitation", 2, actual);
		actual = medline.getArticleArticleCoCitationFrequency(11472177, 814844);
		assertEquals("2 instances of cocitation, symmetric", 2, actual);
	}
	
	@Test
	public void testGetArticleArticleCoCitationRelativeFrequency() {
		double actual = 0.0;
		actual = medline.getArticleArticleCoCitationRelativeFrequency(0, 0);
		assertEquals("2 articles not found", 0.0, actual, 0.001);
		actual = medline.getArticleArticleCoCitationRelativeFrequency(0, 3336035);
		assertEquals("1st article not found", 0.0, actual, 0.001);
		actual = medline.getArticleArticleCoCitationRelativeFrequency(3336035, 0);
		assertEquals("2nd article not found", 0.0, actual, 0.001);
		actual = medline.getArticleArticleCoCitationRelativeFrequency(814844, 11472177);
		assertEquals("2 instances of cocitation", 0.5, actual, 0.001);
		actual = medline.getArticleArticleCoCitationRelativeFrequency(11472177, 814844);
		assertEquals("2 instances of cocitation, symmetric", 0.5, actual, 0.001);
		actual = medline.getArticleArticleCoCitationRelativeFrequency(814844, 12967309);
		assertEquals("another 2 instances of cocitation", 0.666, actual, 0.001);
	}
}
