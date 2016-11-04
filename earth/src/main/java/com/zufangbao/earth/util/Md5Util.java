/**
 * 
 */
package com.zufangbao.earth.util;

import java.security.MessageDigest;

/**
 * @author lute
 *
 */
public class Md5Util {
	
	/**
	 * Encode string using MD5
	 * 
	 * @param value
	 * @return
	 */
	public static String encode(String value) {
		
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			
			byte[] bytesOfValue = value.getBytes("UTF-8");
			byte[] digest = messageDigest.digest(bytesOfValue);
			
			StringBuffer stringBuffer = new StringBuffer();
			for(int i = 0; i < digest.length; i++) {
				stringBuffer.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			return stringBuffer.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
