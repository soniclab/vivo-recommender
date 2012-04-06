package edu.northwestern.sonic.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.northwestern.sonic.dataaccess.vivo.Researcher;
import edu.northwestern.sonic.network.Network;

public class Recommend {
	
	private Researcher researcher = new Researcher();
	public static Network coauthorshipNet = null;
	
	/*
	 * Method to get list of recommendations through Affiliation heuristic
	 * @params : List of identified experts, ego
	 */
	public List<URI> affiliation(Set<URI> experts, User seeker){
		URI expertURI = null;
		List<URI> affiliationList = null;
		Iterator<URI> itr = experts.iterator();
		while(itr.hasNext()){
			affiliationList = new ArrayList<URI>();
			expertURI = itr.next();
			if(researcher.shouldAffiliate(seeker.getUri(), expertURI, seeker.getDepartmentURI())){
				affiliationList.add(expertURI);
			}
		}
		return affiliationList;
	}
	
	/*
	 * Method to get list of recommendations through Friend-of-Friend heuristic
	 * @params : List of identified experts, ego
	 */
	public List<URI> friendOfFriend(Set<URI> experts, User seeker) throws URISyntaxException{
		
		URI expertURI = null;
		List<URI> fOFList = new ArrayList<URI>();
		List<String[]> edges = new ArrayList<String[]>();
		Set<URI> coauthors = null;
		Iterator<URI> itr = experts.iterator();
		while(itr.hasNext()){ // for all experts
			// coauthors = new ArrayList<String[]>();
			expertURI = itr.next();
			coauthors = researcher.getCoAuthors(expertURI);
			if(coauthors != null && coauthors.size() > 0){ // for all coauthors of expert
				Iterator<URI> coItr = coauthors.iterator();
				while(coItr.hasNext()){
					// String[] details = coItr.next();
					URI uri = coItr.next();
					edges.add(new String[]{expertURI.toString(),uri.toString()});
					edges.add(new String[]{uri.toString(),expertURI.toString()});	
				}
			}	
		}
		coauthorshipNet = new Network(edges); // putting experts and their coauthors in network.
		itr = experts.iterator();
		while(itr.hasNext()){ // making a list of all experts linked to seeker through friends.
			expertURI = itr.next();
			if(coauthorshipNet.getShortestPathLength(seeker.getUri().toString(), expertURI.toString()) > 0 && !expertURI.equals(seeker.getUri())){
				fOFList.add(expertURI);
			}
		}
		return fOFList;
	}

}
