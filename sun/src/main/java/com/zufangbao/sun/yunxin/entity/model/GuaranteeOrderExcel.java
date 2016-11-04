package com.zufangbao.sun.yunxin.entity.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;

/**
 * 结算单导出Excel格式
 * 
 * @author louguanyang
 *
 */
public class GuaranteeOrderExcel {
	@ExcelVoAttribute(name = "序号", column = "A")
	private int index;
	@ExcelVoAttribute(name = "补差单号", column = "B")
	private String orderNo;
	@ExcelVoAttribute(name = "还款期号", column = "C")
	private String singleLoanContractNo;
	@ExcelVoAttribute(name = "应还日期", column = "D")
	private Date assetRecycleDate;
	@ExcelVoAttribute(name = "补差截止日", column = "E")
	private Date dueDate;
	@ExcelVoAttribute(name = "商户编号", column = "F")
	private String appId;
	@ExcelVoAttribute(name = "本息金额", column = "G")
	private BigDecimal monthFee;
	@ExcelVoAttribute(name = "差异天数", column = "H")
	private int overDueDays;
	@ExcelVoAttribute(name = "发生时间", column = "I")
	private Date modifyTime;
	@ExcelVoAttribute(name = "补差金额", column = "J")
	private BigDecimal totalRent;
	@ExcelVoAttribute(name = "补差状态", column = "K")
	private String guaranteeStatus;

	public GuaranteeOrderExcel() {
		super();
	}

	public GuaranteeOrderExcel(Order order) {
		super();
		this.orderNo = order.getOrderNo();
		this.singleLoanContractNo = order.getSingleLoanContractNo();
		this.assetRecycleDate = order.getAssetSet().getAssetRecycleDate();
		this.dueDate = order.getDueDate();
		this.appId = order.getAssetSet().getContract().getApp().getAppId();
		this.monthFee = order.getAssetSet().getAssetInitialValue();
		this.overDueDays = order.getAssetSet().getOverDueDays();
		this.modifyTime = order.getModifyTime();
		this.totalRent = order.getTotalRent();
		this.guaranteeStatus = order.getAssetSet().getGuaranteeStatus().getChineseMessage();
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSingleLoanContractNo() {
		return singleLoanContractNo;
	}

	public void setSingleLoanContractNo(String singleLoanContractNo) {
		this.singleLoanContractNo = singleLoanContractNo;
	}

	public Date getAssetRecycleDate() {
		return assetRecycleDate;
	}

	public void setAssetRecycleDate(Date assetRecycleDate) {
		this.assetRecycleDate = assetRecycleDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public BigDecimal getMonthFee() {
		return monthFee;
	}

	public void setMonthFee(BigDecimal monthFee) {
		this.monthFee = monthFee;
	}

	public int getOverDueDays() {
		return overDueDays;
	}

	public void setOverDueDays(int overDueDays) {
		this.overDueDays = overDueDays;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public BigDecimal getTotalRent() {
		return totalRent;
	}

	public void setTotalRent(BigDecimal totalRent) {
		this.totalRent = totalRent;
	}

	public String getGuaranteeStatus() {
		return guaranteeStatus;
	}

	public void setGuaranteeStatus(String guaranteeStatus) {
		this.guaranteeStatus = guaranteeStatus;
	}

	public static List<GuaranteeOrderExcel> convertFrom(List<Order> orderList) {
		List<GuaranteeOrderExcel> guaranteeOrderExcelList = new ArrayList<>();
		for (int index = 0; index < orderList.size(); index++) {
			GuaranteeOrderExcel guaranteeOrderExcel = new GuaranteeOrderExcel(orderList.get(index));
			guaranteeOrderExcel.setIndex(index + 1);
			guaranteeOrderExcelList.add(guaranteeOrderExcel);
		}
		return guaranteeOrderExcelList;
	}

}
