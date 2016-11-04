package com.zufangbao.earth.yunxin.web.controller;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.entity.Result;
import com.demo2do.core.persistence.support.Order;
import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.yunxin.handler.LoanBatchHandler;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.LoanBatch;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.yunxin.entity.model.loanbatch.LoanBatchQueryModel;
import com.zufangbao.sun.yunxin.entity.model.loanbatch.LoanBatchShowModel;
import com.zufangbao.sun.yunxin.log.LogFunctionType;
import com.zufangbao.sun.yunxin.log.LogOperateType;
import com.zufangbao.sun.yunxin.service.LoanBatchService;

@Controller
@RequestMapping("loan-batch")
@MenuSetting("menu-data")
public class LoanBatchController extends BaseController {

	private static final int THE_DEFAULT_SHEET = 0;
	@Autowired
	private LoanBatchService loanBatchService;
	@Autowired
	private LoanBatchHandler loanBatchHandler;
	@Autowired
	private PrincipalService principalService;
	@Autowired
	private FinancialContractService financialContractService;
	
	/**
	 * 
	 * @param page
	 * @param request
	 * @param loanBatchId
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@MenuSetting("submenu-assets-package-loan_batch")
	public ModelAndView loadAll(@Secure Principal principal, Page page, HttpServletRequest request) {
		List<App> appList = principalService.get_can_access_app_list(principal);
		List<LoanBatch> allLoanBatch = loanBatchService.list(LoanBatch.class, new Order().add("id", "desc"));
		List<FinancialContract> financialContractList = financialContractService.loadAll(FinancialContract.class);
		
		ModelAndView modelAndView = new ModelAndView("loanBatch/loan-batch");
		modelAndView.addObject("page", page);
		String queryString = request.getQueryString();
		if (!StringUtils.isEmpty(queryString)) {
			Map<String, String> queries = StringUtils
					.parseQueryString(queryString);
			if (queries.containsKey("page")) {
				queries.remove("page");
			}
			modelAndView.addObject("queryString",
					StringUtils.toQueryString(queries));
		}
		modelAndView.addObject("loanBatchList", allLoanBatch);
		modelAndView.addObject("appList", appList);
		modelAndView.addObject("financialContractList", financialContractList);
		return modelAndView;
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@MenuSetting("submenu-assets-package-loan_batch")
	public @ResponseBody String query(@Secure Principal principal, Page page, HttpServletRequest request,
			@ModelAttribute LoanBatchQueryModel loanBatchQueryModel) {
		try {
			int size = loanBatchService.countLoanBatchList(loanBatchQueryModel);
			List<LoanBatchShowModel> loanBatchShowModelList = loanBatchHandler.generateLoanBatchShowModelList(loanBatchQueryModel, page);
			Map<String, Object> data = new HashMap<>();
			data.put("list", loanBatchShowModelList);
			data.put("size", size);
			return jsonViewResolver.sucJsonResult(data);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	
	/**
	 * @param contract
	 * @param loanBatch
	 */
	@RequestMapping(value = "/{loanBatchId}/loanBatch-detail", method = RequestMethod.GET)
	@MenuSetting("submenu-payment-payment")
	public ModelAndView PaymentDetail(
			@PathVariable("loanBatchId") Long loanBatchId,
			@Secure Principal principal, Page page) {
		LoanBatch loanBatch = loanBatchService.load(LoanBatch.class,
				loanBatchId);
		ModelAndView modelAndView = new ModelAndView(
				"loanBatch/loan-batch-detail");
		modelAndView.addObject("loanBatch", loanBatch);
		return modelAndView;
	}

	@RequestMapping(value = "/export-repayment-plan/{loanBatchId}")
	@MenuSetting("submenu-assets-package-loan_batch")
	public String exportRepaymentPlan(HttpServletRequest request, Page page,
			@PathVariable Long loanBatchId, HttpServletResponse response,
			@Secure Principal principal) {
		try {
			/** 设置response头信息 **/
			// 重置response头信息
			response.reset();
			// 自己写状态码
			response.setStatus(HttpServletResponse.SC_OK);
			// 使客户端浏览器，区分不同种类的数据，并根据不同的MIME调用浏览器内不同的程序嵌入模块来处理相应的数据。
			// ZIP与EXE文件的MIME类型同为application/octet-stream
			response.setContentType("application/octet-stream");
			// Content-disposition 是 MIME 协议的扩展，MIME 协议指示 MIME 用户代理如何显示附加的文件。
			// Content-disposition其实可以控制用户请求所得的内容存为一个文件的时候提供一个默认的文件名，文件直接在浏览器上显示或者在访问时弹出文件下载对话框。
			// 此处设置下载文件的格式为.zip
			response.setHeader(
					"Content-Disposition",
					"attachment;filename="
							+ java.net.URLEncoder.encode(
									create_download_file_name(
											DateUtils.asDay(new Date()),
											loanBatchId), "UTF-8"));
			OutputStream out = response.getOutputStream();
			ZipOutputStream zip = new ZipOutputStream(out);

			List<HSSFWorkbook> excelList = loanBatchHandler
					.createExcelList(loanBatchId);
			zip.flush();
			loanBatchHandler.generateLoanBacthSystemLog(principal, IpUtil.getIpAddress(request), LogFunctionType.EXPORTREPAYEMNTPLAN, LogOperateType.EXPORT, loanBatchId);
			for (HSSFWorkbook workbook : excelList) {
				String excelName = workbook.getSheetAt(THE_DEFAULT_SHEET)
						.getSheetName();
				ZipEntry entry = new ZipEntry(excelName + ".xls");
				// 将压缩实体放入压缩包
				zip.putNextEntry(entry);
				// 将excel内容写进压缩实体
				workbook.write(zip);
			}
			zip.flush();
			zip.close();
			response.flushBuffer();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/delete-loan-batch", method = RequestMethod.POST)
	@MenuSetting("submenu-assets-package-loan_batch")
	public @ResponseBody String deleteLoanBatch(
			@RequestParam(required = true) Long loanBatchId,
			@Secure Principal principal, HttpServletRequest request,
			HttpServletResponse response) {

		Result result = new Result();
		try {
			loanBatchHandler.deleteLoanBatchData(principal, IpUtil.getIpAddress(request), loanBatchId);
			result.success().setMessage("成功删除批次数据！！");
			return JsonUtils.toJsonString(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.fail().setMessage(e.getMessage());
			return JsonUtils.toJsonString(result);
		}
	}

	@RequestMapping(value = "/activate", method = RequestMethod.POST)
	@MenuSetting("submenu-assets-package-loan_batch")
	public @ResponseBody String activateLoanBatch(
			@RequestParam Long loanBatchId, @Secure Principal principal,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		try {
			loanBatchHandler.activateLoanBatch(loanBatchId);
			loanBatchHandler.generateLoanBacthSystemLog(principal, IpUtil.getIpAddress(request),  LogFunctionType.ACTIVELOANBATCH, LogOperateType.ACTIVE, loanBatchId);
		} catch (Exception e) {
			e.printStackTrace();
			result.fail().message("激活失败！！！");
			return JsonUtils.toJsonString(result);
		}
		result.success().message("激活成功！！");
		return JsonUtils.toJsonString(result);
	}

	private String create_download_file_name(Date create_date, Long loanBatchId) {
		LoanBatch loanBatch = loanBatchService.load(LoanBatch.class,
				loanBatchId);
		return loanBatch.getCode() + "批次" + "详细还款计划"
				+ DateUtils.format(create_date, "yyyy_MM_dd") + ".zip";
	}
}