/**
 * 
 */
package com.suidifu.coffer;

public class GlobalSpec {
	
	public static final String DEFAULT_SUCCESS_CODE = "0";
	public static final String DEFAULT_FAIL_CODE = "1";
	public static final int DEFAULT_TIME_OUT = 60000;
	public static final Integer DEFAULT_SOCKET_PORT = 9999;
	public static final int DEFAULT_SOCKET_BUFFER_SIZE = 4096;

	public static final class BankCorpEps {
		
		public static final String DEFAULT_ENCODING = "UTF-8";
		
		public static final String CMB_CREDIT_CODE = "DCPAYMNT";
		public static final String CMB_DEFAULT_BUSCOD = "N02031";
		public static final String CMB_DEFAULT_BUSMOD = "00001";
		public static final String CMB_DEFAULT_CURRENCY = "10";
		public static final String CMB_DEFAULT_SETTLE = "F";
		public static final String CMB_SAMEBANK_CODE = "Y";
		public static final String CMB_INTERBANK_CODE = "N";
		public static final String CMB_DEFAULT_ENCODING = "UTF-8";
		public static final String CMB_SUCCESS_CODE = "0";
		
		public static final String CMB_QUERYCREDIT_CODE = "GetPaymentInfo";
		public static final String CMB_REQUEST_STATUS_FIN = "FIN";
		public static final String CMB_PROCESS_STATUS_SUC = "S";
		
		
		public static final String PAB_SUCCESS_CODE = "000000";
		public static final String PAB_DEFAULT_CURRENCY = "RMB";
		public static final String PAB_SAMEBANK_CODE = "1";
		public static final String PAB_INTERBANK_CODE = "0";
		public static final String PAB_DEFAULT_MODE = "S";
		public static final String PAB_DEFAULT_ADDRFLAG = "2";
		public static final String PAB_DEFAULT_ENCODING = "UTF-8";
		public static final String PAB_DEFAULT_BODY_LENGTH = "0000000000";
		public static final String PAB_CREDIT_CODE = "4004";
		public static final String PAB_QUERYCREDIT_CODE = "4005";
		public static final String PAB_QUERYCASHFLOW_CODE = "4013";
		public static final String PAB_DEFAULT_PAGESIZE = "1000";
		
		public static final String PAB_NOTRECEIVE_RETCODE = "MA0103";
		public static final String PAB_EMPTYDETAIL_RETCODE = "ES0200";
		public static final String PAB_PROCESS_STATUS_SUC = "20";
		public static final String PAB_PROCESS_STATUS_FAI = "30";
		
		public static final String PAB_CASHFLOW_CREDIT_CODE = "C";
		public static final String PAB_CASHFLOW_DEBIT_CODE = "D";
	}
	
	public class ErrorMsg {
		public static final String ERR_SYSTEM_EXCEPTION = "system exception,please check!";
		
		public static final String ERR_NULL_CREDITMODEL = "creditModel is null!";
		public static final String ERR_NULL_WORKPARMS = "workParms is null!";
		public static final String ERR_PARSE_PACKET = "response packet parsing failed!";
		public static final String ERR_TRANSACTION_ANOMALY = "transaction anomaly,please check the transaction result to confirm the transaction status!error message:";
		
		public static final String ERR_NULL_QUERYCASHFLOWMODEL = "queryCashFlowModel is null!";
		public static final String ERR_QUERY_ANOMALY = "query anomaly,please try again!error message:";
		
		public static final String ERR_NULL_QUERYCREDITMODEL = "queryCreditModel is null!";
	
		public static final String ERR_NOT_SUPPORT_CREDIT = "not support credit!";
		public static final String ERR_NOT_SUPPORT_DEBIT = "not support debit!";
		public static final String ERR_NOT_SUPPORT_MODE = "not support mode!";
	}
	
	public static final class ElecPayment {
		public static final String GZUNION_DEBITMODE_BATCH = "batchMode";
		public static final String GZUNION_DEBITMODE_REALTIME = "realTimeMode";
		public static final String GZUNION_DEFAULT_BUSINESSCODE = "14901";
	}
}