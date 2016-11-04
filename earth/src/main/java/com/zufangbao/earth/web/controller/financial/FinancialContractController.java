package com.zufangbao.earth.web.controller.financial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo2do.core.entity.Result;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.yunxin.handler.FinancialContractHandler;
import com.zufangbao.earth.yunxin.handler.SystemOperateLogHandler;
import com.zufangbao.gluon.spec.global.GlobalCodeSpec;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.company.finance.FinanceCompany;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.FinancialContractType;
import com.zufangbao.sun.entity.financial.PaymentChannel;
import com.zufangbao.sun.service.AppService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinanceCompanyService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.PaymentChannelService;
import com.zufangbao.sun.yunxin.entity.AssetPackageFormat;
import com.zufangbao.sun.yunxin.entity.model.CreateFinancialContractModel;
import com.zufangbao.sun.yunxin.entity.model.financialcontract.FinancialContractQueryModel;
import com.zufangbao.sun.yunxin.log.FinancialContractLog;
import com.zufangbao.sun.yunxin.log.LogFunctionType;
import com.zufangbao.sun.yunxin.log.LogOperateType;
import com.zufangbao.sun.yunxin.log.SystemOperateLogRequestParam;

@RestController
@RequestMapping("/financialContract")
@MenuSetting("menu-financial")
public class FinancialContractController extends BaseController {

	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private AppService appService;
	@Autowired
	private PaymentChannelService paymentChannelService;
	@Autowired
	private FinanceCompanyService financeCompanyService;
	@Autowired
	private FinancialContractHandler financialContractHandler;
	@Autowired
	private PrincipalService principalService;
	@Autowired
	private SystemOperateLogHandler systemOperateLogHandler;
	@Autowired
	private ContractService contractService;
	
	private static final Log logger = LogFactory.getLog(FinancialContractController.class);

	@RequestMapping("")
	@MenuSetting("submenu-financial-contract")
	public ModelAndView loadAll(@Secure Principal principal) {
		List<App> appList = principalService.get_can_access_app_list(principal);
		ModelAndView modelAndView = new ModelAndView("financialContract/financial-contract-list");
		modelAndView.addObject("appList", appList);
		modelAndView.addObject("financialContractTypeList",FinancialContractType.values());
		return modelAndView;
	}
	
	@RequestMapping("/query")
	@MenuSetting("submenu-financial-contract")
	public @ResponseBody String query(@Secure Principal principal,
			@ModelAttribute FinancialContractQueryModel financialContractQueryModel,
			Page page) {
		try {
			Map<String, Object> data = financialContractHandler.query(financialContractQueryModel, page); 
			return jsonViewResolver.sucJsonResult(data, SerializerFeature.DisableCircularReferenceDetect);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}

	@RequestMapping("/new-financialContract")
	@MenuSetting("submenu-financial-contract")
	public ModelAndView newFinancialContract() {
		ModelAndView modelAndView = new ModelAndView("financialContract/financial-contract-create");

		List<App> appList = appService.loadAll(App.class);
		List<FinanceCompany> financeCompanies = financeCompanyService
				.loadAll(FinanceCompany.class);
		List<Company> companyList = financeCompanies.stream().map(fc -> fc.getCompany()).collect(Collectors.toList());
		modelAndView.addObject("companyList", companyList);
		modelAndView.addObject("financialContractTypeList", FinancialContractType.values());
		modelAndView.addObject("paymentChannelTypeList", paymentChannelService.loadAll(PaymentChannel.class));
		modelAndView.addObject("assetPackageFormatList", AssetPackageFormat.values());
		modelAndView.addObject("appList", appList);
		return modelAndView;

	}

	@RequestMapping(value = "/edit-show-financialContract/{financialContractId}", method = RequestMethod.GET)
	@MenuSetting("submenu-financial-contract")
	public ModelAndView editShowFinancialContrac(
			@PathVariable Long financialContractId) {
		ModelAndView modelAndView = new ModelAndView(
				"financialContract/financial-contract-edit");

		FinancialContract financialContract = financialContractService.load(
				FinancialContract.class, financialContractId);
		Company company = financialContract.getCompany();
		App app = financialContract.getApp();
		FinancialContractType financialContractType = financialContract
				.getFinancialContractType();
		PaymentChannel paymentChannel = financialContract.getPaymentChannel();
		AssetPackageFormat assetPackageFormat = financialContract
				.getAssetPackageFormat();

		List<App> appList = appService.loadAll(App.class);
		List<FinanceCompany> financeCompanies = financeCompanyService
				.loadAll(FinanceCompany.class);
		List<Company> companyList = financeCompanies.stream()
				.map(fc -> fc.getCompany()).collect(Collectors.toList());
		modelAndView.addObject("companyList", companyList);
		modelAndView.addObject("financialContractTypeList",
				FinancialContractType.values());
		modelAndView.addObject("paymentChannelTypeList",
				paymentChannelService.loadAll(PaymentChannel.class));
		modelAndView.addObject("assetPackageFormatList",
				AssetPackageFormat.values());
		modelAndView.addObject("appList", appList);
		modelAndView.addObject("financialContract", financialContract);
		modelAndView.addObject("company", company);
		modelAndView.addObject("app", app);
		modelAndView.addObject("financialContractType", financialContractType);
		modelAndView.addObject("paymentChannel", paymentChannel);
		modelAndView.addObject("assetPackageFormat", assetPackageFormat);
		return modelAndView;

	}

	@RequestMapping(value = "/add-new-financialContract", method = RequestMethod.POST)
	@MenuSetting("submenu-financial-contract")
	public @ResponseBody String addNewFinancialContract(
			@ModelAttribute CreateFinancialContractModel createFinancialContractModel,
			@Secure Principal principal, HttpServletRequest request,
			HttpServletResponse response) {

		Result result = new Result();
		try {
			createFinancialContractModel.check();
			FinancialContract financialContract = financialContractHandler
					.createFinancialContract(createFinancialContractModel);
			SystemOperateLogRequestParam param = getSystemOperateLogrequestParam(principal, request,
					financialContract);

			systemOperateLogHandler.generateSystemOperateLog(param);
		/*	systemOperateLogHandler.generateCommonSystemOperateLog(principal,
					IpUtil.getIpAddress(request),
					financialContract.getContractNo(),
					LogFunctionType.ADDFINANCIALCONTRACT, LogOperateType.ADD,
					financialContract);*/
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.toJSONString(result.fail().message("创建信托合同失败！！！"));
		}
		result.success().message("创建成功！！！");
		return JSON.toJSONString(result);

	}

	private SystemOperateLogRequestParam getSystemOperateLogrequestParam(Principal principal,
			HttpServletRequest request, FinancialContract financialContract) {
		SystemOperateLogRequestParam param = new SystemOperateLogRequestParam(
				principal.getId(), IpUtil.getIpAddress(request),
				financialContract.getContractNo(),
				LogFunctionType.ADDFINANCIALCONTRACT, LogOperateType.ADD,
				FinancialContract.class, financialContract, null, null);
		return param;
	}

	@RequestMapping(value = "/edit-financialContract/{financialContractId}", method = RequestMethod.POST)
	@MenuSetting("submenu-financiael-contract")
	public @ResponseBody String editFinancialContract(
			@PathVariable Long financialContractId,
			@ModelAttribute CreateFinancialContractModel createFinancialContractModel,
			@Secure Principal principal, HttpServletRequest request,
			HttpServletResponse response) {

		Result result = new Result();
		try {
			FinancialContract financialContract = financialContractService
					.load(FinancialContract.class, financialContractId);
			FinancialContractLog oldFinancialContractLog = new FinancialContractLog(
					financialContract);

			FinancialContract editFinancialContract = financialContractHandler
					.editFinancialContract(createFinancialContractModel,
							financialContractId);
			FinancialContractLog editFinancialContractLog = new FinancialContractLog(
					editFinancialContract);

			/*
			 * systemOperateLogHandler.generateEditSystemOperateLog(principal,
			 * IpUtil.getIpAddress(request),
			 * editFinancialContractLog.getContractNo(),
			 * LogFunctionType.EDITFINANCIALCONTRACT,
			 * FinancialContractLog.class, oldFinancialContractLog,
			 * editFinancialContractLog);
			 */
			SystemOperateLogRequestParam param = getSystemOperateLogRequestParam(
					principal, request, oldFinancialContractLog,
					editFinancialContractLog);
			systemOperateLogHandler.generateSystemOperateLog(param);

		} catch (Exception e) {
			e.printStackTrace();
			return JSON.toJSONString(result.fail().message("修改信托合同失败！！！"));
		}
		result.success().message("修改成功！！！");
		return JSON.toJSONString(result);

	}

	private SystemOperateLogRequestParam getSystemOperateLogRequestParam(
			Principal principal, HttpServletRequest request,
			FinancialContractLog oldFinancialContractLog,
			FinancialContractLog editFinancialContractLog) {
		SystemOperateLogRequestParam param = new SystemOperateLogRequestParam(
				principal.getId(), IpUtil.getIpAddress(request),
				editFinancialContractLog.getContractNo(),
				LogFunctionType.EDITFINANCIALCONTRACT, LogOperateType.UPDATE,
				FinancialContractLog.class, oldFinancialContractLog,
				editFinancialContractLog, null);
		return param;
	}

	@RequestMapping(value = "/{financialContractId}/detail", method = RequestMethod.GET)
	@MenuSetting("submenu-financial-contract")
	public ModelAndView showFinancailContractDetail(@PathVariable Long financialContractId,
			@ModelAttribute CreateFinancialContractModel createFinancialContractModel,
			Page page) {
		try {
			ModelAndView modelAndView = new ModelAndView("financialContract/financial-contract-detail");
			FinancialContract financialContract = financialContractService.getFinancialContractById(financialContractId);
			if(financialContract == null) {
				return pageViewResolver.errorSpec("信托合同不存在！");
			}
			List<Contract> contractList = contractService.getContractsByFinancialContract(financialContract, page);
			long contractsSize = contractService.countContractsByFinancialContract(financialContract);
			modelAndView.addObject("financialContract", financialContract);
			modelAndView.addObject("contractList", contractList);
			modelAndView.addObject("contractsSize", contractsSize);
			return modelAndView;
		} catch (Exception e) {
			logger.error("#showFinancailContractDetail occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	@RequestMapping(value = "/{financialContractId}/contracts", method = RequestMethod.GET)
	@MenuSetting("submenu-financial-contract")
	public @ResponseBody String getContractsInFinancialContractDetail(
			@PathVariable Long financialContractId,
			@ModelAttribute CreateFinancialContractModel createFinancialContractModel,
			Page page) {
		try {
			FinancialContract financialContract = financialContractService.getFinancialContractById(financialContractId);
			
			List<Contract> contractList = contractService.getContractsByFinancialContract(financialContract, page);
			long contractsSize = contractService.countContractsByFinancialContract(financialContract);
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("list", contractList);
			data.put("size", contractsSize);
			
			return jsonViewResolver.sucJsonResult(data);
		} catch (Exception e) {
			logger.error("#getContractsInFinancialContractDetail occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(GlobalCodeSpec.CODE_FAILURE);
		}
	}
	
}
