package com.zufangbao.sun.yunxin.entity.sms;

/**
 * 短信接口返回结果
 * 
 * @author louguanyang
 *
 */
public class SmsResult {
	private boolean isok;
	private String message;

	public SmsResult() {
		super();
	}

	public boolean isIsok() {
		return isok;
	}

	public void setIsok(boolean isok) {
		this.isok = isok;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return isIsok();
	}

}
