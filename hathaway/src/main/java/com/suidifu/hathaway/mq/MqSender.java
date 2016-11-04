package com.suidifu.hathaway.mq;

public interface MqSender {

	public MqResponse send(MqRequest request);
}
