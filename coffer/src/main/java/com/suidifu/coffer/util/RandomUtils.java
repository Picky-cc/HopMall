/**
 * 
 */
package com.suidifu.coffer.util;

import java.util.Random;

/**
 * @author lute
 *
 */
public class RandomUtils {
	
	/**
	 * Numbers can be used to generate random number code
	 */
	private static final char[] constantNumbers = {
		'0','1','2','3','4','5','6','7','8','9'
	};
	
	/**
	 * Get random number code with given length
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNumberCode(int length) {
		
		StringBuilder stringBuilder = new StringBuilder(length);
		
		Random random = new Random();
		
		for(int i = 0; i < length; i++) {
			
			int index = random.nextInt(constantNumbers.length);
			stringBuilder.append(constantNumbers[index]);
			
		}
		
		return stringBuilder.toString();
	}

}
