package edu.northwestern.sonic.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * VIVO test suite
 * 
 * @author Hugh
 * 
 */

@RunWith(Suite.class)

@Suite.SuiteClasses( { 
	edu.northwestern.sonic.test.UnitTests.class,
	edu.northwestern.sonic.test.FunctionalTests.class,
	})
	
public class AllTests {
	// the class remains completely empty,
	// being used only as a holder for the above annotations
}