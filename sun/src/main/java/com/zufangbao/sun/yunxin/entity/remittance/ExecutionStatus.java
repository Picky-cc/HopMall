package com.zufangbao.sun.yunxin.entity.remittance;

import com.zufangbao.sun.utils.ApplicationMessageUtils;


/**
 * 执行状态
 * 
 * @author zhanghongbing
 *
 */
public enum ExecutionStatus {

	/** 已创建 */
	CREATE("enum.remittance-execution-status.create"),
	
	/** 处理中 */
	PROCESSING("enum.remittance-execution-status.processing"),

	/** 成功 */
	SUCCESS("enum.remittance-execution-status.success"),

	/** 失败 */
	FAIL("enum.remittance-execution-status.fail"),
	
	/** 异常 */
	ABNORMAL("enum.remittance-execution-status.abnormal"),
	
	/** 撤销 */
	ABANDON("enum.remittance-execution-status.abandon");

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private ExecutionStatus(String key) {
		this.key = key;
	}
	
	public String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.key);
	}

}