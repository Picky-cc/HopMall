package com.zufangbao.earth.yunxin.unionpay.model.basic;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("INFO")
public class GZUnionPayRtnInfo {
	
	@XStreamAlias("TRX_CODE")
	private String trxCode; //交易代码
	
	@XStreamAlias("VERSION")
	private String version; //版本
	
	@XStreamAlias("DATA_TYPE")
	private String dataType; //数据格式
	
	@XStreamAlias("REQ_SN")
	private String reqSn; //交易流水号
	
	@XStreamAlias("RET_CODE")
	private String retCode; //返回代码
	
	@XStreamAlias("ERR_MSG")
	private String errMsg; //错误信息
	
	@XStreamAlias("SIGNED_MSG")
	private String signMsg; //签名信息

	public String getTrxCode() {
		return trxCode;
	}

	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getReqSn() {
		return reqSn;
	}

	public void setReqSn(String reqSn) {
		this.reqSn = reqSn;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}
	
	public boolean isSuc(){
		return "0000".equals(retCode);
	}
	
}
