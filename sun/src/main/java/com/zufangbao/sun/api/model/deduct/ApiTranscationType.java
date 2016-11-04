package com.zufangbao.sun.api.model.deduct;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

public enum ApiTranscationType {

	DEDUCT("enum.api-transcation-type.deduct"),
	LOAN("enum.api-transcation-type.loan");
	
	private String key;

	/**
	 * @param key
	 */
	private ApiTranscationType(String key) {
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
	public int getOrdinal() {
		return this.ordinal();
	}

	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}

	public static ApiTranscationType fromValue(int value) {

		for (ApiTranscationType item : ApiTranscationType.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}
	
	public  String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
	
}
