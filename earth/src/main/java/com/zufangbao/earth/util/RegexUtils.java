package com.zufangbao.earth.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	
	private static boolean isMatch(String regex, String orginal){  
	        if (orginal == null || orginal.trim().equals("")) {  
	            return false;  
	        }  
	        Pattern pattern = Pattern.compile(regex);  
	        Matcher isNum = pattern.matcher(orginal);  
	        return isNum.matches();  
	    }  
	
	public static boolean isDecimal(String orginal){  
        return isMatch("[-]{0,1}\\d+\\.\\d+", orginal);  
    } 
	
	public static boolean isCurrency(String original){
		 return isMatch("[-]{0,1}\\d+(\\.\\d{1,2})?", original);  
	}
	
	public static boolean isNumberic(String orginal){
		return isMatch("([-]{0,1}[1-9]\\d*)|[0]", orginal);
	}
	
	public static boolean isRealNumber(String orginal){
		
		return isNumberic(orginal) || isDecimal(orginal);
	}

	public static boolean isMobile(String mobile) {
		
		if (null == mobile){
			return false;
		}
		
		return isMatch("\\d{11}", mobile);
	}
	
}
