package com.zufangbao.earth.web.controller.financial;

public class CashFlowAuditControllerSpec {
	
	public static final String NAME = "finance";
	
	public static final String MENU = "menu-finance";
	
	public static final String CREDIT = "credit";
	
	public static final String DEBIT = "debit";
	
	public static final String TOTAL_PAGE_SIZE = "size";
	
	public static final String EVERY_PAGE_SIZE = "everyPageSize";

	public class I_Query_Trade_Parties{
		
		public static final String NAME = "/query-trade-parties";
		
		public static final String PARAM_APP_ID = "appId";
		
		public static final String RETURN_TRADE_PARTIES = "tradeParties";
	}
	public class I_Query_Simplified_Contract_Info{
		
		public static final String NAME = "/query-simplified-contract-info";
		
		public static final String PARAM_APP_ID = "appId";
		
		public static final String RETURN_SIMPLIFIED_CONTRACT_INFO_LIST = "list";
	}
	public class I_Query_Entry_Type_And_Bill_Type_Info{
		
		public static final String NAME = "/query-entry-type-bill-type-info";
		
		public static final String PARAM_APP_ID = "appId";
		
		public static final String RTN_ENTRY_TYPE = "entryType";
		
		public static final String RTN_BANK_INFO_LIST = "bankInfoList";
		
		public static final String RTN_FINANCIAL_ACCOUNT_INFO_LIST = "financialAccountList";

		public static final String RTN_ACCOUNT_INFO_MAP = "accountInfoMap";
		
		public static final String RTN_BILL_TYPE= "billType" ;
	}
	public class I_List_Cash_Flow{
		
		public static final String NAME = "/cash-flow-audit-list";
		
		public static final String CREDIT_VIEW_NAME = "finance/audit/credit-cash-flow";
		
		public static final String DEBIT_VIEW_NAME = "finance/audit/debit-cash-flow";
	}
	public class I_List_Debit_Cash_Flow{
		
		public static final String NAME = I_List_Cash_Flow.NAME;
		
		public static final String SUB_MENU_SETTING = "submenu-debit-cash-flow";
		
		public static final String PARAM_ACCOUNT_SIDE = "accountSide";
	}
	public class I_List_Credit_Cash_Flow{
		
		public static final String NAME = I_List_Cash_Flow.NAME;
		
		public static final String SUB_MENU_SETTING = "submenu-credit-cash-flow";
		
		public static final String PARAM_ACCOUNT_SIDE = "accountSide";
	}
	public class I_Query_Credit_Cash_Flow{
		
		public static final String NAME = "/cash-flow-audit/query";
	}
	public class I_Export_KingDee_Voucher{
		
		public static final String NAME = "/export-kingdee-voucher";
		
		public static final String PARAM_F_YEAR = "fYear";
		
		public static final String PARAM_F_NUMER = "fNumber";
		
		public static final String PARAM_F_SERIAL_NUM = "fSerialNum";
		
		public static final String PARAM_F_PREPARER_ID = "fPreparerId";
		
	}
	
	public class I_Export_Yongyou_Voucher{
		
		public static final String NAME = "/export-yongyou-voucher";
		
		public static final String PARAM_F_VOUCHERNO = "fVoucherNo";
		
		public static final String PARAM_F_VOUCHER_MAKER = "fVoucherMaker";
		
		public static final String PARAM_F_BOOKNO = "fbookNo";
		
		public static final String PARAM_F_UNIT = "fUnit";
		
		public static final String PARAM_F_FINICIA_YEAR = "fFinicialYear";
		
		public static final String PARAM_F_FINICIA_PERIOD = "fFinicialPeriod";
		
		public static final String PARAM_F_ENTRY_AUTONO = "fEntryAutoNo";
		
		public static final String PARAM_F_IDENTITY = "fIdentity";
		
	}
	
	public static class I_Export_AppArriveRecord{
		
		public static final String NAME = "/export-apparrive-record";
	}
	public static class I_List_Account{
		
		public static final String NAME = "/list-account-accountName";
		
		public static final String PARAM_APP_ID = "appId";
		
		public static final String PARAM_TYPE = "type";
		
		public static final String RTN_ACCOUNT_LIST = "accountList";
	}
	public static class I_Fill_Cash_Flow{
		
		public static final String NAME = "/fill-cash-flow-uuid";

	}
	public static class I_Add_Cash_Flow{
		
		public static final String NAME = "/add-cash-flow";

	}
	public static class I_Monitor_Cash_Flow{
		
		public static final String NAME = "/flow/monitor";
		
		public static final String PARAM_CASH_FLOW_UUID = "cashFlowUuid";
		
		public static final String PARAM_ACCOUNT_SIDE = "accountSide";
		
		public static final String RNT_BILL_MATCH_RESULT_LIST = "billMatchResultList";
	}
	public static class I_Voucher_Change{
		
		public static final String NAME = "/voucher-change";
		
		public static final String PARAM_APP_ID = "appId";
		
		public static final String PARAM_CASH_FLOW_UUID = "cashFlowUuid";
		
		public static final String PARAM_ACCOUNT_SIDE = "accountSide";
		
		public static final String PARAM_BILL_MATCH_RESULT_LIST = "billMatchResultList";
		
		public static final String RTN_AUDIT_STATUS = "auditStatus";
		
		public static final String RTN_DUPLICATED_BUSINESS_NO = "duplicatedBusinessNo";
	}
	public static class I_Close_CashFlow_Audit{
		
		public static final String NAME = "/close-cashflow-audit";
		
		public static final String PARAM_APP_ID = "appId";
		
		public static final String PARAM_CASH_FLOW_UUID = "cashFlowUuid";
		
		public static final String PARAM_ACCOUNT_SIDE = "accountSide";
		
		public static final String PARAM_BILL_MATCH_RESULT_LIST = "billMatchResultList";
		
		public static final String RTN_AUDIT_STATUS = "auditStatus";
		
		public static final String RTN_DUPLICATED_BUSINESS_NO = "duplicatedBusinessNo";
	}
	public static class I_List_Bill_Match_Result{
		
		public static final String NAME = "/list-billMatchResult/{cashFlowUuid}";
		
		public static final String PARAM_CASH_FLOW_UUID = "cashFlowUuid";
		
		public static final String PARAM_ACCOUNT_SIDE = "accountSide";
		
		public static final String RNT_BILL_MATCH_RESULT_LIST = "billMatchResultList";
	}
	public static class I_Show_CashFlow_Detail{
		
		public static final String NAME = "/show-cash-detail";
		
		public static final String PARAM_CASH_FLOW_UUID = "cashFlowUuid";
		
		public static final String PARAM_APP_ID = "appId";
		
		public static final String PARAM_ACCOUNT_SIDE = "accountSide";
		
		public static final String RTN_CASH_FLOW_DETAIL = "cashFlowDetail";
		
		public static final String RTN_AUDIT_LOG = "auditLog";
	
	}
	public static class I_List_BillingPlan_For_CashFlow{
		
		public static final String NAME = "billing-plan" + "/index";
		
		public static final String PARAM_CASH_FLOW_UUID = "cashFlowUuid";
		
		public static final String PARAM_APP_ID = "appId";
		
		public static final String PARAM_ACCOUNT_SIDE = "accountSide";
		
		public static final String RNT_BILL_MATCH_RESULT_LIST = "billMatchResultList";
	}
	public static class I_Query_BillingPlan_For_CashFlow{
		
		public static final String PARAM_CASH_FLOW_UUID = "cashFlowUuid";
		
		public static final String PARAM_APP_ID = "appId";
		
		public static final String PARAM_ACCOUNT_SIDE = "accountSide";
		
		public static final String RNT_BILL_MATCH_RESULT_LIST = "billMatchResultList";
	}
	public static class I_Query_BillingPlan_For_Debit{
		
		public static final String NAME = "/query-billingPlan";
		
		
		public static final String PARAM_CASH_FLOW_UUID = "cashFlowUuid";
		
		public static final String PARAM_APP_ID = "appId";
		
		public static final String PARAM_ACCOUNT_SIDE = "accountSide";
		
		public static final String RNT_BILL_MATCH_RESULT_LIST = "billMatchResultList";
	}
}
