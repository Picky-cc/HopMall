package com.zufangbao.sun.entity.icbc.business;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.Constant;

public class FlowRecord {

	/** 借贷标志 */
	private String drcrf;
	/** 凭证号 */
	private String vouhNo;
	/** 借方发生额 */
	private BigDecimal debitAmount;
	/** 贷方发生额 */
	private BigDecimal creditAmount;
	/** 对方行名 */
	private String recipBkName;
	/** 对方账号 */
	private String recipAccNo;
	/** 对方户名 */
	private String recipName;
	/** 摘要 */
	private String summary;
	/** 用途 */
	private String useCN;
	/** 附言 */
	private String postScript;
	/** 时间戳 */
	private Date time;
	/** 余额 */
	private BigDecimal balance;
	/** 流水号 */
	private String serialNo;
	/** 原交易唯一编号，交易是传给银联的编号 */
	private String reqNo;
	/**
	 * 
	 */
	public FlowRecord() {
		super();
	}

	/**
	 * @param drcrf
	 * @param vouhNo
	 * @param debitAmount
	 * @param creditAmount
	 * @param recipBkNo
	 * @param recipAccNo
	 * @param recipName
	 * @param summary
	 * @param useCN
	 * @param postScript
	 * @param time
	 */
	public FlowRecord(String drcrf, String vouhNo, BigDecimal debitAmount,
			BigDecimal creditAmount, String recipBkName, String recipAccNo,
			String recipName, String summary, String useCN, String postScript,
			Date time, String serialNo) {
		super();
		this.drcrf = drcrf;
		this.vouhNo = vouhNo;
		this.debitAmount = debitAmount;
		this.creditAmount = creditAmount;
		this.recipBkName = recipBkName;
		this.recipAccNo = recipAccNo;
		this.recipName = recipName;
		this.summary = summary;
		this.useCN = useCN;
		this.postScript = postScript;
		this.time = time;
		this.serialNo = serialNo;
	}

	/**
	 * @param drcrf
	 * @param vouhNo
	 * @param debitAmount
	 * @param creditAmount
	 * @param recipBkNo
	 * @param recipAccNo
	 * @param recipName
	 * @param summary
	 * @param useCN
	 * @param postScript
	 * @param time
	 * @param balance
	 */
	public FlowRecord(String drcrf, String vouhNo, BigDecimal debitAmount,
			BigDecimal creditAmount, String recipBkName, String recipAccNo,
			String recipName, String summary, String useCN, String postScript,
			Date time, BigDecimal balance, String serialNo, String reqNo) {
		super();
		this.drcrf = drcrf;
		this.vouhNo = vouhNo;
		this.debitAmount = debitAmount;
		this.creditAmount = creditAmount;
		this.recipBkName = recipBkName;
		this.recipAccNo = recipAccNo;
		this.recipName = recipName;
		this.summary = summary;
		this.useCN = useCN;
		this.postScript = postScript;
		this.time = time;
		this.balance = balance;
		this.serialNo = serialNo;
		this.reqNo = reqNo;
	}

	/**
	 * @return the drcrf
	 */
	public String getDrcrf() {
		return drcrf;
	}

	/**
	 * @param drcrf
	 *            the drcrf to set
	 */
	public void setDrcrf(String drcrf) {
		this.drcrf = drcrf;
	}

	/**
	 * @return the vouhNo
	 */
	public String getVouhNo() {
		return vouhNo;
	}

	/**
	 * @param vouhNo
	 *            the vouhNo to set
	 */
	public void setVouhNo(String vouhNo) {
		this.vouhNo = vouhNo;
	}

	/**
	 * @return the debitAmount
	 */
	public BigDecimal getDebitAmount() {
		return debitAmount;
	}

	/**
	 * @param debitAmount
	 *            the debitAmount to set
	 */
	public void setDebitAmount(BigDecimal debitAmount) {
		this.debitAmount = debitAmount;
	}

	/**
	 * @return the creditAmount
	 */
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	/**
	 * @param creditAmount
	 *            the creditAmount to set
	 */
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	/**
	 * @return the recipBkNo
	 */
	public String getRecipBkName() {
		return recipBkName;
	}

	/**
	 * @param recipBkNo
	 *            the recipBkNo to set
	 */
	public void setRecipBkName(String recipBkName) {
		this.recipBkName = recipBkName;
	}

	/**
	 * @return the recipAccNo
	 */
	public String getRecipAccNo() {
		return recipAccNo;
	}

	/**
	 * @param recipAccNo
	 *            the recipAccNo to set
	 */
	public void setRecipAccNo(String recipAccNo) {
		this.recipAccNo = recipAccNo;
	}

	/**
	 * @return the recipName
	 */
	public String getRecipName() {
		return recipName;
	}

	/**
	 * @param recipName
	 *            the recipName to set
	 */
	public void setRecipName(String recipName) {
		this.recipName = recipName;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the useCN
	 */
	public String getUseCN() {
		return useCN;
	}

	/**
	 * @param useCN
	 *            the useCN to set
	 */
	public void setUseCN(String useCN) {
		this.useCN = useCN;
	}

	/**
	 * @return the postScript
	 */
	public String getPostScript() {
		return postScript;
	}

	/**
	 * @param postScript
	 *            the postScript to set
	 */
	public void setPostScript(String postScript) {
		this.postScript = postScript;
	}

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	
	public BigDecimal getTransAmount(){
		if(Constant.DEBIT.equals(drcrf)){
			return debitAmount;
		} else if(Constant.CREDIT.equals(drcrf)){
			return creditAmount;
		}
		return new BigDecimal("0.00");
	}

}
