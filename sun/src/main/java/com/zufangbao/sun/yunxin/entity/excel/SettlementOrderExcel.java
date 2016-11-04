package com.zufangbao.sun.yunxin.entity.excel;

import java.math.BigDecimal;
import java.util.Date;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;
import com.zufangbao.sun.yunxin.SettlementStatus;

public class SettlementOrderExcel {

	@ExcelVoAttribute(name = "结清单号", column = "A")
	private String settleOrderNo;
	@ExcelVoAttribute(name = "回款单号", column = "B")
	private String repaymentNo;
	@ExcelVoAttribute(name = "应还日期", column = "C")
	private String recycleDate;
	@ExcelVoAttribute(name = "清算截止日", column = "D")
	private String dueDate;
	@ExcelVoAttribute(name = "商户编号", column = "E")
	private String appId;
	@ExcelVoAttribute(name = "本息金额", column = "F")
	private String principalAndInterestAmount;
	@ExcelVoAttribute(name = "差异天数", column = "G")
	private int overdueDays;
	@ExcelVoAttribute(name = "差异罚息", column = "H")
	private String overduePenalty;
	@ExcelVoAttribute(name = "发生时间", column = "I")
	private String modifyTime;
	@ExcelVoAttribute(name = "结清金额", column = "J")
	private String settlementAmount;
	@ExcelVoAttribute(name = "状态", column = "K")
	private String settlementStatus;
	@ExcelVoAttribute(name = "备注", column = "L")
	private String comment;

	public String getSettleOrderNo() {
		return settleOrderNo;
	}

	public void setSettleOrderNo(String settleOrderNo) {
		this.settleOrderNo = settleOrderNo;
	}

	public String getRepaymentNo() {
		return repaymentNo;
	}

	public void setRepaymentNo(String repaymentNo) {
		this.repaymentNo = repaymentNo;
	}

	public String getRecycleDate() {
		return recycleDate;
	}

	public void setRecycleDate(Date recycleDate) {
		if(recycleDate == null){
			return ;
		}
		this.recycleDate = DateUtils.format(recycleDate, "yyyy-MM-dd");
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		if(dueDate == null){
			return;
		}
		this.dueDate = DateUtils.format(dueDate, "yyyy-MM-dd");
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getPrincipalAndInterestAmount() {
		return principalAndInterestAmount;
	}

	public void setPrincipalAndInterestAmount(BigDecimal principalAndInterestAmount) {
		this.principalAndInterestAmount = principalAndInterestAmount.toString();
	}

	public int getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(int overdueDays) {
		this.overdueDays = overdueDays;
	}

	public String getOverduePenalty() {
		return overduePenalty;
	}

	public void setOverduePenalty(BigDecimal overduePenalty) {
		this.overduePenalty = overduePenalty.toString();
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		if(modifyTime == null){
			return;
		}
		this.modifyTime = DateUtils.format(modifyTime,"yyyy-MM-dd HH:mm:SS");
	}

	public String getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount.toString();
	}

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(SettlementStatus settlementStatus) {
		if(settlementStatus == SettlementStatus.NOT_OCCURRED){
			this.settlementStatus = new String("未发生");
		}
		if(settlementStatus == SettlementStatus.CREATE){
			this.settlementStatus = new String("已创建");
		}
		if(settlementStatus == SettlementStatus.WAITING){
			this.settlementStatus = new String("待结清");
		}
		if(settlementStatus == SettlementStatus.DONE){
			this.settlementStatus = new String("已结清");
		}
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
