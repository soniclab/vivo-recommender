package edu.northwestern.sonic.persistence;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.update.GraphStore;
import com.hp.hpl.jena.update.GraphStoreFactory;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import com.hp.hpl.jena.update.UpdateRequest;

/**
 * @author Anup
 * This class is a handler for query executions on TDB
 */
public class TDBTransaction extends TDBAccessObject{

	/**
	 * @param query
	 * Get default model from TDB
	 */
	public Model getDefaultModel(){
		read();
		Model m = ModelFactory.createOntologyModel();
		try{
			m.add(getDataset().getDefaultModel());
		}finally{
			end();
		}
		return m;
	}

	/**
	 * @param query
	 * Update state of the user
	 */
	public void updateState(Model newState){
		write();
		try{
			getDataset().getDefaultModel().add(newState);	
			commit(); // commit the changes to TDB
		}finally{
			end();
			newState.close();
		}
	}
}

