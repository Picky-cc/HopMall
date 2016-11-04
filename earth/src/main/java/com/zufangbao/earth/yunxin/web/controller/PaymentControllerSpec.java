package com.zufangbao.earth.yunxin.web.controller;

public class PaymentControllerSpec {
	public static final String VIEW_PREFIX = "yunxin/payment/";

	public static final String NAME = "payment-manage";

	
	public static final String PAYMENTQUERY = "payment/query";
	public static final String PAYMENTLIST = "payment/list";
	public static final String PAYMENTDETAIL = "payment/{transferApplicationId}/detail";
    public static final String PAYMENTEXPORTEXCEL = "payment/exportexcel";
    public static final String DAILYRETURNLISTEXPORTEXCEL = "payment/export/daily-return-list";
	public static class LIST_ORDER{
		public static final String VIEW = VIEW_PREFIX+ "order-list";
		public static final String RT_ORDERLIST = "orderList";
		public static final String RT_APPLIST = "appList";
		public static final String RT_FINANCIALCONTRACTLIST = "financialContracts";
		public static final String RT_EXECUTINGSETTLINGSTATUSLIST = "executingSettlingStatusList";
		public static final String RT_OVERDUE_STATUS_LIST = "overDueStatusList";
		public static final String RT_ORDER_QUERY_MODEL = "orderQueryModel";
		public static final String RT_QUERY_STRING = "queryString";
	}

}
