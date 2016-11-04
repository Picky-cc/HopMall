package com.zufangbao.gluon.exception;

/**
 * 
 * @author louguanyang
 *
 */
public class GlobalRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2181985767744636123L;

	private int code;
	
	private String msg;

	public GlobalRuntimeException() {
		super();
	}

	public GlobalRuntimeException(int code) {
		super();
		this.code = code;
	}

	public GlobalRuntimeException(String msg) {
		super();
		this.msg = msg;
	}

	public GlobalRuntimeException(int code,String msg) {
		super();
		this.code = code;
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

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		return getMsg();
	}
	
}
