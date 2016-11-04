package com.zufangbao.earth.api.exception;

public class TransactionDetailApiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8881359155933579533L;
	private int code;
	private String message;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TransactionDetailApiException(int code){
		this.code = code;
	}
	
	public TransactionDetailApiException(int code,String message){
		this.code = code;
		this.message = message;
	}
	
}
