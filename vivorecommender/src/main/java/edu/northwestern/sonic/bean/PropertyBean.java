package edu.northwestern.sonic.bean;

import java.io.FileReader;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyBean {

	Logger log = Logger.getLogger(PropertyBean.class);
	
	private String service = null;
	private Properties properties = null;
	private static PropertyBean propertyBean = null;
	
	private PropertyBean(){
		properties = new Properties();
		try{
			properties.load(new FileReader("properties/vivorecommender.properties"));
		}catch(Exception e){
			log.error(e,e);
		}
		service = properties.getProperty("service");
	}
	
	public static PropertyBean getInstance(){
		if(propertyBean == null){
			propertyBean = new PropertyBean();
		}
		return propertyBean;
	}
	
	public String getService(){
		return service;
	}
}
