package com.zufangbao.earth.yunxin.entity.deduct.model;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplication;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductPlan;

public class PaymentOrderInformation {
	
	private String paymentOrderNo;
	
	private String deductOrderNo;
	
	private String bankName;
	
	private String bankAccount;
	
	private BigDecimal deductAmount;
	
	private Date   occurTime;
	
	private String repaymentPlanStatus;
	
	private String remark;

	public PaymentOrderInformation(DeductPlan deductPlan, DeductApplication deductApplication) {

		this.paymentOrderNo = deductPlan.getDeductPlanUuid();
		this.deductOrderNo  = deductApplication.getDeductId();
		this.bankName = deductPlan.getCpBankName();
		this.bankAccount = deductPlan.getCpBankCardNo();
		this.deductAmount = deductPlan.getPlannedTotalAmount();
		this.occurTime    = deductPlan.getLastModifiedTime();
		this.repaymentPlanStatus = deductPlan.getExecutionStatus().getChineseMessage();
		this.remark = deductPlan.getExecutionRemark();
	}

	public String getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	public String getDeductOrderNo() {
		return deductOrderNo;
	}

	public void setDeductOrderNo(String deductOrderNo) {
		this.deductOrderNo = deductOrderNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public BigDecimal getDeductAmount() {
		return deductAmount;
	}

	public void setDeductAmount(BigDecimal deductAmount) {
		this.deductAmount = deductAmount;
	}

	public Date getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	public String getRepaymentPlanStatus() {
		return repaymentPlanStatus;
	}

	public void setRepaymentPlanStatus(String repaymentPlanStatus) {
		this.repaymentPlanStatus = repaymentPlanStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
