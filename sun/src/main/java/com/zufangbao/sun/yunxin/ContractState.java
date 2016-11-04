package com.zufangbao.sun.yunxin;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

/**
 * 合同状态: 0:放款中,1:未生效,2:已生效，3:异常中止
 * @author louguanyang
 *
 */
public enum ContractState {
	/** 放款中 **/
	PRE_PROCESS("enum.contract-enum.pre-process"),
	/** 未生效 **/
	INEFFECTIVE("enum.contract-enum.ineffective"),
	/** 已生效 **/
	EFFECTIVE("enum.contract-enum.effective"),
	/** 异常中止 **/
	INVALIDATE("enum.contract-enum.invalidate");
	
	private String key;

	private ContractState(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
	public String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
	
	public int getOrdinal(){
		return this.ordinal();
	}
}