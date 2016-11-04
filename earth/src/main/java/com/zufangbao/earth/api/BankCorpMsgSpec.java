package com.zufangbao.earth.api;

/**
 * 
 * @author zjm
 *
 */
public class BankCorpMsgSpec {
	
	public static final String MSG_SYSTEM_ERROR = "系统错误，请稍后再试！";

	public static final String MSG_CREDIT_EMPTY_ERROR = "批量贷记请求数据为空！";
	
	public static final String MSG_PAYERACCOUNT_EMPTY_ERROR = "获取usbkey失败，未登记的支付账号！";
	
	public static final String MSG_UNKNOWN_USBKEY_ERROR = "获取usbkey失败，未知的usbkey编号！";
	
	public static final String MSG_PACKET_PARSE_ERROR = "响应报文解析失败！";
	
	public static final String MSG_TRANSACTION_ERROR = "交易异常，请查询交易结果确认交易状态！错误信息：";
	
	public static final String MSG_QUERY_ERROR = "查询异常，错误信息：";
	
	public static final String MSG_NULL_REQUEST_ERROR = "查询请求数据异常！";
}
