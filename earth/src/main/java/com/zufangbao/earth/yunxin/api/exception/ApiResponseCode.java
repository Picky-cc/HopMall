package com.zufangbao.earth.yunxin.api.exception;

/**
 * 接口响应代码
 * @author zhanghongbing
 *
 */
public class ApiResponseCode {
	/** 成功 **/
	public static final int SUCCESS = 0; 

	/**** 系统级错误代码 1xxxx ****/
	
	/** 系统错误 **/
	public static final int SYSTEM_ERROR = 10001;
	/** 接口不存在 **/
	public static final int API_NOT_FOUND = 10002;
	/** 接口已关闭 **/
	public static final int API_UNAVAILABLE = 10003;
	/** 功能代码未指定或该请求接口下不存在此功能代码！**/
	public static final int INVALID_FN_CODE = 10004;
	/** 验签失败 **/
	public static final int SIGN_VERIFY_FAIL = 10005;
	/** 缺少商户号merId或商户密钥secret **/
	public static final int SIGN_MER_CONFIG_ERROR = 10006;
	/** 系统繁忙，请稍后再试 **/
	public static final int SYSTEM_BUSY = 10007;
	
	/**** 业务级错误代码 2xxxx ****/

	/** 无效参数 **/
	public static final int INVALID_PARAMS = 20001;
	/** 请求参数解析错误 **/
	public static final int WRONG_FORMAT = 20002;
	
	/** 合同，还款计划相关，错误代码 21xxx **/
	/** 贷款合同不存在 **/
	public static final int CONTRACT_NOT_EXIST = 21001;
	/** 请求编号重复 **/
	public static final int REPEAT_REQUEST_NO = 21002;
	/** 当前贷款合同无法变更 **/
	public static final int FAIL_TO_MODIFY = 21003;
	/** 无效的计划本金总额 **/
	public static final int INVALID_PRINCIPAL_AMOUNT = 21004;
	/** 无效的计划利息总额 **/
	public static final int INVALID_INTEREST_AMOUNT = 21005;
	/** 没有逾期还款计划 **/
	public static final int NO_OVERDUE_REPAYMENT_PLAN = 21006;
	/** 扣款金额超过剩余可扣金额 **/
	public static final int OVERFLOW_DEDUCT_AMOUNT = 21007;
	/** 逾期扣款请求不存在 **/
	public static final int OVERDUE_DEDUCT_REQUEST_NOT_FOUND = 21008;
	/** 计划还款日期排序错误，需按计划还款日期递增 **/
	public static final int WRONG_ASSET_RECYCLE_DATE = 21009;
	/** 不存在未到期还款计划 **/
	public static final int NO_AVAILABLE_ASSET_SET = 21010;
	/** 存在未执行的提前还款 **/
	public static final int PREPAYMENT_ASSETSET_EXSITED = 21011;
	/** 无效的提前还款金额 **/
	public static final int PREPAYMENT_AMOUNT_INVALID = 21012;
	/** 存在未偿还款计划 **/
	public static final int EXPIRE_UNCLEAR_ASSETSET_EXISTED = 21013;
	/** 提前还款日期要早于最近一次还款日期 **/
	public static final int WRONG_PREPAYMENT_DATE = 21014;
	/** 扣款唯一编号重复 **/
	public static final int REPEAT_DEDUCT_ID = 21015;
	/** 计划还款日期不能晚于贷款合同终止日期 **/
	public static final int ASSET_RECYCLE_DATE_TOO_LATE = 21016;
	/** 权限不足，当前信托计划无法变更当日还款计划，请联系运营人员开通相关权限！ **/
	public static final int PERMISSION_DENIED = 21017;
	/** 存在当日扣款成功或处理中的还款计划！ **/
	public static final int EXSIT_PROCESSING_OR_SUCCESS_REPAYMENT_PLAN = 21018;
	/** 计划还款日期不能早于贷款合同开始日期 **/
	public static final int ASSET_RECYCLE_DATE_TOO_EARLY = 21019;
	/** 单个贷款合同变更还款计划请求过于频繁，请降低频率后重试 **/
	public static final int REQUEST_FREQUENT = 21020;
	
	/**变更还款信息**/
	/**银行代码错误**/
	public static final int  NO_BANK_CODE = 22010;
	/**账号错误**/
	public static final int  NO_BANK_ACCOUNT = 22011;

	/**** 通道相关错误代码 22100 - 22200 ****/
	/** 支付通道不存在  **/
	public static final int CHANNEL_NOT_FOUND = 22100;
	
	/**扣款接口相关 22201 - 22300**/
	/**单笔还款计划明细总额有误**/
	public static final int  REPAYMENT_DETAILS_AMOUNT_ERROR = 22201;
	/**还款计划总额与扣款总额不等**/
	public static final int  REPAYMENT_TOTAL_AMOUNT_NOT_EQUALS_DEDUCT_AMOUNT = 22202;
	/**还款计划不在贷款合同内**/
	public static final int  REPAYMENT_CODE_NOT_IN_CONTRACT = 22203;
	/**还款计划条数有误**/
	public static final int  REPAYMENT_PLAN_NUMBER_ERROR =  22204;
	/**扣款金额错误**/
	public static final int  DEDUCT_AMOUNT_ERROR =  22205;
	
	/**不存在相应的扣款请求**/
	public static final int  NOT_DEDUCT_ID =  22206;
	/**接口调用日期与计划还款日期不符合**/
	public static final int  API_CALLED_TIME_NOT_MEET_PLAN_RECYCLE_TIME = 22207;
	/**系统查询错误**/
	public static final int  QUERY_SYSTEM_ERROR = 22208;
	/**还款计划已经存在处理中和成功中的扣款申请**/
	public static final int  HAS_EXIST_DEDUCT_APPLICATION = 22209;
	/**还款计划已经成功**/
	public static final int  REPAYMENT_PLAN_HAS_SUCCESS = 22210;
	
	/**资产包导入**/
	//格式校验
	/**合同总条数格式错误**/
	public static final int  TOTAL_NUMBER_ERROR  = 23010;
	/**合同还款本金总额格式错误**/
	public static final int  TOTAL_AMOUNT_ERROR  = 23011;
	/**贷款客户姓名格式错误！**/
	public static final int  LOAN_CUSTOMER_NAME_ERROR  = 23012;
	/**身份证号格式错误**/
	public static final int  ID_CARD_ERROR  = 23013;
	/**贷款本金总额格式错误**/
	public static final int  LOAN_TOTAL_AMOUNT_ERROR  = 23014;
	/**贷款期数格式错误**/
	public static final int  LOAN_PERIODS_ERROR  = 23015;
	/**设定生效日期格式错误**/
	public static final int  EFFECT_DATE_ERROR  = 23016;
	/**设定到期日期格式错误**/
	public static final int  EXPIRE_DATE_ERROR  = 23017;
	/**贷款利率格式错误**/
	public static final int  LOAN_RATES_ERROR  = 23018;
	/**罚息利率格式错误**/
	public static final int  PENALTY_ERROR  = 23019;
	/**未知回款方式**/
	public static final int  NO_MATCH_REPAYMENT_WAY  = 23020;
	/**还款日期格式错误**/
	public static final int  REPAYMENT_PLAN_REPAYMENT_DATE_ERROR  = 23021;
	/**还款本金格式错误**/
	public static final int  REPAYMENT_PRINCIPAL_ERROR  = 23022;
	/**还款利息格式错误**/
	public static final int  REPAYMENT_INTEREST_ERROR  = 23023;
	/**其他金额格式错误**/
	public static final int  OTHER_FEE_ERROR  = 23024;
	/**批次合同总条数错误**/
	public static final int  TOTAL_CONTRACTS_NUMBER_NOT_MATCH = 23025;
	/**批次合同本金总额错误**/
	public static final int  TOTAL_CONTRACTS_AMOUNT_NOT_MATCH = 23026;
	/**贷款合同编号重复**/
	public static final int  EXIST_LOAN_CONTRACT_NO = 23027;
	/**贷款合同唯一编号重复**/
	public static final int  EXIST_LOAN_CONTRACT_UNIQUE_ID = 23028;
	/**标的物已存在**/
	public static final int  EXIST_THE_SUBJECT_MATTER= 23029;
	/**信托合同商户未设置**/
	public static final int  NOT_SET_APP_IN_FINANCIAL_CONTRACT = 23030;
	/**信托产品代码错误**/
	public static final int  FINANCIAL_PRODUCT_CODE_ERROR = 23031;
	/**还款计划总金额错误**/
	public static final int  REPAYMENT_PLAN_TOTAL_AMOUNT_ERROR = 23032;
	/**还款计划总条数错误**/
	public static final int  REPAYMENT_PLAN_TOTAL_PERIODS_ERROR = 23033;
	/**未知银行代码**/
	public static final int  NO_MATCH_BANK = 23034;
	/**未知省份代码**/
	public static final int  NO_MATCH_PROVINCE = 23035;
	/**未知城市代码**/
	public static final int  NO_MATCH_CITY = 23036;
	/**首期还款还款日期错误**/
	public static final int  FIRST_REPAYMENT_DATE_NOT_CORRECT = 23037;
	/**贷款合同编号或者uniqId为空**/
	public static final int  LOAN_CONTRACT_NO_OR_UNIQUEID_IS_EMPTY = 23038;
	/**贷款客户编号错误**/
	public static final int  LOAN_CUSTOMER_NO_ERROR = 23039;
	/**技术服务费格式错误**/
	public static final int   TECH_MAINTENANCE_FEE_ERROR = 23040;
	/**贷款服务费格式错误**/
	public static final int  LOAN_SERVICE_FEE_ERROR = 23041;
	/**还款账户号错误**/
	public static final int  REPAYMENT_ACCOUNT_ERROR = 23042;
	
	
	/** 修改逾期费用相关，错误代码231xx**/
	
	/** 逾期罚息计算日不能早于罚息计算日**/
	public static final int   OVER_DUE_FEE_CALC_DATE_AFTER_OVER_DUE_DATE = 23100;
	/** 还款计划未激活**/
	public static final int   REPAYMENT_PLAN_NOT_OPEN = 23101;
	/**还款计划已还清**/
	public static final int   REPAYMENT_PLAN_IS_PAID_OFF= 23102;

	/** 查询还款清单相关。错误代码3xxx**/
	
	/** 日期范围不准确**/
	public static final int DATE_RANGE_ERROR = 30001;
	/** 查询的日期范围不能超过3个月！**/
	public static final int DATE_RANGE_NOT_ALLOWED_THAN_THREE_MONTHS = 30002;
	/** 信托合同不存在**/
	public static final int FINANCIAL_CONTRACT_NOT_EXIST = 30003;
	
	/** 商户付款凭证相关。错误代码33xxx**/
	
	/** 不支持的交易类型**/
	public static final int NO_SUCH_TRANSACTION_TYPE = 33001;
	/** 不支持的凭证类型**/
	public static final int NO_SUCH_VOUCHER_TYPE = 33002;
	/** 凭证对应流水不存在或已提交!**/
	public static final int NO_SUCH_CASH_FLOW = 33003;
	/** 凭证金额与流水金额不匹配**/
	public static final int VOUCHER_CASH_FLOW_NOT_EQUAL = 33004;
	/** 还款计划与贷款合同不匹配**/
	public static final int REPAYMENT_PLAN_CONTRACT_NOT_EQUAL = 33005;
	/** 信托计划与贷款合同不匹配**/
	public static final int CONTRACT_NOT_EQUAL_FINANCIAL_CONTRACT = 33006;
	/** 凭证对应流水不存在**/
	public static final int NO_SUCH_VOUCHER = 33007;
	/** 当前凭证无法撤销**/
	public static final int VOUCHER_CAN_NOT_CANCEL = 33008;
	/** 明细金额大于还款计划担保金额**/
	public static final int RECEIVABLE_AMOUNT_NOT_EQUAL = 33009;
	/** 明细金额大于还款计划应还金额**/
	public static final int GURANTEE_AMOUNT_NOT_EQUAL = 33010;
	/** 付款人类型错误，0:贷款人,1:商户垫付**/
	public static final int NO_SUCH_VOUCHER_PAYER = 33011;

	/** 主动付款凭证相关。错误代码34xxx**/
	/** 不支持的文件类型**/
	public static final int UNSUPPORTED_FILE_TYPE = 34001;  
	/** 收款账户错误，收款账户不是贷款合同的回款账户**/
	public static final int NO_SUCH_RECEIVABLE_ACCOUNT_NO = 34002;  
	/** 主动付款凭证金额错误，凭证金额应为还款计划总额**/
	public static final int VOUCHER_AMOUNT_ERROR = 34003;  
	
	
}

