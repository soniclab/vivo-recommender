package edu.northwestern.sonic.sparql;

import java.util.List;
import org.apache.log4j.Logger;

import edu.northwestern.sonic.bean.User;

public class SparqlEngine {

	Logger log = Logger.getLogger(SparqlEngine.class);
	
	private ReasonerEngine reasonerEngine = null;

	public SparqlEngine(){
		reasonerEngine = ReasonerEngine.getInstance();
	}
	public User read(List<String[]> results, String email){
		User user = null;
		if(results.size() > 0){
			user = new User();
			user.setEmail(email);
			String[] details = results.get(0);
			user.setUri(details[0]);
			user.setName(details[1]);
			user.setDepartment(details[2]);
			user.setDepartmentURI(details[3]);
		}
		return user;
	}
	
	public List<String> identifyExpertsByResearchArea(String subject){
		log.info("IN identification by Research Area (single keyword)");
		
		StringBuffer query = new StringBuffer("SELECT ?X WHERE " + "{");
		query.append(" ?X vivo:hasResearchArea ?ResearchArea .");
		query.append(" ?ResearchArea rdfs:label ?area .");
		query.append(" filter regex(?area, \"" + subject + "\",\"i\") .");
	    query.append(" } ");
		 
		return reasonerEngine.getStrings(query.toString());
	}
	
	public List<String> identifyExpertsByKeyword(String subject){
		log.info("IN identification by Keyword (single keyword)");
		
		StringBuffer query = new StringBuffer("SELECT ?X WHERE " + "{");
		query.append(" ?X vivo:authorInAuthorship ?Y .");
		query.append(" ?Y vivo:linkedInformationResource ?Z .");
		query.append(" ?Z vivo:freetextKeyword ?keyword .");
		query.append(" filter regex(?keyword, \"" + subject + "\",\"i\") .");
	    query.append(" } ");
		 
		return reasonerEngine.getStrings(query.toString());
	}
	
	public List<String> identifyExpertsBySubjectArea(String subject){
		log.info("IN identification by Subject Area (single keyword)");
		
		StringBuffer query = new StringBuffer("SELECT ?X WHERE " + "{");
		query.append(" ?X vivo:authorInAuthorship ?pub .");
		query.append(" ?pub vivo:hasSubjectArea ?area .");
		query.append(" ?area rdfs:label ?label .");
		query.append(" filter regex(?label, \"" + subject + "\",\"i\") .");
	    query.append(" } ");
		 
		return reasonerEngine.getStrings(query.toString());
	}
	
	public User getUser(String email){
		log.info("IN getUser");
		
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
	    
	    List<String[]> results = reasonerEngine.getStrings(query.toString(),new String[]{"X","Y","Z","D"});
	    return read(results,email);
	} 
	
	public String getLabel(String uri){
		log.info("IN getLabel");
		
		StringBuffer query = new StringBuffer("SELECT ?Name WHERE " + "{");
		query.append("<" + uri + "> rdfs:label ?Name .");
	    query.append(" }");
	    
	    List<String[]> results = reasonerEngine.getStrings(query.toString(),new String[]{"Name"});
	    return results.get(0)[0];
	}
	
	public String getImage(String uri){
		log.info("IN getImage");
		
		StringBuffer query = new StringBuffer("SELECT ?X WHERE " + "{");
		query.append("<" + uri + "> vitro-public:mainImage ?y .");
		query.append("?y vitro-public:thumbnailImage ?z .");
		query.append("?z vitro-public:downloadLocation ?a .");
		query.append("?a vitro-public:directDownloadUrl ?X .");
	    query.append(" }");
	    
	    List<String> results = reasonerEngine.getStrings(query.toString());
	    
	    String imageLoc = null;
	    
	    if(results.size() == 0){
	    		imageLoc = "http://placehold.it/90x130&text=no+image";
	    }else{
	    		imageLoc = "http://vivo.ufl.edu" + results.get(0);
	    }
	    
	    return imageLoc;
	}
	
	public boolean shouldAffiliate(String seekerURI, String expertURI, String departmentURI){
		log.info("IN getAffliation");
		
		StringBuffer query = new StringBuffer("ASK {");
		query.append(" FILTER NOT EXISTS { ");
		query.append("<" + seekerURI + "> vivo:authorInAuthorship ?cn ." );
		query.append("?cn vivo:linkedInformationResource ?pub .");
		query.append("?pub vivo:informationResourceInAuthorship ?cn2 .");
		query.append("?cn2 vivo:linkedAuthor <" + expertURI + "> .");
		query.append(" FILTER(<" + seekerURI + "> != <" + expertURI + ">)");
		query.append(" FILTER(?cn != ?cn2)");
		query.append("} ");
		query.append(" FILTER EXISTS { ");
		query.append("<" + expertURI + "> vivo:personInPosition ?P .");
		query.append("?P vivo:positionInOrganization <" + departmentURI + "> .");
		query.append("<" + departmentURI + "> a vivo:AcademicDepartment .");
		query.append(" ?P vivo:dateTimeInterval ?dti .");
		query.append(" FILTER NOT EXISTS {?dti vivo:end ?edti}");
	    query.append(" }} ");	
	    
	    return reasonerEngine.ask(query.toString());
	    
	}
	
	public List<String[]> getCoAuthors(String expert){
		log.info("IN getCoAuthors");
		
		List<String[]> results = null;
		StringBuffer query = new StringBuffer("SELECT ?X WHERE { ");
		query.append("<" + expert + "> vivo:authorInAuthorship ?cn .");
		query.append("?cn vivo:linkedInformationResource ?pub .");
		query.append("?pub vivo:informationResourceInAuthorship ?cn2 .");
		query.append("?cn2 vivo:linkedAuthor ?X .");
		query.append("FILTER (<" + expert + "> != ?X)");
		query.append(" } ");
		
		results = reasonerEngine.getStrings(query.toString(), new String[]{"X"});
		return results;
	}
}
