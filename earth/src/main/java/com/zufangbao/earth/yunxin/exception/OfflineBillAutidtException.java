package com.zufangbao.earth.yunxin.exception;

public class OfflineBillAutidtException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8104738857785878220L;
	
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
	
	public OfflineBillAutidtException(){
		
	}
	
	public OfflineBillAutidtException(String message){
		this.message = message;
	}

}
