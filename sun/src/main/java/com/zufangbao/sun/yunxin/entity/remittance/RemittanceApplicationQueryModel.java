package com.zufangbao.sun.yunxin.entity.remittance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.utils.EnumUtil;


public class RemittanceApplicationQueryModel {
	
	/**
	 * 信托合同ids
	 */
	private String financialContractIds;

	/**
	 * 订单编号
	 */
	private String orderNo;
	
	/**
	 * 贷款合同编号
	 */
	private String loanContractNo;
	
	/**
	 * 受理日期起始
	 */
	private String receiveStartDate;
	
	/**
	 * 受理日期终止
	 */
	private String receiveEndDate;
	
	/**
	 * 订单状态
	 */
	private String orderStatus;
	
	/**
	 * 按照受理时间排序
	 */
	private boolean isAsc = false;
	
	public String getFinancialContractIds() {
		return financialContractIds;
	}
	
	public void setFinancialContractIds(String financialContractUuids) {
		this.financialContractIds = financialContractUuids;
	}
	
	public List<Long> getFinancialContractIdList(){
		List<Long> financialContractIdList = JsonUtils.parseArray(this.financialContractIds, Long.class);
		if(financialContractIdList==null){
			return new ArrayList<Long>();
		}
		return financialContractIdList;
	}
	
	public Date getStartDateValue(){
		return receiveStartDate == null?null:DateUtils.parseDate(this.receiveStartDate, "yyyy-MM-dd HH:mm:ss");
	}
	
	public Date getEndDateValue(){
		return receiveEndDate == null?null:DateUtils.parseDate(this.receiveEndDate, "yyyy-MM-dd HH:mm:ss");
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getLoanContractNo() {
		return loanContractNo;
	}

	public void setLoanContractNo(String loanContractNo) {
		this.loanContractNo = loanContractNo;
	}

	public String getReceiveStartDate() {
		return receiveStartDate;
	}

	public void setReceiveStartDate(String receiveStartDate) {
		this.receiveStartDate = receiveStartDate;
	}

	public String getReceiveEndDate() {
		return receiveEndDate;
	}

	public void setReceiveEndDate(String receiveEndDate) {
		this.receiveEndDate = receiveEndDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public List<ExecutionStatus> getExecutionStatusEnumList() {
		List<Integer> ordinals = JsonUtils.parseArray(this.getOrderStatus(), Integer.class);
		if (ordinals == null) {
			ordinals = new ArrayList<Integer>();
		}
		List<ExecutionStatus> executionStatusEnumList = new ArrayList<ExecutionStatus>();
		for(Integer ordinal : ordinals) {
			if (ordinal == null) continue;
			ExecutionStatus executionStatus = EnumUtil.fromOrdinal(ExecutionStatus.class, ordinal);
			if (executionStatus == null) continue;
			executionStatusEnumList.add(executionStatus);
		}
		return executionStatusEnumList;
	}

	public boolean getIsAsc() {
		return isAsc;
	}

	public void setIsAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}
	
	public String getOrderBySentence() {
		 String template = " ORDER BY %s %s, id ASC";
		 String dbField = "createTime";
		 String sortType = this.isAsc ? "ASC" : "DESC";
		 return String.format(template, dbField, sortType);
	}
}