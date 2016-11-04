package com.zufangbao.sun.yunxin.entity.api;


/**
 * 提前还款状态
 * @author zhanghongbing
 *
 */
public enum PrepaymentStatus {
	
	/** 未还款 **/
	UNFINISHED("enum.prepayment-status.unfinished"),
	/** 还款成功 **/
	SUCCESS("enum.prepayment-status.success"),
	/** 还款失败 **/
	FAIL("enum.prepayment-status.fail");
	
	private String key;

	public String getKey() {
		return key;
	}

	private PrepaymentStatus(String key) {
		this.key = key;
	}

}
