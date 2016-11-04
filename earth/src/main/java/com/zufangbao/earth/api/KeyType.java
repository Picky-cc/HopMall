package com.zufangbao.earth.api;


/**
 * usbkey类型
 * 
 * @author zjm
 *
 */
public enum KeyType {

	/** 实体 */
	Entity("enum.key-type.entity"),

	/** 电子 */
	Electronic("enum.key-type.electronic");

	private String key;

	/**
	 * @param key
	 */
	private KeyType(String key) {
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

	public static KeyType fromValue(int value) {

		for (KeyType item : KeyType.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return Entity;
	}
}
