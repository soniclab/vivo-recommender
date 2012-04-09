package edu.northwestern.sonic.network.test;

import static org.junit.Assert.*;
import java.util.*;


import org.junit.*;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import edu.northwestern.sonic.network.Network;
import edu.uci.ics.jung.algorithms.matrix.GraphMatrixOperations;

/**
 * @author Hugh
 *
 */
public class NetworkTest {
	private static final String vertex1 = "v1";
	private static final String vertex2 = "v2";
	private static final String vertex3 = "v3";
	private static final String[] verticesArray = {vertex1, vertex2, vertex3};
	private static final List<String> verticesList = Arrays.asList(verticesArray);
	private static final String[][] edgesArray = {{vertex1, vertex2}, {vertex2, vertex3}};
	private static final List<String[]> edgesList = Arrays.asList(edgesArray);
	protected static Network g = new Network(edgesList);
	private static final double TOLERANCE = 0.01;

	/**
	 * Test method for {@link edu.northwestern.sonic.semanticrecommender.network.Network#Graph(java.util.List)}.
	 */
	@Test
	public void testGetInDegree() {
		assertEquals("foo in degree", 0, g.getInDegree("foo"));
		assertEquals("v1 in degree", 0, g.getInDegree(vertex1));
		assertEquals("v2 in degree", 1, g.getInDegree(vertex2));
		assertEquals("v3 in degree", 1, g.getInDegree(vertex3));
		int[] inDegrees = g.getInDegree(verticesList);
		assertEquals("v1 in degree", 0, inDegrees[0]);
		assertEquals("v2 in degree", 1, inDegrees[1]);
		assertEquals("v3 in degree", 1, inDegrees[2]);
	}
	
	/**
	 * Test method for {@link edu.northwestern.sonic.semanticrecommender.network.Network#Graph(java.util.List)}.
	 */
	@Test
	public void testGetOutDegree() {
		assertEquals("foo out", 0, g.getOutDegree("foo"));
		assertEquals("v1 out", 1, g.getOutDegree(vertex1));
		assertEquals("v2 out", 1, g.getOutDegree(vertex2));
		assertEquals("v3 out", 0, g.getOutDegree(vertex3));
		int[] outDegrees = g.getOutDegree(verticesList);
		assertEquals("v1 out", 1, outDegrees[0]);
		assertEquals("v2 out", 1, outDegrees[1]);
		assertEquals("v3 out", 0, outDegrees[2]);
	}

	/**
	 * Test method for {@link edu.northwestern.sonic.semanticrecommender.network.Network#Graph(java.util.List)}.
	 */
	@Test
	public void testGetInDegreeDensity() {
		assertEquals("foo in density", 0.0, g.getInDegreeDensity("foo"), TOLERANCE);
		assertEquals(vertex1 + " in density", 0.0, g.getInDegreeDensity(vertex1), TOLERANCE);
		assertEquals(vertex2 + " in density", 0.5, g.getInDegreeDensity(vertex2), TOLERANCE);
		assertEquals(vertex3 + " in density", 0.5, g.getInDegreeDensity(vertex3), TOLERANCE);
		double[] inDegreeDensities = g.getInDegreeDensity(verticesList);
		assertEquals(vertex1 + " in density", 0.0, inDegreeDensities[0], TOLERANCE);
		assertEquals(vertex2 + " in density", 0.5, inDegreeDensities[1], TOLERANCE);
		assertEquals(vertex3 + " in density", 0.5, inDegreeDensities[2], TOLERANCE);
	}

	/**
	 * Test method for {@link edu.northwestern.sonic.semanticrecommender.network.Network#Graph(java.util.List)}.
	 */
	@Test
	public void testGetOutDegreeDensity() {
		assertEquals("foo out density", 0.0, g.getOutDegreeDensity("foo"), TOLERANCE);
		assertEquals(vertex1 + " out density", 0.5, g.getOutDegreeDensity(vertex1), TOLERANCE);
		assertEquals(vertex2 + " out density", 0.5, g.getOutDegreeDensity(vertex2), TOLERANCE);
		assertEquals(vertex3 + " out density", 0.0, g.getOutDegreeDensity(vertex3), TOLERANCE);
		double[] outDegreeDensities = g.getOutDegreeDensity(verticesList);
		assertEquals(vertex1 + " out density", 0.5, outDegreeDensities[0], TOLERANCE);
		assertEquals(vertex2 + " out density", 0.5, outDegreeDensities[1], TOLERANCE);
		assertEquals(vertex3 + " out density", 0.0, outDegreeDensities[2], TOLERANCE);
	}

	/**
	 * Test method for {@link edu.northwestern.sonic.semanticrecommender.network.Network#Graph(java.util.List)}.
	 */
	@Test
	public void testGetShortestPathLength() {
		assertEquals("foo to " + vertex1, 0.0, g.getShortestPathLength("foo", vertex1), TOLERANCE);
		assertEquals("foo to " + vertex2, 0.0, g.getShortestPathLength("foo", vertex2), TOLERANCE);
		assertEquals(vertex1 + " to " + vertex2, 1.0, g.getShortestPathLength(vertex1, vertex2), TOLERANCE);
		assertEquals(vertex2 + " to " + vertex1, 0.0, g.getShortestPathLength(vertex2, vertex1), TOLERANCE);
	}

	/**
	 * Test method for {@link edu.northwestern.sonic.semanticrecommender.network.Network#Graph(java.util.List)}.
	 */
	@Test
	public void testGetBetweennessCentrality() {
		assertEquals("foo betweenness", 0.0, g.getBetweennessCentrality("foo"), TOLERANCE);
		assertEquals(vertex1 + " betweenness", 0.0, g.getBetweennessCentrality(vertex1), TOLERANCE);
		assertEquals(vertex2 + " betweenness", 1.0, g.getBetweennessCentrality(vertex2), TOLERANCE);
		assertEquals(vertex3 + " betweenness", 0.0, g.getBetweennessCentrality(vertex3), TOLERANCE);
	}

	/**
	 * Test method for {@link edu.northwestern.sonic.semanticrecommender.network.Network#Graph(java.util.List)}.
	 */
	@Test
	public void testGetNormalizedBetweennessCentrality() {
		assertEquals("foo normalized betweenness", 0.0, g.getNormalizedBetweennessCentrality("foo"), TOLERANCE);
		assertEquals(vertex1 + "normalized betweenness", 0.0, g.getNormalizedBetweennessCentrality(vertex1), TOLERANCE);
		assertEquals(vertex2 + "normalized betweenness", 1.0, g.getNormalizedBetweennessCentrality(vertex2), TOLERANCE);
		assertEquals(vertex3 + "normalized betweenness", 0.0, g.getNormalizedBetweennessCentrality(vertex3), TOLERANCE);
		double[] normalizedBetweennessCentralities = g.getNormalizedBetweennessCentrality(verticesList);
		assertEquals(vertex1 + " in density", 0.0, normalizedBetweennessCentralities[0], TOLERANCE);
		assertEquals(vertex2 + " in density", 1.0, normalizedBetweennessCentralities[1], TOLERANCE);
		assertEquals(vertex3 + " in density", 0.0, normalizedBetweennessCentralities[2], TOLERANCE);
	}

	/**
	 * Test method for {@link edu.northwestern.sonic.semanticrecommender.network.Network#Graph(java.util.List)}.
	 */
	@Test
	public void testGetClosenessCentrality() {
		assertEquals("foo closeness", 0.0, g.getClosenessCentrality("foo"), TOLERANCE);
		assertEquals(vertex1 + " closeness", 0.66, g.getClosenessCentrality(vertex1), TOLERANCE);
		assertEquals(vertex2 + " closeness", 1.0, g.getClosenessCentrality(vertex2), TOLERANCE);
		assertEquals(vertex3 + " closeness", 0.0, g.getClosenessCentrality(vertex3), TOLERANCE);
		double[] closenessCentralities = g.getClosenessCentrality(verticesList);
		assertEquals(vertex1 + " in density", 0.66, closenessCentralities[0], TOLERANCE);
		assertEquals(vertex2 + " in density", 1.0, closenessCentralities[1], TOLERANCE);
		assertEquals(vertex3 + " in density", 0.0, closenessCentralities[2], TOLERANCE);
	}
	
	@Test
	public void testPow(){
		// System.out.println(g.toString());
		SparseDoubleMatrix2D sdm = GraphMatrixOperations.graphToSparseMatrix(g);
		// System.out.println(sdm.toString());
		List<String> vs = new ArrayList<String>(g.getVertices());
		assertEquals("v1 to v2", 1.0, sdm.get(vs.indexOf(vertex1), vs.indexOf(vertex2)), TOLERANCE);
		assertEquals("v2 to v3", 1.0, sdm.get(vs.indexOf(vertex2), vs.indexOf(vertex3)), TOLERANCE);
		assertEquals("cardinality", 2, sdm.cardinality());
		Algebra alg = new Algebra(0.01);
		DoubleMatrix2D dm = alg.pow(sdm,2);
		// System.out.println(dm.toString());
		assertEquals("v1 to v3", 1.0, dm.get(vs.indexOf(vertex1), vs.indexOf(vertex3)), TOLERANCE);
		assertEquals("cardinality", 1, dm.cardinality());
		dm = alg.pow(sdm,3);
		// System.out.println(dm.toString());
		assertEquals("cardinality", 0, dm.cardinality());
	}

}
