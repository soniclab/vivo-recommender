/**
 * Test interface to U. of Iowa medline SPARQL endpoint
 */
package edu.northwestern.sonic.medline.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.*;

import edu.northwestern.sonic.medline.CitationServer;

/**
 * @author Hugh
 *
 */
public class CitationServerTest {
	
	static CitationServer medline;

	final int[] CITED_BY_21145524 = {
			10904454,
			8484377,
			11747140,
			11773208,
			11988456,
			12028181,
			12117699,
			12145369,
			12410898,
			14747216,
			15199035,
			15314627,
			15341561,
			15356666,
			15809534,
			16231023,
			16339115,
			16636211,
			17403720,
			17494056,
			17510091,
			18364384,
			18830075,
			18987271,
			8144820,
			8176141,
			8419667,
			8784698,
			8838836,
			9270413,
			9481598,
			9514371,
			9663405,
			9842379,
			9806310,
			11982670,
			14708033,
			16998083,
			17678660,
			19121597,
			4110024,
			7712363,
			8151851,
			8478855,
			11347782
		};
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Arrays.sort(CITED_BY_21145524);
		medline = new CitationServer();
	}

	@Test
	public void testGetCitations() {
		List<Integer> expected = Arrays.asList(ArrayUtils.toObject(CITED_BY_21145524));
		List<Integer> citations = medline.getCitations(21145524);
		assertEquals("number of citations", expected.size(), citations.size());
		assertEquals("list of citations", expected, citations);
	}

}
