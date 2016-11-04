package com.zufangbao.gluon.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author louguanyang
 *
 */
public class RequestUtil {

	public static Map<String, String> getRequestParams(HttpServletRequest request) {
		Map<String, String> param = new HashMap<String, String>();
		if(request != null) {
			Set<String> paramKeySet = request.getParameterMap().keySet();
			for (String key : paramKeySet) {
				param.put(key, request.getParameter(key));
			}
		}
		return param;
	}
	
}
