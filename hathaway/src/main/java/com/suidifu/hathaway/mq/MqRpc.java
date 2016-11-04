package com.suidifu.hathaway.mq;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MqRpc {
	
	String beanName() default "";
	
	String methodName() default "";
	
	boolean sync() default true;
}
