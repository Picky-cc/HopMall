package com.zufangbao.earth.yunxin.unionpay.template.gz;

import static com.zufangbao.earth.yunxin.unionpay.constant.GZUnionPayConstants.DEFAULT_TRANSACTION_DETAIL_QUERY_PAGE_SIZE;

import java.math.BigDecimal;

import com.demo2do.core.utils.StringUtils;
import com.zufangbao.earth.yunxin.unionpay.model.AccountDetailQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.BatchDeductInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.BatchQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.DeductDetailInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.PaymentDetailInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.RealTimeDeductInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.RealTimePaymentInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailQueryInfoModel;
import com.zufangbao.sun.utils.FinanceUtils;

/**
 * 广州银联请求数据包工厂
 * @author zhanghongbing
 *
 */
public class GZUnionPayPacketFactory {
	
	/**
	 * 批量代收报文模版
	 * 依次入参 ：用户名，用户密码，交易流水号，业务代码，商户号，总记录数，总金额，批扣明细列表
	 */
	public static final String BATCH_DEDUCT_PACKET = "<?xml version=\"1.0\" encoding=\"gbk\"?><GZELINK><INFO><TRX_CODE>100001</TRX_CODE><VERSION>05</VERSION><DATA_TYPE>2</DATA_TYPE><LEVEL>5</LEVEL><USER_NAME>%s</USER_NAME><USER_PASS>%s</USER_PASS><REQ_SN>%s</REQ_SN><SIGNED_MSG></SIGNED_MSG></INFO><BODY><TRANS_SUM><BUSINESS_CODE>%s</BUSINESS_CODE><MERCHANT_ID>%s</MERCHANT_ID><TOTAL_ITEM>%s</TOTAL_ITEM><TOTAL_SUM>%s</TOTAL_SUM></TRANS_SUM><TRANS_DETAILS>%s</TRANS_DETAILS></BODY></GZELINK>";
	
	/**
	 * 批量代付报文模板
	 * 依次入参 ：用户名，用户密码，交易流水号，业务代码，商户号，总记录数，总金额，批扣明细列表
	 */
	public static final String BATCH_PAYMENT_PACKET= "<?xml version=\"1.0\" encoding=\"gbk\"?><GZELINK><INFO><TRX_CODE>100002</TRX_CODE><VERSION>05</VERSION><DATA_TYPE>2</DATA_TYPE><LEVEL>5</LEVEL><USER_NAME>%s</USER_NAME><USER_PASS>%s</USER_PASS><REQ_SN>%s</REQ_SN><SIGNED_MSG></SIGNED_MSG></INFO><BODY><TRANS_SUM><BUSINESS_CODE>%s</BUSINESS_CODE><MERCHANT_ID>%s</MERCHANT_ID><TOTAL_ITEM>%s</TOTAL_ITEM><TOTAL_SUM>%s</TOTAL_SUM></TRANS_SUM><TRANS_DETAILS>%s</TRANS_DETAILS></BODY></GZELINK>";
	
	/**
	 * 实时代收报文模版
	 * 依次入参 ：用户名，用户密码，交易流水号，业务代码，商户号，总金额，实时代收明细
	 */
	public static final String REAL_TIME_DEDUCT_PACKET = "<?xml version=\"1.0\" encoding=\"gbk\"?><GZELINK><INFO><TRX_CODE>100004</TRX_CODE><VERSION>05</VERSION><DATA_TYPE>2</DATA_TYPE><LEVEL>0</LEVEL><USER_NAME>%s</USER_NAME><USER_PASS>%s</USER_PASS><REQ_SN>%s</REQ_SN><SIGNED_MSG></SIGNED_MSG></INFO><BODY><TRANS_SUM><BUSINESS_CODE>%s</BUSINESS_CODE><MERCHANT_ID>%s</MERCHANT_ID><TOTAL_ITEM>1</TOTAL_ITEM><TOTAL_SUM>%s</TOTAL_SUM></TRANS_SUM><TRANS_DETAILS>%s</TRANS_DETAILS></BODY></GZELINK>";
	
	
	/**
	 * 实时代付模版
	 * 依次入参 ：用户名，用户密码，交易流水号，业务代码，商户号，总记录数，总金额，实时代收明细
	 */
	public static final String REAL_TIME_PAYMENT_PACKET = "<?xml version=\"1.0\" encoding=\"gbk\"?><GZELINK><INFO><TRX_CODE>100005</TRX_CODE><VERSION>05</VERSION><DATA_TYPE>2</DATA_TYPE><LEVEL>5</LEVEL><USER_NAME>%s</USER_NAME><USER_PASS>%s</USER_PASS><REQ_SN>%s</REQ_SN><SIGNED_MSG></SIGNED_MSG></INFO><BODY><TRANS_SUM><BUSINESS_CODE>%s</BUSINESS_CODE><MERCHANT_ID>%s</MERCHANT_ID><TOTAL_ITEM>%s</TOTAL_ITEM><TOTAL_SUM>%s</TOTAL_SUM></TRANS_SUM><TRANS_DETAILS>%s</TRANS_DETAILS></BODY></GZELINK>";
	
	
	/**
	 * 实时代付数据包传递明细模版
	 * 依次入参数 ：序列号，银行代码，银行卡号，持卡人姓名，开户证件类型，证件号，开户行所在省，开户行所在市，金额，备注
	 */
	public static final String PAYMENT_PACKET_TRANS_DETAIL = "<TRANS_DETAIL><SN>%s</SN><BANK_CODE>%s</BANK_CODE><ACCOUNT_TYPE>00</ACCOUNT_TYPE><ACCOUNT_NO>%s</ACCOUNT_NO><ACCOUNT_NAME>%s</ACCOUNT_NAME><ID_TYPE>0</ID_TYPE><ID>%s</ID><PROVINCE>%s</PROVINCE><CITY>%s</CITY><AMOUNT>%s</AMOUNT><REMARK>%s</REMARK></TRANS_DETAIL>";
	
	
	
	/**
	 * 代收数据包传递明细模版
	 * 依次入参数 ：序列号，银行代码，银行卡号，持卡人姓名，身份证号，开户行所在省，开户行所在市，金额，备注
	 */
	public static final String DEDUCT_PACKET_TRANS_DETAIL = "<TRANS_DETAIL><SN>%s</SN><BANK_CODE>%s</BANK_CODE><ACCOUNT_TYPE>00</ACCOUNT_TYPE><ACCOUNT_NO>%s</ACCOUNT_NO><ACCOUNT_NAME>%s</ACCOUNT_NAME><ID>%s</ID><PROVINCE>%s</PROVINCE><CITY>%s</CITY><AMOUNT>%s</AMOUNT><REMARK>%s</REMARK></TRANS_DETAIL>";
	
	/**
	 * 批量代付数据包传递明细模板
	 * 依次传入参数：序列号，银行代码，银行卡号，持卡人姓名，身份证号，开户行所在省，开户行所在市，开户行名称，金额，备注
	 */
	public static final String BATCH_PAYMENT_PACKET_TRANS_DETAIL = "<TRANS_DETAIL><SN>%s</SN><BANK_CODE>%s</BANK_CODE><ACCOUNT_TYPE>00</ACCOUNT_TYPE><ACCOUNT_NO>%s</ACCOUNT_NO><ACCOUNT_NAME>%s</ACCOUNT_NAME><ID>%s</ID><PROVINCE>%s</PROVINCE><CITY>%s</CITY><BANK_NAME>%s</BANK_NAME><AMOUNT>%s</AMOUNT><REMARK>%s</REMARK></TRANS_DETAIL>";
	
	/**
	 * 交易结果查询报文模版
	 * 依次入参 ：用户名，用户密码，交易流水号，要查询对交易流水号
	 */
	public static final String BATCH_QUERY_PACKET = "<?xml version=\"1.0\" encoding=\"gbk\"?><GZELINK><INFO><TRX_CODE>200001</TRX_CODE><VERSION>05</VERSION><DATA_TYPE>2</DATA_TYPE><USER_NAME>%s</USER_NAME><USER_PASS>%s</USER_PASS><REQ_SN>%s</REQ_SN><SIGNED_MSG></SIGNED_MSG></INFO><BODY><QUERY_TRANS><QUERY_SN>%s</QUERY_SN></QUERY_TRANS></BODY></GZELINK>";
	
	
	/**
	 * 交易结果查询请求报文模版
	 * 依次入参 ：用户名，用户密码，交易流水号，要查询的交易流水号
	 */
	public static final String TRANSACTION_RESULT_QUERY_PACKET = "<?xml version=\"1.0\" encoding=\"gbk\"?><GZELINK><INFO><TRX_CODE>200001</TRX_CODE><VERSION>05</VERSION><DATA_TYPE>2</DATA_TYPE><USER_NAME>%s</USER_NAME><USER_PASS>%s</USER_PASS><REQ_SN>%s</REQ_SN><SIGNED_MSG></SIGNED_MSG></INFO><BODY><QUERY_TRANS><QUERY_SN>%s</QUERY_SN></QUERY_TRANS></BODY></GZELINK>";
	
	
	/**
	 * 当日或历史查询报文模版
	 * 依次入参 ：用户名，用户密码，交易流水号，商户号，开始时间，结束时间，页码，每页记录数
	 */
	public static final String TRANSACTION_DETAIL_QUERY_PACKET = "<?xml version=\"1.0\" encoding=\"gbk\"?><GZELINK><INFO><TRX_CODE>200002</TRX_CODE><VERSION>05</VERSION><DATA_TYPE>2</DATA_TYPE><USER_NAME>%s</USER_NAME><USER_PASS>%s</USER_PASS><REQ_SN>%s</REQ_SN><SIGNED_MSG></SIGNED_MSG></INFO><BODY><QUERY_TRANS><MERCHANT_ID>%s</MERCHANT_ID><BEGIN_DATE>%s</BEGIN_DATE><END_DATE>%s</END_DATE><PAGE_NUM>%s</PAGE_NUM><PAGE_SIZE>%s</PAGE_SIZE><SF_TYPE>S</SF_TYPE><REQ_TYPE>0</REQ_TYPE><RESULT_TYPE>2</RESULT_TYPE></QUERY_TRANS></BODY></GZELINK>";
	
	public static final String ACCOUNT_DETAIL_QUERY_PACKET= "<?xml version=\"1.0\" encoding=\"gbk\"?><GZELINK><INFO><TRX_CODE>500002</TRX_CODE><VERSION>05</VERSION><DATA_TYPE>2</DATA_TYPE><USER_NAME>%s</USER_NAME><USER_PASS>%s</USER_PASS><REQ_SN>%s</REQ_SN><SIGNED_MSG></SIGNED_MSG></INFO><BODY><QUERY_TRANS><ACCOUNT_TYPE>%s</ACCOUNT_TYPE><MERCHANT_ID>%s</MERCHANT_ID><BEGIN_DATE>%s</BEGIN_DATE><END_DATE>%s</END_DATE><PAGE_NUM>%s</PAGE_NUM><PAGE_SIZE>%s</PAGE_SIZE><SF_TYPE>S</SF_TYPE><REQ_TYPE>0</REQ_TYPE><RESULT_TYPE>2</RESULT_TYPE></QUERY_TRANS></BODY></GZELINK>";
	/**
	 * 生成批扣数据报文
	 * @param batchDeductInfoModel
	 * @return
	 */
	public static String generateBatchDeductPacket(BatchDeductInfoModel batchDeductInfoModel) {
		if(batchDeductInfoModel == null) {
			batchDeductInfoModel = new BatchDeductInfoModel();
		}
		
		String userName = batchDeductInfoModel.getUserName();
		String userPwd = batchDeductInfoModel.getUserPwd();
		String merchantId = batchDeductInfoModel.getMerchantId();
		
		String reqNo = batchDeductInfoModel.getReqNo();
		String businessCode = batchDeductInfoModel.getBusinessCode();
		int totalItem = batchDeductInfoModel.getTotalItem();
		String totalSum = FinanceUtils.convert_yuan_to_cent(batchDeductInfoModel.getTotalSum());
		
		StringBuffer buffer = new StringBuffer();
		for (DeductDetailInfoModel detailInfo : batchDeductInfoModel.getDetailInfos()) {
			String sn = detailInfo.getSn();
			String bankCode = detailInfo.getBankCode();
			String accountNo = detailInfo.getAccountNo();
			String accountName = detailInfo.getAccountName();
			String idCardNum = detailInfo.getIdCardNum();
			String province = detailInfo.getProvince();
			String city = detailInfo.getCity();
			
			String amount = FinanceUtils.convert_yuan_to_cent(detailInfo.getAmount());
			String remark = detailInfo.getRemark();
			String detailXml = formatEscapeNull(DEDUCT_PACKET_TRANS_DETAIL, sn, bankCode, accountNo, accountName, idCardNum, province, city, amount, remark);
			buffer.append(detailXml);
		}
		
		return formatEscapeNull(BATCH_DEDUCT_PACKET, userName, userPwd, reqNo, businessCode, merchantId, totalItem, totalSum, buffer.toString());
	}
	
	/**
	 * 生成实时代收数据报文
	 * @param realTimeDeductInfoModel
	 * @return
	 */
	public static String generateRealTimeDeductPacket(RealTimeDeductInfoModel realTimeDeductInfoModel) {
		if(realTimeDeductInfoModel == null) {
			realTimeDeductInfoModel = new RealTimeDeductInfoModel();
		}
		
		String userName = realTimeDeductInfoModel.getUserName();
		String userPwd = realTimeDeductInfoModel.getUserPwd();
		String merchantId = realTimeDeductInfoModel.getMerchantId();
		
		String reqNo = realTimeDeductInfoModel.getReqNo();
		String businessCode = realTimeDeductInfoModel.getBusinessCode();
		
		DeductDetailInfoModel deductDetailInfoModel = realTimeDeductInfoModel.getDeductDetailInfoModel();
		
		String sn = deductDetailInfoModel.getSn();
		String bankCode = deductDetailInfoModel.getBankCode();
		String accountNo = deductDetailInfoModel.getAccountNo();
		String accountName = deductDetailInfoModel.getAccountName();
		
		String idCardNum = deductDetailInfoModel.getIdCardNum();
		String province = deductDetailInfoModel.getProvince();
		String city = deductDetailInfoModel.getCity();
		
		String amount = FinanceUtils.convert_yuan_to_cent(deductDetailInfoModel.getAmount());
		String remark = deductDetailInfoModel.getRemark();
		String detailXml = formatEscapeNull(DEDUCT_PACKET_TRANS_DETAIL, sn, bankCode, accountNo, accountName, idCardNum, province, city, amount, remark);
		
		return formatEscapeNull(REAL_TIME_DEDUCT_PACKET, userName, userPwd, reqNo, businessCode, merchantId, amount, detailXml);
	}
	
	
	/**
	 * 生成实时代付请求 
	 */
	public static String generateRealTimePaymentPacket(RealTimePaymentInfoModel realTimePaymentInfoModel ){
		if(realTimePaymentInfoModel == null) {
			realTimePaymentInfoModel = new RealTimePaymentInfoModel();
		}
		
		String userName = realTimePaymentInfoModel.getUserName();
		String userPwd = realTimePaymentInfoModel.getUserPwd();
		String merchantId = realTimePaymentInfoModel.getMerchantId();
		
		String reqNo = realTimePaymentInfoModel.getReqNo();
		String businessCode = realTimePaymentInfoModel.getBusinessCode();
		int totalItem = realTimePaymentInfoModel.getTotalItem();
		String totalSum = FinanceUtils.convert_yuan_to_cent(new BigDecimal( realTimePaymentInfoModel.getTotalSum()) );
		
		PaymentDetailInfoModel detailInfo = realTimePaymentInfoModel.getDetailInfo();
		String sn = detailInfo.getSn();
		String bankCode = detailInfo.getBankCode();
		String accountNo = detailInfo.getAccountNo();
		String accountName = detailInfo.getAccountName();
		String idNum = detailInfo.getIdNum();
		String province = detailInfo.getProvince();
		String city = detailInfo.getCity();
			
		String amount = FinanceUtils.convert_yuan_to_cent(new BigDecimal( detailInfo.getAmount()) );
		String remark = detailInfo.getRemark();
		String detailXml = formatEscapeNull(PAYMENT_PACKET_TRANS_DETAIL, sn, bankCode, accountNo, accountName, idNum, province, city, amount, remark);
	
		return formatEscapeNull(REAL_TIME_PAYMENT_PACKET, userName, userPwd, reqNo, businessCode, merchantId, totalItem, totalSum, detailXml);
		
	}
	

	/**
	 * 生成批查询数据报文
	 * @param batchQueryInfoModel
	 * @return
	 */
	public static String generateBatchQueryPacket(BatchQueryInfoModel batchQueryInfoModel) {
		if(batchQueryInfoModel == null) {
			batchQueryInfoModel = new BatchQueryInfoModel();
		}
		
		String userName = batchQueryInfoModel.getUserName();
		String userPwd = batchQueryInfoModel.getUserPwd();
		String reqNo = batchQueryInfoModel.getReqNo();
		String queryReqNo = batchQueryInfoModel.getQueryReqNo();
		return formatEscapeNull(BATCH_QUERY_PACKET, userName, userPwd, reqNo, queryReqNo);
	}
	
	public static String generateTransactionDetailQueryPacket(
			TransactionDetailQueryInfoModel model) {
		if(model == null) {
			model = new TransactionDetailQueryInfoModel();
		}
		
		String userName = model.getUserName();
		String userPwd = model.getUserPwd();
		String merchantId = model.getMerchantId();
		
		String reqNo = model.getReqNo();
		String beginDate = model.getBeginDate();
		String endDate = model.getEndDate();
		String pageNum = model.getPageNum();
		String pageSize = StringUtils.isEmpty(model.getPageSize()) ? DEFAULT_TRANSACTION_DETAIL_QUERY_PAGE_SIZE : model.getPageSize();
		return formatEscapeNull(TRANSACTION_DETAIL_QUERY_PACKET, userName, userPwd, reqNo, merchantId, beginDate, endDate, pageNum, pageSize);
	}
	
	public static String generateAccountDetailQueryPacket(
			AccountDetailQueryInfoModel model) {
		if(model == null) {
			model = new AccountDetailQueryInfoModel();
		}
		
		String userName = model.getUserName();
		String userPwd = model.getUserPwd();
		String merchantId = model.getMerchantId();
		
		String reqNo = model.getReqNo();
		String beginDate = model.getBeginDate();
		String endDate = model.getEndDate();
		String pageNum = model.getPageNum();
		String pageSize = model.getPageSize();
		String accountType = model.getAccountType();
		return formatEscapeNull(ACCOUNT_DETAIL_QUERY_PACKET, userName, userPwd, reqNo, accountType,merchantId, beginDate, endDate, pageNum, pageSize);
	}
	
	private static String formatEscapeNull(String format, Object... args) {
		for (int i = 0; i < args.length; i++) {
			if(args[i] == null) {
				args[i] = "";
			}
		}
		return String.format(format, args);
	}
	
}
