package com.zufangbao.earth.web.controller.remittance;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.yunxin.entity.remittance.model.RemittanceApplicationShowModel;
import com.zufangbao.earth.yunxin.handler.remittance.RemittancePlanExecLogHandler;
import com.zufangbao.earth.yunxin.handler.remittance.RemittancetApplicationHandler;
import com.zufangbao.earth.yunxin.handler.remittance.RemittancetPlanHandler;
import com.zufangbao.gluon.api.jpmorgan.model.TradeSchedule;
import com.zufangbao.gluon.exception.CommonException;
import com.zufangbao.gluon.spec.global.GlobalCodeSpec;
import com.zufangbao.gluon.util.BeanWrapperUtil;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.yunxin.entity.remittance.ExecutionStatus;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceApplication;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceApplicationQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlan;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLog;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanShowModel;
import com.zufangbao.sun.yunxin.log.LogFunctionType;
import com.zufangbao.sun.yunxin.log.LogOperateType;
import com.zufangbao.sun.yunxin.log.SystemOperateLog;
import com.zufangbao.sun.yunxin.service.SystemOperateLogService;
import com.zufangbao.sun.yunxin.service.remittance.IRemittanceApplicationService;
import com.zufangbao.sun.yunxin.service.remittance.IRemittancePlanExecLogService;
import com.zufangbao.sun.yunxin.service.remittance.IRemittancePlanService;

@Controller
@RequestMapping("/remittance")
@MenuSetting("menu-data")
public class RemittanceController extends BaseController{
	
	@Autowired
	private FinancialContractService financialContractService;

	@Autowired
	private IRemittancePlanService iRemittancePlanService;
	
	@Autowired
	private IRemittancePlanExecLogService remittancePlanExecLogService;
	
	@Autowired
	private IRemittanceApplicationService iRemittanceApplicationService;
	
	@Autowired
	private RemittancetPlanHandler remittancetPlanHandler;
	
	@Autowired
	private RemittancePlanExecLogHandler remittancePlanExecLogHandler; 
	
	@Autowired
	private RemittancetApplicationHandler remittancetApplicationHandler;
	
	@Autowired
	private SystemOperateLogService systemOperateLogService;
	
	private static final Log logger = LogFactory.getLog(RemittanceController.class);
	
	// 计划订单---列表页面
	@RequestMapping(value = "/application")
	@MenuSetting("submenu-remittance-application")
	public ModelAndView showRemittanceApplicationPage() {
		try {
			ModelAndView modelAndView = new ModelAndView("remittance/plan-order-list");
			List<FinancialContract> financialContracts = financialContractService.loadAll(FinancialContract.class);
			modelAndView.addObject("financialContracts", financialContracts);
			modelAndView.addObject("orderStatus", Arrays.asList(ExecutionStatus.values()));
			return modelAndView;
		} catch (Exception e) {
			logger.error("#showRemittanceApplicationPage# occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	// 计划订单---列表查询
	@RequestMapping(value = "/application/query")
	@MenuSetting("submenu-remittance-application")
	public @ResponseBody String queryRemittanceApplication(
			@ModelAttribute RemittanceApplicationQueryModel remittanceApplicationQueryModel,
			Page page) {
		try {
			List<RemittanceApplicationShowModel> showModels = remittancetApplicationHandler.queryShowModelList(remittanceApplicationQueryModel, page);
			int total = iRemittanceApplicationService.queryRemittanceApplicationCount(remittanceApplicationQueryModel);
			Map<String, Object> data = new HashMap<>();
			data.put("remittanceApplicationQueryModel",remittanceApplicationQueryModel);
			data.put("list", showModels);
			data.put("size", total);
			return jsonViewResolver.sucJsonResult(data,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.error("#queryRemittanceApplication#  occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("查询错误");
		}
	}
	
	// 计划订单---详情页面
	@RequestMapping(value="/application/details/{remittanceApplicationUuid}")
	@MenuSetting("submenu-remittance-application")
	public ModelAndView showRemittanceApplicationDetails(
			@PathVariable(value="remittanceApplicationUuid") String remittanceApplicationUuid){
		try {
			ModelAndView modelAndView = new ModelAndView("remittance/plan-order-detail");
			RemittanceApplication remittanceApplication = iRemittanceApplicationService.getUniqueRemittanceApplicationByUuid(remittanceApplicationUuid);
			modelAndView.addObject("remittanceApplication", remittanceApplication);
			List<RemittancePlan> remittancePlans = iRemittancePlanService.getRemittancePlanListBy(remittanceApplicationUuid);
			modelAndView.addObject("remittancePlans", remittancePlans);
			return modelAndView;
		} catch (Exception e) {
			logger.error("#showRemittanceApplicationDetails# occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	// 计划订单---详情页面重新回调结果
	@RequestMapping(value="/application/details/updateplannotifynumber/{remittanceApplicationUuid}")
	@MenuSetting("submenu-remittance-application")
	public @ResponseBody String updatePlanNotifyNumber(@PathVariable(value="remittanceApplicationUuid") String remittanceApplicationUuid){
		try{
			boolean isSuc = iRemittanceApplicationService.addPlanNotifyNumber(remittanceApplicationUuid, 1);
			return jsonViewResolver.jsonResult(isSuc);
		} catch (Exception e){
			logger.error("#updatePlanNotifyNumber# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("发生错误，请稍后重试");
		}
	}
	
	// 放款单---列表页面
	@RequestMapping(value = "/plan")
	@MenuSetting("submenu-remittance-plan")
	public ModelAndView showRemittancePlanPage() {
		try {
			ModelAndView modelAndView = new ModelAndView("remittance/loan-list");
			List<FinancialContract> financialContracts = financialContractService.loadAll(FinancialContract.class);
			modelAndView.addObject("financialContracts", financialContracts);
			modelAndView.addObject("executionStatus", Arrays.asList(ExecutionStatus.values()));
			return modelAndView;
		} catch (Exception e) {
			logger.error("#showRemittancePlanPage# occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	// 放款单---列表查询
	@RequestMapping(value= "/plan/query")
	@MenuSetting("submenu-remittance-plan")
	public @ResponseBody String queryRemittancePlan(@ModelAttribute RemittancePlanQueryModel queryModel, Page page){
		try{
			List<RemittancePlanShowModel> showModels = remittancetPlanHandler.queryShowModelList(queryModel, page);
			int total = iRemittancePlanService.queryRemittancePlanCount(queryModel);
			Map<String, Object> data = new HashMap<>();
			data.put("list", showModels);
			data.put("size", total);
			return jsonViewResolver.sucJsonResult(data,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		}catch (Exception e){
			logger.error("#queryRemittancePlan# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("查询错误");
		}
	}
	
	// 放款单---详情页面
	@RequestMapping(value="/plan/details/{remittancePlanUuid}")
	@MenuSetting("submenu-remittance-plan")
	public ModelAndView showRemittancePlanDetails(
			@PathVariable(value="remittancePlanUuid") String remittancePlanUuid){
		try {
			ModelAndView modelAndView = new ModelAndView("remittance/loan-detail");
			RemittancePlan remittancePlan = iRemittancePlanService.getUniqueRemittancePlanByUuid(remittancePlanUuid);
			String remittanceApplicationUuid = remittancePlan.getRemittanceApplicationUuid();
			RemittanceApplication remittanceApplication = iRemittanceApplicationService.getUniqueRemittanceApplicationByUuid(remittanceApplicationUuid);
			Map<String, Object>rtnRemittanceApplication = BeanWrapperUtil.wrapperMap(remittanceApplication, "auditorName", "auditTime", "executionStatus");
			List<RemittancePlanExecLog> remittancePlanExecLogs = remittancePlanExecLogService.getRemittancePlanExecLogListBy(remittancePlanUuid);
			modelAndView.addObject("remittancePlan", remittancePlan);
			modelAndView.addObject("remittanceApplication", rtnRemittanceApplication);
			modelAndView.addObject("remittancePlanExecLogs", remittancePlanExecLogs);
			return modelAndView;
		} catch (Exception e) {
			logger.error("#showRemittancePlanDetails# occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	// 放款单---详情页面---失败再放款
	@RequestMapping(value= "/plan/resend", method = RequestMethod.GET)
	@MenuSetting("submenu-remittance-plan")
	public @ResponseBody String reRemittanceForFailedPlan(
			@RequestParam(value="remittancePlanUuid", required = true) String remittancePlanUuid,
			@Secure Principal principal,
			HttpServletRequest request) {
		try {
			TradeSchedule tradeSchedule = remittancetPlanHandler.saveRemittanceInfoBeforeResendForFailedPlan(remittancePlanUuid);
			remittancetPlanHandler.processingAndUpdateRemittanceInfoForResend_NoRollback(tradeSchedule);
			recordSystemOperateLogForResendRemittance(principal, request, remittancePlanUuid, tradeSchedule.getSourceMessageUuid());
			return jsonViewResolver.sucJsonResult();
		} catch (CommonException e) {
			logger.error("#reRemittanceForFailedPlan# occur error.");
			return jsonViewResolver.errorJsonResult(e.getErrorMsg());
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(GlobalCodeSpec.CODE_FAILURE);
		}
	}
	
	private void recordSystemOperateLogForResendRemittance(Principal principal, HttpServletRequest request, String remittancePlanUuid, String execReqNo) {
		if(StringUtils.isEmpty(remittancePlanUuid)) {
			return;
		}
		SystemOperateLog log = new SystemOperateLog();
		log.setUserId(principal.getId());
		log.setIp(IpUtil.getIpAddress(request));
		log.setObjectUuid(remittancePlanUuid);
		log.setLogFunctionType(LogFunctionType.RESEND_REMITTANCE);
		log.setLogOperateType(LogOperateType.RESEND);
		log.setKeyContent(remittancePlanUuid);
		log.setRecordContent("重新执行放款单，线上代付单号［" + execReqNo + "］");
		log.setOccurTime(new Date());
		systemOperateLogService.save(log);
	}

}
