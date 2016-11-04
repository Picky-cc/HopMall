/**
 * 
 */
package com.zufangbao.sun;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

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
