package com.suidifu.hathaway.mq;

import java.lang.reflect.Type;

import com.suidifu.hathaway.util.KryoUtils;

public class MqRequestKryo extends MqRequest{
	
	protected String lazyArugmentListStr = new String();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -10051708245496293L;

	public MqRequestKryo() {
		super("","", "", true);
	}
	@Override
	public void pushAllArguments(Object[] args) {
		this.lazyArugmentListStr = KryoUtils.writeClassAndObject(args);
		
	}
	@Override
	public Object[] extractArguments(Type[] types) {
		
		Object obj = KryoUtils.readClassAndObject(lazyArugmentListStr, types);
	
        return (Object[]) obj;
	}

	@Override
	public void pushArument(Object value) {
		pushAllArguments(new Object[]{value});
	}
	@Override
	public Object deserializeFromString(String serializedString) {
		
		MqRequest MqRequest= (MqRequest) KryoUtils.readClassAndObject(serializedString,MqRequestKryo.class);
	    
		if(MqRequest==null) return null;
        
		return MqRequest;
	}
	@Override
	public String serializeToStrig() {
		return KryoUtils.writeClassAndObject(this);
	}
}
