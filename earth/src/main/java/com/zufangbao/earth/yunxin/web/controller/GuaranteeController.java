package com.zufangbao.earth.yunxin.web.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.yunxin.handler.OfflineBillHandler;
import com.zufangbao.earth.yunxin.handler.SystemOperateLogHandler;
import com.zufangbao.gluon.spec.global.GlobalCodeSpec;
import com.zufangbao.gluon.spec.global.GlobalMsgSpec;
import com.zufangbao.gluon.spec.global.GlobalSpec;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.AssetCategory;
import com.zufangbao.sun.ledgerbook.AssetConvertor;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.excel.ExcelUtil;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.GuaranteeStatus;
import com.zufangbao.sun.yunxin.entity.OrderType;
import com.zufangbao.sun.yunxin.entity.model.GuaranteeOrderExcel;
import com.zufangbao.sun.yunxin.entity.model.GuaranteeOrderModel;
import com.zufangbao.sun.yunxin.entity.model.OfflineBillCreateModel;
import com.zufangbao.sun.yunxin.log.LogFunctionType;
import com.zufangbao.sun.yunxin.log.LogOperateType;
import com.zufangbao.sun.yunxin.log.SystemOperateLogRequestParam;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.wellsfargo.exception.OfflineBillCreateException;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.OfflineBillPayDetail;
import com.zufangbao.wellsfargo.yunxin.handler.OrderHandler;

@Controller
@RequestMapping("guarantee/order")
@MenuSetting("menu-finance")
public class GuaranteeController extends BaseController{

	private static final Log logger = LogFactory.getLog(GuaranteeController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private OfflineBillHandler offlineBillHandler;

	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private AssetPackageService assetPackageService;
	
	@Autowired
	private LedgerBookHandler ledgerBookHandler;
	
	@Autowired
	private LedgerBookService ledgerBookService;
	
	@Autowired
	private LedgerItemService ledgerItemService;
	
	@Autowired
	private SystemOperateLogHandler systemOperateLogHandler;
	
	@Autowired
	private OrderHandler orderHandler;
	
	@RequestMapping("")
	@MenuSetting("submenu-guarantee-order")
	public ModelAndView showGuaranteeOrdersPage(@ModelAttribute GuaranteeOrderModel guaranteeOrderModel, @Secure Principal principal){
		try {
			ModelAndView modelAndView = new ModelAndView("guarantee/guarantee-order-list");
			List<FinancialContract> financialContractList = financialContractService.getAvailableFinancialContractForPincipal(principal.getId());
			List<GuaranteeStatus> guaranteeStatus = GuaranteeStatus.getGuaranteeStatusForGuaranteeOrder();
			modelAndView.addObject("GuaranteeStatus", guaranteeStatus);
			modelAndView.addObject("guaranteeOrderModel", guaranteeOrderModel);
			modelAndView.addObject("financialContracts",financialContractList);
			return modelAndView;
		} catch (Exception e) {
			logger.error("#showGuaranteeOrdersPage occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	@RequestMapping("/search")
	@MenuSetting("submenu-guarantee-order")
	public @ResponseBody String searchGuaranteeOrders(
			@ModelAttribute GuaranteeOrderModel guaranteeOrderModel, Page page) {
		try {
			List<Order> orderList = orderService.getGuaranteeOrders(guaranteeOrderModel, null);
			List<Order> pagedOrderList = orderService.getGuaranteeOrders(guaranteeOrderModel, page);
			Map<String, Object> resultData = new HashMap<String, Object>();
			resultData.put("list", pagedOrderList);
			resultData.put("size", orderList.size());
			return jsonViewResolver.sucJsonResult(resultData,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.error("#searchGuaranteeOrders occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	

	@RequestMapping(value = "/{orderId}/guarantee-detail", method = RequestMethod.GET)
	@MenuSetting("submenu-guarantee-order")
	public ModelAndView showGuaranteeDetailPage(@PathVariable("orderId") Long orderId) {
		try {
			Order order = orderService.getOrderById(orderId, OrderType.GUARANTEE);
			if(order == null) {
				return pageViewResolver.errorSpec("担保单不存在！");
			}
			List<OfflineBillPayDetail> payDetails = orderHandler.getOfflineBillList(order);
			ModelAndView modelAndView = new ModelAndView("guarantee/guarantee-detail");
			modelAndView.addObject("order", order);
			modelAndView.addObject("payDetails", payDetails);
			return modelAndView;
		} catch (Exception e) {
			logger.error("#showGuaranteeDetailPage occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	@RequestMapping(value = "/{orderId}/guarantee-lapse", method = RequestMethod.POST)
	@MenuSetting("submenu-payment-payment")
	public @ResponseBody String lapseGuarantee(HttpServletRequest request,
			@PathVariable("orderId") long orderId,
			@Secure Principal principal) {
		try {
			Order order = orderService.load(Order.class, orderId);
			if(order==null || order.getOrderType()!=OrderType.GUARANTEE){
				return jsonViewResolver.errorJsonResult("没有该担保单");
			}
			AssetSet assetSet = order.getAssetSet();
			if(assetSet==null ||!assetSet.guaranteeStatusCanBeLapsed()){
				return jsonViewResolver.errorJsonResult("不符合担保作废的条件");
			}
			assetSet.setGuaranteeStatus(GuaranteeStatus.LAPSE_GUARANTEE);
			repaymentPlanService.save(assetSet);
			SystemOperateLogRequestParam param = new SystemOperateLogRequestParam(
					principal.getId(), IpUtil.getIpAddress(request),
					order.getOrderNo(), LogFunctionType.GUARANTEELAPSE,
					LogOperateType.LAPSE, Order.class, order, null, null);
			systemOperateLogHandler.generateSystemOperateLog(param);
			
			try {
				LedgerBook ledgerBook = ledgerBookService.getBookByAsset(assetSet);
				AssetCategory assetCategory=AssetConvertor.convertAnMeiTuAssetCategory(assetSet,"",null);
				ledgerItemService.lapse_guarantee_of_asset(ledgerBook, assetSet.getAssetUuid(),assetCategory);
				logger.info("lapse receivable_guranttee with assetUuid["+assetSet.getAssetUuid()+"].");
			} catch(Exception e){
				logger.error("occur exception when lapse guaranteeOrder.");
				e.printStackTrace();
			}
			
			return jsonViewResolver.sucJsonResult();
		} catch(Exception e){
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(GlobalMsgSpec.MSG_FAILURE);
		}
		
	}
	
	@RequestMapping(value = "/{orderId}/guarantee-active", method = RequestMethod.POST)
	@MenuSetting("submenu-payment-payment")
	public @ResponseBody String activeGuarantee(HttpServletRequest request,
			@PathVariable("orderId") long orderId,
			@Secure Principal principal) {
		try {
			Order order = orderService.load(Order.class, orderId);
			if(order==null || order.getOrderType()!=OrderType.GUARANTEE){
				return jsonViewResolver.errorJsonResult("没有该担保单");
			}
			AssetSet assetSet = order.getAssetSet();
			if(assetSet==null ||!assetSet.isGuaranteeStatusLapsed()){
				return jsonViewResolver.errorJsonResult("不符合担保激活的条件");
			}
			assetSet.setGuaranteeStatus(GuaranteeStatus.WAITING_GUARANTEE);
			repaymentPlanService.save(assetSet);
			SystemOperateLogRequestParam param = new SystemOperateLogRequestParam(
					principal.getId(), IpUtil.getIpAddress(request),
					order.getOrderNo(), LogFunctionType.GUARANTEEACTIVE,
					LogOperateType.ACTIVE, Order.class, order, null, null);
			systemOperateLogHandler.generateSystemOperateLog(param);
			
			try {
				LedgerBook ledgerBook = ledgerBookService.getBookByAsset(assetSet);
				LedgerTradeParty guaranteeLedgerTradeParty = ledgerItemService.getGuranteLedgerTradeParty(assetSet);
				AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(assetSet);
				ledgerBookHandler.book_receivable_load_guarantee_and_assets_sold_for_repurchase(ledgerBook, assetCategory, guaranteeLedgerTradeParty, assetSet.getAssetInitialValue());
				logger.info("book receivable_guranttee with assetUuid["+assetSet.getAssetUuid()+"].");
			} catch(Exception e){
				logger.error("occur exception when active guaranteeOrder.");
				e.printStackTrace();
			}
			
			return jsonViewResolver.sucJsonResult();
		} catch(Exception e){
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(GlobalMsgSpec.MSG_FAILURE);
		}
		
	}
	

	@RequestMapping(value = "/pre-create-offline-bill", method = RequestMethod.POST)
	public ModelAndView preCreateOfflineBill(
			@RequestParam(value = "guaranteeRepaymentUuids") String guaranteeRepaymentUuidsJson,
			Principal principal, HttpServletRequest request) {
		try {
			List<String> guaranteeRepaymentUuids = JsonUtils.parseArray(
					guaranteeRepaymentUuidsJson, String.class);
			List<Order> guaranteeOrders = orderService.listOrder(
					guaranteeRepaymentUuids, OrderType.GUARANTEE);
			offlineBillHandler.validOrderStatus(guaranteeOrders);
			String orderNoSetJson = offlineBillHandler
					.extractOrderNoSet(guaranteeOrders);
			BigDecimal totalAmount = offlineBillHandler
					.getTotalAmount(guaranteeOrders);
			ModelAndView modelAndView = new ModelAndView(
					"yunxin/payment/offline-bill-pre-create");
			modelAndView.addObject("date", new Date());
			String orderNoSet = StringUtils.join(
					JsonUtils.parseArray(orderNoSetJson, String.class), ',');
			modelAndView.addObject("orderNoSet", orderNoSet);
			modelAndView.addObject("totalAmount", totalAmount);
			modelAndView.addObject("guaranteeRepaymentUuids",
					guaranteeRepaymentUuidsJson);
			modelAndView.addObject("source", "差额补足");
			return modelAndView;
		} catch (OfflineBillCreateException createException) {
			createException.printStackTrace();
			return pageViewResolver.errorSpec("已补足的差额补足单不能生成差额支付单");
		} catch (Exception e){
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}

	@RequestMapping(value = "/create-offline-bill", method = RequestMethod.POST)
	public ModelAndView createOfflineBill(
			@ModelAttribute OfflineBillCreateModel offlineBillCreateModel,
			Principal principal, HttpServletRequest request) {
		try {
			offlineBillHandler.createOfflineBillForGuaranteOrderMatch(offlineBillCreateModel);
			return pageViewResolver.redirectPageSpce("guarantee/order/search");
		} catch (OfflineBillCreateException billCreateException) {
			billCreateException.printStackTrace();
			return pageViewResolver.errorSpec("错误");
		} catch(Exception e){
			e.printStackTrace();
			return pageViewResolver.errorSpec("系统错误");
		}

	}

	@RequestMapping("/export-excel")
	@MenuSetting("submenu-guarantee-order")
	public @ResponseBody String exportExcel(@Secure Principal principal,
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute GuaranteeOrderModel guaranteeOrderModel) {
		try {
			logger.info("Start Download Guarantee Order!");
			
			List<Order> orderList = orderService.getGuaranteeOrders(guaranteeOrderModel, null);
			List<GuaranteeOrderExcel> guaranteeOrderExcelList = GuaranteeOrderExcel
					.convertFrom(orderList);
			ExcelUtil<GuaranteeOrderExcel> excels = new ExcelUtil<GuaranteeOrderExcel>(
					GuaranteeOrderExcel.class);
			HSSFWorkbook workBook = excels.exportDataToHSSFWork(guaranteeOrderExcelList, "差额补足单");
			
			String fileName = DateUtils.getNowFullDateTime() + "_" + "差额补足单.xls";
			exportExcelToClient(response, fileName, GlobalSpec.UTF_8, workBook);
			
			List<String> uuidList = orderList.stream().map(fc -> fc.getUuid())
					.collect(Collectors.toList());

			SystemOperateLogRequestParam param = new SystemOperateLogRequestParam(
					principal.getId(), IpUtil.getIpAddress(request), null,
					LogFunctionType.GUARANTEEEXPORT, LogOperateType.EXPORT,
					null, null, null, uuidList);
			systemOperateLogHandler.generateSystemOperateLog(param);
			logger.info("Execute Download Guarantee Order success!");
			return jsonViewResolver.sucJsonResult();
		} catch (Exception e) {
			logger.error("Execute Download Guarantee Order Error!:"
					+ e.getMessage());
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(GlobalCodeSpec.CODE_FAILURE);
		}
	}

}
