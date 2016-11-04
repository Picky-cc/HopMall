package com.zufangbao.sun.yunxin.entity.excel;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;
import com.zufangbao.sun.yunxin.entity.AssetSet;

public class ProjectInformationExeclVo {

	private Long contractId;
	
	@ExcelVoAttribute(name = "贷款合同编号", column = "A")
	private String contractNo;
	@ExcelVoAttribute(name = "资产编号", column = "B")
	private String assetNo;
	@ExcelVoAttribute(name = "贷款利率", column = "C")
	private String loanRate;
	@ExcelVoAttribute(name = "生效日期", column = "D")
	private String effectDate;
	@ExcelVoAttribute(name = "预计终止日期", column = "E")
	private String expectTerminalDate;
	@ExcelVoAttribute(name = "实际终止日期", column = "F")
	private String actualTermainalDate;
	@ExcelVoAttribute(name = "还款进度", column = "G")
	private String repaymentSchedule;
	@ExcelVoAttribute(name = "贷款方式", column = "H")
	private String loanType;
	@ExcelVoAttribute(name = "还款周期", column = "I")
	private int repaymentCycle;
	@ExcelVoAttribute(name = "还款日期", column = "J")
	private String repaymentDate;
	@ExcelVoAttribute(name = "客户姓名", column = "K")
	private String customerName;
	@ExcelVoAttribute(name = "贷款总额", column = "L")
	private String loanAmount;
	@ExcelVoAttribute(name = "本期还款金额", column = "M")
	private String currentPeriodRepaymentAmount;
	@ExcelVoAttribute(name = "本期还款利息", column = "N")
	private String currentPeriodRepaymentInterest;
	@ExcelVoAttribute(name = "还款情况", column = "O")
	private String repaymentSituation;

	public ProjectInformationExeclVo(Contract contract,
			String actualTermainalDate, String repaymentSchedule,
			String repaymentDate, AssetSet currentAssetSet,String expectTerminalDate) {
		this.contractId = contract.getId();
		this.contractNo = contract.getContractNo();
		this.assetNo = contract.getHouse().getAddress();
		this.loanRate = contract.getInterestRate().toString();
		this.effectDate = DateUtils.format(contract.getBeginDate(), "yyyy-MM-dd");
		this.expectTerminalDate = expectTerminalDate;
		if(actualTermainalDate !=null){
			this.actualTermainalDate = actualTermainalDate;
		}
		this.repaymentSchedule = repaymentSchedule;
		this.loanType = contract.getRepaymentWayMsg();
		this.repaymentCycle = contract.getPaymentFrequency();
		this.repaymentDate = repaymentDate;
		this.customerName = contract.getCustomer().getName();
		this.loanAmount = contract.getTotalAmount().toString();
		if(currentAssetSet != null){
			this.currentPeriodRepaymentAmount = currentAssetSet.getAssetPrincipalValue().toString();
			this.currentPeriodRepaymentInterest = currentAssetSet.getAssetInterestValue().toString();
			this.repaymentSituation = currentAssetSet.isPaidOff()?"已还":"未还";
		}
	}
	
	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getAssetNo() {
		return assetNo;
	}
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	public String getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(String loanRate) {
		this.loanRate = loanRate;
	}
	public String getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}
	public String getExpectTerminalDate() {
		return expectTerminalDate;
	}
	public void setExpectTerminalDate(String expectTerminalDate) {
		this.expectTerminalDate = expectTerminalDate;
	}
	public String getActualTermainalDate() {
		return actualTermainalDate;
	}
	public void setActualTermainalDate(String actualTermainalDate) {
		this.actualTermainalDate = actualTermainalDate;
	}
	public String getRepaymentSchedule() {
		return repaymentSchedule;
	}
	public void setRepaymentSchedule(String repaymentSchedule) {
		this.repaymentSchedule = repaymentSchedule;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public int getRepaymentCycle() {
		return repaymentCycle;
	}
	public void setRepaymentCycle(int repaymentCycle) {
		this.repaymentCycle = repaymentCycle;
	}
	public String getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getCurrentPeriodRepaymentAmount() {
		return currentPeriodRepaymentAmount;
	}
	public void setCurrentPeriodRepaymentAmount(String currentPeriodRepaymentAmount) {
		this.currentPeriodRepaymentAmount = currentPeriodRepaymentAmount;
	}
	public String getCurrentPeriodRepaymentInterest() {
		return currentPeriodRepaymentInterest;
	}
	public void setCurrentPeriodRepaymentInterest(
			String currentPeriodRepaymentInterest) {
		this.currentPeriodRepaymentInterest = currentPeriodRepaymentInterest;
	}
	public String getRepaymentSituation() {
		return repaymentSituation;
	}
	public void setRepaymentSituation(String repaymentSituation) {
		this.repaymentSituation = repaymentSituation;
	}

	public ProjectInformationExeclVo() {
		super();
	}
	
}
