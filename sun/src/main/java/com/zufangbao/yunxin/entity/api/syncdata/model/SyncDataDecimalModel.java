package com.zufangbao.yunxin.entity.api.syncdata.model;

import java.math.BigDecimal;
import java.util.Map;

import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.model.api.ExtraChargeSpec;

public class SyncDataDecimalModel {

	
	private BigDecimal repayTotalAmount;
	
	private BigDecimal preRepayPrincipal;
	
	private BigDecimal repayPrincipal;
	
	private BigDecimal preRepayInterest;
	
	private BigDecimal repayInterest;
	
	private BigDecimal preRepayPennalty;
	
	private BigDecimal repayPennalty;
	
	private BigDecimal techMaintenanceFee;
	
	private BigDecimal loanServiceFee;
	
	private BigDecimal latePenalty;
	
	private BigDecimal lateServiceFee;
	
	private BigDecimal lateOtherCosts;
	
	private BigDecimal loanCallCost;
	
	private BigDecimal otherFee;
	
	
	
	public SyncDataDecimalModel(){
		
	}
	//TODO
	public SyncDataDecimalModel(AssetSet repaymentPlan, Map<String, BigDecimal> amountMap, BigDecimal penaltyFee) {
		this.repayTotalAmount = repaymentPlan.getAssetFairValue();
		this.preRepayPrincipal = repaymentPlan.getAssetPrincipalValue();
		this.repayPrincipal   = repaymentPlan.getAssetPrincipalValue();
		this.preRepayInterest = repaymentPlan.getAssetInterestValue();
		this.repayInterest = repaymentPlan.getAssetInterestValue();
		this.preRepayPennalty = penaltyFee;
		this.repayPennalty   = penaltyFee;
		this.techMaintenanceFee = getAmountFromMap(amountMap, ExtraChargeSpec.LOAN_TECH_FEE_KEY);
		this.loanServiceFee     = getAmountFromMap(amountMap, ExtraChargeSpec.LOAN_SERVICE_FEE_KEY);
		this.latePenalty        = getAmountFromMap(amountMap, ExtraChargeSpec.OVERDUE_FEE_OBLIGATION_KEY);
		this.lateServiceFee     = getAmountFromMap(amountMap, ExtraChargeSpec.OVERDUE_FEE_SERVICE_FEE_KEY);
		this.lateOtherCosts     = getAmountFromMap(amountMap, ExtraChargeSpec.OVERDUE_FEE_OTHER_FEE_KEY);
		this.loanCallCost       = BigDecimal.ZERO;
		this.otherFee           = getAmountFromMap(amountMap, ExtraChargeSpec.LOAN_OTHER_FEE_KEY);
	}
	
	public SyncDataDecimalModel(AssetSet repaymentPlan,BigDecimal preRepayPennaltyData ) {
		this.repayTotalAmount = repaymentPlan.getAssetFairValue();
		this.preRepayPrincipal = repaymentPlan.getAssetPrincipalValue();
		this.preRepayInterest = repaymentPlan.getAssetInterestValue();
		this.preRepayPennalty = preRepayPennaltyData;
		this.repayPrincipal   = BigDecimal.ZERO;
		this.repayInterest = BigDecimal.ZERO;
		this.repayPennalty   = BigDecimal.ZERO;
		this.techMaintenanceFee = BigDecimal.ZERO;
		this.loanServiceFee     = BigDecimal.ZERO;
		this.latePenalty        = BigDecimal.ZERO;
		this.lateServiceFee     = BigDecimal.ZERO;
		this.lateOtherCosts     = BigDecimal.ZERO;
		this.loanCallCost       = BigDecimal.ZERO;
		this.otherFee           = BigDecimal.ZERO;
	}

	private BigDecimal  getAmountFromMap( Map<String, BigDecimal> amountMap,String chartString){
		if(amountMap.get(chartString) != null){
			return amountMap.get(chartString);
		}
		return BigDecimal.ZERO; 
	}
	
	public BigDecimal getRepayTotalAmount() {
		return repayTotalAmount;
	}

	public void setRepayTotalAmount(BigDecimal repayTotalAmount) {
		this.repayTotalAmount = repayTotalAmount;
	}

	public BigDecimal getPreRepayPrincipal() {
		return preRepayPrincipal;
	}

	public void setPreRepayPrincipal(BigDecimal preRepayPrincipal) {
		this.preRepayPrincipal = preRepayPrincipal;
	}

	public BigDecimal getRepayPrincipal() {
		return repayPrincipal;
	}

	public void setRepayPrincipal(BigDecimal repayPrincipal) {
		this.repayPrincipal = repayPrincipal;
	}

	public BigDecimal getPreRepayInterest() {
		return preRepayInterest;
	}

	public void setPreRepayInterest(BigDecimal preRepayInterest) {
		this.preRepayInterest = preRepayInterest;
	}

	public BigDecimal getRepayInterest() {
		return repayInterest;
	}

	public void setRepayInterest(BigDecimal repayInterest) {
		this.repayInterest = repayInterest;
	}

	public BigDecimal getPreRepayPennalty() {
		return preRepayPennalty;
	}

	public void setPreRepayPennalty(BigDecimal preRepayPennalty) {
		this.preRepayPennalty = preRepayPennalty;
	}

	public BigDecimal getRepayPennalty() {
		return repayPennalty;
	}

	public void setRepayPennalty(BigDecimal repayPennalty) {
		this.repayPennalty = repayPennalty;
	}

	public BigDecimal getTechMaintenanceFee() {
		return techMaintenanceFee;
	}

	public void setTechMaintenanceFee(BigDecimal techMaintenanceFee) {
		this.techMaintenanceFee = techMaintenanceFee;
	}

	public BigDecimal getLoanServiceFee() {
		return loanServiceFee;
	}

	public void setLoanServiceFee(BigDecimal loanServiceFee) {
		this.loanServiceFee = loanServiceFee;
	}

	public BigDecimal getLatePenalty() {
		return latePenalty;
	}

	public void setLatePenalty(BigDecimal latePenalty) {
		this.latePenalty = latePenalty;
	}

	public BigDecimal getLateServiceFee() {
		return lateServiceFee;
	}

	public void setLateServiceFee(BigDecimal lateServiceFee) {
		this.lateServiceFee = lateServiceFee;
	}

	public BigDecimal getLateOtherCosts() {
		return lateOtherCosts;
	}

	public void setLateOtherCosts(BigDecimal lateOtherCosts) {
		this.lateOtherCosts = lateOtherCosts;
	}

	public BigDecimal getLoanCallCost() {
		return loanCallCost;
	}

	public void setLoanCallCost(BigDecimal loanCallCost) {
		this.loanCallCost = loanCallCost;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}
	
	
}
