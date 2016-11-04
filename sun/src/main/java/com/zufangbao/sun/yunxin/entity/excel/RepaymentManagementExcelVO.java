package com.zufangbao.sun.yunxin.entity.excel;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;
import com.zufangbao.sun.yunxin.entity.model.assetset.AssetSetShowModel;

public class RepaymentManagementExcelVO {

	@ExcelVoAttribute(name = "还款编号", column = "A")
	private String singleLoanContractNo;
	@ExcelVoAttribute(name = "贷款合同编号", column = "B")
	private String contractNo;
	@ExcelVoAttribute(name = "信托合约编号", column = "C")
	private String financialContractNo;
	@ExcelVoAttribute(name = "信托项目名称", column = "D")
	private String financialProjectName;
	@ExcelVoAttribute(name = "资产编号", column = "E")
	private String assetNo;
	@ExcelVoAttribute(name = "客户姓名", column = "F")
	private String customerName;
	@ExcelVoAttribute(name = "计划还款日期", column = "G")
	private String assetRecycleDate;
	@ExcelVoAttribute(name = "当前期数", column = "H")
	private int currentPeriod;
	@ExcelVoAttribute(name = "总期数", column = "I")
	private int allPeriods;
	@ExcelVoAttribute(name = "计划还款本金", column = "J")
	private String assetPrincipalValue;
	@ExcelVoAttribute(name = "计划还款利息", column = "K")
	private String assetInterestValue;
	@ExcelVoAttribute(name = "计划还款金额", column = "L")
	private String assetInitialValue;
	@ExcelVoAttribute(name = "差异天数", column = "M")
	private int overDueDays;
	@ExcelVoAttribute(name = "差异罚息金额", column = "N")
    private String penaltyInterestAmount;
	@ExcelVoAttribute(name = "应还款金额", column = "O")
	private String amount;
	@ExcelVoAttribute(name = "实际还款金额", column = "P")
	private String actualAmount;
	@ExcelVoAttribute(name = "实际还款日期", column = "Q")
	private String actualRecycleDate;
	@ExcelVoAttribute(name = "退款金额", column = "R")
	private String refundAmount;
	@ExcelVoAttribute(name = "还款状态", column = "S")
	private String paymentStatus;
	@ExcelVoAttribute(name = "担保状态", column = "T")
	private String guaranteeStatus;
	@ExcelVoAttribute(name = "逾期天数", column = "U")
	private int auditOverdueDays;
	@ExcelVoAttribute(name = "备注", column = "V")
	private String comment;
	
	public RepaymentManagementExcelVO(AssetSetShowModel showModel) {
		this.singleLoanContractNo = showModel.getSingleLoanContractNo();
		this.contractNo = showModel.getContractNo();
		this.financialContractNo = showModel.getFinancialContractNo();
		this.financialProjectName = showModel.getFinancialProjectName();
		this.assetNo = showModel.getAssetNo();
		this.customerName = showModel.getCustomerName();
		this.assetRecycleDate = DateUtils.format(showModel.getAssetRecycleDate());
		this.currentPeriod = showModel.getCurrentPeriod();
		this.allPeriods = showModel.getAllPeriods();
		this.assetPrincipalValue = showModel.getAssetPrincipalValue().toString();
		this.assetInterestValue = showModel.getAssetInterestValue().toString();
		this.assetInitialValue = showModel.getAssetInitialValue().toString();
		this.overDueDays = showModel.getOverDueDays();
		this.penaltyInterestAmount = showModel.getPenaltyInterestAmount().toString();
		this.amount = showModel.getAmount().toString();
		this.actualAmount = showModel.getActualAmount().toString();
		if(showModel.getActualRecycleDate() != null){
		    this.actualRecycleDate = DateUtils.format(showModel.getActualRecycleDate());
		}
		this.refundAmount = showModel.getRefundAmount().toString();
		this.paymentStatus = showModel.getPaymentStatus();
		this.guaranteeStatus = showModel.getGuaranteeStatus();
		this.auditOverdueDays = showModel.getAuditOverdueDays();
		this.comment = showModel.getComment();
		
	}
	public String getSingleLoanContractNo() {
		return singleLoanContractNo;
	}
	public void setSingleLoanContractNo(String singleLoanContractNo) {
		this.singleLoanContractNo = singleLoanContractNo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getFinancialContractNo() {
		return financialContractNo;
	}
	public void setFinancialContractNo(String financialContractNo) {
		this.financialContractNo = financialContractNo;
	}
	public String getFinancialProjectName() {
		return financialProjectName;
	}
	public void setFinancialProjectName(String financialProjectName) {
		this.financialProjectName = financialProjectName;
	}
	public String getAssetNo() {
		return assetNo;
	}
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAssetRecycleDate() {
		return assetRecycleDate;
	}
	public void setAssetRecycleDate(String assetRecycleDate) {
		this.assetRecycleDate = assetRecycleDate;
	}
	public int getCurrentPeriod() {
		return currentPeriod;
	}
	public void setCurrentPeriod(int currentPeriod) {
		this.currentPeriod = currentPeriod;
	}
	public int getAllPeriods() {
		return allPeriods;
	}
	public void setAllPeriods(int allPeriods) {
		this.allPeriods = allPeriods;
	}
	public String getAssetPrincipalValue() {
		return assetPrincipalValue;
	}
	public void setAssetPrincipalValue(String assetPrincipalValue) {
		this.assetPrincipalValue = assetPrincipalValue;
	}
	public String getAssetInterestValue() {
		return assetInterestValue;
	}
	public void setAssetInterestValue(String assetInterestValue) {
		this.assetInterestValue = assetInterestValue;
	}
	public String getAssetInitialValue() {
		return assetInitialValue;
	}
	public void setAssetInitialValue(String assetInitialValue) {
		this.assetInitialValue = assetInitialValue;
	}
	public int getOverDueDays() {
		return overDueDays;
	}
	public void setOverDueDays(int overDueDays) {
		this.overDueDays = overDueDays;
	}
	public String getPenaltyInterestAmount() {
		return penaltyInterestAmount;
	}
	public void setPenaltyInterestAmount(String penaltyInterestAmount) {
		this.penaltyInterestAmount = penaltyInterestAmount;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}
	public String getActualRecycleDate() {
		return actualRecycleDate;
	}
	public void setActualRecycleDate(String actualRecycleDate) {
		this.actualRecycleDate = actualRecycleDate;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getGuaranteeStatus() {
		return guaranteeStatus;
	}
	public void setGuaranteeStatus(String guaranteeStatus) {
		this.guaranteeStatus = guaranteeStatus;
	}
	public int getAuditOverdueDays() {
		return auditOverdueDays;
	}
	public void setAuditOverdueDays(int auditOverdueDays) {
		this.auditOverdueDays = auditOverdueDays;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
