package com.zufangbao.sun.yunxin.entity.remittance;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.utils.EnumUtil;


public class RemittancePlanQueryModel {
	
	/**
	 * 信托合同Uuids
	 */
	private String financialContractUuids;
	
	/**
	 * 放款编号
	 */
	private String orderNo;
	
	/**
	 * 贷款合同编号
	 */
	private String loanContractNo;
	
	/**
	 * 计划放款日期起始
	 */
	private String plannedStartDate;
	
	/**
	 * 计划放款日期终止
	 */
	private String plannedEndDate;
	
	/**
	 * 付款方账户名
	 */
	private String payerAccountHolder;
	
	/**
	 * 收款方账户名---交易对手方银行卡持有人
	 */
	private String cpBankAccountHolder;
	
	/**
	 * 放款状态
	 */
	private int executionStatus=-1;
	
	/**
	 * 放款类型
	 */
	private int remittanceType = -1;
	
	/**
	 * 按照计划放款日期排序
	 */
	private boolean isAsc = false;
	
	public ExecutionStatus getExecutionStatusEnum(){
		return EnumUtil.fromOrdinal(ExecutionStatus.class, executionStatus);
	}
	
	public Date getStartDateValue(){
		return plannedStartDate == null?null:DateUtils.asDay(plannedStartDate);
	}
	
	public Date getEndDateValue(){
		return plannedEndDate == null?null:DateUtils.asDay(plannedEndDate);
	}
	
	public List<String> getFinancialContractUuidList(){
		List<String> financialContractList = JsonUtils.parseArray(this.financialContractUuids,String.class);
		if(financialContractList==null){
			return Collections.emptyList();
		}
		return financialContractList;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getLoanContractNo() {
		return loanContractNo;
	}

	public void setLoanContractNo(String loanContractNo) {
		this.loanContractNo = loanContractNo;
	}

	public String getPlannedStartDate() {
		return plannedStartDate;
	}

	public void setPlannedStartDate(String plannedStartDate) {
		this.plannedStartDate = plannedStartDate;
	}

	public String getPlannedEndDate() {
		return plannedEndDate;
	}

	public void setPlannedEndDate(String plannedEndDate) {
		this.plannedEndDate = plannedEndDate;
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

	public int getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(int executionStatus) {
		this.executionStatus = executionStatus;
	}

	public int getRemittanceType() {
		return remittanceType;
	}
	
	public AccountSide getRemittanceTypeEnum() {
		return EnumUtil.fromOrdinal(AccountSide.class, this.remittanceType);
	}

	public void setRemittanceType(int remittanceType) {
		this.remittanceType = remittanceType;
	}

	public boolean getIsAsc() {
		return isAsc;
	}

	public void setIsAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}
	
	public String getFinancialContractUuids() {
		return financialContractUuids;
	}
	
	public void setFinancialContractUuids(String financialContractUuids) {
		this.financialContractUuids = financialContractUuids;
	}

	public RemittancePlanQueryModel() {
		super();
	}

	public String getOrderBySentence() {
		return " ORDER BY id DESC";
//		 String template = " ORDER BY %s %s, id DESC";
//		 String dbField = "plannedPaymentDate";
//		 String sortType = this.isAsc ? "ASC" : "DESC";
//		 return String.format(template, dbField, sortType);
	}
}