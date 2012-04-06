package edu.northwestern.sonic.network;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wrappers for JUNG centrality methods
 *
 * @author Hugh
 *
 */
public enum Centrality {
	IN_DEGREE {
		@Override
		public double[] get(Network network, List<String> uris) {
			return network.getInDegreeDensity(uris);
		}
	},
	OUT_DEGREE {
		@Override
		public double[] get(Network network, List<String> uris) {
			return network.getOutDegreeDensity(uris);
		}
	},
	BETWEENNESS {
		@Override
		public double[] get(Network network, List<String> uris) {
			return network.getNormalizedBetweennessCentrality(uris);
		}
	},
	CLOSENESS {
		@Override
		public double[] get(Network network, List<String> uris) {
			return network.getClosenessCentrality(uris);
		}
	};

	private static final Map<String, Centrality> map = new HashMap<String, Centrality>();

	static {
		for (Centrality centrality : EnumSet.allOf(Centrality.class))
			map.put(centrality.toString(), centrality);
	}

	/**
	 * reverse look-up
	 * 
	 * @param motivation
	 *            as String
	 * @return motivation
	 *			as Centrality enum
	 */
	public static Centrality get(String centrality) {
		return map.get(centrality);
	}
	
	public abstract double[] get(Network network, List<String> uris);
}

