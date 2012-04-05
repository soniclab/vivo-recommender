package edu.northwestern.sonic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.northwestern.sonic.bean.User;
import edu.northwestern.sonic.network.Network;
import edu.northwestern.sonic.sparql.SparqlEngine;

public class Recommend {
	
	private SparqlEngine sparqlEngine = new SparqlEngine();
	public static Network coauthorshipNet = null;
	
	/*
	 * Method to get list of recommendations through Affiliation heuristic
	 * @params : List of identified experts, ego
	 */
	public List<String> affiliation(List<String> experts, User seeker){
		String expertURI = null;
		List<String> affiliationList = null;
		Iterator<String> itr = experts.iterator();
		while(itr.hasNext()){
			affiliationList = new ArrayList<String>();
			expertURI = itr.next();
			if(sparqlEngine.shouldAffiliate(seeker.getUri(), expertURI, seeker.getDepartmentURI())){
				affiliationList.add(expertURI);
			}
		}
		return affiliationList;
	}
	
	/*
	 * Method to get list of recommendations through Friend-of-Friend heuristic
	 * @params : List of identified experts, ego
	 */
	public List<String> friendOfFriend(List<String> experts, User seeker){
		
		String expertURI = null;
		List<String> fOFList = new ArrayList<String>();
		List<String[]> edges = new ArrayList<String[]>();
		List<String[]> coauthors = null;
		Iterator<String> itr = experts.iterator();
		while(itr.hasNext()){ // for all experts
			coauthors = new ArrayList<String[]>();
			expertURI = itr.next();
			coauthors = sparqlEngine.getCoAuthors(expertURI);
			if(coauthors != null && coauthors.size() > 0){ // for all coauthors of expert
				Iterator<String[]> coItr = coauthors.iterator();
				while(coItr.hasNext()){
					String[] details = coItr.next();
					String uri = details[0];
					edges.add(new String[]{expertURI,uri});
					edges.add(new String[]{uri,expertURI});	
				}
			}	
		}
		coauthorshipNet = new Network(edges); // putting experts and their coauthors in network.
		itr = experts.iterator();
		while(itr.hasNext()){ // making a list of all experts linked to seeker through friends.
			expertURI = itr.next();
			if(coauthorshipNet.getShortestPathLength(seeker.getUri(), expertURI) > 0 && !expertURI.equals(seeker.getUri())){
				fOFList.add(expertURI);
			}
		}
		return fOFList;
	}

}
