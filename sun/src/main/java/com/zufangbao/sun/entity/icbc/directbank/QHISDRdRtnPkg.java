package com.zufangbao.sun.entity.icbc.directbank;

import java.math.BigDecimal;

/**
 * 
 * @author zjm
 *
 */
public class QHISDRdRtnPkg implements java.io.Serializable {

	/**
   * 
   */
	private static final long serialVersionUID = 2251476756996700339L;

	/** 借贷标志 */
	private String drcrf;
	/** 凭证号 */
	private String vouhNo;
	/** 借方发生额 */
	private BigDecimal debitAmount;
	/** 贷方发生额 */
	private BigDecimal creditAmount;
	/** 余额 */
	private BigDecimal balance;
	/** 对方行号 */
	private String recipBkNo;
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
	/** 业务代码 */
	private String busCode;
	/** 交易日期 */
	private String date;
	/** 时间戳 */
	private String time;
	/** 业务编号 */
	private String ref;
	/** 相关业务编号 */
	private String oref;
	/** 英文备注 */
	private String enSummary;
	/** 业务种类 */
	private String busType;
	/** 凭证种类 */
	private String cvouhType;
	/** 附加信息 */
	private String addInfo;

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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * @return the recipBkNo
	 */
	public String getRecipBkNo() {
		return recipBkNo;
	}

	/**
	 * @param recipBkNo
	 *            the recipBkNo to set
	 */
	public void setRecipBkNo(String recipBkNo) {
		this.recipBkNo = recipBkNo;
	}

	/**
	 * @return the recipBkName
	 */
	public String getRecipBkName() {
		return recipBkName;
	}

	/**
	 * @param recipBkName
	 *            the recipBkName to set
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
	 * @return the busCode
	 */
	public String getBusCode() {
		return busCode;
	}

	/**
	 * @param busCode
	 *            the busCode to set
	 */
	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the ref
	 */
	public String getRef() {
		return ref;
	}

	/**
	 * @param ref
	 *            the ref to set
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}

	/**
	 * @return the oref
	 */
	public String getOref() {
		return oref;
	}

	/**
	 * @param oref
	 *            the oref to set
	 */
	public void setOref(String oref) {
		this.oref = oref;
	}

	/**
	 * @return the enSummary
	 */
	public String getEnSummary() {
		return enSummary;
	}

	/**
	 * @param enSummary
	 *            the enSummary to set
	 */
	public void setEnSummary(String enSummary) {
		this.enSummary = enSummary;
	}

	/**
	 * @return the busType
	 */
	public String getBusType() {
		return busType;
	}

	/**
	 * @param busType
	 *            the busType to set
	 */
	public void setBusType(String busType) {
		this.busType = busType;
	}

	/**
	 * @return the cvouhType
	 */
	public String getCvouhType() {
		return cvouhType;
	}

	/**
	 * @param cvouhType
	 *            the cvouhType to set
	 */
	public void setCvouhType(String cvouhType) {
		this.cvouhType = cvouhType;
	}

	/**
	 * @return the addInfo
	 */
	public String getAddInfo() {
		return addInfo;
	}

	/**
	 * @param addInfo
	 *            the addInfo to set
	 */
	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
