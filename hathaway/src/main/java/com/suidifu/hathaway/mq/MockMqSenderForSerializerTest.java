package com.suidifu.hathaway.mq;

import org.springframework.stereotype.Component;


@Component("mockMqSenderForSerializerTest")
public class MockMqSenderForSerializerTest implements MqSender {

	
	private MqRequest request;
	@Override
	public MqResponse send(MqRequest req) {
		request=req;
		return null;
	}
	
	public MqRequest getMockRequest()
	{
		return request;
	}

}
