package edu.northwestern.sonic.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.northwestern.sonic.dataaccess.vivo.AuthorAuthorCitation;
import edu.northwestern.sonic.dataaccess.vivo.Researcher;
import edu.northwestern.sonic.network.Network;

public class Recommend {
	
	private Researcher researcher = new Researcher();
	public static Network coauthorshipNet = null;
	AuthorAuthorCitation authorAuthorCitation = new AuthorAuthorCitation();
	
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
	
	/**
	 * use co-citation as an indicator of possible future collaboration
	 * @param experts set of URIs of qualified experts
	 * @param ego URI of our seeker
	 * @return set of URIs of qualified experts, who are co-cited with ego, but not coauthors of ego
	 * @throws URISyntaxException
	 */
	public Set<URI> cocitation(Set<URI> experts, URI ego) throws URISyntaxException {
		Set<URI> returnValue = new TreeSet<URI>(experts); // copy constructor
		returnValue.removeAll(researcher.getCoAuthors(ego)); // disqualify previous coauthors
		returnValue.retainAll(authorAuthorCitation.getAuthorAuthorCoCitationSet(ego)); // must be co-cited
		returnValue.remove(ego); // should not be in expert list
		return returnValue;
	}

	/*
	 * coauthorship network for a list of authors
	 * @params uris a set of author URIs
	 * @return combined ego networks (radius 1)
	 */
	public Network getCoAuthorship(Set<URI> uris) throws URISyntaxException {
		Network returnValue = new Network(false);
		for(URI uri : uris) {
			String uriString = uri.toString();
			Set<URI> coauthors = researcher.getCoAuthors(uri);
			for(URI coauthor : coauthors)
				returnValue.add(uriString, coauthor.toString());
		}
		return returnValue;
	}
	
	/*
	 * coauthorship network for a list of authors
	 * @params uris a set of author URIs
	 * @return combined cocitation networks (radius 1)
	 */
	public Network getCoCitation(Set<URI> uris) throws URISyntaxException {
		Network returnValue = new Network(false);
		for(URI uri : uris) {
			String uriString = uri.toString();
			Set<URI> coCitedAuthors = authorAuthorCitation.getAuthorAuthorCoCitationSet(uri);
			for(URI coCitedAuthor : coCitedAuthors)
				returnValue.add(uriString, coCitedAuthor.toString());
		}
		return returnValue;
	}
	
	/*
	 * citation network for a list of authors
	 * @params uris a set of author URIs
	 * @return combined citation networks (radius 1)
	 */
	public Network getCitation(Set<URI> uris) throws URISyntaxException {
		Network returnValue = new Network(false);
		Set<URI> citedAuthors = new TreeSet<URI>();
		for(URI uri : uris) {
			String uriString = uri.toString();
			citedAuthors = authorAuthorCitation.getAuthorAuthorCitationToSet(uri);
			for(URI citedAuthor : citedAuthors)
				returnValue.add(citedAuthor.toString(), uriString);
			citedAuthors = authorAuthorCitation.getAuthorAuthorCitationFromSet(uri);
			for(URI citedAuthor : citedAuthors)
				returnValue.add(uriString, citedAuthor.toString());
		}
		return returnValue;
	}
	
	/*
	 * Method to get list of recommendations through Friend-of-Friend heuristic
	 * @params : List of identified experts, ego
	 */
	public List<URI> friendOfFriend(Set<URI> experts, User seeker) throws URISyntaxException{
		List<URI> fOFList = new ArrayList<URI>();
		experts.add(seeker.getUri());
		Network coauthorshipNet = getCoAuthorship(experts); // putting experts and their coauthors in network.
		Iterator<URI> itr = experts.iterator();
		while(itr.hasNext()){ // making a list of all experts linked to seeker through friends.
			URI expertURI = itr.next();
			if(expertURI.equals(seeker.getUri()))
				continue;
			if(coauthorshipNet.getShortestPathLength(seeker.getUri().toString(), expertURI.toString()) > 0){
				fOFList.add(expertURI);
			}
		}
		return fOFList;
	}

}
