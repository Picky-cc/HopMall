package com.zufangbao.sun.yunxin.entity;

/**
 * 
 * @author louguanyang
 *
 */
public enum DictionaryCode {
	SMS_URL("SMS_URL"),
	REMIND_DAY("REMIND_DAY"),
	SMS_ALLOW_SEND_FLAG("SMS_ALLOW_SEND_FLAG"),
	PLATFORM_PRI_KEY("PLATFORM_PRI_KEY");
	
	private String code;

	private DictionaryCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}
