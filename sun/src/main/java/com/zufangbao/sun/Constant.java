/**
 * 
 */
package com.zufangbao.sun;


/**
 * @author lute
 *
 */

public class Constant {

	public static final String MD5_SALT = "galaxy";

	public static final String ORDERNO_PAYNO_SPLIT_CHAR = "@";
	
	public static final String ORDERNO_SPLIT_CHAR = "-";

	public static final String DATE_FORMAT_YMDHMSS = "yyyyMMddHHmmssSSS";

	public class CompareResult {
		public static final int LESS_THAN = -1;
		public static final int EQUAL = 0;
		public static final int GREATER_THAN = 1;
	}

	public static final class BankCorpEps {

		public static final String CMB_CODE = "CMB";
		public static final String ICBC_CODE = "ICBC";
		
		public static final String PAB_CODE = "PAB";
		public static final String PAB_BODY_DEFAULT_LENGTH = "0000000000";
		public static final String PAB_SUCCESS_CODE = "000000";
		public static final String PAB_DEBIT_CODE = "D";
		public static final String PAB_INTERBANK_CODE = "0";
		public static final String PAB_WITHINBANK_CODE = "1";
		public static final String PAB_SAMECITY_CODE = "1";
		public static final String PAB_REMOTECITY_CODE = "2";

	}

	public static final int YOPARK_OVER_DUE_DAYS = 11;
	
	public static final String YOPARK_APP_ID = "youpark";

	public static final int ZERO = 0;

	public static final String BUFFER_CASH_FLOW_BOOKING_DATE = "2015-01-01";
	
	public static final String DEBIT = "1";

	public static final String CREDIT = "2";

	public static final String CMB_SUCCESS_CODE = "0";

	public static final String PRE_PAYMENT_CONTRACT_NO = "pre_payment_contract_no";

	public static final String CMB_DEBIT_CODE = "D";

	public static final String ALIPAY_CODE = "alipay";

	public static final String UNION_CODE = "unionpay";

	public static final class FinanceDocumentSpec {
		public static final double TEST_MONEY_LIMIT = 5.0D;
	}

	public static final String RECORD = "record";

	public static final String COLUMN_COLOR_RED = "red";

	public static final String COlUMN_COLOR_GREEN = "green";

	public static final String DIRECT_BANK_ALIAS = "directbank";

	public static final int CMB_DATE_RANGE = 99;

	public static final String REPO_ORDER_SUFFIX = "-repo";
	
	public static final long ONE_DAY_TIME = 1000 * 60 * 60 * 24;
	
	public static class ErrorCode{
		public static final int ERROR_ILLEGAL_PARAM=-4;
	}
	public static class ErrorData{
		public static final int ERROR_NO_DATA = -1;
		public static final int ERROR_NO_ORDER = ERROR_NO_DATA-1;
		public static final int ERROR_NO_CONTRACT = ERROR_NO_ORDER-1;
		public static final int ERROR_NO_APP = ERROR_NO_CONTRACT-1;
		public static final int ERROR_NO_COMPANY = ERROR_NO_APP-1;
	}

	
	public static class MatchScore{
		public static final int NO_MATCH = 0;
		public static final int MATCH_ACCOUNT_NAME = 1;
		public static final int MATCH_ACCOUNT = 2;
		public static final int MATCH_ACCOUNT_AND_NAME = 3;
		public static final int MATCH_BY_JOURNAL_VOUCHER = 4;
	}

	public static final int CASH_FLOW_BILL_MATCH_DAYS = 180;
	
	public static final String MATCH_SMS_TEMPLATE = "回款提醒：%s账户，在 %s收到匹配回款%s元，付款人（%s），回款备注（%s）。对应还款编号%s，应付款日%s，应付金额%s元。请及时确认。";
	
}
