package com.zufangbao.sun.yunxin.entity.model;

import java.util.Date;

import com.demo2do.core.utils.DateUtils;

public class ProjectInformationQueryModel {

	private Long financialContractId = -1l;
	
	private String contractNo;
	
	private String customerName;
	
	private String loanEffectStartDate;
	
	private String loanEffectEndDate;
	
	private String loanExpectTerminateStartDate;
	
	private String loanExpectTerminateEndDate;
	
	private String financialContractUuid;

	public Long getFinancialContractId() {
		return financialContractId;
	}

	public void setFinancialContractId(Long financialContractId) {
		this.financialContractId = financialContractId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getLoanEffectStartDate() {
		return loanEffectStartDate;
	}

	public void setLoanEffectStartDate(String loanEffectStartDate) {
		this.loanEffectStartDate = loanEffectStartDate;
	}

	public String getLoanEffectEndDate() {
		return loanEffectEndDate;
	}

	public void setLoanEffectEndDate(String loanEffectEndDate) {
		this.loanEffectEndDate = loanEffectEndDate;
	}

	public String getLoanExpectTerminateStartDate() {
		return loanExpectTerminateStartDate;
	}

	public void setLoanExpectTerminateStartDate(String loanExpectTerminateStartDate) {
		this.loanExpectTerminateStartDate = loanExpectTerminateStartDate;
	}

	public String getLoanExpectTerminateEndDate() {
		return loanExpectTerminateEndDate;
	}

	public void setLoanExpectTerminateEndDate(String loanExpectTerminateEndDate) {
		this.loanExpectTerminateEndDate = loanExpectTerminateEndDate;
	}
	
	public String getFinancialContractUuid() {
		return financialContractUuid;
	}

	public void setFinancialContractUuid(String financialContractUuid) {
		this.financialContractUuid = financialContractUuid;
	}

	public String getLoanEffectEndDateAddOneDay(){
		Date date = DateUtils.parseDate(this.loanEffectEndDate, "yyyy-MM-dd");
		Date addOneDayDate = DateUtils.addDays(date, 1);
		return DateUtils.format(addOneDayDate, "yyyy-MM-dd HH:mm:ss");
	}
	public String getLoanExpectTerminateEndDateAddOneDay(){
		Date date = DateUtils.parseDate(this.loanExpectTerminateEndDate, "yyyy-MM-dd");
		Date addOneDayDate = DateUtils.addDays(date, 1);
		return DateUtils.format(addOneDayDate, "yyyy-MM-dd HH:mm:ss");
	}

	
}
