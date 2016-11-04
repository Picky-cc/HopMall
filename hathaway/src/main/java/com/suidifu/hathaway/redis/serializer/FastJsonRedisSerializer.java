package com.suidifu.hathaway.redis.serializer;

import java.lang.reflect.Type;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author wukai
 *
 * @param <T>
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

	static final byte[] EMPTY_ARRAY = new byte[0];
	
	private final Type javaType;
	
	public FastJsonRedisSerializer(Class<T> javaType){
		this.javaType = javaType;
	}
	@Override
	public byte[] serialize(T t) throws SerializationException {
		
		if(null == t){
			return EMPTY_ARRAY;
		}
		
		return JSON.toJSONBytes(t);
	}
	private boolean isEmpty(byte[] data) {
		return (data == null || data.length == 0);
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		
		if(isEmpty(bytes)){
			return null;
		}
		return (T) JSON.parseObject(bytes,javaType);
	}

}
