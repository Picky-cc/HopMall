package com.zufangbao.sun.utils;

import org.apache.commons.lang.StringUtils;
public class SensitiveInfoUtils {

	
		  private static final int ID_CARD_AND_ACCOUNT_NO_MIN_LENGTH = 7;

		/** 
	     * 前三位，后四位，其他隐藏<例子:138******1234> 
	     *  
	     * @param num 
	     * @return 
	     */  
	    public static String desensitizationString(String num) {  
	       if(StringUtils.isBlank(num)){
	    	   return "";
	       }
	       if(StringUtils.length(num) <=ID_CARD_AND_ACCOUNT_NO_MIN_LENGTH){
	    	   return num;
	       }
	        return StringUtils.left(num, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*"), "***"));  
	    }  


}
