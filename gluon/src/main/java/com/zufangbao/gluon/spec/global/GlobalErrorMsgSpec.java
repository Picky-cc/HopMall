package com.zufangbao.gluon.spec.global;

/**
 * GlobalRuntimeException Error Message
 * @author louguanyang
 *
 */
public class GlobalErrorMsgSpec {

	public class VoucherErrorMsgSpec{
		public static final String VOUCHER_ERROR_OF_CONTRACT = "contractUniqueId错误";

		public static final String VOUCHER_ERROR_OF_FINANCIAL_CONTRACT = "FinancialContract贷款合同不在信托计划内";

		public static final String VOUCHER_ERROR_OF_REPAYMENT_PLAN_NO = "repaymentPlanNo错误";

		public static final String VOUCHER_ERROR_OF_RECEIVABLE_AMOUNT = "金额大于未还金额";

		public static final String VOUCHER_ERROR_OF_GURANTEE_AMOUNT = "金额大于担保金额";

		public static final String VOUCHER_ERROR_OF_PAYER = "付款人类型错误，0:贷款人,1:商户垫付";

		public static final String VOUCHER_ERROR_OF_VOUCHER_TYPE = "不支持的凭证类型";
	}
	
}
