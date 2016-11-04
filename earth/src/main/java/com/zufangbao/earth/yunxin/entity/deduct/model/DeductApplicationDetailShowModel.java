package com.zufangbao.earth.yunxin.entity.deduct.model;

import java.util.List;

public class DeductApplicationDetailShowModel {
	
	private LoanInformation loanInformation;
	
	
	private DeductInformation deductInformation;
	
	
	private AccountInformation accountInformation;
	
	
	private List<PaymentOrderInformation> paymentOrderInformations;
	
	
	
	private List<RepaymentPlanDetailShowModel> repaymentPlanDetailShowModels;



	public DeductApplicationDetailShowModel(LoanInformation newLoanInformation, DeductInformation newDeductInformation,
			AccountInformation newAccountInformation, List<PaymentOrderInformation> newPaymentOrderInformationList,
			List<RepaymentPlanDetailShowModel> newReapymentPlanDetailShowModelList) {

			this.loanInformation = newLoanInformation;
			this.deductInformation = newDeductInformation;
			this.accountInformation = newAccountInformation;
			this.paymentOrderInformations = newPaymentOrderInformationList;
			this.repaymentPlanDetailShowModels = newReapymentPlanDetailShowModelList;
	}



	public LoanInformation getLoanInformation() {
		return loanInformation;
	}



	public void setLoanInformation(LoanInformation loanInformation) {
		this.loanInformation = loanInformation;
	}



	public DeductInformation getDeductInformation() {
		return deductInformation;
	}



	public void setDeductInformation(DeductInformation deductInformation) {
		this.deductInformation = deductInformation;
	}



	public AccountInformation getAccountInformation() {
		return accountInformation;
	}



	public void setAccountInformation(AccountInformation accountInformation) {
		this.accountInformation = accountInformation;
	}



	public List<PaymentOrderInformation> getPaymentOrderInformations() {
		return paymentOrderInformations;
	}



	public void setPaymentOrderInformations(List<PaymentOrderInformation> paymentOrderInformations) {
		this.paymentOrderInformations = paymentOrderInformations;
	}



	public List<RepaymentPlanDetailShowModel> getRepaymentPlanDetailShowModels() {
		return repaymentPlanDetailShowModels;
	}



	public void setRepaymentPlanDetailShowModels(List<RepaymentPlanDetailShowModel> repaymentPlanDetailShowModels) {
		this.repaymentPlanDetailShowModels = repaymentPlanDetailShowModels;
	}  
	
	
	

}
