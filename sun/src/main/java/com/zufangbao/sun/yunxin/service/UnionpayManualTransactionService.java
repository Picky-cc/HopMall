package com.zufangbao.sun.yunxin.service;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.unionpay.UnionpayManualTransaction;

public interface UnionpayManualTransactionService extends GenericService<UnionpayManualTransaction>{
	public UnionpayManualTransaction getUnionpayManualTransactionServiceByReqNo(String reqNo);
}
