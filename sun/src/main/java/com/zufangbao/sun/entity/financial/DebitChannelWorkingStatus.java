package com.zufangbao.sun.entity.financial;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

/**
 * 收款通道工作状态
 * @author Lux
 */
public enum DebitChannelWorkingStatus {
	
	ON("enum.debit-channel-working-status.on"),
	OFF("enum.debit-channel-working-status.off"),
	NOTLINK("enum.debit-channel-working-status.notlink");
	
	private String key;

	private DebitChannelWorkingStatus(String key) {
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
	
	public static DebitChannelWorkingStatus formValue(int value){
		for(DebitChannelWorkingStatus item:DebitChannelWorkingStatus.values()){
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
