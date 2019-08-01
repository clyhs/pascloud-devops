package com.pascloud.mybatis.multi;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * 
 * @date 2019年8月1日 上午9:19:36
 *
 * @author 大鱼
 *
 */

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		
		return DataSourceHolder.getDataSource();
	}

}