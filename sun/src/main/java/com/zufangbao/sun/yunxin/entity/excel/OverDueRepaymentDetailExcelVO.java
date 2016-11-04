package com.zufangbao.sun.yunxin.entity.excel;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;
import com.zufangbao.sun.yunxin.entity.AssetSet;

public class OverDueRepaymentDetailExcelVO {

	@ExcelVoAttribute(name = "信托合作商户名称", column = "A")
	private String appName;
	@ExcelVoAttribute(name = "贷款合同编号", column = "B")
	private String loanContractNo;
	@ExcelVoAttribute(name = "还款编号", column = "C")
	private String repaymentNo;
	@ExcelVoAttribute(name = "放款日期", column = "D")
	private String loanDate;
	@ExcelVoAttribute(name = "贷款客户姓名", column = "E")
	private String customerName;
	@ExcelVoAttribute(name = "计划还款日期", column = "F")
	private String assetRecycleDate;
	@ExcelVoAttribute(name = "实际还款日期", column = "G")
	private String actualRecycleDate;
	@ExcelVoAttribute(name = "当前期数", column = "H")
	private int currentPeriod;
	@ExcelVoAttribute(name = "计划还款本金", column = "I")
	private String assetPrincipalValue;
	@ExcelVoAttribute(name = "计划还款利息", column = "J")
	private String assetInterestValue;
	@ExcelVoAttribute(name = "计划还款金额", column = "K")
	private String assetInitialValue;
	@ExcelVoAttribute(name = "差异天数", column = "L")
	private int daysDifference;
	@ExcelVoAttribute(name = "差异罚息金额", column = "M")
	private String differencesPenaltyAmount;
	@ExcelVoAttribute(name = "还款金额", column = "N")
	private String assetFairValue;
	@ExcelVoAttribute(name = "退款金额", column = "O")
	private String refundAmount;
	@ExcelVoAttribute(name = "还款状态", column = "P")
	private String repaymentStatus;
	@ExcelVoAttribute(name = "逾期状态", column = "Q")
	private String overDueStatus;
	@ExcelVoAttribute(name = "担保状态", column = "R")
	private String guaranteeStatus;
	@ExcelVoAttribute(name = "备注", column = "S")
	private String comment;
	
	
	public OverDueRepaymentDetailExcelVO(AssetSet assetSet) {
		Contract contract = assetSet.getContract();
		this.appName = contract.getApp().getAppId();
		this.loanContractNo = contract.getContractNo();
		this.repaymentNo = assetSet.getSingleLoanContractNo();
		this.loanDate = DateUtils.format(contract.getBeginDate());
		this.customerName = contract.getCustomer().getName();
		this.assetRecycleDate = DateUtils.format(assetSet.getAssetRecycleDate());
		if(assetSet.getActualRecycleDate() != null){
			this.actualRecycleDate =  DateUtils.format(assetSet.getActualRecycleDate());
		}
		this.currentPeriod =assetSet.getCurrentPeriod();
		this.assetPrincipalValue =StringUtils.defaultString(assetSet.getAssetPrincipalValue());
		this.assetInterestValue = StringUtils.defaultString(assetSet.getAssetInterestValue());
		this.assetInitialValue = StringUtils.defaultString(assetSet.getAssetInitialValue());
		this.daysDifference = assetSet.getOverDueDays();
		this.differencesPenaltyAmount =StringUtils.defaultString(assetSet.getAssetFairValue().subtract(assetSet.getAssetInitialValue()));
		this.assetFairValue = StringUtils.defaultString(assetSet.getAssetFairValue());
		this.refundAmount = StringUtils.defaultString(assetSet.getRefundAmount());
		this.repaymentStatus = assetSet.isPaidOff()?"还款成功":"还款失败";
		this.overDueStatus = assetSet.getOverdueStatus().getChineseMessage();
		this.guaranteeStatus = assetSet.getGuaranteeStatusString();
		this.comment = assetSet.getComment();
	}
	
	public OverDueRepaymentDetailExcelVO(){
		
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
	public String getActualRecycleDate() {
		return actualRecycleDate;
	}
	public void setActualRecycleDate(String actualRecycleDate) {
		this.actualRecycleDate = actualRecycleDate;
	}
	public int getCurrentPeriod() {
		return currentPeriod;
	}
	public void setCurrentPeriod(int currentPeriod) {
		this.currentPeriod = currentPeriod;
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
	public String getAssetInitialValue() {
		return assetInitialValue;
	}
	public void setAssetInitialValue(String assetInitialValue) {
		this.assetInitialValue = assetInitialValue;
	}
	public int getDaysDifference() {
		return daysDifference;
	}
	public void setDaysDifference(int daysDifference) {
		this.daysDifference = daysDifference;
	}
	public String getDifferencesPenaltyAmount() {
		return differencesPenaltyAmount;
	}
	public void setDifferencesPenaltyAmount(String differencesPenaltyAmount) {
		this.differencesPenaltyAmount = differencesPenaltyAmount;
	}
	public String getAssetFairValue() {
		return assetFairValue;
	}
	public void setAssetFairValue(String assetFairValue) {
		this.assetFairValue = assetFairValue;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getRepaymentStatus() {
		return repaymentStatus;
	}
	public void setRepaymentStatus(String repaymentStatus) {
		this.repaymentStatus = repaymentStatus;
	}
	public String getGuaranteeStatus() {
		return guaranteeStatus;
	}
	public void setGuaranteeStatus(String guaranteeStatus) {
		this.guaranteeStatus = guaranteeStatus;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
