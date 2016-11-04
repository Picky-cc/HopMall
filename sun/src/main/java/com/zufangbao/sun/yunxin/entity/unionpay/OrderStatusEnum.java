package com.zufangbao.sun.yunxin.entity.unionpay;

/**
 * 订单状态
 * 
 * 0:已接受,1:处理中,2:处理成功,3:处理失败,4:撤销处理中,5:撤销成功
 * @author louguanyang
 *
 */
public enum OrderStatusEnum {
	ACCEPTED("已接受"),
	PROCESSING("处理中"),
	PROCESSING_SUCCESS("处理成功"),
	PROCESSING_FAILED("处理失败"),
	UNDO_PROCESSING("撤销处理中"),
	UNDO_SUCCESS("撤销成功");
	
	private String key;

	private OrderStatusEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
	
}
