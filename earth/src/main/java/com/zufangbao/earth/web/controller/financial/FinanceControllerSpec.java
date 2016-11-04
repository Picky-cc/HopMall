package com.zufangbao.earth.web.controller.financial;

public class FinanceControllerSpec {

	public static class Url{
		public static final String Url_CREDIT_CASH_FLOW = "/credit-cash-flow";
		public static final String Url_CREDIT_CASH_FLOW_SEARCH = "/credit-cash-flow-search";
		public static final String CLOSE_DEBIT_AUDIT = "/close-debit-audit";
		public static final String Url_SHOW_CASHFLOW_DETAIL = "/debit-cash-detail";
		public static final String QUERY_BILLING_PLAN = "/query-billingPlan";
		public static final String VOUCHER_CHANGE = "/voucher-change";
		public static final String LIST_BILL_MATCH_RESULT_CASH_FLOW_UUID = "/list-billMatchResult/{cashFlowUuid}";
		public static final String APP_ID = "=appId";
		public static final String TYPE = "type";
		public static final String LIST_ACCOUNT_ACCOUNT_NAME = "/list-account-accountName";
		public static final String FILL_CASH_FLOW_UUID = "/fill-cash-flow-uuid";
		public static final String Url_FLOW_MONITOR = "/flow/monitor";
	}
	public static class I_Export_KingDee_Voucher{
		
		public static final String NAME = "/export-kingdee-voucher";
		
		public static final String PARAM_F_YEAR = "fYear";
		
		public static final String PARAM_F_NUMER = "fNumber";
		
		public static final String PARAM_F_SERIAL_NUM = "fSerialNum";
		
		public static final String PARAM_F_PREPARER_ID = "fPreparerId";
		
	}
	public static class I_Export_AppArriveRecord{
		
		public static final String NAME = "/export-apparrive-record";
	}
	
	public static class Submenu{
		public static final String SUBMENU_CREDIT_CASH_FLOW = "submenu-credit-cash-flow";
	}
	
	public static class Params{

		public static final String CASH_FLOW_UUID = "cashFlowUuid";
		public static final String PARAMS_APP_ID = "appId";
	}
	
	public static class ViewSpec{
		public static class ListAppArriveRecord{

			public static final String ACCOUNT_LIST = "accountList";
			public static final String APP_LIST = "appList";
			public static final String AUDIT_STATUS_LIST = "auditStatusList";
			public static final String CASH_FLOW_CONDITION_MODEL = "cashFlowConditionModel";
			public static final String APP_ARRIVE_RECORD_LIST = "appArriveRecordList";
			public static final String LIST = "list";
			public static final String SIZE = "size";
			public static final String EVERY_PAGE_SIZE = "everyPageSize";
			public static final String CHART_OF_ACCOUNT="chartOfAccount";
			
		}

		public static final String AUDIT_STATUS = "auditStatus";
		public static final String BILL_MATCH_RESULT_LIST = "billMatchResultList";
		public static final String ACCOUNT_LIST = "accountList";
		public static final String DUPLICATED_BUSINESS_NO = "duplicatedBusinessNo";
		
		public static final String CASH_FLOW_DETAIL = "cashFlowDetail";
		public static final String AUDIT_LOG = "auditLog";
	}
	
	public static class InfoMessage{
	}
}
