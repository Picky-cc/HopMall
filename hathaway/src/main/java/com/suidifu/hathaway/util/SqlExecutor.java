package com.suidifu.hathaway.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * 
 * @author wukai
 *
 */
public class SqlExecutor {
	
	public static void execute(String sqlScript,String url,String name,String password){
		
		Resource sqlScriptResource = new ClassPathResource(sqlScript);
		
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		
		populator.setSqlScriptEncoding("UTF-8");
		populator.addScript(sqlScriptResource);
		
		DatabasePopulatorUtils.execute(populator, new DriverManagerDataSource(url, name, password));
	}
	
	
}
