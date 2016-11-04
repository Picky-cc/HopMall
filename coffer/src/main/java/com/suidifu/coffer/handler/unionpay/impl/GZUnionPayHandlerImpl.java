package com.suidifu.coffer.handler.unionpay.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.suidifu.coffer.entity.DebitModel;
import com.suidifu.coffer.entity.DebitResult;
import com.suidifu.coffer.entity.unionpay.gz.GZUnionPayBatchDebitModel;
import com.suidifu.coffer.entity.unionpay.gz.GZUnionPayDebitQueryModel;
import com.suidifu.coffer.entity.unionpay.gz.GZUnionPayRealTimeDebitModel;
import com.suidifu.coffer.entity.unionpay.gz.GZUnionPayRealTimePaymentModel;
import com.suidifu.coffer.entity.unionpay.gz.GZUnionPayTransactionDetailQueryModel;
import com.suidifu.coffer.entity.unionpay.gz.base.GZUnionPayBaseModel;
import com.suidifu.coffer.handler.unionpay.GZUnionpayHandler;
import com.suidifu.coffer.util.GZUnionPayUtil;
@Component("gzUnionpayHandler")
public class GZUnionPayHandlerImpl implements GZUnionpayHandler{

	private static Log logger = LogFactory.getLog(GZUnionPayHandlerImpl.class);
	
	private DebitResult executeRequest(GZUnionPayBaseModel unionPayBaseModel, Map<String, String> workParms) {
		try {
			unionPayBaseModel.process(workParms);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("报文封装出错", e);
		}
		return GZUnionPayUtil.executePostRequest(unionPayBaseModel.getXmlPacket(), unionPayBaseModel);
	}

	@Override
	public DebitResult realTimeDebit(DebitModel realTimeDebitModel, Map<String, String> workParms) {
		return executeRequest((GZUnionPayRealTimeDebitModel) realTimeDebitModel, workParms);
	}

	@Override
	public DebitResult batchDebit(DebitModel batchDebitModel, Map<String, String> workParms) {
		return executeRequest((GZUnionPayBatchDebitModel) batchDebitModel, workParms);
	}

	@Override
	public DebitResult debitQuery(DebitModel batchDebitQueryModel, Map<String, String> workParms) {
		return executeRequest((GZUnionPayDebitQueryModel) batchDebitQueryModel, workParms);
	}

	@Override
	public DebitResult transactionDetailQuery(DebitModel transactionDetailQueryModel, Map<String, String> workParms) {
		return executeRequest((GZUnionPayTransactionDetailQueryModel) transactionDetailQueryModel, workParms);
	}

	@Override
	public DebitResult realTimePayment(DebitModel reanTimePaymentModel, Map<String, String> workParms) {
		return executeRequest((GZUnionPayRealTimePaymentModel) reanTimePaymentModel, workParms);
	}

}
