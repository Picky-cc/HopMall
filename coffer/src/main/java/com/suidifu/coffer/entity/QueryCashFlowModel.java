package com.suidifu.coffer.entity;

import java.util.Date;

public class QueryCashFlowModel {

	private String queryAccountNo;

	private String currencyCode;

	private String beginDate;

	private String endDate;

	private String pageNo;

	private String pageSize;

	private Date requestDate;

	public QueryCashFlowModel() {
		super();
	}

	public QueryCashFlowModel(String queryAccountNo, String beginDate,
			String endDate, Date requestDate) {
		super();
		this.queryAccountNo = queryAccountNo;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.requestDate = requestDate;
	}

	public String getQueryAccountNo() {
		return queryAccountNo;
	}

	public void setQueryAccountNo(String queryAccountNo) {
		this.queryAccountNo = queryAccountNo;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

}
