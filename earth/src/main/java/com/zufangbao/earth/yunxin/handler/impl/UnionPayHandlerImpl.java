package com.zufangbao.earth.yunxin.handler.impl;

import static com.zufangbao.earth.yunxin.unionpay.constant.GZUnionPayConstants.GZUnionPayResponseCode.BALANCE_IS_NOT_ENOUGH;
import static com.zufangbao.earth.yunxin.unionpay.constant.GZUnionPayConstants.GZUnionPayResponseCode.DIFFERENT_BANK_PROCESSING;
import static com.zufangbao.earth.yunxin.unionpay.constant.GZUnionPayConstants.GZUnionPayResponseCode.PROCESSED;
import static com.zufangbao.earth.yunxin.unionpay.constant.GZUnionPayConstants.GZUnionPayResponseCode.PROCESSING;
import static com.zufangbao.earth.yunxin.unionpay.constant.GZUnionPayConstants.GZUnionPayResponseCode.QUERY_REQ_NO_NOT_EXIST;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.yunxin.handler.UnionPayHandler;
import com.zufangbao.earth.yunxin.unionpay.component.IGZUnionPayApiComponent;
import com.zufangbao.earth.yunxin.unionpay.constant.GZUnionPayConstants.GZUnionPayBusinessCode;
import com.zufangbao.earth.yunxin.unionpay.constant.GZUnionPayConstants.GZUnionPayResponseCode;
import com.zufangbao.earth.yunxin.unionpay.model.BatchDeductInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.BatchQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.RealTimeDeductInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailNode;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailQueryResult;
import com.zufangbao.earth.yunxin.unionpay.model.basic.GZUnionPayResult;
import com.zufangbao.gluon.exception.CommonException;
import com.zufangbao.gluon.spec.global.GlobalCodeSpec;
import com.zufangbao.sun.entity.finance.BatchPayRecord;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.PaymentChannel;
import com.zufangbao.sun.entity.financial.PaymentChannelType;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.entity.unionpay.UnionpayBankConfig;
import com.zufangbao.sun.service.BatchPayRecordService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.service.TransferApplicationService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.ExecutingDeductStatus;
import com.zufangbao.sun.yunxin.entity.ExecutingSettlingStatus;
import com.zufangbao.sun.yunxin.exception.SmsTemplateNotExsitException;
import com.zufangbao.sun.yunxin.handler.SmsQueneHandler;
import com.zufangbao.sun.yunxin.service.DictionaryService;
import com.zufangbao.sun.yunxin.service.UnionpayBankConfigService;
import com.zufangbao.wellsfargo.yunxin.handler.TransferApplicationHandler;

/**
 * 银联相关
 */
@Component("unionPayHandler")
public class UnionPayHandlerImpl implements UnionPayHandler {

	@Autowired
	private TransferApplicationService transferApplicationService;
	
	@Autowired
	private BatchPayRecordService batchPayRecordService;
	
	@Autowired
	private TransferApplicationHandler transferApplicationHandler;
	
	@Autowired
	private IGZUnionPayApiComponent igzUnionPayApiComponent;
	
	@Autowired
	private SmsQueneHandler smsQueneHandler;
	
	@Autowired
	private DictionaryService dictionaryService; 

	@Autowired
	private UnionpayBankConfigService unionpayBankConfigService;
	
	@Autowired
	private OrderService orderService;

	private static Log logger = LogFactory.getLog(UnionPayHandlerImpl.class);

	@Override
	public void startSingleDeduct(Serializable id) {
		try {
			TransferApplication transferApplication = transferApplicationService.load(TransferApplication.class, id);
			BatchPayRecord batchPayRecord = transferApplicationHandler.createBatchPayRecordBy(transferApplication);

			GZUnionPayResult gzUnionPayResult;
			String bankCode = transferApplication.getContractAccount().getBankCode();
			
			//实时，批量接口选择
			if(isUseBatchDeduct(bankCode)) {
				BatchDeductInfoModel batchDeductInfoModel = getBatchDeductInfoModelBy(transferApplication);
				if(batchDeductInfoModel == null) {
					return;
				}
				gzUnionPayResult = igzUnionPayApiComponent.execBatchDeduct(batchDeductInfoModel);
				logger.info(">>>>>>>>>>>>>>>>> 批量接口,支付单号：" + transferApplication.getId());
			}else {
				RealTimeDeductInfoModel realTimeDeductInfoModel = getRealTimeDeductInfoModelBy(transferApplication);
				if(realTimeDeductInfoModel == null) {
					return;
				}
				gzUnionPayResult = igzUnionPayApiComponent.execRealTimeDeductPacket(realTimeDeductInfoModel);
				logger.info(">>>>>>>>>>>>>>>>> 实时接口,支付单号：" + transferApplication.getId());
			}
			
			analysis_and_record_realtime_deduct_result(gzUnionPayResult, transferApplication, batchPayRecord);
		} catch (Exception e) {
			logger.error("startSingleRealTimeDeduct has error!");
			e.printStackTrace();
		}
	}
	
	private boolean isUseBatchDeduct(String bankCode) {
		List<UnionpayBankConfig> unionpayBankConfigs = unionpayBankConfigService.getUnionpayBankConfigs();
		boolean useBatchDeduct = unionpayBankConfigs.stream().filter(ubc -> ubc.isUseBatchDeduct() && StringUtils.equals(bankCode, ubc.getBankCode())).count() > 0;
		return useBatchDeduct;
	}
	
	private RealTimeDeductInfoModel getRealTimeDeductInfoModelBy(
			TransferApplication transferApplication) {
		RealTimeDeductInfoModel realTimeDeductInfoModel;
		try {
			PaymentChannel channel = getPaymentChannelBy(transferApplication);
			
			realTimeDeductInfoModel = new RealTimeDeductInfoModel(transferApplication, channel, GZUnionPayBusinessCode.REPAY_A_LOAN);
		}catch (CommonException ce) {
			recordErrorMsg(ce.getErrorMsg(), transferApplication, true, ce);
			return null;
		}catch (Exception e1) {
			recordErrorMsg("扣款执行前，数据采集出错！(" + transferApplication.getTransferApplicationNo() +")", transferApplication, true, e1);
			return null;
		}
		return realTimeDeductInfoModel;
	}
	
	private BatchDeductInfoModel getBatchDeductInfoModelBy(
			TransferApplication transferApplication) {
		BatchDeductInfoModel batchDeductInfoModel;
		try {
			PaymentChannel channel = getPaymentChannelBy(transferApplication);
			
			batchDeductInfoModel = new BatchDeductInfoModel(transferApplication, channel, GZUnionPayBusinessCode.REPAY_A_LOAN);
		}catch (CommonException ce) {
			recordErrorMsg(ce.getErrorMsg(), transferApplication, true, ce);
			return null;
		}catch (Exception e1) {
			recordErrorMsg("扣款执行前，数据采集出错！(" + transferApplication.getTransferApplicationNo() +")", transferApplication, true, e1);
			return null;
		}
		return batchDeductInfoModel;
	}

	private void analysis_and_record_realtime_deduct_result(GZUnionPayResult result, TransferApplication transferApplication, BatchPayRecord batchPayRecord) {
		String sendTime = result.getSendTime();
		String rtnReqNo = result.getReqNo();
		String requestData = result.getRequestPacket();
		String responseData = result.getResponsePacket();
		batchPayRecordService.updateTranDateTimeAndResponseData(batchPayRecord, sendTime, rtnReqNo, requestData, responseData);
		
		transferApplication.setDeductSendTime(result.getSendDate());
		transferApplicationService.update(transferApplication);
		
		if (!result.isValid()) {
			recordErrorMsg(result.getMessage(), transferApplication, false, null);
		}
		
		String rtnCode = result.getRetCode();
		String rtnMsg = result.getRetMsg();

		if (result.isValid() && !GZUnionPayResponseCode.isAccepted(rtnCode)) {
			transferApplication.setComment(rtnMsg);
			transferApplication.setExecutingDeductStatus(ExecutingDeductStatus.FAIL);
			transferApplicationService.update(transferApplication);
		}
	}
	
	@Override
	public void startSingleQueryUnionpayDeductResult() {
		List<TransferApplication> transferApplicationList = transferApplicationService.getInProcessTransferApplicationList();
		for (TransferApplication transferApplication : transferApplicationList) {
			logger.info("startSingleQueryUnionpayDeductResult, [transferApplicationId:" + transferApplication.getId() + ", deductStatus: " + transferApplication.getDeductStatus() + ", executingDeductStatus: " + transferApplication.getExecutingDeductStatus() + " ]");
			try {
				BatchPayRecord batchPayRecord = batchPayRecordService.load(BatchPayRecord.class, transferApplication.getBatchPayRecordId());
				
				BatchQueryInfoModel batchQueryInfoModel = getBatchQueryInfoModelBy(transferApplication);
				
				GZUnionPayResult gzUnionPayResult = igzUnionPayApiComponent.execBatchQuery(batchQueryInfoModel);
				
				analysis_and_record_batch_query_result(gzUnionPayResult, transferApplication, batchPayRecord);
				
			} catch (Exception e) {
				logger.error("startSingleQueryUnionpayDeductResult has error!");
				e.printStackTrace();
			}
		}
	}

	private BatchQueryInfoModel getBatchQueryInfoModelBy(
			TransferApplication transferApplication) {
		BatchQueryInfoModel batchQueryInfoModel;
		try {
			PaymentChannel channel = getPaymentChannelBy(transferApplication);
			
			String queryReqNo = transferApplication.getTransferApplicationNo();
			batchQueryInfoModel = new BatchQueryInfoModel(channel, queryReqNo);
		}catch (CommonException ce) {
			recordErrorMsg(ce.getErrorMsg(), transferApplication,  true, ce);
			return null;
		} catch (Exception e) {
			recordErrorMsg("交易结果查询执行前，数据采集出错！(" + transferApplication.getTransferApplicationNo() +")", transferApplication, true, e);
			return null;
		}
		return batchQueryInfoModel;
	}
	
	private void analysis_and_record_batch_query_result(GZUnionPayResult gzUnionPayResult, TransferApplication transferApplication, BatchPayRecord batchPayRecord) {
		record_query_result_packet(gzUnionPayResult, batchPayRecord);
		if(!gzUnionPayResult.isValid()) {
			return ;
		}
			
		String retCode = gzUnionPayResult.getRetCode();
		if(PROCESSED.equals(retCode)){
			deeply_analysis_query_result(gzUnionPayResult, transferApplication);
		}else{		
			summary_analysis_query_result(gzUnionPayResult, transferApplication, retCode);
		}
	}

	private void summary_analysis_query_result(GZUnionPayResult gzUnionPayResult,
			TransferApplication transferApplication, String retCode) {
		//交易流水不存在，线上支付单状态置失败
		if(QUERY_REQ_NO_NOT_EXIST.equals(retCode)) {
			transferApplication.setExecutingDeductStatus(ExecutingDeductStatus.FAIL);
		}else {
			String comment = gzUnionPayResult.concactRetCodeAndRetMsg();
			transferApplication.setComment(comment);
		}
		transferApplication.setLastModifiedTime(new Date());
		transferApplicationService.update(transferApplication);
	}

	private void deeply_analysis_query_result(GZUnionPayResult gzUnionPayResult,
			TransferApplication transferApplication) {
		TransactionDetailQueryResult queryResult = TransactionDetailQueryResult.initialization(gzUnionPayResult);
		TransactionDetailNode detailNode = queryResult.getFirstTransactionDetailNode();
		if(detailNode == null) {
			return ;
		}
		analysis_detail_node(transferApplication, detailNode);
	}

	private void record_query_result_packet(GZUnionPayResult gzUnionPayResult,
			BatchPayRecord batchPayRecord) {
		String queryResponseData = gzUnionPayResult.isValid() ? gzUnionPayResult.getResponsePacket() : gzUnionPayResult.getMessage();
		batchPayRecord.updateAfterQueryUnion(queryResponseData);
		batchPayRecordService.saveOrUpdate(batchPayRecord);
	}

	private void analysis_detail_node(TransferApplication transferApplication,
			TransactionDetailNode detailNode) {
		String detailRetCode = detailNode.getRetCode();
		String detailRetMsg = detailNode.getErrMsg();
		
		//交易明细显示为处理中
		if(is_transcation_detail_in_processing_status(detailRetCode)) {
			return;
		}
		
		boolean detailIsSuccess = PROCESSED.equals(detailRetCode);
		Date deductTime = detailIsSuccess ? DateUtils.parseFullDateTimeToDate(detailNode.getCompleteTime()) : null;
		
		transferApplicationHandler.updateTransferAppAndOrderAndAssetBy(transferApplication, detailIsSuccess, detailRetMsg, deductTime);
		
		Order order = transferApplication.getOrder();
		if(detailIsSuccess) {
			createSuccessDeductSms(order);
		}else {
			//跨日失败响应，结算单同步
			syncNotOnTheSameDayFailedTransferApplication(transferApplication, order);
			if(BALANCE_IS_NOT_ENOUGH.equals(detailRetCode)) {
				transferApplicationHandler.todayFirstTransferApplicationFailRemind(order, detailRetMsg);
			}
		}
	}

	private boolean is_transcation_detail_in_processing_status(String detailRetCode) {
		return PROCESSING.equals(detailRetCode) || DIFFERENT_BANK_PROCESSING.equals(detailRetCode);
	}

	private void createSuccessDeductSms(Order order) {
		try {
			boolean allowedSendStatus = dictionaryService.getSmsAllowSendFlag();
			smsQueneHandler.saveSuccessSmsQuene(order.getAssetSet(), allowedSendStatus);
		} catch (SmsTemplateNotExsitException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跨日失败响应，结算单同步
	 */
	private void syncNotOnTheSameDayFailedTransferApplication(TransferApplication transferApplication, Order order) {
		Date taCreateTime = transferApplication.getCreateTime();
		Date currentTime = new Date();
		if(DateUtils.isSameDay(taCreateTime, currentTime)) {
			return;
		}
		order.setExecutingSettlingStatus(ExecutingSettlingStatus.FAIL);
		order.setModifyTime(currentTime);
		orderService.update(order);
	}

	private PaymentChannel getPaymentChannelBy(TransferApplication transferApplication) throws CommonException {
		Order order = transferApplication.getOrder();
		FinancialContract financialContract = order.getFinancialContract();
		PaymentChannel channel = financialContract.getPaymentChannel();
		if(channel.getPaymentChannelType() != PaymentChannelType.GuangdongUnionPay) {
			String errorMsg = "支付接口暂不支持 ！(" + transferApplication.getTransferApplicationNo() +")";
			logger.error(errorMsg);
			throw new CommonException(GlobalCodeSpec.CODE_FAILURE, errorMsg);
		}
		return channel;
	}
	
	private void recordErrorMsg(String errorMsg, TransferApplication transferApplication, boolean isFail, Exception e) {
		transferApplication.setComment(errorMsg);
		transferApplication.setLastModifiedTime(new Date());
		if(isFail) {
			transferApplication.setExecutingDeductStatus(ExecutingDeductStatus.FAIL);
		}
		transferApplicationService.update(transferApplication);
		if(e != null) {
			logger.error(errorMsg, e);
			e.printStackTrace();
		}
	}

}
