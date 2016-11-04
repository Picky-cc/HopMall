package com.zufangbao.earth.yunxin.web.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.handler.ActivePaymentVoucherHandler;
import com.zufangbao.earth.yunxin.api.handler.BusinessPaymentVoucherHandler;
import com.zufangbao.earth.yunxin.api.handler.BusinessPaymentVoucherSession;
import com.zufangbao.earth.yunxin.api.util.ApiMessageUtil;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.yunxin.entity.api.VoucherType;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherCreateBaseModel;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherCreateModel;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherDetailBusinessModel;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherDetailModel;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherQueryModel;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherShowModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetail;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetailStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.SourceDocumentDetailHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentDetailService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;

/**
 * 商户付款凭证页
 * @author louguanyang
 *
 */
@Controller("voucherController")
@RequestMapping("/voucher")
@MenuSetting("menu-capital")
public class VoucherController extends BaseController{
	
	private static final Log logger = LogFactory.getLog(VoucherController.class);
	
	@Autowired
	private SourceDocumentDetailService sourceDocumentDetailService;
	@Autowired
	private SourceDocumentService sourceDocumentService;
	@Autowired
	private SourceDocumentDetailHandler sourceDocumentDetailHandler;
	@Autowired
	private BusinessPaymentVoucherSession businessPaymentVoucherSession;
	@Autowired
	private BusinessPaymentVoucherHandler businessPaymentVoucherHandler;
	@Autowired
	private ActivePaymentVoucherHandler activePaymentVoucherHandler;
	
	
	@RequestMapping(value = "/business", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-business")
	public ModelAndView showBusinessVoucherPage(@ModelAttribute VoucherQueryModel voucherQueryModel, @Secure Principal principal, Page page, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("voucher/list");
		modelAndView.addObject("voucherTypeList", Arrays.asList(VoucherType.business_payment_voucher_type));
		modelAndView.addObject("voucherStatusList", Arrays.asList(SourceDocumentDetailStatus.values()));
		return modelAndView;
	}
	
	/**
	 * 主动付款凭证-列表页
	 * @param voucherQueryModel
	 * @param principal
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/active", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-active")
	public ModelAndView showActiveVoucherPage(@ModelAttribute VoucherQueryModel voucherQueryModel, @Secure Principal principal, Page page, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("voucher/active-payment-voucher-list");
		modelAndView.addObject("voucherTypeList", Arrays.asList(VoucherType.active_payment_voucher_type));
		modelAndView.addObject("voucherStatusList", Arrays.asList(SourceDocumentDetailStatus.values()));
		return modelAndView;
	}
	
	@RequestMapping(value = "/business/query", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-business")
	public @ResponseBody String queryBusinessVoucher(@ModelAttribute VoucherQueryModel voucherQueryModel, @Secure Principal principal, Page page, HttpServletRequest request) {
		try {
			int size = sourceDocumentDetailService.countBusinessVouchers(voucherQueryModel);
			List<VoucherShowModel> showList = sourceDocumentDetailHandler.getBusinessVoucherShowModels(voucherQueryModel, page);
			Map<String, Object> data = new HashMap<>();
			data.put("list", showList);
			data.put("size", size);
			return jsonViewResolver.sucJsonResult(data);
		} catch (Exception e) {
			logger.error("#queryVoucher occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	/**
	 * 主动付款凭证查询
	 * @param voucherQueryModel
	 * @param principal
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/active/query", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-active")
	public @ResponseBody String queryActiveVoucher(@ModelAttribute VoucherQueryModel voucherQueryModel, @Secure Principal principal, Page page, HttpServletRequest request) {
		try {
			int size = sourceDocumentDetailService.countActiveVouchers(voucherQueryModel);
			List<VoucherShowModel> showList = sourceDocumentDetailHandler.getActiveVoucherShowModels(voucherQueryModel, page);
			Map<String, Object> data = new HashMap<>();
			data.put("list", showList);
			data.put("size", size);
			return jsonViewResolver.sucJsonResult(data);
		} catch (Exception e) {
			logger.error("#queryVoucher occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	@RequestMapping(value = "/business/detail/{detailId}", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-business")
	public ModelAndView detailOfBusinessVoucher(@PathVariable("detailId") Long detailId,@Secure Principal principal, HttpServletRequest request) {
		try {
			VoucherDetailModel detail = sourceDocumentDetailHandler.getBusinessVoucherDetailModel(detailId);
			if (detail == null) {
				return pageViewResolver.errorSpec("当前凭证不是商户付款凭证，无法进入详情页!");
			}
			ModelAndView modelAndView = new ModelAndView("voucher/detail");
			modelAndView.addObject("detail", detail);
			modelAndView.addObject("detailId", detailId);
			return modelAndView;
		} catch (Exception e) {
			return pageViewResolver.errorSpec();
		}
	}
	
	/**
	 * 主动付款凭证-详情
	 * @param detailId
	 * @param principal
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/active/detail/{detailId}", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-active")
	public ModelAndView detailOfActiveVoucher(@PathVariable("detailId") Long detailId,@Secure Principal principal, HttpServletRequest request) {
		try {
			VoucherDetailModel detail = sourceDocumentDetailHandler.getActiveVoucherDetailModel(detailId);
			if (detail == null) {
				return pageViewResolver.errorSpec("当前凭证不是主动付款凭证，无法进入详情页!");
			}
			ModelAndView modelAndView = new ModelAndView("voucher/active-payment-voucher-detail"); 
			modelAndView.addObject("detail", detail);
			modelAndView.addObject("detailId", detailId);
			return modelAndView;
		} catch (Exception e) {
			return pageViewResolver.errorSpec();
		}
	}
	
	@RequestMapping(value = "/business/detail/hexiao/{detailId}", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-business")
	public @ResponseBody String hexiaoSingleBusinessVoucher(@PathVariable("detailId") Long detailId,@Secure Principal principal, HttpServletRequest request) {
		try {
			SourceDocumentDetail detail = sourceDocumentDetailService.load(SourceDocumentDetail.class, detailId);
			SourceDocument sourceDocument = sourceDocumentService.getSourceDocumentBy(detail.getSourceDocumentUuid());
			businessPaymentVoucherSession.single_compensatory_recover_loan_asset(Arrays.asList(detailId), sourceDocument);
			return jsonViewResolver.sucJsonResult();
		} catch (Exception e) {
			logger.error("#queryVoucher occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	/**
	 * 凭证-业务单据
	 * @param detailId
	 * @param principal
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/business/detail/query", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-business")
	public @ResponseBody String queryBusinessVoucherDetails(@RequestParam("detailId") Long detailId, @Secure Principal principal, Page page, HttpServletRequest request) {
		try {
			return queryVoucherDetails(detailId, page);
		} catch (Exception e) {
			logger.error("#queryVoucher occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	/**
	 * 主动付款凭证-业务单据
	 * @param detailId
	 * @param principal
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/active/detail/query", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-active")
	public @ResponseBody String queryActiveVoucherDetails(@RequestParam("detailId") Long detailId, @Secure Principal principal, Page page, HttpServletRequest request) {
		try {
			return queryVoucherDetails(detailId, page);
		} catch (Exception e) {
			logger.error("#queryVoucher occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}

	private String queryVoucherDetails(Long detailId, Page page) {
		SourceDocumentDetail detail = sourceDocumentDetailService.load(SourceDocumentDetail.class, detailId);
		int size = sourceDocumentDetailService.countSameVersionDetails(detail);	
		List<VoucherDetailBusinessModel> showList= sourceDocumentDetailHandler.queryVoucherDetails(detail, page);
		Map<String, Object> data = new HashMap<>();
		data.put("list", showList);
		data.put("size", size);
		return jsonViewResolver.sucJsonResult(data);
	}
	
	@RequestMapping(value = "/business/invalid/{detailId}", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-business")
	public @ResponseBody String invalidBusinessVoucher(@PathVariable("detailId") Long detailId,@Secure Principal principal, HttpServletRequest request) {
		try {
			businessPaymentVoucherHandler.invalidSourceDocument(detailId);
			return jsonViewResolver.sucJsonResult();
		} catch (Exception e) {
			logger.error("#invalidBusinessVoucher occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	/**
	 * 主动付款凭证作废
	 * @param detailId
	 * @param principal
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/active/invalid/{detailId}", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-active")
	public @ResponseBody String invalidActiveVoucher(@PathVariable("detailId") Long detailId,@Secure Principal principal, HttpServletRequest request) {
		try {
			businessPaymentVoucherHandler.invalidSourceDocument(detailId);
			return jsonViewResolver.sucJsonResult();
		} catch (Exception e) {
			logger.error("#invalidActiveVoucher occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	/**
	 * 主动付款凭证-新增
	 * @param principal
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/active/create", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-active")
	public ModelAndView create(@Secure Principal principal, HttpServletRequest request) {
		try {
			ModelAndView modelAndView = new ModelAndView("voucher/active-payment-create"); 
			modelAndView.addObject("voucherTypes", VoucherType.active_payment_voucher_type);
			return modelAndView;
		} catch (Exception e) {
			return pageViewResolver.errorSpec();
		}
	}
	
	/**
	 * 主动付款凭证-新增-根据贷款合同编号匹配相关信息
	 * @param principal
	 * @param request
	 * @param contractNo 贷款合同编号
	 * @return
	 */
	@RequestMapping(value = "/active/create/search-contract", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-active")
	public @ResponseBody String searchAccountInfo(@Secure Principal principal,
			HttpServletRequest request,
			@RequestParam(value = "contractNo", required = true, defaultValue = "") String contractNo) {
		try {
			VoucherCreateBaseModel accountInfo = activePaymentVoucherHandler.searchAccountInfoByContractNo(contractNo);
			return jsonViewResolver.sucJsonResult("accountInfo", accountInfo);
		} catch (Exception e) {
			logger.error("#searchAccountInfo occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	@RequestMapping(value = "/active/create/search-name", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-active")
	public @ResponseBody String searchAccountInfoByName(@Secure Principal principal,
			HttpServletRequest request,
			@RequestParam(value = "name", required = true, defaultValue = "") String name) {
		try {
			List<ContractAccount> accountInfos = activePaymentVoucherHandler.searchAccountInfoByName(name);
			return jsonViewResolver.sucJsonResult("contractAccountList", accountInfos);
		} catch (Exception e) {
			logger.error("#searchAccountInfo occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	@RequestMapping(value = "/active/create/save", method = RequestMethod.POST)
	@MenuSetting("submenu-voucher-active")
	public @ResponseBody String save(@Secure Principal principal, @ModelAttribute VoucherCreateModel model,
			HttpServletRequest request) {
		try {
			logger.info("请求新增主动付款凭证：【凭证内容】" + model.toString() + ", 【操作人】" + principal.getName() + "【IP地址】" + IpUtil.getIpAddress(request));
			
			if(model.paramsHasError()) {
				return jsonViewResolver.errorJsonResult(model.getCheckFailedMsg());
			}
			activePaymentVoucherHandler.saveActiveVoucher(model, principal, IpUtil.getIpAddress(request));
			return jsonViewResolver.sucJsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			String error_msg = e.getMessage();
			if(e instanceof ApiException) {
				error_msg = ApiMessageUtil.getMessage(((ApiException) e).getCode());
			}
			logger.error("#save active voucher occur error ]: " + error_msg);
			return jsonViewResolver.errorJsonResult("系统错误," + error_msg);
		}
	}
	
	/**
	 * 新增主动付款凭证上传资源文件返回资源文件uuid
	 * @param principal
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/active/create/upload-file", method = RequestMethod.POST)
	@MenuSetting("submenu-voucher-active")
	public @ResponseBody String uploadSingleFile(@Secure Principal principal,
			MultipartFile file, HttpServletRequest request) {
		try {
			logger.info("新增主动付款凭证，上传资源文件,【操作人】" + principal.getName() + "【IP地址】" + IpUtil.getIpAddress(request));
			if(file == null) {
				return jsonViewResolver.errorJsonResult("请选择要上传的文件");
			}
			String uuid = activePaymentVoucherHandler.uploadSingleFileReturnUUID(file);
			return jsonViewResolver.sucJsonResult("uuid", uuid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("#save active voucher occur error ]: " + e.getMessage());
			return jsonViewResolver.errorJsonResult("系统错误," + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/active/detail/update-comment/{detailId}", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-active")
	public @ResponseBody String updateActiveVoucherComment(@PathVariable("detailId") Long detailId,@Secure Principal principal, HttpServletRequest request, @RequestParam(value = "comment", required = true, defaultValue = "") String comment) {
		try {
			activePaymentVoucherHandler.updateActiveVoucherComment(detailId, comment);
			return jsonViewResolver.sucJsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("#update active voucher comment occur error ]: " + e.getMessage());
			return jsonViewResolver.errorJsonResult("系统错误," + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/active/detail/resource/{detailId}", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-active")
	public @ResponseBody String getActiveVoucherResource(@PathVariable("detailId") Long detailId,@Secure Principal principal, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<String> resources = activePaymentVoucherHandler.getActiveVoucherResource(detailId);
			String zipFileName = "主动付款凭证资源文件_" + detailId + "_" + UUID.randomUUID().toString() + ".zip";
			zipFilesToClient(response, zipFileName, "UTF-8", resources);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("#update active voucher comment occur error ]: " + e.getMessage());
			return jsonViewResolver.errorJsonResult("系统错误," + e.getMessage());
		}
		return jsonViewResolver.sucJsonResult();
	}
	
	/**
	 * 流水匹配
	 * 商户付款凭证
	 * @param detailId
	 * @param principal
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/business/detail/match-cash-flow/{detailId}", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-business")
	public @ResponseBody String matchCashFlow(@PathVariable("detailId") Long detailId,@Secure Principal principal, HttpServletRequest request)  {
		try {
			List<CashFlow> cashFlows = businessPaymentVoucherHandler.matchCashflow(detailId);
			return jsonViewResolver.sucJsonResult("cashFlows", cashFlows);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("#match cash flow occur error ]: " + e.getMessage());
			return jsonViewResolver.errorJsonResult("系统错误," + e.getMessage());
		}
	}
	
	/**
	 * 流水匹配
	 * 商户付款凭证
	 * @param detailId
	 * @param principal
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/business/detail/connection-cash-flow/{detailId}", method = RequestMethod.GET)
	@MenuSetting("submenu-voucher-business")
	public @ResponseBody String connectionCashFlow(@PathVariable("detailId") Long detailId,@Secure Principal principal, HttpServletRequest request, @RequestParam(value = "cashFlowUuid", required = true, defaultValue = "") String cashFlowUuid)  {
		try {
			businessPaymentVoucherHandler.connectionCashFlow(detailId, cashFlowUuid);
			return jsonViewResolver.sucJsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("#connection cash flow occur error ]: " + e.getMessage());
			return jsonViewResolver.errorJsonResult("系统错误," + e.getMessage());
		}
	}
	
}
