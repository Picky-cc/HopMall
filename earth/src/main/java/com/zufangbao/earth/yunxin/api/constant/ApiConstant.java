package com.zufangbao.earth.yunxin.api.constant;


public class ApiConstant {

	/**
	 * 参数功能代码key
	 */
	public static final String PARAMS_FN_KEY = "fn";
	/**
	 * 参数数字签名sign
	 */
	public static final String PARAMS_SIGN = "sign";
	/**
	 * 参数商户号merId
	 */
	public static final String PARAMS_MER_ID = "merId";
	/**
	 * 参数商户密钥
	 */
	public static final String PARAMS_SECRET = "secret";
	
	/**
	 * 参数功能代码key(带连接符)
	 */
	public static final String PARAMS_FN_KEY_WITH_COMBINATORS = "fn=";
	
	public static class ApiUrlConstant {
		
		/**
		 * 查询类接口
		 */
		public static final String URL_QUERY_API = "/api/query";
		
		/**
		 * 修改类接口
		 */
		public static final String URL_MODIFY_API ="/api/modify";
		
		/**
		 * 指令类接口
		 */
		public static final String URL_COMMAND_API ="/api/command";
		
	}
	
}
