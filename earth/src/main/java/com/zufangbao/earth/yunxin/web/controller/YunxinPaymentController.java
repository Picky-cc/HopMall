package com.zufangbao.earth.yunxin.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
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
import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.StringUtils;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.util.ExceptionUtils;
import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.util.RegexUtils;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.yunxin.excel.DailyYunxinPaymentFlowCheckExcel;
import com.zufangbao.earth.yunxin.handler.SystemOperateLogHandler;
import com.zufangbao.earth.yunxin.web.controller.PaymentControllerSpec.LIST_ORDER;
import com.zufangbao.gluon.spec.earth.MessageTable4Earth;
import com.zufangbao.gluon.spec.global.GlobalSpec;
import com.zufangbao.gluon.util.BeanWrapperUtil;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.AppService;
import com.zufangbao.sun.service.BatchPayRecordService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.service.TransferApplicationService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.ExecutingDeductStatus;
import com.zufangbao.sun.yunxin.entity.ExecutingSettlingStatus;
import com.zufangbao.sun.yunxin.entity.OrderType;
import com.zufangbao.sun.yunxin.entity.OverDueStatus;
import com.zufangbao.sun.yunxin.entity.PaymentWay;
import com.zufangbao.sun.yunxin.entity.model.OrderQueryModel;
import com.zufangbao.sun.yunxin.entity.model.OrderViewDetail;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationQueryModel;
import com.zufangbao.sun.yunxin.handler.AssetValuationDetailHandler;
import com.zufangbao.sun.yunxin.log.LogFunctionType;
import com.zufangbao.sun.yunxin.log.LogOperateType;
import com.zufangbao.sun.yunxin.log.RecordLogCore;
import com.zufangbao.sun.yunxin.log.SystemOperateLogRequestParam;
import com.zufangbao.sun.yunxin.service.SystemOperateLogService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.OfflineBillPayDetail;
import com.zufangbao.wellsfargo.yunxin.handler.OrderHandler;

/**
 * @author zhenghb
 */
@Controller
@RequestMapping(PaymentControllerSpec.NAME)
@MenuSetting("menu-finance")
public class YunxinPaymentController extends BaseController {
	@Autowired
	private PrincipalService principalService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderHandler orderHandler;

	@Autowired
	private AppService appService;

	@Autowired
	private FinancialContractService financialContractService;

	@Autowired
	private BatchPayRecordService batchPayRecordService;

	@Autowired
	private TransferApplicationService transferApplicationService;
	@Autowired
	private DailyYunxinPaymentFlowCheckExcel dailyYunxinPaymentFlowCheckExcel;
	@Autowired
	private AssetValuationDetailHandler assetValuationDetailHandler;
	@Autowired
	private RecordLogCore recordLog;
	@Autowired
	private SystemOperateLogService systemOperateLogService;

	@Autowired
	private SystemOperateLogHandler systemOperateLogHandler;

	private static final Log logger = LogFactory.getLog(YunxinPaymentController.class);

	@RequestMapping(value = PaymentControllerSpec.PAYMENTLIST, method = RequestMethod.GET)
	@MenuSetting("submenu-payment-payment")
	public ModelAndView showOnlinePaymentOrdersPage(@Secure Principal principal) {
		ModelAndView modelAndView = new ModelAndView("finance/payment-success");
		List<FinancialContract> financialContractList =financialContractService.loadAll(FinancialContract.class);
		modelAndView.addObject("paymentWayList", PaymentWay.values());
		modelAndView.addObject("financialContractList",financialContractList);
		modelAndView.addObject("executingDeductStatusList",ExecutingDeductStatus.values());
		return modelAndView;
	}

	@RequestMapping(value = PaymentControllerSpec.PAYMENTDETAIL, method = RequestMethod.GET)
	@MenuSetting("submenu-payment-payment")
	public ModelAndView showOnlinePaymentOrderDetail(
			@PathVariable("transferApplicationId") Long transferApplicationId,
			@Secure Principal principal, Page page) {
		try {
			TransferApplication transferApplication = transferApplicationService.getTransferApplicationById(transferApplicationId);
			if(transferApplication == null) {
				return pageViewResolver.errorSpec("线上支付单不存在！");
			}
			ModelAndView modelAndView = new ModelAndView("finance/payment-detail");
			modelAndView.addObject("transferApplication", transferApplication);
			return modelAndView;
		} catch (Exception e) {
			logger.error("#showOnlinePaymentOrderDetail occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}

	@RequestMapping(value = PaymentControllerSpec.PAYMENTQUERY, method = RequestMethod.GET)
	@MenuSetting("submenu-payment-payment")
	public @ResponseBody String paymentShowView(
			HttpServletRequest request,
			@ModelAttribute TransferApplicationQueryModel transferApplicationQueryModel,
			@Secure Principal principal, Page page) {
		try {
			Map<String, Object> data = transferApplicationService
					.getTransferApplicationListByOnLinePaymentQuery(transferApplicationQueryModel, page);
			List<Object> resultRtn = (List<Object>) data.get("list");
			String[] propertyNames = { "transferApplicationNo", "order.id",
					"order.orderNo", "contractAccount.bank",
					"contractAccount.payerName", "contractAccount.bindId",
					"contractAccount.payAcNo", "amount", "lastModifiedTime",
					"comment", "paymentWayMsg", "executingDeductStatusMsg",
					"id" };
			List<Map<String, Object>> resultList = BeanWrapperUtil.wrapperList(resultRtn, propertyNames);
			data.put("list", resultList);
			return jsonViewResolver.sucJsonResult(data, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	@RequestMapping(value = PaymentControllerSpec.PAYMENTEXPORTEXCEL, method = RequestMethod.POST)
	@MenuSetting("submenu-payment-payment")
	public String paymentExportExcel(
			@RequestParam("startDateString") String startDate,
			@RequestParam("financialContractId") Long financialContractId,
			@Secure Principal principal, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (StringUtils.isEmpty(startDate)
					|| DateUtils.asDay(startDate) == null) {
				startDate = DateUtils.today();
			}
			HSSFWorkbook workBook = dailyYunxinPaymentFlowCheckExcel.gzUnionpayExcel(principal, IpUtil.getIpAddress(request), startDate, financialContractId);

			String fileName = create_download_file_name(DateUtils.asDay(startDate));
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

	@RequestMapping(value = PaymentControllerSpec.DAILYRETURNLISTEXPORTEXCEL, method = RequestMethod.GET)
	@MenuSetting("submenu-payment-payment")
	public String dailReturnListExcel(
			@RequestParam("startDateString") String startDate,
			@RequestParam("financialContractId") Long financialContractId,
			@Secure Principal principal, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (StringUtils.isEmpty(startDate)
					|| DateUtils.asDay(startDate) == null) {
				startDate = DateUtils.today();
			}
			
			HSSFWorkbook workBook = dailyYunxinPaymentFlowCheckExcel.dailyRetuenListExcel(principal,IpUtil.getIpAddress(request), startDate, financialContractId);

			String fileName = create_daily_return_list_download_file_name(DateUtils.asDay(startDate));
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
		return DateUtils.format(create_date, "yyyy_MM_dd") + "扣款流水对账" + ".xls";
	}

	private String create_daily_return_list_download_file_name(Date create_date) {
		return DateUtils.format(create_date, "yyyy_MM_dd") + "每日回款清单" + ".xls";
	}

	public Filter getContractFilter(
			TransferApplicationQueryModel transferApplicationQueryModel,
			Principal principal) {
		Filter filter = new Filter();

		if (transferApplicationQueryModel.getPaymentWayEnum() != null) {
			filter.addEquals("paymentWay",
					transferApplicationQueryModel.getPaymentWayEnum());
		}
		if (transferApplicationQueryModel.getExecutingDeductStatusEnum() != null) {
			filter.addEquals("executingDeductStatus",
					transferApplicationQueryModel
							.getExecutingDeductStatusEnum());
		}
		if (is_where_condition(transferApplicationQueryModel.getAccountName())) {
			filter.addLike("contractAccount.payerName",
					transferApplicationQueryModel.getAccountName());
		}
		if (is_where_condition(transferApplicationQueryModel.getPaymentNo())) {
			filter.addLike("transferApplicationNo",
					transferApplicationQueryModel.getPaymentNo());
		}

		if (is_where_condition(transferApplicationQueryModel.getRepaymentNo())) {
			filter.addLike("order.assetSet.singleLoanContractNo",
					transferApplicationQueryModel.getRepaymentNo());
		}
		if (is_where_condition(transferApplicationQueryModel.getBillingNo())) {
			filter.addLike("order.orderNo",
					transferApplicationQueryModel.getBillingNo());
		}
		if (is_where_condition(transferApplicationQueryModel.getPayAcNo())) {
			filter.addLike("contractAccount.payAcNo",
					transferApplicationQueryModel.getPayAcNo());

		}

		if (is_where_condition(transferApplicationQueryModel
				.getStartDateString())) {
			filter.addGreaterThan(
					"lastModifiedTime",
					DateUtils.addDays(
							transferApplicationQueryModel.getStartDate(), -1));
		}
		if (is_where_condition(transferApplicationQueryModel.getEndDateString())) {
			filter.addLessThan(
					"lastModifiedTime",
					DateUtils.addDays(
							transferApplicationQueryModel.getEndDate(), 1));
		}

		return filter;
	}

	private boolean is_where_condition(String address) {
		return !StringUtils.isEmpty(address);
	}

	@RequestMapping(value = "/order/list", method = RequestMethod.GET)
	@MenuSetting("submenu-payment-order")
	public ModelAndView listOrders(
			@Secure Principal principal, HttpServletRequest request) {
		try {
			List<FinancialContract> financialContracts = financialContractService.list(FinancialContract.class, new Filter());
			ModelAndView mav = new ModelAndView(LIST_ORDER.VIEW);
			mav.addObject(LIST_ORDER.RT_FINANCIALCONTRACTLIST,financialContracts);
			mav.addObject(LIST_ORDER.RT_EXECUTINGSETTLINGSTATUSLIST,ExecutingSettlingStatus.values());
			mav.addObject(LIST_ORDER.RT_OVERDUE_STATUS_LIST,OverDueStatus.values());
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	private List<Order> getPagedSettlementOrders(
			OrderQueryModel orderQueryModel, Page page) {
		return orderService.listOrder(orderQueryModel,
				page.getBeginIndex(), page.getEveryPage());
	}

	@RequestMapping(value = "/order/query", method = RequestMethod.GET)
	@MenuSetting("submenu-payment-order")
	public @ResponseBody String queryOrders(
			@ModelAttribute OrderQueryModel orderQueryModel, Page page,
			@Secure Principal principal, HttpServletRequest request) {
		try {
			int unpagedOrders = orderService.countOrderList(orderQueryModel);
			List<Order> orderList = getPagedSettlementOrders(orderQueryModel, page);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("size",unpagedOrders );
			data.put("list", orderList);
			return jsonViewResolver.sucJsonResult(data, SerializerFeature.DisableCircularReferenceDetect);
		} catch (Exception e) {
			e.printStackTrace();
			int errCode = ExceptionUtils.getErrorCodeFromException(e);
			String message = MessageTable4Earth.getMessage(errCode);
			logger.error("#listBillingPlansBy occur error, code[" + errCode
					+ " ],message[" + message + "]");
			return jsonViewResolver.errorJsonResult(message);
		}
	}

	@RequestMapping(value = "/order/{orderId}/detail", method = RequestMethod.GET)
	@MenuSetting("submenu-payment-order")
	public ModelAndView showOrderDetail(@PathVariable("orderId") Long orderId,
			@Secure Principal principal, HttpServletRequest request, Page page) {
		try {
			Order order = orderService.getOrderById(orderId, OrderType.NORMAL);
			if(order == null) {
				return pageViewResolver.errorSpec("结算单不存在！");
			}
			OrderViewDetail orderViewDetail = orderHandler.convertToOrderDetail(order);
			List<OfflineBillPayDetail> payDetails = orderHandler.getOfflineBillList(order);
			orderViewDetail.setPaymentWay(CollectionUtils.isEmpty(payDetails));
			
			List<TransferApplication> transferApplications = transferApplicationService.getTransferApplicationListBy(order);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderViewDetail", orderViewDetail);
			params.put("transferApplications", transferApplications);
			params.put("payDetails", payDetails);
			return pageViewResolver.pageSpec("yunxin/payment/order-show-detail",params);
		} catch (Exception e) {
			e.printStackTrace();
			int errCode = ExceptionUtils.getErrorCodeFromException(e);
			String message = MessageTable4Earth.getMessage(errCode);
			logger.error("#listBillingPlansBy occur error, code[" + errCode
					+ " ],message[" + message + "]");
			return pageViewResolver.errorSpec();
		}
	}

	@RequestMapping(value = "/order/{repaymentBillUuid}/pre-edit", method = RequestMethod.GET)
	public ModelAndView preEditOrder(
			@PathVariable("repaymentBillUuid") String repaymentBillUuid,
			@Secure Principal principal, HttpServletRequest request) {
		try {
			Order order = orderService
					.getOrderByRepaymentBillId(repaymentBillUuid);
			if (order.isEditable()) {
				return pageViewResolver.pageSpec(
						"yunxin/payment/order-pre-edit", "order", order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageViewResolver.pageSpec("error-modal", "message", "结算单不可编辑！");

	}

	@RequestMapping(value = "/order/{orderId}/edit", method = RequestMethod.POST)
	public @ResponseBody String editOrder(
			@PathVariable("orderId") Long orderId,
			@Secure Principal principal,
			HttpServletRequest request,
			@RequestParam(value = "penalty-amount", required = true, defaultValue = "") String expectedPenaltyAmountString,
			@RequestParam(value = "comment", required = true, defaultValue = "") String comment) {
		try {
			String trimedPenaltyAmountString = org.apache.commons.lang.StringUtils.trim(expectedPenaltyAmountString);
			if(!RegexUtils.isCurrency(trimedPenaltyAmountString)){
				return jsonViewResolver.errorJsonResult("请输入正确的金额格式！");
			}
			BigDecimal expectedPenaltyAmount = new BigDecimal(trimedPenaltyAmountString);
			Order order = orderService.getOrderById(orderId,OrderType.NORMAL);
			if (!isValid(order, expectedPenaltyAmount)) {
				return jsonViewResolver.errorJsonResult("不可编辑");
			}
			AssetSet assetSet = order.getAssetSet();
			BigDecimal penaltyAmountBeforeEdit = assetSet.getPenaltyInterestAmount();
			BigDecimal detalAmount = expectedPenaltyAmount.subtract(penaltyAmountBeforeEdit);
			

			Order oldOrder = new Order(order);
			Order editOrder = assetValuationDetailHandler
					.add_asset_valuation_and_modify_amount_of_asset_order(
							order, detalAmount, new Date(), comment);
			Order newOrder = new Order(editOrder);
			SystemOperateLogRequestParam param =getSystemOperateLogRequestParam(principal, request, oldOrder,
					newOrder);
			systemOperateLogHandler.generateSystemOperateLog(param);
			
			return jsonViewResolver.sucJsonResult();

		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}

	private SystemOperateLogRequestParam getSystemOperateLogRequestParam(Principal principal,
			HttpServletRequest request, Order oldOrder, Order newOrder) {
		SystemOperateLogRequestParam param = new SystemOperateLogRequestParam(
				principal.getId(), IpUtil.getIpAddress(request),
				newOrder.getOrderNo(), LogFunctionType.EDITORDER,
				LogOperateType.UPDATE, Order.class, oldOrder, newOrder,
				null);
		return param;
	}

	private boolean isValid(Order order, BigDecimal expectedPenaltyAmount) {
		if (order == null || !order.isEditable()) {
			return false;
		}
		if (expectedPenaltyAmount == null
				|| expectedPenaltyAmount.compareTo(BigDecimal.ZERO) < 0) {
			return false;
		}
		return true;
	}

}
