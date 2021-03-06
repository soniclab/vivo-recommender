package edu.northwestern.sonic.network;

import java.net.URI;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import edu.northwestern.sonic.dataaccess.vivo.AuthorAuthorCitation;

/**
 * Factory methods for bibliographic networks
 * factories return JUNG networks
 * in which nodes are author URIs
 * and edges are bibliographic relations
 * @author Anup
 * @author Hugh
 *
 */
public class AuthorNetwork {
	
	/*
	 * coauthorship network for a list of authors
	 * @params uris a set of author URIs
	 * @return combined ego networks (radius 1)
	 */
	public static Network coAuthorshipNetworkFactory(Set<URI> authors) {
		Network returnValue = new Network(false);
		AuthorAuthorCitation authorAuthorCitation = new AuthorAuthorCitation();
		for(URI author : authors) {
			String authorUriString = author.toString();
			Set<URI> coauthors = authorAuthorCitation.getCoAuthors(author);
			for(URI coauthor : coauthors)
				returnValue.add(authorUriString, coauthor.toString());
		}
		return returnValue;
	}
	
	/*
	 * cocitation network for a list of authors
	 * @params uris a set of author URIs
	 * @return combined cocitation networks (radius 1)
	 */
	public static Network coCitationNetworkFactory(Set<URI> authors) {
		Network returnValue = new Network(false);
		AuthorAuthorCitation authorAuthorCitation = new AuthorAuthorCitation();
		for(URI author : authors) {
			String authorUriString = author.toString();
			Set<URI> coCitedAuthors = authorAuthorCitation.getAuthorAuthorCoCitationSet(author);
			for(URI coCitedAuthor : coCitedAuthors)
				returnValue.add(authorUriString, coCitedAuthor.toString());
		}
		return returnValue;
	}
	
	/*
	 * citation network for a list of authors
	 * @params uris a set of author URIs
	 * @return combined citation networks (radius 1)
	 */
	public static Network citationNetworkFactory(Set<URI> authors) {
		//Network returnValue = new Network(false);
		Network returnValue = new Network(true);
		AuthorAuthorCitation authorAuthorCitation = new AuthorAuthorCitation();
        Set<URI> citedAuthors = new TreeSet<URI>();
		for(URI author : authors) {
			String authorUriString = author.toString();
			// inbound
			citedAuthors = authorAuthorCitation.getAuthorAuthorCitationToSet(author);
			for(URI citedAuthor : citedAuthors)
				returnValue.add(citedAuthor.toString(), authorUriString);
			// outbound
			citedAuthors = authorAuthorCitation.getAuthorAuthorCitationFromSet(author);
			for(URI citedAuthor : citedAuthors)
				returnValue.add(authorUriString, citedAuthor.toString());
		}
		return returnValue;
	}
	
	/**
	 * This method returns union of two networks. 
	 * @param netA
	 * @param netB
	 * @return unionNet union of two networks. 
	 */
	public static Network unionNetwork(Network netA, Network netB){
		Network unionNet = new Network(true);

		/* Adding all the vertices to the union network */
		
		for(String vertex : netA.getVertices()){
			unionNet.addVertex(vertex);
		}
		
		for(String vertex : netB.getVertices()){
			unionNet.addVertex(vertex);
		}
		/* Adding all the edges */
		
		for(String edge : netA.getEdges()){
			unionNet.addEdge(edge,netA.getSource(edge), netA.getDest(edge));
		}
		
		for(String edge : netB.getEdges()){
			unionNet.addEdge(edge,netB.getSource(edge), netB.getDest(edge));
		}

		return unionNet;
	}
	
}
