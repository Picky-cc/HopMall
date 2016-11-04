package com.zufangbao.earth.yunxin.api.controller;

import static com.zufangbao.earth.yunxin.api.constant.ApiConstant.PARAMS_FN_KEY_WITH_COMBINATORS;
import static com.zufangbao.earth.yunxin.api.constant.CommandOpsFunctionCodes.COMMAND_ACTIVE_PAYMENT_VOUCHER;
import static com.zufangbao.earth.yunxin.api.constant.CommandOpsFunctionCodes.COMMAND_BUSINESS_PAYMENT_VOUCHER;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.yunxin.api.BaseApiController;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.earth.yunxin.api.handler.ActivePaymentVoucherHandler;
import com.zufangbao.earth.yunxin.api.handler.BusinessPaymentVoucherHandler;
import com.zufangbao.earth.yunxin.api.handler.ContractApiHandler;
import com.zufangbao.earth.yunxin.api.model.command.ActivePaymentVoucherCommandModel;
import com.zufangbao.earth.yunxin.api.model.command.BusinessPaymentVoucherCommandModel;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.icbc.business.CashFlow;

@Controller
@RequestMapping("/api/command")
public class CommandApiController extends BaseApiController{

	private static final Log logger = LogFactory.getLog(CommandApiController.class);
	
	@Autowired
	private BusinessPaymentVoucherHandler businessPaymentVoucherHandler;
	@Autowired
	private ContractApiHandler contractApiHandler;
	
	/*
	*//**
	 * 执行扣款指令
	 *//*
	@RequestMapping(value = "", params = {PARAMS_FN_KEY_WITH_COMBINATORS + COMMAND_OVERDUE_DEDUCT}, method = RequestMethod.POST)
	public @ResponseBody String commandDeduct(
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DeductCommandRequestModel commandModel
			) {
		try {
			if(!commandModel.isValid()) {
				return signErrorResult(response, ApiResponseCode.INVALID_PARAMS,commandModel.getCheckFailedMsg());
			}
			String merchantId = getMerchantId(request);
			//创建
			List<TradeSchedule> tradeSchedules = deductApplicationHandler.checkAndsaveDeductInfoBeforePorcessing(commandModel, IpUtil.getIpAddress(request), merchantId);
			
			deductApplicationHandler.processingAndUpdateDeducInfo_NoRollback(tradeSchedules,  commandModel.getDeductApplicationUuid());
			return signSucResult(response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("#commandOverdueDeduct occur error [requestNo : "+ commandModel.getRequestNo() +" ]!");
			return signErrorResult(response, e);
		}
	} */
	
	/**
	 * 商户付款凭证指令-提交
	 */
	@RequestMapping(value = "", params = {PARAMS_FN_KEY_WITH_COMBINATORS + COMMAND_BUSINESS_PAYMENT_VOUCHER, "transactionType=0"}, method = RequestMethod.POST)
	public @ResponseBody String submitBusinessPaymentVoucher(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute BusinessPaymentVoucherCommandModel model) {
		try {
			if(!model.checkSubmitParams()) {
				return signErrorResult(response, ApiResponseCode.INVALID_PARAMS, model.getCheckFailedMsg());
			}
			List<CashFlow> cashFlowList = businessPaymentVoucherHandler.businessPaymentVoucher(model, IpUtil.getIpAddress(request));
			if(cashFlowList.size() > 1) {
				return signSucResult(response, "处理中！", null, null);
			}else {
				return signSucResult(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("#commandBusinessPaymentVoucher occur error ]: " + e.getMessage());
			return signErrorResult(response, e);
		}
	}
	
	/**
	 * 商户付款凭证指令-撤销
	 */
	@RequestMapping(value = "", params = {PARAMS_FN_KEY_WITH_COMBINATORS + COMMAND_BUSINESS_PAYMENT_VOUCHER, "transactionType=1"}, method = RequestMethod.POST)
	public @ResponseBody String undoBusinessPaymentVoucher(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute BusinessPaymentVoucherCommandModel model) {
		try {
			if(!model.checkUndoParams()) {
				return signErrorResult(response, ApiResponseCode.INVALID_PARAMS, model.getCheckFailedMsg());
			}
			businessPaymentVoucherHandler.undoBusinessPaymentVoucher(model, IpUtil.getIpAddress(request));
			return signSucResult(response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("#undoBusinessPaymentVoucher occur error ]: " + e.getMessage());
			return signErrorResult(response, e);
		}
	}
	
	@Autowired
	private ActivePaymentVoucherHandler activePaymentVoucherHandler;
	
	@RequestMapping(value = "", params = {PARAMS_FN_KEY_WITH_COMBINATORS + COMMAND_ACTIVE_PAYMENT_VOUCHER, "transactionType=0"}, method = RequestMethod.POST)
	public @ResponseBody String submitActivePaymentVoucher(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute ActivePaymentVoucherCommandModel model) {
		try {
			MultipartHttpServletRequest fileRequest = (MultipartHttpServletRequest) request;
			if(model.submitParamsError()) {
				return signErrorResult(response, ApiResponseCode.INVALID_PARAMS, model.getCheckFailedMsg());
			}
			Contract contract = contractApiHandler.getContractBy(model.getUniqueId(), model.getContractNo());
			activePaymentVoucherHandler.checkRequestNoAndSaveLog(model, IpUtil.getIpAddress(fileRequest));
			activePaymentVoucherHandler.submitActivePaymentVoucher(model, contract, fileRequest);
			return signSucResult(response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("#submitActivePaymentVoucher occur error ]: " + e.getMessage());
			return signErrorResult(response, e);
		}
	}

	/**
	 * 主动付款凭证指令-撤销
	 */
	@RequestMapping(value = "", params = {PARAMS_FN_KEY_WITH_COMBINATORS + COMMAND_ACTIVE_PAYMENT_VOUCHER, "transactionType=1"}, method = RequestMethod.POST)
	public @ResponseBody String undoActivePaymentVoucher(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute ActivePaymentVoucherCommandModel model) {
		try {
			if(model.undoParamsError()) {
				return signErrorResult(response, ApiResponseCode.INVALID_PARAMS, model.getCheckFailedMsg());
			}
			activePaymentVoucherHandler.checkRequestNoAndSaveLog(model, IpUtil.getIpAddress(request));
			Contract contract = contractApiHandler.getContractBy(model.getUniqueId(), model.getContractNo());
			String bankTransactionNo = model.getBankTransactionNo();
			activePaymentVoucherHandler.undoActivePaymentVoucher(contract, bankTransactionNo);
			return signSucResult(response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("#undoBusinessPaymentVoucher occur error ]: " + e.getMessage());
			return signErrorResult(response, e);
		}
	}

	
}
