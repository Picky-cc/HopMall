package com.zufangbao.earth.api;

import java.util.List;

import com.zufangbao.earth.api.xml.BatchPaymentProcessingResultXml;
import com.zufangbao.earth.api.xml.BatchPaymentReqXml;
import com.zufangbao.earth.api.xml.TransactionStatusQueryRspDetailXml;

public interface IPaymentAndQuery {

	public List<BatchPaymentProcessingResultXml> execBatchPayment(BatchPaymentReqXml reqXml, String reqXmlPacket) throws Exception ;
	
	public TransactionStatusQueryRspDetailXml execTransactionQuery(RequestRecord record) throws Exception;
}
