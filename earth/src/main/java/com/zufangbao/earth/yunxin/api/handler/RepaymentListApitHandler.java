package com.zufangbao.earth.yunxin.api.handler;

import java.util.List;

import com.zufangbao.earth.yunxin.api.model.RepaymentListDetail;
import com.zufangbao.earth.yunxin.api.model.query.RepaymentListQueryModel;

public interface RepaymentListApitHandler {
	
	public List<RepaymentListDetail> queryRepaymentList(RepaymentListQueryModel queryModel);
	
}
