package com.zufangbao.earth.yunxin.handler;

import java.io.Serializable;
import java.util.List;

/**
 * 提前还款处理
 * @author zhanghongbing
 *
 */
public interface PrepaymentHandler {

	/**
	 * 创建提前还款，线上支付单，返回Ids
	 * @return
	 */
	public List<Serializable> createPrepaymentTransferApplication();

	/**
	 * 处理提前还款申请（还款成功时）
	 * @param repaymentPlanUuids 提前还款成功的还款计划uuid
	 */
	public void processingPrepaymentApplicationAfterSuccess(List<String> repaymentPlanUuids);

	/**
	 * 处理提前还款申请（还款失败时）
	 * @param repaymentPlanUuids 提前还款成功的还款计划uuid
	 */
	public void processingPrepaymentApplicationAfterFail(List<String> repaymentPlanUuids);
	
}
