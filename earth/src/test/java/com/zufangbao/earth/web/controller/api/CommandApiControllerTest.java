package com.zufangbao.earth.web.controller.api;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.zufangbao.earth.api.test.post.BaseApiTestPost;
import com.zufangbao.earth.yunxin.api.controller.CommandApiController;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {
//		"classpath:/context/applicationContext-*.xml",
//		"classpath:/local/applicationContext-*.xml",
//		"classpath:/DispatcherServlet.xml" })
//@TransactionConfiguration(defaultRollback = false)
//@Transactional
public class CommandApiControllerTest extends BaseApiTestPost{
	
	private MockHttpServletRequest request = new MockHttpServletRequest();
	
	@Autowired
	private CommandApiController commandApiController;

	
	@Before
	public void setUp() {
	}


}
