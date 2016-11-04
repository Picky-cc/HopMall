package com.zufangbao.earth.yunxin.handler.remittance;

import java.util.List;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceRefundBillQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceRefundBillShowModel;

public interface RemittanceRefundBillHandler {
	
	public List<RemittanceRefundBillShowModel> queryShowModelList(RemittanceRefundBillQueryModel queryModel,Page page);
	
}
