package com.zufangbao.official.account.entity;

import java.math.BigDecimal;
import java.util.Date;

public class IOU{

	private  String   projectCode;
	
	private  BigDecimal   singleAmount;

	private  Date     loanDate;
	
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public BigDecimal getSingleAmount() {
		return singleAmount;
	}

	public void setSingleAmount(BigDecimal singleAmount) {
		this.singleAmount = singleAmount;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	
}
