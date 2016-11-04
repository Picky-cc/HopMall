package com.zufangbao.earth.yunxin.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.gluon.spec.global.GlobalSpec;
import com.zufangbao.sun.VO.ProjectInformationSQLReturnData;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.excel.ExcelUtil;
import com.zufangbao.sun.yunxin.entity.excel.ProjectInformationExeclVo;
import com.zufangbao.sun.yunxin.entity.model.ProjectInformationQueryModel;
import com.zufangbao.sun.yunxin.handler.ContractHandler;

@Controller
@RequestMapping(ProjectInformationControllerSpec.NAME)
@MenuSetting("menu-report")
public class ProjectInformationController extends BaseController {

	private static final Log logger = LogFactory
			.getLog(ProjectInformationController.class);

	@Autowired
	private FinancialContractService financialContractService;

	@Autowired
	private PrincipalService principalService;

	@Autowired
	private ContractService contractService;
	
	@Autowired 
	private ContractHandler contractHandler;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@MenuSetting("submenu-project-information")
	public ModelAndView loadAll(HttpServletRequest request,
			@Secure Principal principal) {

		try {

			ModelAndView modelAndView = new ModelAndView(
					"projectInformation/project-information");
			List<FinancialContract> financialContracts = financialContractService
					.loadAll(FinancialContract.class);
			List<App> appList = principalService
					.get_can_access_app_list(principal);

			modelAndView.addObject("appList", appList);
			modelAndView.addObject("financialContracts", financialContracts);
			return modelAndView;

		} catch (Exception e) {
			logger.error("#showContractsPage occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}

	}

	@RequestMapping(value = ProjectInformationControllerSpec.QUERY, method = RequestMethod.GET)
	@MenuSetting("submenu-project-information")
	public @ResponseBody String queryList(
			@ModelAttribute ProjectInformationQueryModel projectInformationQueryModel, Page page) {
		try {
			queryAndSetFinancialContractUuid(projectInformationQueryModel);
			
			int size = contractService.countContractListBy(projectInformationQueryModel);
			List<ProjectInformationSQLReturnData> pagedDatas = contractService.getContractListBy(projectInformationQueryModel, page);
			
			List<ProjectInformationExeclVo> pagedShowVOs = contractHandler.castProjectInformationShowVOs(pagedDatas);
			
			Map<String, Object> data = new HashMap<>();
			data.put("list", pagedShowVOs);
			data.put("size", size);
			return jsonViewResolver.sucJsonResult(data);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}

	private void queryAndSetFinancialContractUuid(
			ProjectInformationQueryModel projectInformationQueryModel) {
		Long financialContractId = projectInformationQueryModel.getFinancialContractId();
		FinancialContract financialContract = financialContractService.getFinancialContractById(financialContractId);
		if(financialContract != null) {
			String financialContractUuid = financialContract.getFinancialContractUuid();
			projectInformationQueryModel.setFinancialContractUuid(financialContractUuid);
		}
	}
	
	@RequestMapping(value = ProjectInformationControllerSpec.EXPORTEXCEL, method = RequestMethod.GET)
	@MenuSetting("submenu-project-information")
	public @ResponseBody String exportProjectInformation(
			@ModelAttribute ProjectInformationQueryModel projectInformationQueryModel,
			HttpServletResponse response) {
		try {
			long start = System.currentTimeMillis();
			queryAndSetFinancialContractUuid(projectInformationQueryModel);
			
			List<ProjectInformationSQLReturnData> pagedDatas = contractService
					.getContractListBy(projectInformationQueryModel, null);
			
			List<ProjectInformationExeclVo> excelVOs = contractHandler.castProjectInformationShowVOs(pagedDatas);
			
			ExcelUtil<ProjectInformationExeclVo> excelUtil = new ExcelUtil<ProjectInformationExeclVo>(ProjectInformationExeclVo.class);
			List<String> csvData = excelUtil.exportDatasToCSV(excelVOs);

			Map<String, List<String>> csvs = new HashMap<String, List<String>>();
			csvs.put("项目信息表", csvData);
			exportZipToClient(response, create_download_file_name(), GlobalSpec.UTF_8, csvs);
			logger.info("#exportProjectInformation end. used ["+(System.currentTimeMillis()-start)+"]ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String create_download_file_name() {
		return "项目信息表" +"_"+DateUtils.format(new Date(), "yyyyMMdd_HHmmssSSS") +".zip";
	}
}
