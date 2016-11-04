package com.suidifu.coffer.entity;

import com.suidifu.coffer.GlobalSpec;

public class ResultBaseModel {

	private String commCode;

	private String errMsg;

	public ResultBaseModel() {
		super();
		commCode = GlobalSpec.DEFAULT_SUCCESS_CODE;
	}

	public ResultBaseModel(String errMsg) {
		super();
		commCode = GlobalSpec.DEFAULT_FAIL_CODE;
		this.errMsg = errMsg;
	}

	public ResultBaseModel(String commCode, String errMsg) {
		super();
		this.commCode = commCode;
		this.errMsg = errMsg;
	}

	public String getCommCode() {
		return commCode;
	}

	public void setCommCode(String commCode) {
		this.commCode = commCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	public boolean commFailed() {
		return commCode == GlobalSpec.DEFAULT_FAIL_CODE;
	}

}
