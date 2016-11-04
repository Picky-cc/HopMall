/**
 * 
 */
package com.zufangbao.gluon.opensdk;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;

import com.zufangbao.gluon.exception.CommonException;
import com.zufangbao.gluon.spec.global.GlobalCodeSpec;

/**
 * @author wukai
 *
 */
public abstract class BaseExceptionUtils {
	
	public static final int DEAFULT_CODE = GlobalCodeSpec.CODE_FAILURE;

	public static final String EXCEPTION_DUPLICATE_CLASS_NAME = "MySQLIntegrityConstraintViolationException";
	
	public static final String EXCEPTION_OBJECT_NOT_FOUND = "ObjectNotFoundException";
	
	public static int getErrorCodeFromException(Exception e){
		
		if(isReadTimeout(e)){
			
			return GlobalCodeSpec.GeneralErrorCode.ERROR_READ_TIME_OUT;
		}
		if(isRemoteCallError(e)){
			
			return GlobalCodeSpec.GeneralErrorCode.ERROR_NETWORK_FAILURE;
		}
		
		if(isDuplicate(e)){
			
			return GlobalCodeSpec.GeneralErrorCode.ERROR_NO_OBJECT_IN_DB;
		}
		if(e instanceof CommonException){
			return ((CommonException)(e)).getErrorCode();
		}
		
		return DEAFULT_CODE;
	}
	
	public static boolean isDuplicate(Exception e){
		
		if(e instanceof ConstraintViolationException){
			
			ConstraintViolationException constraintViolationException = (ConstraintViolationException)e;
			
			String exceptionName =  constraintViolationException.getSQLException().getClass().getSimpleName();
			
			if((EXCEPTION_DUPLICATE_CLASS_NAME.equals(exceptionName))){
				
				return true;
			}
			
			return false;
		}
		return false;
	}
	public static boolean isRemoteCallError(Exception e){
		
		if(e instanceof org.jsoup.HttpStatusException){
			
			return true;
		}
		return false;
	}
	public static boolean isReadTimeout(Exception e){
		
		return e instanceof java.net.SocketTimeoutException;
	}
	public static boolean isObjectNotFound(Exception e){
		
		return e instanceof ObjectNotFoundException;
	}
	public static boolean isDefaultCode(int code){
		
		return code == DEAFULT_CODE;
	}
}
