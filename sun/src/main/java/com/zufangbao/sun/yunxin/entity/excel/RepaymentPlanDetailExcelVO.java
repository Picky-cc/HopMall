package com.zufangbao.sun.yunxin.entity.excel;

import org.apache.commons.lang.StringUtils;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.utils.SensitiveInfoUtils;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;
import com.zufangbao.sun.yunxin.entity.AssetSet;

public class RepaymentPlanDetailExcelVO {

	
	@ExcelVoAttribute(name = "信托合约编号", column = "A")
	private String financialContractNo;
	@ExcelVoAttribute(name = "信托合作商户名称", column = "B")
	private String appName;
	@ExcelVoAttribute(name = "贷款合同编号", column = "C")
	private String loanContractNo;
	@ExcelVoAttribute(name = "还款编号", column = "D")
	private String repaymentNo;
	@ExcelVoAttribute(name = "放款日期", column = "E")
	private String loanDate;
	@ExcelVoAttribute(name = "计划还款日期", column = "F")
	private String assetRecycleDate;
	@ExcelVoAttribute(name = "实际还款日期", column = "G")
	private String actualRecycleDate;
	@ExcelVoAttribute(name = "计划还款利息", column = "H")
	private String assetInterestValue;
	@ExcelVoAttribute(name = "计划还款本金", column = "I")
	private String assetPrincipalValue;
	@ExcelVoAttribute(name = "信托账户号", column = "J")
	private String financialAccountNo;
	@ExcelVoAttribute(name = "贷款客户姓名", column = "K")
	private String customerName;
	@ExcelVoAttribute(name = "贷款客户身份证号码", column = "L")
	private String idCardNo;
	@ExcelVoAttribute(name = "还款账户开户行名称", column = "M")
	private String bankName;
	@ExcelVoAttribute(name = "开户行所在省", column = "N")
	private String province;
	@ExcelVoAttribute(name = "开户行所在市", column = "O")
	private String city;
	@ExcelVoAttribute(name = "还款账户号", column = "P")
	private String payAcNo;
	@ExcelVoAttribute(name = "生效日期", column = "Q")
	private String effectiveDate;

	public RepaymentPlanDetailExcelVO(Contract contract,
			ContractAccount contractAccount,
			FinancialContract financialContract, AssetSet assetSet) {
		Account account = financialContract.getCapitalAccount();
		this.financialContractNo = financialContract.getContractNo();
		this.appName = contract.getApp().getAppId();
		this.loanContractNo = contract.getContractNo();
		this.repaymentNo = assetSet.getSingleLoanContractNo();
		this.loanDate = DateUtils.format(contract.getBeginDate());
		this.assetRecycleDate = DateUtils.format(assetSet.getAssetRecycleDate());
		if(assetSet.getActualRecycleDate()!=null){
			this.actualRecycleDate = DateUtils.format(assetSet.getActualRecycleDate());
		}
		this.assetInterestValue = assetSet.getAssetInterestValue().toString();
		this.assetPrincipalValue = assetSet.getAssetPrincipalValue().toString();
		if(!StringUtils.isEmpty(account.getAccountNo())){
			this.financialAccountNo = SensitiveInfoUtils.desensitizationString(account.getAccountNo());
		}
		this.customerName = contract.getCustomer().getName();
		if(!StringUtils.isEmpty(contractAccount.getIdCardNum())){
			this.idCardNo =SensitiveInfoUtils.desensitizationString(contractAccount.getIdCardNum());
		}
		this.bankName = contractAccount.getBank();
		this.province = contractAccount.getProvince();
		this.city = contractAccount.getCity();
		if(!StringUtils.isEmpty(contractAccount.getPayAcNo())){
			this.payAcNo = SensitiveInfoUtils.desensitizationString(contractAccount.getPayAcNo());
		}
		this.effectiveDate = DateUtils.format(contract.getBeginDate());
		
	}
	public RepaymentPlanDetailExcelVO(){
		
	}
	public String getFinancialContractNo() {
		return financialContractNo;
	}
	public void setFinancialContractNo(String financialContractNo) {
		this.financialContractNo = financialContractNo;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getLoanContractNo() {
		return loanContractNo;
	}
	public void setLoanContractNo(String loanContractNo) {
		this.loanContractNo = loanContractNo;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public String getAssetRecycleDate() {
		return assetRecycleDate;
	}
	public void setAssetRecycleDate(String assetRecycleDate) {
		this.assetRecycleDate = assetRecycleDate;
	}
	public String getAssetInterestValue() {
		return assetInterestValue;
	}
	public void setAssetInterestValue(String assetInterestValue) {
		this.assetInterestValue = assetInterestValue;
	}
	public String getAssetPrincipalValue() {
		return assetPrincipalValue;
	}
	public void setAssetPrincipalValue(String assetPrincipalValue) {
		this.assetPrincipalValue = assetPrincipalValue;
	}
	public String getFinancialAccountNo() {
		return financialAccountNo;
	}
	public void setFinancialAccountNo(String financialAccountNo) {
		this.financialAccountNo = financialAccountNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPayAcNo() {
		return payAcNo;
	}
	public void setPayAcNo(String payAcNo) {
		this.payAcNo = payAcNo;
	}
	
	public String getRepaymentNo() {
		return repaymentNo;
	}
	public void setRepaymentNo(String repaymentNo) {
		this.repaymentNo = repaymentNo;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	
	
	
}
