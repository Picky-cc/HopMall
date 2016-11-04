package com.zufangbao.sun.yunxin.service.remittance;

import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceRefundBill;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceRefundBillQueryModel;

public interface IRemittanceRefundBillService extends GenericService<RemittanceRefundBill> {
	
	public List<RemittanceRefundBill> queryRemittanceRefundBill(RemittanceRefundBillQueryModel queryModel, Page page);

	public int queryCount(RemittanceRefundBillQueryModel queryModel);
	
	public List<RemittanceRefundBill> listRemittanceRefundBill(String relatedExecReqNo);
}
