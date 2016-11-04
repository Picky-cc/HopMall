package com.suidifu.hathaway.job.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodStage {
	
	String beanName() default "";
	
	String methodName() default "";
	
	int ordinal() default 0;
}
