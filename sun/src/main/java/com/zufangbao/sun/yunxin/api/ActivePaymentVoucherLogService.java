package com.zufangbao.sun.yunxin.api;

import java.math.BigDecimal;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.yunxin.entity.api.ActivePaymentVoucherLog;

public interface ActivePaymentVoucherLogService extends GenericService<ActivePaymentVoucherLog> {

	List<ActivePaymentVoucherLog> getLogByRequestNo(String requestNo);

	BigDecimal getLogAmountByRequestNo(String requestNo);
	
	ActivePaymentVoucherLog getLog(String requestNo);

}
