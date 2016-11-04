package com.zufangbao.earth.yunxin.unionpay.model.interfaces;

import com.zufangbao.sun.entity.financial.PaymentChannel;

/**
 * 扣款结果查询接口定义
 * @author zhanghongbing
 *
 */
public interface IDeductResultQueryModel {

	public String getQueryReqNo();
	
	public PaymentChannel getPaymentChannel();
	
	public void setQueryReqNo(String queryReqNo);

	public void setPaymentChannel(PaymentChannel paymentChannel);
	
}
