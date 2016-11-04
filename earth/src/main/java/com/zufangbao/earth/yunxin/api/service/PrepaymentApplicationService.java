package com.zufangbao.earth.yunxin.api.service;

import com.demo2do.core.service.GenericService;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.sun.yunxin.entity.api.PrepaymentApplication;

public interface PrepaymentApplicationService extends GenericService<PrepaymentApplication>{
	
	public void checkByRequestNo(String requestNo) throws ApiException;
	
	/**
	 * 通过提前还款的还款计划uuid，查询提前还款申请
	 * @param repaymentPlanUuid 提前还款的还款计划uuid
	 * @return 提前还款申请
	 */
	public PrepaymentApplication getUniquePrepaymentApplicationByRepaymentPlanUuid(String repaymentPlanUuid);

}
