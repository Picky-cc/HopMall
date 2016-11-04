package com.zufangbao.earth.api;

public class TransactionDetailConstant {
	public static class RTNCode{
		public static final int SUC = 0;
		public static final int SUBMIT_TO_HOST_FAIL = -1;
		public static final int EXECUTE_FAIL = -2;
		public static final int WRONG_FORMAT = -3;
		public static final int NOT_LOGIN = -4;
		public static final int REQUEST_FREQUENTLY = -5;
		public static final int NOT_AUTHORIZED_USER = -6;
		public static final int USER_CANCELLED = -7;
		public static final int OTHER_ERROR = -9;
	}
	public static class ErrMsg{
		public static final String MSG_SUC = "成功";
		public static final String SYSTEM_ERROR = "其他错误";
		public static final String ERR_MSG_NO_MERID_ACCOUNTNO = "没有对应的商户号和账号";
		public static final String ERR_MSG_UNIONPAY_XML_FORMAT = "银联报文解析失败";
		
		
		public static final String ERR_MSG_WRONG_BEGIN_DATE_FORMAT="起始日期格式错误";
		public static final String ERR_MSG_WRONG_END_DATE_FORMAT="结束日期格式错误";
		public static final String ERR_MSG_TODAY_BEGIN_DATE_GT_60 = "起始日期与当前日期超过60天";
		public static final String ERR_MSG_END_DATE_BEGIN_DATE_GE_3 = "起始日期与结束日期跨度超过3天";
		public static final String ERR_MSG_EMPTY_MERID = "商户号为空";
		public static final String ERR_XML_FORMAT = "报文格式错误";
		public static final String ERR_FUNC_NAME = "非法函数名";
		public static final String ERR_WRONG_GATE_WAY = "没有相应的网关";
		public static final String ERR_REPEAT_YURREF = "业务参考号重复";
		
		public static final String ERR_GATEWAY_UNAVAILABLE = "该网关尚未开放";
		public static class ErrMSg4DirectBank{
			public static final String ERR_MSG_EMPTY_ACCOUNT ="银行账号为空";
			public static final String ERR_MSG_END_DATE_BEGIN_DATE_GE_100 = "起始日期与结束日期跨度超过100天";
			public static final String ERR_MSG_NO_ACCOUNT ="没有对应的银行账号";
		}
		
		public static final String ERR_GATEWAY = "该网关尚未开放";
		
		public static final String ERR_Account = "该账号尚未开放此服务";
	}
	public static class FunctionName{
		public static final String FUNC_TRANSACTION_DETAIL_QUERY = "NTEBPINF";
		public static final String FUNC_BATCH_PAYMENT = "NTIBCOPR";
		public static final String FUNC_TRANSACTION_STATUS_QUERY = "NTQRYEBP";
		
	}
}
