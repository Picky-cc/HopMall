package com.zufangbao.sun.entity.icbc.directbank;

/**
 * 
 * @author zjm
 *
 */
public class QPAYENTRdRtnPkg implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2888482905711572921L;

	private String iSeqno;
	private String qryiSeqno;
	private String reimburseNo;
	private String reimburseNum;
	private String startDate;
	private String startTime;
	private String payType;
	private String payAccNo;
	private String payAccNameCN;
	private String payAccNameEN;
	private String recAccNo;
	private String recAccNameCN;
	private String recAccNameEN;
	private String sysIOFlg;
	private String isSameCity;
	private String recICBCCode;
	private String recCityName;
	private String recBankNo;
	private String recBankName;
	private String currType;
	private String payAmt;
	private String useCode;
	private String useCN;
	private String enSummary;
	private String postScript;
	private String summary;
	private String ref;
	private String oref;
	private String eRPSqn;
	private String busCode;
	private String eRPcheckno;
	private String crvouhType;
	private String crvouhName;
	private String crvouhNo;
	private String iRetCode;
	private String iRetMsg;
	private String result;
	private String instrRetCode;
	private String instrRetMsg;
	private String bankRetTime;

	public QPAYENTRdRtnPkg(String iSeqno, String qryiSeqno, String reimburseNo,
			String reimburseNum, String startDate, String startTime,
			String payType, String payAccNo, String payAccNameCN,
			String payAccNameEN, String recAccNo, String recAccNameCN,
			String recAccNameEN, String sysIOFlg, String isSameCity,
			String recICBCCode, String recCityName, String recBankNo,
			String recBankName, String currType, String payAmt, String useCode,
			String useCN, String enSummary, String postScript, String summary,
			String ref, String oref, String eRPSqn, String busCode,
			String eRPcheckno, String crvouhType, String crvouhName,
			String crvouhNo, String iRetCode, String iRetMsg, String result,
			String instrRetCode, String instrRetMsg, String bankRetTime) {
		super();
		this.iSeqno = iSeqno;
		this.qryiSeqno = qryiSeqno;
		this.reimburseNo = reimburseNo;
		this.reimburseNum = reimburseNum;
		this.startDate = startDate;
		this.startTime = startTime;
		this.payType = payType;
		this.payAccNo = payAccNo;
		this.payAccNameCN = payAccNameCN;
		this.payAccNameEN = payAccNameEN;
		this.recAccNo = recAccNo;
		this.recAccNameCN = recAccNameCN;
		this.recAccNameEN = recAccNameEN;
		this.sysIOFlg = sysIOFlg;
		this.isSameCity = isSameCity;
		this.recICBCCode = recICBCCode;
		this.recCityName = recCityName;
		this.recBankNo = recBankNo;
		this.recBankName = recBankName;
		this.currType = currType;
		this.payAmt = payAmt;
		this.useCode = useCode;
		this.useCN = useCN;
		this.enSummary = enSummary;
		this.postScript = postScript;
		this.summary = summary;
		this.ref = ref;
		this.oref = oref;
		this.eRPSqn = eRPSqn;
		this.busCode = busCode;
		this.eRPcheckno = eRPcheckno;
		this.crvouhType = crvouhType;
		this.crvouhName = crvouhName;
		this.crvouhNo = crvouhNo;
		this.iRetCode = iRetCode;
		this.iRetMsg = iRetMsg;
		this.result = result;
		this.instrRetCode = instrRetCode;
		this.instrRetMsg = instrRetMsg;
		this.bankRetTime = bankRetTime;
	}

	/**
	 * 
	 */
	public QPAYENTRdRtnPkg() {
		super();
	}

	public String getiSeqno() {
		return iSeqno;
	}

	public void setiSeqno(String iSeqno) {
		this.iSeqno = iSeqno;
	}

	public String getQryiSeqno() {
		return qryiSeqno;
	}

	public void setQryiSeqno(String qryiSeqno) {
		this.qryiSeqno = qryiSeqno;
	}

	public String getReimburseNo() {
		return reimburseNo;
	}

	public void setReimburseNo(String reimburseNo) {
		this.reimburseNo = reimburseNo;
	}

	public String getReimburseNum() {
		return reimburseNum;
	}

	public void setReimburseNum(String reimburseNum) {
		this.reimburseNum = reimburseNum;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayAccNo() {
		return payAccNo;
	}

	public void setPayAccNo(String payAccNo) {
		this.payAccNo = payAccNo;
	}

	public String getPayAccNameCN() {
		return payAccNameCN;
	}

	public void setPayAccNameCN(String payAccNameCN) {
		this.payAccNameCN = payAccNameCN;
	}

	public String getPayAccNameEN() {
		return payAccNameEN;
	}

	public void setPayAccNameEN(String payAccNameEN) {
		this.payAccNameEN = payAccNameEN;
	}

	public String getRecAccNo() {
		return recAccNo;
	}

	public void setRecAccNo(String recAccNo) {
		this.recAccNo = recAccNo;
	}

	public String getRecAccNameCN() {
		return recAccNameCN;
	}

	public void setRecAccNameCN(String recAccNameCN) {
		this.recAccNameCN = recAccNameCN;
	}

	public String getRecAccNameEN() {
		return recAccNameEN;
	}

	public void setRecAccNameEN(String recAccNameEN) {
		this.recAccNameEN = recAccNameEN;
	}

	public String getSysIOFlg() {
		return sysIOFlg;
	}

	public void setSysIOFlg(String sysIOFlg) {
		this.sysIOFlg = sysIOFlg;
	}

	public String getIsSameCity() {
		return isSameCity;
	}

	public void setIsSameCity(String isSameCity) {
		this.isSameCity = isSameCity;
	}

	public String getRecICBCCode() {
		return recICBCCode;
	}

	public void setRecICBCCode(String recICBCCode) {
		this.recICBCCode = recICBCCode;
	}

	public String getRecCityName() {
		return recCityName;
	}

	public void setRecCityName(String recCityName) {
		this.recCityName = recCityName;
	}

	public String getRecBankNo() {
		return recBankNo;
	}

	public void setRecBankNo(String recBankNo) {
		this.recBankNo = recBankNo;
	}

	public String getRecBankName() {
		return recBankName;
	}

	public void setRecBankName(String recBankName) {
		this.recBankName = recBankName;
	}

	public String getCurrType() {
		return currType;
	}

	public void setCurrType(String currType) {
		this.currType = currType;
	}

	public String getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}

	public String getUseCode() {
		return useCode;
	}

	public void setUseCode(String useCode) {
		this.useCode = useCode;
	}

	public String getUseCN() {
		return useCN;
	}

	public void setUseCN(String useCN) {
		this.useCN = useCN;
	}

	public String getEnSummary() {
		return enSummary;
	}

	public void setEnSummary(String enSummary) {
		this.enSummary = enSummary;
	}

	public String getPostScript() {
		return postScript;
	}

	public void setPostScript(String postScript) {
		this.postScript = postScript;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getOref() {
		return oref;
	}

	public void setOref(String oref) {
		this.oref = oref;
	}

	public String geteRPSqn() {
		return eRPSqn;
	}

	public void seteRPSqn(String eRPSqn) {
		this.eRPSqn = eRPSqn;
	}

	public String getBusCode() {
		return busCode;
	}

	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}

	public String geteRPcheckno() {
		return eRPcheckno;
	}

	public void seteRPcheckno(String eRPcheckno) {
		this.eRPcheckno = eRPcheckno;
	}

	public String getCrvouhType() {
		return crvouhType;
	}

	public void setCrvouhType(String crvouhType) {
		this.crvouhType = crvouhType;
	}

	public String getCrvouhName() {
		return crvouhName;
	}

	public void setCrvouhName(String crvouhName) {
		this.crvouhName = crvouhName;
	}

	public String getCrvouhNo() {
		return crvouhNo;
	}

	public void setCrvouhNo(String crvouhNo) {
		this.crvouhNo = crvouhNo;
	}

	public String getiRetCode() {
		return iRetCode;
	}

	public void setiRetCode(String iRetCode) {
		this.iRetCode = iRetCode;
	}

	public String getiRetMsg() {
		return iRetMsg;
	}

	public void setiRetMsg(String iRetMsg) {
		this.iRetMsg = iRetMsg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getInstrRetCode() {
		return instrRetCode;
	}

	public void setInstrRetCode(String instrRetCode) {
		this.instrRetCode = instrRetCode;
	}

	public String getInstrRetMsg() {
		return instrRetMsg;
	}

	public void setInstrRetMsg(String instrRetMsg) {
		this.instrRetMsg = instrRetMsg;
	}

	public String getBankRetTime() {
		return bankRetTime;
	}

	public void setBankRetTime(String bankRetTime) {
		this.bankRetTime = bankRetTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
