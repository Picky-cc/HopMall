package com.zufangbao.sun.yunxin;

import com.zufangbao.sun.utils.ApplicationMessageUtils;


/**
 * 结清状态
 * 
 * @author louguanyang
 *
 */
public enum SettlementStatus {
	/**
	 * 未发生
	 */
	NOT_OCCURRED("enum.settlement-status.not-occurred"),
	/**
	 * 已创建
	 */
	CREATE("enum.settlement-status.create"),
	/**
	 * 待结清
	 */
	WAITING("enum.settlement-status.waiting"),
	/**
	 * 已结清
	 */
	DONE("enum.settlement-status.done");
	
	private String key;

	private SettlementStatus(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
	public int getOrdinal(){
		return this.ordinal();
	}
	
	
	public static SettlementStatus formValue(int value){
		for (SettlementStatus item : SettlementStatus.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
		
	}
	
	public String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
}
