package com.zufangbao.earth.yunxin.api.exception;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.earth.yunxin.api.util.ApiMessageUtil;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 7823104901088570592L;
	
	private int code;
	
	private String msg;

	public ApiException() {
		super();
	}

	public ApiException(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	
	public ApiException(int code) {
		super();
		this.code = code;
	}

	public ApiException(String msg) {
		super();
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	@Override
	public String getMessage() {
		return getMsg();
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public void printStackTrace() {
		String fullMsg = StringUtils.isEmpty(this.msg) ? ApiMessageUtil.getMessage(this.code) : this.msg;
		System.err.println("#接口异常 Code: " + this.code + " Msg: "+ fullMsg);
		super.printStackTrace();
	}
	
}
