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
		for(int i = 2; i < stes.length && i < 10; i++) {
			log.info(stes[i].toString());
		}
	}
}
