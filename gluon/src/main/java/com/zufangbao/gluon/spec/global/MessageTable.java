/**
 * 
 */
package com.zufangbao.gluon.spec.global;

import java.util.Hashtable;

import com.zufangbao.gluon.spec.global.GlobalCodeSpec.GeneralErrorCode;
import com.zufangbao.gluon.spec.global.GlobalMsgSpec.GeneralErrorMsg;

/**
 * @author wukai
 *
 */
public class MessageTable {

	private static Hashtable<Integer, String> table = init();
	
	private static Hashtable<Integer, String> init(){
		
		Hashtable<Integer, String> table = new Hashtable<Integer, String>();
		
		table.put(GlobalCodeSpec.CODE_SUC, GlobalMsgSpec.MSG_SUC);
		
		table.put(GlobalCodeSpec.GeneralErrorCode.ERROR_NETWORK_FAILURE, GlobalMsgSpec.GeneralErrorMsg.MSG_NET_WORK_FAILURE);
		
		table.put(GeneralErrorCode.ERROR_NO_OBJECT_IN_DB, GeneralErrorMsg.MSG_NO_OBJECT_IN_DB);
		
		return table;
	}
	
	public static String getMessage(int key){
		
		return table.getOrDefault(key, GlobalMsgSpec.MSG_FAILURE);
	}
	public static Hashtable<Integer, String> getTable(){
		
		return table;
	}
}
