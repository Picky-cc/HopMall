/**
 * 
 */
package com.zufangbao.earth.web.controller.financial;

import static com.zufangbao.earth.yunxin.api.constant.ApiConstant.PARAMS_MER_ID;
import static com.zufangbao.earth.yunxin.api.constant.ApiConstant.PARAMS_SECRET;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.StringUtils;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.api.GZUnionPayApiHandler;
import com.zufangbao.earth.api.TransactionDetailConstant.FunctionName;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.earth.yunxin.api.util.ApiSignUtils;
import com.zufangbao.earth.yunxin.handler.remittance.RemittancePlanExecLogHandler;
import com.zufangbao.earth.yunxin.handler.remittance.RemittanceRefundBillHandler;
import com.zufangbao.gluon.spec.global.GlobalSpec;
import com.zufangbao.sun.Constant.BankCorpEps;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.PaymentInstitutionName;
import com.zufangbao.sun.entity.icbc.business.FlowRecord;
import com.zufangbao.sun.service.AccountService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.FlowService;
import com.zufangbao.sun.utils.excel.ExcelUtil;
import com.zufangbao.sun.yunxin.entity.api.TMerConfig;
import com.zufangbao.sun.yunxin.entity.model.DirectbankCashFlowQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.CashFlowExportModel;
import com.zufangbao.sun.yunxin.entity.remittance.ExecutionStatus;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLog;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLogExportModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLogQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLogShowModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceRefundBill;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceRefundBillQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceRefundBillShowModel;
import com.zufangbao.sun.yunxin.entity.remittance.ReverseStatus;
import com.zufangbao.sun.yunxin.service.DictionaryService;
import com.zufangbao.sun.yunxin.service.TMerConfigService;
import com.zufangbao.sun.yunxin.service.remittance.IRemittancePlanExecLogService;
import com.zufangbao.sun.yunxin.service.remittance.IRemittanceRefundBillService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.CashFlowHandler;

/**
 * @author zjm
 *
 */

@Controller("flowController")
@RequestMapping("/capital")
@MenuSetting("menu-capital")
public class FlowController extends BaseController{

	private static final String SIGN_FAIL_TEMPLATE = "<?xml version='1.0' encoding='UTF-8'?><SDKPGK><INFO><FUNNAM>%s</FUNNAM><RETCOD>-9</RETCOD><ERRMSG>验签失败</ERRMSG></INFO></SDKPGK>";
	private static final String OTHER_FAIL_TEMPLATE = "<?xml version='1.0' encoding='UTF-8'?><SDKPGK><INFO><FUNNAM>%s</FUNNAM><RETCOD>-9</RETCOD><ERRMSG>其他错误</ERRMSG></INFO></SDKPGK>";
	private static final String ERR_MER_INFO_TEMPLATE = "<?xml version='1.0' encoding='UTF-8'?><SDKPGK><INFO><FUNNAM>%s</FUNNAM><RETCOD>-9</RETCOD><ERRMSG>商户信息配置错误，缺少商户号MerId或secret！</ERRMSG></INFO></SDKPGK>";

	@Autowired
	private FlowService flowService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private GZUnionPayApiHandler gzUnionPayApiHandler;
	@Autowired
	private TMerConfigService tMerConfigService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private IRemittancePlanExecLogService remittancePlanExecLogService;
	@Autowired
	private RemittancePlanExecLogHandler remittancePlanExecLogHandler; 
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private CashFlowHandler cashFlowHandler;
	@Autowired
	private RemittanceRefundBillHandler remittanceRefundBillHandler;
	@Autowired
	private IRemittanceRefundBillService remittanceRefundBillService;
	
	private static final Log logger = LogFactory.getLog(FlowController.class);

	@SuppressWarnings("unchecked")
	private Result cal_total_borrowings(Result flow_result) {
		List<FlowRecord> flowList = (List<FlowRecord>)flow_result.getData().get("flowList");
		
		BigDecimal debitSum = BigDecimal.ZERO;
		BigDecimal creditSum = BigDecimal.ZERO;

		if(flowList != null){
			
			for (FlowRecord flowRecord : flowList) {
				
				debitSum = debitSum
						.add(flowRecord.getCreditAmount() == null ? BigDecimal.ZERO
								: flowRecord.getCreditAmount());
				creditSum = creditSum
						.add(flowRecord.getDebitAmount() == null ? BigDecimal.ZERO
								: flowRecord.getDebitAmount());
			}
		}

		flow_result.data("debitSum", debitSum);
		flow_result.data("creditSum", creditSum);

		return flow_result;
	}

	@RequestMapping("/directbank-cash-flow")
	@MenuSetting("submenu-flow-monitor")
	public ModelAndView monitorHistory(@Secure Principal principal,@ModelAttribute DirectbankCashFlowQueryModel directbankCashFlowQueryModel) {
		try {
			ModelAndView modelAndView = new ModelAndView("finance/flow/flow-monitor-history");
			Date today = new Date();
			directbankCashFlowQueryModel.setStartDate(DateUtils.addDays(today, -6));
			directbankCashFlowQueryModel.setEndDate(today);
			List<Account> accountList = accountService.listAccountWithUsbKey();
			modelAndView.addObject("accountList", accountList);
			modelAndView.addObject("directbankCashFlowQueryModel", directbankCashFlowQueryModel);
			return modelAndView;
		} catch(Exception e){
			logger.error("monitorHistory eccor error");
			e.printStackTrace();
		}
		return pageViewResolver.errorSpec("系统错误");

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "directbank-cash-flow/search", method = RequestMethod.GET)
	@MenuSetting("submenu-flow-monitor")
	public ModelAndView flowHistorySearch(@Secure Principal principal, @ModelAttribute DirectbankCashFlowQueryModel directbankCashFlowQueryModel) {
		try {
			ModelAndView modelAndView = new ModelAndView(
					"finance/flow/flow-monitor-history");
			if(!directbankCashFlowQueryModel.isDateValid()){
				return pageViewResolver.errorSpec("请输入日期");
			}
			if(directbankCashFlowQueryModel.getAccountId()==null){
				return pageViewResolver.errorSpec("请选择账户");
			}
			List<Account> accountList = accountService.listAccountWithUsbKey();
			Account account = accountService.load(Account.class, directbankCashFlowQueryModel.getAccountId());
			
			List<FlowRecord> records = new ArrayList<FlowRecord>();
			Result flow_result = new Result();
			//TODO 
			if(BankCorpEps.PAB_CODE.equals(account.getBankCode())) {
				records = flowService.getHistoryFlowsByAccountFromDB(account, DateUtils.parseDate(directbankCashFlowQueryModel.formateStartDate(), "yyyyMMdd"), DateUtils.parseDate(directbankCashFlowQueryModel.formateEndDate(), "yyyyMMdd"));
				flow_result.success();
			}else{
				flow_result = flowService.getHistoryFlowsByAccount(account, directbankCashFlowQueryModel.formateStartDate(), directbankCashFlowQueryModel.formateEndDate());
				records = (List<FlowRecord>) flow_result.getData().get("flowList");
			}
			
			List<FlowRecord> filteredResult = getFilteredResult(directbankCashFlowQueryModel.getAccountSide(), directbankCashFlowQueryModel.getRecipAccNo(), directbankCashFlowQueryModel.getRecipName(), directbankCashFlowQueryModel.getSummary(), records);
			flow_result.data("flowList", filteredResult);
			cal_total_borrowings(flow_result);
			modelAndView.addObject("flow_result", flow_result);
			modelAndView.addObject("accountList", accountList);
			modelAndView.addObject("directbankCashFlowQueryModel", directbankCashFlowQueryModel);
			return modelAndView;
		} catch(Exception e){
			logger.error("flowHistorySearch eccor error");
			e.printStackTrace();
		}
		return pageViewResolver.errorSpec("系统错误");
	}

	public List<FlowRecord> getFilteredResult(String accountSide, String recipAccNo, String recipName, String summary, List<FlowRecord> records) {
		if( null == records){
			return new ArrayList<FlowRecord>();
		}
		Comparator<FlowRecord> sortByTimeDesc = (FlowRecord a,FlowRecord b)->a==null||b==null||a.getTime()==null||b.getTime()==null?0:b.getTime().compareTo(a.getTime());
		return	records.stream().filter(matchRecipName(recipName)).
		                      filter(matchRecipAccNo(recipAccNo)).
		                      filter(matchSummary(summary)).
		                      filter(matchAccountSide(accountSide)).
		                      sorted(sortByTimeDesc).
		                      collect(Collectors.toList());
	}

	private Predicate<? super FlowRecord> matchAccountSide(String accountSide) {
		if(StringUtils.isEmpty(accountSide)){
			return flowRecord->true;
		}
		return flowRecord->!StringUtils.isEmpty(flowRecord.getDrcrf())&&flowRecord.getDrcrf().equals(accountSide);

	}

	private Predicate<? super FlowRecord> matchRecipName(String filter_recipName) {
		if(StringUtils.isEmpty(filter_recipName)){
			return flowRecord->true;
		}
		return flowRecord->!StringUtils.isEmpty(flowRecord.getRecipName())&&flowRecord.getRecipName().contains(filter_recipName);

	}

	private Predicate<? super FlowRecord> matchRecipAccNo(String filter_recipAccNo) {
		if(StringUtils.isEmpty(filter_recipAccNo)){
			return flowRecord->true;
		}
		

		return flowRecord->!StringUtils.isEmpty(flowRecord.getRecipAccNo())&&flowRecord.getRecipAccNo().contains(filter_recipAccNo);

	}

	private Predicate<? super FlowRecord> matchSummary(String summary) {
		if(StringUtils.isEmpty(summary)){
			return flowRecord->true;
		}
		return flowRecord->!StringUtils.isEmpty(flowRecord.getSummary())&&flowRecord.getSummary().contains(summary);
	}
	
	@RequestMapping(value = "transaction-detail")
	public @ResponseBody String queryTransactionDetail(@RequestBody String reqXmlPacket
			, HttpServletRequest request, HttpServletResponse response){
		logger.info("capital: query transaction detail begin");
		logger.info("reqXmlPacket:"+reqXmlPacket);
		
		try {
			verifySign(reqXmlPacket, request);
			String rtnXmlPacket = gzUnionPayApiHandler.queryTransactionDetail(reqXmlPacket);
			logger.info("capital: query transaction detail end");
			return getPriKeyAndSign(response, rtnXmlPacket);
		} catch (RuntimeException e) { 
			e.printStackTrace();
			return processRuntimeException(e, FunctionName.FUNC_TRANSACTION_DETAIL_QUERY);
		} catch (Exception e) {
			e.printStackTrace();
			return otherFailPacket(FunctionName.FUNC_TRANSACTION_DETAIL_QUERY);
		}
	}

	private String processRuntimeException(RuntimeException e, String failedPocket) {
		if( e instanceof ApiException ) { 
			int error_code = ((ApiException) e).getCode();
			switch (error_code) {
			case ApiResponseCode.SIGN_MER_CONFIG_ERROR:
				return errorMerInfoPacket(failedPocket);
			case ApiResponseCode.SIGN_VERIFY_FAIL:
				return verifySignFailedPacket(failedPocket);
			default:
				return otherFailPacket(failedPocket);
			}
		}
		return otherFailPacket(failedPocket);
	}
	
	private String otherFailPacket(String failedPocket) {
		return String.format(OTHER_FAIL_TEMPLATE, failedPocket);
	}
	
	private void verifySign(String reqXmlPacket, HttpServletRequest request) {
		String merId = request.getHeader(PARAMS_MER_ID);
		String secret = request.getHeader(PARAMS_SECRET);
		vaildateHeader(merId, secret);
		TMerConfig merConfig = tMerConfigService.getTMerConfig(merId, secret);
		if(!ApiSignUtils.verifySign(reqXmlPacket, request, merConfig)) 
			throw new ApiException(ApiResponseCode.SIGN_VERIFY_FAIL);
	}
	
	private void vaildateHeader(String merId, String secret) {
		if (StringUtils.isEmpty(merId) || StringUtils.isEmpty(secret))
			throw new ApiException(ApiResponseCode.SIGN_MER_CONFIG_ERROR);
	}
	
	@RequestMapping(value = "batch-payment")
	public @ResponseBody String execBatchPayment(@RequestBody String reqXmlPacket
			, HttpServletRequest request, HttpServletResponse response){
		logger.info("capital: execute batch payment begin");
		logger.info("reqXmlPacket:"+reqXmlPacket);
		try {
			verifySign(reqXmlPacket, request);
			String rtnXmlPacket = gzUnionPayApiHandler.executeBatchPayment(reqXmlPacket);
			logger.info("capital: execute batch payment end");
			return getPriKeyAndSign(response, rtnXmlPacket);
		} catch (RuntimeException e) { 
			return processRuntimeException(e, FunctionName.FUNC_BATCH_PAYMENT);
		} catch (Exception e) {
			return otherFailPacket(FunctionName.FUNC_TRANSACTION_DETAIL_QUERY);
		}
	}

	@RequestMapping(value = "query-transaction")
	public @ResponseBody String execQueryTransaction(@RequestBody String reqXmlPacket
			, HttpServletRequest request, HttpServletResponse response){
		logger.info("capital: execute query transaction begin");
		logger.info("reqXmlPacket:"+reqXmlPacket);
		
		try {
			verifySign(reqXmlPacket, request);
			String rtnXmlPacket = gzUnionPayApiHandler.execTransactionResultQuery(reqXmlPacket);
			logger.info("capital: execute query transaction end");
			return getPriKeyAndSign(response, rtnXmlPacket);
		} catch (RuntimeException e) { 
			e.printStackTrace();
			return processRuntimeException(e, FunctionName.FUNC_TRANSACTION_STATUS_QUERY);
		} catch (Exception e) {
			e.printStackTrace();
			return otherFailPacket(FunctionName.FUNC_TRANSACTION_STATUS_QUERY);
		}
	}

	private String getPriKeyAndSign(HttpServletResponse response, String result) {
		String privateKey = dictionaryService.getPlatformPrivateKey();
		return ApiSignUtils.sign_and_return_result(response, result, privateKey);
	}
	
	private String verifySignFailedPacket(String functionName) {
		
		return String.format(SIGN_FAIL_TEMPLATE, functionName);
	}
	
	private String errorMerInfoPacket(String functionName) {
		
		return String.format(ERR_MER_INFO_TEMPLATE, functionName);
	}
	
	
	// 线上代付单---列表页面
	@RequestMapping(value = "/plan/execlog")
	@MenuSetting("submenu-remittance-plan-execlog")
	public ModelAndView showRemittancePlanExecLogPage() {
		try {
			ModelAndView modelAndView = new ModelAndView("remittance/online-advance-list");
			List<FinancialContract> financialContracts = financialContractService.loadAll(FinancialContract.class);
			modelAndView.addObject("financialContracts", financialContracts);
			modelAndView.addObject("remittanceChannel", PaymentInstitutionName.values());
			modelAndView.addObject("executionStatus", Arrays.asList(ExecutionStatus.values()));
			modelAndView.addObject("reverseStatus",ReverseStatus.values());
			return modelAndView;
		} catch (Exception e) {
			logger.error("#showRemittancePlanExecLogPage# occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	// 线上代付单---列表查询
	@RequestMapping(value = "/plan/execlog/query")
	@MenuSetting("submenu-remittance-plan-execlog")
	public @ResponseBody String queryRemittancePlanExecLog(@ModelAttribute RemittancePlanExecLogQueryModel queryModel,Page page) {
		try {
			List<RemittancePlanExecLogShowModel> showModels = remittancePlanExecLogHandler.queryShowModelList(queryModel, page);
			int total = remittancePlanExecLogService.queryRemittacePlanExecLogCount(queryModel);
			Map<String, Object> data = new HashMap<>();
			data.put("list", showModels);
			data.put("size", total);
			return jsonViewResolver.sucJsonResult(data,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.error("#queryRemittancePlanExecLog#  occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("查询错误");
		}
	}
	
	// 线上代付单---详情页面
	@RequestMapping(value="/plan/execlog/details/{execReqNo}")
	@MenuSetting("submenu-remittance-plan-execlog")
	public ModelAndView showRemittancePlanExecLogDetails(@PathVariable(value="execReqNo") String execReqNo){
		try {
			ModelAndView modelAndView = new ModelAndView("remittance/online-advance-detail");
			RemittancePlanExecLog remittancePlanExecLog = remittancePlanExecLogService.getRemittancePlanExecLogBy(execReqNo);
			List<RemittanceRefundBill> refundBills = remittanceRefundBillService.listRemittanceRefundBill(execReqNo);
			modelAndView.addObject("remittancePlanExecLog", remittancePlanExecLog);
			modelAndView.addObject("remittanceRefundBills", refundBills);
			return modelAndView;
		} catch (Exception e) {
			logger.error("#showRemittancePlanExecLogDetails# occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	// 线上支付单---导出对账单
	@RequestMapping(value="/plan/execlog/export")
	@MenuSetting("submenu-remittance-plan-execlog")
	public @ResponseBody String remittancePlanExecLogExport(
			@RequestParam(value="startDate",required = false, defaultValue = "") String startDate,
			@RequestParam(value="endDate",required = false, defaultValue = "") String endDate,
			@RequestParam(value = "financialContractUuid", required=false) String financialContractUuid, 
			HttpServletRequest request,
			HttpServletResponse response){
		try{
			Date fromDate = DateUtils.parseDate(startDate, "yyyy-MM-dd HH:mm:ss");
			Date toDate = DateUtils.parseDate(endDate, "yyyy-MM-dd HH:mm:ss");
			toDate = (toDate == null) ? new Date() : toDate;
			
			String msg = checkDate(fromDate, toDate);
			if(!StringUtils.isEmpty(msg)){
				return jsonViewResolver.errorJsonResult(msg);
			}
			FinancialContract financialContract = financialContractService.getFinancialContractBy(financialContractUuid);
			if(financialContract == null){
				return jsonViewResolver.errorJsonResult("筛选条件有误，请重新选择。");
			}
			
			// Ajax 请求不提供下载
		    if (StringUtils.isEmpty(request.getHeader("X-Requested-With"))) {
		    	List<RemittancePlanExecLogExportModel> logExportModels =  remittancePlanExecLogHandler.extractExecLogExportModel(financialContractUuid, fromDate, toDate);
				if(CollectionUtils.isEmpty(logExportModels)){
					logExportModels.add(new RemittancePlanExecLogExportModel());
				}
				ExcelUtil<RemittancePlanExecLogExportModel> execLogExportModelExcelUtil = new ExcelUtil<RemittancePlanExecLogExportModel>(RemittancePlanExecLogExportModel.class);
				List<String> remittanceExecLogs = execLogExportModelExcelUtil.exportDatasToCSV(logExportModels);
				
				String accountNo = financialContract.getCapitalAccount().getAccountNo();
				List<CashFlowExportModel> flowExportModels = cashFlowHandler.getCashFlowExportModelsBy(accountNo, fromDate, toDate, com.zufangbao.sun.ledgerbook.AccountSide.CREDIT);
				if(CollectionUtils.isEmpty(flowExportModels)){
					flowExportModels.add(new CashFlowExportModel());
				}
				ExcelUtil<CashFlowExportModel> cashFlowExportModelExcelUtil = new ExcelUtil<CashFlowExportModel>(CashFlowExportModel.class);
				List<String> remittanceCashFlows = cashFlowExportModelExcelUtil.exportDatasToCSV(flowExportModels);
				
				Map<String, List<String>> csvs = new HashMap<String, List<String>>();
				csvs.put("线上代付单", remittanceExecLogs);
				csvs.put("通道流水", remittanceCashFlows);
				exportZipToClient(response, genFileName(fromDate, toDate, financialContract.getContractName()), GlobalSpec.UTF_8, csvs);
		    }
		    return jsonViewResolver.sucJsonResult();
		}catch (Exception e){
			logger.error("#remittancePlanExecLogExport# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("导出失败，请稍后重试!");
		}
	}
	
	private String checkDate(Date begin, Date end){
		if(begin == null){
			return "筛选条件有误，请重新选择。";
		}
		long diff = end.getTime() - begin.getTime();
		if( diff < 0 || diff > 7*24*1000*60*60 ){ // 时间间隔为负OR超过7天
			return "筛选条件有误，请重新选择。";
		}
		return "";
	}
	
	private String genFileName(Date begin, Date end,String contractName) {
		return DateUtils.format(begin, "yyyyMMddHHmmss") + "_" + DateUtils.format(end, "yyyyMMddHHmmss") + "_"+ contractName + " 放款流水对账.zip";
	}
	
	// 代付撤销单---列表页面
	@RequestMapping(value = "/refundbill", method = RequestMethod.GET)
	@MenuSetting("submenu-remittance-refund-bill")
	public ModelAndView showRemittanceRefundBillPage() {
		try {
			ModelAndView modelAndView = new ModelAndView("remittance/advance-cancel-list");
			List<FinancialContract> financialContracts = financialContractService.loadAll(FinancialContract.class);
			modelAndView.addObject("financialContracts", financialContracts);
			modelAndView.addObject("paymentInstitutionNames", PaymentInstitutionName.values());
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	// 代付撤销单---列表查询
	@RequestMapping(value = "/refundbill/query")
	@MenuSetting("submenu-remittance-refund-bill")
	public @ResponseBody String queryRefundBill(@ModelAttribute RemittanceRefundBillQueryModel queryModel, Page page){
		try{
			List<RemittanceRefundBillShowModel> showModels = remittanceRefundBillHandler.queryShowModelList(queryModel, page);
			int total = remittanceRefundBillService.queryCount(queryModel);
			Map<String, Object> data = new HashMap<>();
			data.put("list", showModels);
			data.put("size", total);
			return jsonViewResolver.sucJsonResult(data,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e){
			logger.error("#queryRefundBill#  occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("查询错误");
		}
	}
	
}