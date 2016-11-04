package com.zufangbao.sun.service;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.financial.PaymentChannel;
import com.zufangbao.sun.entity.financial.PaymentChannelType;

public interface PaymentChannelService extends GenericService<PaymentChannel> {
	public PaymentChannel getPaymentChannel(String merchantId, PaymentChannelType paymentChannelType);
}
