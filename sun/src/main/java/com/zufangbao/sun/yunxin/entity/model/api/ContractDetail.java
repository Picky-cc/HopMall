package com.zufangbao.sun.yunxin.entity.model.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.house.House;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.ContractState;
import com.zufangbao.sun.yunxin.entity.RepaymentWay;
public class ContractDetail {


	
	private String uniqueId;
	
	private String loanContractNo;
	
	private String loanCustomerNo;
	
	private String loanCustomerName;
	
	private String subjectMatterassetNo;
	
	private String iDCardNo;
	
	private String bankCode;
	
	private String bankOfTheProvinceCode;
	
	private String bankOfTheCityCode;
	
	private String repaymentAccountNo;
	
	private String loanTotalAmount;
	
	private int loanPeriods;
	
	private String effectDate;
	
	private String expiryDate;
	
	private String loanRates;
	
	private int  interestRateCycle;
	
	private String penalty;
	
	private int  repaymentWay;

	private List<ImportRepaymentPlanDetail> repaymentPlanDetails;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getLoanContractNo() {
		return loanContractNo;
	}

	public void setLoanContractNo(String loanContractNo) {
		this.loanContractNo = loanContractNo;
	}

	public String getLoanCustomerNo() {
		return loanCustomerNo;
	}

	public void setLoanCustomerNo(String loanCustomerNo) {
		this.loanCustomerNo = loanCustomerNo;
	}

	public String getLoanCustomerName() {
		return loanCustomerName;
	}

	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}


	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankOfTheProvince() {
		return bankOfTheProvinceCode;
	}

	public void setBankOfTheProvince(String bankOfTheProvince) {
		this.bankOfTheProvinceCode = bankOfTheProvince;
	}

	public String getBankOfTheCity() {
		return bankOfTheCityCode;
	}

	public void setBankOfTheCity(String bankOfTheCity) {
		this.bankOfTheCityCode = bankOfTheCity;
	}

	public String getRepaymentAccountNo() {
		return repaymentAccountNo;
	}

	public void setRepaymentAccountNo(String repaymentAccountNo) {
		this.repaymentAccountNo = repaymentAccountNo;
	}

	public String getLoanTotalAmount() {
		return loanTotalAmount;
	}

	public void setLoanTotalAmount(String loanTotalAmount) {
		this.loanTotalAmount = loanTotalAmount;
	}


	public String getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getLoanRates() {
		return loanRates;
	}

	public void setLoanRates(String loanRates) {
		this.loanRates = loanRates;
	}

	public int getLoanPeriods() {
		return loanPeriods;
	}

	public void setLoanPeriods(int loanPeriods) {
		this.loanPeriods = loanPeriods;
	}
	public String getPenalty() {
		return penalty;
	}

	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}
	public int getInterestRateCycle() {
		return interestRateCycle;
	}

	public void setInterestRateCycle(int interestRateCycle) {
		this.interestRateCycle = interestRateCycle;
	}

	public int getRepaymentWay() {
		return repaymentWay;
	}

	public void setRepaymentWay(int repaymentWay) {
		this.repaymentWay = repaymentWay;
	}

	public List<ImportRepaymentPlanDetail> getRepaymentPlanDetails() {
		return repaymentPlanDetails;
	}

	public void setRepaymentPlanDetails(
			List<ImportRepaymentPlanDetail> repaymentPlanDetails) {
		this.repaymentPlanDetails = repaymentPlanDetails;
	}

	public String getIDCardNo() {
		return iDCardNo;
	}

	public void setIDCardNo(String iDCardNo) {
		this.iDCardNo = iDCardNo;
	}

	public String getSubjectMatterassetNo() {
		return subjectMatterassetNo;
	}

	public void setSubjectMatterassetNo(String subjectMatterassetNo) {
		this.subjectMatterassetNo = subjectMatterassetNo;
	}

	public void copyToContract(App app, Customer customer, House house, Contract contract, FinancialContract financialContract) {
		contract.setApp(app);
		contract.setCustomer(customer);
		contract.setHouse(house);
		contract.setContractNo(getLoanContractNo());
		contract.setUniqueId(getUniqueId());
		
		contract.setTotalAmount(new BigDecimal(getLoanTotalAmount()));
		contract.setPeriods(getLoanPeriods());
		contract.setBeginDate(DateUtils.asDay(getEffectDate()));
		contract.setEndDate(DateUtils.asDay(getExpiryDate()));
		
		contract.setCreateTime(new Date());
		contract.setRepaymentWay(RepaymentWay.fromValue(getRepaymentWay()));
		
		
		contract.setInterestRate(new BigDecimal(getLoanRates()));
		contract.setPenaltyInterest(new BigDecimal(getPenalty()));
		contract.setActiveVersionNo(Contract.INITIAL_VERSION_NO);
		contract.setUuid(UUID.randomUUID().toString());
		contract.setState(ContractState.EFFECTIVE);
		contract.setFinancialContractUuid(financialContract.getFinancialContractUuid());
	}

	
}
