package com.zufangbao.earth.yunxin.web.controller;

public class YunxinOfflinePaymentControllerSpec {

	
	public static final String NAME = "offline-payment-manage";
	
	public static final String PAYMENTLIST = "payment/list";

    public static final String PAYEMENTQUERY = "payment/query";
    
    public static final String PAYEMENTDETAIL = "payment/{offlineBillId}/detail";
    
    public static final String ILLEGAL_INPUT_AMOUNT = "请输入正确的金额！";
    
    public static final String SOURCE_DOCUMENT_NOT_EXIST = "原始凭证不存在！";
    
    public static final String AUDIT_WITH_LAPSED_GUARANTEE_ORDER = "不可关联已作废的担保单";
    
    public static final String JOURNAL_VOUCHER_ERROR = "凭证制作出错";
}