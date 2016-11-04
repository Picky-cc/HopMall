package com.zufangbao.sun.yunxin.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;

public class DailyReturnListExcel {
	@ExcelVoAttribute(name = "扣款日期", column = "A")
	private String debitDate;
	@ExcelVoAttribute(name = "实际还款金额", column = "B")
	private String actualRepaymentAmount;
	@ExcelVoAttribute(name = "还款人名称", column = "C")
	private String repaymentName;
	@ExcelVoAttribute(name = "还款账号", column = "D")
	private String repaymentAccountNo;
	@ExcelVoAttribute(name = "计划还款日期", column = "E")
	private String planRepaymentDate;
	@ExcelVoAttribute(name = "计划还款本金", column = "F")
	private String planRepaymentPrincipal;
	@ExcelVoAttribute(name = "计划还款利息", column = "G")
	private String planRepaymentInterest;
	@ExcelVoAttribute(name = "信托账户", column = "H")
	private String financialAccountNo;
	@ExcelVoAttribute(name = "第三方扣款状态", column = "I")
	private String repaymentStatus;
	@ExcelVoAttribute(name = "备注", column = "J")
	private String remark;
	@ExcelVoAttribute(name = "银行", column = "K")
	private String bank;
	@ExcelVoAttribute(name = "发起时间", column = "L")
	private String deductSendDate;
	@ExcelVoAttribute(name = "最新更新时间", column = "M")
	private String lastModifyTime;
	@ExcelVoAttribute(name = "贷款合同编号", column = "N")
	private String contractNo;
	@ExcelVoAttribute(name = "合同生效日期", column = "O")
	private String effectDate;



	public DailyReturnListExcel() {

	}

	public DailyReturnListExcel(TransferApplication transferApplication) {
		this.actualRepaymentAmount = BigDecimal.ZERO.toString();
		if (transferApplication.getExecutingDeductStatus() == ExecutingDeductStatus.SUCCESS) {
			this.actualRepaymentAmount = transferApplication.getAmount()
					.toString();
		}
		
		this.financialAccountNo = transferApplication.getOrder().getFinancialContract()
				.getCapitalAccount().getAccountNo();
		this.debitDate = DateUtils.format(transferApplication.getCreateTime());
		this.planRepaymentDate = DateUtils.format(transferApplication
				.getOrder().getAssetSet().getAssetRecycleDate());
		this.planRepaymentInterest = transferApplication.getOrder()
				.getAssetSet().getAssetInterestValue().toString();
		this.planRepaymentPrincipal = transferApplication.getOrder()
				.getAssetSet().getAssetPrincipalValue().toString();
		this.repaymentAccountNo = transferApplication.getContractAccount()
				.getPayAcNo();
		this.repaymentName = transferApplication.getContractAccount()
				.getPayerName();
		setRepaymentStatus(transferApplication.getExecutingDeductStatus());
		this.remark = transferApplication.getComment();
		this.bank = transferApplication.getContractAccount().getBank();
		this.contractNo = transferApplication.getOrder().getAssetSet()
				.getContractNo();
		this.effectDate = DateUtils.format(transferApplication.getOrder().getAssetSet().getContract().getBeginDate());
		Date taSendTime = transferApplication.getDeductSendTime();
		if(taSendTime == null) {
			this.deductSendDate = "";
		}else {
			this.deductSendDate = DateUtils.format(taSendTime, "yyyy-MM-dd HH:mm:ss");
		}
		this.lastModifyTime = DateUtils.format(
				transferApplication.getLastModifiedTime(),
				"yyyy-MM-dd HH:mm:ss");
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getDeductSendDate() {
		return deductSendDate;
	}

	public void setDeductSendDate(String deductSendDate) {
		this.deductSendDate = deductSendDate;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getDebitDate() {
		return debitDate;
	}

	public void setDebitDate(String debitDate) {
		this.debitDate = debitDate;
	}

	public String getActualRepaymentAmount() {
		return actualRepaymentAmount;
	}

	public void setActualRepaymentAmount(String actualRepaymentAmount) {
		this.actualRepaymentAmount = actualRepaymentAmount;
	}

	public String getRepaymentName() {
		return repaymentName;
	}

	public void setRepaymentName(String repaymentName) {
		this.repaymentName = repaymentName;
	}

	public String getRepaymentAccountNo() {
		return repaymentAccountNo;
	}

	public void setRepaymentAccountNo(String repaymentAccountNo) {
		this.repaymentAccountNo = repaymentAccountNo;
	}

	public String getPlanRepaymentDate() {
		return planRepaymentDate;
	}

	public void setPlanRepaymentDate(String planRepaymentDate) {
		this.planRepaymentDate = planRepaymentDate;
	}

	public String getPlanRepaymentPrincipal() {
		return planRepaymentPrincipal;
	}

	public void setPlanRepaymentPrincipal(String planRepaymentPrincipal) {
		this.planRepaymentPrincipal = planRepaymentPrincipal;
	}

	public String getPlanRepaymentInterest() {
		return planRepaymentInterest;
	}

	public void setPlanRepaymentInterest(String planRepaymentInterest) {
		this.planRepaymentInterest = planRepaymentInterest;
	}

	public String getFinancialAccountNo() {
		return financialAccountNo;
	}

	public void setFinancialAccountNo(String financialAccountNo) {
		this.financialAccountNo = financialAccountNo;
	}

	public String getRepaymentStatus() {
		return repaymentStatus;
	}

	public void setRepaymentStatus(ExecutingDeductStatus executingDeductStatus) {
		if (executingDeductStatus.equals(ExecutingDeductStatus.CREATE)) {
			this.repaymentStatus = "已创建";
		}
		if (executingDeductStatus.equals(ExecutingDeductStatus.DOING)) {
			this.repaymentStatus = "处理中";
		}
		if (executingDeductStatus.equals(ExecutingDeductStatus.SUCCESS)) {
			this.repaymentStatus = "成功";
		}
		if (executingDeductStatus.equals(ExecutingDeductStatus.FAIL)) {
			this.repaymentStatus = "失败";
		}
		if (executingDeductStatus.equals(ExecutingDeductStatus.TIME_OUT)) {
			this.repaymentStatus = "异常";
		}
	}
	public String getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}

}
