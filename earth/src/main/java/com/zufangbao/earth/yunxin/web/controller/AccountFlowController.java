package com.zufangbao.earth.yunxin.web.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.web.controller.financial.CapitalControllerSpec.URL;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.VirtualAccountFlow;
import com.zufangbao.sun.entity.account.VirtualAccountTransactionType;
import com.zufangbao.sun.service.VirtualAccountFlowService;
import com.zufangbao.sun.yunxin.entity.model.AccountFlowModel;

@Controller()
@RequestMapping(URL.CAPITAL_NAME)
@MenuSetting(URL.CAPITAL_MENU)
public class AccountFlowController extends BaseController{
	
	@Autowired
	private VirtualAccountFlowService virtualAccountFlowService;
	
	@RequestMapping(value = "customer-account-manage/account-flow-list/show", method = RequestMethod.GET)
	@MenuSetting("submenu-account-flow-list")
	public ModelAndView showAccountFlow(@ModelAttribute AccountFlowModel accountFlowModel, @Secure Principal principal, Page page, HttpServletRequest request) {
		try {
			
			ModelAndView modelAndView = new ModelAndView("capital/customer-account-manage/account-flow-list");
			
			modelAndView.addObject("accountSideList", Arrays.asList(AccountSide.values()));
			modelAndView.addObject("virtualAccountTransactionTypeList", Arrays.asList(VirtualAccountTransactionType.values()));
			modelAndView.addObject("accountFlowModel",accountFlowModel);
			return modelAndView;
		} catch(Exception e){
			e.printStackTrace();
		}
		return pageViewResolver.errorSpec();
		
	}
	
	@RequestMapping("customer-account-manage/account-flow-list/query")
	public @ResponseBody String queryAccountFlow(@Secure Principal principal,
			@ModelAttribute AccountFlowModel accountFlowModel, Page page) {
		try {
			List<VirtualAccountFlow> virtualAccountFlowList = virtualAccountFlowService.getVirtualAccountFlowList(accountFlowModel, page);
			int count = virtualAccountFlowService.count(accountFlowModel);
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.putIfAbsent("list", virtualAccountFlowList);
			data.putIfAbsent("size", count);
			return jsonViewResolver.sucJsonResult(data);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}

}