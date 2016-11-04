/**
 * 
 */
package com.zufangbao.sun.yunxin.entity.model;

import java.util.Date;

import com.demo2do.core.utils.DateUtils;




public class DirectbankCashFlowQueryModel{
	private static final int DEFAULT = -1;
	private String startDateString;
	private String endDateString;
	private String accountSide;
	private Long accountId;
	private String recipAccNo;
	private String recipName;
	private String summary;
	public String getStartDateString() {
		return startDateString;
	}
	public void setStartDate(Date startDate) {
		if(startDate!=null){
			startDateString = DateUtils.format(startDate);
		}
	}
	public Date getStartDate() {
		return startDateString==null?null:DateUtils.asDay(startDateString);
	}
	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}
	public String formateStartDate(){
		if(getStartDate()!=null){
			return DateUtils.format(getStartDate(),"yyyyMMdd");
		}
		return "";
	}
	public String getEndDateString() {
		return endDateString;
	}
	public void setEndDate(Date endDate) {
		if(endDate!=null){
			endDateString = DateUtils.format(endDate);
		}
	}
	public Date getEndDate() {
		return endDateString==null?null:DateUtils.asDay(endDateString);
	}
	public String formateEndDate(){
		if(getEndDate()!=null){
			return DateUtils.format(getEndDate(),"yyyyMMdd");
		}
		return "";
	}
	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}
	public String getAccountSide() {
		return accountSide;
	}
	public void setAccountSide(String accountSide) {
		this.accountSide = accountSide;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getRecipAccNo() {
		return recipAccNo;
	}
	public void setRecipAccNo(String recipAccNo) {
		this.recipAccNo = recipAccNo;
	}
	public String getRecipName() {
		return recipName;
	}
	public void setRecipName(String recipName) {
		this.recipName = recipName;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public boolean isDateValid(){
		return getStartDate()!=null && getEndDate()!=null;
	}
	
	
	
}
