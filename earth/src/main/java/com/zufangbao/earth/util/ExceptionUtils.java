/**
 * 
 */
package com.zufangbao.earth.util;

import com.zufangbao.gluon.opensdk.BaseExceptionUtils;

/**
 * @author wukai
 *
 */
public abstract class ExceptionUtils extends BaseExceptionUtils {

	public static String getCauseErrorMessage(Exception exception) {
		if(exception.getCause() != null){
			return exception.getCause().getMessage();
		}
		return exception.getMessage();
	}
}
