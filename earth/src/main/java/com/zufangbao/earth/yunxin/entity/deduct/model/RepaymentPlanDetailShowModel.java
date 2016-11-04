package com.zufangbao.earth.yunxin.entity.deduct.model;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.api.model.deduct.DeductApplicationRepaymentDetail;
import com.zufangbao.sun.yunxin.entity.AssetSet;

public class RepaymentPlanDetailShowModel {

	
	private String repaymentPlanCode;
	
	private Date   repaymentDate;
	
	private int    currentPeriods;
	
	private BigDecimal principal;
	
	private BigDecimal interest;
	
	private BigDecimal loanFee;
	
	private BigDecimal techFee; 
	
	private BigDecimal otherFee;
	
	private BigDecimal totalOverDueFee;
	
	private BigDecimal planRepaymentAmount;
	
	private BigDecimal actualAmount;
	
	private String     repaymentStatus;
	
	private String remark;

	public RepaymentPlanDetailShowModel(DeductApplicationRepaymentDetail deductApplicationRepaymentDetail,
			AssetSet repaymentPlan) {
		this.repaymentPlanCode = repaymentPlan.getSingleLoanContractNo();
		this.repaymentDate     = deductApplicationRepaymentDetail.getCreateTime();
		this.currentPeriods    = repaymentPlan.getCurrentPeriod();
		this.principal         = deductApplicationRepaymentDetail.getRepaymentPrincipal();
		this.interest          = deductApplicationRepaymentDetail.getRepaymentInterest();
		this.loanFee           = deductApplicationRepaymentDetail.getLoanFee();
		this.techFee           = deductApplicationRepaymentDetail.getTechFee();
		this.otherFee          = deductApplicationRepaymentDetail.getOtherFee();
		this.totalOverDueFee   = deductApplicationRepaymentDetail.getTotalOverduFee();
		this.planRepaymentAmount = deductApplicationRepaymentDetail.getAccountReceivableAmount();
		this.actualAmount       = deductApplicationRepaymentDetail.getTotalAmount();
		this.repaymentStatus   = repaymentPlan.getPaymentStatus().getChineseMessage();
		this.remark            = "";
	}

	public String getRepaymentPlanCode() {
		return repaymentPlanCode;
	}

	public void setRepaymentPlanCode(String repaymentPlanCode) {
		this.repaymentPlanCode = repaymentPlanCode;
	}

	public Date getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	public int getCurrentPeriods() {
		return currentPeriods;
	}

	public void setCurrentPeriods(int currentPeriods) {
		this.currentPeriods = currentPeriods;
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

	public BigDecimal getTotalOverDueFee() {
		return totalOverDueFee;
	}

	public void setTotalOverDueFee(BigDecimal totalOverDueFee) {
		this.totalOverDueFee = totalOverDueFee;
	}

	public BigDecimal getPlanRepaymentAmount() {
		return planRepaymentAmount;
	}

	public void setPlanRepaymentAmount(BigDecimal planRepaymentAmount) {
		this.planRepaymentAmount = planRepaymentAmount;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getRepaymentStatus() {
		return repaymentStatus;
	}

	public void setRepaymentStatus(String repaymentStatus) {
		this.repaymentStatus = repaymentStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	
	
	
}
