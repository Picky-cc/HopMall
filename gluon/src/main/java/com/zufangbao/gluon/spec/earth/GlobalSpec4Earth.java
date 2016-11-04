package com.zufangbao.gluon.spec.earth;

public class GlobalSpec4Earth {
	
	public static final String PARAM_APP_ID = "appId";
	public static final String PARAM_CONTRACT_NO = "contractNo";
	public static final String PARAM_PAGE = "page";
	public static final String PARAM_UNIQUE_PARTICLE_ID = "particalUniqueId";
	public static final String RTN_PAGE_QUERY_STRING = "queryString";
	public static final String RTN_PAYMENT_PARTICLE = "quarkParticals";
	public static final String ENCRYPT_SALT_STR = "3aMt3a4UXtNeO-6jwBa2dg==";
	public static final String PARAM_VIEW_ENTRY = "viewEntry";
	public static final String PARAM_VIRTUAL_ACCOUNT_UNIQUE_ID = "virtualAccountUniqueId";
	public static final String PARAM_VIRTUAL_ACCOUNT_UNIQUE_ID_LIST = "virtualAccountUniqueIdList";
	public static final String RTN_VIRTUAL_ACCOUNT_INFO_LIST = "virtualAccountInfoList";
	public static final String PARAM_START_DATE = "startDate";
	public static final String PARAM_END_DATE = "endDate";
	public static final String PARAM_TRANSACTION_RECORD_STATUS = "transactionRecordStatus";
	public static final String PARAM_BILL_STATUS_JSON = "billStatus";
	public static final String PARAM_TRANSACTION_QUERY_CONDITION = "transactionQueryCondition";
	public static final String PARAM_TRADENO = "tradeNo";
	public static final String PARAM_PAYNO = "payNo";
	
	public static final String ORDERLIST = "orderList";
	public static final String UNDERLINE_SPLIT_UNIQUEBILLID = "_";
	
	public static final int ERROR_EMPTY_APPID = -100;
	public static final String MSG_EMPTY_APPID = "appId为空！";
	public static final int ERROR_NOT_EXIST_APP = ERROR_EMPTY_APPID - 1;
	public static final String MSG_NOT_EXIST_APP = "app不存在！";
	public static final int ERROR_ILLEGAL_PARAMS = ERROR_NOT_EXIST_APP - 1;
	public static final String MSG_ILLEGAL_PARAMS = "非法参数！";
	
	public static final int ERROR_NO_DATA = -200;
	
	/**
	 * code为-1
	 */
	public static final int CODE_ENUM_ALL = -1;
	
	/**
	 * code值为[-2000] -- [-6000]
	 */
	
	public static final int ERROR_EMTPY_VIRTUAL_ACCOUNT_UNIQUE_ID = ERROR_ILLEGAL_PARAMS -1;
	
	public static final int ERROR_PARTICAL_SYSTEM=-20000;
	private static final int ERROR_CONTRACT_PARTICAL = -30000;
	private static final int ERROR_AUTHORISED_BILL_SKETCH = -4000;
	private static final int ERROR_RECEIVABLES = -5000;
	private static final int ERROR_SETTING = -5100;
	private static final int  ERROR_ORDER_VIRTUAL_ACCOUNT = -5200;
	private static final int  ERROR_VIRTUAL_ACCOUNT = -5300;
	private static final int  ERROR_BILL_DEPOSITE = -5400;
	private static final int  ERROR_VIRTUAL_ACCOUNT_CACHE = -5500;
	private static final int ERROR_CONTRACT = -5550;
	private static final int ERROR_ORDER = -5560;
	private static final int ERROR_RENTER_NOTIFY = -5570;
	private static final int ERROR_CUSTOMER = -5580;
	private static final int ERROR_WELLS_FARGO = - 5600;
	private static final int ERROR_APP_ARRIVE_RECORD = - 5700;
	
	/**
	 * 云信项目code值为[-6000] -- [-7000]
	 */
	private static final int ERROR_CODE_YUNXIN = -6000;
	
	public class ErrorCode4ParticalSystem
	{
		public static final int ERROR_TRY_TO_CREATE_DUPLICATE_PARTICALS=ERROR_PARTICAL_SYSTEM-1;
		public static final int ERROR_TRY_TO_CREATE_PARTICALS_WITH_INVALID_DATA = ERROR_TRY_TO_CREATE_DUPLICATE_PARTICALS-1;
		public static final int ERROR_PARTICAL_NOT_FOUND = GlobalSpec4Earth.ERROR_ILLEGAL_PARAMS - 1;
		public static final int ERROR_PARTICALMODEL_NOT_FOUND  = ERROR_PARTICAL_NOT_FOUND - 1;
	}
	public class ErrorMsg4ParticalSystem
	{
		public static final String MSG_PARTICAL_SYSTEM = "partical system 出错！";
		public static final String MSG_TRY_TO_CREATE_DUPLICATE_PARTICALS = "尝试创建重复的particals！";
		public static final String MSG_TRY_TO_CREATE_PARTICALS_WITH_INVALID_DATA = "尝试使用无效的数据创建particals！";
		public static final String MSG_PARTICAL_NOT_FOUND = "partical找不到！";
		public static final String MSG_PARTICALMODEL_NOT_FOUND  = "partical model找不到！";
	}
	public class ErrorCode4ContractPartical{
		
		public static final int ERROR_EXIST_CONTRACT_PARTICAL = ERROR_CONTRACT_PARTICAL - 1;
		public static final int ERROR_NOTEXIST_CONTRACT_PARTICAL = ERROR_EXIST_CONTRACT_PARTICAL - 1;;
		
	}
	public class ErrorMsg4ContractPartical{

		public static final String MSG_EXIST_CONTRACT_PARTICAL = "该租约已存在支付通道中！";
		public static final String MSG_NOTEXIST_CONTRACT_PARTICAL = "该租约不存在支付通道中！";
		
	}
	public class ErrorCode4AutorisedBillSketch{

		public static final int  ERROR_GENERATE_BILL_UNIQUEID = ERROR_AUTHORISED_BILL_SKETCH  - 1;
		public static final int ERROR_EMPTY_AUTHORISED_BILLSKETCH = ERROR_GENERATE_BILL_UNIQUEID - 1;
		public static final int ERROR_EMPTY_UNIQUE_BILLIDS = ERROR_EMPTY_AUTHORISED_BILLSKETCH - 1;
		public static final int ERROR_NOT_EXIST_ORDER = ERROR_EMPTY_UNIQUE_BILLIDS - 1;
		public static final int ERROR_EMPTY_HOT_BILL_SKETCHS = ERROR_NOT_EXIST_ORDER - 1;
		public static final int ERROR_BEGIN_OR_MAX_INVALID = ERROR_EMPTY_HOT_BILL_SKETCHS - 1;

	}
	public class ErrorMsg4AutorisedBillSketch{

		public static final String MSG_GENERATE_BILL_UNIQUEID = "生成Bill的uniqueId出错！";
		public static final String MSG_EMPTY_AUTHORISED_BILLSKETCH = "授权的bill sktech为空！";
		public static final String MSG_EMPTY_UNIQUE_BILLIDS = "uniqueBillId集合为空！";
		public static final String MSG_NOT_EXIST_ORDER = "不存在该订单！";
		public static final String MSG_EMPTY_HOT_BILL_SKETCHS = "hot状态的bill sketchs为空！";
		public static final String MSG_BEGIN_OR_MAX_INVALID = "最大或最小值无效！";

	}
	public class ErrorCode4Receivables{
		
		public  static final  int  ERROR_CONFIRM_TRANSACTION_RECORD_FAIL = ERROR_RECEIVABLES - 1;
	}
	
	public class ErrorMsg4Receivables{
		
		public static final String MSG_CONFIRM_TRANSACTION_RECORD_FAIL = "确认交易纪录失败！";
	}
	public class ErrorCode4Setting{
		
		public  static final  int  ERROR_NO_PRINCIPAL_ACCOUNT = ERROR_SETTING - 1;
	}
	
	public class ErrorMsg4Setting{
		
		public static final String MSG_NO_PRINCIPAL_ACCOUNT = "该App没有开通登陆账号！";
	}
	
	public class ErrorCode4OrderVirutalAccount{
		
		public  static final int  ERROR_ILLEGAL_PARAM_ACCOUNT_UNIQUE_ID_EMPTY = ERROR_ORDER_VIRTUAL_ACCOUNT - 1;
		public  static final int  ERROR_ILLEGAL_PARAM_PARTICAL_UNIQUE_ID_EMPTY = ERROR_ILLEGAL_PARAM_ACCOUNT_UNIQUE_ID_EMPTY - 1;
		public  static final int  ERROR_ILLEGAL_PARAM_OREDER_ID_INVALID = ERROR_ILLEGAL_PARAM_PARTICAL_UNIQUE_ID_EMPTY - 1;
		public  static final int  ERROR_ILLEGAL_PARAM_OREDER_ID_LIST_EMPTY = ERROR_ILLEGAL_PARAM_OREDER_ID_INVALID - 1;
		public  static final int  ERROR_ORDER_ALREADY_BINDED = ERROR_ILLEGAL_PARAM_OREDER_ID_LIST_EMPTY - 1;
		public  static final int  ERROR_ORDER_ALREADY_UNBINDED = ERROR_ORDER_ALREADY_BINDED - 1;
		public  static final int  ERROR_ORDER_NOT_EXIST = ErrorCode4Order.ERROR_NO_SUCH_OREDER;
		public static final int ERROR_PARSE_ORDER_ID_LIST_IN_JSON = ERROR_ORDER_NOT_EXIST - 1;
		public  static final int  ERROR_ILLEGAL_PARAM_APP_ID_EMPTY = ERROR_PARSE_ORDER_ID_LIST_IN_JSON - 1;
		public  static final int  ERROR_ILLEGAL_PARAM_VIRTUAL_ACCOUNT_INFO = ERROR_ILLEGAL_PARAM_APP_ID_EMPTY - 1;
		public  static final int  ERROR_ORDER_VIRTUAL_ACCOUNT_NOT_EXIST = ERROR_ILLEGAL_PARAM_VIRTUAL_ACCOUNT_INFO - 1;
		public  static final int  ERROR_ORDER_BIND_STATUS_EXCEPTION = ERROR_ORDER_VIRTUAL_ACCOUNT_NOT_EXIST - 1;
		public  static final int  ERROR_ORDER_UNBIND_STATUS_EXCEPTION = ERROR_ORDER_BIND_STATUS_EXCEPTION - 1;
		public  static final int  ERROR_VIRTUALACCOUNT_NOT_IN_THE_SAME_PARTICAL = ERROR_ORDER_UNBIND_STATUS_EXCEPTION - 1;
		public  static final int  ERROR_VIRTUALACCOUNT_REMOTE_TRANSFER = ERROR_VIRTUALACCOUNT_NOT_IN_THE_SAME_PARTICAL - 1;
	}
	
	public class ErrorMsg4OrderVirutalAccount{
		
		public static final String ERROR_ILLEGAL_PARAM_ACCOUNT_UNIQUE_ID_EMPTY = "非法参数，账户标识为空！";
		public  static final String  ERROR_ILLEGAL_PARAM_PARTICAL_UNIQUE_ID_EMPTY = "非法参数，通道标识为空！";
		public  static final String  ERROR_ILLEGAL_PARAM_OREDER_ID_INVALID= "非法参数，订单号非法！";
		public  static final String  ERROR_ILLEGAL_PARAM_OREDER_ID_LIST_EMPTY = "非法参数，订单号集合为空！";
		public  static final String  ERROR_ORDER_ALREADY_BINDED = "错误，订单已划入，不要重复划入！";
		public  static final String  ERROR_ORDER_ALREADY_UNBINDED = "错误，订单已划出，不要重复划出！";
		public  static final String  ERROR_ORDER_NOT_EXIST = "错误，订单不存在！";
		public static final String ERROR_PARSE_ORDER_ID_LIST_IN_JSON = "json解析订单主键集合错误!";
		public  static final String  ERROR_ILLEGAL_PARAM_APP_ID_EMPTY = "非法参数，appId为空！";
		public  static final String  ERROR_ORDER_VIRTUAL_ACCOUNT_NOT_EXIST = "订单账户不存在！";
		public  static final String  ERROR_ORDER_BIND_STATUS = "划入订单的状态异常！";
		public  static final String  ERROR_ORDER_UNBIND_STATUS = "划出订单的状态异常！";
		public  static final String  ERROR_VIRTUALACCOUNT_NOT_IN_THE_SAME_PARTICAL = "账户不存在同一个通道中！";
		public  static final String  ERROR_VIRTUALACCOUNT_REMOTE_TRANSFER = "远程转移账户失败！";
	}
	public class ErrorCode4VirutalAccount{
		
		public static final int ERROR_ILLEGAL_PARAM_APP_ID_EMPTY = ERROR_VIRTUAL_ACCOUNT - 1;
	}
	public class ErrorCode4BillDeposite{
		
		public static final int ERROR_ILLEGAL_PARAM_UNIQUE_PARTICAL_ID_EMPTY = ERROR_BILL_DEPOSITE - 1;
		public static final int ERROR_ILLEGAL_PARAM_UNIQUE_BILL_IDS_EMPTY = ERROR_ILLEGAL_PARAM_UNIQUE_PARTICAL_ID_EMPTY - 1;
		public static final int ERROR_PARSE_UNIQUE_BILL_IDS_IN_JSON = ERROR_ILLEGAL_PARAM_UNIQUE_BILL_IDS_EMPTY - 1;
	}
	public class ErrorMsg4BillDeposite{
		
		public static final String ERROR_ILLEGAL_PARAM_UNIQUE_PARTICAL_ID_EMPTY = "非法参数，通道ID为空！";
		public static final String ERROR_ILLEGAL_PARAM_UNIQUE_BILL_IDS_EMPTY = "非法参数，Bill ID为空！";
		public static final String ERROR_PARSE_UNIQUE_BILL_IDS_IN_JSON = "解析Bill ID 错误！";
	}
	public class ErrorCode4VirutalAccountCache{
		
		public static final int ERROR_ILLEGAL_PARAM_COMPANY_ID= ERROR_VIRTUAL_ACCOUNT_CACHE - 1;
		public static final int ERROR_ILLEGAL_PARAM_VIRTUAL_ACCOUNT_SKETCH_EMPTY = ERROR_ILLEGAL_PARAM_COMPANY_ID - 1;
		public static final int ERROR_ILLEGAL_PARAM_VIRTUAL_ACCOUNT_UNIQUE_ID_EMPTY = ERROR_ILLEGAL_PARAM_VIRTUAL_ACCOUNT_SKETCH_EMPTY - 1;
		public static final int ERROR_VIRTUAL_ACCOUNT_CACHE_NOT_EXIST = ERROR_ILLEGAL_PARAM_VIRTUAL_ACCOUNT_SKETCH_EMPTY - 1;
	}
	public class ErrorCode4Contract{
		
		public static final int ERROR_NO_SUCH_CONTRACT = ERROR_CONTRACT - 1;
		public static final int ERROR_CONTRACT_ALREADY_CLOSED = ERROR_NO_SUCH_CONTRACT - 1;
		public static final int ERROR_EXIST_NOT_CLOSED_ORDER  = ERROR_CONTRACT_ALREADY_CLOSED - 1;
		
	}
	public class ErrorMsg4Contract{
		
		public static final String ERROR_NO_SUCH_CONTRACT = "不存在指定的租约！";
		public static final String ERROR_CONTRACT_ALREADY_CLOSED = "该租约已经关闭，不需要重复关闭！";
		public static final String ERROR_EXIST_NOT_CLOSED_ORDER  = "该租约存在未关闭的订单，先关闭订单！";
		
	}
	public class ErrorCode4Order{
		
		public static final int ERROR_NO_SUCH_OREDER = ERROR_ORDER - 1;
		public static final int ERROR_EMPTY_ORDER_NO = ERROR_NO_SUCH_OREDER - 1;
		public static final int ERROR_ORDER_ALREADY_CLOSED = ERROR_NO_SUCH_OREDER - 1;
		public static final int ERROR_CLOSE_REMOTE_ORDER = ERROR_ORDER_ALREADY_CLOSED - 1;
		
	}
	public class ErrorMsg4Order{
		
		public static final String ERROR_EMPTY_ORDER_NO = "订单号不能为空！";
		public static final String ERROR_NO_SUCH_OREDER = "不存在该订单！";
		public static final String ERROR_ORDER_ALREADY_CLOSED = "该订单已关闭，不需要重复关闭！";
		
	}
	public class ErrorCode4RentNotify{
		
		public static final int ERROR_NO_SMS_SERVICE_SETTING = ERROR_RENTER_NOTIFY - 1;
		
	}
	public class ErrorMsg4RentNotify{
		
		public static final String ERROR_NO_SMS_SERVICE_SETTING = "该App没有配置短信通知服务！";
		
	}
	public class ErrorCode4Customer{
		
		public static final int ERROR_NO_SUCH_CUSTOMER = ERROR_CUSTOMER - 1;
		
	}
	public class ErrorMsg4Customer{
		
		public static final String ERROR_NO_SUCH_CUSTOMER = "不存在该用户！";
		
	}
	public class ErrorCode4Audit{
		
		public static final int ERROR_NO_JOURNALVOUCHER = ERROR_NO_DATA - 1;
		
	}
	public static class ErrorMsg4Audit{

		public static final String REPEAT_BILL = "重复订单";
		
	}
	
	public static class ErrorCode4WellsFargo{
		
		public static final int ERROR_NO_SUBJECT_MATTER = ERROR_WELLS_FARGO - 1;
		public static final int ERROR_NOT_EXIST_BUSINESS_CONTRACT = ERROR_NO_SUBJECT_MATTER - 1;
		public static final int ERROR_NOT_EXIST_UNDERLYING_ASSET = ERROR_NOT_EXIST_BUSINESS_CONTRACT - 1;
		public static final int ERROR_PUSH_DOCUMENT_SLICE_FAIL = ERROR_NOT_EXIST_UNDERLYING_ASSET - 1;
		public static final int ERROR_REMOVE_ASSET_FAIL = ERROR_PUSH_DOCUMENT_SLICE_FAIL - 1;
		public static final int ERROR_ADD_ASSET_FAIL = ERROR_REMOVE_ASSET_FAIL - 1;
		public static final int ERROR_AUDIT_ASSET_NOT_EXIST_ASSET = ERROR_ADD_ASSET_FAIL - 1;
		public static final int ERROR_AUDIT_ASSET_NO_PERMISSIONS = ERROR_AUDIT_ASSET_NOT_EXIST_ASSET - 1;
		public static final int ERROR_AUDIT_ASSET_DATA_OUT_OF_DATE = ERROR_AUDIT_ASSET_NO_PERMISSIONS - 1;
		
		public static final int ERROR_CREATE_BUSINESS_CONTRACT = ERROR_WELLS_FARGO - 20;
		public static final int ERROR_LEASING_BUSINESS_CONTRACT_FORM = ERROR_CREATE_BUSINESS_CONTRACT-1;
		public static final int ERROR_LEASING_BILLING_PLAN_FORM = ERROR_LEASING_BUSINESS_CONTRACT_FORM-1;
		public static final int ERROR_CREATE_CONTRACT_WRONG_PARTY_CONCERNED = ERROR_LEASING_BILLING_PLAN_FORM-1;
		public static final int ERROR_CREATE_CONTRACT_WRONG_TRADE_PARTY = ERROR_CREATE_CONTRACT_WRONG_PARTY_CONCERNED-1;
		public static final int ERROR_CREATE_CONTRACT_IN_CREATE_PAYMENTTERM = ERROR_CREATE_CONTRACT_WRONG_TRADE_PARTY-1;
		public static final int ERROR_CREATE_BUSINESS_CONTRACT_DETAIL = ERROR_CREATE_CONTRACT_IN_CREATE_PAYMENTTERM-1;
		public static final int ERROR_CREATE_BUSINESS_CREATING_BILLINGPLAN = ERROR_CREATE_BUSINESS_CONTRACT_DETAIL-1;
		public static final int ERROR_CREATE_BUSINESS_DUPLICATE_CREATATION = ERROR_CREATE_BUSINESS_CREATING_BILLINGPLAN-1;
		public static final int ERROR_END_BUSINESS = ERROR_CREATE_BUSINESS_DUPLICATE_CREATATION-1;
		
		public static final int ERROR_NO_UNDERLYING_ASSET = ERROR_CREATE_BUSINESS_CONTRACT -20;
		
		
		public static final int ERROR_NO_PARTY_CONCERNED = ERROR_NO_UNDERLYING_ASSET -20;
		
		
		public static final int ERROR_NO_TRADE_PARTY =ERROR_NO_PARTY_CONCERNED-20;
		
		
		public static final int ERROR_NO_EXIST_SUBJECT_MATTER =ERROR_NO_TRADE_PARTY-20;
		
		public static final int ERROR_RENTAL_BUSINESS_CONTRACT_FORM = ERROR_NO_EXIST_SUBJECT_MATTER-1;
		
		
	}
	
	public static class ErrorCode4Yunxin{
		public static final int ERROR_NO_BATCH_PAY = ERROR_CODE_YUNXIN-1;
		public static final int ERROR_NO_CREATE_BATCH_PAY_RECORD = ERROR_NO_BATCH_PAY-1;
		public static final int ERROR_NO_CREATE_BATCH_PAY_RECORD_NO_TRANSACTIONAPP = ERROR_NO_CREATE_BATCH_PAY_RECORD-1;
		public static final int ERROR_NO_CREATE_BATCH_PAY_RECORD_ACCOUNT_INCONSISTENT = ERROR_NO_CREATE_BATCH_PAY_RECORD_NO_TRANSACTIONAPP-1;
	}
	
	public static class ErrorMsg4Yunxin{
		public static final String ERROR_NO_BATCH_PAY = "批量代扣出错";
		public static final String ERROR_NO_CREATE_BATCH_PAY_RECORD = "生成批量代扣单出错";
		public static final String ERROR_NO_CREATE_BATCH_PAY_RECORD_NO_TRANSACTIONAPP = "生成批量代扣单出错：转账申请单为空!";
		public static final String ERROR_NO_CREATE_BATCH_PAY_RECORD_ACCOUNT_INCONSISTENT = "生成批量代扣单出错：账号不一致";
	}
	
	public static class ErrorMsg4WellsFargo{
		
		public static final String ERROR_NO_SUBJECT_MATTER = "商业合约[{0}]没有相应标的。";
		public static final String ERROR_NOT_EXIST_BUSINESS_CONTRACT = "商业合约[{0}]不存在。";
		public static final String ERROR_NOT_EXIST_UNDERLYING_ASSET = "基础资产[{0}]不存在。";
		public static final String ERROR_PUSH_DOCUMENT_SLICE_FAIL = "提交文档失败";
		public static final String ERROR_REMOVE_ASSET_FAIL = "撤回资产失败[{0}]。";
		public static final String ERROR_ADD_ASSET_FAIL = "资产无法进行重复投递,合约号为[{0}]。";
		public static final String ERROR_AUDIT_ASSET_NOT_EXIST_ASSET = "资产[{0}]不存在，无法进行审核。";
		public static final String ERROR_AUDIT_ASSET_NO_PERMISSIONS = "权限不足，无法审核资产[{0}]。";
		public static final String ERROR_AUDIT_ASSET_DATA_OUT_OF_DATE = "资产[{0}]有更新，请重新尝试审核。";
		
		
		public static final String ERROR_CREATE_BUSINESS_CONTRACT = "创建合同出错";
		public static final String ERROR_LEASING_BUSINESS_CONTRACT_FORM = "合同表单有误";
		public static final String ERROR_LEASING_BILLING_PLAN_FORM = "账单表单有误";
		public static final String ERROR_CREATE_CONTRACT_WRONG_PARTY_CONCERNED = "联系人信息有误";
		public static final String ERROR_CREATE_CONTRACT_WRONG_TRADE_PARTY = "承租人信息有误";
		public static final String ERROR_CREATE_CONTRACT_IN_CREATE_PAYMENTTERM = "租金信息有误";
		public static final String ERROR_CREATE_BUSINESS_CONTRACT_DETAIL="生成合同详情时出错";
		public static final String ERROR_CREATE_BUSINESS_CREATING_BILLINGPLAN="生成账单时出错";
		public static final String ERROR_CREATE_BUSINESS_DUPLICATE_CREATATION="重复创建合同!";
		public static final String ERROR_END_BUSINESS="该租约仍有未关闭的账单，请先关闭所有账单后重试！";
		
		public static final String ERROR_NO_UNDERLYING_ASSET = "没有基础资产";
		
		
		public static final String ERROR_NO_PARTY_CONCERNED = "没有联系人";
		
		
		public static final String ERROR_NO_TRADE_PARTY = "没有承租人";
		
		
		public static final String ERROR_NO_EXIST_SUBJECT_MATTER = "没有相应的物业";
		
	}
	public static class ErrorCode4AppArriveRecord{
		public static final int ERROR_NO_SUCH_CASH_FLOW = ERROR_APP_ARRIVE_RECORD - 1;
	}
	public static class MessageCode4AppArriveRecord{
		public static final String ERROR_NO_SUCH_CASH_FLOW = "没有对应的现金流";
	}
	
}
