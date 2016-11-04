/**
 * 
 */
package com.suidifu.hathaway;

import com.suidifu.hathaway.util.ApplicationMessageUtils;

/**
 * @author wukai
 *
 */
public interface BasicEnum {

	public String getKey();
	default String getChineseName(){
		return ApplicationMessageUtils.getChineseMessage(getKey());
	}
	
}
