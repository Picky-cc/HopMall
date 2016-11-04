package com.suidifu.hathaway.ledgerbook.entity;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
public interface HashableEntity {

	public String[] sortedFieldNames();
	
	public List<Field> sortedHashFields();
	
	public default List<Field> sortedHashFields(Class Clazz) {
		String[] sortedFieldNames = this.sortedFieldNames();
		List<Field> fieldList = new ArrayList<Field>();
		
		for(String fieldName : sortedFieldNames) {
			try {
				Field field = Clazz.getDeclaredField(fieldName);
				fieldList.add(field);
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}
		
		return fieldList;
	}
	
	
	public default String flattenValueString() {
		List<Field> sortFields=sortedHashFields();
		List<String> fieldNameList = new ArrayList<String>();
		for (Field field:sortFields) {
			field.setAccessible(true);
			try {
				Object val = field.get(this);	
				String result = "";
				
				if (val == null) result="NULL";
				
				if(val instanceof Date){
					result = ((Date)(val)).getTime()+"";
				}
				
				fieldNameList.add(result);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		String flattenStr = StringUtils.join(fieldNameList, ';');
		return flattenStr;
	}
	
	
	public static String calculate(List<? extends HashableEntity> hashableEntities, String proceedHashValue) {
		return calculate(hashableEntities, proceedHashValue, HashAlgorithmType.SHA_256);
	}
	
	
	public static  String calculate(List<? extends HashableEntity> hashableEntities, String proceedHashValue, HashAlgorithmType algorithm) {
		if(hashableEntities.size() == 0) return null;
		String hashValue="";
		if(StringUtils.isEmpty(proceedHashValue)==false) 
			hashValue = proceedHashValue;
		MessageDigest hashAlgorithm = getDigest(algorithm.getKey()); 
		
		for(HashableEntity ledgerItem:hashableEntities) {
			hashValue=hashValue+";";
			String flattenStr=ledgerItem.flattenValueString();
			String preHashString =hashValue+flattenStr;
			byte[] b = hashAlgorithm.digest(preHashString.getBytes());
			hashValue = Hex.encodeHexString(b);
		}
		
		return hashValue;
	}
	
	public static  MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch(NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
