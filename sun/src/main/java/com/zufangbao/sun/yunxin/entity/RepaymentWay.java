package com.zufangbao.sun.yunxin.entity;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

public enum RepaymentWay {
	/** 等额本息 */
	AVERAGE_CAPITAL_PLUS_INTEREST("enum.repayment-way.averageCapitalPlusInterest"),
	
	/** 等额本金 */
	AVERAGE_CAPITAL("enum.repayment-way.averageCapital"),
	
	/**锯齿型**/
	SAW_TOOTH("enum.repayment-way.sawtooth");

	private String key;
	
	
	/**
	 * @param key
	 */
	private RepaymentWay(String key) {
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
	
	
	public static RepaymentWay fromValue(int value){
	    
	    for(RepaymentWay item : RepaymentWay.values()){
	      if(item.ordinal() == value){
	        return item;
	      }
	    }
	    return null;
	  }

	public static RepaymentWay fromString(String str){
		
		for(RepaymentWay item : RepaymentWay.values()){
			String value = ApplicationMessageUtils.getChineseMessage(item.getKey());
			if(StringUtils.equals(value, str)){
				return item;
			}
		}
		return null;
	}
	
	public  String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
}
