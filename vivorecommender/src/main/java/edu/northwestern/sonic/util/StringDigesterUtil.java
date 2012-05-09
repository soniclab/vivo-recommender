package edu.northwestern.sonic.util;

import java.security.MessageDigest;

/**
 * @author Anup
 * This class helps encrypt strings using java MessageDigest
 */
public class StringDigesterUtil {
	
	private static final char digits[] =
		{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 
		   'E', 'F'};

	/**
	 * @param bytes
	 * @return String
	 * Convert byte array to hex string.
	 */
	private static String byteArrayToHexString(byte[] bytes)
	{
		StringBuffer hexString = new StringBuffer(bytes.length);
		for(int i =0; i < bytes.length; i++){
			hexString.append(digits[(bytes[i] & 0xF0) >> 4]);
			hexString.append(digits[bytes[i] & 0x0F]);
		}
		return hexString.toString();
	}
	
	/**
	 * @param password
	 * @return String
	 * Converts plain text password to hashString using MessageDigest.
	 */
	public static String digest(String str){
		try{
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(str.getBytes("UTF-8"));
			byte[] mdBytes = md.digest();
			String hashString = byteArrayToHexString(mdBytes);
			return hashString;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	/**
	 * @param args
	 * Main method to run this class independently if need be.
	 */
	public static void main(String[] args) {
		String str = args[0];
		System.out.println(str + " --> " + digest(str));

	}

}
