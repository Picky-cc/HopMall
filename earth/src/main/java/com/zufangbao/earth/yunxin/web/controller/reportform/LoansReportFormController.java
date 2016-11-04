package com.zufangbao.earth.yunxin.web.controller.reportform;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.zufangbao.earth.yunxin.handler.reportform.LoansReportFormHandler;
import com.zufangbao.gluon.spec.global.GlobalSpec;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.excel.ExcelUtil;
import com.zufangbao.sun.yunxin.entity.model.reportform.LoansQueryModel;
import com.zufangbao.sun.yunxin.entity.model.reportform.LoansShowModel;
/**
 * 云信贷款规模报表Controller
 * @author louguanyang
 *
 */
@Controller
@RequestMapping("/reportform/loans")
@MenuSetting("menu-report")
public class LoansReportFormController extends BaseController {
	
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private LoansReportFormHandler loansReportFormHandler;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@MenuSetting("submenu-report-form-loans")
	public ModelAndView load(@Secure Principal principal, Page page, HttpServletRequest request) {
		List<FinancialContract> financialContracts = financialContractService.loadAll(FinancialContract.class);
		ModelAndView modelAndView = new ModelAndView("reportform/loans-reportform");
		modelAndView.addObject("financialContractList", financialContracts);
		return modelAndView;
	}

	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@MenuSetting("submenu-report-form-loans")
	public @ResponseBody String query(@Secure Principal principal, Page page, HttpServletRequest request,
			@ModelAttribute LoansQueryModel queryModel) {
		try {
			List<LoansShowModel> list = loansReportFormHandler.query(queryModel);
			Map<String, Object> data = new HashMap<>();
			data.put("list", list);
			data.put("size", list.size());
			return jsonViewResolver.sucJsonResult(data);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误:" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/exprot", method = RequestMethod.GET)
	@MenuSetting("submenu-report-form-loans")
	public String export(HttpServletRequest request,
			@ModelAttribute LoansQueryModel queryModel,
			HttpServletResponse response) {
		try {
			List<LoansShowModel> list = loansReportFormHandler.query(queryModel);
			ExcelUtil<LoansShowModel> excelUtil = new ExcelUtil<LoansShowModel>(LoansShowModel.class);
			HSSFWorkbook workBook = excelUtil.exportDataToHSSFWork(list, "贷款规模报表");

			exportExcelToClient(response, create_download_file_name(), GlobalSpec.UTF_8, workBook);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String create_download_file_name() {
		return "贷款规模报表" +"_"+DateUtils.format(new Date(), "yyyyMMdd_HHmmssSSS") +".xls";
	}
}
