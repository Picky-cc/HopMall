package com.zufangbao.earth.web.controller.deduct;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.web.controller.remittance.RemittanceController;
import com.zufangbao.earth.yunxin.entity.deduct.model.DeductApplicationDetailShowModel;
import com.zufangbao.earth.yunxin.entity.deduct.model.QueryDeductApplicationShowModel;
import com.zufangbao.earth.yunxin.handler.deduct.DeductApplicationHandler;
import com.zufangbao.sun.api.model.deduct.DeductApplicationExecutionStatus;
import com.zufangbao.sun.api.model.deduct.RepaymentType;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.yunxin.entity.deduct.DeductApplicationQeuryModel;


@RestController
@RequestMapping("/deduct")
@MenuSetting("menu-finance")
public class DeductController extends BaseController {

	private static final Log logger = LogFactory.getLog(RemittanceController.class);
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private DeductApplicationHandler deductApplicationHandler;
	
	
	
	// 计划订单---列表页面
	@RequestMapping(value = "/application")
	@MenuSetting("submenu-deduct")
	public ModelAndView showRemittanceApplicationPage() {
		try {
			ModelAndView modelAndView = new ModelAndView("deduct/deduct-list");
			List<FinancialContract> financialContracts = financialContractService.loadAll(FinancialContract.class);
			modelAndView.addObject("financialContracts", financialContracts);
			modelAndView.addObject("orderStatus", Arrays.asList(DeductApplicationExecutionStatus.values()));
			modelAndView.addObject("repaymentType",Arrays.asList(RepaymentType.values()));
			return modelAndView;
		} catch (Exception e) {
			logger.error("#showDeductApplicationPage# occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	@RequestMapping(value="/application/query")
	@MenuSetting("submenu-deduct")
	public @ResponseBody String queryDeductApplication(DeductApplicationQeuryModel deductApplicationQeuryModel,Page page){
		try {
			

			
			List<QueryDeductApplicationShowModel> showModels =  deductApplicationHandler.queryDeductApplicationShowModel(deductApplicationQeuryModel,page);
			int total = deductApplicationHandler.countQueryDeductApplicationNumber(deductApplicationQeuryModel);
			
			Map<String, Object> data = new HashMap<>();
			data.put("deductApplicationQeuryModel",deductApplicationQeuryModel);
			data.put("list", showModels);
			data.put("size", total);
			return jsonViewResolver.sucJsonResult(data,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.error("#queryDeductApplicationPage# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("查询错误");
		}
	}

	@RequestMapping(value="application/detail/{deductApplicationUuid}")
	@MenuSetting("submenu-deduct")
	public ModelAndView showDeductApplicationDetails(
			@PathVariable(value = "deductApplicationUuid") String deductApplicationUuid){
		try {
			ModelAndView modelAndView = new ModelAndView("deduct/deduct-detail");
			
			DeductApplicationDetailShowModel showModel = deductApplicationHandler.assembleDeductApplicationDetailShowModel(deductApplicationUuid);
			
			modelAndView.addObject("showModel", showModel);
			
			return modelAndView;
			
			
		} catch (Exception e) {
			
			logger.error("#showDeductApplicationDetails# occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
}
