package com.zufangbao.sun.entity.financial;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

/**
 * 配置进程
 * @author Lux
 */
public enum ConfigureProgress {
	/**
	 * 已配置
	 */
	DONE("enum.configure-progress.done"),
	/**
	 * 待配置
	 */
	WAITING("enum.configure-progress.waiting");
	
	private String key;

	private ConfigureProgress(String key) {
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
	
	public static ConfigureProgress formValue(int value){
		for(ConfigureProgress item:ConfigureProgress.values()){
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
