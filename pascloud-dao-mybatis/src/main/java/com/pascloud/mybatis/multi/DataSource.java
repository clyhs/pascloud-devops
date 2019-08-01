package com.pascloud.mybatis.multi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * @date 2019年8月1日 上午9:32:46
 *
 * @author 大鱼
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
	String name() default DataSource.dn1;
	
	public static String dn1 = "dataSource_dn1";
}
