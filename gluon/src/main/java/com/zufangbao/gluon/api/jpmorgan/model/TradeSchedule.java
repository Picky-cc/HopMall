package com.zufangbao.gluon.api.jpmorgan.model;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.zufangbao.gluon.api.jpmorgan.enums.AccountSide;

public class TradeSchedule {

	/** 借贷标记，0:贷 1:借 **/
	private AccountSide accountSide;

	/** 交易对手方账户名 **/
	private String destinationAccountName;

	/** 交易对手方账户号 **/
	private String destinationAccountNo;

	/** ［证件号：idNumber］ **/
	private String destinationAccountAppendix;

	/** bankCode, bankProvince, bankCity, bankName **/
	private String destinationBankInfo;

	/** 交易备注 **/
	private String postscript;

	/** 外部交易计划uuid **/
	private String outlierTransactionUuid;

	/** 交易唯一请求编号 **/
	private String sourceMessageUuid;

	/** 交易使用通道uuid **/
	private String fstPaymentChannelUuid;

	/** 计划执行起始日期 **/
	private Date fstEffectiveAbsoluteTime;

	/** 交易金额 **/
	private BigDecimal fstTransactionAmount;

	/** 交易计划所属批次uuid **/
	private String batchUuid;

	/**
	 * {"and":{"plan_uuid1":"success","plan_uuid2":"success"}}
	 **/
	private String executionPrecond;
	
	private String relatedDetailUuid;
	
	private String relatedBusinessRecordNo;
	
	/**
	 * 优先级
	 */
	private int priorityLevel;
	
	/**
	 * 支付网关
	 */
	private Integer relatedPaymentGateway;
	
	/**
	 * 支付通道名称
	 */
	private String relatedPaymentChannelName;
	
	private String fstGatewayRouterInfo;
	
	@JSONField(serialize = false)
	public String getRelatedDetailUuid() {
		return relatedDetailUuid;
	}

	public void setRelatedDetailUuid(String relatedDetailUuid) {
		this.relatedDetailUuid = relatedDetailUuid;
	}
	
	@JSONField(serialize = false)
	public String getRelatedBusinessRecordNo() {
		return relatedBusinessRecordNo;
	}

	public void setRelatedBusinessRecordNo(String relatedBusinessRecordNo) {
		this.relatedBusinessRecordNo = relatedBusinessRecordNo;
	}
	
	@JSONField(serialize = false)
	public int getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(int priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	@JSONField(serialize = false)
	public Integer getRelatedPaymentGateway() {
		return relatedPaymentGateway;
	}

	public void setRelatedPaymentGateway(Integer relatedPaymentGateway) {
		this.relatedPaymentGateway = relatedPaymentGateway;
	}
	
	@JSONField(serialize = false)
	public String getRelatedPaymentChannelName() {
		return relatedPaymentChannelName;
	}

	public void setRelatedPaymentChannelName(String relatedPaymentChannelName) {
		this.relatedPaymentChannelName = relatedPaymentChannelName;
	}

	@JSONField(serialize = false)
	public AccountSide getEnumAccountSide() {
		return accountSide;
	}
	
	public int getAccountSide() {
		return accountSide.ordinal();
	}

	public void setAccountSide(AccountSide accountSide) {
		this.accountSide = accountSide;
	}

	public String getDestinationAccountName() {
		return destinationAccountName;
	}

	public void setDestinationAccountName(String destinationAccountName) {
		this.destinationAccountName = destinationAccountName;
	}

	public String getDestinationAccountNo() {
		return destinationAccountNo;
	}

	public void setDestinationAccountNo(String destinationAccountNo) {
		this.destinationAccountNo = destinationAccountNo;
	}

	public String getDestinationAccountAppendix() {
		return destinationAccountAppendix;
	}

	public void setDestinationAccountAppendix(String destinationAccountAppendix) {
		this.destinationAccountAppendix = destinationAccountAppendix;
	}

	public String getDestinationBankInfo() {
		return destinationBankInfo;
	}

	public void setDestinationBankInfo(String destinationBankInfo) {
		this.destinationBankInfo = destinationBankInfo;
	}

	public String getPostscript() {
		return postscript;
	}

	public void setPostscript(String postscript) {
		this.postscript = postscript;
	}

	public String getOutlierTransactionUuid() {
		return outlierTransactionUuid;
	}

	public void setOutlierTransactionUuid(String outlierTransactionUuid) {
		this.outlierTransactionUuid = outlierTransactionUuid;
	}

	public String getSourceMessageUuid() {
		return sourceMessageUuid;
	}

	public void setSourceMessageUuid(String sourceMessageUuid) {
		this.sourceMessageUuid = sourceMessageUuid;
	}

	public String getFstPaymentChannelUuid() {
		return fstPaymentChannelUuid;
	}

	public void setFstPaymentChannelUuid(String fstPaymentChannelUuid) {
		this.fstPaymentChannelUuid = fstPaymentChannelUuid;
	}

	public Date getFstEffectiveAbsoluteTime() {
		return fstEffectiveAbsoluteTime;
	}

	public void setFstEffectiveAbsoluteTime(Date fstEffectiveAbsoluteTime) {
		this.fstEffectiveAbsoluteTime = fstEffectiveAbsoluteTime;
	}

	public BigDecimal getFstTransactionAmount() {
		return fstTransactionAmount;
	}

	public void setFstTransactionAmount(BigDecimal fstTransactionAmount) {
		this.fstTransactionAmount = fstTransactionAmount;
	}

	public String getBatchUuid() {
		return batchUuid;
	}

	public void setBatchUuid(String batchUuid) {
		this.batchUuid = batchUuid;
	}

	public String getExecutionPrecond() {
		return executionPrecond;
	}

	public void setExecutionPrecond(String executionPrecond) {
		this.executionPrecond = executionPrecond;
	}
	
	public TradeSchedule() {
		super();
	}

	public TradeSchedule(AccountSide accountSide,
			String destinationAccountName, String destinationAccountNo,
			String destinationAccountAppendix, String destinationBankInfo,
			String postscript, String outlierTransactionUuid,
			String sourceMessageUuid, String fstPaymentChannelUuid,
			Date fstEffectiveAbsoluteTime, BigDecimal fstTransactionAmount,
			String batchUuid, String executionPrecond,
			String relatedDetailUuid, String relatedBusinessRecordNo,
			int priorityLevel, Integer relatedPaymentGateway,
			String relatedPaymentChannelName, String fstGatewayRouterInfo) {
		this(accountSide, destinationAccountName, destinationAccountNo,
				destinationAccountAppendix, destinationBankInfo, postscript,
				outlierTransactionUuid, sourceMessageUuid,
				fstPaymentChannelUuid, fstEffectiveAbsoluteTime,
				fstTransactionAmount, batchUuid, executionPrecond,
				relatedDetailUuid, relatedBusinessRecordNo,
				fstGatewayRouterInfo);
		this.priorityLevel = priorityLevel;
		this.relatedPaymentGateway = relatedPaymentGateway;
		this.relatedPaymentChannelName = relatedPaymentChannelName;
	}
	
	public TradeSchedule(AccountSide accountSide,
			String destinationAccountName, String destinationAccountNo,
			String destinationAccountAppendix, String destinationBankInfo,
			String postscript, String outlierTransactionUuid,
			String sourceMessageUuid, String fstPaymentChannelUuid,
			Date fstEffectiveAbsoluteTime, BigDecimal fstTransactionAmount,
			String batchUuid, String executionPrecond,
			String relatedDetailUuid, String relatedBusinessRecordNo, String fstGatewayRouterInfo) {
		super();
		this.accountSide = accountSide;
		this.destinationAccountName = destinationAccountName;
		this.destinationAccountNo = destinationAccountNo;
		this.destinationAccountAppendix = destinationAccountAppendix;
		this.destinationBankInfo = destinationBankInfo;
		this.postscript = postscript;
		this.outlierTransactionUuid = outlierTransactionUuid;
		this.sourceMessageUuid = sourceMessageUuid;
		this.fstPaymentChannelUuid = fstPaymentChannelUuid;
		this.fstEffectiveAbsoluteTime = fstEffectiveAbsoluteTime;
		this.fstTransactionAmount = fstTransactionAmount;
		this.batchUuid = batchUuid;
		this.executionPrecond = executionPrecond;
		this.relatedDetailUuid = relatedDetailUuid;
		this.relatedBusinessRecordNo = relatedBusinessRecordNo;
		this.fstGatewayRouterInfo = fstGatewayRouterInfo;
	}
	
	private JSONObject getDestinationAccountAppendixMap() {
		return JSON.parseObject(this.destinationAccountAppendix);
	}
	
	private JSONObject getDestinationBankInfoMap() {
		return JSON.parseObject(this.destinationBankInfo);
	}
	
	private JSONObject getFstGatewayRouterInfoMap() {
		return JSON.parseObject(this.fstGatewayRouterInfo);
	}
	
	private String getValueSkipNullJSONObject(JSONObject jsonObject, String key) {
		return jsonObject == null ? null : jsonObject.getString(key);
	}
	
	public String getFstGatewayRouterInfo() {
		return fstGatewayRouterInfo;
	}

	public void setFstGatewayRouterInfo(String fstGatewayRouterInfo) {
		this.fstGatewayRouterInfo = fstGatewayRouterInfo;
	}

	@JSONField(serialize = false)
	public String getIdNumber() {
		return getValueSkipNullJSONObject(this.getDestinationAccountAppendixMap(), "idNumber");
	}
	
	@JSONField(serialize = false)
	public String getBankCode() {
		return getValueSkipNullJSONObject(this.getDestinationBankInfoMap(), "bankCode");
	}

	@JSONField(serialize = false)
	public String getBankProvince() {
		return getValueSkipNullJSONObject(this.getDestinationBankInfoMap(), "bankProvince");
	}
	
	@JSONField(serialize = false)
	public String getBankCity() {
		return getValueSkipNullJSONObject(this.getDestinationBankInfoMap(), "bankCity");
	}
	
	@JSONField(serialize = false)
	public String getBankName() {
		return getValueSkipNullJSONObject(this.getDestinationBankInfoMap(), "bankName");
	}
	
	@JSONField(serialize = false)
	public String getReckonAccount() {
		return getValueSkipNullJSONObject(this.getFstGatewayRouterInfoMap(), "reckonAccount");
	}
	
}
