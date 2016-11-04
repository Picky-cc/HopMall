package com.suidifu.hathaway.mq.exceptions;

import java.util.UUID;

/**
 * 
 * @author wukai
 *
 */
public class ErrorRpcException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4700812790983968788L;

	public ErrorRpcException() {
		super();
	}
	public ErrorRpcException(String message) {
		super(String.format("Exception [%s],exception name is %s",UUID.randomUUID().toString(),message));
	}
	public ErrorRpcException(Exception e) {
		this(e.getClass().getName());
		e.printStackTrace();
	}
}
