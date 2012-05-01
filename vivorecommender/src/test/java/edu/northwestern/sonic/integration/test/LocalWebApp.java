package edu.northwestern.sonic.integration.test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * holder for URL of local web app
 * 
 * @author Hugh
 * 
 */

public class LocalWebApp {
	private static URL url = null;
	static {
		try {
			setUrl(new URL("http://localhost:8080/vivorecommender/"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
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
	private static void setUrl(URL url) {
		LocalWebApp.url = url;
	}
	
}