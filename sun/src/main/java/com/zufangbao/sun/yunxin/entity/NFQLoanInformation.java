package com.zufangbao.sun.yunxin.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.house.House;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;
import com.zufangbao.sun.yunxin.ContractState;
/**
 * 农分期资产包 - 贷款信息
 * @author louguanyang
 *
 */
public class NFQLoanInformation {
	@ExcelVoAttribute(name = "信托合约编号", column = "A")
	private String trustContractNo;
	@ExcelVoAttribute(name = "信托合作商户名称", column = "B")
	private String appId;
	@ExcelVoAttribute(name = "信托账户号", column = "C")
	private String trustAccountNo;
	@ExcelVoAttribute(name = "贷款合同编号", column = "D")
	private String contractNo;
	@ExcelVoAttribute(name = "贷款客户编号", column = "E")
	private String customerCode;
	@ExcelVoAttribute(name = "贷款客户姓名", column = "F")
	private String customerName;
	@ExcelVoAttribute(name = "贷款客户身份证号码", column = "G")
	private String customerIDNo;
	@ExcelVoAttribute(name = "还款账户开户行编号", column = "H")
	private String customerBankCode;
	@ExcelVoAttribute(name = "还款账户开户行名称", column = "I")
	private String customerBankName;
	@ExcelVoAttribute(name = "开户行所在省", column = "J")
	private String customerBankProvince;
	@ExcelVoAttribute(name = "开户行所在市", column = "K")
	private String customerBankCity;
	@ExcelVoAttribute(name = "还款账户号", column = "L")
	private String customerAccountNo;
	@ExcelVoAttribute(name = "贷款本金总额", column = "M")
	private String loanCapitalSum;
	@ExcelVoAttribute(name = "贷款期数", column = "N")
	private String periods;
	@ExcelVoAttribute(name = "设定生效日期", column = "O")
	private String startDate;
	@ExcelVoAttribute(name = "还款方法", column = "P")
	private String repaymentWay;
	@ExcelVoAttribute(name = "贷款利率", column = "Q")
	private String interestRate;
	@ExcelVoAttribute(name = "罚息", column = "R")
	private String penaltyInterest;
	@ExcelVoAttribute(name = "回购期", column = "S")
	private String advaRepoTerm;
	@ExcelVoAttribute(name = "还款宽限期", column = "T")
	private String advaMatuterm;
	public String getTrustContractNo() {
		return trustContractNo;
	}
	public void setTrustContractNo(String trustContractNo) {
		this.trustContractNo = trustContractNo;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTrustAccountNo() {
		return trustAccountNo;
	}
	public void setTrustAccountNo(String trustAccountNo) {
		this.trustAccountNo = trustAccountNo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerIDNo() {
		return customerIDNo;
	}
	public void setCustomerIDNo(String customerIDNo) {
		this.customerIDNo = customerIDNo;
	}
	public String getCustomerBankCode() {
		return StringUtils.removeDecimal(customerBankCode);
	}
	public void setCustomerBankCode(String customerBankCode) {
		this.customerBankCode = customerBankCode;
	}
	public String getCustomerBankName() {
		return customerBankName;
	}
	public void setCustomerBankName(String customerBankName) {
		this.customerBankName = customerBankName;
	}
	public String getCustomerBankProvince() {
		return customerBankProvince;
	}
	public void setCustomerBankProvince(String customerBankProvince) {
		this.customerBankProvince = customerBankProvince;
	}
	public String getCustomerBankCity() {
		return customerBankCity;
	}
	public void setCustomerBankCity(String customerBankCity) {
		this.customerBankCity = customerBankCity;
	}
	public String getCustomerAccountNo() {
		return customerAccountNo;
	}
	public void setCustomerAccountNo(String customerAccountNo) {
		this.customerAccountNo = customerAccountNo;
	}
	public String getLoanCapitalSum() {
		return loanCapitalSum;
	}
	public void setLoanCapitalSum(String loanCapitalSum) {
		this.loanCapitalSum = loanCapitalSum;
	}
	public String getPeriods() {
		return periods;
	}
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getRepaymentWay() {
		return repaymentWay;
	}
	public void setRepaymentWay(String repaymentWay) {
		this.repaymentWay = repaymentWay;
	}
	public String getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	public String getAdvaRepoTerm() {
		return advaRepoTerm;
	}
	public void setAdvaRepoTerm(String advaRepoTerm) {
		this.advaRepoTerm = advaRepoTerm;
	}
	public String getAdvaMatuterm() {
		return advaMatuterm;
	}
	public void setAdvaMatuterm(String advaMatuterm) {
		this.advaMatuterm = advaMatuterm;
	}
	public String getPenaltyInterest() {
		return penaltyInterest;
	}
	public void setPenaltyInterest(String penaltyInterest) {
		this.penaltyInterest = penaltyInterest;
	}
	public int getPeriodsIntValue() {
		try {
			return new Float(periods).intValue();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	public void copyToContract(App app, Customer customer, House house, Contract contract, FinancialContract financialContract) {
		contract.setApp(app);
		contract.setCustomer(customer);
		contract.setHouse(house);
		contract.setContractNo(getContractNo());
	    //TODO 是否还需要
		contract.setAssetType(AssetType.NONGFENQISEED);
		contract.setFinancialContractUuid(financialContract.getFinancialContractUuid());
		
		contract.setTotalAmount(new BigDecimal(getLoanCapitalSum()));
		contract.setPeriods(getPeriodsIntValue());
		contract.setBeginDate(DateUtils.asDay(getStartDate()));
		contract.setCreateTime(new Date());
		contract.setRepaymentWay(RepaymentWay.fromString(getRepaymentWay()));
		contract.setInterestRate(new BigDecimal(getInterestRate()));
		contract.setPenaltyInterest(new BigDecimal(getPenaltyInterest()));
		contract.setActiveVersionNo(Contract.INITIAL_VERSION_NO);
		contract.setUuid(UUID.randomUUID().toString());
		contract.setState(ContractState.EFFECTIVE);
	}

}
