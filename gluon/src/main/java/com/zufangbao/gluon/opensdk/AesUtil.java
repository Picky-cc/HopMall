/**
 * 
 */
package com.zufangbao.gluon.opensdk;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.zufangbao.gluon.exception.CommonException;
import com.zufangbao.gluon.spec.global.GlobalCodeSpec;

/**
 * @author wukai
 *
 */
public abstract class AesUtil {
	
	private static final String KEY_ALGORITHM = "AES";
	
	private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	
	private static final int KEY_SIZE  = 128;
	
	private static final String CHARSET_NAME = "UTF8";
	
	private static final char PATH_SPLIT_CHAR = '/';
	
	private static final char SPEC_REPLACE_CHAR = '@';
	
	public static String encrypt(String data,String key) throws CommonException{
		
		return encrypt(data, initKey(key));
	}
	public static String encrypt(String data,byte[] key) throws CommonException{
		
		data = data.replace(PATH_SPLIT_CHAR,SPEC_REPLACE_CHAR);
		
		byte[] result =  endecrypt(data.getBytes(), key, Cipher.ENCRYPT_MODE);
		
		return getEncoder().encodeToString(result);
		
	}
	public static String decrypt(String data,String key) throws CommonException{
		
		return decrypt(data, initKey(key));
	}
	public static String decrypt(String data,byte[] key) throws CommonException{
		
		try {
			data = data.replace(SPEC_REPLACE_CHAR,PATH_SPLIT_CHAR);
			
			byte[] result = getDecoder().decode(data);
			
			return new String(endecrypt(result, key, Cipher.DECRYPT_MODE),CHARSET_NAME);
			
		} catch (IOException e) {
			e.printStackTrace();
			
			throw new CommonException(GlobalCodeSpec.CODE_FAILURE);
		}
	}
	
	private static byte[] endecrypt(byte[] data,byte[] key, int cipherMode) throws CommonException{
		
		Cipher cipher;
		
		try {
			cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			
			cipher.init(cipherMode, toKey(key));
			
			return cipher.doFinal(data);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException  e) {
			
			e.printStackTrace();
			
			throw new CommonException(GlobalCodeSpec.CODE_FAILURE);
		}
		
	}
	private static Key toKey(byte[] key){
		
		return new SecretKeySpec(key, KEY_ALGORITHM);
		
	}
	public static byte[] initKey(String key){
		
		try {
			return getDecoder().decode(key);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return null;
		}
	}
	public static Encoder getEncoder(){
		
		return Base64.getUrlEncoder();
	}
	public static Decoder getDecoder(){
		
		return Base64.getUrlDecoder();
	}
	public static String initKey(){
		
		KeyGenerator keyGenerator;
		
		try {
			keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
			
			keyGenerator.init(KEY_SIZE);
			
			SecretKey key = keyGenerator.generateKey();
			
			return getEncoder().encodeToString(key.getEncoded());
			
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
			
			return null;
		}
		
	}

}
