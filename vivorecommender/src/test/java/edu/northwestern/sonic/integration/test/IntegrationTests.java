package edu.northwestern.sonic.integration.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * test suite
 * 
 * @author Hugh
 * 
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({ 
	HomePageIT.class,
	LoginPageIT.class,
	})
	
public class IntegrationTests {

}