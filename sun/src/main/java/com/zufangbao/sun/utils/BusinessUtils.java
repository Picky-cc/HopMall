package com.zufangbao.sun.utils;

import org.apache.commons.lang.StringUtils;

public class BusinessUtils {
	public static String get_merId_clearingNo(String merId, String clearingNo){
		if(StringUtils.isEmpty(merId)){
			return StringUtils.EMPTY;
		}
		if(StringUtils.isEmpty(clearingNo)){
			return merId;
		}
		return merId+"_"+clearingNo;
	}
}
