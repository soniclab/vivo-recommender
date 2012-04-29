package edu.northwestern.sonic.integration.test;

import java.net.MalformedURLException;
import java.net.URL;

public class ITest {
	private static URL url = null;
	
	static {
		try {
			setUrl("http://ciknow1.northwestern.edu/vivorecommender/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set the base application URL
	 * @param url as a String
	 * @throws MalformedURLException
	 */
	protected static void setUrl(String url) throws MalformedURLException {
		ITest.url = new URL("http://ciknow1.northwestern.edu/vivorecommender/"); 
	}

	/**
	 * @return the application url
	 */
	protected static URL getUrl() {
		return url;
	}

}
