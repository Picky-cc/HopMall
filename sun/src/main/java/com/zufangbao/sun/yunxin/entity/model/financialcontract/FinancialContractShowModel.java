package com.zufangbao.sun.yunxin.entity.model.financialcontract;

import java.util.Date;

import com.zufangbao.sun.entity.financial.FinancialContract;

/**
 * 信托合同显示类
 * 
 * @author louguanyang
 *
 */
public class FinancialContractShowModel {
	/** 信托合同ID */
	private Long id;
	/** 信托合同编号 */
	private String contractNo;
	/** 信托合同名称 */
	private String contractName;
	/** 合作商户名称 */
	private String appName;
	/** 信托合约类型 消费贷,小微企业贷款 */
	private String financialContractType;
	/** 信托合同起始日期 */
	private Date advaStartDate;
	/** 信托合同截止日期 */
	private Date thruDate;
	/** 资金账户号 */
	private String capitalAccountNo;
	/** 支付通道 */
	private String paymentChannelType;
	/** 商户号 */
	private String merchantId;

	public FinancialContractShowModel() {
		super();
	}

	public FinancialContractShowModel(FinancialContract financialContract) {
		super();
		this.id = financialContract.getId();
		this.contractNo = financialContract.getContractNo();
		this.contractName = financialContract.getContractName();
		this.appName = financialContract.getApp().getName();
		this.financialContractType = financialContract.getFinancialContractType().toString();
		this.advaStartDate = financialContract.getAdvaStartDate();
		this.thruDate = financialContract.getThruDate();
		this.capitalAccountNo = financialContract.getCapitalAccount().getAccountNo();
		this.paymentChannelType = financialContract.getPaymentChannel().getPaymentChannelType().toString();
		this.merchantId = financialContract.getPaymentChannel().getMerchantId();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getFinancialContractType() {
		return financialContractType;
	}

	public void setFinancialContractType(String financialContractType) {
		this.financialContractType = financialContractType;
	}

	public Date getAdvaStartDate() {
		return advaStartDate;
	}

	public void setAdvaStartDate(Date advaStartDate) {
		this.advaStartDate = advaStartDate;
	}

	public Date getThruDate() {
		return thruDate;
	}

	public void setThruDate(Date thruDate) {
		this.thruDate = thruDate;
	}

	public String getCapitalAccountNo() {
		return capitalAccountNo;
	}

	public void setCapitalAccountNo(String capitalAccountNo) {
		this.capitalAccountNo = capitalAccountNo;
	}

	public String getPaymentChannelType() {
		return paymentChannelType;
	}

	public void setPaymentChannelType(String paymentChannelType) {
		this.paymentChannelType = paymentChannelType;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

}
