package com.suidifu.hathaway;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @author wukai
 *
 */
public class CacheKey {
	
	private String key;	
	
	private String value;
	
	private long timeout;
	
	private TimeUnit timeUnit;
	
	public CacheKey(String key,String value,long timeout,TimeUnit timeUnit) {
		this.value = value;
		this.key = key;
		this.timeout = timeout;
		this.timeUnit = timeUnit;
	}

	public String getKey() {
		return key+"_"+value;
	}
	public String getRawKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}
}
