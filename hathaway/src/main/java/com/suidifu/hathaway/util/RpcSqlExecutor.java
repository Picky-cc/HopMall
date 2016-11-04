package com.suidifu.hathaway.util;



/**
 * 
 * @author wukai
 *
 */
public class RpcSqlExecutor {
	
	//TODO FIX
	public static void executeBerkshire(String sqlScript){
		
		String url = "jdbc:mysql://127.0.0.1:3306/berkshire_dev";
		
		String name = "berkshire_dev";
		
		String password = "berkshire_dev";
		
		SqlExecutor.execute(sqlScript, url, name, password);
	}
	
}
