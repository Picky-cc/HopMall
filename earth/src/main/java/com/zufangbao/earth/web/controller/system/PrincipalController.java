package com.zufangbao.earth.web.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.utils.StringUtils;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.handler.PrincipalHandler;
import com.zufangbao.earth.model.PrincipalInfoModel;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.service.AppService;
import com.zufangbao.sun.service.CompanyService;

@Controller
@MenuSetting("menu-system")
public class PrincipalController extends BaseController{
	
	@Autowired
	private PrincipalService principalService;
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private PrincipalHandler principalHandler;
	
	@MenuSetting("submenu-update-password")
	@RequestMapping("/post-update-password")
	public ModelAndView postToUpdatePasswordPage(@Secure Principal principal) {
		ModelAndView result = new ModelAndView("principal/principal-update-password");
		return result;
	}
	
	@RequestMapping(value="/update-password",method=RequestMethod.POST)
	public @ResponseBody String updatePassword(@Secure Principal principal,String oldPassword,String newPassword) {
		return this.principalService.updatePassword(principal,oldPassword,newPassword);
	}
	
	@MenuSetting("submenu-role-principal")
	@RequestMapping(value="create-user-role",method=RequestMethod.GET)
	public ModelAndView postToCreateUserRole(){
		ModelAndView result = new ModelAndView("principal/create-user-role");
		List<Company> companyList = companyService.loadAll(Company.class);
		result.addObject("companyList", companyList);
		return result;
	}
	
	@MenuSetting("submenu-role-list")
	@RequestMapping(value="edit-user-role/{principalId}",method=RequestMethod.GET)
	public ModelAndView editUserRole(
			@PathVariable(value = "principalId") Long principalId
			){
		try {
			ModelAndView result = new ModelAndView("principal/edit-user-role");
			List<Company> companyList = companyService.loadAll(Company.class);
			result.addObject("companyList", companyList);
			
			Principal principal = principalService.load(Principal.class, principalId);
			result.addObject("principal", principal);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	@RequestMapping(value="create-user-role",method=RequestMethod.POST)
	public @ResponseBody String createUserRole(
			@ModelAttribute PrincipalInfoModel principalInfoModel,
			@Secure Principal creator
			){
		Result result = new Result();
		
		try {
			result = isValidPrincipalInfo(principalInfoModel, result, false);
			if(!result.isValid()) {
				return JsonUtils.toJsonString(result);
			}
			String pwd = principalHandler.createPrincipal(principalInfoModel, creator.getId());
			return JsonUtils.toJsonString(result.success().message("创建用户成功，初始密码为：" + pwd));
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtils.toJsonString(result.fail().message("创建用户失败，请联系管理员！"));
		}
	}
	
	@RequestMapping(value="edit-user-role",method=RequestMethod.POST)
	public @ResponseBody String editUserRole(
			@ModelAttribute PrincipalInfoModel principalInfoModel,
			@Secure Principal creator
			){
		Result result = new Result();
		
		try {
			result = isValidPrincipalInfo(principalInfoModel, result, true);
			if(!result.isValid()) {
				return JsonUtils.toJsonString(result);
			}
			principalHandler.updatePrincipal(principalInfoModel, creator.getId());
			return JsonUtils.toJsonString(result.success().message("编辑用户成功！"));
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtils.toJsonString(result.fail().message("编辑用户失败，请联系管理员！"));
		}
	}
	
	private Result isValidPrincipalInfo(PrincipalInfoModel principalInfoModel, Result result, boolean isUpdate) {
		
		if(!isUpdate) {
			if(!principalInfoModel.isValidUsername()) {
				return result.message("请输入格式有效的用户名！");
			}

			String username = principalInfoModel.getUsername();
			Principal principal = principalService.getPrincipal(username);
			if(principal != null){
				return result.message("用户名已存在！");
			}
		}
		
		String realname = principalInfoModel.getRealname();
		if(StringUtils.isEmpty(realname)) {
			return result.message("请输入真实名字！");
		}
		
		String role = principalInfoModel.getRole();
		if(StringUtils.isEmpty(role)) {
			return result.message("请选择一种用户角色！");
		}
		
		return result.success();
	}

	@MenuSetting("submenu-role-list")
	@RequestMapping(value="show-user-list",method=RequestMethod.GET)
	public ModelAndView userList(Page page){
		ModelAndView result = new ModelAndView("principal/user-role-list");
		List<Principal> roleList = getRoleList(page);
		result.addObject("roleList",roleList);
		return result;
	}

	public List<Principal> getRoleList(Page page) {
		com.demo2do.core.persistence.support.Order order = new com.demo2do.core.persistence.support.Order();
		order.add("id", "DESC");
		return this.principalService.list(Principal.class, order,page);
	}
	
	//用户假删除
	@MenuSetting("submenu-role-list")
	@RequestMapping(value="delete-user-role/{id}",method=RequestMethod.GET)
	public ModelAndView deleteUser(@PathVariable Long id,Page page){
		Principal principal = this.principalService.load(Principal.class, id);
		principalService.deleteUser(principal);
		ModelAndView result = new ModelAndView("principal/user-role-list");
		List<Principal> roleList = getRoleList(page);
		result.addObject("roleList",roleList);
		return result;
	}
	
}
