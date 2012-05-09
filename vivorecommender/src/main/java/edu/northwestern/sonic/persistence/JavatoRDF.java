package edu.northwestern.sonic.persistence;

import org.apache.log4j.Logger;

import thewebsemantic.Bean2RDF;
import thewebsemantic.NotFoundException;
import thewebsemantic.RDF2Bean;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import edu.northwestern.sonic.model.User;
import edu.northwestern.sonic.util.LogUtil;

/**
 * @author Anup
 * The purpose of this class is to convert java bean to RDF and vice versa.
 */
public class JavatoRDF {
	
	private static Model m;
	private static Bean2RDF writer;
	private static RDF2Bean reader;
	private static Logger log = LogUtil.log;
	
	public static Model write(User ego){
		m = ModelFactory.createOntologyModel();
		writer = new Bean2RDF(m);
		writer.save(ego);
		return m;
	}
	
	public static User read(Model model,String id){
        m = ModelFactory.createOntologyModel();
        m.add(model);
		User ego = null;
		reader = new RDF2Bean(m);
		try{
			ego = reader.load(User.class,id);
		}catch(NotFoundException e){
			log.error("Did not find Ego in TDB");
		}
		return ego;
	}
}
