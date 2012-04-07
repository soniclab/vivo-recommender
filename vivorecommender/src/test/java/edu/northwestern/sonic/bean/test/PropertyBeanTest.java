package edu.northwestern.sonic.bean.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.northwestern.sonic.bean.PropertyBean;

public class PropertyBeanTest {

	static PropertyBean propertyBean;
	
	@Before
	public void setUp() throws Exception {
		propertyBean = PropertyBean.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	public void testGetInstance() {
		assertNotNull("Object not null",PropertyBean.getInstance());
		assertEquals("same object",propertyBean,PropertyBean.getInstance());
	}

	@Ignore
	public void testGetService() {
		assertEquals("service check","http://ciknow1.northwestern.edu:3030/UF-VIVO/query",propertyBean.getService());
	}
}
