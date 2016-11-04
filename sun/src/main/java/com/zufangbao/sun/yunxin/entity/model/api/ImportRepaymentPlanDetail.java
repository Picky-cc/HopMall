package com.zufangbao.sun.yunxin.entity.model.api;

import java.math.BigDecimal;

public class ImportRepaymentPlanDetail {

	
	private String repaymentDate;
	
	private String repaymentPrincipal;
	
	private String repaymentInterest;
	
	private String techMaintenanceFee;
	
	private String loanServiceFee;
	
	private String otheFee;
	
	
	public BigDecimal getTheAdditionalFee(){
		return new BigDecimal(techMaintenanceFee).add(new BigDecimal(loanServiceFee)).add(new BigDecimal(otheFee));
		
	}
	
	public String getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	public String getRepaymentPrincipal() {
		return repaymentPrincipal;
	}

	public void setRepaymentPrincipal(String repaymentPrincipal) {
		this.repaymentPrincipal = repaymentPrincipal;
	}

	public String getRepaymentInterest() {
		return repaymentInterest;
	}

	public void setRepaymentInterest(String repaymentInterest) {
		this.repaymentInterest = repaymentInterest;
	}

	public String getOtheFee() {
		return otheFee;
	}
	
	public BigDecimal getBDOtheFee() {
		return new BigDecimal(otheFee);
	}

	public void setOtheFee(String otheFee) {
		this.otheFee = otheFee;
	}
	
	public String getTechMaintenanceFee() {
		return techMaintenanceFee;
	}
	
	public BigDecimal getBDTechMaintenanceFee() {
		return new BigDecimal(techMaintenanceFee);
	}

	public void setTechMaintenanceFee(String techMaintenanceFee) {
		this.techMaintenanceFee = techMaintenanceFee;
	}

	public String getLoanServiceFee() {
		return loanServiceFee;
	}
	
	public BigDecimal getBDLoanServiceFee() {
		return new BigDecimal(loanServiceFee);
	}

	public void setLoanServiceFee(String loanServiceFee) {
		this.loanServiceFee = loanServiceFee;
	}
}
