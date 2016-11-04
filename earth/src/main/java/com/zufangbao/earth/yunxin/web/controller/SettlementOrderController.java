package com.zufangbao.earth.yunxin.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.yunxin.excel.SettlementOrderForExportExcel;
import com.zufangbao.gluon.spec.global.GlobalSpec;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.SettlementStatus;
import com.zufangbao.sun.yunxin.entity.SettlementOrder;
import com.zufangbao.sun.yunxin.entity.model.SettlementOrderQueryModel;
import com.zufangbao.sun.yunxin.service.SettlementOrderService;
import com.zufangbao.wellsfargo.yunxin.handler.SettlementOrderHandler;

@Controller
@RequestMapping(SetttlementOrderControllerSpec.NAME)
@MenuSetting("menu-finance")
public class SettlementOrderController extends BaseController{

	private static final Log logger = LogFactory.getLog(SettlementOrderController.class);
	
	@Autowired
	private PrincipalService principalService;

	@Autowired
	private SettlementOrderService settlementOrderService;

	@Autowired
	private SettlementOrderForExportExcel SettlementOrderForExportExcel;
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private SettlementOrderHandler settlementOrderHandler;

	private List<SettlementOrder> querySettlementOrderSortByLastModifyTime(SettlementOrderQueryModel settlementOrderQueryModel,Page page){
		com.demo2do.core.persistence.support.Order order = new com.demo2do.core.persistence.support.Order("settlementOrder.lastModifyTime","desc");
		order.add("settlementOrder.id", "desc");
		
		List<SettlementOrder> settlementOrderList = settlementOrderService.getSettlementOrderListBy(settlementOrderQueryModel, order, page.getBeginIndex(), page.getEveryPage());
		return settlementOrderList;
	}
	
	@RequestMapping(value = SetttlementOrderControllerSpec.SETTLELIST, method = RequestMethod.GET)
	@MenuSetting("submenu-settlement-order")
	public ModelAndView firstShowSettlementOrder(HttpServletRequest request,
			@ModelAttribute SettlementOrderQueryModel settlementOrderQueryModel,@Secure Principal principal){
		try {
			List<App> appList = this.principalService.get_can_access_app_list(principal);
			List<FinancialContract> financialContractList = financialContractService.getAvailableFinancialContractForPincipal(principal.getId());
			ModelAndView modelAndView = new ModelAndView("yunxin/settlement/settlement-order");
			modelAndView.addObject("financialContracts",financialContractList);
			modelAndView.addObject("settlementOrderQueryModel", settlementOrderQueryModel);
			modelAndView.addObject("authority", principal.getAuthority());
			modelAndView.addObject("appList", appList);
			modelAndView.addObject("settlementStatusList", SettlementStatus.values());
			return modelAndView;
		} catch(Exception e){
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
			
	
	@RequestMapping(value = "query", method = RequestMethod.GET)
	@MenuSetting("submenu-settlement-order")
	public @ResponseBody String querySettlementOrder(HttpServletRequest request,
			@ModelAttribute SettlementOrderQueryModel settlementOrderQueryModel, @Secure Principal principal,
			Page page) {
		try {
			List<SettlementOrder> settlementOrderList = querySettlementOrderSortByLastModifyTime(settlementOrderQueryModel, page) ;
			Map<String, Object> resultData = new HashMap<String, Object>();
			resultData.put("list", settlementOrderList);
			return jsonViewResolver.sucJsonResult(resultData,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e){
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
		
	}

	@RequestMapping(value = SetttlementOrderControllerSpec.SETTLEDETAIL, method = RequestMethod.GET)
	public ModelAndView showSettlementOrderDetail(@PathVariable("settlementOrderId") Long settlementOrderId,
			@Secure Principal principal) {

		ModelAndView modelAndView = new ModelAndView("yunxin/settlement/settlement-order-detail");
		SettlementOrder settlementOrder = settlementOrderService.load(SettlementOrder.class, settlementOrderId);

		modelAndView.addObject("settlementOrder", settlementOrder);
		return modelAndView;
	}

	@RequestMapping(value = SetttlementOrderControllerSpec.EXPORTEXCEL, method = RequestMethod.GET)
	@MenuSetting("submenu-settlement-order")
	public ModelAndView exportSettlementExcel(@ModelAttribute SettlementOrderQueryModel settlementOrderQueryModel,
			HttpServletResponse response) {
		try {
			HSSFWorkbook workBook = SettlementOrderForExportExcel.createExcel(settlementOrderQueryModel);

			String fileName = create_download_file_name(new Date());
			exportExcelToClient(response, fileName, GlobalSpec.UTF_8, workBook);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String create_download_file_name(Date create_date) {
		return DateUtils.format(create_date, "yyyy_MM_dd") + "担保清算单" + ".xls";
	}

	@RequestMapping(value = "batch-submit", method = RequestMethod.POST)
	@MenuSetting("submenu-settlement-order")
	public @ResponseBody String batchSubmit(HttpServletRequest request,
			@ModelAttribute SettlementOrderQueryModel settlementOrderQueryModel) {
		try {
			logger.info("start batch submit: " + settlementOrderQueryModel.getSettlementOrderUuids());
			settlementOrderHandler.batchSubmit(settlementOrderQueryModel);
			logger.info("end batch submit! ");
			return JsonUtils.toJsonString(new Result().success());
		} catch (Exception e) {
			logger.info("batch submit error: " + e.getMessage());
			e.printStackTrace();
			return JsonUtils.toJsonString(new Result().fail().data("errorMsg", e.getMessage()));
		}
	}
	
	@RequestMapping(value = "batch-settlement", method = RequestMethod.POST)
	@MenuSetting("submenu-settlement-order")
	public @ResponseBody String batchSettlement(HttpServletRequest request,
			@ModelAttribute SettlementOrderQueryModel settlementOrderQueryModel) {
		try {
			logger.info("start batch settlement: " + settlementOrderQueryModel.getSettlementOrderUuids());
			settlementOrderHandler.batchSettlement(settlementOrderQueryModel);
			logger.info("end batch settlement! ");
			return JsonUtils.toJsonString(new Result().success());
		} catch (Exception e) {
			logger.info("batch settlement error: " + e.getMessage());
			e.printStackTrace();
			return JsonUtils.toJsonString(new Result().fail().data("errorMsg", e.getMessage()));
		}
	}

}
