package com.zufangbao.earth.web.controller.financial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.utils.StringUtils;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.handler.PrincipalHandler;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.util.ExceptionUtils;
import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.web.controller.financial.CashFlowAuditControllerSpec.I_Close_CashFlow_Audit;
import com.zufangbao.earth.web.controller.financial.CashFlowAuditControllerSpec.I_List_Bill_Match_Result;
import com.zufangbao.earth.web.controller.financial.CashFlowAuditControllerSpec.I_List_Cash_Flow;
import com.zufangbao.earth.web.controller.financial.CashFlowAuditControllerSpec.I_List_Credit_Cash_Flow;
import com.zufangbao.earth.web.controller.financial.CashFlowAuditControllerSpec.I_List_Debit_Cash_Flow;
import com.zufangbao.earth.web.controller.financial.CashFlowAuditControllerSpec.I_Monitor_Cash_Flow;
import com.zufangbao.earth.web.controller.financial.CashFlowAuditControllerSpec.I_Query_BillingPlan_For_Debit;
import com.zufangbao.earth.web.controller.financial.CashFlowAuditControllerSpec.I_Query_Credit_Cash_Flow;
import com.zufangbao.earth.web.controller.financial.CashFlowAuditControllerSpec.I_Show_CashFlow_Detail;
import com.zufangbao.earth.web.controller.financial.CashFlowAuditControllerSpec.I_Voucher_Change;
import com.zufangbao.earth.web.controller.financial.FinanceControllerSpec.ViewSpec.ListAppArriveRecord;
import com.zufangbao.earth.yunxin.handler.SystemOperateLogHandler;
import com.zufangbao.gluon.exception.NoAuthorityException;
import com.zufangbao.gluon.exception.NoDataInDBException;
import com.zufangbao.gluon.spec.earth.GlobalSpec4Earth.ErrorMsg4Audit;
import com.zufangbao.gluon.spec.global.GlobalCodeSpec;
import com.zufangbao.gluon.spec.global.GlobalMsgSpec;
import com.zufangbao.gluon.spec.global.GlobalMsgSpec.GeneralErrorMsg;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.icbc.business.AppArriveRecord;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.AccountService;
import com.zufangbao.sun.service.AppArriveRecordService;
import com.zufangbao.sun.service.AppService;
import com.zufangbao.sun.service.CompanyService;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.service.SystemLogService;
import com.zufangbao.sun.yunxin.entity.model.CashFlowConditionModel;
import com.zufangbao.wellsfargo.greypool.document.entity.busidocument.bill.BillingPlan;
import com.zufangbao.wellsfargo.greypool.document.entity.busidocument.contract.SimplifiedContractInfo;
import com.zufangbao.wellsfargo.greypool.document.entity.busidocument.handler.BusinessContractHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.BillMatchResult;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.VoucherOperationInfo;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.AfterVoucherIssuedHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.CashFlowCacheHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.CashFlowHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.webform.CashFlowDetail;
import com.zufangbao.wellsfargo.yunxin.handler.OrderHandler;


/**
 * @author wukai
 *
 */
@Controller
@RequestMapping(value={"/finance","/billing-plan"})
@MenuSetting("menu-capital")
public class CashFlowAuditController extends BaseController {

	@Autowired
	private PrincipalService principalService;
	@Autowired
	private SystemLogService systemLogService;

	@Autowired
	private AppArriveRecordService appArriveRecordService;
	@Autowired
	private AppService appService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private PrincipalHandler principalHandler;
	@Autowired
	private CashFlowHandler cashFlowHandler;
	@Autowired
	private CashFlowCacheHandler cashFlowCacheHandler;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ContractAccountService contractAccountService;
	@Autowired
	private BusinessContractHandler contractHandler;
	@Autowired
	private OrderHandler orderHandler;
	@Autowired
	private AfterVoucherIssuedHandler afterVoucherIssuedHandler;
	@Autowired
	private SystemOperateLogHandler systemOperateLogHandler;
	
	private static final Log logger = LogFactory.getLog(CashFlowAuditController.class);
	
	
	@RequestMapping(value=CashFlowAuditControllerSpec.I_Query_Trade_Parties.NAME,method = RequestMethod.GET)
	public @ResponseBody String queryTradePartyNameListByKeyWord(HttpServletRequest request,@Secure Principal principal, 
			@RequestParam(CashFlowAuditControllerSpec.I_Query_Trade_Parties.PARAM_APP_ID) String appId,
			@RequestParam("keyword") String keyWord){
		try {
			
			logger.info("begin querySimplifiedContractInfoBy with appId["+appId+"].keyword["+keyWord+"]");
			
			Long companyId = companyService.getCompanyIdBy(appId);
			List<ContractAccount> contractAccountList = contractAccountService.getContractAccountsByMatch(keyWord);
			
			return jsonViewResolver.sucJsonResult(CashFlowAuditControllerSpec.I_Query_Trade_Parties.RETURN_TRADE_PARTIES, convertToNamePartyOrder(contractAccountList));
			
		} catch (Exception e) {
			
			int errCode = ExceptionUtils.getErrorCodeFromException(e);
			
			logger.error("#querySimplifiedContractInfoBy occur error,  error code[" + errCode +" ]");
			
			e.printStackTrace();
			
			return jsonViewResolver.errorJsonResult(GlobalMsgSpec.GeneralErrorMsg.MSG_SYSTEM_ERROR);
		}
	}
	
	private Map<String,Integer> convertToNamePartyOrder(List<ContractAccount> contractAccounts){
		Map<String,Integer> accountNames = new HashMap<String,Integer>();
		for (ContractAccount contractAccount : contractAccounts) {
			if(contractAccount==null) continue;
			accountNames.put(contractAccount.getPayerName(), 2);
		}
		return accountNames;
	}
	
	
	@RequestMapping(value=CashFlowAuditControllerSpec.I_Query_Simplified_Contract_Info.NAME,method = RequestMethod.GET)
	public @ResponseBody String querySimplifiedContractInfoBy(HttpServletRequest request,@Secure Principal principal, 
			@RequestParam(CashFlowAuditControllerSpec.I_Query_Simplified_Contract_Info.PARAM_APP_ID) String appId,
			@RequestParam("keyWord") String keyword){
		try {
			
			logger.info("begin querySimplifiedContractInfoBy with appId["+appId+"].keyword["+keyword+"]");
			
			Long companyId = companyService.getCompanyIdBy(appId);
			
			List<SimplifiedContractInfo> simplifiedContractInfo = contractHandler.getSimpliedContractInfoByMatch(keyword);
			
			return jsonViewResolver.sucJsonResult(CashFlowAuditControllerSpec.I_Query_Simplified_Contract_Info.RETURN_SIMPLIFIED_CONTRACT_INFO_LIST, simplifiedContractInfo);
			
		} catch (Exception e) {
			
			int errCode = ExceptionUtils.getErrorCodeFromException(e);
			
			logger.error("#querySimplifiedContractInfoBy occur error,  error code[" + errCode +" ]");
			
			e.printStackTrace();
			
			return jsonViewResolver.errorJsonResult(GlobalMsgSpec.GeneralErrorMsg.MSG_SYSTEM_ERROR);
		}
	}
	//cash flow audit
	@MenuSetting(I_List_Credit_Cash_Flow.SUB_MENU_SETTING)
	@RequestMapping(value = I_List_Credit_Cash_Flow.NAME, method = RequestMethod.GET,params = I_List_Credit_Cash_Flow.PARAM_ACCOUNT_SIDE+"="+CashFlowAuditControllerSpec.CREDIT)
	public ModelAndView listCreditCashFLow(HttpServletRequest request, Page page, @Secure Principal principal,
			@ModelAttribute CashFlowConditionModel cashFlowConditionModel) {
		return listCashFLow(request, page, principal, cashFlowConditionModel);
	}
	
	@MenuSetting(I_List_Debit_Cash_Flow.SUB_MENU_SETTING)
	@RequestMapping(value = I_List_Debit_Cash_Flow.NAME, method = RequestMethod.GET,params = I_List_Credit_Cash_Flow.PARAM_ACCOUNT_SIDE+"="+CashFlowAuditControllerSpec.DEBIT)
	public ModelAndView listDebitCashFLow(HttpServletRequest request, Page page, @Secure Principal principal,
			@ModelAttribute CashFlowConditionModel cashFlowConditionModel) {
		return listCashFLow(request, page, principal, cashFlowConditionModel);
	}
	
	@RequestMapping(value = I_Query_Credit_Cash_Flow.NAME, method = RequestMethod.GET)
	public @ResponseBody String searchCashFLow(HttpServletRequest request, Page page, @Secure Principal principal,
			@ModelAttribute CashFlowConditionModel cashFlowConditionModel) {

		try {
			logger.info("query cash flow begin, Params: cashFlowConditionModel[" + cashFlowConditionModel
					+ "].");
			
			List<AppArriveRecord> appArriveRecordList = listCashFLowWithPageable(
					request, page, principal, cashFlowConditionModel);
			
			int count = appArriveRecordService.countAppArriveRecodeBy(cashFlowConditionModel);
			Map<String,Object> rtnMap = new HashMap<String,Object>();
			
			String appArriveRecordListJson = JSON.toJSONString(
					appArriveRecordList,
					SerializerFeature.DisableCircularReferenceDetect);
			
			rtnMap.put("list", appArriveRecordListJson);
			rtnMap.put("size", count);
			rtnMap.put("everyPageSize",page.getEveryPage());
			return jsonViewResolver.sucJsonResult(rtnMap);
			
		} catch (Exception e) {
		
			int errCode = ExceptionUtils.getErrorCodeFromException(e);
			
			logger.error("#query cash flow occur error,  error code[" + errCode +" ]");
			
			e.printStackTrace();
			
			return jsonViewResolver.errorJsonResult("失败");
		} 

	}
	private ModelAndView listCashFLow(HttpServletRequest request, Page page, @Secure Principal principal, CashFlowConditionModel cashFlowConditionModel) {
		try {
			
			logger.info("Controller list CashFLow begin, Params: cashFlowConditionModel[" + cashFlowConditionModel
					+ "].");
			
			int count = appArriveRecordService.countAppArriveRecodeBy(cashFlowConditionModel);
			Map<String,String> accountCharts = new HashMap<String,String>(){{put("test","test");}};
			
			return listAppArriveRecord(
					null, cashFlowConditionModel,
					request, new ArrayList<App>(), new ArrayList<Account>(),count,JsonUtils.toJsonString(accountCharts));
		} catch (Exception  e) {
			
			int errCode = ExceptionUtils.getErrorCodeFromException(e);
			
			//String errMsg = MessageTable4Wellsfargo.getMessage(errCode);
			
			logger.error("#list CashFLow occur error,  error code[" + errCode + "]");
			
			e.printStackTrace();
			
			return pageViewResolver.errorSpec("失败");
		}
	}
	private ModelAndView listAppArriveRecord(List<AppArriveRecord> appArriveRecordList, CashFlowConditionModel cashFlowConditionModel,
			HttpServletRequest request, List<App> appList, List<Account> accountList,int count,String chartofAccountStr) {
		
		String viewName = AccountSide.CREDIT.getName().equalsIgnoreCase(cashFlowConditionModel.getAccountSide()) ? I_List_Cash_Flow.CREDIT_VIEW_NAME : I_List_Cash_Flow.DEBIT_VIEW_NAME;
		ModelAndView modelAndView = new ModelAndView(viewName);
		modelAndView.addObject(ListAppArriveRecord.APP_ARRIVE_RECORD_LIST, appArriveRecordList);
		modelAndView.addObject(ListAppArriveRecord.CASH_FLOW_CONDITION_MODEL, cashFlowConditionModel);
		modelAndView.addObject(ListAppArriveRecord.AUDIT_STATUS_LIST, AuditStatus.values());
		modelAndView.addObject(ListAppArriveRecord.APP_LIST, appList);
		modelAndView.addObject(ListAppArriveRecord.ACCOUNT_LIST, accountList);
		modelAndView.addObject(ListAppArriveRecord.AUDIT_STATUS_LIST, AuditStatus.values());
		modelAndView.addObject(ListAppArriveRecord.SIZE, count);
		modelAndView.addObject(ListAppArriveRecord.CHART_OF_ACCOUNT, chartofAccountStr);
		
		return modelAndView;
	}
	
	private List<AppArriveRecord> listCashFLowWithPageable(HttpServletRequest request, Page page, Principal principal,
			CashFlowConditionModel cashFlowConditionModel){

		List<AppArriveRecord> appArriveRecordList = appArriveRecordService.listAppArriveRecodBy(cashFlowConditionModel,
				page.getBeginIndex(), page.getMaxResultRecords());
		page.calculate(appArriveRecordList.size());
		List<AppArriveRecord> pagedAppArriveRecordList = appArriveRecordList.size() > page.getEveryPage()
				? appArriveRecordList.subList(0, page.getEveryPage()) : appArriveRecordList;
		return pagedAppArriveRecordList;
	}

	@RequestMapping(value = I_List_Bill_Match_Result.NAME, method = RequestMethod.GET)
	public @ResponseBody String listBillMatchResult(HttpServletRequest request, @Secure Principal principal,
			@PathVariable(I_List_Bill_Match_Result.PARAM_CASH_FLOW_UUID) String cashFlowUuid,
			@RequestParam(I_List_Bill_Match_Result.PARAM_ACCOUNT_SIDE) String accountSide
			) {
		try {
			List<BillMatchResult> billMatchResultList = cashFlowCacheHandler.getBillMatchResultList(cashFlowUuid, AccountSide.fromName(accountSide));
			return jsonViewResolver.sucJsonResult(I_Monitor_Cash_Flow.RNT_BILL_MATCH_RESULT_LIST,
					billMatchResultList);
		} catch (Exception e) {
			logger.error("listBillMatchResult occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(GlobalMsgSpec.GeneralErrorMsg.MSG_SYSTEM_ERROR);
		}
	}

	@RequestMapping(value =I_Voucher_Change.NAME, method = RequestMethod.POST)
	public @ResponseBody String changeVoucher(HttpServletRequest request, @Secure Principal principal,
			@RequestParam(I_Voucher_Change.PARAM_BILL_MATCH_RESULT_LIST) String jsonBillMatchResultList,
			@RequestParam(I_Voucher_Change.PARAM_CASH_FLOW_UUID) String cashFlowUuid,
			@RequestParam(I_Voucher_Change.PARAM_APP_ID) String appId,
			@RequestParam(I_Voucher_Change.PARAM_ACCOUNT_SIDE) String accountSide) {
		try {
			logger.info("ChangeVoucher with params: cashFlowUuid[" + cashFlowUuid + "],appId[" + appId
					+ "],jsonBillMatchResultList[" + jsonBillMatchResultList + "].accountSide["+accountSide+"]");
			List<BillMatchResult> billMatchResultList = JsonUtils.parseArray(jsonBillMatchResultList,
					BillMatchResult.class);
			Long companyId = companyService.getCompanyIdBy(appId);
			logger.info("companyId is " + companyId + ".");
			AppArriveRecord appArriveRecord = appArriveRecordService.getArriveRecordByCashFlowUuid(cashFlowUuid);
			Set<String> duplicatedBillUuids = cashFlowHandler.checkJVAndBill(billMatchResultList, companyId,
					cashFlowUuid);
			if (!CollectionUtils.isEmpty(duplicatedBillUuids)) {
				return jsonViewResolver.jsonResult(GlobalCodeSpec.GeneralErrorCode.ERROR_BAD_REQUEST,
						ErrorMsg4Audit.REPEAT_BILL, I_Voucher_Change.RTN_DUPLICATED_BUSINESS_NO, duplicatedBillUuids);
			}
			VoucherOperationInfo operatorInfo = new VoucherOperationInfo(principal.getId(), IpUtil.getIpAddress(request));
			AuditStatus auditStatus = cashFlowHandler.changeVouchers(billMatchResultList, companyId, cashFlowUuid, AccountSide.fromName(accountSide), afterVoucherIssuedHandler);
			logger.info("auditStatus is " + auditStatus + ".");
			systemOperateLogHandler.generateCashFLowAuditSystemLog(operatorInfo, appArriveRecord);
			
			return jsonViewResolver.sucJsonResult(I_Voucher_Change.RTN_AUDIT_STATUS, auditStatus);

		} catch (NoAuthorityException nae) {
			logger.error("changeVoucher occur NoAuthorityException.");
			nae.printStackTrace();
			return jsonViewResolver.errorJsonResult(GlobalMsgSpec.GeneralErrorMsg.MSG_NO_AUTHORITY);
		} catch (NoDataInDBException noDataException) {
			logger.error("changeVoucher occur NoDataInDBException");
			noDataException.printStackTrace();
			return jsonViewResolver.errorJsonResult(GlobalMsgSpec.GeneralErrorMsg.MSG_NO_OBJECT_IN_DB);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(GlobalMsgSpec.GeneralErrorMsg.MSG_SYSTEM_ERROR);
		}
	}
	
	//need to pass account side 
	@RequestMapping(value = I_Close_CashFlow_Audit.NAME, method = RequestMethod.POST)
	public @ResponseBody String closeCashFlowAudit(@RequestParam(I_Close_CashFlow_Audit.PARAM_CASH_FLOW_UUID) String cashFlowUuid,@RequestParam(I_Close_CashFlow_Audit.PARAM_ACCOUNT_SIDE) String accountSideStr,
			HttpServletRequest request, @Secure Principal principal) {
		try {
			logger.info("Controller closeDebitAudit begin with params:cashFlowUuid[" + cashFlowUuid + "].");
			if (StringUtils.isEmpty(cashFlowUuid)) {
				return jsonViewResolver.errorJsonResult(GlobalMsgSpec.GeneralErrorMsg.MSG_ILLEGAL_PARAM);
			}
			VoucherOperationInfo operatorInfo = new VoucherOperationInfo(principal.getId(), IpUtil.getIpAddress(request));
			
			cashFlowHandler.lapseIssuedAndDebitVouchersAndUpdateStatus(cashFlowUuid, operatorInfo, AccountSide.fromName(accountSideStr));
			
			AppArriveRecord appArriveRecord = appArriveRecordService.getArriveRecordByCashFlowUuid(cashFlowUuid);
			systemOperateLogHandler.generateCashFLowAuditSystemLog(operatorInfo, appArriveRecord);
			logger.info("lapse issuedJV and update status end. Success.");
			return jsonViewResolver.sucJsonResult();
		} catch (Exception e) {
			logger.error("closeDebitAudit occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(GlobalMsgSpec.GeneralErrorMsg.MSG_SYSTEM_ERROR);
		}
	}
	//need to pass accountsie
	@RequestMapping(value = I_Show_CashFlow_Detail.NAME, method = RequestMethod.GET)
	public @ResponseBody String showCashFlowDetail(@RequestParam(I_Show_CashFlow_Detail.PARAM_CASH_FLOW_UUID) String cashFlowUuid,@RequestParam(I_Close_CashFlow_Audit.PARAM_ACCOUNT_SIDE) String accountSideStr,
			HttpServletRequest request, @Secure Principal principal) {
		try {
			if (StringUtils.isEmpty(cashFlowUuid)) {
				return jsonViewResolver.errorJsonResult(GlobalMsgSpec.GeneralErrorMsg.MSG_ILLEGAL_PARAM);
			}
			AppArriveRecord appArriveRecord = appArriveRecordService.getArriveRecordByCashFlowUuid(cashFlowUuid);
			if (appArriveRecord == null) {
				return jsonViewResolver.errorJsonResult(GlobalMsgSpec.GeneralErrorMsg.MSG_NO_DATA);
			}
			Long companyId = companyService.getCompanyIdBy(appArriveRecord.getApp());

			Account account = accountService.getAccountByAccountNo(appArriveRecord.getReceiveAcNo());
			CashFlowDetail cashFlowDetail = new CashFlowDetail(appArriveRecord,account);
			List<BillMatchResult> billMatchResultList = cashFlowHandler.getMatchedBillInDbBy(cashFlowUuid, AccountSide.fromName(accountSideStr));
			cashFlowDetail.addRelatedNo(billMatchResultList);

			Map<String, Object> parms = new HashMap<String, Object>();
			
			parms.put(I_Show_CashFlow_Detail.RTN_CASH_FLOW_DETAIL, cashFlowDetail);
			parms.put(I_Show_CashFlow_Detail.RTN_AUDIT_LOG, new ArrayList<>());

			return jsonViewResolver.sucJsonResult(parms);

		} catch (Exception e) {
			logger.error("showCashFlowDetail occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(GlobalMsgSpec.GeneralErrorMsg.MSG_SYSTEM_ERROR);
		}
	}
	
	
	//需要上传accountSide
	@RequestMapping(value = I_Query_BillingPlan_For_Debit.NAME, method = RequestMethod.GET)
	public @ResponseBody String queryDebitBillingPlan(@RequestParam(I_Query_BillingPlan_For_Debit.PARAM_APP_ID) String appId,@RequestParam(I_Query_BillingPlan_For_Debit.PARAM_ACCOUNT_SIDE) String accountSideStr,
			@RequestParam("billingPlanUuid") String billingPlanUuid) {
		try {

//			billingPlanQueryModel.setAccountSide(AccountSide.DEBIT);
			Long companyId = companyService.getCompanyIdBy(appId);
			if (StringUtils.isEmpty(billingPlanUuid)) {
				logger.info("billingPlanUuid is empty. return.");
				return jsonViewResolver.errorJsonResult(GlobalCodeSpec.GeneralErrorCode.ERROR_ILLEGAL_PARAM,
						GeneralErrorMsg.MSG_ILLEGAL_PARAM);
			}
			Order order = orderService.getOrderByRepaymentBillId(billingPlanUuid);
			if (order==null) {
				return jsonViewResolver.errorJsonResult(GeneralErrorMsg.MSG_NO_DATA);
			}
			List<Order> orderList = new ArrayList<Order>();
			orderList.add(order);
			List<BillMatchResult> billMatchResultList = cashFlowHandler
					.generateDebitBillMatchResultWithSettlingAmountFrom(orderList, companyId, AccountSide.fromName(accountSideStr));

			return jsonViewResolver.sucJsonResult(I_Query_BillingPlan_For_Debit.RNT_BILL_MATCH_RESULT_LIST, billMatchResultList);
		} catch (Exception e) {
			logger.error("queryDebitBillingPlan occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(GlobalMsgSpec.GeneralErrorMsg.MSG_SYSTEM_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/query-by-leasing-contract",method = RequestMethod.GET)
	public @ResponseBody String queryBillingPlansByLeasingContractUuid(
			@RequestParam("appId") String appId,
			@RequestParam(value="businessContractUuid") long contractId
			){
		
		try {
			
			logger.info("queryBillingPlansByContractUuid with params:leasingContractUuid["+contractId+"]");
			
			Long companyId = companyService.getCompanyIdBy(appId);
			
			List<Order> orderList = orderService.getUnclearedRepaymentOrder(contractId);
			List<BillingPlan> billingPlanList = orderHandler.convertOrderToBillingPlan(orderList);

			return jsonViewResolver.sucJsonResult("list", billingPlanList);
			
		} catch (Exception e){
			
			logger.error("queryBillingPlansBySubjectMatterUuid error.");
			
			e.printStackTrace();
			
			return jsonViewResolver.errorJsonResult(GlobalMsgSpec.GeneralErrorMsg.MSG_SYSTEM_ERROR);
		}
	}
}