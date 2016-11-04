package com.zufangbao.earth.cache;

import java.io.Serializable;
import java.util.HashMap;

import com.zufangbao.sun.entity.account.DepositeAccountInfo;

public class DepositeAccountInfoCache implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6661618715188159655L;
	private long time;
	private HashMap<String,DepositeAccountInfo> accountInfoMap;
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public HashMap<String, DepositeAccountInfo> getAccountInfoMap() {
		return accountInfoMap;
	}
	public void setAccountInfoMap(
			HashMap<String, DepositeAccountInfo> accountInfoMap) {
		this.accountInfoMap = accountInfoMap;
	}
	
	public boolean isExpired(long timeout) {
		return (System.currentTimeMillis()-time)>timeout;
	}
	public DepositeAccountInfoCache(){
		
	}
	public DepositeAccountInfoCache(HashMap<String, DepositeAccountInfo> accountInfoMap, long time){
		this.time = time;
		this.accountInfoMap = accountInfoMap;
	}
	
}
