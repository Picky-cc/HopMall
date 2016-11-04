/**
 * 
 */
package com.zufangbao.sun.entity.icbc.directbank;

import java.math.BigDecimal;

import com.zufangbao.sun.utils.FinanceUtils;

/**
 * @author zjm
 *
 */
public class QACCBALRdRtnPkg implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2611300915401120577L;
	/** 指令序列号 */
	private String iSeqNo;
	/** 账号 */
	private String accNo;
	/** 昨日余额 */
	private BigDecimal accBalance;
	/** 币种 */
	private String currType;
	/** 当前余额 */
	private BigDecimal balance;
	/** 可用余额 */
	private String usableBalance;
	/** 冻结额度合计 */
	private String FrzAmt;
	/** 查询时间 */
	private String queryTime;
	/** 明细交易返回码 */
	private String iRetCode;
	/** 明细交易返回描述 */
	private String iRetMsg;

	public BigDecimal getAccBalance() {
		return accBalance;
	}

	public void setAccBalance(BigDecimal accBalance) {
		this.accBalance = accBalance;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * @return the iSeqNo
	 */
	public String getiSeqNo() {
		return iSeqNo;
	}

	/**
	 * @param iSeqNo
	 *            the iSeqNo to set
	 */
	public void setiSeqNo(String iSeqNo) {
		this.iSeqNo = iSeqNo;
	}

	/**
	 * @return the accNo
	 */
	public String getAccNo() {
		return accNo;
	}

	/**
	 * @param accNo
	 *            the accNo to set
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	/**
	 * @return the currType
	 */
	public String getCurrType() {
		return currType;
	}

	/**
	 * @param currType
	 *            the currType to set
	 */
	public void setCurrType(String currType) {
		this.currType = currType;
	}

	/**
	 * @return the usableBalance
	 */
	public String getUsableBalance() {
		return usableBalance;
	}

	/**
	 * @param usableBalance
	 *            the usableBalance to set
	 */
	public void setUsableBalance(String usableBalance) {
		this.usableBalance = usableBalance;
	}

	/**
	 * @return the frzAmt
	 */
	public String getFrzAmt() {
		return FrzAmt;
	}

	/**
	 * @param frzAmt
	 *            the frzAmt to set
	 */
	public void setFrzAmt(String frzAmt) {
		FrzAmt = frzAmt;
	}

	/**
	 * @return the queryTime
	 */
	public String getQueryTime() {
		return queryTime;
	}

	/**
	 * @param queryTime
	 *            the queryTime to set
	 */
	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
	}

	/**
	 * @return the iRetCode
	 */
	public String getiRetCode() {
		return iRetCode;
	}

	/**
	 * @param iRetCode
	 *            the iRetCode to set
	 */
	public void setiRetCode(String iRetCode) {
		this.iRetCode = iRetCode;
	}

	/**
	 * @return the iRetMsg
	 */
	public String getiRetMsg() {
		return iRetMsg;
	}

	/**
	 * @param iRetMsg
	 *            the iRetMsg to set
	 */
	public void setiRetMsg(String iRetMsg) {
		this.iRetMsg = iRetMsg;
	}

	@Override
	public String toString() {
		return String.format(
				"账号(%s)的当前余额是=%s,可用余额=%s,昨日余额=%s,指令序列号=%s,查询时间=%s", this.accNo,
				this.balance, this.usableBalance, this.accBalance, this.iSeqNo,
				this.queryTime);
	}

	public boolean is_query_successfully() {
		return FinanceUtils.SUCCESS_RETURN_CODE.equals(getiRetCode());
	}
}
