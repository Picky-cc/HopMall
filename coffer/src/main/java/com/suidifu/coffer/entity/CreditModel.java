package com.suidifu.coffer.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

public class CreditModel {

	private String sourceAccountName;

	private String sourceAccountNo;

	private String sourceAccountAppendix;

	private String sourceBankInfo;

	private String destinationAccountName;

	private String destinationAccountNo;

	private String destinationAccountAppendix;

	private String destinationBankInfo;

	private String transactionAmount;

	private String currencyCode;

	private String postscript;

	private String transactionVoucherNo;

	private String transactionAppendix;

	private Date requestDate;

	public CreditModel() {
		super();
	}

	public CreditModel(String sourceAccountName, String sourceAccountNo,
			String sourceAccountAppendix, String sourceBankInfo,
			String destinationAccountName, String destinationAccountNo,
			String destinationAccountAppendix, String destinationBankInfo,
			String transactionAmount, String currencyCode, String postscript,
			String transactionVoucherNo, String transactionAppendix,
			Date requestDate) {
		super();
		this.sourceAccountName = sourceAccountName;
		this.sourceAccountNo = sourceAccountNo;
		this.sourceAccountAppendix = sourceAccountAppendix;
		this.sourceBankInfo = sourceBankInfo;
		this.destinationAccountName = destinationAccountName;
		this.destinationAccountNo = destinationAccountNo;
		this.destinationAccountAppendix = destinationAccountAppendix;
		this.destinationBankInfo = destinationBankInfo;
		this.transactionAmount = transactionAmount;
		this.currencyCode = currencyCode;
		this.postscript = postscript;
		this.transactionVoucherNo = transactionVoucherNo;
		this.transactionAppendix = transactionAppendix;
		this.requestDate = requestDate;
	}

	public String getSourceAccountName() {
		return sourceAccountName;
	}

	public void setSourceAccountName(String sourceAccountName) {
		this.sourceAccountName = sourceAccountName;
	}

	public String getSourceAccountNo() {
		return sourceAccountNo;
	}

	public void setSourceAccountNo(String sourceAccountNo) {
		this.sourceAccountNo = sourceAccountNo;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getSourceAccountAppendix() {
		if (StringUtils.isEmpty(this.sourceAccountAppendix)) {
			return new HashMap<String, String>();
		}
		try {
			return JSON.parseObject(this.sourceAccountAppendix, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, String>();
		}
	}

	public void setSourceAccountAppendix(String sourceAccountAppendix) {
		this.sourceAccountAppendix = sourceAccountAppendix;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getSourceBankInfo() {
		if (StringUtils.isEmpty(this.sourceBankInfo)) {
			return new HashMap<String, String>();
		}
		try {
			return JSON.parseObject(this.sourceBankInfo, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, String>();
		}
	}

	public void setSourceBankInfo(String sourceBankInfo) {
		this.sourceBankInfo = sourceBankInfo;
	}

	public String getDestinationAccountName() {
		return destinationAccountName;
	}

	public void setDestinationAccountName(String destinationAccountName) {
		this.destinationAccountName = destinationAccountName;
	}

	public String getDestinationAccountNo() {
		return destinationAccountNo;
	}

	public void setDestinationAccountNo(String destinationAccountNo) {
		this.destinationAccountNo = destinationAccountNo;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getDestinationBankInfo() {
		if (StringUtils.isEmpty(this.destinationBankInfo)) {
			return new HashMap<String, String>();
		}
		try {
			return JSON.parseObject(this.destinationBankInfo, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, String>();
		}
	}

	public void setDestinationBankInfo(String destinationBankInfo) {
		this.destinationBankInfo = destinationBankInfo;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getDestinationAccountAppendix() {
		if (StringUtils.isEmpty(this.destinationAccountAppendix)) {
			return new HashMap<String, String>();
		}
		try {
			return JSON.parseObject(this.destinationAccountAppendix, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, String>();
		}
	}

	public void setDestinationAccountAppendix(String destinationAccountAppendix) {
		this.destinationAccountAppendix = destinationAccountAppendix;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getPostscript() {
		return postscript;
	}

	public void setPostscript(String postscript) {
		this.postscript = postscript;
	}

	public String getTransactionVoucherNo() {
		return transactionVoucherNo;
	}

	public void setTransactionVoucherNo(String transactionVoucherNo) {
		this.transactionVoucherNo = transactionVoucherNo;
	}

	public String getTransactionAppendix() {
		return transactionAppendix;
	}

	public void setTransactionAppendix(String transactionAppendix) {
		this.transactionAppendix = transactionAppendix;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

}
