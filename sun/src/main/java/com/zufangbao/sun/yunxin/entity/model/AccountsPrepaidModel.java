package com.zufangbao.sun.yunxin.entity.model;

import java.math.BigDecimal;

import com.zufangbao.sun.entity.account.VirtualAccountTransactionType;
import com.zufangbao.sun.entity.customer.CustomerType;
import com.zufangbao.sun.utils.EnumUtil;
import com.zufangbao.sun.yunxin.entity.SourceDocumentStatus;

/**
 * 
 * @author dcc
 *
 */
public class AccountsPrepaidModel {
	
	private String financialContractUuid;
	
	private int sourceDocumentStatus;
	
	private int customerType;
	
	private String key;

	public String getFinancialContractUuid() {
		return financialContractUuid;
	}

	public void setFinancialContractUuid(String financialContractUuid) {
		this.financialContractUuid = financialContractUuid;
	}

	public int getSourceDocumentStatus() {
		return sourceDocumentStatus;
	}

	public void setSourceDocumentStatus(int sourceDocumentStatus) {
		this.sourceDocumentStatus = sourceDocumentStatus;
	}
	
	public SourceDocumentStatus getSourceDocumentStatusEnum(){
		return EnumUtil.fromOrdinal(SourceDocumentStatus.class,sourceDocumentStatus);
	}

	public int getCustomerType() {
		return customerType;
	}

	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}
	
	public CustomerType getCustomerTypeEnum(){
		return EnumUtil.fromOrdinal(CustomerType.class,customerType);
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
