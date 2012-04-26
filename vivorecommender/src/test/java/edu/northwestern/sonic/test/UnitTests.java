package edu.northwestern.sonic.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import edu.northwestern.sonic.dataaccess.test.BindingsTest;
import edu.northwestern.sonic.dataaccess.test.ListFilterTest;
import edu.northwestern.sonic.network.test.NetworkTest;
import edu.northwestern.sonic.util.test.ArraysUtilTest;
import edu.northwestern.sonic.util.test.SetUtilTest;
import edu.northwestern.sonic.util.test.StringUtilTest;
import edu.northwestern.sonic.util.test.UriUtilTest;

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
	SetUtilTest.class,
	UriUtilTest.class,
	NetworkTest.class,
	ListFilterTest.class,
	BindingsTest.class,
	})
	
public class UnitTests {
	// the class remains completely empty,
	// being used only as a holder for the above annotations
}