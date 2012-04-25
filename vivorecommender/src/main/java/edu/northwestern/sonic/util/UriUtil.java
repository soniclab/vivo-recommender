/**
 * 
 */
package edu.northwestern.sonic.util;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Hugh
 *
 */
public class UriUtil {
	
	/**
	 * safe URI constructor from String
	 * logs and returns null on parse failures
	 * @param uri
	 * @return URI on success, null on parse failure
	 */
	public static URI safeUriFactory(final String uri) {
		URI returnValue = null;
		try {
			returnValue = new URI(uri);
		} catch (URISyntaxException e) {
			LogUtil.log.trace(e, e);
		}
		return returnValue;		
	}

}
