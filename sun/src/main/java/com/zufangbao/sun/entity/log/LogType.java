package com.zufangbao.sun.entity.log;

/**
 * 
 * @author zjm
 *
 */
public enum LogType {

	/** 登陆日志 */
	LOGIN("enum.log-type.login"),

	/** 操作日志 */
	OPERATE("enum.log-type.operate");

	private String key;

	/**
	 * @param key
	 */
	private LogType(String key) {
		this.key = key;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Get alias of order status
	 * 
	 * @return
	 */
	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}

}
