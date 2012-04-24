package edu.northwestern.sonic.util;

import org.apache.log4j.Logger;

/**
 * singleton for logging
 *
 * @author Hugh
 *
 */
public final class LogUtil {
	public static Logger log = Logger.getLogger("vivo_recommender_logger");
	
	public static void printStackTrace() {
		log.info("STACK:");
		StackTraceElement[] stes = Thread.currentThread().getStackTrace();
		for(int i = 2; i < stes.length && i < 12; i++) {
			log.info(stes[i].toString());
		}
	}
	
	/**
	 * logger for jar versions
	 * @param name module name
	 * @param version module version
	 * @param date module build date
	 */
	public static void logVersion(String name, String version, String date){
		log.info(name + " version " + version + " built " + date);		
	}


}
