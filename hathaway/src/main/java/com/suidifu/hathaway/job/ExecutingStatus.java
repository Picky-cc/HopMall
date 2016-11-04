/**
 * 
 */
package com.suidifu.hathaway.job;

/**
 * @author wukai
 *
 */
public enum ExecutingStatus {
	
	CREATE("enum.executing-status.create"),
	
	PROCESSING("enum.executing-status.processing"),
	
	DONE("enum.executing-status.done"),
	
	ABANDON("enum.executing-status.abandon"),
	
	;
	
	private ExecutingStatus(String key) {
		this.key = key;
	}

	private String key;

	public String getKey() {
		return key;
	}
}
