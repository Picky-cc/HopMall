package com.zufangbao.sun.yunxin.entity.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.yunxin.entity.GuaranteeStatus;

public class GuaranteeOrderModel {
	private int guaranteeStatus = -1;
	private String financialContractIds;
	private String orderNo;
	private String singleLoanContractNo;
	private String appId;
	private String startDate;
	private String endDate;
	private String dueStartDate;
	private String dueEndDate;

	public GuaranteeOrderModel() {
		super();
	}

	public GuaranteeStatus getGuaranteeStatusEnum() {
		return GuaranteeStatus.fromValue(guaranteeStatus);
	}
	
	public int getGuaranteeStatus() {
		return guaranteeStatus;
	}

	public void setGuaranteeStatus(int guaranteeStatus) {
		this.guaranteeStatus = guaranteeStatus;
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

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getStartDate() {
		return startDate;
	}
	public Date getStartDate_date(){
		return startDate==null?null:DateUtils.asDay(startDate);
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}
	public Date getEndDate_date(){
		return endDate==null?null:DateUtils.asDay(endDate);
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDueStartDate() {
		return dueStartDate;
	}
	
	public Date getDueStartDate_date(){
		return dueStartDate==null?null:DateUtils.asDay(dueStartDate);
	}

	public void setDueStartDate(String dueStartDate) {
		this.dueStartDate = dueStartDate;
	}

	public String getDueEndDate() {
		return dueEndDate;
	}
	
	public Date getDueEndDate_date(){
		return dueEndDate==null?null:DateUtils.asDay(dueEndDate);
	}

	public void setDueEndDate(String dueEndDate) {
		this.dueEndDate = dueEndDate;
	}
	
	public GuaranteeStatus fromValue(){
		return GuaranteeStatus.fromValue(this.guaranteeStatus);
	}

	public String getFinancialContractIds() {
		return financialContractIds;
	}

	public void setFinancialContractIds(String financialContractIds) {
		this.financialContractIds = financialContractIds;
	}
	public List<Long> getFinancialContractIdList(){
		List<Long> financialContractIdList = JsonUtils.parseArray(financialContractIds,Long.class);
		if(financialContractIdList==null){
			return Collections.emptyList();
		}
		return financialContractIdList;
	}
	
	
	

}
