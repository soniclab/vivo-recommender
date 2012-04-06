package edu.northwestern.sonic.dataaccess.vivo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.northwestern.sonic.model.User;

public class Researcher extends VivoSparqlService {
	
	public User read(List<String[]> results, String email) throws URISyntaxException {
		User user = null;
		if(results.size() > 0){
			user = new User();
			user.setEmail(email);
			String[] details = results.get(0);
			user.setUri(new URI(details[0]));
			user.setName(details[1]);
			user.setDepartment(details[2]);
			user.setDepartmentURI(new URI(details[3]));
		}
		return user;
	}
		
	public User getUser(String email) throws URISyntaxException{
		StringBuffer query = new StringBuffer("SELECT DISTINCT ?X (str(?name) AS ?Y) (str(?l) AS ?Z) ?D WHERE " + "{");
		query.append(" ?X a vivo:FacultyMember .");
		query.append(" ?X vivo:primaryEmail \"" + email + "\".");
		query.append(" ?X rdfs:label ?name .");
		query.append(" ?X vivo:personInPosition ?P .");
		query.append(" ?P vivo:positionInOrganization ?D .");
		query.append(" ?D a vivo:AcademicDepartment .");
		query.append(" ?P vivo:dateTimeInterval ?dti .");
		query.append(" FILTER NOT EXISTS {?dti vivo:end ?edti}");
		query.append(" ?D rdfs:label ?l .");
	    query.append(" } LIMIT 1");
	    
	    List<String[]> results = getStrings(query.toString(),new String[]{"X","Y","Z","D"});
	    return read(results,email);
	} 
	
	public String getLabel(URI uri){
		StringBuffer query = new StringBuffer("SELECT ?Name WHERE " + "{");
		query.append("<" + uri.toString() + "> rdfs:label ?Name .");
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
	
	public Set<URI> getCoAuthors(URI expertURI) throws URISyntaxException {
		StringBuffer whereClause = new StringBuffer("<" + expertURI.toString() + "> vivo:authorInAuthorship ?cn .");
		whereClause.append("?cn vivo:linkedInformationResource ?pub .");
		whereClause.append("?pub vivo:informationResourceInAuthorship ?cn2 .");
		whereClause.append("?cn2 vivo:linkedAuthor ?X .");
		whereClause.append("FILTER (<" + expertURI.toString() + "> != ?X)");
		return getDistinctSortedURIs(whereClause.toString());
	}
	
	// not used anywhere yet
	public Set<URI> getCoAuthors(Set<URI> uris) throws URISyntaxException {
		TreeSet<URI> returnValue = new TreeSet<URI>();
		for(URI uri : uris)
			returnValue.addAll(getCoAuthors(uri));
		return returnValue;	
	}
	
}
