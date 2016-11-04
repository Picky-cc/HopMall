package com.zufangbao.earth.yunxin.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.gluon.spec.global.GlobalSpec;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.service.TransferApplicationService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.utils.excel.ExcelUtil;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.AuditOverdueStatus;
import com.zufangbao.sun.yunxin.entity.OverDueStatus;
import com.zufangbao.sun.yunxin.entity.PaymentStatus;
import com.zufangbao.sun.yunxin.entity.SettlementOrder;
import com.zufangbao.sun.yunxin.entity.excel.OverDueRepaymentDetailExcelVO;
import com.zufangbao.sun.yunxin.entity.excel.RepaymentManagementExcelVO;
import com.zufangbao.sun.yunxin.entity.excel.RepaymentPlanDetailExcelVO;
import com.zufangbao.sun.yunxin.entity.model.assetset.AssetSetQueryModel;
import com.zufangbao.sun.yunxin.entity.model.assetset.AssetSetShowModel;
import com.zufangbao.sun.yunxin.handler.RepaymentPlanHandler;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.sun.yunxin.service.SettlementOrderService;

/**
 * 
 * @author louguanyang
 *
 */
@Controller
@RequestMapping("/assets")
@MenuSetting("menu-finance")
public class AssetSetController extends BaseController {

	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private PrincipalService principalService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private SettlementOrderService settlementOrderService;
	@Autowired
	private TransferApplicationService transferApplicationService;
	@Autowired
	private ContractAccountService contractAccountService;
	@Autowired
	private AssetPackageService assetPackageService;
	@Autowired
	private FinancialContractService financialContractService;
	
	private static final Log logger = LogFactory.getLog(AssetSetController.class);
	
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	
	@RequestMapping("/{assetId}/detail")
	@MenuSetting("submenu-payment-asset")
	public ModelAndView detail(@PathVariable("assetId") Long assetId) {
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetId);
		ContractAccount contractAccount = contractAccountService
				.getTheEfficientContractAccountBy(assetSet.getContract());
		List<Order> allOrderList = orderService
				.getOrderListByAssetSetId(assetId);
		List<Order> orderList = new ArrayList<>();
		List<Order> guaranteeOrderList = new ArrayList<>();
		for (Order order : allOrderList) {
			if (order.isNormalOrder()) {
				orderList.add(order);
			}
			if (order.isGuaranteeOrder()) {
				guaranteeOrderList.add(order);
			}
		}
		List<SettlementOrder> settlementOrderList = settlementOrderService.getSettlementOrderListBy(assetSet);
		List<TransferApplication> transferApplicationList = transferApplicationService.getRecentDayTransferApplicationList(assetSet);
		ModelAndView modelAndView = new ModelAndView("assets/assets-detail");
		modelAndView.addObject("contractAccount", contractAccount);
		modelAndView.addObject("assetSet", assetSet);
		modelAndView.addObject("orderList", orderList);
		modelAndView.addObject("guaranteeOrderList", guaranteeOrderList);
		modelAndView.addObject("settlementOrderList", settlementOrderList);
		modelAndView.addObject("transferApplicationList", transferApplicationList);
		modelAndView.addObject("overdueStatusList", Arrays.asList(AuditOverdueStatus.values()));
		modelAndView.addObject("assetSetUuid", assetSet.getAssetUuid());
		return modelAndView;
	}

	@RequestMapping(value = "/{assetSetId}/pre-confirm-recycle-date", method = RequestMethod.GET)
	@MenuSetting("submenu-payment-asset")
	public ModelAndView preConfirmPayTime(
			@PathVariable("assetSetId") Long assetSetId) {
		try {
			AssetSet assetSet = repaymentPlanService
					.load(AssetSet.class, assetSetId);
			if (assetSet == null || !assetSet.isClearAssetSet()) {
				return pageViewResolver.pageSpec("error-modal", "message",
						"还款信息不存在或未还款！");
			}
			return pageViewResolver.pageSpec(
					"assets/assets-pre-confirm-recycle-date", "assetSet",
					assetSet);
		} catch (Exception e) {
			e.printStackTrace();
			return pageViewResolver.pageSpec("error-modal", "message", "系统错误");
		}
	}

	@RequestMapping(value = "/{assetId}/confirm-recycle-date", method = RequestMethod.POST)
	@MenuSetting("submenu-payment-asset")
	public @ResponseBody String confirmPayTime(
			@Secure Principal principal,
			@PathVariable("assetId") Long assetId,
			@RequestParam(value = "confirmRecycleDateString") String confirmRecycleDateString,
			@RequestParam("comment") String comment) {
		try {
			AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetId);
			if (assetSet == null || !assetSet.isClearAssetSet()) {
				return jsonViewResolver.errorJsonResult("还款信息不存在或未还款！");
			}
			Date confirmRecyleDate = confirmRecycleDateString == null ? null
					: DateUtils.asDay(confirmRecycleDateString);
			if (confirmRecyleDate == null) {
				return jsonViewResolver.errorJsonResult("请输入确认还款日期！");
			}
			assetSet.setConfirmRecycleDate(confirmRecyleDate);
			assetSet.setComment(comment);
			repaymentPlanService.save(assetSet);
			logger.info("confirm-recycle-date: assetId["+assetId+"],principalName["+principal.getName()+"],confirmRecycleDateString["+confirmRecycleDateString+"],comment["+comment+"].");
			return jsonViewResolver.sucJsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}

	@RequestMapping(value = "/{assetSetId}/pre-update-refund", method = RequestMethod.GET)
	@MenuSetting("submenu-payment-asset")
	public ModelAndView preUpdateRefund(
			@PathVariable("assetSetId") Long assetSetId) {
		try {
			AssetSet assetSet = repaymentPlanService
					.load(AssetSet.class, assetSetId);
			if (assetSet == null || !assetSet.isClearAssetSet()) {
				return pageViewResolver.pageSpec("error-modal", "message",
						"还款信息不存在或未还款！");
			}
			return pageViewResolver.pageSpec("assets/assets-pre-update-refund",
					"assetSet", assetSet);
		} catch (Exception e) {
			e.printStackTrace();
			return pageViewResolver.pageSpec("error-modal", "message", "系统错误");
		}
	}

	@RequestMapping(value = "/{assetId}/update-refund", method = RequestMethod.POST)
	@MenuSetting("submenu-payment-asset")
	public @ResponseBody String updateRefund(@Secure Principal principal,
			@PathVariable("assetId") Long assetId,
			@RequestParam(value = "refundAmount") BigDecimal refundAmount,
			@RequestParam("comment") String comment) {
		try {
			AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetId);
			if (assetSet == null || !assetSet.isClearAssetSet()) {
				return jsonViewResolver.errorJsonResult("还款信息不存在或未还款！");
			}
			if (refundAmount == null) {
				return jsonViewResolver.errorJsonResult("请输入退款金额！");
			}
			assetSet.setRefundAmount(refundAmount);
			assetSet.setComment(comment);
			repaymentPlanService.save(assetSet);
			logger.info("refund: assetId["+assetId+"],principalName["+principal.getName()+"],refundAmount["+refundAmount+"],comment["+comment+"].");
			return jsonViewResolver.sucJsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@MenuSetting("submenu-payment-asset")
	public ModelAndView showRepaymentPlansPage(@Secure Principal principal, Page page, HttpServletRequest request) {
		List<FinancialContract> financialContracts = financialContractService.loadAll(FinancialContract.class);
		ModelAndView modelAndView = new ModelAndView("assets/assets-list");
		modelAndView.addObject("financialContracts", financialContracts);
		modelAndView.addObject("paymentStatusList", Arrays.asList(PaymentStatus.values()));
		modelAndView.addObject("auditOverdueStatusList", Arrays.asList(AuditOverdueStatus.values()));//逾期状态
		modelAndView.addObject("overDueStatusList",  Arrays.asList(OverDueStatus.values()));//差异状态
		return modelAndView;
	}

	@RequestMapping("/query")
	@MenuSetting("submenu-financial-contract")
	public @ResponseBody String queryRepaymentPlans(@Secure Principal principal,
			@ModelAttribute AssetSetQueryModel assetSetQueryModel, Page page) {
		try {
			int size= repaymentPlanHandler.countAssetSetIds(assetSetQueryModel);
			List<AssetSetShowModel> assetSetModelList = repaymentPlanHandler.generateAssetSetModelList(assetSetQueryModel, page);
			Map<String, Object> data = new HashMap<>();
			data.put("assetSetQueryModel", assetSetQueryModel);
			data.put("list", assetSetModelList);
			data.put("size", size);
			return jsonViewResolver.sucJsonResult(data);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	@RequestMapping(value="/{assetSetId}/pre-update-comment",method = RequestMethod.GET)
	@MenuSetting("submenu-payment-asset")
	public ModelAndView preUpdateComment(
			@PathVariable("assetSetId") Long assetSetId) {
		try {
			AssetSet assetSet = repaymentPlanService
					.load(AssetSet.class, assetSetId);
			if (assetSet == null) {
				return pageViewResolver.pageSpec("error-modal", "message",
						"还款信息不存在！");
			}
			return pageViewResolver.pageSpec(
					"assets/assets-pre-update-comment", "assetSet", assetSet);
		} catch (Exception e) {
			e.printStackTrace();
			return pageViewResolver.pageSpec("error-modal", "message", "系统错误");
		}
	}

	@RequestMapping(value = "/{assetId}/update-comment", method = RequestMethod.POST)
	@MenuSetting("submenu-payment-asset")
	public @ResponseBody String updateComment(@Secure Principal principal,@PathVariable("assetId") Long assetId,
			@RequestParam("comment") String comment,
			HttpServletRequest request) {
		try {
			logger.info("update comment: assetId["+assetId+"], principalName["+principal.getName()+"], comment["+comment+"].");
			AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetId);
			if (assetSet == null) {
				return jsonViewResolver.errorJsonResult("还款信息不存在！");
			}
			repaymentPlanHandler.updateCommentAndSaveLog(assetId, principal.getId(), comment, IpUtil.getIpAddress(request));
			return jsonViewResolver.sucJsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}

	@RequestMapping(value = "/{assetId}/confirm-overdue", method = RequestMethod.POST)
	@MenuSetting("submenu-payment-asset")
	public @ResponseBody String confirmOverdue(@Secure Principal principal,@PathVariable("assetId") Long assetId,
			@RequestParam("status") int status, @RequestParam("reason") String reason,
			@RequestParam(value = "overdueDate", required = false, defaultValue = "") String overdueDate,
			HttpServletRequest request) {
		try {
			logger.info("confirm overdue: assetId["+assetId+"], principalName["+principal.getName()+"], status["+status+"], reason["+reason+"], overdueDate["+overdueDate+"].");
			AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetId);
			FinancialContract financialContract = assetPackageService.getFinancialContract(assetSet.getContract());
			String errorMsg = checkParamReturnMsg(status, overdueDate, assetSet, financialContract);
			if(!StringUtils.isEmpty(errorMsg)) {
				return jsonViewResolver.errorJsonResult(errorMsg); 
			}
			repaymentPlanHandler.updateOverdueStatusAndSaveLog(assetId, principal.getId(), AuditOverdueStatus.fromValue(status), reason, overdueDate, IpUtil.getIpAddress(request));
			return jsonViewResolver.sucJsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}

	@RequestMapping(value = "exprot-repayment-plan-detail", method = RequestMethod.GET)
	@MenuSetting("submenu-assets-contract")
	public String exportRepaymentPlanDetail(HttpServletRequest request,
			@Secure Principal principal,
			@ModelAttribute AssetSetQueryModel assetSetQueryModel,
			HttpServletResponse response) {
		try {
			long start = System.currentTimeMillis();
			//默认导出excel只导出有效的还款单，若要按页面筛选，则删去。
			assetSetQueryModel.setActiveStatus(AssetSetActiveStatus.OPEN.ordinal());
			
			List<RepaymentPlanDetailExcelVO> repaymentPlanDetailExcelVOs = repaymentPlanHandler.get_repayment_plan_Detail_excel(assetSetQueryModel);
			
			ExcelUtil<RepaymentPlanDetailExcelVO> excelUtil = new ExcelUtil<RepaymentPlanDetailExcelVO>(RepaymentPlanDetailExcelVO.class);
			List<String> csvData = excelUtil.exportDatasToCSV(repaymentPlanDetailExcelVOs);
			
			Map<String, List<String>> csvs = new HashMap<String, List<String>>();
			csvs.put("还款计划明细汇总表", csvData);
			exportZipToClient(response, create_repaymnet_plan_summary_file_name("zip"), GlobalSpec.UTF_8, csvs);
			logger.info("#exportRepaymentPlanDetail end. used ["+(System.currentTimeMillis() - start)+"]ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "exprot-overDue-repayment-plan-detail", method = RequestMethod.GET)
	@MenuSetting("submenu-assets-contract")
	public String exportOverDueRepaymentPlan(HttpServletRequest request,
			@Secure Principal principal,
			@ModelAttribute AssetSetQueryModel assetSetQueryModel,
			HttpServletResponse response) {
		try {
			long start = System.currentTimeMillis();
			//默认导出excel只导出有效的还款单，若要按页面筛选，则删去。
			assetSetQueryModel.setActiveStatus(AssetSetActiveStatus.OPEN.ordinal());
			
			List<OverDueRepaymentDetailExcelVO> overDueRepaymentDetailExcelVOs = repaymentPlanHandler.get_overdue_than_over_due_start_day_repayment_detail_excel(assetSetQueryModel);
			
			ExcelUtil<OverDueRepaymentDetailExcelVO> excelUtil = new ExcelUtil<OverDueRepaymentDetailExcelVO>(OverDueRepaymentDetailExcelVO.class);
			List<String> csvData = excelUtil.exportDatasToCSV(overDueRepaymentDetailExcelVOs);
			
			Map<String, List<String>> csvs = new HashMap<String, List<String>>();
			csvs.put("逾期还款明细表", csvData);
			exportZipToClient(response,  create_overDue_download_file_name("zip"), GlobalSpec.UTF_8, csvs);
			logger.info("#exportOverDueRepaymentPlan end. used ["+(System.currentTimeMillis() - start)+"]ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "exprot-repayment-management", method = RequestMethod.GET)
	@MenuSetting("submenu-assets-contract")
	public String exportRepaymentManagement(HttpServletRequest request,
			@Secure Principal principal,
			@ModelAttribute AssetSetQueryModel assetSetQueryModel,
			HttpServletResponse response) {
		try {
			//默认导出excel只导出有效的还款单，若要按页面筛选，则删去。
			long start = System.currentTimeMillis();
			assetSetQueryModel.setActiveStatus(AssetSetActiveStatus.OPEN.ordinal());
			List<AssetSetShowModel> assetSetModelList = repaymentPlanHandler.generateAssetSetModelList(assetSetQueryModel, null);
			
			List<RepaymentManagementExcelVO> excelVOs = assetSetModelList.stream().map(assetSet -> new RepaymentManagementExcelVO(assetSet)).collect(Collectors.toList());
			
			ExcelUtil<RepaymentManagementExcelVO> excelUtil = new ExcelUtil<RepaymentManagementExcelVO>(RepaymentManagementExcelVO.class);
			List<String> csvData = excelUtil.exportDatasToCSV(excelVOs);

			Map<String, List<String>> csvs = new HashMap<String, List<String>>();
			csvs.put("还款管理表", csvData);
			exportZipToClient(response, cretae_repayment_management_file_name("zip"), GlobalSpec.UTF_8, csvs);
			logger.info("#exportRepaymentManagement end. used ["+(System.currentTimeMillis() - start)+"]ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String create_overDue_download_file_name(String format) {
		return  "逾期还款明细表" +"_"+ DateUtils.format(new Date(), "yyyyMMdd_HHmmssSSS")+ "."+format;
	}
	private String cretae_repayment_management_file_name(String format) {
		return  "还款管理表" +"_"+ DateUtils.format(new Date(), "yyyyMMdd_HHmmssSSS")+ "."+format;
	}


	private String create_repaymnet_plan_summary_file_name(String format) {
		return  "还款计划明细汇总表" + "_"+ DateUtils.format(new Date(), "yyyyMMdd_HHmmssSSS") +"."+format;
	}

	private String checkParamReturnMsg(int status, String overdueDate, AssetSet assetSet, FinancialContract financialContract) {
		if(assetSet == null){
			return "还款信息不存在！"; 
		}
		if (assetSet.getOverdueStatus() == AuditOverdueStatus.OVERDUE) {
			return "该还款计划已逾期";
		}
		AuditOverdueStatus overdueStatus = AuditOverdueStatus.fromValue(status);
		if (overdueStatus == null) {
			return "请选择逾期状态";
		}
		if(overdueStatus == AuditOverdueStatus.OVERDUE) {
			if(StringUtils.isEmpty(overdueDate)) {
				return "请选择逾期日期";
			}
			Date inputOverdueDate = DateUtils.asDay(overdueDate);
			
			Date overdueStartDate = assetSet.getOverdueStartDate(financialContract);
			if(inputOverdueDate.before(overdueStartDate)) {
				return "逾期日期应不早于" + DateUtils.format(overdueStartDate);
			}
			Date actualRecycleDate = DateUtils.asDay(new Date());
			if(assetSet.isClearAssetSet()) {
				actualRecycleDate = assetSet.getActualRecycleDate();
			}
			if(inputOverdueDate.after(actualRecycleDate)) {
				return "逾期日期应不晚于" + DateUtils.format(actualRecycleDate);
			}
		}
		return "";
	}

}
