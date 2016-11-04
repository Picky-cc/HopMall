/**
 * 
 */
package com.suidifu.hathaway.mq;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

/**
 * @author wukai
 *
 */
@Component
@Aspect
public class RpcSqlAspect {

	@Around("@annotation (com.suidifu.hathaway.mq.RpcSql)")
	public Object valid(ProceedingJoinPoint joinPoint) throws Throwable {

			Object target = joinPoint.getTarget();
			
			RpcSql mqSql = findAnnotationBy(target.getClass().getMethods(),RpcSql.class);
			
			Resource sqlScript = new ClassPathResource(mqSql.value());
			
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
			
			populator.setSqlScriptEncoding("UTF-8");
			populator.addScript(sqlScript);
			
			String url = "jdbc:mysql://127.0.0.1:3306/galaxy_develop";
			
			String name = "galaxy_develop";
			
			String password = "galaxy_develop";
			
			DatabasePopulatorUtils.execute(populator, new DriverManagerDataSource(url, name, password));
			
			return null;
	}

	private RpcSql findAnnotationBy(Method[] methods, Class<RpcSql> clazz) {
		
		for (Method method : methods) {
			
			if( null != method.getAnnotation(clazz)){
				
				return  method.getAnnotation(clazz);
			}
		}
		
		return null;
	}
	
	
}
