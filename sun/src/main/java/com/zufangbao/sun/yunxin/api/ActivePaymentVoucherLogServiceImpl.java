package com.zufangbao.sun.yunxin.api;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.yunxin.entity.api.ActivePaymentVoucherLog;

@Service("activePaymentVoucherLogService")
public class ActivePaymentVoucherLogServiceImpl extends GenericServiceImpl<ActivePaymentVoucherLog> implements ActivePaymentVoucherLogService {

	@Override
	public List<ActivePaymentVoucherLog> getLogByRequestNo(String requestNo) {
		Filter filter = new Filter();
		filter.addEquals("requestNo", requestNo);
		List<ActivePaymentVoucherLog> result = this.list(ActivePaymentVoucherLog.class, filter);
		return result;
	}

	@Override
	public BigDecimal getLogAmountByRequestNo(String requestNo) {
		ActivePaymentVoucherLog log = getLog(requestNo);
		if(log == null) {
			return BigDecimal.ZERO;
		}else {
			return log.getVoucherAmount();
		}
	}

	@Override
	public ActivePaymentVoucherLog getLog(String requestNo) {
		List<ActivePaymentVoucherLog> logs = getLogByRequestNo(requestNo);
		if(CollectionUtils.isEmpty(logs)) {
			return null;
		}else {
			return logs.get(0);
		}
	}

}
