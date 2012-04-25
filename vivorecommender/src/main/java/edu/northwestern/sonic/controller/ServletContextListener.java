/**
 * C-IKnow Recommendation Servlet Context Listener
 * 2011-05-16 HJD initial implementation
 */
package edu.northwestern.sonic.controller;

import javax.servlet.ServletContextEvent;
import org.apache.log4j.Logger;

import com.hp.hpl.jena.Jena;
import com.hp.hpl.jena.query.ARQ;

import edu.northwestern.sonic.util.LogUtil;

/**
 * @author Hugh
 * 
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {
	private static Logger log = LogUtil.log;

	/**
	 * Create the SPARQL data engine once on application start-up
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.info("Ready to recommend!");
		LogUtil.logVersion(Jena.NAME, Jena.VERSION, Jena.BUILD_DATE);
		LogUtil.logVersion(ARQ.NAME, ARQ.VERSION, ARQ.BUILD_DATE);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("contextDestroyed method called");
	}

}
