package com.zufangbao.gluon.exception;

import com.zufangbao.gluon.spec.global.GlobalCodeSpec;

public class NoAuthorityException extends CommonException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8979081243150190478L;
	
	public NoAuthorityException(int errorCode, String errorMsg) {
		super(errorCode, errorMsg);
	}

	public NoAuthorityException(int errorCode){
		super(errorCode);
	}
	
	public NoAuthorityException(){
		super(GlobalCodeSpec.GeneralErrorCode.ERROR_NO_AUTHORITY);
	}
	
}
