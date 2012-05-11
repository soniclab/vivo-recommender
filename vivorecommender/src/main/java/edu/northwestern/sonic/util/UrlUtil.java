/**
 * URL factory from known valid URL strings 
 */
package edu.northwestern.sonic.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Hugh
 *
 */
public class UrlUtil {
	
	/**
	 * safe URL constructor from String
	 * logs and returns null on parse failures
	 * @param url as String
	 * @return URL on success, null on parse failure
	 */
	public static URL safeUrlFactory(final String url) {
		URL returnValue = null;
		try {
			returnValue = new URL(url);
		} catch (MalformedURLException e) {
			LogUtil.log.trace(e, e);
		}
		return returnValue;		
	}

}
