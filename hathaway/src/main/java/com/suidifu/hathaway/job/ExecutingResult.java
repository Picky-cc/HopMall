/**
 * 
 */
package com.suidifu.hathaway.job;

/**
 * @author wukai
 *
 */
public enum ExecutingResult {
	
	SUC("enum.executing-reusulting.suc"),
	
	FAIL("enum.executing-reusulting.fail"),
	
	;
	
	private ExecutingResult(String key) {
		this.key = key;
	}

	private String key;

	public String getKey() {
		return key;
	}
	public static ExecutingResult fromValue(boolean suc) {
		return suc ? ExecutingResult.SUC : ExecutingResult.FAIL;
	}
}
