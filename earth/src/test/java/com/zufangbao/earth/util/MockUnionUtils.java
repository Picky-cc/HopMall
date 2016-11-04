package com.zufangbao.earth.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.unionpay.QueryOrderResult;
import com.zufangbao.sun.yunxin.entity.unionpay.QueryOrderResult.OrderDetail;
import com.zufangbao.sun.yunxin.entity.unionpay.SinglePayResult;

public class MockUnionUtils {
	@Autowired
	private MockUtils mockUtils;
	
	private String urlPrex = "/bind/";
	
	public void mockUnionPayAndQuery(){
		mockUtils.init();
		mockUnionPay();
		mockUnionQuery();
	}
	
	public void mockUnionPay(){
		SinglePayResult singlePayResult = new SinglePayResult("0000", "ok", "orderNo",2, new Date(), "queryId"+DateUtils.getCurrentTimeMillis());
		mockUtils.mockPostRequest(urlPrex+"singlePay.do", JsonUtils.toJsonString(singlePayResult));
	}
	public void mockUnionQuery(){
		QueryOrderResult expectedQueryOrderResult = new QueryOrderResult("0000", "ok", "orderNo",
				new OrderDetail("01", "ok", 2,new Date(), "remark"));
		mockUtils.mockPostRequest(urlPrex+"queryOrder.do", JsonUtils.toJsonString(expectedQueryOrderResult));
	}
}
