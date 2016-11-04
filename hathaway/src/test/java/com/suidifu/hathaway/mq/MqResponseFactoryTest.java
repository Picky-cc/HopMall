package com.suidifu.hathaway.mq;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" 
})
public class MqResponseFactoryTest {

	@Autowired
	private MqResponseFactory mqResponseFactory;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCreateSucMqResponse() {
		
		String result = mqResponseFactory.createSucMqResponse("referenecUuid", new Object(), true);
		
		assertNotNull(result);
	}

	@Test
	public void testCreateErrorMqResponse() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateUnknownResponse() {
		fail("Not yet implemented");
	}

}
