package com.zufangbao.sun.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.utils.StringUtils;
import com.zufangbao.sun.entity.finance.BatchPayRecord;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.BatchPayRecordService;
import com.zufangbao.sun.service.TransferApplicationService;

@Service("batchPayRecordService")
public class BatchPayRecordServiceImpl extends GenericServiceImpl<BatchPayRecord> implements
		BatchPayRecordService {

	@Autowired
	private TransferApplicationService transferApplicationService;
	
	@Override
	public void updateTranDateTimeAndResponseData(BatchPayRecord batchPayRecord, String sendTime, String rtnReqNo, String requestData, String responseData) {
		batchPayRecord.updateTranDateTimeAndResponseData(sendTime, requestData, responseData, rtnReqNo);
		this.update(batchPayRecord);
	}

	@Override
	public BatchPayRecord getBatchPayRecord(String batchPayRecordUuid) {
		if(StringUtils.isEmpty(batchPayRecordUuid)){
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("batchPayRecordUuid", batchPayRecordUuid);
		List<BatchPayRecord> batchPayRecordList = this.list(BatchPayRecord.class, filter);
		if(CollectionUtils.isEmpty(batchPayRecordList)){
			return null;
		}
		return batchPayRecordList.get(0);
	}

	@Override
	public Order getOrderByBathPayRecordUuid(String batchPayRecordUuid) {
		BatchPayRecord batchPayRecord = getBatchPayRecord(batchPayRecordUuid);
		if(batchPayRecord==null){
			return null;
		}
		TransferApplication transferApplication = transferApplicationService.getSingleTransferApplicationBy(batchPayRecord.getId());
		if(transferApplication==null){
			return null;
		}
		return transferApplication.getOrder();
		
	}
	
	

}
