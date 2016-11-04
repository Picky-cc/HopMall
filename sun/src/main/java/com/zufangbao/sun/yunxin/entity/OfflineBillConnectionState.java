package com.zufangbao.sun.yunxin.entity;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

/**
 * 线下支付单关联状态
 * 
 * @author louguanyang
 *
 */
public enum OfflineBillConnectionState {
	/** 未关联 */
	NONE("enum.offline-bill-connection-state.none"),
	/** 部分关联 */
	PART("enum.offline-bill-connection-state.part"),
	/** 已关联 */
	ALL("enum.offline-bill-connection-state.all");

	private String key;

	private OfflineBillConnectionState(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public int getOrdinal() {
		return this.ordinal();
	}
	
	public String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}

}
