package com.zufangbao.earth.yunxin.unionpay.component.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zufangbao.earth.api.TransactionDetailConstant.ErrMsg;
import com.zufangbao.earth.api.TransactionDetailConstant.RTNCode;
import com.zufangbao.earth.api.exception.TransactionDetailApiException;
import com.zufangbao.earth.yunxin.unionpay.component.IGZUnionPayApiComponent;
import com.zufangbao.earth.yunxin.unionpay.model.AccountDetailQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.BatchDeductInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.BatchQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.RealTimeDeductInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.RealTimePaymentInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailQueryResult;
import com.zufangbao.earth.yunxin.unionpay.model.basic.GZUnionPayResult;
import com.zufangbao.earth.yunxin.unionpay.model.basic.GZUnionPayRtnInfo;
import com.zufangbao.earth.yunxin.unionpay.template.gz.GZUnionPayPacketFactory;
import com.zufangbao.earth.yunxin.unionpay.util.GZUnionPayUtil;

/**
 * 广州银联接口实现
 * @author zhanghongbing
 *
 */
@Component("GZUnionPayApiComponentImpl")
public class GZUnionPayApiComponentImpl implements IGZUnionPayApiComponent {
	
	private static Log log = LogFactory.getLog(GZUnionPayApiComponentImpl.class);

	@Override
	public GZUnionPayResult execBatchDeduct(BatchDeductInfoModel batchDeductInfoModel) {
		String xmlPacket = GZUnionPayPacketFactory.generateBatchDeductPacket(batchDeductInfoModel);
		return GZUnionPayUtil.executePostRequest(xmlPacket, batchDeductInfoModel);
	}
	
	@Override
	public GZUnionPayResult execRealTimeDeductPacket(RealTimeDeductInfoModel realTimeDeductInfoModel) {
		String xmlPacket = GZUnionPayPacketFactory.generateRealTimeDeductPacket(realTimeDeductInfoModel);
		return GZUnionPayUtil.executePostRequest(xmlPacket, realTimeDeductInfoModel);
	}

	@Override
	public GZUnionPayResult execBatchQuery(BatchQueryInfoModel batchQueryInfoModel) {
		String xmlPacket = GZUnionPayPacketFactory.generateBatchQueryPacket(batchQueryInfoModel);
		return GZUnionPayUtil.executePostRequest(xmlPacket, batchQueryInfoModel);
	}

	@Override
	public TransactionDetailQueryResult execTransactionDetailQuery(
			TransactionDetailQueryInfoModel model) throws TransactionDetailApiException {
		String xmlPacket = GZUnionPayPacketFactory.generateTransactionDetailQueryPacket(model);
		GZUnionPayResult result = GZUnionPayUtil.executePostRequest(xmlPacket, model);
		validSuccess(result);
		TransactionDetailQueryResult queryResult = TransactionDetailQueryResult.initialization(result);
		log.info("交易明细查询结果（已解析）:" + JSON.toJSONString(queryResult));
		return queryResult;
	}

	private void validSuccess(GZUnionPayResult result) throws TransactionDetailApiException {
		if(!result.isValid()){
			throw new TransactionDetailApiException(RTNCode.OTHER_ERROR,result.getMessage());
		}
		GZUnionPayRtnInfo gzUnionPayRtnInfo = TransactionDetailQueryResult.parseInfo(result);
		if(gzUnionPayRtnInfo==null){
			throw new TransactionDetailApiException(RTNCode.OTHER_ERROR,ErrMsg.ERR_MSG_UNIONPAY_XML_FORMAT);
		}
		if(!gzUnionPayRtnInfo.isSuc()){
			throw new TransactionDetailApiException(RTNCode.OTHER_ERROR,gzUnionPayRtnInfo.getErrMsg());
		}
	}

	@Override
	public GZUnionPayResult execAccountDetailQuery(AccountDetailQueryInfoModel model) {
		String xmlPacket = GZUnionPayPacketFactory.generateAccountDetailQueryPacket(model);
		GZUnionPayResult result = GZUnionPayUtil.executePostRequest(xmlPacket, model);
		return result;
	}

	@Override
	public GZUnionPayResult execRealTimePayment(RealTimePaymentInfoModel model) {
		String xmlPacket = GZUnionPayPacketFactory.generateRealTimePaymentPacket(model);
		GZUnionPayResult result = GZUnionPayUtil.executePostRequest(xmlPacket, model);
		return result;
	}

/*	@Override
	public GZUnionPayResult execTransactionResultQuery(BatchQueryInfoModel queryModel) {
		String xmlPacket = GZUnionPayPacketFactory.generateBatchQueryPacket(queryModel);
		GZUnionPayResult result = GZUnionPayUtil.executePostRequest(xmlPacket, queryModel);
		return result;
	}
*/
}
