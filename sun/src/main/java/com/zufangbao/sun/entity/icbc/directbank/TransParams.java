/**
 * 
 */
package com.zufangbao.sun.entity.icbc.directbank;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 交易参数设定
 * @author wk
 *
 */
public class TransParams {

	/**交易编码*/
	private String transCode;
	/**交易日期*/
	private String tranDate;
	/**交易时间*/
	private String tranTime;
	/**交易发送时间*/
	private String sendTime;
	/**包序列号*/
	private String packageID;
	/**子包序列号*/
	private String iSeqNo;
	public TransParams(String transCode) {
		super();
		this.transCode=transCode;
		Date date = new Date();
		SimpleDateFormat tranDateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat tranTimeFormat = new SimpleDateFormat("HHmmssSSS");
		this.tranDate = tranDateFormat.format(date);
		this.tranTime = tranTimeFormat.format(date);
		this.sendTime = tranDate + tranTime;
		this.packageID = this.sendTime;
		this.iSeqNo = this.packageID + this.tranTime;
	}
	
	/**
	 * 
	 */
	public TransParams() {
		super();
		Date date = new Date();
		SimpleDateFormat tranDateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat tranTimeFormat = new SimpleDateFormat("HHmmssSSS");
		this.tranDate = tranDateFormat.format(date);
		this.tranTime = tranTimeFormat.format(date);
		this.sendTime = tranDate + tranTime;
		this.packageID = this.sendTime;
		this.iSeqNo = new StringBuffer(this.packageID).append(this.tranTime).toString();
	}

	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	public String getTranTime() {
		return tranTime;
	}
	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}
	public String getPackageID() {
		return packageID;
	}
	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	/**
	 * @return the transCode
	 */
	public String getTransCode() {
		return transCode;
	}
	/**
	 * @param transCode the transCode to set
	 */
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	/**
	 * @return the iSeqNo
	 */
	public String getiSeqNo() {
		return iSeqNo;
	}
	
}
