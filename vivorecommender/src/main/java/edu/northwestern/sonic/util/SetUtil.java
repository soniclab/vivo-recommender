package edu.northwestern.sonic.util;

import java.util.Set;
import java.util.TreeSet;

/**
 * Set Utilities 
 * @author Hugh
 *
 */
public class SetUtil {
	
	/**
	 * Jaccard index;
	 * see http://en.wikipedia.org/wiki/Jaccard_index;
	 * see Jaccard, Paul (1901), "…tude comparative de la distribution florale dans une portion des Alpes et des Jura", 
	 * Bulletin de la SociÈtÈ Vaudoise des Sciences Naturelles 37: 547ñ579
	 * @param set1 a set
	 * @param set2 a set 
	 * @return the Jaccard similarity coefficient of the two sets
	 */
	public static <E> double jaccard(final Set<E> set1, final Set<E> set2) {
		if(set1.isEmpty())
			return 0.0;
		if(set2.isEmpty())
			return 0.0;
		Set<E> union = new TreeSet<E>(set1);
		union.addAll(set2);
		Set<E> intersection = new TreeSet<E>(set1);
		intersection.retainAll(set2);
		if(intersection.isEmpty())
			return 0.0;
		return (double) intersection.size() / (double) union.size();
	}

}
