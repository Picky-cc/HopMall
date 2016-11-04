package com.zufangbao.sun.yunxin.entity.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.entity.icbc.business.BankSide;

public class CashFlowConditionModel {

	private String appId;
	private String accountNo;
	private String accountName;
	private int auditStatusValue=-1;
	private String startTime;
	private String endTime;
	private String queryKeyWord;
	private String drcrf;
	private String amountString;
	private String financialAccountName;
	private String accountSide;
	
	public CashFlowConditionModel(){
		
	}
	
	public CashFlowConditionModel(String appId, String accountNo,
			String accountName, int auditStatusValue, String startTime,
			String endTime, String queryKeyWord, String drcrf, String amount) {
		super();
		this.appId = appId;
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.auditStatusValue = auditStatusValue;
		this.startTime = startTime;
		this.endTime = endTime;
		this.queryKeyWord = queryKeyWord;
		this.drcrf = drcrf;
		this.amountString = amount;
	}

	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getQueryKeyWord() {
		return queryKeyWord;
	}
	public void setQueryKeyWord(String queryKeyWord) {
		this.queryKeyWord = queryKeyWord;
	}
	
	public int getAuditStatusValue() {
		return auditStatusValue;
	}

	public void setAuditStatusValue(int auditStatusValue) {
		this.auditStatusValue = auditStatusValue;
	}
	
	public Date getStartDate(){
		return startTime==null?null:DateUtils.asDay(startTime);
	}
	
	public Date getEndDate(){
		return endTime==null?null:DateUtils.asDay(endTime);
	}
	
	public AuditStatus getAuditStatusEnum(){
		return AuditStatus.fromOrdinal(auditStatusValue);
	}
	
	public BigDecimal getAmount(){
		
		if (StringUtils.isEmpty(amountString)){
			return null;
		}
		try{
			return new BigDecimal(amountString);
		} catch (Exception e){
			return BigDecimal.ZERO;
		}
	}
	

	public String getDrcrf() {
		BankSide bankSide = BankSide.fromName(this.getAccountSide());
		if(null == bankSide){
			return StringUtils.EMPTY;
		}
		return bankSide.getValue();
	}

	public void setDrcrf(String drcrf) {
		this.drcrf = drcrf;
	}
	
	public String getAmountString() {
		return amountString;
	}

	public void setAmountString(String amount) {
		this.amountString = amount;
	}
	public String getAccountSide() {
		return accountSide;
	}
	public void setAccountSide(String accountSide) {
		this.accountSide = accountSide;
	}

	@Override
	public String toString() {
		return "CashFlowConditionModel [appId=" + appId + ", accountNo="
				+ accountNo + ", accountName=" + accountName
				+ ", auditStatusValue=" + auditStatusValue + ", startTime="
				+ startTime + ", endTime=" + endTime + ", queryKeyWord="
				+ queryKeyWord + ", drcrf=" + drcrf + ", amountString="
				+ amountString +",financialAccountName="+financialAccountName+ "]";
	}

	public String getFinancialAccountName() {
		return financialAccountName;
	}

	public void setFinancialAccountName(String financialAccountName) {
		this.financialAccountName = financialAccountName;
	}
}
