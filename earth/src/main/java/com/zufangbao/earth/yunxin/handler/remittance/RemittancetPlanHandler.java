package com.zufangbao.earth.yunxin.handler.remittance;

import java.util.List;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.gluon.api.jpmorgan.model.TradeSchedule;
import com.zufangbao.gluon.exception.CommonException;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanShowModel;


public interface RemittancetPlanHandler {
	
	public List<RemittancePlanShowModel>  queryShowModelList(RemittancePlanQueryModel queryModel, Page page);
	
	/**
	 * 生成线上代付单，在重新执行放款单前
	 */
	public TradeSchedule saveRemittanceInfoBeforeResendForFailedPlan(String remittancePlanUuid) throws CommonException;

	/**
	 * 处理且更新放款状态（重新执行放款单）
	 */
	public void processingAndUpdateRemittanceInfoForResend_NoRollback(TradeSchedule tradeSchedule) throws CommonException;
	
}
