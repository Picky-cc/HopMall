package com.zufangbao.sun.yunxin.api;

import java.math.BigDecimal;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.yunxin.entity.api.BusinessPaymentVoucherLog;

public interface BusinessPaymentVoucherLogService extends GenericService<BusinessPaymentVoucherLog> {

	List<BusinessPaymentVoucherLog> getLogByRequestNo(String requestNo);

	BigDecimal getLogAmountByRequestNo(String requestNo);
	
	BusinessPaymentVoucherLog getLog(String requestNo);
}
