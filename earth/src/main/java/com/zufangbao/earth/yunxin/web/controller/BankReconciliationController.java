package com.zufangbao.earth.yunxin.web.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.web.controller.financial.CapitalControllerSpec.URL;
import com.zufangbao.gluon.spec.global.GlobalMsgSpec;
import com.zufangbao.gluon.spec.global.GlobalSpec;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.ledgerbook.AssetCategory;
import com.zufangbao.sun.ledgerbook.AssetConvertor;
import com.zufangbao.sun.service.CashFlowService;
import com.zufangbao.sun.service.CustomerService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.VirtualAccountService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.excel.ExcelUtil;
import com.zufangbao.sun.yunxin.entity.excel.CashFlowExcelVO;
import com.zufangbao.sun.yunxin.entity.model.BankReconciliationQueryModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.CashFlowAutoIssueHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.VirtualAccountHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.webform.CustomerDepositResult;
import com.zufangbao.wellsfargo.yunxin.handler.SourceDocumentHandler;

/**
 * 
 * @author dcc
 *
 */

@Controller()
@RequestMapping(URL.CAPITAL_NAME)
@MenuSetting(URL.CAPITAL_MENU)
public class BankReconciliationController extends BaseController {
	
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private CashFlowService cashFlowService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CashFlowAutoIssueHandler cashFlowAutoIssueHandler;
	@Autowired
	private SourceDocumentHandler sourceDocumentHandler;
	@Autowired
	private SourceDocumentService sourceDocumentService;
	@Autowired
	private VirtualAccountService virtualAccountService;
	@Autowired
	private VirtualAccountHandler virtualAccountHandler;
	
	private static final Log logger = LogFactory.getLog(BankReconciliationController.class);

	@RequestMapping(value = "account-manager/cash-flow-audit/show", method = RequestMethod.GET)
	@MenuSetting("submenu-bank-cash-flow-audit")
	public ModelAndView showBankReconciliation(@ModelAttribute BankReconciliationQueryModel bankReconciliationQueryModel, @Secure Principal principal, Page page, HttpServletRequest request) {
		try {
			List<FinancialContract> financialContracts = financialContractService.loadAll(FinancialContract.class);
			ModelAndView modelAndView = new ModelAndView("capital/account-manage/audit/bank-cash-flow-audit");
			modelAndView.addObject("financialContracts", financialContracts);
			modelAndView.addObject("accountSideList", Arrays.asList(AccountSide.values()));
			modelAndView.addObject("auditStatusList", Arrays.asList(AuditStatus.values()));
			modelAndView.addObject("bankReconciliationQueryModel",bankReconciliationQueryModel);
			return modelAndView;
		} catch(Exception e){
			e.printStackTrace();
		}
		return pageViewResolver.errorSpec();
		
	}

	@RequestMapping("account-manager/cash-flow-audit/query")
	public @ResponseBody String queryBankReconciliation(@Secure Principal principal,
			@ModelAttribute BankReconciliationQueryModel bankReconciliationQueryModel, Page page) {
		try {
			List<CashFlow> cashFlowList = cashFlowService.getCashFlowList(bankReconciliationQueryModel, page);
			int count = cashFlowService.count(bankReconciliationQueryModel);
			Map<String, Object> data = new HashMap<String, Object>();
			data.putIfAbsent("list", cashFlowList);
			data.putIfAbsent("size", count);
			return jsonViewResolver.sucJsonResult(data);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	@RequestMapping("account-manager/cash-flow-audit/export-csv")
	@ResponseBody public String exportCashFlowCSV(@ModelAttribute BankReconciliationQueryModel bankReconciliationQueryModel,HttpServletResponse response) {
		try {
			if(!bankReconciliationQueryModel.valid_4export_csv()){
				return jsonViewResolver.errorJsonResult(bankReconciliationQueryModel.getCheckErrorMsg());
			}
			List<CashFlow> cashFlowList = cashFlowService.getCashFlowList(bankReconciliationQueryModel, null);
			List<CashFlowExcelVO> cashFlowCSV = cashFlowList.stream().map(cashflow -> new CashFlowExcelVO(cashflow)).collect(Collectors.toList());
			ExcelUtil<CashFlowExcelVO> excelUtil = new ExcelUtil<CashFlowExcelVO>(
					CashFlowExcelVO.class);
			List<String> CSVData = excelUtil.exportDatasToCSV(cashFlowCSV);
			Map<String,List<String>> csvs = new HashMap<String,List<String>>();
			String fileName = "银行流水" + "_"+ DateUtils.format(new Date(), "yyyyMMdd_HHmmssSSS");
			csvs.put(fileName, CSVData);
			exportZipToClient(response, fileName+".zip", GlobalSpec.UTF_8, csvs);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(GlobalMsgSpec.GeneralErrorMsg.MSG_SYSTEM_ERROR);
		}
		return jsonViewResolver.sucJsonResult();
		
	}
	
	@RequestMapping("account-manager/cash-flow-audit/show-deposit-result")
	public @ResponseBody String queryDepositResult(String cashFlowUuid) {
		try {
			
			List<SourceDocument> sourceDocumentList = sourceDocumentService.getDepositReceipt(cashFlowUuid);
			List<CustomerDepositResult> depisitResults = sourceDocumentHandler.convertToDepositResult(sourceDocumentList);
			return jsonViewResolver.sucJsonResult("depisitResults",depisitResults);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	@RequestMapping("account-manager/cash-flow-audit/query-virtual-account")
	public @ResponseBody String queryVirtualAccounts(String financialContractUuid, Long customerType, String customerKey,Page page) {
		try {
			List<VirtualAccount> virtualAccountList = virtualAccountService.queryVirtualAccountList(financialContractUuid, customerType, customerKey, page);
			return jsonViewResolver.sucJsonResult("virtualAccountList",virtualAccountList);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	@RequestMapping("account-manager/cash-flow-audit/build-deposit-result")
	public @ResponseBody String buildDepositResult(String virtualAccountUuid) {
		try {
			CustomerDepositResult depisitResult = virtualAccountHandler.buildDeposit(virtualAccountUuid);
			return jsonViewResolver.sucJsonResult("depisitResult",depisitResult);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	@RequestMapping("account-manager/cash-flow-audit/deposit")
	public @ResponseBody String deposit(@ModelAttribute CustomerDepositResult depositResult) {
		try {
			
			CustomerDepositResult depisitResult = new CustomerDepositResult();
			String cashFlowUuid = "";
			BigDecimal bookingAmount = depisitResult.getDepositAmount();
			String customerUuid = "";
			String remark = depositResult.getRemark();
			
			String financialContractUuid = "";
			FinancialContract financialContract = financialContractService.getFinancialContractBy(financialContractUuid);
			Customer customer = customerService.getCustomer(customerUuid);
			CashFlow cashFlow = cashFlowService.getCashFlowByCashFlowUuid(cashFlowUuid);
			AssetCategory assetCategory = AssetConvertor.convertAppDepositAssetCategory();
			cashFlowAutoIssueHandler.charge_cash_into_virtual_account(cashFlow, customer, bookingAmount, financialContract, assetCategory, remark);
			return jsonViewResolver.sucJsonResult("depisitResult",depisitResult);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	@RequestMapping("account-manager/cash-flow-audit/deposit-cancel")
	public @ResponseBody String depositCancel(@ModelAttribute CustomerDepositResult depositResult,
			String cashFlowUuid) {
		try {
			
			String sourceDocumentUuid = depositResult.getSourceDocumentUuid();
			SourceDocument sourceDocument = null;
			
			BigDecimal bookingAmount = depositResult.getDepositAmount();
			String customerUuid = "";
			String financialContractUuid = "";
			FinancialContract financialContract = financialContractService.getFinancialContractBy(financialContractUuid);
			Customer customer = customerService.getCustomer(customerUuid);
			CashFlow cashFlow = cashFlowService.getCashFlowByCashFlowUuid(cashFlowUuid);
			cashFlowAutoIssueHandler.deposit_cancel(sourceDocumentUuid);
			return jsonViewResolver.sucJsonResult("depisitResult",depositResult);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
}
