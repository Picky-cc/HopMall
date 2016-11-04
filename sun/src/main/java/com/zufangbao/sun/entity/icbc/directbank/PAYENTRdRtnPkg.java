/**
 * 
 */
package com.zufangbao.sun.entity.icbc.directbank;

/**
 * @author wk
 *
 */
public class PAYENTRdRtnPkg implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2611300915401120577L;
	/**指令顺序号*/
	private String iSeqno;
	/**平台交易序号*/
	private String orderNo;
	/**付方账号*/
	private String payAccNo;
	/**付方账号中文名称*/
	private String payAccNameCN;
	/**对方账号*/
	private String recAccNo;
	/**对方账号名称*/
	private String recAccNameCN;
	/**对方账号开户行*/
	private String recBankNo;
	/**对方银行名称*/
	private String recBankName;
	/**币种*/
	private String currType;
	/**金额*/
	private String payAmt;
	/**用途代码*/
	private String useCode;
	/**用途中文描述*/
	private String useCN;
	/**附言*/
	private String postscript;
	/**业务编码*/
	private String ref;
	/**摘要*/
	private String summary;
	/**ERP系统的流水号*/
	private String ERPSqn;
	/**业务代码*/
	private String busCode;
	/**ERP支票号*/
	private String ERPCheckNo;
	/**原始凭证种类*/
	private String crvouhType;
	/**原始凭证名称*/
	private String crvouhName;
	/**原始凭证号*/
	private String crvouhNo;
	/**指令状态*/
	private String result;
	/**明细交易返回码*/
	private String iRetCode;
	/**明细交易返回描述*/
	private String iRetMsg;
	/**
	 * @param iSeqno
	 * @param orderNo
	 * @param payAccNo
	 * @param payAccNameCN
	 * @param payBranch
	 * @param currType
	 * @param payAmt
	 * @param useCode
	 * @param useCN
	 * @param postscript
	 * @param ref
	 * @param summary
	 * @param eRPSqn
	 * @param busCode
	 * @param eRPCheckNo
	 * @param crvouhType
	 * @param crvouhName
	 * @param crvouhNo
	 * @param result
	 * @param iRetCode
	 * @param iRetMsg
	 */
	public PAYENTRdRtnPkg(String iSeqno, String orderNo, String payAccNo,
			String payAccNameCN, String payBranch, String currType,
			String payAmt, String useCode, String useCN, String postscript,
			String ref, String summary, String eRPSqn, String busCode,
			String eRPCheckNo, String crvouhType, String crvouhName,
			String crvouhNo, String result, String iRetCode, String iRetMsg) {
		super();
		this.iSeqno = iSeqno;
		this.orderNo = orderNo;
		this.payAccNo = payAccNo;
		this.payAccNameCN = payAccNameCN;
		this.recBankNo = payBranch;
		this.currType = currType;
		this.payAmt = payAmt;
		this.useCode = useCode;
		this.useCN = useCN;
		this.postscript = postscript;
		this.ref = ref;
		this.summary = summary;
		ERPSqn = eRPSqn;
		this.busCode = busCode;
		ERPCheckNo = eRPCheckNo;
		this.crvouhType = crvouhType;
		this.crvouhName = crvouhName;
		this.crvouhNo = crvouhNo;
		this.result = result;
		this.iRetCode = iRetCode;
		this.iRetMsg = iRetMsg;
	}
	public PAYENTRdRtnPkg() {
	}
	/**
	 * @return the iSeqno
	 */
	public String getiSeqno() {
		return iSeqno;
	}
	/**
	 * @param iSeqno the iSeqno to set
	 */
	public void setiSeqno(String iSeqno) {
		this.iSeqno = iSeqno;
	}
	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}
	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * @return the payAccNo
	 */
	public String getPayAccNo() {
		return payAccNo;
	}
	/**
	 * @param payAccNo the payAccNo to set
	 */
	public void setPayAccNo(String payAccNo) {
		this.payAccNo = payAccNo;
	}
	/**
	 * @return the payAccNameCN
	 */
	public String getPayAccNameCN() {
		return payAccNameCN;
	}
	/**
	 * @param payAccNameCN the payAccNameCN to set
	 */
	public void setPayAccNameCN(String payAccNameCN) {
		this.payAccNameCN = payAccNameCN;
	}
	/**
	 * @return the payBranch
	 */
	public String getPayBranch() {
		return recBankNo;
	}
	/**
	 * @param payBranch the payBranch to set
	 */
	public void setPayBranch(String payBranch) {
		this.recBankNo = payBranch;
	}
	/**
	 * @return the currType
	 */
	public String getCurrType() {
		return currType;
	}
	/**
	 * @param currType the currType to set
	 */
	public void setCurrType(String currType) {
		this.currType = currType;
	}
	/**
	 * @return the payAmt
	 */
	public String getPayAmt() {
		return payAmt;
	}
	/**
	 * @param payAmt the payAmt to set
	 */
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	/**
	 * @return the useCode
	 */
	public String getUseCode() {
		return useCode;
	}
	/**
	 * @param useCode the useCode to set
	 */
	public void setUseCode(String useCode) {
		this.useCode = useCode;
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
	 * @return the postscript
	 */
	public String getPostscript() {
		return postscript;
	}
	/**
	 * @param postscript the postscript to set
	 */
	public void setPostscript(String postscript) {
		this.postscript = postscript;
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
	 * @return the eRPSqn
	 */
	public String getERPSqn() {
		return ERPSqn;
	}
	/**
	 * @param eRPSqn the eRPSqn to set
	 */
	public void setERPSqn(String eRPSqn) {
		ERPSqn = eRPSqn;
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
	 * @return the eRPCheckNo
	 */
	public String getERPCheckNo() {
		return ERPCheckNo;
	}
	/**
	 * @param eRPCheckNo the eRPCheckNo to set
	 */
	public void setERPCheckNo(String eRPCheckNo) {
		ERPCheckNo = eRPCheckNo;
	}
	/**
	 * @return the crvouhType
	 */
	public String getCrvouhType() {
		return crvouhType;
	}
	/**
	 * @param crvouhType the crvouhType to set
	 */
	public void setCrvouhType(String crvouhType) {
		this.crvouhType = crvouhType;
	}
	/**
	 * @return the crvouhName
	 */
	public String getCrvouhName() {
		return crvouhName;
	}
	/**
	 * @param crvouhName the crvouhName to set
	 */
	public void setCrvouhName(String crvouhName) {
		this.crvouhName = crvouhName;
	}
	/**
	 * @return the crvouhNo
	 */
	public String getCrvouhNo() {
		return crvouhNo;
	}
	/**
	 * @param crvouhNo the crvouhNo to set
	 */
	public void setCrvouhNo(String crvouhNo) {
		this.crvouhNo = crvouhNo;
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return the iRetCode
	 */
	public String getiRetCode() {
		return iRetCode;
	}
	/**
	 * @param iRetCode the iRetCode to set
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
	 * @param iRetMsg the iRetMsg to set
	 */
	public void setiRetMsg(String iRetMsg) {
		this.iRetMsg = iRetMsg;
	}
	/**
	 * @return the recAccNo
	 */
	public String getRecAccNo() {
		return recAccNo;
	}
	/**
	 * @param recAccNo the recAccNo to set
	 */
	public void setRecAccNo(String recAccNo) {
		this.recAccNo = recAccNo;
	}
	/**
	 * @return the recAccNameCN
	 */
	public String getRecAccNameCN() {
		return recAccNameCN;
	}
	/**
	 * @param recAccNameCN the recAccNameCN to set
	 */
	public void setRecAccNameCN(String recAccNameCN) {
		this.recAccNameCN = recAccNameCN;
	}
	
	/**
	 * @return the recBankNo
	 */
	public String getRecBankNo() {
		return recBankNo;
	}
	/**
	 * @param recBankNo the recBankNo to set
	 */
	public void setRecBankNo(String recBankNo) {
		this.recBankNo = recBankNo;
	}
	/**
	 * @return the recBankName
	 */
	public String getRecBankName() {
		return recBankName;
	}
	/**
	 * @param recBankName the recBankName to set
	 */
	public void setRecBankName(String recBankName) {
		this.recBankName = recBankName;
	}
	@Override
	public String toString(){
		return String.format("本方(账号=%s,名称=%s支付给对方(账号=%s,名称=%s,行号=%s,银行名称=%s)金额(%s),附言(%s),平台交易号是=%s)",
				this.payAccNo,this.payAccNameCN,this.recAccNo,this.recAccNameCN,this.recBankNo,this.recBankName,this.payAmt,this.postscript,this.orderNo
				);
	}
}
