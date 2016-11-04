package com.zufangbao.earth.yunxin.entity.remittance.model;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.yunxin.entity.remittance.RemittanceApplication;

public class RemittanceApplicationShowModel {

	private String financialProjectName;
	
	private String contractUniqueId;
	
	private String loanContractNo;
	
	private String orderNo;
	
	private BigDecimal planLoanAmount;
	
	private BigDecimal actualLoanAmount;
	
	private String remittanceStrategy;
	
	private Date receiveTime;
	
	private String orderStatus;
	
	public RemittanceApplicationShowModel(RemittanceApplication remittanceApplication) {
		financialProjectName = remittanceApplication.getFinancialProductCode();
		contractUniqueId = remittanceApplication.getContractUniqueId();
		loanContractNo = remittanceApplication.getContractNo();
		orderNo = remittanceApplication.getRemittanceApplicationUuid();
		planLoanAmount = remittanceApplication.getPlannedTotalAmount();
		actualLoanAmount = remittanceApplication.getActualTotalAmount();
		remittanceStrategy = remittanceApplication.getRemittanceStrategy().getChineseMessage();
		receiveTime = remittanceApplication.getCreateTime();
		orderStatus = remittanceApplication.getExecutionStatus().getChineseMessage();
	}

	public String getFinancialProjectName() {
		return financialProjectName;
	}

	public void setFinancialProjectName(String financialProjectName) {
		this.financialProjectName = financialProjectName;
	}

	public String getLoanContractNo() {
		return loanContractNo;
	}

	public void setLoanContractNo(String loanContractNo) {
		this.loanContractNo = loanContractNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getPlanLoanAmount() {
		return planLoanAmount;
	}

	public void setPlanLoanAmount(BigDecimal planLoanAmount) {
		this.planLoanAmount = planLoanAmount;
	}

	public BigDecimal getActualLoanAmount() {
		return actualLoanAmount;
	}

	public void setActualLoanAmount(BigDecimal actualLoanAmount) {
		this.actualLoanAmount = actualLoanAmount;
	}

	public String getRemittanceStrategy() {
		return remittanceStrategy;
	}

	public void setRemittanceStrategy(String remittanceStrategy) {
		this.remittanceStrategy = remittanceStrategy;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getContractUniqueId() {
		return contractUniqueId;
	}

	public void setContractUniqueId(String contractUniqueId) {
		this.contractUniqueId = contractUniqueId;
	}
	
	
}
