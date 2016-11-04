/**
 * 
 */
package runtime;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * @author lute
 *
 */
public class ApplicationSchemaSetting {
	
	private static final Log logger = LogFactory.getLog(ApplicationSchemaSetting.class);
	
	@Test
	public void init() throws Exception {
		
		logger.info("Setting up database.");
		
		Resource script = new ClassPathResource("schema/schema.sql");

		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(script);
		populator.setSqlScriptEncoding("UTF-8");
		
		String url = "jdbc:mysql://127.0.0.1:3306/";
		// read username from inputstream
		logger.info("Your database connection user name: ");
		
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		String name = new BufferedReader(inputStreamReader).readLine();
		
		// read password from inputstream
		logger.info("Your database connection password: ");
		
		inputStreamReader = new InputStreamReader(System.in);
		String password = new BufferedReader(inputStreamReader).readLine();
		
		DatabasePopulatorUtils.execute(populator, new DriverManagerDataSource(url, name, password));
	}

}