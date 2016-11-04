package com.zufangbao.sun.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.financial.PaymentChannel;
import com.zufangbao.sun.entity.financial.PaymentChannelType;
import com.zufangbao.sun.service.PaymentChannelService;

@Service("paymentChannelService")
public class PaymentChannelServiceImpl extends
		GenericServiceImpl<PaymentChannel> implements PaymentChannelService {

	@Override
	public PaymentChannel getPaymentChannel(String merchantId, PaymentChannelType paymentChannelType) {
		Filter filter = new Filter();
		filter.addEquals("merchantId", merchantId);
		filter.addEquals("paymentChannelType", paymentChannelType);
		List<PaymentChannel> paymentChannelList = this.list(PaymentChannel.class, filter);
		if(CollectionUtils.isEmpty(paymentChannelList)){
			return null;
		}
		return paymentChannelList.get(0);
	}

}
