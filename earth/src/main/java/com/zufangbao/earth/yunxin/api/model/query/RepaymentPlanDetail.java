package com.zufangbao.earth.yunxin.api.model.query;

import java.math.BigDecimal;
import java.util.Map;

import com.zufangbao.gluon.opensdk.DateUtils;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.PaymentStatus;

public class RepaymentPlanDetail {

	private String repaymentNumber;

	private String planRepaymentDate;

	private String planRepaymentPrincipal="0.00";

	private String planRepaymentInterest="0.00";

	private String loanFees = "0.00";

	private String technicalServicesFees = "0.00";

	private String otherFees = "0.00";

	private String repaymentExecutionState;
	
	private String penaltyFee = "0.00";

	public RepaymentPlanDetail(AssetSet assetSet, Map<String, BigDecimal> amountMap, BigDecimal totalOverDueFee) {
		this.repaymentNumber = assetSet.getSingleLoanContractNo();
		this.planRepaymentDate = DateUtils.format(assetSet.getAssetRecycleDate(), "yyyy-MM-dd");
		
		this.planRepaymentPrincipal  =  assetSet.getAssetPrincipalValue().toString();
		this.planRepaymentInterest   =   assetSet.getAssetInterestValue().toString();
		this.repaymentExecutionState = transferPaymentStatus(assetSet.getPaymentStatus());
		this.penaltyFee              =      totalOverDueFee.toString();
		this.loanFees                = getAmountFromMap(amountMap,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_LOAN_SERVICE_FEE).toString();
		this.technicalServicesFees   = getAmountFromMap(amountMap,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_TECH_FEE).toString();
		this.otherFees               = getAmountFromMap(amountMap,ChartOfAccount.SND_UNEARNED_LOAN_ASSET_OTHER_FEE).toString();
	}
	private BigDecimal getAmountFromMap(Map<String,BigDecimal> amountMap,String chartString ){
		
		if(amountMap.get(chartString) != null){
			return amountMap.get(chartString);
		}
		return BigDecimal.ZERO; 
	}
	
	public RepaymentPlanDetail(){
		
	}

	private String transferPaymentStatus(PaymentStatus paymentStatus) {
		switch (paymentStatus.ordinal()) {
		case  0:
			return  "未到期";
		case  1:
			return  "还款处理中";
		case  2:
			return  "还款异常";
		case  3:
			return  "还款成功";
		default:
			break;
		}
		return "";
	}

	public String getRepaymentNumber() {
		return repaymentNumber;
	}

	public void setRepaymentNumber(String repaymentNumber) {
		this.repaymentNumber = repaymentNumber;
	}

	public String getPlanRepaymentDate() {
		return planRepaymentDate;
	}

	public void setPlanRepaymentDate(String planRepaymentDate) {
		this.planRepaymentDate = planRepaymentDate;
	}

	public String getPlanRepaymentPrincipal() {
		return planRepaymentPrincipal;
	}

	public void setPlanRepaymentPrincipal(String planRepaymentPrincipal) {
		this.planRepaymentPrincipal = planRepaymentPrincipal;
	}

	public String getPlanRepaymentInterest() {
		return planRepaymentInterest;
	}

	public void setPlanRepaymentInterest(String planRepaymentInterest) {
		this.planRepaymentInterest = planRepaymentInterest;
	}

	public String getLoanFees() {
		return loanFees;
	}

	public void setLoanFees(String loanFees) {
		this.loanFees = loanFees;
	}

	public String getTechnicalServicesFees() {
		return technicalServicesFees;
	}

	public void setTechnicalServicesFees(String technicalServicesFees) {
		this.technicalServicesFees = technicalServicesFees;
	}

	public String getOtherFees() {
		return otherFees;
	}

	public void setOtherFees(String otherFees) {
		this.otherFees = otherFees;
	}

	public String getRepaymentExecutionState() {
		return repaymentExecutionState;
	}

	public void setRepaymentExecutionState(String repaymentExecutionState) {
		this.repaymentExecutionState = repaymentExecutionState;
	}
	public String getPenaltyFee() {
		return penaltyFee;
	}
	public void setPenaltyFee(String penaltyFee) {
		this.penaltyFee = penaltyFee;
	}

}
