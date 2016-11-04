package com.zufangbao.sun.entity.financial;

import com.zufangbao.sun.utils.ApplicationMessageUtils;


/**
 * 通道工作状态
 * @author Lux
 */
public enum ChannelWorkingStatus {
	
	/**
	 * 未对接
	 */
	NOTLINK("enum.channel-working-status.NOTLINK"),
	/**
	 * 已开启
	 */
	ON("enum.channel-working-status.ON"),
	/**
	 * 已关闭
	 */
	OFF("enum.channel-working-status.OFF");
	
	private String key;

	private ChannelWorkingStatus(String key) {
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
	
	public static ChannelWorkingStatus formValue(int value){
		for(ChannelWorkingStatus item:ChannelWorkingStatus.values()){
			if(item.ordinal() == value){ 
				return item;
			}
		}
		return null;
	}
	
	public  String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
	
	public static ChannelWorkingStatus[] getValuesExceptNotLink(){
		return new ChannelWorkingStatus[] {ChannelWorkingStatus.ON, ChannelWorkingStatus.OFF};
	}
	
}
