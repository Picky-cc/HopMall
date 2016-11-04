package com.zufangbao.earth.yunxin.entity.deduct.model;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplication;

public class QueryDeductApplicationShowModel {

	
	private String deudctNo;

	private String repaymentType;
	
	private String loanContractNo;
	
	private String financialContractCode;
	
	private String financialProjectName;
	
	private String customerName;
	
	private BigDecimal planDeductAmount;
	
	private BigDecimal actualDeductAmount;
	
	private Date   deductApplicationDate;
	
	private String deductStatus;
	
	private String remark;

	public QueryDeductApplicationShowModel(DeductApplication deductApplication, FinancialContract financialContract) {
		this.deudctNo = deductApplication.getDeductId();
		this.repaymentType = deductApplication.getRepaymentType().getChineseMessage();
		this.loanContractNo = deductApplication.getContractNo();
		this.financialContractCode = financialContract.getContractNo();
		this.financialProjectName = financialContract.getContractName();
		this.customerName = deductApplication.getCustomerName();
		this.planDeductAmount = deductApplication.getPlannedDeductTotalAmount();
		this.actualDeductAmount = deductApplication.getActualDeductTotalAmount();
		this.deductApplicationDate = deductApplication.getCreateTime();
		this.deductStatus = deductApplication.getExecutionStatus().getChineseMessage();
		this.remark = deductApplication.getExecutionRemark();
	}

	public String getDeudctNo() {
		return deudctNo;
	}

	public void setDeudctNo(String deudctNo) {
		this.deudctNo = deudctNo;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getLoanContractNo() {
		return loanContractNo;
	}

	public void setLoanContractNo(String loanContractNo) {
		this.loanContractNo = loanContractNo;
	}

	public String getFinancialContractCode() {
		return financialContractCode;
	}

	public void setFinancialContractCode(String financialContractCode) {
		this.financialContractCode = financialContractCode;
	}

	public String getFinancialProjectName() {
		return financialProjectName;
	}

	public void setFinancialProjectName(String financialProjectName) {
		this.financialProjectName = financialProjectName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getPlanDeductAmount() {
		return planDeductAmount;
	}

	public void setPlanDeductAmount(BigDecimal planDeductAmount) {
		this.planDeductAmount = planDeductAmount;
	}

	public BigDecimal getActualDeductAmount() {
		return actualDeductAmount;
	}

	public void setActualDeductAmount(BigDecimal actualDeductAmount) {
		this.actualDeductAmount = actualDeductAmount;
	}

	public String getDeductStatus() {
		return deductStatus;
	}

	public void setDeductStatus(String deductStatus) {
		this.deductStatus = deductStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getDeductApplicationDate() {
		return deductApplicationDate;
	}

	public void setDeductApplicationDate(Date deductApplicationDate) {
		this.deductApplicationDate = deductApplicationDate;
	}
	
	
	
}
