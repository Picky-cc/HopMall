package com.zufangbao.official.account.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class IOUSummaryOfOneDay {

	 private  Date  loanDate;
	 
	 private List<IOU> IOUList;
	 
	 private BigDecimal  todayLoanAmount;

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public List<IOU> getIOUList() {
		return IOUList;
	}

	public void setIOUList(List<IOU> iOUList) {
		IOUList = iOUList;
	}

	public BigDecimal getTodayLoanAmount() {
		return todayLoanAmount;
	}

	public void setTodayLoanAmount(BigDecimal todayLoanAmount) {
		this.todayLoanAmount = todayLoanAmount;
	}
	
}
