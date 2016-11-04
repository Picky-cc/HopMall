package com.zufangbao.earth.web.controller.system;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.entity.security.Principal;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
		"classpath:/DispatcherServlet.xml"
})
public class PrincipalControllerTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Resource
	private PrincipalController principalController;
	@Resource
	private GenericDaoSupport genericDaoSupport;
	@Test
	public void postToUpdatePasswordTest(){
		Principal principal = this.genericDaoSupport.load(Principal.class, 1L);
		ModelAndView result = this.principalController.postToUpdatePasswordPage(principal);
		Assert.assertEquals("principal/principal-update-password", result.getViewName());
	}
	@Test
	@Sql("classpath:test/updatePasswordDataTest.sql")
	public void updatePasswordTest(){
		Principal principal = this.genericDaoSupport.load(Principal.class, 1L);
		String message = this.principalController.updatePassword(principal, "zufangbao@905", "zufangbao@905");
		Assert.assertEquals("修改成功", message);
	}
	
	@Test
	@Sql("classpath:test/updatePasswordDataTest.sql")
	public void updatePasswordWrongOldPassTest(){
		Principal principal = this.genericDaoSupport.load(Principal.class, 1L);
		String message = this.principalController.updatePassword(principal, "1234567", "zufangbao@905");
		Assert.assertEquals("原密码输入有误", message);
	}
	
	@Test
	@Sql("classpath:test/getUserRoleListData.sql")
	public void getUserRoleListTest(){
		Page page = new Page();
		page.setBeginIndex(1);
		page.setEveryPage(12);
		page.setCurrentPage(1);
		List<Principal> principal = principalController.getRoleList(page);
		Assert.assertEquals(principal.size(), 12);
	}
	
	@Test
	@Sql("classpath:test/deleteUser.sql")
	public void deleteUserTest(){
		boolean flag = true;
		Page page = new Page();
		page.setBeginIndex(1);
		page.setEveryPage(12);
		page.setCurrentPage(1);
		List<Principal> principalList = principalController.getRoleList(page);
		for(Principal principal : principalList)
		 principalController.deleteUser(principal.getId(), page);
		
		List<Principal> principalList2 = principalController.getRoleList(page);
		for(Principal principal : principalList2)
		 if(principal.getThruDate()==null)flag=false;
		Assert.assertTrue(flag);
	}
}