package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("NTOPRRTNZ")
public class BatchPaymentProcessingResultXml {
	
	@XStreamAlias("YURREF")
	private String reqNo; //业务参考号
	
	@XStreamAlias("RTNFLG")
	private String execResult; //业务处理结果
	
	@XStreamAlias("ERRCOD")
	private String errCode; //错误代码
	
	@XStreamAlias("ERRTXT")
	private String errTxt; //错误文本
	
	@XStreamAlias("RSV50Z")
	private String reservedWord; //保留字段

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getExecResult() {
		return execResult;
	}

	public void setExecResult(String execResult) {
		this.execResult = execResult;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrTxt() {
		return errTxt;
	}

	public void setErrTxt(String errTxt) {
		this.errTxt = errTxt;
	}

	public String getReservedWord() {
		return reservedWord;
	}

	public void setReservedWord(String reservedWord) {
		this.reservedWord = reservedWord;
	}

	public BatchPaymentProcessingResultXml() {
		super();
	}

	public BatchPaymentProcessingResultXml(String reqNo, String execResult,
			String errCode, String errTxt) {
		super();
		this.reqNo = reqNo;
		this.execResult = execResult;
		this.errCode = errCode;
		this.errTxt = errTxt;
	}
	
}
