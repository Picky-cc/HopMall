package com.zufangbao.earth.api;

import java.util.List;

import com.zufangbao.earth.api.exception.TransactionDetailApiException;
import com.zufangbao.earth.api.xml.TransactionDetail;
import com.zufangbao.earth.api.xml.TransactionDetailQueryParams;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailQueryInfoModel;
import com.zufangbao.sun.entity.financial.PaymentChannelType;

public interface GZUnionPayApiHandler {
	
	public String executeBatchPayment(String reqXmlPacket);

	public String queryTransactionDetail(String reqPacketXml);
	/**
	 * 查询交易明细
	 * @return 银联交易明细查询结果
	 * @throws TransactionDetailApiException 
	 * 
	 */
	public List<TransactionDetail> execTransactionDetailQuery(TransactionDetailQueryParams transactionDetailOutQueryParams) throws TransactionDetailApiException;
	
	public List<TransactionDetail> execDirectBankTransactionDetailQuery(TransactionDetailQueryParams transactionDetailQueryParams) throws TransactionDetailApiException;

	public TransactionDetailQueryInfoModel convert(TransactionDetailQueryParams transactionDetailOutQueryParams, PaymentChannelType paymentChannelType) throws TransactionDetailApiException;
	
	public String execTransactionResultQuery(String reqXmlPacket);
}
