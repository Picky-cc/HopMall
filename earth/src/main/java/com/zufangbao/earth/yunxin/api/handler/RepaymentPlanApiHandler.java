package com.zufangbao.earth.yunxin.api.handler;

import java.util.List;

import com.zufangbao.earth.yunxin.api.model.query.RepaymentPlanDetail;
import com.zufangbao.earth.yunxin.api.model.query.RepaymentPlanQueryModel;

public interface RepaymentPlanApiHandler {
	
	public List<RepaymentPlanDetail> queryRepaymentPlanDetail(RepaymentPlanQueryModel queryModel);

}
