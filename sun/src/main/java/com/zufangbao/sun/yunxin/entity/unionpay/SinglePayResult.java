package com.zufangbao.sun.yunxin.entity.unionpay;

import java.util.Date;

/**
 * 银联单笔支付返回结果
 * 
 * @author louguanyang
 *
 */
public class SinglePayResult {
	/**
	 * 成功的返回码
	 */
	public final static String SUCCESS_RET_CODE = "0000";

	/** 返回码 */
	private String retCode;
	/** 失败返回错误描述 */
	private String retDesc;
	/** 交易唯一编号 */
	private String orderNo;
	/**
	 * 订单状态0:已接受, 1:处 理中,2:处理成功,3:处理失败 {@link OrderStatusEnum}
	 */
	private int orderStatus;
	/** 系统处理日期 */
	private Date processDate;
	/** 平台流水号 */
	private String queryId;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetDesc() {
		return retDesc;
	}

	public void setRetDesc(String retDesc) {
		this.retDesc = retDesc;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public SinglePayResult() {
		super();
	}
	
	public SinglePayResult(String retCode, String retDesc, String orderNo,
			int orderStatus, Date processDate, String queryId) {
		super();
		this.retCode = retCode;
		this.retDesc = retDesc;
		this.orderNo = orderNo;
		this.orderStatus = orderStatus;
		this.processDate = processDate;
		this.queryId = queryId;
	}

}
