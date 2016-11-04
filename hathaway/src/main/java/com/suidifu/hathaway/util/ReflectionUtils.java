/**
 * 
 */
package com.suidifu.hathaway.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.suidifu.hathaway.mq.exceptions.DuplicateMethodNameException;

/**
 * @author wukai
 *
 */
public class ReflectionUtils{
	
	public static Method findOnlyPublicAccessMethod(Class<?> targetBeanClazz, String methodName) throws DuplicateMethodNameException,NoSuchMethodException{
		
		Class<?>[] interfaces = targetBeanClazz.getInterfaces();
		
		List<Method> targetMethods = new ArrayList<Method>();
		
		for (Class<?> clazz : interfaces) {
			
			Method[] methods = clazz.getDeclaredMethods();
			
			for (Method method : methods) {
				
//				if(Modifier.isPublic(method.getModifiers()))
				
					if(StringUtils.equals(method.getName(), methodName)){
						
						targetMethods.add(method);
					}
				
			}
		
			
		}
		if(targetMethods.size() == 0){
			throw new NoSuchMethodException("not found method name  is "+methodName);
		}
		if(targetMethods.size() > 1){
			throw new DuplicateMethodNameException("duplicate method name is "+methodName);
		}
		return targetMethods.get(0);
	
	}

}
