/**
 * 
 */
package com.pascloud.mybatis.multi;

import java.lang.reflect.Parameter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @date 2019年8月1日 下午12:45:07
 *
 * @author 大鱼
 *
 */
//@Aspect
//@Order(-1)
//@Component
public class AopDataSourcePequestParam {
	
	private static Logger log = LoggerFactory.getLogger(AopDataSourcePequestParam.class);

	@Around(value = " execution(* com.pascloud.dao.**.*Dao.*(..))")
	public Object invoke(ProceedingJoinPoint   joinPoint) throws Throwable {
		//获取参数对象
        Object[] args = joinPoint.getArgs();
        //获取方法参数
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        
        Parameter[] parameters = signature.getMethod().getParameters();
        for (int i = 0; i < parameters.length; i++) {
        	Parameter parameter = parameters[i];
        	//Java自带基本类型的参数（例如Integer、String）的处理方式
            if (isPrimite(parameter.getType())) {
            	if(parameter.isAnnotationPresent(DataSourceParam.class)){
            		log.info(args[i].toString());
            		DataSourceHolder.setDataSource(args[i].toString()); 
            	}
            	
                
            }
        }
        Object obj = joinPoint.proceed();
        return obj;
		
	}
	
	/**
     * 判断是否为基本类型：包括String
     * @param clazz clazz
     * @return  true：是;     false：不是
     */
    private boolean isPrimite(Class<?> clazz){
        return clazz.isPrimitive() || clazz == String.class;
    }
}
