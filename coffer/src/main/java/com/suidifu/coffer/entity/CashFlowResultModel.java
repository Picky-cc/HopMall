package com.suidifu.coffer.entity;

import java.util.List;

public class CashFlowResultModel extends ResultBaseModel {
	
	private String accountNo;
	
	private boolean hasNextPage = true;
	
	private int pageNo;
	
	private int pageRecCount;

	private List<CashFlowResult> cashFlowResult;

	public CashFlowResultModel() {
		super();
	}

	public CashFlowResultModel(String errMsg) {
		super(errMsg);
	}

	public List<CashFlowResult> getCashFlowResult() {
		return cashFlowResult;
	}

	public void setCashFlowResult(List<CashFlowResult> cashFlowResult) {
		this.cashFlowResult = cashFlowResult;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageRecCount() {
		return pageRecCount;
	}

	public void setPageRecCount(int pageRecCount) {
		this.pageRecCount = pageRecCount;
	}


}
