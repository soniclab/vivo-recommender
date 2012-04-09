package edu.northwestern.sonic.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import edu.uci.ics.jung.algorithms.filters.KNeighborhoodFilter;
import edu.uci.ics.jung.algorithms.matrix.GraphMatrixOperations;
import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.algorithms.scoring.VertexScorer;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;

/**
 * Represent networks using JUNG Graph
 * nodes are unique strings, typically URIs
 * edges are named after their vertices, concatenated using DEL
 *
 * @author Hugh
 * 
 */
@SuppressWarnings("serial")
public class Network extends DirectedSparseMultigraph<String, String> {
	public static final String DEL = " "; // delimiter between from and to vertices in edge string
	private boolean directed = true;

	// Inner classes
	private abstract class IntMetric {
		public abstract int metric(String vertex);

		public int get(String vertex) {
			if (!containsVertex(vertex))
				return 0;
			return metric(vertex);
		}

		public int[] get(List<String> vertices) {
			int[] result = new int[vertices.size()];
			for (int i = 0; i < vertices.size(); i++)
				result[i] = get(vertices.get(i));
			return result;
		}
		
		public double getDensity(String vertex) {
			return (double) get(vertex) / (getVertexCount() - 1);
		}
		
		public double[] getDensity(List<String> vertices) {
			double[] result = new double[vertices.size()];
			for (int i = 0; i < vertices.size(); i++)
				result[i] = getDensity(vertices.get(i));
			return result;
		}

	}

	private class DoubleMetric {
		private VertexScorer<String,Double > scorer;
		
		public DoubleMetric(VertexScorer<String,Double > scorer){
			this.scorer = scorer;
		}

		public double get(String vertex) {
			if (!containsVertex(vertex))
				return 0.0;
			double result = scorer.getVertexScore(vertex);
			if (Double.isNaN(result))
				return 0.0;
			return result;
		}

		public double[] get(List<String> vertices) {
			double[] result = new double[vertices.size()];
			for (int i = 0; i < vertices.size(); i++)
				result[i] = get(vertices.get(i));
			return result;
		}
		
	}
	
	private DijkstraShortestPath<String, String> shortestPath = new DijkstraShortestPath<String, String>(this);;
	private DoubleMetric closeness  = new DoubleMetric(new ClosenessCentrality<String, String>(this));
	private IntMetric inDegree = new IntMetric() {
		@Override
		public int metric(String vertex) {
			return inDegree(vertex);
		}
	};
	private IntMetric outDegree = new IntMetric() {
		@Override
		public int metric(String vertex) {
			return outDegree(vertex);
		}
	};

	/**
	 * constructor for an empty directed network
	 *
	 * @param edges a list of pairs of strings, each pair the vertices of an edge
	 */
	public Network() {
	}
	
	/**
	 * constructor
	 *
	 * @param edges a list of pairs of strings, each pair the vertices of an edge
	 * @param directed true for direct, false for undirected
	 */
	public Network(boolean directed) {
		this.directed = directed;
	}
	
	/**
	 * constructor for a directed network
	 *
	 * @param edges a list of pairs of strings, each pair the vertices of an edge
	 */
	public Network(List<String[]> edges) {
		add(edges);
	}
	
	/**
	 * constructor
	 *
	 * @param edges a list of pairs of strings, each pair the vertices of an edge
	 * @param directed true for direct, false for undirected
	 */
	public Network(List<String[]> edges, boolean directed) {
		this(directed);
		add(edges);
	}
	
	/**
	 * add vertices and edges
	 * @param edges
	 */
	private void add(List<String[]> edges) {
		for (String[] edge : edges)
			add(edge[0], edge[1]);
	}

	public void add(String vertex1, String vertex2) {
		addVertex(vertex1);
		addVertex(vertex2);
		addEdge(vertex1 + DEL + vertex2, vertex1, vertex2);
		if(!directed)
			addEdge(vertex2 + DEL + vertex1, vertex2, vertex1);		
	}
	
	/**
	 * get the in-degree of a vertex
	 * @param vertex
	 * @return inDegree, 0 if not found
	 */
	public int getInDegree(String vertex) {
		return inDegree.get(vertex);
	}

	/**
	 * calls in degree for each vertex on a list
	 * 
	 * @param vertices
	 * @return array of densities
	 */
	public int[] getInDegree(List<String> vertices) {
		return inDegree.get(vertices);
	}

	/**
	 * get the in-degree density of a vertex
	 * @param vertex
	 * @return real value in the unit interval, inDegree as proportion of maximum possible in-degree (the
	 *         number of other vertices), 0.0 if not found
	 */
	public double getInDegreeDensity(String vertex) {
		return inDegree.getDensity(vertex);
	}

	/**
	 * calls degree density for each vertex on a list
	 * 
	 * @param vertices
	 * @return array of densities
	 */
	public double[] getInDegreeDensity(List<String> vertices) {
		return inDegree.getDensity(vertices);
	}

	/**
	 * get out-degree density for a vertex
	 * @param vertex
	 * @return outDegree, 0 if not found
	 */
	public int getOutDegree(String vertex) {
		return outDegree.get(vertex);	}

	/**
	 * calls out degree for each vertex on a list
	 * 
	 * @param vertices
	 * @return array of densities
	 */
	public int[] getOutDegree(List<String> vertices) {
		return outDegree.get(vertices);
	}

	/**
	 * the out-degree density of a vertex
	 * @param vertex
	 * @return a real value from the unit interval, out-degree as proportion of maximum possible out-degree (the
	 *         number of other vertices), 0.0 if not found
	 */
	public double getOutDegreeDensity(String vertex) {
		return outDegree.getDensity(vertex);
	}

	/**
	 * calls degree density for each vertex on a list
	 * 
	 * @param vertices
	 * @return array of densities
	 */
	public double[] getOutDegreeDensity(List<String> vertices) {
		return outDegree.getDensity(vertices);
	}
	
	/**
	 * get the betweenness centrality
	 * @param vertex
	 * @return betweenness centrality
	 */
	public double getBetweennessCentrality(String vertex) {
		final DoubleMetric betweenness = new DoubleMetric(new BetweennessCentrality<String, String>(this));
		return betweenness.get(vertex);
	}

	/**
	 * Normalization as per JUNG doc for BetweennessCentrality class
	 * @param vertex
	 * @return normalized betweenness centrality, 0.0 if not found
	 */
	public double getNormalizedBetweennessCentrality(String vertex) {
		int n = getVertexCount();
		if(n < 3)
			return 0.0; // prevent division by zero
		return 2.0 * getBetweennessCentrality(vertex) / ((n - 1) * (n - 2));
	}

	/**
	 * calls getNormalizedBetweennessCentrality for each vertex on a list
	 * 
	 * @param vertices
	 * @return array of centralities
	 */
	public double[] getNormalizedBetweennessCentrality(List<String> vertices) {
		double[] result = new double[vertices.size()];
		for (int i = 0; i < vertices.size(); i++)
			result[i] = getNormalizedBetweennessCentrality(vertices.get(i));
		return result;
	}

	/**
	 * get the closeness centrality of a vertex
	 * @param vertex
	 * @return
	 */
	public double getClosenessCentrality(String vertex) {
		return closeness.get(vertex);
	}

	/**
	 * calls getClosenessCentrality for each vertex on a list
	 * 
	 * @param vertices
	 * @return array of centralities
	 */
	public double[] getClosenessCentrality(List<String> vertices) {
		return closeness.get(vertices);
	}

	/**
	 * the length of the shortest path between two vertices
	 * @param source a vertex
	 * @param destination a vertex
	 * @return the length of the shortest path between source and destination
	 */
	public int getShortestPathLength(String source, String destination) {
		// 0 = no path, 1 = adjacent, etc.
		// not found throws an exception in getPath
		if (!containsVertex(source))
			return 0;
		if (!containsVertex(destination))
			return 0;
		return shortestPath.getPath(source, destination).size();
	}

	/**
	 * calls shortest path length for each vertex in a list
	 * @param source a vertex
	 * @param destinations a list of vertices
	 * @return an array of shortest path lengths
	 */
	public int[] getShortestPathLength(String source, List<String> destinations) {
		int[] result = new int[destinations.size()];
		for (int i = 0; i < destinations.size(); i++)
			result[i] = getShortestPathLength(source, destinations.get(i));
		return result;
	}

	/**
	 * the number of distinct paths of shortest length between a vertex and each vertex on a list
	 * @param source a vertex
	 * @param destinations  a list of vertices
	 * @param arrray of shortestPathLengths
	 * @return an array of counts of distinct shortest paths
	 */
	public long[] getNumGeodesics(String source, List<String> destinations, int[] shortestPathLengths) {
		// shortest path lengths: 0 = no path, 1 = adjacent, etc.
		// not found throws an exception in getPath
		final Algebra alg = new Algebra(0.01);
		long[] result = new long[destinations.size()];
		Arrays.fill(result, 0);
		// vertex names in internal order 
		List<String> vertices = new ArrayList<String>(getVertices());
		// seekerName index in adjacency array
		int sourceIndex = vertices.indexOf(source);
		// extract adjacency matrix in CERN structure
		DoubleMatrix2D dm = GraphMatrixOperations.graphToSparseMatrix(this);
		DoubleMatrix2D prod = (DoubleMatrix2D)dm.clone();
		// System.out.println("adjacency matrix=\n"+prod.toString());
		// longest shortest path length
		int max = NumberUtils.max(shortestPathLengths);
		for (int i = 2; i <= max; i++) {
			prod = alg.mult(prod,dm);		
			// System.out.println(i+" power=\n"+prod.toString());
			if(prod.cardinality()==0) // matrix empty?
				break;
			for(int j = 0; j<shortestPathLengths.length; j++){
				if(shortestPathLengths[j]==i) { // is this expert i away from seekerName?
					// record the number of shortest paths from seekerName to expert
					int expertColumnIndex = vertices.indexOf(destinations.get(j));
					result[j] = (long)prod.get(sourceIndex, expertColumnIndex);
				}
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see edu.uci.ics.jung.graph.DirectedSparseMultigraph#getEdges()
	 */
	public Collection<String> getEdges() {
		return super.getEdges();
	}
	
	/* (non-Javadoc)
	 * @see edu.uci.ics.jung.graph.DirectedSparseMultigraph#getVertices()
	 */
	public Collection<String> getVertices() {
		return super.getVertices();
	}
	
	/* (non-Javadoc)
	 * @see edu.uci.ics.jung.graph.DirectedSparseMultigraph#getIncidentEdges(java.lang.Object)
	 */
	public Collection<String> getIncidentEdges(String vertex) {
		return super.getIncidentEdges(vertex);
	}
	
	/* (non-Javadoc)
	 * @see edu.uci.ics.jung.graph.DirectedSparseMultigraph#getNeighbors(java.lang.Object)
	 */
	public Collection<String> getNeighbors(String vertex) {
		return super.getNeighbors(vertex);
	}
	
	/**
	 * Edges incident to any vertex in a set of vertices
	 * @param vertices
	 * @return list of strings representing edges "from-to"
	 */
	public Collection<String> getEdges(Set<String> vertices){ 
		// radius 1, in/out (all edges incident on root element, whether directed or undirected)
		KNeighborhoodFilter<String, String> neighborhoodFilter = new KNeighborhoodFilter<String, String>(vertices, 1, KNeighborhoodFilter.EdgeType.IN_OUT);
		DirectedSparseMultigraph<String, String> dsmg = (DirectedSparseMultigraph<String, String>) this;
		edu.uci.ics.jung.graph.Graph<String, String> g = neighborhoodFilter.transform(dsmg);
		return g.getEdges();
	}
}
