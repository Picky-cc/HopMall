package com.zufangbao.earth.yunxin.unionpay.constant;

/**
 * 广州银联常量表
 * @author zhanghongbing
 *
 */
public class GZUnionPayConstants {
	
	public static final String DEFAULT_TRANSACTION_DETAIL_QUERY_PAGE_SIZE = "5000";
	
	public class GZUnionPayBankCode {
		/**
		 * 中国邮政储蓄银行
		 */
		public static final String BANK_CODE_PSBC = "403";
	}

	/**
	 * 业务代码常量
	 */
	public class GZUnionPayBusinessCode {
		/**
		 * 还贷
		 */
		public static final String REPAY_A_LOAN = "14901";
		
		/**
		 * 其他代付
		 */
		public static final String OTHER_PAYMENT = "04900";
		/**
		 * 其他费用
		 */
		public static final String OTHER = "14900";
	}
	
	/**
	 * 响应码常量
	 *
	 */
	public static class GZUnionPayResponseCode {
		/**
		 * 处理完成
		 */
		public static final String PROCESSED = "0000";
		
		/**
		 * 无法查询到该交易
		 */
		public static final String QUERY_REQ_NO_NOT_EXIST = "1002";
		
		/**
		 * 处理中
		 */
		public static final String PROCESSING = "2000";
		
		/**
		 * 跨行处理中
		 */
		public static final String DIFFERENT_BANK_PROCESSING = "2008";
		
		/**
		 * 系统繁忙
		 */
		public static final String SYSTEM_BUSY = "3028";
		
		/**
		 * 余额不足
		 */
		public static final String BALANCE_IS_NOT_ENOUGH = "3008";

		public static boolean isAccepted(String rtnCode) {
			return PROCESSED.equals(rtnCode) || PROCESSING.equals(rtnCode) || SYSTEM_BUSY.equals(rtnCode);
		}
	}
	
}
