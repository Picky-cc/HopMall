package com.zufangbao.sun;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
"classpath:/local/applicationContext-*.xml" })
public class InitDataBase {

	private static final Log logger = LogFactory.getLog(InitDataBase.class);
	
	/*@Test
	public void init(){
		
		logger.info("Setting up database and contructor table structure");
		
		Resource schemaScript = new ClassPathResource("schema/schema.sql");
		
		Resource structureScript = new ClassPathResource("schema/maintain/galaxy_online_structure.sql");

		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		
		populator.addScript(schemaScript);
		
		populator.addScript(structureScript);
		
		populator.setSqlScriptEncoding("UTF-8");
		
		String url = "jdbc:mysql://127.0.0.1:3306/";
		
		String name = "root";
		
		String password = "zufangbao";
		
		DatabasePopulatorUtils.execute(populator, new DriverManagerDataSource(url, name, password));
	}*/
	
	@Test
	@Sql("classpath:schema/maintain/yunxin_structure.sql")
	public void init(){
		
		logger.info("init database structure");
		
	}

	
	
}
