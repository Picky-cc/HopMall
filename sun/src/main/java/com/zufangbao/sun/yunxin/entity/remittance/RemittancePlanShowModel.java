package com.zufangbao.sun.yunxin.entity.remittance;

import java.math.BigDecimal;
import java.util.Date;

public class RemittancePlanShowModel {

	/**
	 * 放款编号
	 */
	private String remittancePlanUuid="";
	
	/**
	 * 合同编号
	 */
	private String contractNo="";
	
	/**
	 * 贷款合同唯一编号
	 */
	private String contractUniqueId="";

	/**
	 * 计划支付时间
	 */
	private Date plannedPaymentDate;
	
	/**
	 * 计划交易总金额
	 */
	private BigDecimal plannedTotalAmount=null;

	/**
	 * 付款方账户名
	 */
	private String payerAccountHolder="";
	
	/**
	 * 收款方账户名---交易对手方银行卡持有人
	 */
	private String cpBankAccountHolder="";
	
	/**
	 * 执行状态
	 */
	private String executionStatus;
	
	/**
	 * 交易备注
	 */
	private String transactionRemark;
	
	/**
	 * 付款方信息
	 */
	private AccountInfoModel pgAccountInfo;
	
	/**
	 * 收款方信息
	 */
	private AccountInfoModel cpAccountInfo;
	
	/**
	 * 交易类型
	 */
	private String transactionType;

	public String getRemittancePlanUuid() {
		return remittancePlanUuid;
	}

	public void setRemittancePlanUuid(String remittancePlanUuid) {
		this.remittancePlanUuid = remittancePlanUuid;
	}

	public String getContractNo() {
		return contractNo;
	}
	
	public String getContractUniqueId() {
		return contractUniqueId;
	}
	
	public void setContractUniqueId(String contractUniqueId) {
		this.contractUniqueId = contractUniqueId;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public BigDecimal getPlannedTotalAmount() {
		return plannedTotalAmount;
	}

	public void setPlannedTotalAmount(BigDecimal plannedTotalAmount) {
		this.plannedTotalAmount = plannedTotalAmount;
	}

	public String getPayerAccountHolder() {
		return payerAccountHolder;
	}

	public void setPayerAccountHolder(String payerAccountHolder) {
		this.payerAccountHolder = payerAccountHolder;
	}

	public String getCpBankAccountHolder() {
		return cpBankAccountHolder;
	}

	public void setCpBankAccountHolder(String cpBankAccountHolder) {
		this.cpBankAccountHolder = cpBankAccountHolder;
	}

	public String getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(String executionStatusMsg) {
		this.executionStatus = executionStatusMsg;
	}

	public String getTransactionRemark() {
		return transactionRemark;
	}

	public void setTransactionRemark(String transactionRemark) {
		this.transactionRemark = transactionRemark;
	}

	public Date getPlannedPaymentDate() {
		return plannedPaymentDate;
	}

	public void setPlannedPaymentDate(Date plannedPaymentDate) {
		this.plannedPaymentDate = plannedPaymentDate;
	}
	
	public AccountInfoModel getCpAccountInfo() {
		return cpAccountInfo;
	}
	
	public void setCpAccountInfo(AccountInfoModel cpAccountInfo) {
		this.cpAccountInfo = cpAccountInfo;
	}
	
	public AccountInfoModel getPgAccountInfo() {
		return pgAccountInfo;
	}
	
	public void setPgAccountInfo(AccountInfoModel pgAccountInfo) {
		this.pgAccountInfo = pgAccountInfo;
	}
	
	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public RemittancePlanShowModel() {
		super();
	}
	
	public RemittancePlanShowModel(RemittancePlan remittancePlan){
		this.contractNo = remittancePlan.getContractNo();
		this.contractUniqueId = remittancePlan.getContractUniqueId();
		this.cpBankAccountHolder = remittancePlan.getCpBankAccountHolder();
		this.executionStatus = remittancePlan.getExecutionStatus().getChineseMessage();
//		this.payerAccountHolder = remittancePlan.getPayerAccountHolder();
		this.plannedTotalAmount = remittancePlan.getPlannedTotalAmount();
		this.remittancePlanUuid = remittancePlan.getRemittancePlanUuid();
		this.transactionRemark = remittancePlan.getTransactionRemark();
		this.plannedPaymentDate = remittancePlan.getPlannedPaymentDate();
		this.transactionType = remittancePlan.getTransactionType() == AccountSide.CREDIT ? "代付" : (remittancePlan.getTransactionType() == AccountSide.DEBIT ? "代收": "");
		this.cpAccountInfo = remittancePlan.getCpAccountInfo();
		this.pgAccountInfo = remittancePlan.getPgAccountInfo();
		
	}
}
