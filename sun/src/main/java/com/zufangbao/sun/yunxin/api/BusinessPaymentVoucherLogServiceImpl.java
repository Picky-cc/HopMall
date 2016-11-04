package com.zufangbao.sun.yunxin.api;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.yunxin.entity.api.BusinessPaymentVoucherLog;

@Service("businessPaymentVoucherLogService")
public class BusinessPaymentVoucherLogServiceImpl extends GenericServiceImpl<BusinessPaymentVoucherLog> implements BusinessPaymentVoucherLogService {

	@Override
	public List<BusinessPaymentVoucherLog> getLogByRequestNo(String requestNo) {
		Filter filter = new Filter();
		filter.addEquals("requestNo", requestNo);
		List<BusinessPaymentVoucherLog> result = this.list(BusinessPaymentVoucherLog.class, filter);
		return result;
	}

	@Override
	public BigDecimal getLogAmountByRequestNo(String requestNo) {
		BusinessPaymentVoucherLog log = getLog(requestNo);
		if(log == null) {
			return BigDecimal.ZERO;
		}else {
			return log.getVoucherAmount();
		}
	}

	@Override
	public BusinessPaymentVoucherLog getLog(String requestNo) {
		List<BusinessPaymentVoucherLog> logs = getLogByRequestNo(requestNo);
		if(CollectionUtils.isEmpty(logs)) {
			return null;
		}else {
			return logs.get(0);
		}
	}

	
}
