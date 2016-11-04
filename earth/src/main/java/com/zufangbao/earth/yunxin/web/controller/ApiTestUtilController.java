package com.zufangbao.earth.yunxin.web.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.yunxin.api.util.ApiSignUtils;
import com.zufangbao.sun.utils.HttpUtils;
import com.zufangbao.sun.yunxin.service.DictionaryService;

@Controller
@RequestMapping(value="manual-apis")
public class ApiTestUtilController extends BaseController {

	private static final Log logger = LogFactory.getLog(ApiTestUtilController.class);
	@Autowired
	private DictionaryService dictionaryService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView showApiTestUtilView() {
		ModelAndView view = new ModelAndView("manual-apis");
		return view;
	}
	
	@Value("#{config['earth_testApiHost']}")
	private String EARTH_TEST_API_HOST = "";
	
	private static final String MODIFY_URL = "/api/modify";
	private static final String QUERY_URL = "/api/query";	
	private static final String COMMAND_URL = "/api/command";
	
	@RequestMapping(value="modify-repayment-plan",method=RequestMethod.POST)
	@MenuSetting("submenu-payment-asset")
	public @ResponseBody String manualDeduct(@Secure Principal principal,
			@RequestParam(value="uniqueId", required = false, defaultValue = "") String uniqueId,
			@RequestParam(value="contractNo", required = false, defaultValue = "") String contractNo,
			@RequestParam(value="reason",required=true) int reason,
			@RequestParam(value="data",required=true) String data
			) {
		try {
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("fn", "200001");
			requestParams.put("requestNo", UUID.randomUUID().toString());
			requestParams.put("uniqueId", uniqueId);
			requestParams.put("contractNo", contractNo);
			requestParams.put("requestReason", "" + reason);
			requestParams.put("requestData", data);
			String result = HttpUtils.post(EARTH_TEST_API_HOST + MODIFY_URL, requestParams, getIdentityInfoMap(requestParams), "UTF-8");
			logger.info("result----->" + result);
			return jsonViewResolver.sucJsonResult("result", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return jsonViewResolver.errorJsonResult("Error msg:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="business-payment-voucher",method=RequestMethod.POST)
	@MenuSetting("submenu-payment-asset")
	public @ResponseBody String businessPaymentVoucher(@Secure Principal principal,
			@RequestParam(value="transactionType",required=true) int transactionType,
			@RequestParam(value="voucherType",required=true) int voucherType,
			@RequestParam(value="voucherAmount",required=true) BigDecimal voucherAmount,
			@RequestParam(value="financialContractNo",required=true) String financialContractNo,
			@RequestParam(value="receivableAccountNo",required=true) String receivableAccountNo,
			@RequestParam(value="paymentAccountNo",required=true) String paymentAccountNo,
			@RequestParam(value="bankTransactionNo",required=true) String bankTransactionNo,
			@RequestParam(value="paymentName",required=true) String paymentName,
			@RequestParam(value="paymentBank",required=true) String paymentBank,
			@RequestParam(value="detail",required=true) String detail
			) {
		try {
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("fn", "300003");
			requestParams.put("requestNo", UUID.randomUUID().toString());
			requestParams.put("transactionType", "" + transactionType);
			requestParams.put("voucherType", "" + voucherType);
			requestParams.put("voucherAmount", voucherAmount.toString());
			requestParams.put("financialContractNo", financialContractNo);
			requestParams.put("receivableAccountNo", receivableAccountNo);
			requestParams.put("paymentAccountNo", paymentAccountNo);
			requestParams.put("bankTransactionNo", bankTransactionNo);
			requestParams.put("paymentName", paymentName);
			requestParams.put("paymentBank", paymentBank);
			requestParams.put("detail", detail);
			String result = HttpUtils.post(EARTH_TEST_API_HOST + COMMAND_URL, requestParams, getIdentityInfoMap(requestParams), "UTF-8");
			logger.info("result----->" + result);
			return jsonViewResolver.sucJsonResult("result", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return jsonViewResolver.errorJsonResult("Error msg:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="modify-repayment-information",method=RequestMethod.POST)
	@MenuSetting("submenu-payment-asset")
	public @ResponseBody String modifyRepaymentInformation(@Secure Principal principal,
			@RequestParam(value="uniqueId", required = false, defaultValue = "") String uniqueId,
			@RequestParam(value="contractNo", required = false, defaultValue = "") String contractNo,			
			@RequestParam(value="bankCode",required=true) String bankCode,
			@RequestParam(value="bankAccount",required=true) String bankAccount,
			@RequestParam(value="bankName",required=true) String bankName,
			@RequestParam(value="bankProvince",required=true) String bankProvince,
			@RequestParam(value="bankCity",required=true) String bankCity
			) {
		try {
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("fn", "200003");
			requestParams.put("requestNo", UUID.randomUUID().toString());
			requestParams.put("uniqueId", uniqueId);
			requestParams.put("contractNo", contractNo);
			requestParams.put("bankCode", bankCode);
			requestParams.put("bankAccount", bankAccount);
			requestParams.put("bankName", bankName);
			requestParams.put("bankProvince", bankProvince);
			requestParams.put("bankCity", bankCity);
			String result = HttpUtils.post(EARTH_TEST_API_HOST + MODIFY_URL, requestParams, getIdentityInfoMap(requestParams), "UTF-8");
			logger.info("result----->" + result);
			return jsonViewResolver.sucJsonResult("result", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return jsonViewResolver.errorJsonResult("Error msg:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="prepayment",method=RequestMethod.POST)
	@MenuSetting("submenu-payment-asset")
	public @ResponseBody String prepayment(@Secure Principal principal,
			@RequestParam(value="uniqueId", required = false, defaultValue = "") String uniqueId,
			@RequestParam(value="contractNo", required = false, defaultValue = "") String contractNo,			
			@RequestParam(value="type",required=true) int type,
			@RequestParam(value="assetInitialValue",required=true) BigDecimal assetInitialValue,
			@RequestParam(value="assetRecycleDate",required=true) String assetRecycleDate
			) {
		try {
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("fn", "200002");
			requestParams.put("requestNo", UUID.randomUUID().toString());
			requestParams.put("uniqueId", uniqueId);
			requestParams.put("contractNo", contractNo);
			requestParams.put("assetRecycleDate", assetRecycleDate);
			requestParams.put("assetInitialValue", assetInitialValue.toString());
			requestParams.put("type", "" + type);
			String result = HttpUtils.post(EARTH_TEST_API_HOST + MODIFY_URL, requestParams, getIdentityInfoMap(requestParams), "UTF-8");
			logger.info("result----->" + result);
			return jsonViewResolver.sucJsonResult("result", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return jsonViewResolver.errorJsonResult("Error msg:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="import-asset-package",method=RequestMethod.POST)
	@MenuSetting("submenu-payment-asset")
	public @ResponseBody String importAssetPackage(@Secure Principal principal,
			@RequestParam(value="assetPackageContent",required=true) String assetPackageContent
			) {
		try {
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("fn", "200004");
			requestParams.put("requestNo", UUID.randomUUID().toString());
			requestParams.put("importAssetPackageContent", assetPackageContent);
			String result = HttpUtils.post(EARTH_TEST_API_HOST + MODIFY_URL, requestParams, getIdentityInfoMap(requestParams), "UTF-8");
			logger.info("result----->" + result);
			return jsonViewResolver.sucJsonResult("result", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return jsonViewResolver.errorJsonResult("Error msg:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="modify-overdue-fee",method=RequestMethod.POST)
	@MenuSetting("submenu-payment-asset")
	public @ResponseBody String modifyOverDueFee(@Secure Principal principal,
			@RequestParam(value="modifyOverDueFeeDetails",required=true) String modifyOverDueFeeDetails
			) {
		try {
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("fn", "200005");
			requestParams.put("requestNo", UUID.randomUUID().toString());
			requestParams.put("modifyOverDueFeeDetails", modifyOverDueFeeDetails);
			String result = HttpUtils.post(EARTH_TEST_API_HOST + MODIFY_URL, requestParams, getIdentityInfoMap(requestParams), "UTF-8");
			logger.info("result----->" + result);
			return jsonViewResolver.sucJsonResult("result", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return jsonViewResolver.errorJsonResult("Error msg:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="query-repayment-plan",method=RequestMethod.POST)
	@MenuSetting("submenu-payment-asset")
	public @ResponseBody String queryRepaymentPlan(@Secure Principal principal,
			@RequestParam(value="uniqueId", required = false, defaultValue = "") String uniqueId,
			@RequestParam(value="contractNo", required = false, defaultValue = "") String contractNo			
			) {
		try {
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("fn", "100001");
			requestParams.put("requestNo", UUID.randomUUID().toString());
			requestParams.put("uniqueId", uniqueId);
			requestParams.put("contractNo", contractNo);
			String result = HttpUtils.post(EARTH_TEST_API_HOST + QUERY_URL, requestParams, getIdentityInfoMap(requestParams), "UTF-8");
			logger.info("result----->" + result);
			return jsonViewResolver.sucJsonResult("result", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return jsonViewResolver.errorJsonResult("Error msg:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="query-repayment-list",method=RequestMethod.POST)
	@MenuSetting("submenu-payment-asset")
	public @ResponseBody String queryRepaymentList(@Secure Principal principal,
			@RequestParam(value="financialContractNo", required = true) String financialContractNo,
			@RequestParam(value="queryStartDate", required = true) String queryStartDate,			
			@RequestParam(value="queryEndDate",required=true) String queryEndDate
			) {
		try {
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("fn", "100003");
			requestParams.put("requestNo", UUID.randomUUID().toString());
			requestParams.put("financialContractNo", financialContractNo);
			requestParams.put("queryStartDate", queryStartDate);
			requestParams.put("queryEndDate", queryEndDate);
			String result = HttpUtils.post(EARTH_TEST_API_HOST + QUERY_URL, requestParams, getIdentityInfoMap(requestParams), "UTF-8");
			logger.info("result----->" + result);
			return jsonViewResolver.sucJsonResult("result", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return jsonViewResolver.errorJsonResult("Error msg:" + e.getMessage());
		}
	}
	
	@Value("#{config['bridgewaterHost']}")
	private String BRIDGEWATER_HOST = "";
	
	@RequestMapping(value="command-deduct",method=RequestMethod.POST)
	@MenuSetting("submenu-payment-asset")
	public @ResponseBody String commandDeduct(@Secure Principal principal,
			@RequestParam(value="uniqueId", required = false, defaultValue = "") String uniqueId,
			@RequestParam(value="contractNo", required = false, defaultValue = "") String contractNo,
			@RequestParam(value="deductId", required = true) String deductId,
			@RequestParam(value="financialProductCode", required = true) String financialProductCode,
			@RequestParam(value="apiCalledTime", required = true) String apiCalledTime,			
			@RequestParam(value="deductAmount", required = true) BigDecimal deductAmount,			
			@RequestParam(value="repaymentType",required=true) int repaymentType,
			@RequestParam(value="repaymentDetails",required=true) String repaymentDetails
			) {
		try {
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("fn", "300001");
			requestParams.put("requestNo", UUID.randomUUID().toString());
			requestParams.put("uniqueId", uniqueId);
			requestParams.put("contractNo", contractNo);
			requestParams.put("deductId", deductId);
			requestParams.put("financialProductCode", financialProductCode);
			requestParams.put("apiCalledTime", apiCalledTime);
			requestParams.put("amount", deductAmount.toString());
			requestParams.put("repaymentType", "" + repaymentType);
			requestParams.put("repaymentDetails", repaymentDetails);
			String result = HttpUtils.post(BRIDGEWATER_HOST + COMMAND_URL, requestParams, getIdentityInfoMap(requestParams), "UTF-8");
			logger.info("result----->" + result);
			return jsonViewResolver.sucJsonResult("result", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return jsonViewResolver.errorJsonResult("Error msg:" + e.getMessage());
		}
	}
	
	public static final String TEST_MERID = "t_test_zfb";
	public static final String TEST_SECRET = "123456";
	
	public  Map<String, String> getIdentityInfoMap(Map<String, String> requestParams) {
		String signContent = ApiSignUtils.getSignCheckContent(requestParams);
		logger.info("签名字符串----->" + signContent);
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("merId", TEST_MERID);
		headers.put("secret", TEST_SECRET);
		String privateKey = dictionaryService.getPlatformPrivateKey();
		String sign = ApiSignUtils.rsaSign(signContent, privateKey);
		headers.put("sign", sign);
		logger.info("merId----->" + TEST_MERID);
		logger.info("secret----->" + TEST_SECRET);
		logger.info("sign----->" + sign);
		return headers;
	}
	
}
