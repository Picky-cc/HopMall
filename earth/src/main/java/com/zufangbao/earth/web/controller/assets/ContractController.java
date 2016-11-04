package com.zufangbao.earth.web.controller.assets;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo2do.core.web.interceptor.InfoMessage;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.web.controller.reapymentInfo.BankCoreCodeMapSpec;
import com.zufangbao.gluon.spec.global.GlobalSpec;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.excel.ExcelUtil;
import com.zufangbao.sun.yunxin.ContractState;
import com.zufangbao.sun.yunxin.entity.AssetClearStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.OnAccountStatus;
import com.zufangbao.sun.yunxin.entity.excel.LoanContractDetailCheckExcelVO;
import com.zufangbao.sun.yunxin.entity.model.ContractQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceApplication;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlan;
import com.zufangbao.sun.yunxin.handler.ContractHandler;
import com.zufangbao.sun.yunxin.handler.RepaymentPlanHandler;
import com.zufangbao.sun.yunxin.service.remittance.IRemittanceApplicationService;
import com.zufangbao.sun.yunxin.service.remittance.IRemittancePlanService;

/**
 * @author Downpour
 */
@Controller
@RequestMapping("/contracts")
@MenuSetting("menu-data")
public class ContractController extends BaseController{

	@Autowired
	private ContractService contractService;
	@Autowired
	private ContractAccountService contractAccountService;
	@Autowired
	private PrincipalService principalService;
	@Autowired
	private AssetPackageService assetPackageService;
	@Autowired
	private ContractHandler contractHandler;
	@Autowired
	public FinancialContractService financialContractService;
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	@Autowired
	private IRemittanceApplicationService remittanceApplicationService;
	@Autowired
	private IRemittancePlanService iRemittancePlanService;

	private static final Log logger = LogFactory.getLog(ContractController.class);

	/**
	 * 已还本金returnedPrincipal和未还本金planningPrincipal
	 * 已还利息returnedInterest和未还利息planningInterest
	 * 已还期数returnedPeriod和未还期数planningPeriod
	 * 已还 本金+利息 returnedPrincipalAndInterest
	 * 未还 本金+利息 planningPrincipalAndInterest
	 */
	private Map<String, Object> getReturnedAndPlanedField(List<AssetSet> assetSets) {
		BigDecimal returnedPrincipal = new BigDecimal("0.00");
		BigDecimal planningPrincipal = new BigDecimal("0.00");
	
		BigDecimal returnedInterest = new BigDecimal("0.00");
		BigDecimal planningInterest = new BigDecimal("0.00");
	
		int returnedPeriod = 0;
	
		for (AssetSet assetSet : assetSets) {
			if (assetSet.getAssetStatus() == AssetClearStatus.CLEAR
					&& assetSet.getOnAccountStatus() == OnAccountStatus.WRITE_OFF) {
				returnedPrincipal = returnedPrincipal.add(assetSet.getAssetPrincipalValue());
				returnedInterest = returnedInterest.add(assetSet.getAssetInterestValue());
				returnedPeriod++;
			}else{
				planningPrincipal = planningPrincipal.add(assetSet.getAssetPrincipalValue());
				planningInterest = planningInterest.add(assetSet.getAssetInterestValue());
			}
		}
	
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("returnedPrincipal", returnedPrincipal);
		result.put("planningPrincipal", planningPrincipal);
		result.put("returnedInterest", returnedInterest);
		result.put("planningInterest", planningInterest);
		result.put("returnedPeriod", returnedPeriod);
		result.put("planningPeriod", assetSets.size() - returnedPeriod);
		result.put("returnedPrincipalAndInterest", returnedPrincipal.add(returnedInterest));
		result.put("planningPrincipalAndInterest", planningPrincipal.add(planningInterest));
		return result;
	}

	@RequestMapping("/detail")
	@MenuSetting("submenu-assets-contract")
	public ModelAndView showContractDetail(@RequestParam(value="id", required=false) Long contractId, 
			@RequestParam(value="uid", required=false) String uniqueId) {
		try {
			Contract contract = null;
			List<RemittanceApplication> remittanceApplications = null;
			ContractAccount contractAccount = null;
			FinancialContract financialContract = null;
			List<AssetSet> assetSetList = null;
			Map<String, Object> fields = null;
			
			if(contractId !=null){
				contract = contractService.getContract(contractId);
			}else if( StringUtils.isNotEmpty(uniqueId) ){
				contract = contractService.getContractByUniqueId(uniqueId);
			}
			if(contract != null) {
				contractAccount = contractAccountService.getTheEfficientContractAccountBy(contract);
				financialContract = assetPackageService.getFinancialContract(contract);
				assetSetList = repaymentPlanHandler.getEffectiveRepaymentPlansByContract(contract);
				fields = this.getReturnedAndPlanedField(assetSetList);
				uniqueId = StringUtils.isNotEmpty(uniqueId) ? uniqueId : contract.getUniqueId();
			}
			remittanceApplications = remittanceApplicationService.getRemittanceApplicationsBy(uniqueId);

			return create_detail_model_and_view(contract, contractAccount, financialContract, assetSetList, fields, remittanceApplications);
		} catch (Exception e) {
			logger.error("#showContractDetail  occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	private ModelAndView create_detail_model_and_view(Contract contract, ContractAccount contractAccount,
			FinancialContract financialContract, List<AssetSet> assetSetList, Map<String, Object> fields,
			List<RemittanceApplication> remittanceApplications) {
		ModelAndView modelAndView = new ModelAndView("contract/contract-detail");
		BigDecimal raTotalAmount = BigDecimal.ZERO;            // 放款总额
		BigDecimal raPaidAmount = BigDecimal.ZERO;             // 已放
//		BigDecimal raPaidAmountRate = BigDecimal.ZERO;         // 已放所占比例
		BigDecimal raNotPaidAmount = BigDecimal.ZERO;          // 未放
		BigDecimal totalAssetPrincipalValue = BigDecimal.ZERO; // 本金合计
		BigDecimal totalAssetInterestValue = BigDecimal.ZERO;  // 利息合计
		BigDecimal totalOtherFee = BigDecimal.ZERO;            // 其他费用合计
		BigDecimal total = BigDecimal.ZERO;                    // 总计 以上和
		
		if(CollectionUtils.isNotEmpty(remittanceApplications)){
			for (RemittanceApplication remittanceApplication : remittanceApplications) {
				raTotalAmount = raTotalAmount.add(remittanceApplication.getPlannedTotalAmount());
				raPaidAmount = raPaidAmount.add(remittanceApplication.getActualTotalAmount());
			}
//			raPaidAmountRate = raTotalAmount.compareTo(BigDecimal.ZERO)==0 ? BigDecimal.ONE: raPaidAmount.divide(raTotalAmount);
			raNotPaidAmount = raTotalAmount.subtract(raPaidAmount);
		}
		if(assetSetList != null) {
			int canInvalidateCount = 0;
			for (AssetSet sssetSet : assetSetList) {
				if( new Date().after(sssetSet.getAssetRecycleDate()) || sssetSet.getAssetStatus() == AssetClearStatus.CLEAR ){
					canInvalidateCount ++;
				}
				totalAssetPrincipalValue = totalAssetPrincipalValue.add(sssetSet.getAssetPrincipalValue());
				totalAssetInterestValue = totalAssetInterestValue.add(sssetSet.getAssetInterestValue());
			}
			total = totalAssetPrincipalValue.add(totalAssetInterestValue).add(totalOtherFee);
			modelAndView.addObject("canInvalidate", (canInvalidateCount==0) && (contract.getState() == ContractState.EFFECTIVE) );
		}
		modelAndView.addObject("assetSetList", assetSetList);
		modelAndView.addObject("contract", contract);
		modelAndView.addObject("contractAccount", contractAccount);
		modelAndView.addObject("financialContract", financialContract);
		modelAndView.addObject("fields", fields);
		modelAndView.addObject("coreBanks",BankCoreCodeMapSpec.coreBankMap);
		modelAndView.addObject("remittanceApplications", remittanceApplications);
		modelAndView.addObject("raTotalAmount", raTotalAmount);
		modelAndView.addObject("raPaidAmount", raPaidAmount);
		modelAndView.addObject("raNotPaidAmount", raNotPaidAmount);
//		modelAndView.addObject("raPaidAmountRate", raPaidAmountRate);
		modelAndView.addObject("totalAssetPrincipalValue", totalAssetPrincipalValue);
		modelAndView.addObject("totalAssetInterestValue", totalAssetInterestValue);
		modelAndView.addObject("totalOtherFee", totalOtherFee);
		modelAndView.addObject("total", total);
		return modelAndView;
	}
	
	@RequestMapping(value="/detail/planlist")
	@MenuSetting("submenu-assets-contract")
	public @ResponseBody String getRemittancePlanList(@RequestParam(value="remittanceApplicationUuid") String remittanceApplicationUuid){
		try{
			List<RemittancePlan> remittancePlans = iRemittancePlanService.getRemittancePlanListBy(remittanceApplicationUuid);
			return jsonViewResolver.sucJsonResult("remittancePlans", remittancePlans, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteNullStringAsEmpty);
		}catch (Exception e){
			logger.error("#getRemittancePlanList# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("获取列表错误");
		}
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@MenuSetting("submenu-assets-contract")
	public ModelAndView showContractsPage(HttpServletRequest request,
			@Secure Principal principal,
			@ModelAttribute ContractQueryModel contractQueryModel, Page page) {
		try {
			ModelAndView modelAndView = new ModelAndView("contract/contract-list");
			List<FinancialContract> financialContracts = financialContractService.loadAll(FinancialContract.class);
			modelAndView.addObject("financialContracts", financialContracts);
			List<App> appList = principalService.get_can_access_app_list(principal);
			modelAndView.addObject("appList", appList);
			modelAndView.addObject("contractStates", ContractState.values());
			modelAndView.addObject("contractQueryModel",contractQueryModel);
			return modelAndView;
		} catch (Exception e) {
			logger.error("#showContractsPage occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@MenuSetting("submenu-assets-contract")
	public @ResponseBody String searchContracts(
			@ModelAttribute ContractQueryModel queryModel, Page page) {
		try {
			queryAndSetFinancialContractsMap(queryModel);
			
			int size = contractService.queryContractCountBy(queryModel);
			List<Contract> contractInfos = contractService.queryContractListBy(queryModel, page);
			Map<String, Object> data = new HashMap<>();
			data.put("list", contractInfos);
			data.put("size", size);
			
			return jsonViewResolver.sucJsonResult(data, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}

	@RequestMapping(value = "exprot-loan-contract", method = RequestMethod.GET)
	@MenuSetting("submenu-assets-contract")
	public String exportLoanContract(HttpServletRequest request,
			@ModelAttribute ContractQueryModel queryModel,
			HttpServletResponse response) {
		try {
			long start = System.currentTimeMillis();
			queryAndSetFinancialContractsMap(queryModel);
			
			List<LoanContractDetailCheckExcelVO> loanContractDetailCheckExcelVOs = contractHandler.getLoanContractExcelVO(queryModel);
			
			ExcelUtil<LoanContractDetailCheckExcelVO> excelUtil = new ExcelUtil<LoanContractDetailCheckExcelVO>(LoanContractDetailCheckExcelVO.class);
			List<String> csvData = excelUtil.exportDatasToCSV(loanContractDetailCheckExcelVOs);
			
			Map<String, List<String>> csvs = new HashMap<String, List<String>>();
			csvs.put("贷款信息表", csvData);
			exportZipToClient(response, create_download_file_name(), GlobalSpec.UTF_8, csvs);
			logger.info("#exportLoanContract end. used ["+(System.currentTimeMillis()-start)+"]ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void queryAndSetFinancialContractsMap(ContractQueryModel queryModel) {
		List<FinancialContract> financialContracts = financialContractService.getFinancialContractsByIds(queryModel.getFinancialContractIdList());
		Map<String, FinancialContract> financialContractsMap = financialContracts.stream().collect(Collectors.toMap(FinancialContract::getFinancialContractUuid, fc -> fc));
		queryModel.setFinancialContractsMap(financialContractsMap);
	}

	private String create_download_file_name() {
		return "贷款信息表" +"_"+DateUtils.format(new Date(), "yyyyMMdd_HHmmssSSS") +".zip";
	}

	@RequestMapping(value = "/add-success")
	@InfoMessage("info.contract.create.success")
	public String addSuccess() {
		return "redirect:/contracts";
	}

	@RequestMapping(value = "/invalidate", method = RequestMethod.POST)
	@MenuSetting("submenu-assets-contract")
	public @ResponseBody String invalidate(HttpServletRequest request,
			@Secure Principal principal,
			@RequestParam(value = "contractId") long contractId,
			@RequestParam(value = "comment", required = false) String comment){
		try {
			Contract contract = contractService.getContract(contractId);
			if(contract == null) {
				return jsonViewResolver.errorJsonResult("贷款合同不存在！");
			}
			
			contractHandler.invalidateContract(contract, principal.getId(), principal.getName(), comment, IpUtil.getIpAddress(request));
			return jsonViewResolver.sucJsonResult();
		} catch (RuntimeException e) {
			logger.error("#invalidate  occur error." + e.getMessage());
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(e.getMessage());
		} catch (Exception e) {
			logger.error("#invalidate  occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
}
