package com.zufangbao.sun.yunxin.entity.api;

/**
 * 接口状态
 * @author zhanghongbing
 *
 */
public enum ApiStatus {

	/** 关闭 **/
	CLOSED("enum.api-status.closed"),
	/** 启用 **/
	ENABLE("enum.api-status.enable");
	
	private String key;
	
	private ApiStatus(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
