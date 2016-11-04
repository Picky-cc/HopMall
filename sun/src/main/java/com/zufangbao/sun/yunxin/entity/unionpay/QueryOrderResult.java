package com.zufangbao.sun.yunxin.entity.unionpay;

import java.util.Date;

/**
 * 银联查询订单返回结果类
 * 
 * @author louguanyang
 *
 */
public class QueryOrderResult {
	/** 返回码 */
	private String retCode;
	/** 失败返回错误描述 */
	private String retDesc;
	/** 交易唯一编号 */
	private String orderNo;
	/** 订单详情 */
	private OrderDetail orderDetail;

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

	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}
	
	public boolean isSuc(){
		return orderDetail==null?false:orderDetail.isSuc();
	}
	
	public boolean isFail(){
		return orderDetail==null?true:orderDetail.isFail();
	}

	public QueryOrderResult(){
		
	}
	
	public QueryOrderResult(String retCode, String retDesc, String orderNo,
			OrderDetail orderDetail) {
		super();
		this.retCode = retCode;
		this.retDesc = retDesc;
		this.orderNo = orderNo;
		this.orderDetail = orderDetail;
	}

	/**
	 * 银联查询订单 订单详情类
	 * 
	 * @author louguanyang
	 *
	 */
	public static class OrderDetail {
		/** 查询订单状态返回码 */
		private String retCode;
		/** 查询订单状态描述 */
		private String retDesc;
		/**
		 * 订单状态0:已接受,1:处理中,2:处理成功,3:处理失败,4:撤销处理中,5:撤销成功
		 * {@link OrderStatusEnum}
		 */
		private int orderStatus;
		/** 查询订单系统处理日期 */
		private Date processDate;
		/** 查询订单备注 */
		private String remark;

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

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}
		
		public OrderDetail(){
			
		}
		
		public OrderDetail(String retCode, String retDesc, int orderStatus,
				Date processDate, String remark) {
			super();
			this.retCode = retCode;
			this.retDesc = retDesc;
			this.orderStatus = orderStatus;
			this.processDate = processDate;
			this.remark = remark;
		}

		public boolean isSuc(){
			return orderStatus==OrderStatusEnum.PROCESSING_SUCCESS.ordinal();
		}
		public boolean isFail(){
			return orderStatus==OrderStatusEnum.PROCESSING_FAILED.ordinal() ||orderStatus==OrderStatusEnum.ACCEPTED.ordinal() ;
		}
	}
}
