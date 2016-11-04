/**
 * 
 */
package com.zufangbao.gluon.exception;

/**
 * @author wukai
 *
 */
public class CommonException extends Exception {

	private static final long serialVersionUID = 6111145029104552277L;
	
	private int errorCode;
	
	private String errorMsg;

	public CommonException(int errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public CommonException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}


	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
