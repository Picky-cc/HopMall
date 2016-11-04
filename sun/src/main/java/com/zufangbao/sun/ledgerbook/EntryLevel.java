package com.zufangbao.sun.ledgerbook;

public enum EntryLevel {
	
	/**1st */
	LVL1("enum.entry-account－level.lvl1"),

	/** 2nd */
	LVL2("enum.entry－account－level.lvl2"),
	
	/** 3rd */
	LVL3 ("enum.entry－account－level.lvl3")
	;
	

	private String key;

	/**
	 * @param key
	 */
	private EntryLevel(String key) {
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
	public int getOrdinal(){
		return this.ordinal();
	}
	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}

	public static EntryLevel fromValue(int value) {

		for (EntryLevel item : EntryLevel.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}

}
