package com.zufangbao.official.account.entity;

import java.math.BigDecimal;
import java.util.List;

public class IOUSummary {

	private  String      projectCode;
	
	private  BigDecimal  totalAmount;
	
	private  int         IOUTotalNumber;
	
	private  int         allPeriodss;
	
	private  List<IOUSummaryOfOneDay> IOUSummaryOfOneDayList;

	
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getIOUTotalNumber() {
		return IOUTotalNumber;
	}

	public void setIOUTotalNumber(int iOUTotalNumber) {
		IOUTotalNumber = iOUTotalNumber;
	}

	public int getAllPeriodss() {
		return allPeriodss;
	}

	public void setAllPeriodss(int allPeriodss) {
		this.allPeriodss = allPeriodss;
	}

	public List<IOUSummaryOfOneDay> getIOUSummaryOfOneDayList() {
		return IOUSummaryOfOneDayList;
	}

	public void setIOUSummaryOfOneDayList(List<IOUSummaryOfOneDay> iOUSummaryOfOneDayList) {
		IOUSummaryOfOneDayList = iOUSummaryOfOneDayList;
	}
	
	
}
