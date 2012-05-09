package edu.northwestern.sonic.util.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.northwestern.sonic.util.StringDigesterUtil;

public class StringDigesterUtilTest {
	
	
	private static final String code = "8151325DCDBAE9E0FF" +
			"95F9F9658432DBEDFDB209";
	
	@Test
	public void testDigest() {
		assertEquals("Encryption match : ", code,StringDigesterUtil.digest
				("sample"));
	}

}
