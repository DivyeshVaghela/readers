package com.learning.readers.util;

import java.nio.charset.Charset;
import java.util.Random;

public class RandomGenerationUtil {

	public static String generateRandomString(int length) {
		
		byte[] array = new byte[length];
	    new Random().nextBytes(array);
	    String generatedString = new String(array, Charset.forName("US-ASCII"));
	 
	    return generatedString;
	}
}
