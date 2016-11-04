/**
 *
 */
package runtime;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author downpour
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/context/applicationContext-*.xml", "classpath:/local/applicationContext-*.xml" })
public class EarthDatabaseSetting {
	
	private static final Log logger = LogFactory.getLog(EarthDatabaseSetting.class);
	
	@Autowired
	private DataSource dataSource;
	
	@Test
	public void updateSchema() {
		logger.info("Updating schema using hibernate .. ");
	}
 	
	@Test
	public void initData() {

		logger.info("Setting up init data.");
		
		Resource script = new ClassPathResource("schema/init_data.sql");

		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(script);
		populator.setSqlScriptEncoding("UTF-8");

		DatabasePopulatorUtils.execute(populator, dataSource);
		
	}
		
}
