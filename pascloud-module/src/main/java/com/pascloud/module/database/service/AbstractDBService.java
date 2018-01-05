package com.pascloud.module.database.service;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pascloud.module.config.PasCloudConfig;

public abstract class AbstractDBService {
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	
	protected String getTableSqlByType(ComboPooledDataSource ds){
		String sql = "";
		String dcn = ds.getDriverClass();
		String dsName = ds.getDataSourceName();
		String userName = ds.getUser();
		if(dcn.equalsIgnoreCase("com.ibm.db2.jcc.DB2Driver")){
			sql = "select name as id,name from sysibm.systables where type='T' and creator='"+userName.toUpperCase()+"' ORDER BY name asc";
		}else if(dcn.equalsIgnoreCase("com.mysql.jdbc.Driver")){
			sql = "SELECT TABLE_NAME id, TABLE_NAME name  FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='"+dsName+"' ORDER BY name asc";
		}else if(dcn.equalsIgnoreCase("oracle.jdbc.driver.OracleDriver")){
			sql = "select table_name id,table_name name from user_tables order by name asc";
		}
		return sql;
	}
	
	protected String getColumnsByType(ComboPooledDataSource ds,String tablename){
		String sql = "";
		String dcn = ds.getDriverClass();
		String dsName = ds.getDataSourceName();
		String userName = ds.getUser();
		if(dcn.equalsIgnoreCase("com.ibm.db2.jcc.DB2Driver")){
			sql = "select t.colname as columnName,t.typename dataType from syscat.COLUMNS t where tabschema='"+userName.toUpperCase()+"' and tabname=upper('"+tablename+"')";
		}else if(dcn.equalsIgnoreCase("com.mysql.jdbc.Driver")){
			sql = "select column_name columnName,data_type dataType from information_schema.columns where table_schema='"+dsName+"' and table_name='"+tablename+"'";
		}else if(dcn.equalsIgnoreCase("oracle.jdbc.driver.OracleDriver")){
			sql = "select COLUMN_NAME columnName,DATA_TYPE dataType from user_tab_columns where Table_Name='"+tablename.toUpperCase()+"' order by column_name ";
		}
		return sql;
	}
	
	protected String buildPageSql(String sql,ComboPooledDataSource ds,
			Integer startRow,Integer pageSize){
		StringBuffer newSql = new StringBuffer(); 
		String dcn = ds.getDriverClass();
		
		if(dcn.equalsIgnoreCase("com.ibm.db2.jcc.DB2Driver")){
			newSql.append(" select * ");
			newSql.append(" from ( ");
			newSql.append(" 	select b.*, rownumber() over() as rn  ");
			newSql.append(" 	from ( ");
			newSql.append(		sql); 
			newSql.append(" 	) as b ");
			newSql.append(" ) as a where a.rn between "+(startRow+1)+" and " +(pageSize+startRow)+" with ur ");
		}else if(dcn.equalsIgnoreCase("com.mysql.jdbc.Driver")){
			newSql.append(sql+" limit "+startRow+","+pageSize);
		}else if(dcn.equalsIgnoreCase("oracle.jdbc.driver.OracleDriver")){
			newSql.append(" select * from ( ");
			newSql.append(" 	select r.*, rownum rn from ( ");
			newSql.append(		sql);
			newSql.append(" 	)r where rownum<"+(pageSize + startRow));
			newSql.append(" )pg where pg.rn>="+(startRow));
			
		}
		return newSql.toString(); 
	}
	

}
