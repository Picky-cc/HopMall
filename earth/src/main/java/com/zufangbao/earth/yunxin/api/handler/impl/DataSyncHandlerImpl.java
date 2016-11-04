package com.zufangbao.earth.yunxin.api.handler.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.api.data.sync.DataSyncMapSpec;
import com.zufangbao.earth.yunxin.api.dataSync.task.SyncRequestData;
import com.zufangbao.earth.yunxin.api.handler.DataSyncHandler;
import com.zufangbao.earth.yunxin.api.util.ApiSignUtils;
import com.zufangbao.gluon.opensdk.HttpClientUtils;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.utils.AmountUtil;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AuditOverdueStatus;
import com.zufangbao.sun.yunxin.entity.GuaranteeStatus;
import com.zufangbao.sun.yunxin.entity.RepaymentPlanType;
import com.zufangbao.sun.yunxin.entity.api.DataSyncLog;
import com.zufangbao.sun.yunxin.entity.model.api.ExtraChargeSpec;
import com.zufangbao.sun.yunxin.service.DataSyncLogService;
import com.zufangbao.sun.yunxin.service.DictionaryService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanExtraChargeService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.yunxin.entity.api.syncdata.model.DataSyncRequestModel;
import com.zufangbao.yunxin.entity.api.syncdata.model.DataSyncStatus;
import com.zufangbao.yunxin.entity.api.syncdata.model.RepayType;
import com.zufangbao.yunxin.entity.api.syncdata.model.Status;
import com.zufangbao.yunxin.entity.api.syncdata.model.SyncDataDecimalModel;

@Component("dataSyncHandler")
public class DataSyncHandlerImpl implements DataSyncHandler {
	
	private static final int SINGLE_RULE = 1;

	private static final int BATCH_RULE = 0;
	
	@Autowired
	private RepaymentPlanService repaymentPlanService;

	@Autowired
	private DataSyncLogService dataSyncLogService;

	@Autowired
	private RepaymentPlanExtraChargeService repaymentPlanExtraChargeService;

	@Autowired
	private FinancialContractService financialContractService;

	@Autowired
	private DictionaryService dictionaryService;

	@Value("#{config['yx.syncdata.url']}")
	private String YX_SYNC_DATA_URL = null;

	@Value("#{config['yx.api.merId']}")
	private String YX_API_MERID = null;

	@Value("#{config['yx.api.secretkey']}")
	private String YX_API_SECRETKEY = null;

	private static final Log logger = LogFactory.getLog(DataSyncHandlerImpl.class);

	//TODO
	@Override
	public List<String> getPendingRepaymentPlanList() {
		List<String> resultAssetSetUuids = new ArrayList<>();

		List<String> repaymentSuccessList = repaymentPlanService.getPaidOffAndNoSyncRepaymentPlans();
		if (CollectionUtils.isNotEmpty(repaymentSuccessList)) {
			resultAssetSetUuids.addAll(repaymentSuccessList);
		}
		List<String> repaymentNotSuccessList = repaymentPlanService
				.getNotPaidOffAndHasGuaranteeRepaymentPlans();
		if (CollectionUtils.isNotEmpty(repaymentNotSuccessList)) {
			resultAssetSetUuids.addAll(repaymentNotSuccessList);
		}

		return resultAssetSetUuids;
	}

	@Override
	public RepayType castRepayType(AssetSet assetSet) {

		int repaymentStatus = assembleRepaymentType(assetSet);
		int overDueStatus = assembleOverDueStatus(assetSet);
		int guaranteeStatus = assembleGuaranteeStatus(assetSet);
		int advanceRepaymentStatus = assembleAdvanceRepaymentStatus(assetSet);

		return DataSyncMapSpec.RepaymentTypeMap
				.get(castStatusMapString(repaymentStatus, overDueStatus, guaranteeStatus, advanceRepaymentStatus));

	}

	private String castStatusMapString(int repaymentStatus, int overDueStatus, int guaranteeStatus,
			int advanceRepaymentStatus) {
		StringBuffer stringBuffer = new StringBuffer(5);
		return stringBuffer.append(advanceRepaymentStatus).append(guaranteeStatus).append(overDueStatus)
				.append(repaymentStatus).toString();

	}
	//TODO
	// 0正常 1 提前全部 2 提前部分
	private int assembleAdvanceRepaymentStatus(AssetSet assetSet) {

		if (assetSet.getRepaymentPlanType() == RepaymentPlanType.PREPAYMENT) {
			if (assetSet.getCurrentPeriod() == assetSet.getContract().getPeriods()) {
				return 1;
			}
			return 2;
		}
		return 0;
	}

	private int assembleGuaranteeStatus(AssetSet assetSet) {

		if (assetSet.getGuaranteeStatus() == GuaranteeStatus.HAS_GUARANTEE) {
			return 1;
		}
		return 0;
	}

	private int assembleOverDueStatus(AssetSet assetSet) {
		if (assetSet.getOverdueStatus() == AuditOverdueStatus.OVERDUE) {
			return 1;
		}
		return 0;
	}

	private int assembleRepaymentType(AssetSet assetSet) {
		if (assetSet.isPaidOff()) {
			return 1;
		}
		return 0;

	}

	@Override
	public DataSyncLog generateSyncLog(String repaymentPlanUuid) {

		AssetSet repaymentPlan = repaymentPlanService.getUniqueRepaymentPlanByUuid(repaymentPlanUuid);
		
		RepayType repayType = castRepayType(repaymentPlan);
		if (repayType == null) {
			return null;
		}
		if (dataSyncLogService.countSuccessAssetSet(repaymentPlanUuid, repayType) != 0) { // 已同步 
			return null;
		}
		
		Map<String, BigDecimal> amountMap = repaymentPlanExtraChargeService
				.getAssetSetExtraChargeModels(repaymentPlan.getAssetUuid());

		BigDecimal penaltyFee = calcPenaltyFee(amountMap, repaymentPlan);

		// 未还
		SyncDataDecimalModel syncDataDecimalModel = new SyncDataDecimalModel(repaymentPlan, penaltyFee);
		// 已还
		if (assembleRepaymentType(repaymentPlan) == 1) {
			syncDataDecimalModel = new SyncDataDecimalModel(repaymentPlan, amountMap, penaltyFee);
		}
		FinancialContract financialContract = financialContractService
				.getFinancialContractByLoanContractUniqueId(repaymentPlan.getContract().getUniqueId());
		AssetSet lastRepaymentPlan = repaymentPlanService.getLastPeriodInTheContract(repaymentPlan.getContract());
		return new DataSyncLog(repaymentPlan, syncDataDecimalModel, repayType, financialContract,lastRepaymentPlan);

	}

	private BigDecimal calcPenaltyFee(Map<String, BigDecimal> amountMap, AssetSet repaymentPlan) {

		if (CollectionUtils.isEmpty(amountMap.keySet())) {
			return repaymentPlan.getPenaltyInterestAmount();
		}
		return AmountUtil.getAmountFromMap(amountMap, ExtraChargeSpec.PENALTY_KEY);
	}

	/**
	 * 0 批量 1 单条
	 */
	@Override
	public void execSingleNotifyForDataSync(List<DataSyncLog> dataSyncLogList, int sendRule) {

		logger.info("#数据同步接口回调，开始同步,同步时间["+DateUtils.format(new Date(), "yyyy-MM-dd HH::mm::SS")+"]");
		String notifyUrl = YX_SYNC_DATA_URL;

		
		if(sendRule == BATCH_RULE){
			sendHttpRequest(dataSyncLogList, notifyUrl);
		}
		if(sendRule == SINGLE_RULE){
			for(DataSyncLog dataSyncLog:dataSyncLogList){
				List<DataSyncLog> singleDataSyncLogList = new ArrayList<DataSyncLog>();
				singleDataSyncLogList.add(dataSyncLog);
				sendHttpRequest(singleDataSyncLogList, notifyUrl);
			}
		}
		
		
	}
	
	private void sendHttpRequest(List<DataSyncLog> dataSyncLogList, String notifyUrl) {
		
		String content = buildDataSyncRequestContent(dataSyncLogList);
		Map<String, String> headerParams = buildHeaderParamsForNotifyRemittanceResult(content);
		
		Result result = HttpClientUtils.executePostRequest(notifyUrl, content, headerParams);
		String responseStr = String.valueOf(result.get(HttpClientUtils.DATA_RESPONSE_PACKET));
		if (!result.isValid() || parseResponseString(responseStr) == null) {
			updateDataSyncLogAndStatuss(false, dataSyncLogList, "http通信失败，对端接受失败", content);
			logger.info("#同步数据接口：同步失败, 同步时间["+DateUtils.format(new Date(), "yyyy-MM-dd HH::mm::SS")+"] 失败原因：[http通信失败，对端接受失败]！");
			return;
		}
		Status status = parseResponseString(responseStr);
		if (status.getIsSuccess() == true && status != null) {
			updateDataSyncLogAndStatuss(true, dataSyncLogList, JsonUtils.toJsonString(status), content);
			logger.info("#同步数据接口：同步成功, 同步时间["+DateUtils.format(new Date(), "yyyy-MM-dd HH::mm::SS")+"],内容[" + content + "], 返回参数["+ responseStr + "]");
			return;
		}

		updateDataSyncLogAndStatuss(false, dataSyncLogList,  JsonUtils.toJsonString(status), content);
		logger.info("#同步数据接口：同步失败,同步时间["+DateUtils.format(new Date(), "yyyy-MM-dd HH::mm::SS")+"],失败原因[" + status.getResponseCode()
				+ "]"); 

		logger.info("#数据同步接口，结束同步,同步时间["+DateUtils.format(new Date(), "yyyy-MM-dd HH::mm::SS")+"] ");
	}

	private Status parseResponseString(String responseStr) {
		Map<String,Object> messageMap = JsonUtils.parse(responseStr);
		Status status = JsonUtils.parse(messageMap.get("Status").toString(), Status.class);
		if (status == null) {
			return null;
		}
		return status;
	}

	private void updateDataSyncLogAndStatuss(Boolean isSuccess, List<DataSyncLog> dataSyncLogList, String returnMessage, String requestMessage) {

		if(isSuccess == true ){
			dataSyncLogList.stream().forEach(hb -> updateAssetSetSyncStatus(hb));
		}
		    dataSyncLogList.stream().forEach(hb -> updateOperation(isSuccess,hb,returnMessage, requestMessage));
		
	}
	
	private void updateOperation(Boolean isSuccess,DataSyncLog dataSyncLog, String returnMessgae, String requestMessage){
		dataSyncLog.setIsSuccess(isSuccess);
		dataSyncLog.setLastModifyTime(new Date());
		dataSyncLog.setReturnMessage(returnMessgae);
		dataSyncLog.setRequestMessage(requestMessage);
		dataSyncLogService.save(dataSyncLog);
	}
	
	private void  updateAssetSetSyncStatus(DataSyncLog dataSyncLog){
		
		repaymentPlanService.updateAssetSetSyncStatusWhenIsPaidOff(dataSyncLog.getAssetSetUuid(), DataSyncStatus.HAS_SYNCED);
	}

	private String buildDataSyncRequestContent(List<DataSyncLog> dataSyncLogList) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("RequestId", UUID.randomUUID().toString());
		List<DataSyncRequestModel> requestModels = dataSyncLogList.stream().map(fc -> new DataSyncRequestModel(fc)).collect(Collectors.toList());
		result.put("SDFDatas", JsonUtils.toJsonString(requestModels));
		
		SyncRequestData  requestData = new SyncRequestData(requestModels);
		return JSON.toJSONString(requestData, SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteDateUseDateFormat);
	}
	

	private Map<String, String> buildHeaderParamsForNotifyRemittanceResult(String content) {
		Map<String, String> headerParams = new HashMap<String, String>();
		headerParams.put("Content-Type", "application/json");
		headerParams.put("merid", YX_API_MERID);
		headerParams.put("secretkey", YX_API_SECRETKEY);

		String privateKey = dictionaryService.getPlatformPrivateKey();
		String signedMsg = ApiSignUtils.rsaSign(content, privateKey);

		headerParams.put("signedmsg", signedMsg);
		return headerParams;
	}
}
