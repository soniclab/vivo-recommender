package edu.northwestern.sonic.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import edu.northwestern.sonic.dataaccess.vivo.AuthorAuthorCitation;
import edu.northwestern.sonic.dataaccess.vivo.Authorship;
import edu.northwestern.sonic.dataaccess.vivo.Researcher;
import edu.northwestern.sonic.network.AuthorNetwork;
import edu.northwestern.sonic.network.Network;
import edu.northwestern.sonic.util.UriUtil;

public class Recommend {
	
	private Researcher researcher = new Researcher();
	public static Network coauthorshipNet = null;
	AuthorAuthorCitation authorAuthorCitation = new AuthorAuthorCitation();
	
	/**
	 * @author Anup
	 * @param experts
	 * @param seeker
	 * @return list of experts working in same department as ego but never worked with ego.
	 */
	public List<User> affiliation(Set<URI> experts, User ego) {
		URI expertURI = null;
		Set<URI> affiliationList = null;
		experts.remove(ego.getUri());
		Iterator<URI> itr = experts.iterator();
		affiliationList = new HashSet<URI>();
		while(itr.hasNext()){
			expertURI = itr.next();
			if(researcher.shouldAffiliate(ego.getUri(), expertURI, ego.getDepartmentURI())){
				affiliationList.add(expertURI);
			}
		}
		return new Researcher().getUsers(affiliationList);
	}
	
	/**
	 * use co-citation as an indicator of possible future collaboration
	 * @param experts set of URIs of qualified experts
	 * @param ego URI of our seeker
	 * @return set of URIs of qualified experts, who are co-cited with ego, but not coauthors of ego
	 * @throws URISyntaxException
	 */
	public List<User> cocitation(Set<URI> experts, URI ego) {
		Set<URI> returnValue = new TreeSet<URI>(experts); // copy constructor
		returnValue.removeAll(authorAuthorCitation.getCoAuthors(ego)); // disqualify previous coauthors
		returnValue.retainAll(authorAuthorCitation.getAuthorAuthorCoCitationSet(ego)); // must be co-cited
		returnValue.remove(ego); // should not be in expert list
		return new Researcher().getUsers(returnValue);
	}
	
	/**
	 * combine the in-degree of citation network and coauthorship network;
	 * return the experts with higher in-degree in the combined network;
	 * @param experts
	 * @param ego
	 * @return List of experts with higher in-degree in combined network
	 * @throws URISyntaxException
	 */
	public List<User> followTheCrowd(Set<URI> experts, User ego) {
		Set<URI> ftcList = new HashSet<URI>();
		Network citationNet = AuthorNetwork.citationNetworkFactory(experts);
		Network coauthorNet = AuthorNetwork.coAuthorshipNetworkFactory(experts);
		experts.remove(ego.getUri());
		Iterator<URI> itr = experts.iterator();
		URI expert;
		while(itr.hasNext()){
			expert = itr.next();
			if((citationNet.getInDegree(expert.toString()) + coauthorNet.getInDegree
					(expert.toString())) > 0){
				ftcList.add(expert);
			}
		}
		return new Researcher().getUsers(ftcList);
	}
	
	/**
	 * @author Anup
	 * @param experts
	 * @param ego
	 * @return List of experts that are selected through 'Friend of a Friend' heuristic.
	 * @throws URISyntaxException
	 */
	public List<User> friendOfFriend(Set<URI> experts, User ego) {
		Set<URI> fOFList = new HashSet<URI>();
		experts.add(ego.getUri());
		/* putting experts and their coauthors in network. */
		Network coauthorshipNet = AuthorNetwork.coAuthorshipNetworkFactory(experts); 
		Iterator<URI> itr = experts.iterator();
		/* making a list of all experts linked to seeker through friends. */
		while(itr.hasNext()){ 
			URI expertURI = itr.next();
			if(expertURI.equals(ego.getUri()))
				continue;
			if(coauthorshipNet.getShortestPathLength(ego.getUri().toString(),
					expertURI.toString()) > 0){
				fOFList.add(expertURI);
			}
		}
		return new Researcher().getUsers(fOFList);
	}
	
	/**
	 * @author Anup
	 * @param experts
	 * @param ego
	 * @return list of users who appear to be closest to the ego with respect to attributes like
	 * moniker, work department, gradSchool and major field of study.
	 * @throws URISyntaxException
	 */
	public List<User> birdsOfFeather(Set<URI> experts, User ego) {

		
		if(experts.contains(ego.getUri())){
			experts.remove(ego.getUri());
		}
		Researcher researcher = new Researcher();
		Map<String,List<Integer>> dictionary = formDictionary(ego);
		List<User> bof = researcher.getUsers(experts);
		setEducationBackground(bof);
		updateDictionary(bof,dictionary);

		int[] egoVector = new int[dictionary.keySet().size()];
		for(int i =0; i < egoVector.length; i++){
			egoVector[i] = 1;
		}
		double egoLength = Math.sqrt(egoVector.length);
		double dotProd;
		int[] expertVector;
		double expertLength;
		String key;
		Iterator<String> itr;
		int index;
		double totalLen;
		for(int i = 1; i <= bof.size(); i++){ // for all experts
			itr = dictionary.keySet().iterator();
			expertVector =  new int[egoVector.length];
			index = 0;
			expertLength = 0;
			dotProd = 0;
			totalLen = 0;
			while(itr.hasNext()){ // get the vector of one expert
				key = itr.next();
				expertVector[index] = dictionary.get(key).get(i);
				expertLength += dictionary.get(key).get(i);
				index++;
			}
			expertLength = Math.sqrt(expertLength);
			for(int j =0; j < egoVector.length; j++){
				dotProd += egoVector[j]*expertVector[j];
			}
			totalLen = egoLength*expertLength;
			if(totalLen == 0){
				bof.remove(i-1);
			}else{
				bof.get(i-1).setBofScore(dotProd/totalLen);
			}
		}
			
		class CompareUsers implements Comparator<User>  {

			@Override
			public int compare(User expert1, User expert2) {
				if(expert1.getBofScore() > expert2.getBofScore()){
					return -1;
				}else if(expert1.getBofScore() < expert2.getBofScore()){
					return 1;
				}else{
					return 0;
				}	
			}			
		}
		
		Collections.sort(bof, new CompareUsers());
		
		return bof;
	}
	
	
	/**
	 * @param experts
	 * @param ego
	 * @return List of experts selected through social exchange heuristic
	 * @throws URISyntaxException
	 */
	public List<User> exchange(Set<URI> experts, User ego) {
		Set<URI> exchangeList = new HashSet<URI>();
		
		Authorship authorship = new Authorship();
		AuthorAuthorCitation autautCit = new AuthorAuthorCitation();
		/* get all the pubmedIds of ego */
		Set<Integer> pubmedIds = authorship.getArticlesSet(ego.getUri()); 
		experts.remove(ego.getUri());
		Iterator<URI> itr = experts.iterator();
		Set<Integer> numCit;
		URI expertURI;
		if(pubmedIds.size() > 0){
			while(itr.hasNext()){
				expertURI = itr.next();
				/* number of times ego cited by the expert */
				numCit =  autautCit.getAuthorAuthorCitation(expertURI,pubmedIds);
				if(numCit.size() > 0){
					exchangeList.add(expertURI);
				}
			}
		}
		return new Researcher().getUsers(exchangeList);
	}
	
	public List<User> mobilizing(Set<URI> experts, User ego) {
		Set<URI> mobilizingList = new HashSet<URI>();
		
		return new Researcher().getUsers(mobilizingList);
	}
	
	
	
	/**
	 * @param experts
	 * @param ego
	 * @param keyword
	 * @return List of experts selected as most qualified.
	 * @throws URISyntaxException
	 */
	public List<User> mostQualified(Set<URI> experts, User ego,String keyword) {
		Set<URI> mostQualifiedList = new HashSet<URI>();
		AuthorAuthorCitation autautCit = new AuthorAuthorCitation();
		experts.remove(ego.getUri());
		Iterator<URI> itr = experts.iterator();
		int hIndex = 0;
		URI expertURI;
		while(itr.hasNext()){
			expertURI = itr.next();
			hIndex = autautCit.getHIndex(expertURI,keyword);
			if(hIndex > 0){
				mostQualifiedList.add(expertURI);
			}
		}
		return new Researcher().getUsers(mostQualifiedList);
	}
	


	private Map<String,List<Integer>> formDictionary(User ego) {

		Map<String,List<Integer>> dictionary = Collections.synchronizedMap(new TreeMap<String,List<Integer>>());
		List<Integer> scale;
		if(ego.getMoniker()!=null && !ego.getMoniker().equals("")){
			addToDictionary(ego.getMoniker(),dictionary);
		}
		if(ego.getDepartmentURI()!=null){
			addToDictionary(ego.getDepartmentURI().toString(),dictionary);
		}
		setEducationBackground(ego);
		if(ego.getMajorField()!=null && ego.getMajorField().size() > 0){
			Iterator<String> itr = ego.getMajorField().iterator();
			while(itr.hasNext()){
				addToDictionary(itr.next(),dictionary);
			}
		}
		if(ego.getGradSchool()!=null && ego.getGradSchool().size() > 0){
			Iterator<URI> itr = ego.getGradSchool().iterator();
			while(itr.hasNext()){
				addToDictionary(itr.next().toString(),dictionary);
			}
		}
		
		return dictionary;
	}
	
	private void updateDictionary(User expert, Map<String,List<Integer>> dictionary) {
		Set<String> details = getDetails(expert);
		Iterator<String> itr = dictionary.keySet().iterator();
		String key;
		List<Integer> scale;
		while(itr.hasNext()){
			key = itr.next();
			scale = dictionary.get(key);
			if(details.contains(key)){
				scale.add(1); // identified expert's attribute matches with ego's attribute.
			}else{
				scale.add(0); // identified expert's attribute does not match with ego's attribute.
			}
			dictionary.put(key, scale);
		}
	}
	
	private void updateDictionary(List<User> expertObjs, Map<String,List<Integer>> dictionary) {
		Iterator<User> itr = expertObjs.iterator();
		while(itr.hasNext()){
			updateDictionary(itr.next(),dictionary);
		}
	}
	
	private Set<String> getDetails(User expert) {
		Set<String> details = new HashSet<String>();
	    if(expert.getMoniker()!=null && !expert.getMoniker().equals("")){
	    		details.add(expert.getMoniker());
	    }
	    if(expert.getDepartmentURI()!=null){
	    		details.add(expert.getDepartmentURI().toString());
	    }
	    if(expert.getMajorField()!=null && expert.getMajorField().size() > 0){
			Iterator<String> itr = expert.getMajorField().iterator();
			while(itr.hasNext()){
				details.add(itr.next());
			}
		}
	    if(expert.getGradSchool()!=null && expert.getGradSchool().size() > 0){
			Iterator<URI> itr = expert.getGradSchool().iterator();
			while(itr.hasNext()){
				details.add(itr.next().toString());
			}
		}
	    
	    return details;
	}
	
	private void addToDictionary(String key,Map<String,List<Integer>> dictionary){
		List<Integer> scale;
		if(!dictionary.containsKey(key)){
			dictionary.put(key, new ArrayList<Integer>());
		}
		scale = dictionary.get(key);
		scale.add(1);
		dictionary.put(key, scale);
	}
	
	private void setEducationBackground(User expert) {
		Researcher researcher = new Researcher();
		List<String[]> educationBackground = researcher.getEducationalBackground(expert.getUri());
		if(educationBackground!=null && educationBackground.size() > 0){
			Iterator<String[]> itr = educationBackground.iterator();
			Set<String> fields = new HashSet<String>();
			Set<URI> gradSchools = new HashSet<URI>();
			String[] eduDetails;
			while(itr.hasNext()){
				eduDetails = itr.next();
				if(eduDetails[0]!=null && !eduDetails[0].equals("")){
					fields.add(eduDetails[0]);
				}
				if(eduDetails[1]!=null && !eduDetails[1].equals("")){
					gradSchools.add(UriUtil.safeUriFactory(eduDetails[1]));
				}
			}
			expert.setMajorField(fields);
			expert.setGradSchool(gradSchools);
		}
	}
	
	private void setEducationBackground(List<User> expertObjs) {

		Iterator<User> itr = expertObjs.iterator();
		int count =0;
		while(itr.hasNext()){
			System.out.println("Expert number : " + count++);
			setEducationBackground(itr.next());
		}
	}
}
