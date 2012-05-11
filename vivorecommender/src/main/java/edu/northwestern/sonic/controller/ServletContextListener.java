package edu.northwestern.sonic.controller;

import java.io.FileReader;
import java.util.Properties;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.Jena;
import com.hp.hpl.jena.query.ARQ;

import edu.northwestern.sonic.persistence.TDBAccessObject;
import edu.northwestern.sonic.persistence.TDBTransaction;
import edu.northwestern.sonic.util.LogUtil;

/**
 * Servlet Context Listener
 * 2011-05-16 HJD initial implementation
 *
 * @author Hugh
 * 
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {
	private static Logger log = LogUtil.log;

	/**
	 * called once on application start-up
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.info("contextInitialized method called");
		LogUtil.logVersion(Jena.NAME, Jena.VERSION, Jena.BUILD_DATE);
		LogUtil.logVersion(ARQ.NAME, ARQ.VERSION, ARQ.BUILD_DATE);
		loadTDB();	
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("contextDestroyed method called");
	}
	
	/**
	 * Read the property file and set TDB dataset
	 */
	private void loadTDB(){
		Properties properties = new Properties();
		try{
			properties.load(getClass().getResourceAsStream("/vivorecommender.properties"));
			TDBAccessObject.setDataset(properties.getProperty("directoryTDB"));
		}catch(Exception e){
			log.error("Could not connect to dataset : Set directoryTDB path " +
					"in vivorecommender.properties", e);
		}

	}

}
