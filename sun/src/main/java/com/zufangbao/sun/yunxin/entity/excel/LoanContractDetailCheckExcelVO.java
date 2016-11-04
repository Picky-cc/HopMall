package com.zufangbao.sun.yunxin.entity.excel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.utils.SensitiveInfoUtils;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;
import com.zufangbao.sun.yunxin.entity.RepaymentWay;

public class LoanContractDetailCheckExcelVO {

	@ExcelVoAttribute(name = "信托合约编号", column = "A")
	private String financialContractNo;
	@ExcelVoAttribute(name = "信托合作商户名称", column = "B")
	private String appName;
	@ExcelVoAttribute(name = "信托账户号", column = "C")
	private String accountNo;
	@ExcelVoAttribute(name = "贷款合同编号", column = "D")
	private String contractNo;
	@ExcelVoAttribute(name = "贷款客户姓名", column = "E")
	private String customerName;
	@ExcelVoAttribute(name = "放款日期", column = "F")
	private String loanDate;
	@ExcelVoAttribute(name = "到期日期", column = "G")
	private String dueDate;
	@ExcelVoAttribute(name = "贷款本金总额", column = "H")
	private String totalAmount;
	@ExcelVoAttribute(name = "贷款利息总额", column = "I")
	private String totalInterest;
	@ExcelVoAttribute(name = "贷款期数", column = "J")
	private int periods;
	@ExcelVoAttribute(name = "剩余本金余额", column = "K")
	private String restSumPrincipal;
	@ExcelVoAttribute(name = "剩余利息余额", column = "L")
	private String restSumInterest;
	@ExcelVoAttribute(name = "还款方法", column = "M")
	private String repaymentWay;
	@ExcelVoAttribute(name = "贷款利率", column = "N")
	private String interestRate;
	@ExcelVoAttribute(name = "罚息利率", column = "O")
	private String penaltyInterest;
	@ExcelVoAttribute(name = "回购期", column = "P")
	private int loanOverdueEndDay;
	@ExcelVoAttribute(name = "还款宽限期", column = "Q")
	private int repaymentGraceTerm;
	@ExcelVoAttribute(name = "贷款客户身份证号码", column = "R")
	private String idCardNum;
	@ExcelVoAttribute(name = "还款账户开户行名称", column = "S")
	private String bank;
	@ExcelVoAttribute(name = "开户行所在省", column = "T")
	private String province;
	@ExcelVoAttribute(name = "开户行所在市", column = "U")
	private String city;
	@ExcelVoAttribute(name = "还款账户号", column = "V")
	private String payAcNo;
	@ExcelVoAttribute(name = "设定生效日期", column = "W")
	private String beginDate;

	public LoanContractDetailCheckExcelVO(FinancialContract financialContract,
			Contract contract, Date maxAssetRecycleDate,
			BigDecimal totalInterest, BigDecimal restSumPrincipal,
			BigDecimal restSumInterest, ContractAccount contractAccount) {
		this.financialContractNo = financialContract.getContractNo();
		this.appName = contract.getApp().getAppId();
		this.accountNo = SensitiveInfoUtils.desensitizationString(financialContract.getCapitalAccount().getAccountNo());
		this.contractNo = contract.getContractNo();
		this.customerName = contract.getCustomer().getName();
		this.loanDate = DateUtils.format(contract.getBeginDate());
		if (maxAssetRecycleDate != null) {
			this.dueDate = DateUtils.format(maxAssetRecycleDate);
		}
		this.totalAmount = contract.getTotalAmount().toString();
		this.totalInterest = totalInterest.toString();
		this.periods = contract.getPeriods();
		this.restSumPrincipal = restSumPrincipal.toString();
		this.restSumInterest = restSumInterest.toString();
		this.repaymentWay = castRepaymentWay(contract.getRepaymentWay());
		DecimalFormat df = new DecimalFormat("0.00%");
		df.setMinimumFractionDigits(2);
		this.interestRate = df.format(contract.getInterestRate());
		this.penaltyInterest =df.format(contract.getPenaltyInterest());
		this.loanOverdueEndDay = financialContract.getLoanOverdueEndDay();
		this.repaymentGraceTerm = financialContract.getLoanOverdueStartDay() - 1;
		
		if(!StringUtils.isEmpty(contractAccount.getIdCardNum())){
			this.idCardNum =SensitiveInfoUtils.desensitizationString(contractAccount.getIdCardNum());
		}
		this.bank = contractAccount.getBank();
		this.province = contractAccount.getProvince();
		this.city = contractAccount.getCity();
		if(!StringUtils.isEmpty(contractAccount.getPayAcNo())){
			this.payAcNo = SensitiveInfoUtils.desensitizationString(contractAccount.getPayAcNo());
		}
		this.beginDate = DateUtils.format(contract.getBeginDate());
	}

	private String castRepaymentWay(RepaymentWay repaymentWay) {
		switch (repaymentWay) {
		case AVERAGE_CAPITAL_PLUS_INTEREST:
			return "等额本息";
		case AVERAGE_CAPITAL:
			return "等额本金";
		case SAW_TOOTH:
			return "锯齿形";
		default:
			return "";
		}
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

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(String totalInterest) {
		this.totalInterest = totalInterest;
	}

	public int getPeriods() {
		return periods;
	}

	public void setPeriods(int periods) {
		this.periods = periods;
	}

	public String getRestSumPrincipal() {
		return restSumPrincipal;
	}

	public void setRestSumrincipal(String restSumPrincipal) {
		this.restSumPrincipal = restSumPrincipal;
	}

	public String getRestSumInterest() {
		return restSumInterest;
	}

	public void setRestSumInterest(String restSumInterest) {
		this.restSumInterest = restSumInterest;
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

	public String getPenaltyInterest() {
		return penaltyInterest;
	}

	public void setPenaltyInterest(String penaltyInterest) {
		this.penaltyInterest = penaltyInterest;
	}

	public int getLoanOverdueEndDay() {
		return loanOverdueEndDay;
	}

	public void setLoanOverdueEndDay(int loanOverdueEndDay) {
		this.loanOverdueEndDay = loanOverdueEndDay;
	}

	public int getRepaymentGraceTerm() {
		return repaymentGraceTerm;
	}

	public void setRepaymentGraceTerm(int repaymentGraceTerm) {
		this.repaymentGraceTerm = repaymentGraceTerm;
	}

	public String getIdCardNum() {
		return idCardNum;
	}

	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
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

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

}
