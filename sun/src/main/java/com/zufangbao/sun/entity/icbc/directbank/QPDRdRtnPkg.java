/**
 * 
 */
package com.zufangbao.sun.entity.icbc.directbank;

import java.math.BigDecimal;

/**
 * @author wk
 *
 */
public class QPDRdRtnPkg implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2611300915401120577L;
	/**借贷标志*/
	private String drcrf;
	/**凭证号*/
	private String vouhNo;
	/**发生额*/
	private BigDecimal amount;
	/**对方行号*/
	private String recipBkNo;
	/**对方账号*/
	private String recipAccNo;
	/**对方户名*/
	private String recipName;
	/**摘要*/
	private String summary;
	/**用途*/
	private String useCN;
	/**附言*/
	private String postScript;
	/**业务编码*/
	private String ref;
	/**业务代码*/
	private String busCode;
	/**相关业务编号*/
	private String oref;
	/**英文备注*/
	private String enSummary;
	/**业务种类*/
	private String busType;
	/**凭证种类*/
	private String cvouhType;
	/**附加信息*/
	private String addInfo;
	/**时间戳*/
	private String timestamp;
	/**
	 * @return the drcrf
	 */
	public String getDrcrf() {
		return drcrf;
	}
	/**
	 * @param drcrf the drcrf to set
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
	 * @param vouhNo the vouhNo to set
	 */
	public void setVouhNo(String vouhNo) {
		this.vouhNo = vouhNo;
	}
	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * @return the recipBkNo
	 */
	public String getRecipBkNo() {
		return recipBkNo;
	}
	/**
	 * @param recipBkNo the recipBkNo to set
	 */
	public void setRecipBkNo(String recipBkNo) {
		this.recipBkNo = recipBkNo;
	}
	/**
	 * @return the recipAccNo
	 */
	public String getRecipAccNo() {
		return recipAccNo;
	}
	/**
	 * @param recipAccNo the recipAccNo to set
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
	 * @param recipName the recipName to set
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
	 * @param summary the summary to set
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
	 * @param useCN the useCN to set
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
	 * @param postScript the postScript to set
	 */
	public void setPostScript(String postScript) {
		this.postScript = postScript;
	}
	/**
	 * @return the ref
	 */
	public String getRef() {
		return ref;
	}
	/**
	 * @param ref the ref to set
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}
	/**
	 * @return the busCode
	 */
	public String getBusCode() {
		return busCode;
	}
	/**
	 * @param busCode the busCode to set
	 */
	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}
	/**
	 * @return the oref
	 */
	public String getOref() {
		return oref;
	}
	/**
	 * @param oref the oref to set
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
	 * @param enSummary the enSummary to set
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
	 * @param busType the busType to set
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
	 * @param cvouhType the cvouhType to set
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
	 * @param addInfo the addInfo to set
	 */
	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}
	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString(){
		String flag = "1".equals(this.drcrf) ? "借":"贷";
		return String.format("时间：%s,对方(账号=%s,户名=%s,行号=%s)%s的金额是=%s,附言=%s",this.timestamp,this.recipAccNo,this.recipName,this.recipBkNo,flag,this.amount,this.postScript);
	}
	
}
