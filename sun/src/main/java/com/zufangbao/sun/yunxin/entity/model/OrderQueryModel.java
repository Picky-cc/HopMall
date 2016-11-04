/**
 * 
 */
package com.zufangbao.sun.yunxin.entity.model;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.yunxin.entity.ExecutingSettlingStatus;
import com.zufangbao.sun.yunxin.entity.OrderType;
import com.zufangbao.sun.yunxin.entity.OverDueStatus;

public class OrderQueryModel {

	private static final int DEFAULT = -1;
	private String financialContractIds;
	private int executingSettlingStatusInt = DEFAULT;
	private int overDueStatus = DEFAULT;
	private int orderType = OrderType.NORMAL.ordinal();
	private String orderNo;
	private String singleLoanContractNo;
	private String settlementStartDateString;
	private String settlementEndDateString;
	private String assetRecycleStartDateString;
	private String assetRecycleEndDateString;

	private String sortField;

	private boolean isAsc;
	
	private static final Map<String, String> sortFieldMapper = new HashMap<String, String>(){
		private static final long serialVersionUID = -7353834934187273307L;
		{
			put("assetRecycleDate", "assetSet.assetRecycleDate");
			put("dueDate", "dueDate");
			put("assetInitialValue", "assetSet.assetInitialValue");
//			put("penaltyAmount", "assetSet.penaltyAmount");
//			put("numbersOfOverdueDays", "");
			put("modifyTime", "modifyTime");
			put("totalRent", "totalRent");
		}
	};

	public String getFinancialContractIds() {
		return financialContractIds;
	}

	public void setFinancialContractIds(String financialContractIds) {
		this.financialContractIds = financialContractIds;
	}

	public long getExecutingSettlingStatusInt() {
		return executingSettlingStatusInt;
	}

	public void setExecutingSettlingStatusInt(int executingSettlingStatusInt) {
		this.executingSettlingStatusInt = executingSettlingStatusInt;
	}

	public ExecutingSettlingStatus getExecutingSettlingStatusEnum() {
		return ExecutingSettlingStatus.fromValue(executingSettlingStatusInt);
	}

	public int getOverDueStatus() {
		return overDueStatus;
	}

	public void setOverDueStatus(int overDueStatus) {
		this.overDueStatus = overDueStatus;
	}

	public OverDueStatus getOverDueStatusEnum() {
		return OverDueStatus.fromValue(overDueStatus);
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

	public String getSettlementStartDateString() {
		return settlementStartDateString;
	}

	public Date getSettlementStartDate() {
		return settlementStartDateString == null ? null : DateUtils
				.asDay(settlementStartDateString);
	}

	public void setSettlementStartDateString(String settlementStartDateString) {
		this.settlementStartDateString = settlementStartDateString;
	}

	public String getSettlementEndDateString() {
		return settlementEndDateString;
	}

	public Date getSettlementEndDate() {
		return settlementEndDateString == null ? null : DateUtils
				.asDay(settlementEndDateString);
	}

	public void setSettlementEndDateString(String settlementEndDateString) {
		this.settlementEndDateString = settlementEndDateString;
	}

	public String getAssetRecycleStartDateString() {
		return assetRecycleStartDateString;
	}

	public Date getAssetRecycleStartDate() {
		return assetRecycleStartDateString == null ? null : DateUtils
				.asDay(assetRecycleStartDateString);
	}

	public void setAssetRecycleStartDateString(
			String assetRecycleStartDateString) {
		this.assetRecycleStartDateString = assetRecycleStartDateString;
	}

	public String getAssetRecycleEndDateString() {
		return assetRecycleEndDateString;
	}

	public Date getAssetRecycleEndDate() {
		return assetRecycleEndDateString == null ? null : DateUtils
				.asDay(assetRecycleEndDateString);
	}

	public void setAssetRecycleEndDateString(String assetRecycleEndDateString) {
		this.assetRecycleEndDateString = assetRecycleEndDateString;
	}

	public List<Long> getFinancialContractIdList(){
		List<Long> financialContractList = JsonUtils.parseArray(financialContractIds,Long.class);
		if(financialContractList==null){
			return Collections.emptyList();
		}
		return financialContractList;
	}
	public boolean isExecutingSettlingStatusIntInput() {
		return executingSettlingStatusInt != DEFAULT;
	}

	public boolean isOverDueStatusInput() {
		return overDueStatus != DEFAULT;
	}

	public int getOrderType() {
		return orderType;
	}

	public OrderType getOrderTypeEnum() {
		return OrderType.fromValue(orderType);
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public boolean getIsAsc() {
		return isAsc;
	}

	public void setIsAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}
	
	public String getOrderBySentence() {
		 String template = " order by %s %s, id DESC";
		 String dbField = sortFieldMapper.getOrDefault(this.sortField, "modifyTime");
		 String sortType = this.isAsc ? "ASC" : "DESC";
		 return String.format(template, dbField, sortType);
	}
	
}
