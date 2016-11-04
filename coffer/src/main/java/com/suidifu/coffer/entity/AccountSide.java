package com.suidifu.coffer.entity;

public enum AccountSide {

	/** 贷 */
	CREDIT,

	/** 借 */
	DEBIT;

	public static AccountSide fromOrdinal(Integer ordinal) {
		if(null == ordinal) {
			return null;
		}

		for (AccountSide item : AccountSide.values()) {
			if (item.ordinal() == ordinal) {
				return item;
			}
		}
		return null;
	}

}
