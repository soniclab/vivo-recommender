package edu.northwestern.sonic.controller;

import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.Jena;
import com.hp.hpl.jena.query.ARQ;

import edu.northwestern.sonic.bean.PropertyBean;
import edu.northwestern.sonic.dataaccess.vivo.VivoSparqlService;
import edu.northwestern.sonic.persistence.TDBAccessObject;
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
	private static final PropertyBean propertyBean = PropertyBean.getInstance();

	/**
	 * called once on application start-up
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.info("contextInitialized method called");
		LogUtil.logVersion(Jena.NAME, Jena.VERSION, Jena.BUILD_DATE);
		LogUtil.logVersion(ARQ.NAME, ARQ.VERSION, ARQ.BUILD_DATE);
		try {
			String vivoUrlString = propertyBean.getService();
			URL vivoUrl = new URL(vivoUrlString);
			VivoSparqlService.setUrl(vivoUrl);
			log.info("VIVO Sparql service configured for " + vivoUrl);
		} catch (MalformedURLException e) {
			log.fatal(e, e);
		}
		loadTDB();	
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("contextDestroyed method called");
	}
	
	/**
	 * Read the property file and set TDB dataset
	 */
	private void loadTDB() {
		try {
			TDBAccessObject.setDataset(propertyBean.getTdbDirectory());
		} catch(Exception e) {
			log.error("Could not connect to dataset : Set directoryTDB path " +
					"in vivorecommender.properties", e);
		}
	}

}
