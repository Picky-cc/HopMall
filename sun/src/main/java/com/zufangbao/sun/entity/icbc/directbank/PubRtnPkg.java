/**
 * 
 */
package com.zufangbao.sun.entity.icbc.directbank;

import com.zufangbao.sun.utils.FinanceUtils;


/**
 * 工商银行文件级返回包
 * @author wk
 *
 */
public class PubRtnPkg implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2480628137009250019L;
	/**交易代码*/
	private String transCode;
	/**集团CIS*/
	private String CIS;
	/**归属银行编号*/
	private String bankCode;
	/**证书ID*/
	private String certificateID;
	/**交易日期*/
	private String  tranDate;
	/**交易时间*/
	private String tranTime;
	/**指令包序列号*/
	private String fSeqno;
	/**交易返回码*/
	private String retCode;
	/**交易返回描述*/
	private String retMsg;
	/**
	 * 
	 */
	public PubRtnPkg() {
		super();
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
	 * @return the cIS
	 */
	public String getCIS() {
		return CIS;
	}
	/**
	 * @param cIS the cIS to set
	 */
	public void setCIS(String cIS) {
		CIS = cIS;
	}
	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}
	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	/**
	 * @return the certificateID
	 */
	public String getCertificateID() {
		return certificateID;
	}
	/**
	 * @param certificateID the certificateID to set
	 */
	public void setCertificateID(String certificateID) {
		this.certificateID = certificateID;
	}
	/**
	 * @return the tranDate
	 */
	public String getTranDate() {
		return tranDate;
	}
	/**
	 * @param tranDate the tranDate to set
	 */
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	/**
	 * @return the tranTime
	 */
	public String getTranTime() {
		return tranTime;
	}
	/**
	 * @param tranTime the tranTime to set
	 */
	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}
	/**
	 * @return the fSeqno
	 */
	public String getfSeqno() {
		return fSeqno;
	}
	/**
	 * @param fSeqno the fSeqno to set
	 */
	public void setfSeqno(String fSeqno) {
		this.fSeqno = fSeqno;
	}
	/**
	 * @return the retCode
	 */
	public String getRetCode() {
		return retCode;
	}
	/**
	 * @param retCode the retCode to set
	 */
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	/**
	 * @return the retMsg
	 */
	public String getRetMsg() {
		return retMsg;
	}
	/**
	 * @param retMsg the retMsg to set
	 */
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	@Override
	public String toString(){
		return "TransCode="+this.transCode+",CIS="+this.CIS+",BankCode="+this.bankCode+",ID="+this.certificateID
				+",TransDate="+this.tranDate+",TransTime="+this.tranTime+",fSeqno="+this.fSeqno+",RetCode="+this.retCode+",RenMsg="+this.retMsg;
	}
  public boolean is_submit_successfully() {
    return FinanceUtils.SUCCESS_RETURN_CODE.equals(getRetCode());
  }
  
	public String getResultMsg() {
		return "错误码：" + retCode + " 描述：" + retMsg + " 序列号：" + fSeqno;
	}
  
}
