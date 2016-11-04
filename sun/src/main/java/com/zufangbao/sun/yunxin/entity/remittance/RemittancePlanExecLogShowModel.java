package com.zufangbao.sun.yunxin.entity.remittance;

import java.math.BigDecimal;
import java.util.Date;

public class RemittancePlanExecLogShowModel {
	/**
	 * 交易上送请求号 -- 代付单号
	 */
	private String execReqNo;
	/**
	 * 放款计划uuid -- 放款编号
	 */
	private String remittancePlanUuid;
	/**
	 * 放款申请uuid
	 */
	private String remittanceApplicationUuid;
	/**
	 * 付款方账户名
	 */
	private String payerAccountHolder="";
	/**
	 * 放款通道
	 */
	private String remittanceChannel;
	/**
	 * 计划支付金额--放款金额
	 */
	private BigDecimal plannedAmount;
	/**
	 * 交易对手方银行卡号
	 */
	private String cpBankCardNo;
	/**
	 * 交易对手方银行卡持有人
	 */
	private String cpBankAccountHolder;
	/**
	 * 交易对手方证件类型
	 */
	private String cpIdType;
	/**
	 * 交易对手方证件号
	 */
	private String cpIdNumber;
	/**
	 * 交易对手方开户行所在省
	 */
	private String cpBankProvince;
	/**
	 * 交易对手方开户行所在市
	 */
	private String cpBankCity;
	/**
	 * 交易对手方开户行名称
	 */
	private String cpBankName;
	/**
	 * 交易备注
	 */
	private String transactionRemark;
	/**
	 * 代付状态, 0:已创建、1:处理中、2:成功、3:失败、4:异常、5:撤销
	 */
	private String executionStatus;
	/**
	 * 执行备注
	 */
	private String executionRemark;
	/**
	 * 状态变更时间
	 */
	private Date lastModifiedTime;
	/**
	 * 冲账状态
	 */
	private String reverseStatus = "";
	
	/**
	 * 付款方信息
	 */
	private AccountInfoModel pgAccountInfo;
	
	/**
	 * 收款方信息
	 */
	private AccountInfoModel cpAccountInfo;
	
	/**
	 * 执行时差
	 */
	private String executedTimeOff;
	
	public String getExecReqNo() {
		return execReqNo;
	}
	public void setExecReqNo(String execReqNo) {
		this.execReqNo = execReqNo;
	}
	public String getRemittancePlanUuid() {
		return remittancePlanUuid;
	}
	public void setRemittancePlanUuid(String remittancePlanUuid) {
		this.remittancePlanUuid = remittancePlanUuid;
	}
	public String getRemittanceApplicationUuid() {
		return remittanceApplicationUuid;
	}
	public void setRemittanceApplicationUuid(String remittanceApplicationUuid) {
		this.remittanceApplicationUuid = remittanceApplicationUuid;
	}
	public String getPayerAccountHolder() {
		return payerAccountHolder;
	}
	public void setPayerAccountHolder(String payerAccountHolder) {
		this.payerAccountHolder = payerAccountHolder;
	}
	public String getRemittanceChannel() {
		return remittanceChannel;
	}
	public void setRemittanceChannel(String remittanceChannel) {
		this.remittanceChannel = remittanceChannel;
	}
	public BigDecimal getPlannedAmount() {
		return plannedAmount;
	}
	public void setPlannedAmount(BigDecimal plannedAmount) {
		this.plannedAmount = plannedAmount;
	}
	public String getCpBankCardNo() {
		return cpBankCardNo;
	}
	public void setCpBankCardNo(String cpBankCardNo) {
		this.cpBankCardNo = cpBankCardNo;
	}
	public String getCpBankAccountHolder() {
		return cpBankAccountHolder;
	}
	public void setCpBankAccountHolder(String cpBankAccountHolder) {
		this.cpBankAccountHolder = cpBankAccountHolder;
	}
	public String getCpIdType() {
		return cpIdType;
	}
	public void setCpIdType(String cpIdType) {
		this.cpIdType = cpIdType;
	}
	public String getCpIdNumber() {
		return cpIdNumber;
	}
	public void setCpIdNumber(String cpIdNumber) {
		this.cpIdNumber = cpIdNumber;
	}
	public String getCpBankProvince() {
		return cpBankProvince;
	}
	public void setCpBankProvince(String cpBankProvince) {
		this.cpBankProvince = cpBankProvince;
	}
	public String getCpBankCity() {
		return cpBankCity;
	}
	public void setCpBankCity(String cpBankCity) {
		this.cpBankCity = cpBankCity;
	}
	public String getCpBankName() {
		return cpBankName;
	}
	public void setCpBankName(String cpBankName) {
		this.cpBankName = cpBankName;
	}
	public String getTransactionRemark() {
		return transactionRemark;
	}
	public void setTransactionRemark(String transactionRemark) {
		this.transactionRemark = transactionRemark;
	}
	public String getExecutionStatus() {
		return executionStatus;
	}
	public void setExecutionStatus(String executionStatus) {
		this.executionStatus = executionStatus;
	}
	public String getExecutionRemark() {
		return executionRemark;
	}
	public void setExecutionRemark(String executionRemark) {
		this.executionRemark = executionRemark;
	}
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getReverseStatus() {
		return reverseStatus;
	}
	public void setReverseStatus(String reverseStatus) {
		this.reverseStatus = reverseStatus;
	}
	public AccountInfoModel getPgAccountInfo() {
		return pgAccountInfo;
	}
	public void setPgAccountInfo(AccountInfoModel pgAccountInfo) {
		this.pgAccountInfo = pgAccountInfo;
	}
	public AccountInfoModel getCpAccountInfo() {
		return cpAccountInfo;
	}
	public void setCpAccountInfo(AccountInfoModel cpAccountInfo) {
		this.cpAccountInfo = cpAccountInfo;
	}
	public String getExecutedTimeOff() {
		return executedTimeOff;
	}
	public void setExecutedTimeOff(String executedTimeOff) {
		this.executedTimeOff = executedTimeOff;
	}
	public RemittancePlanExecLogShowModel() {
		super();
	}
	public RemittancePlanExecLogShowModel(RemittancePlanExecLog execLog) {
		super();
		this.execReqNo=execLog.getExecReqNo();
		this.remittancePlanUuid=execLog.getRemittancePlanUuid();
		this.payerAccountHolder="";
		this.cpBankAccountHolder=execLog.getCpBankAccountHolder();
		this.plannedAmount=execLog.getPlannedAmount();
		this.remittanceChannel=execLog.getPaymentGateway() == null ? "" : execLog.getPaymentGateway().getChineseMessage();
		this.lastModifiedTime=execLog.getLastModifiedTime();
		this.executionStatus=execLog.getExecutionStatus()==null?"":execLog.getExecutionStatus().getChineseMessage();
		this.reverseStatus=execLog.getReverseStatus() == null ? "" : execLog.getReverseStatus().getChineseMessage();
		this.executionRemark=execLog.getExecutionRemark();
		this.cpBankCardNo=execLog.getCpBankCardNo();
		this.cpIdNumber=execLog.getCpIdNumber();
		this.cpBankProvince=execLog.getCpBankProvince();
		this.cpBankCity=execLog.getCpBankCity();
		this.cpBankName=execLog.getCpBankName();
		this.cpAccountInfo = execLog.getCpAccountInfo();
		this.pgAccountInfo = execLog.getPgAccountInfo();
		this.executedTimeOff = execLog.getExecutedTime();
	}

}
