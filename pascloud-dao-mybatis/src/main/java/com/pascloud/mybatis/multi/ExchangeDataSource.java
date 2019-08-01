package com.pascloud.mybatis.multi;

import java.lang.reflect.Method;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 
 * @date 2019年8月1日 上午9:36:23
 *
 * @author 大鱼
 *
 */

@Component
public class ExchangeDataSource implements MethodBeforeAdvice,
		AfterReturningAdvice {
	private static Logger log = LoggerFactory.getLogger(ExchangeDataSource.class);

	public void afterReturning(Object returnValue, Method method, Object[] args,
			Object target) throws Throwable {
		// TODO Auto-generated method stub
		DataSourceHolder.clearDataSource();

	}

	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		// TODO Auto-generated method stub
		if (method.isAnnotationPresent(DataSource.class))   
        {  
			
            DataSource datasource = method.getAnnotation(DataSource.class);
            
            if(null == datasource || (null != datasource && null == datasource.name()))
            	
            	DataSourceHolder.setDataSource(datasource.dn1);
    		else {
    			log.info(datasource.name());
    			
    			DataSourceHolder.setDataSource(datasource.name());  
    		}
            
            log.info("now datasource:"+DataSourceHolder.getDataSource());
        }


	}

}