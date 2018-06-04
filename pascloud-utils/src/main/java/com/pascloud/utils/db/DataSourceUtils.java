package com.pascloud.utils.db;

import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceUtils {
	
	private static  ConcurrentHashMap<String,DataSource> dataSourceMap = new ConcurrentHashMap<>();

	DataSourceUtils(){}
	
	public static void addDataSource(String id,DataSource dataSource){
		if(dataSourceMap.get(id) == null && null != dataSource){
			dataSourceMap.put(id, dataSource);
		}
	}
	
	public static DataSource getDataSource(String id){
		return dataSourceMap.get(id);
	}
	
	public static ConcurrentHashMap getDataSources(){
		return dataSourceMap;
	}
}
