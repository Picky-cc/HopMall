/**
 * 
 */
package com.zufangbao.earth.web.controller;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.service.PrincipalService;

/**
 * @author wukai
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
		"classpath:/DispatcherServlet.xml"})
@TransactionConfiguration
@Transactional
@Component
public class BaseContollerTestUtils {
	
	private MockHttpServletRequest request;
	
	@Autowired
	private PrincipalService principalService;
	
	@Autowired
	private DataSource dataSource;
	
	Long DEFAULT_COMPANY_ID = 14L;
	
	String DEFAULT_APP_ID = "yuanlai";
	
	String DEFAULT_SCRIPT_NAME = "test/test4BusinessDocument.sql";
	
	private void prepareTestDB(String scriptName)
	{
		Resource script = new ClassPathResource(scriptName);
		
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(script);
		populator.setSqlScriptEncoding("UTF-8");
		DatabasePopulatorUtils.execute(populator, dataSource);
		
	}
	
	private void initRequestContext(){
		
		request = new MockHttpServletRequest();
	}
	
	public void assertView(String viewName,ModelAndView modelAndView){
		
		Assert.assertEquals(viewName, modelAndView.getViewName());
	}

	public void assertResult(String json){
		Assert.assertTrue(JsonUtils.parse(json, Result.class).isValid());
	}
	public <T> T getModelData(String dataKey,ModelAndView modelAndView){
		return (T) modelAndView.getModel().get(dataKey);
	}
	public <T> T getAppData(String dataKey,ModelAndView modelAndView){
		return (T) modelAndView.getModel().get(dataKey);
	}
	public <T> T getJsonData(String json,String dataKey){
		return (T) JsonUtils.parse(json, Result.class).get(dataKey);
	}
	
	public MockHttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(MockHttpServletRequest request) {
		this.request = request;
	}

}
