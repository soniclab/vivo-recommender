package edu.northwestern.sonic.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import edu.northwestern.sonic.network.test.NetworkTest;
import edu.northwestern.sonic.util.test.ArraysUtilTest;
import edu.northwestern.sonic.util.test.StringUtilTest;

/**
 * VIVO unit test suite
 * 
 * @author Hugh
 * 
 */

@RunWith(Suite.class)

@Suite.SuiteClasses( { 
	StringUtilTest.class,
	ArraysUtilTest.class,
	NetworkTest.class,
	})
	
public class UnitTests {
	// the class remains completely empty,
	// being used only as a holder for the above annotations
}