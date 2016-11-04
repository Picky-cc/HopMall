package com.zufangbao.sun.api.model.deduct;

import java.math.BigDecimal;

public class RepaymentResInfo {

	private  BigDecimal repaymentPlanNo;
	
	
	// 0 未到期 1还款处理中2 还款异常 3 还款成功
	private  int  repaymentStatus;
	
	private  BigDecimal  repaymentAmount;
	
	private  BigDecimal repaymentPrincipal;
	
	private  BigDecimal repaymentInterest;
	
	private  BigDecimal loanFee;
	
	private  BigDecimal techFee;
	
	private  BigDecimal otherFee;
	
	private  BigDecimal totalOverduFee;

	public BigDecimal getRepaymentPlanNo() {
		return repaymentPlanNo;
	}

	public void setRepaymentPlanNo(BigDecimal repaymentPlanNo) {
		this.repaymentPlanNo = repaymentPlanNo;
	}

	public int getRepaymentStatus() {
		return repaymentStatus;
	}

	public void setRepaymentStatus(int repaymentStatus) {
		this.repaymentStatus = repaymentStatus;
	}

	public BigDecimal getRepaymentAmount() {
		return repaymentAmount;
	}

	public void setRepaymentAmount(BigDecimal repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}

	public BigDecimal getRepaymentPrincipal() {
		return repaymentPrincipal;
	}

	public void setRepaymentPrincipal(BigDecimal repaymentPrincipal) {
		this.repaymentPrincipal = repaymentPrincipal;
	}

	public BigDecimal getRepaymentInterest() {
		return repaymentInterest;
	}

	public void setRepaymentInterest(BigDecimal repaymentInterest) {
		this.repaymentInterest = repaymentInterest;
	}

	public BigDecimal getLoanFee() {
		return loanFee;
	}

	public void setLoanFee(BigDecimal loanFee) {
		this.loanFee = loanFee;
	}

	public BigDecimal getTechFee() {
		return techFee;
	}

	public void setTechFee(BigDecimal techFee) {
		this.techFee = techFee;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	public BigDecimal getTotalOverduFee() {
		return totalOverduFee;
	}

	public void setTotalOverduFee(BigDecimal totalOverduFee) {
		this.totalOverduFee = totalOverduFee;
	}
	
	
	
}
