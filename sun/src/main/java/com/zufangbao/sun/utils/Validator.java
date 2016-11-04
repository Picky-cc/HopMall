/**
 * 
 */
package com.zufangbao.sun.utils;

import java.util.Map;

/**
 * @author wukai
 *
 */
public interface Validator {

	public boolean valid();
	
	public Map<String,Object> errorMap();
	
	public boolean newObject();
}
