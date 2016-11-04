/**
 * 
 */
package com.suidifu.hathaway.job;

/**
 * @author wukai
 *
 */
public enum JobType {
	
	Voucher("enum.job-type.voucher"),
	
	Other("enum.job-type.other"),
	
	;
	
	private JobType(String key) {
		this.key = key;
	}

	private String key;

	public String getKey() {
		return key;
	}
}
