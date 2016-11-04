package com.zufangbao.earth.yunxin.handler.remittance.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.yunxin.handler.remittance.RemittanceRefundBillHandler;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceRefundBill;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceRefundBillQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceRefundBillShowModel;
import com.zufangbao.sun.yunxin.service.remittance.IRemittanceRefundBillService;


@Component("remittanceRefundBillHandler")
public class RemittanceRefundBillHandlerImpl implements RemittanceRefundBillHandler{
	
	@Autowired
	private IRemittanceRefundBillService remittanceRefundBillService;

	@Override
	public List<RemittanceRefundBillShowModel> queryShowModelList(RemittanceRefundBillQueryModel queryModel, Page page) {
		
		List<RemittanceRefundBill> refundBills = remittanceRefundBillService.queryRemittanceRefundBill(queryModel, page);
		List<RemittanceRefundBillShowModel> showModels = new ArrayList<>();
		
		if(CollectionUtils.isNotEmpty(refundBills)){
			showModels = refundBills.stream().map(a->new RemittanceRefundBillShowModel(a)).collect(Collectors.toList());
		}
		return showModels;
	}

}
