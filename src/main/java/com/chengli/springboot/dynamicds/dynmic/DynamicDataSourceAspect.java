package com.chengli.springboot.dynamicds.dynmic;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-1) //spring order排序后执行顺序是从小到大，目的是确保在事务管理器执行前先执行
@Component
public class DynamicDataSourceAspect {

	@Before("@annotation(useDataSource)")//拦截注解 UseDataSource
	public void setDataSourceType(JoinPoint point, UseDataSource useDataSource) throws Throwable {
		DynmicDataSourceContextHolder.setDataSourceKey(useDataSource.value());
	}

}