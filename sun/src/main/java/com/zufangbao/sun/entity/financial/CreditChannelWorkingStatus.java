package com.zufangbao.sun.entity.financial;

import com.zufangbao.sun.utils.ApplicationMessageUtils;


/**
 * 付款通道工作状态
 * @author Lux
 */
public enum CreditChannelWorkingStatus {
	
	ON("enum.credit-channel-working-status.on"),
	OFF("enum.credit-channel-working-status.off"),
	NOTLINK("enum.credit-channel-working-status.notlink");
	
	private String key;

	private CreditChannelWorkingStatus(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}

	public int getOrdinal() {
		return ordinal();
	}
	
	public static CreditChannelWorkingStatus formValue(int value){
		for(CreditChannelWorkingStatus item:CreditChannelWorkingStatus.values()){
			if(item.ordinal() == value){ 
				return item;
			}
		}
		return null;
	}
	
	public  String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
}
