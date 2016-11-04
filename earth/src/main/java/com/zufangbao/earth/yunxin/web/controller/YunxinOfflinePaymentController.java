package com.zufangbao.earth.yunxin.web.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.utils.StringUtils;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.util.RegexUtils;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.yunxin.exception.OfflineBillAutidtException;
import com.zufangbao.earth.yunxin.exception.SourceDocumentNotExistException;
import com.zufangbao.earth.yunxin.handler.OfflineBillHandler;
import com.zufangbao.earth.yunxin.handler.SystemOperateLogHandler;
import com.zufangbao.earth.yunxin.handler.YunxinOfflinePaymentHandler;
import com.zufangbao.gluon.spec.global.GlobalMsgSpec;
import com.zufangbao.sun.yunxin.entity.IllegalInputAmountException;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.entity.OfflineBillConnectionState;
import com.zufangbao.sun.yunxin.entity.OrderType;
import com.zufangbao.sun.yunxin.entity.model.OfflineBillCreateModel;
import com.zufangbao.sun.yunxin.entity.model.OfflineBillQueryModel;
import com.zufangbao.sun.yunxin.entity.model.OrderMatchModel;
import com.zufangbao.sun.yunxin.entity.model.OrderMatchQueryModel;
import com.zufangbao.sun.yunxin.entity.model.OrderMatchShowModel;
import com.zufangbao.sun.yunxin.log.LogFunctionType;
import com.zufangbao.sun.yunxin.log.LogOperateType;
import com.zufangbao.sun.yunxin.log.RecordLogCore;
import com.zufangbao.sun.yunxin.log.SystemOperateLogRequestParam;
import com.zufangbao.sun.yunxin.service.OfflineBillService;
import com.zufangbao.sun.yunxin.service.SystemOperateLogService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;
import com.zufangbao.wellsfargo.yunxin.handler.SourceDocumentHandler;

@Controller
@RequestMapping(YunxinOfflinePaymentControllerSpec.NAME)
@MenuSetting("menu-finance")
public class YunxinOfflinePaymentController extends BaseController{

	@Autowired
	private OfflineBillService offlineBillService;
	@Autowired
	private OfflineBillHandler offlineBillHandler;
	@Autowired
	private SourceDocumentService sourceDocumentService;
	@Autowired
	private SourceDocumentHandler sourceDocumentHandler;
	@Autowired
	private YunxinOfflinePaymentHandler yunxinOfflinePaymentHandler;
	@Autowired
	private RecordLogCore recordLog;
	@Autowired
	private SystemOperateLogService systemOperateLogService;
	@Autowired
	private SystemOperateLogHandler systemOperateLogHandler;
	
	private static final Log logger = LogFactory.getLog(YunxinOfflinePaymentController.class);

	@MenuSetting("submenu-offline-payment-payment")
	@RequestMapping(value = YunxinOfflinePaymentControllerSpec.PAYMENTLIST, method = RequestMethod.GET)
	public ModelAndView showOfflinePaymentOrdersPage() {
		return new ModelAndView("finance/offline-payment-bill");
	}

	@MenuSetting("submenu-offline-payment-payment")
	@RequestMapping(value = YunxinOfflinePaymentControllerSpec.PAYEMENTQUERY, method = RequestMethod.GET)
	public @ResponseBody String queryOfflinePaymentOrders(Page page, @ModelAttribute OfflineBillQueryModel offlineBillQueryModel) {
		try {
			Map<String, Object> resultData = yunxinOfflinePaymentHandler.queryAllOfflineBillCorrespondingSourceDocumentByAuditStatus(offlineBillQueryModel, page);
			return jsonViewResolver.sucJsonResult(resultData,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.error("#queryOfflinePaymentOrders occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}

	@MenuSetting("submenu-offline-payment-payment")
	@RequestMapping(value = YunxinOfflinePaymentControllerSpec.PAYEMENTDETAIL, method = RequestMethod.GET)
	public ModelAndView showOfflineBillDetail(
			@PathVariable("offlineBillId") Long offlineBillId,
			@Secure Principal principal, Page page) {
		try {
			ModelAndView modelAndView = new ModelAndView("yunxin/payment/offline-bill-show-detail");
			OfflineBill offlineBill = offlineBillService.getOfflineBillById(offlineBillId);
			if (offlineBill == null) {
				return pageViewResolver.errorSpec("线下支付单不存在！");
			}
			OfflineBillConnectionState offlineBillConnectionState = sourceDocumentHandler.getOfflineBillConnectionState(offlineBill);
			offlineBill.setOfflineBillConnectionState(offlineBillConnectionState);
			
			List<OrderMatchShowModel> orderMatchShowModelList = offlineBillHandler.getOrderMatchShowModelBy(offlineBill.getOfflineBillUuid());
			modelAndView.addObject("offlineBill", offlineBill);
			modelAndView.addObject("orderMatchShowModelList",
					orderMatchShowModelList);
			return modelAndView;
		} catch (Exception e) {
			return pageViewResolver.errorSpec();
		}
	}

	public Filter getContractFilter(OfflineBillQueryModel offlineBillQueryModel) {
		Filter filter = new Filter();

		if (is_where_condition(offlineBillQueryModel.getAccountName())) {
			filter.addLike("payerAccountName",
					offlineBillQueryModel.getAccountName());
		}

		if (is_where_condition(offlineBillQueryModel.getPayAcNo())) {
			filter.addLike("payerAccountNo", offlineBillQueryModel.getPayAcNo());
		}

		return filter;
	}

	private boolean is_where_condition(String address) {
		return !StringUtils.isEmpty(address);
	}

	@MenuSetting("submenu-offline-payment-payment")
	@RequestMapping(value = "payment/pre-create-offline-bill", method = RequestMethod.GET)
	public ModelAndView preCreateOfflineBill(@Secure Principal principal) {

		ModelAndView modelAndView = new ModelAndView(
				"yunxin/payment/offline-bill-pre-create");
		modelAndView.addObject("source", "");
		return modelAndView;
	}

	@MenuSetting("submenu-offline-payment-payment")
	@RequestMapping(value = "payment/create-offline-bill", method = RequestMethod.POST)
	public @ResponseBody String createOfflineBill(@Secure Principal principal,
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute OfflineBillCreateModel offlineBillCreateModel) {
		try {
			if (!offlineBillCreateModel.isValid()) {
				return jsonViewResolver.errorJsonResult(offlineBillCreateModel.getCheckFailedMsg());
			}
			OfflineBill offlineBill = offlineBillHandler.create_offline_bill_and_create_source_document(offlineBillCreateModel);

			SystemOperateLogRequestParam param = getSystemOperateLogRequestParam(principal, request, offlineBill);
			systemOperateLogHandler.generateSystemOperateLog(param);
			return jsonViewResolver.sucJsonResult("offlineBillNo",offlineBill.getOfflineBillNo());
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("生成线下支付单出错！");
		}
	}

	private SystemOperateLogRequestParam getSystemOperateLogRequestParam(
			Principal principal, HttpServletRequest request,
			OfflineBill offlineBill) {
		SystemOperateLogRequestParam param = new SystemOperateLogRequestParam(
				principal.getId(), IpUtil.getIpAddress(request),
				offlineBill.getOfflineBillNo(), LogFunctionType.ADDOFFLINEBILL,
				LogOperateType.ADD, OfflineBill.class, offlineBill, null, null);
		return param;
	}

	@MenuSetting("submenu-offline-payment-payment")
	@RequestMapping(value = "match", method = RequestMethod.POST)
	public ModelAndView smartMatchOrderList(
			@RequestParam("offlineBillUuid") String offlineBillUuid) {
		List<OrderMatchModel> orderMatchModelList = offlineBillHandler
				.showSmartMatchModelOrderList(offlineBillUuid);
		ModelAndView modelAndView = new ModelAndView(
				"yunxin/payment/offline-bill-match");
		modelAndView.addObject("orderMatchModelList", orderMatchModelList);
		modelAndView.addObject("offlineBillUuid", offlineBillUuid);
		modelAndView.addObject("orderTypes", Arrays.asList(OrderType.values()));
		return modelAndView;
	}

	@MenuSetting("submenu-offline-payment-payment")
	@RequestMapping(value = "connection", method = RequestMethod.POST)
	public @ResponseBody String connectionOrder(
			@RequestParam(value = "orderNoAndValues") String orderNoAndValues,
			@RequestParam(value = "offlineBillUuid") String offlineBillUuid,
			@Secure Principal principal, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Map<String, Object> map = JsonUtils.parse(orderNoAndValues);
			BigDecimal connectionAmount = check_input_amount_format_return_connection_amount(map);
			offlineBillHandler.buildAssociationBetweenOrderAndOfflineBill(offlineBillUuid, map, connectionAmount, principal.getId(), IpUtil.getIpAddress(request));
			systemOperateLogHandler.generateAssociateSystemLog(principal, IpUtil.getIpAddress(request), offlineBillUuid, map);
			return jsonViewResolver.sucJsonResult();
		} catch (IllegalInputAmountException e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(YunxinOfflinePaymentControllerSpec.ILLEGAL_INPUT_AMOUNT);
		} catch (SourceDocumentNotExistException e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(YunxinOfflinePaymentControllerSpec.SOURCE_DOCUMENT_NOT_EXIST);
		} catch (OfflineBillAutidtException e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(GlobalMsgSpec.MSG_FAILURE);
		}
	}

	private BigDecimal check_input_amount_format_return_connection_amount(
			Map<String, Object> map) throws IllegalInputAmountException {
		BigDecimal connectionAmount = BigDecimal.ZERO;
		Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			String value = (String) entry.getValue();
			if (!RegexUtils.isRealNumber(value)) {
				throw new IllegalInputAmountException();
			}
			connectionAmount = connectionAmount.add(new BigDecimal(value));
		}
		if (connectionAmount.compareTo(BigDecimal.ZERO) == -1) {
			throw new IllegalInputAmountException();
		}
		return connectionAmount;
	}

	@MenuSetting("submenu-offline-payment-payment")
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public ModelAndView searchOrderList(
			@ModelAttribute OrderMatchQueryModel orderMatchQueryModel) {
		List<OrderMatchModel> orderMatchModelList = offlineBillHandler
				.searchMatchModelOrderList(orderMatchQueryModel);
		ModelAndView modelAndView = new ModelAndView(
				"yunxin/payment/offline-bill-match");
		modelAndView.addObject("orderMatchModelList", orderMatchModelList);
		modelAndView.addObject("orderTypes", Arrays.asList(OrderType.values()));
		modelAndView.addObject("orderMatchQueryModel", orderMatchQueryModel);
		return modelAndView;
	}
}
