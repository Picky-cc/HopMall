package com.zufangbao.gluon.api.jpmorgan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo2do.core.entity.Result;
import com.zufangbao.gluon.api.jpmorgan.model.TradeSchedule;
import com.zufangbao.gluon.opensdk.HttpClientUtils;

@Component
public class JpmorganApiHelper {
	
	@Value("#{config['jpmorganHost']}")
	private String JPMORGAN_HOST = "";
	
	/**
	 * 批量支付
	 */
	private static final String TRADESCHEDULE_BATCHPAYMENT = "tradeSchedule/batchPayment";

	/**
	 * 批量查询
	 */
	private static final String TRADESCHEDULE_QUERYSTATUS = "tradeSchedule/queryStatus";
	
	/**
	 * 发送一批交易计划
	 * @param tradeSchedules
	 */
	public Result sendBatchTradeSchedules(List<TradeSchedule> tradeSchedules) {
		String requestBody = JSON.toJSONString(tradeSchedules, SerializerFeature.DisableCircularReferenceDetect);
		return HttpClientUtils.executePostRequest(JPMORGAN_HOST.trim() + TRADESCHEDULE_BATCHPAYMENT, requestBody, null);
	}
	
	/**
	 * 查询交易结果
	 * @param transactionUuid 交易计划uuid
	 * @param batchUuid 交易批次uuid
	 * @return
	 */
	public Result queryTradeSchedulesStatus(String transactionUuid, String batchUuid) {
		Map<String, String> params = new HashMap<String, String>();
		if(StringUtils.isNotEmpty(transactionUuid)) {
			params.put("transactionUuid", transactionUuid);
		}
		if(StringUtils.isNotEmpty(batchUuid)) {
			params.put("batchUuid", batchUuid);
		}
		return HttpClientUtils.executePostRequest(JPMORGAN_HOST.trim() + TRADESCHEDULE_QUERYSTATUS, params, null);
	}
	
	/**
	 * 构建执行前置条件
	 * @param planUuidsForStart 开始条件为：全部成功
	 * @param planUuidsForAbandon 撤销条件为：全部失败或撤销
	 * @return Json格式执行前置条件
	 */
	public String buildPreCond(String[] planUuidsForStart, String[] planUuidsForAbandon) {
		Map<String, Object> preCondMap = new HashMap<String, Object>();
		if(ArrayUtils.isNotEmpty(planUuidsForStart)) {
			preCondMap.put("start", planUuidsForStart);
		}
		if(ArrayUtils.isNotEmpty(planUuidsForAbandon)) {
			preCondMap.put("abandon", planUuidsForAbandon);
		}
		if(!preCondMap.keySet().isEmpty()) {
			return JSON.toJSONString(preCondMap, SerializerFeature.DisableCircularReferenceDetect);
		}
		return null;
	}
	
}
