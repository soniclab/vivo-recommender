package edu.northwestern.sonic.dataaccess;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

import edu.northwestern.sonic.util.LogUtil;
/**
 * wraps Jena QueryFactory
 * logs queries
 *
 * @author Hugh
 *
 */
public class LoggingQueryFactory {
	private static Logger log = LogUtil.log;

	public static Query create(String queryString) {
		log.info("QUERY:\n" + queryString);
		return(QueryFactory.create(queryString));
	}
	 
}
