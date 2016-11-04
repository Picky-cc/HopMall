package com.zufangbao.gluon.spec.global;

/**
 * 
 * @author wukai
 *
 */
public class GlobalMsgSpec {
	
	public static final String MSG_SUC = "成功";
	public static final String MSG_FAILURE = "失败";
	
	public static final String MSG_SUC_SEND_VERICY_CODE = "已发送验证码到您的手机号";
	
	public class GeneralErrorMsg{

		public static final String MSG_NO_AUTHORITY = "无权访问";
		public static final String MSG_SYSTEM_ERROR = "系统错误，请稍后再试！";
		public static final String MSG_ILLEGAL_REQ = "非法请求！";
		public static final String MSG_ILLEGAL_PARAM = "非法参数请求！";
		
		public static final String MSG_ILLEGAL_VERIFY_CODE = "非法验证码获取请求！";
		public static final String MSG_SEND_VERIFY_CODE = "发送短信验证码失败，请稍后再试!";
		public static final String MSG_CODE_WRONG = "验证码错误";
		public static final String MSG_NO_DATA = "暂无数据！";
		
		public static final String MSG_NO_PAYMENT_RESOURCE = "没有支付通道资源，请联系管理员！";
		public static final String MSG_NO_VIEW_ENTRY = "请重新选择服务商！";
		public static final String MSG_SYSTEM_BUSY = "系统繁忙，请稍后再试！";
		
		public static final String MSG_NET_WORK_FAILURE = "网络错误!";
		public static final String MSG_NO_OBJECT_IN_DB = "数据库中不存在该对象！";
	}
	public class PaymentMsgSpec{
		
		public static final String MSG_ORDER_NO_NOT_VALID = "订单无效！";
		public static final String MSG_UPDATE_PAY_DATA_SUC = "更新支付数据成功!";
		public static final String MSG_UPDATE_PAY_DATA_FAILURE = "更新支付数据失败，请联系客服！";
	}
	
	public class ControllerMsgSpec{
	
	}

}
