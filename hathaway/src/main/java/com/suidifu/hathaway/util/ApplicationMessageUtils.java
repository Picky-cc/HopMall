/**
 * 
 */
package com.suidifu.hathaway.util;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author wukai
 *
 */
public class ApplicationMessageUtils {
	
	private static final String MSG_BASE_NAME = "ApplicationMessage";
	
	private static final Log logger = LogFactory.getLog(ApplicationMessageUtils.class);
	
	public static String getChineseMessage(String key){
		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(MSG_BASE_NAME, Locale.CHINESE);
			return resourceBundle.getString(key);
		} catch(Exception e){
			logger.error("#ApplicationMessageUtils.getChineseMessage occur error, with key["+key+"].");
			e.printStackTrace();
			return StringUtils.EMPTY;
		}
	}
}
