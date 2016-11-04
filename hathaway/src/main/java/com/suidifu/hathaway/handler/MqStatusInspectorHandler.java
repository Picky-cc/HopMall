/**
 * 
 */
package com.suidifu.hathaway.handler;

import com.suidifu.hathaway.CacheKey;

/**
 * @author wukai
 *
 */
public interface MqStatusInspectorHandler {
	
	public void setStatus(CacheKey cacheKey);

	public boolean needRetrySendRequest(String key);
}
