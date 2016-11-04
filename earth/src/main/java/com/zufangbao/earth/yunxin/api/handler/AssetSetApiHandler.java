package com.zufangbao.earth.yunxin.api.handler;

import java.util.List;

import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.model.modify.RepaymentPlanModifyModel;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.model.api.PrepaymentModifyModel;

public interface AssetSetApiHandler {

	public List<AssetSet> prepayment(Contract contract, PrepaymentModifyModel model, String ipAddress);

	public List<AssetSet> modify_repaymentPlan(Contract contract, RepaymentPlanModifyModel modifyModel, String ip, Integer oldActiveVersionNo) throws ApiException;

}
	