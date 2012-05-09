package edu.northwestern.sonic.persistence;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.tdb.TDBFactory;

/**
 * @author Anup
 * class to connect TDB, open dataset, close dataset and access model.
 */
public class TDBAccessObject {
	
	private static Dataset dataset;
	
	/**
	 * @param directory
	 * Sets dataset with given directory path for TDB
	 */
	public static void setDataset(String directory){
		TDBAccessObject.dataset = TDBFactory.createDataset(directory);
	}
	
	/**
	 * @return dataset
	 * Gets dataset
	 */
	protected static Dataset getDataset(){
		return TDBAccessObject.dataset;
	}
	
	/**
	 * Start a read transaction
	 */
	protected static synchronized void read(){
		TDBAccessObject.dataset.begin(ReadWrite.READ);
	}
	
	/**
	 * Start a write transaction
	 */
	protected static synchronized void write(){
		TDBAccessObject.dataset.begin(ReadWrite.WRITE);
	}
	
	/**
	 * Close dataset.
	 */
	protected static void close(){
		TDBAccessObject.dataset.close();
	}
	
	/**
	 * Commit to dataset - finish the transaction and make any changes permanent
	 */
	protected static void commit(){
		TDBAccessObject.dataset.commit();
	}
	
	/**
	 * Finish the transaction
	 */
	protected static void end(){
		TDBAccessObject.dataset.end();
	}
	
	/**
	 * Abort a transaction - finish the transaction and undo any changes
	 */
	protected static void abort(){
		TDBAccessObject.dataset.abort();
	}
	
	/**
	 * Reset - Reset internal state, releasing all datasets. No checking done,
	 *  do not call while TDB is execution queries or updates. Mainly for the
	 *  tests to have a known clean state.
	 */
	public static void reset(){
		TDBFactory.reset();
	}

}
