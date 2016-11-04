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
import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.customer.CustomerType;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.VirtualAccountService;
import com.zufangbao.sun.yunxin.entity.model.VirtualAccountModel;
import com.zufangbao.sun.yunxin.entity.model.VirtualAccountShowModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.VirtualAccountHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;
import com.zufangbao.wellsfargo.yunxin.handler.SourceDocumentHandler;

@Controller()
@RequestMapping(URL.CAPITAL_NAME)
@MenuSetting(URL.CAPITAL_MENU)
public class VirtualAccountController extends BaseController{
	
	@Autowired
	private SourceDocumentService sourceDocumentService;
	
	@Autowired
	private SourceDocumentHandler sourceDocumentHandler;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private VirtualAccountHandler virtualAccountHandler;
	
	@Autowired
	private VirtualAccountService virtualAccountService;
	
	
	@RequestMapping(value = "customer-account-manage/virtual-account-list/show", method = RequestMethod.GET)
	@MenuSetting("submenu-virtual-account-list")
	public ModelAndView showVirtualAccount(@ModelAttribute VirtualAccountModel virtualAccountModel, @Secure Principal principal, Page page, HttpServletRequest request) {
		try {
			
			ModelAndView modelAndView = new ModelAndView("capital/customer-account-manage/virtual-account-list");
			
			List<FinancialContract> financialContracts = financialContractService.loadAll(FinancialContract.class);
			modelAndView.addObject("financialContracts", financialContracts);
			modelAndView.addObject("customerTypeList", Arrays.asList(CustomerType.values()));
			modelAndView.addObject("virtualAccountModel",virtualAccountModel);
			return modelAndView;
		} catch(Exception e){
			e.printStackTrace();
		}
		return pageViewResolver.errorSpec();
		
	}
	
	
	@RequestMapping("customer-account-manage/virtual-account-list/query")
	public @ResponseBody String queryVirtualAccount(@Secure Principal principal,
			@ModelAttribute VirtualAccountModel virtualAccountModel, Page page) {
		try {
			List<VirtualAccount> virtualAccounts = virtualAccountService.getVirtualAccountList(virtualAccountModel, page);
			List<VirtualAccountShowModel> virtualAccountShowModelList = virtualAccountHandler.getVirtualAccountList(virtualAccounts);
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.putIfAbsent("virtualAccountModel", virtualAccountModel);
			data.putIfAbsent("list", virtualAccountShowModelList);
			data.putIfAbsent("size", virtualAccounts.size());
			return jsonViewResolver.sucJsonResult(data);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
}
