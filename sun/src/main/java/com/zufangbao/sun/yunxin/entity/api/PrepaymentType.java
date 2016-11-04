package com.zufangbao.sun.yunxin.entity.api;

/**
 * 提前还款类型
 * 
 * @author louguanyang
 *
 */
public enum PrepaymentType {
	
	/** 全部提前还款 **/
	ALL("enum.prepayment-type.all");
	
	private String key;

	public String getKey() {
		return key;
	}

	private PrepaymentType(String key) {
		this.key = key;
	}

}
