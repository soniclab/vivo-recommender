package edu.northwestern.sonic.test;

import org.junit.runner.RunWith;

import com.mycila.junit.concurrent.Concurrency;
import com.mycila.junit.concurrent.ConcurrentSuiteRunner;

/**
 * VIVO functional test suite
 * 
 * @author Hugh
 * 
 */

@RunWith(ConcurrentSuiteRunner.class)
@Concurrency(value = 10)
public class ConcurrentFunctionalTests extends FunctionalTests {
	// the class remains completely empty,
	// being used only as a holder for the above annotations
}