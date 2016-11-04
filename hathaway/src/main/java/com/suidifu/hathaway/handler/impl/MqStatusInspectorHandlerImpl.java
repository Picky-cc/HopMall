/**
 * 
 */
package com.suidifu.hathaway.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.suidifu.hathaway.CacheKey;
import com.suidifu.hathaway.handler.MqStatusInspectorHandler;

/**
 * @author wukai
 *
 */
@Component("mqStatusInspectorHandler")
public class MqStatusInspectorHandlerImpl implements MqStatusInspectorHandler {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public boolean needRetrySendRequest(String key) {
		return !stringRedisTemplate.hasKey(key);
	}
	@Override
	public void setStatus(CacheKey cacheKey) {
		stringRedisTemplate.boundListOps(cacheKey.getKey()).leftPush(cacheKey.getValue());
		stringRedisTemplate.expire(cacheKey.getKey(), cacheKey.getTimeout(), cacheKey.getTimeUnit());
	}

}
