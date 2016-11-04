package com.zufangbao.earth.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.zufangbao.earth.api.TransactionDetailConstant.FunctionName;
import com.zufangbao.earth.api.exception.TransactionDetailApiException;
import com.zufangbao.earth.api.xml.BatchPaymentProcessingResultXml;
import com.zufangbao.earth.api.xml.BatchPaymentReqXml;
import com.zufangbao.earth.api.xml.PaymentDetail;
import com.zufangbao.earth.api.xml.TransactionStatusQueryRspDetailXml;
import com.zufangbao.earth.api.xml.UnionpayRealTimePaymentRspXml;
import com.zufangbao.earth.api.xml.UnionpayRealTimePaymentRspXmlRetDetail;
import com.zufangbao.earth.api.xml.UnionpayTransactionResultQueryRetDetail;
import com.zufangbao.earth.api.xml.UnionpayTransactionResultXmlModel;
import com.zufangbao.earth.util.XmlUtil;
import com.zufangbao.earth.yunxin.unionpay.component.IGZUnionPayApiComponent;
import com.zufangbao.earth.yunxin.unionpay.constant.GZUnionPayConstants.GZUnionPayBusinessCode;
import com.zufangbao.earth.yunxin.unionpay.model.BatchQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.PaymentDetailInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.RealTimePaymentInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.basic.GZUnionPayResult;
import com.zufangbao.earth.yunxin.unionpay.model.basic.GZUnionPayRtnInfo;
import com.zufangbao.sun.entity.financial.GateWay;
import com.zufangbao.sun.entity.financial.PaymentChannel;
import com.zufangbao.sun.entity.financial.PaymentChannelType;
import com.zufangbao.sun.service.PaymentChannelService;
import com.zufangbao.sun.utils.FinanceUtils;

@Component("UnionpayPaymentAndQuery")
public class UnionpayPaymentAndQuery implements IPaymentAndQuery {
	
	@Autowired
	private IGZUnionPayApiComponent gZUnionPayHandler;
	
	@Autowired
	private RequestRecordService requestRecordService;
	
	@Autowired
	private PaymentChannelService paymentChannelService;

	@Override
	public List<BatchPaymentProcessingResultXml> execBatchPayment(
			BatchPaymentReqXml reqXml, String reqXmlPacket) throws TransactionDetailApiException {
		List<BatchPaymentProcessingResultXml> reee = new ArrayList<BatchPaymentProcessingResultXml>();
		List<PaymentDetail> paymentDetailList = reqXml.getPaymentDetailList();
		PaymentDetail paymentDetail = paymentDetailList.get(0);
		
		RealTimePaymentInfoModel model = genPaymentInfoModel(paymentDetail, reqXmlPacket);
		GZUnionPayResult result = gZUnionPayHandler.execRealTimePayment(model);
		XStream xStream = XmlUtil.xStream();
		xStream.alias("GZELINK", UnionpayRealTimePaymentRspXml.class);
		UnionpayRealTimePaymentRspXml rspXml = (UnionpayRealTimePaymentRspXml) xStream.fromXML(result.getResponsePacket());
		if (rspXml.getUnionpayRtnBody().getRetDetails() == null) {
//			throw new TransactionDetailApiException(
//					Integer.parseInt(rspXml.getUnionpayRtnInfo().getRetCode()), 
//					rspXml.getUnionpayRtnInfo().getErrMsg());
			reee.add(new BatchPaymentProcessingResultXml(paymentDetail.getReqNo(), "BNK", rspXml.getUnionpayRtnInfo().getRetCode(), rspXml.getUnionpayRtnInfo().getErrMsg()));

		}else{
			
			UnionpayRealTimePaymentRspXmlRetDetail retDetail = rspXml.getUnionpayRtnBody().getRetDetails().getRetDetail();
			reee.add(new BatchPaymentProcessingResultXml(paymentDetail.getReqNo(), "BNK", retDetail.getRetCode(), retDetail.getErrmsg()));
		}
		return reee;
	}

	@Override
	public TransactionStatusQueryRspDetailXml execTransactionQuery(RequestRecord record) throws Exception {
		 // 银联接口
		BatchQueryInfoModel infoModel = new BatchQueryInfoModel();
		PaymentChannel paymentChannel = paymentChannelService.getPaymentChannel(record.getMerchantId(), PaymentChannelType.GuangdongUnionPay);
		TransactionStatusQueryRspDetailXml detail = new TransactionStatusQueryRspDetailXml();
		if(paymentChannel != null){
			infoModel.setApiUrl(paymentChannel.getApiUrl());
			infoModel.setCerFilePath(paymentChannel.getCerFilePath());
			infoModel.setMerchantId(paymentChannel.getMerchantId());
			infoModel.setPfxFileKey(paymentChannel.getPfxFileKey());
			infoModel.setPfxFilePath(paymentChannel.getPfxFilePath());
			infoModel.setQueryReqNo(record.getTransactionUuid());
			infoModel.setReqNo(UUID.randomUUID().toString().replace("-", "")); // TODO
			infoModel.setUserName(paymentChannel.getUserName());
			infoModel.setUserPwd(paymentChannel.getUserPassword());
			GZUnionPayResult result = gZUnionPayHandler.execBatchQuery(infoModel);
			XStream xStream = XmlUtil.xStream();
			xStream.alias("GZELINK", UnionpayTransactionResultXmlModel.class);
			UnionpayTransactionResultXmlModel resultModel = (UnionpayTransactionResultXmlModel) xStream.fromXML(result.getResponsePacket());
			GZUnionPayRtnInfo rtnInfo = resultModel.getRtnInfo();
			if(!rtnInfo.getRetCode().equals("0000")){
				throw new Exception(rtnInfo.getErrMsg());
			}
			
			UnionpayTransactionResultQueryRetDetail retDetail = resultModel.getRtnBody().getRetDetails().getRetDetailList().get(0);
			detail.setAccountNo(retDetail.getAccount());
			detail.setAmount(FinanceUtils.convert_cent_to_yuan(new BigDecimal(retDetail.getAmount())).toPlainString());
			detail.setBankNo(null);
			detail.setCurrencyNo("10");
			detail.setOperateDate(retDetail.getSettDate());
			detail.setReqNo(record.getReqNo());
			detail.setRsvMsg(null);
			detail.setRtnFlag(retDetail.getRetCode().equals("0000")?"S":"F");
		}
		return detail;
	}
	

	private RealTimePaymentInfoModel genPaymentInfoModel(PaymentDetail paymentDetail, String reqXmlPacket){
		if(paymentDetail == null){
			return new RealTimePaymentInfoModel();
		}
		String merchantId = paymentDetail.getAccountNo();
		PaymentChannel paymentChannel = paymentChannelService.getPaymentChannel(merchantId, PaymentChannelType.GuangdongUnionPay);
		if(paymentChannel == null){
			return new RealTimePaymentInfoModel();
		}
		String transactionUuid = UUID.randomUUID().toString().replace("-", "");
		requestRecordService.save(new RequestRecord(GateWay.UnionPay, FunctionName.FUNC_BATCH_PAYMENT,
				paymentDetail.getReqNo(),transactionUuid, reqXmlPacket, paymentDetail.getAccountNo()));
		String amount = FinanceUtils.convert_yuan_to_cent(new BigDecimal(paymentDetail.getAmount()));
		PaymentDetailInfoModel detailInfomodel = new PaymentDetailInfoModel(
				"001",  BankCode.BANK_CODE_MAP.getOrDefault(paymentDetail.getBeneficiaryBankNo(), ""), paymentDetail.getPayeeNo(),
				paymentDetail.getPayeeName(), paymentDetail.getProvince(),
				paymentDetail.getCity(), paymentDetail.getBankName(), amount,
				paymentDetail.getRemark(), null);
		RealTimePaymentInfoModel model = new RealTimePaymentInfoModel();
		model.setApiUrl(paymentChannel.getApiUrl());
		model.setBusinessCode(GZUnionPayBusinessCode.OTHER_PAYMENT);
		model.setCerFilePath(paymentChannel.getCerFilePath());
		model.setMerchantId(merchantId);
		model.setDetailInfo(detailInfomodel);
		model.setPfxFileKey(paymentChannel.getPfxFileKey());
		model.setPfxFilePath(paymentChannel.getPfxFilePath());
		model.setReqNo(transactionUuid);
		model.setTotalItem(1);
		model.setTotalSum(amount);
		model.setUserName(paymentChannel.getUserName());
		model.setUserPwd(paymentChannel.getUserPassword());
		return model;
	}

}
