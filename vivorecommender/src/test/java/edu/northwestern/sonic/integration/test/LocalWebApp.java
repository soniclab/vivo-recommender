package edu.northwestern.sonic.integration.test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * holder for URL of local web application
 * 
 * @author Hugh
 * 
 */

public final class LocalWebApp {
	private static URL url = null;
	static {
		try {
			setUrl(new URL("http://localhost:8080/vivorecommender/"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private LocalWebApp() {
		// singleton
	}
	
	/**
	 * @return the url
	 */
	public static URL getUrl() {
		return url;
	}
	
	/**
	 * @param url the url to set
	 */
	private static void setUrl(final URL url) {
		LocalWebApp.url = url;
	}
	
}