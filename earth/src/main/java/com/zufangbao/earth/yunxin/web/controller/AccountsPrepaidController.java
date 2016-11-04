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
import com.zufangbao.sun.entity.customer.CustomerType;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.yunxin.entity.SourceDocumentStatus;
import com.zufangbao.sun.yunxin.entity.model.AccountsPrepaidModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentVO;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;
import com.zufangbao.wellsfargo.yunxin.handler.SourceDocumentHandler;

@Controller()
@RequestMapping(URL.CAPITAL_NAME)
@MenuSetting(URL.CAPITAL_MENU)
public class AccountsPrepaidController extends BaseController{
	
	@Autowired
	private SourceDocumentService sourceDocumentService;
	
	@Autowired
	private SourceDocumentHandler sourceDocumentHandler;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	
	@RequestMapping(value = "customer-account-manage/deposit-receipt-list/show", method = RequestMethod.GET)
	@MenuSetting("submenu-deposit-receipt-list")
	public ModelAndView showAccountsPrepaid(@ModelAttribute AccountsPrepaidModel accountsPrepaidModel, @Secure Principal principal, Page page, HttpServletRequest request) {
		try {
			
			ModelAndView modelAndView = new ModelAndView("capital/customer-account-manage/deposit-receipt-list");
			
			List<FinancialContract> financialContracts = financialContractService.loadAll(FinancialContract.class);
			modelAndView.addObject("financialContracts", financialContracts);
			modelAndView.addObject("sourceDocumentStatusList", Arrays.asList(SourceDocumentStatus.values()));
			modelAndView.addObject("customerTypeList", Arrays.asList(CustomerType.values()));
			modelAndView.addObject("accountsPrepaidModel",accountsPrepaidModel);
			return modelAndView;
		} catch(Exception e){
			e.printStackTrace();
		}
		return pageViewResolver.errorSpec();
		
	}
	
	
	@RequestMapping("customer-account-manage/deposit-receipt-list/query")
	public @ResponseBody String queryAccountsPrepaid(@Secure Principal principal,
			@ModelAttribute AccountsPrepaidModel accountsPrepaidModel, Page page) {
		try {
			List<SourceDocument> sourceDocumentList = sourceDocumentService.getSourceDocumentList(accountsPrepaidModel, page);
			List<SourceDocumentVO> sourceDocumentVOList = sourceDocumentHandler.castSourceDocumentVO(sourceDocumentList);
			
			int count = sourceDocumentService.count(accountsPrepaidModel);
			Map<String, Object> data = new HashMap<String, Object>();
			data.putIfAbsent("accountsPrepaidModel", accountsPrepaidModel);
			data.putIfAbsent("list", sourceDocumentVOList);
			data.putIfAbsent("size", count);
			return jsonViewResolver.sucJsonResult(data);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
}
