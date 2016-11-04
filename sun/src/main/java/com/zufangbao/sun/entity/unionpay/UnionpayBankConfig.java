package com.zufangbao.sun.entity.unionpay;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 银联银行配置
 * 
 * @author zhanghongbing
 *
 */
@Entity
@Table(name = "unionpay_bank_config")
public class UnionpayBankConfig {

	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 银行代码
	 */
	private String bankCode;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 是否使用批量扣款 0:不使用，1:使用
	 */
	private boolean useBatchDeduct;
	
	/**
	 * 恒生银行编码
	 */
	private String  standardBankCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public boolean isUseBatchDeduct() {
		return useBatchDeduct;
	}

	public void setUseBatchDeduct(boolean useBatchDeduct) {
		this.useBatchDeduct = useBatchDeduct;
	}

	public String getStandardBankCode() {
		return standardBankCode;
	}

	public void setStandardBankCode(String standardBankCode) {
		this.standardBankCode = standardBankCode;
	}
	
}
