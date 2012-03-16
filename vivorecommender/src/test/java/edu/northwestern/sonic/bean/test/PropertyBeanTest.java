package edu.northwestern.sonic.bean.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
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

	@Test
	public void testGetInstance() {
		assertNotNull("Object not null",PropertyBean.getInstance());
		assertEquals("same object",propertyBean,PropertyBean.getInstance());
	}

	@Test
	public void testGetService() {
		assertEquals("service check","http://localhost:2020/sparql",propertyBean.getService());
	}
}
