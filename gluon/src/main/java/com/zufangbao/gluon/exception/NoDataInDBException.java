package com.zufangbao.gluon.exception;

import com.zufangbao.gluon.spec.global.GlobalCodeSpec;

public class NoDataInDBException extends CommonException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2910646669338912564L;
	
	public NoDataInDBException(int errorCode, String errorMsg) {
		super(errorCode, errorMsg);
	}

	public NoDataInDBException(int errorCode){
		super(errorCode);
	}
	
	public NoDataInDBException(){
		super(GlobalCodeSpec.GeneralErrorCode.ERROR_NO_RESOURCE);
	}
	
}
