package edu.northwestern.sonic.dataaccess.vivo;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import edu.northwestern.sonic.model.User;
import edu.northwestern.sonic.util.UriUtil;

public class Researcher extends VivoSparqlService {
	
	public User read(List<String[]> results, String email) {
		User user = null;
		if(results!=null && results.size() > 0){
			user = new User();
			user.setEmail(email);
			String[] details = results.get(0);
			user.setUri(UriUtil.safeUriFactory(details[0]));
			user.setName(details[1]);
			user.setDepartment(details[2]);
			user.setDepartmentURI(UriUtil.safeUriFactory(details[3]));
			if(details[4]!=null){
				user.setMoniker(details[4]);
			}
		}
		return user;
	}
	
	public User read(List<String[]> results, URI uri) {
		User user = null;
		if(results!=null && results.size() > 0){
			user = new User();
			user.setUri(uri);
			String [] details = results.get(0);
			if(details[0]!=null)
				user.setEmail(details[0]);
			user.setName(details[1]);
			if(details[2]!=null)
				user.setDepartment(details[2]);
			if(details[3]!=null)
				user.setDepartmentURI(UriUtil.safeUriFactory(details[3]));
			if(details[4]!=null)
				user.setMoniker(details[4]);
		}
		return user;
	}
		
	public User getUser(String email) {
		StringBuffer query = new StringBuffer("SELECT DISTINCT ?X (str(?name) AS ?Y) (str(?l) AS ?Z) ?D (str(?m) AS ?M) WHERE " + "{");
		query.append(" ?X a vivo:FacultyMember .");
		query.append(" ?X vivo:primaryEmail \"" + email + "\".");
		query.append(" ?X rdfs:label ?name .");
		query.append(" ?X vivo:personInPosition ?P .");
		query.append(" ?P vivo:positionInOrganization ?D .");
		query.append(" ?D a vivo:AcademicDepartment .");
		query.append(" ?D rdfs:label ?l .");
		query.append(" OPTIONAL {?X vitro:moniker ?m .}");
	    query.append(" } LIMIT 1");
	    
	    List<String[]> results = getStrings(query.toString(),new String[]{"X","Y","Z","D","M"});
	    return read(results,email);
	} 
	
	public User getUser(URI uri) {

		StringBuffer query = new StringBuffer("SELECT (str(?email) AS ?E) (str(?name) AS ?Y) (str(?l) AS ?Z) ?D (str(?m) AS ?M) WHERE " + "{");
		query.append("<"+ uri.toString() +"> rdfs:label ?name .");
		query.append(" OPTIONAL {<" + uri.toString() +"> vivo:primaryEmail ?email .}");
		query.append(" OPTIONAL { ");
		query.append("<"+ uri.toString() + "> vivo:personInPosition ?P .");
		query.append(" ?P vivo:positionInOrganization ?D .");
		query.append(" ?D a vivo:AcademicDepartment .");
		query.append(" ?P vivo:dateTimeInterval ?dti .");
		query.append(" FILTER NOT EXISTS {?dti vivo:end ?edti}");
		query.append(" ?D rdfs:label ?l .");
		query.append(" } ");
		query.append(" OPTIONAL {<" + uri.toString() +"> vitro:moniker ?m .}");
	    query.append(" } ");
	    
	    List<String[]> results = getStrings(query.toString(),new String[]{"E","Y","Z","D","M"});
	    return read(results,uri);
	} 
	
	public List<User> getUsers(Set<URI> experts) {
		List<User> expertObjs = new ArrayList<User>();
		Iterator<URI> itr = experts.iterator();
		while(itr.hasNext()){
			expertObjs.add(getUser(itr.next()));
		}
		return expertObjs;
	}
	
	/**
	 * @author Anup
	 * @param uri : uri of the VIVO expert
	 * @return list containing major fields and gradschools of the expert.
	 * @throws URISyntaxException
	 */
	public List<String[]> getEducationalBackground(URI uri) {
		StringBuffer query = new StringBuffer("SELECT (str(?f) AS ?F) ?O WHERE {");
		query.append("<" + uri.toString() +"> vivo:educationalTraining ?Y .");
		query.append("OPTIONAL {?Y vivo:majorField ?f .}");
		query.append("OPTIONAL {?Y vivo:trainingAtOrganization ?O .}");
		query.append("}");
		List<String[]> results = getStrings(query.toString(),new String[]{"F","O"});
		return results;
	}
	
	public String getLabel(URI uri){
		StringBuffer query = new StringBuffer("SELECT (str(?n) AS ?Name) WHERE " + "{");
		query.append("<" + uri.toString() + "> rdfs:label ?n .");
	    query.append(" }");
	    
	    List<String[]> results = getStrings(query.toString(),new String[]{"Name"});
	    return results.get(0)[0];
	}
	
	public String getImage(URI uri){
		StringBuffer query = new StringBuffer("SELECT ?X WHERE " + "{");
		query.append("<" + uri.toString() + "> vitro-public:mainImage ?y .");
		query.append("?y vitro-public:thumbnailImage ?z .");
		query.append("?z vitro-public:downloadLocation ?a .");
		query.append("?a vitro-public:directDownloadUrl ?X .");
	    query.append(" }");
	    
	    List<String> results = getStrings(query.toString());
	    
	    String imageLoc = null;
	    
	    if(results.size() == 0){
	    		imageLoc = "http://placehold.it/90x130&text=no+image";
	    }else{
	    		imageLoc = "http://vivo.ufl.edu" + results.get(0);
	    }
	    
	    return imageLoc;
	}
	
	public boolean shouldAffiliate(URI uri, URI expert, URI departmentURI){
		StringBuffer query = new StringBuffer();
		query.append(" FILTER NOT EXISTS { ");
		query.append("<" + uri.toString() + "> vivo:authorInAuthorship ?cn ." );
		query.append("?cn vivo:linkedInformationResource ?pub .");
		query.append("?pub vivo:informationResourceInAuthorship ?cn2 .");
		query.append("?cn2 vivo:linkedAuthor <" + expert.toString() + "> .");
		query.append(" FILTER(<" + uri.toString() + "> != <" + expert.toString() + ">)");
		query.append(" FILTER(?cn != ?cn2)");
		query.append("} ");
		query.append(" FILTER EXISTS { ");
		query.append("<" + expert.toString() + "> vivo:personInPosition ?P .");
		query.append("?P vivo:positionInOrganization <" + departmentURI.toString() + "> .");
		query.append("<" + departmentURI.toString() + "> a vivo:AcademicDepartment .");
		query.append(" ?P vivo:dateTimeInterval ?dti .");
		query.append(" FILTER NOT EXISTS {?dti vivo:end ?edti}");
	    query.append(" } ");	
	    
	    return ask(query.toString());
	    
	}
	
}
