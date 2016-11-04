package com.zufangbao.earth.yunxin.handler.remittance.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo2do.core.entity.Result;
import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.yunxin.handler.remittance.RemittancetPlanHandler;
import com.zufangbao.gluon.api.jpmorgan.JpmorganApiHelper;
import com.zufangbao.gluon.api.jpmorgan.enums.AccountSide;
import com.zufangbao.gluon.api.jpmorgan.model.TradeSchedule;
import com.zufangbao.gluon.exception.CommonException;
import com.zufangbao.gluon.opensdk.HttpClientUtils;
import com.zufangbao.gluon.spec.global.GlobalCodeSpec;
import com.zufangbao.sun.utils.EnumUtil;
import com.zufangbao.sun.yunxin.entity.remittance.ExecutionStatus;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceApplication;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlan;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLog;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanShowModel;
import com.zufangbao.sun.yunxin.service.UnionpayBankConfigService;
import com.zufangbao.sun.yunxin.service.remittance.IRemittanceApplicationService;
import com.zufangbao.sun.yunxin.service.remittance.IRemittancePlanExecLogService;
import com.zufangbao.sun.yunxin.service.remittance.IRemittancePlanService;

@Component("remittancetPlanHandler")
public class RemittancePlanHandlerImpl implements RemittancetPlanHandler{
	
	private static final Log logger = LogFactory.getLog(RemittancePlanHandlerImpl.class);
	
	@Autowired
	private IRemittancePlanService iRemittancePlanService;
	
	@Autowired
	private IRemittanceApplicationService iRemittanceApplicationService;
	
	@Autowired
	private IRemittancePlanExecLogService iRemittancePlanExecLogService;
	
	@Autowired
	private UnionpayBankConfigService unionpayBankConfigService;
	
	@Autowired
	private GenericDaoSupport genericDaoSupport; 
	
	@Autowired
	private JpmorganApiHelper jpmorganApiHelper;

	@Override
	public List<RemittancePlanShowModel> queryShowModelList(RemittancePlanQueryModel queryModel, Page page) {
		List<RemittancePlan> remittancePlans = iRemittancePlanService.queryRemittancePlan(queryModel, page);
		List<RemittancePlanShowModel> remittancePlanShowModels = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(remittancePlans)){
			remittancePlanShowModels = remittancePlans.stream().map(c-> new RemittancePlanShowModel(c)).collect(Collectors.toList());
		}
		return remittancePlanShowModels;
	}

	@Override
	public TradeSchedule saveRemittanceInfoBeforeResendForFailedPlan(String remittancePlanUuid) throws CommonException {
		RemittancePlan remittancePlan = iRemittancePlanService.getUniqueRemittancePlanByUuid(remittancePlanUuid);
		if(remittancePlan == null || remittancePlan.getExecutionStatus() != ExecutionStatus.FAIL) {
			throw new CommonException(GlobalCodeSpec.CODE_FAILURE, "仅失败的放款单允许人工处理！");
		}
		String remittanceApplicationUuid = remittancePlan.getRemittanceApplicationUuid();
		RemittanceApplication remittanceApplication = iRemittanceApplicationService.getUniqueRemittanceApplicationByUuid(remittanceApplicationUuid);
		if(remittanceApplication == null || remittanceApplication.getExecutionStatus() != ExecutionStatus.ABNORMAL) {
			throw new CommonException(GlobalCodeSpec.CODE_FAILURE, "仅异常的放款计划订单允许人工处理！");
		}
		
		List<RemittancePlanExecLog> planExecLogs = iRemittancePlanExecLogService.getRemittancePlanExecLogListBy(remittancePlanUuid);
		if(CollectionUtils.isEmpty(planExecLogs)) {
			throw new CommonException(GlobalCodeSpec.CODE_FAILURE, "人工处理失败，放款单下不存在相关线上代付单！");
		}
		
		long count = planExecLogs
				.stream()
				.filter(log -> log.getExecutionStatus() != ExecutionStatus.FAIL)
				.count();
		if(count > 0) {
			throw new CommonException(GlobalCodeSpec.CODE_FAILURE, "仅相关线上代付单为失败时，允许人工处理！");
		}
		
		TradeSchedule tradeSchedule = convertToTradeSchedule(remittanceApplicationUuid, remittancePlan);
		RemittancePlanExecLog remittancePlanExecLog = new RemittancePlanExecLog(remittancePlan, tradeSchedule.getSourceMessageUuid());
		iRemittancePlanExecLogService.save(remittancePlanExecLog);
		return tradeSchedule;
	}
	
	private TradeSchedule convertToTradeSchedule(String remittanceApplicationUuid, RemittancePlan remittancePlan) {
		String fstPaymentChannelUuid = remittancePlan.getPaymentChannelUuid();
		Integer paymentGateway = remittancePlan.getPaymentGateway().ordinal();
		String channelName = remittancePlan.getPaymentChannelName();
		
		String remittanceDetailUuid = remittancePlan.getRemittanceApplicationDetailUuid();
		String relatedBusinessRecordNo = remittancePlan.getBusinessRecordNo();
				
		Map<String, String> destinationAccountAppendixMap = new HashMap<String, String>();
		destinationAccountAppendixMap.put("idNumber", remittancePlan.getCpIdNumber());
		String destinationAccountAppendix = JSON.toJSONString(destinationAccountAppendixMap, SerializerFeature.DisableCircularReferenceDetect);
		
		Map<String, String> destinationBankInfoMap = new HashMap<String, String>();
		destinationBankInfoMap.put("bankCode", remittancePlan.getCpBankCode());
		destinationBankInfoMap.put("bankProvince", remittancePlan.getCpBankProvince());
		destinationBankInfoMap.put("bankCity", remittancePlan.getCpBankCity());
		destinationBankInfoMap.put("bankName", remittancePlan.getCpBankName());
		String destinationBankInfo = JSON.toJSONString(destinationBankInfoMap, SerializerFeature.DisableCircularReferenceDetect);
		
		int transactionType = remittancePlan.getTransactionType().ordinal();
		String bankAccountHolder = remittancePlan.getCpBankAccountHolder();
		String bankCardNo = remittancePlan.getCpBankCardNo();
		
		BigDecimal fstTransactionAmount = remittancePlan.getPlannedTotalAmount();
		int priorityLevel = remittancePlan.getPriorityLevel();
		String fstGateWayRouterInfo = null;
		if(transactionType == AccountSide.DEBIT.ordinal()) {
			String debitBankCode = remittancePlan.getCpBankCode();
			String reckonAccount = remittancePlan.getPgClearingAccount();
			fstGateWayRouterInfo = getFstGateWayRouterInfoForDeduct(debitBankCode, reckonAccount);
		}
		String outlierTransactionUuid = remittancePlan.getRemittancePlanUuid();
		
		AccountSide accountSide = EnumUtil.fromOrdinal(AccountSide.class, transactionType);
		return new TradeSchedule(accountSide, bankAccountHolder, bankCardNo, destinationAccountAppendix, destinationBankInfo, null, outlierTransactionUuid, UUID.randomUUID().toString(), fstPaymentChannelUuid, null, fstTransactionAmount, remittanceApplicationUuid, null, remittanceDetailUuid, relatedBusinessRecordNo, priorityLevel, paymentGateway, channelName, fstGateWayRouterInfo);
	}
	
	private String getFstGateWayRouterInfoForDeduct(String standardBankCode, String reckonAccount) {
		String debitMode = unionpayBankConfigService.isUseBatchDeduct(null, standardBankCode) ? "batchMode" : "realTimeMode";
		
		Map<String,String> fstGateWayRouterInfoMap = new HashMap<String,String>();
		fstGateWayRouterInfoMap.put("debitMode",debitMode);
		
		if(StringUtils.isNotEmpty(reckonAccount)) {
			fstGateWayRouterInfoMap.put("reckonAccount", reckonAccount);
		}
		
		return JSON.toJSONString(fstGateWayRouterInfoMap);
	}
	
	@Override
	public void processingAndUpdateRemittanceInfoForResend_NoRollback(TradeSchedule tradeSchedule) throws CommonException {
		Result result = jpmorganApiHelper.sendBatchTradeSchedules(Arrays.asList(tradeSchedule));
		
		String remittanceApplicationUuid = tradeSchedule.getBatchUuid();
		String remittancePlanUuid = tradeSchedule.getOutlierTransactionUuid();
		String remittanceExecReqNo = tradeSchedule.getSourceMessageUuid();
		
		if(!result.isValid()) {
			logger.error("放款请求受理失败，递交对端失败!");
			updateRemittancePlanExecLogAfterSendFailBy(remittanceExecReqNo, "系统繁忙，请稍后再试");
			throw new CommonException(GlobalCodeSpec.CODE_FAILURE, "系统繁忙，请稍后再试");
		}
		
		String responseStr = String.valueOf(result.get(HttpClientUtils.DATA_RESPONSE_PACKET));
		Result responseResult = JsonUtils.parse(responseStr, Result.class);
		if(responseResult != null && !responseResult.isValid()) {
			logger.error("放款请求受理失败，对端响应（"+responseResult.getMessage()+"）!");
			updateRemittancePlanExecLogAfterSendFailBy(remittanceExecReqNo, "放款请求受理失败!");
			throw new CommonException(GlobalCodeSpec.CODE_FAILURE, "放款请求受理失败!");
		}
		
		updateRemittanceStatusAfterResendSuccessBy(remittanceApplicationUuid, remittancePlanUuid);
		logger.info("放款请求受理成功，响应结果（"+JsonUtils.toJsonString(result)+"）");
	}
	
	/**
	 * 更新线上代付单状态（交易受理失败）
	 * @param commandModel
	 * @param tradeSchedules
	 */
	private void updateRemittancePlanExecLogAfterSendFailBy(String remittanceExecReqNo, String executionRemark) {
		Map<String, Object> paramsForFail = new HashMap<String, Object>();
		paramsForFail.put("executionStatus", ExecutionStatus.FAIL.ordinal());
		paramsForFail.put("executionRemark", executionRemark);
		paramsForFail.put("lastModifiedTime", new Date());
		paramsForFail.put("execReqNo", remittanceExecReqNo);
		
		genericDaoSupport.executeSQL(
				"UPDATE t_remittance_plan_exec_log "
					+ " SET execution_status =:executionStatus, execution_remark =:executionRemark, last_modified_time =:lastModifiedTime "
					+ " WHERE exec_req_no =:execReqNo", paramsForFail);
	}
	
	/**
	 * 更新放款相关单据状态，为处理中（以便进行重新同步）
	 * @param remittanceApplicationUuid
	 */
	private void updateRemittanceStatusAfterResendSuccessBy(
			String remittanceApplicationUuid, String remittancePlanUuid) {
		if(StringUtils.isEmpty(remittanceApplicationUuid) || StringUtils.isEmpty(remittancePlanUuid)) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("executionStatus", ExecutionStatus.PROCESSING.ordinal());
		params.put("remittanceApplicationUuid", remittanceApplicationUuid);
		params.put("remittancePlanUuid", remittancePlanUuid);
		genericDaoSupport.executeSQL(
				"UPDATE t_remittance_application "
					+ "SET execution_status =:executionStatus "
					+ "WHERE remittance_application_uuid =:remittanceApplicationUuid", params);
		genericDaoSupport.executeSQL(
				"UPDATE t_remittance_application_detail "
						+ "SET execution_status =:executionStatus "
						+ "WHERE remittance_application_uuid =:remittanceApplicationUuid", params);
		genericDaoSupport.executeSQL(
				"UPDATE t_remittance_plan "
						+ "SET execution_status =:executionStatus "
						+ "WHERE remittance_plan_uuid =:remittancePlanUuid", params);
	}
	
}