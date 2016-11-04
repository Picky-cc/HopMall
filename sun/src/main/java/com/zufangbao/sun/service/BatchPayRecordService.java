package com.zufangbao.sun.service;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.finance.BatchPayRecord;
import com.zufangbao.sun.entity.order.Order;

public interface BatchPayRecordService extends GenericService<BatchPayRecord> {

	public void updateTranDateTimeAndResponseData(BatchPayRecord batchPayRecord, String sendTime, String rtnReqNo, String requestData, String responseData);

	public BatchPayRecord getBatchPayRecord(String batchPayRecordUuid);
	
	public Order getOrderByBathPayRecordUuid(String batchPayRecordUuid);
}
