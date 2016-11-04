package com.zufangbao.sun.yunxin.entity.model;

import java.math.BigDecimal;

public class VirtualAccountModel {
	
	private String fstLevelContractUuid;
	
	private int customerType;
	
	private String key;

	public String getFstLevelContractUuid() {
		return fstLevelContractUuid;
	}

	public void setFstLevelContractUuid(String fstLevelContractUuid) {
		this.fstLevelContractUuid = fstLevelContractUuid;
	}

	public int getCustomerType() {
		return customerType;
	}

	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public BigDecimal getAmountFromKey() {
		try {
			return new BigDecimal(key);
		} catch(Exception e){
		}
		return null;
	}
}
