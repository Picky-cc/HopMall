package com.zufangbao.sun.yunxin.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.unionpay.UnionpayManualTransaction;
import com.zufangbao.sun.yunxin.service.UnionpayManualTransactionService;

@Service("UnionpayManualTransactionService")
public class UnionpayManualTransactionServiceImpl extends GenericServiceImpl<UnionpayManualTransaction> implements UnionpayManualTransactionService{

	@Override
	public UnionpayManualTransaction getUnionpayManualTransactionServiceByReqNo(
			String reqNo) {
		Filter filter = new Filter();
		filter.addEquals("unionpayTransactionNo",reqNo);
		List<UnionpayManualTransaction> list = this.list(UnionpayManualTransaction.class, filter);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}

}
