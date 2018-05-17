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
		log.info(sql);
		return sql;
	}
	
	protected String getColumnsByType(ComboPooledDataSource ds,String tablename){
		String sql = "";
		String dcn = ds.getDriverClass();
		String dsName = ds.getDataSourceName();
		String userName = ds.getUser();
		if(dcn.equalsIgnoreCase("com.ibm.db2.jcc.DB2Driver")){
			sql = "select t.colname as columnName,t.typename dataType,"
					+ "case when NULLS='Y' THEN 'YES' ELSE 'NO' END AS isNullable,"
					+ "CONCAT(TYPENAME,CONCAT(CONCAT('(',LENGTH),')')) AS columnType,"
					+ "REMARKS AS columnComment,"
					+ "case when IDENTITY='Y' then 'PRI' else '' end as columnKey "
					+ "from syscat.COLUMNS t where tabschema='"+userName.toUpperCase()+"' and tabname=upper('"+tablename+"')";
		}else if(dcn.equalsIgnoreCase("com.mysql.jdbc.Driver")){
			sql = "select column_name columnName,data_type dataType,DATA_TYPE dataType ,"
					+ "IS_NULLABLE isNullable,COLUMN_TYPE columnType,"
					+ "COLUMN_COMMENT columnComment, "
					+ "COLUMN_KEY columnKey "
					+ "from information_schema.columns "
					+ "where table_schema='"+dsName+"' and table_name='"+tablename+"'";
		}else if(dcn.equalsIgnoreCase("oracle.jdbc.driver.OracleDriver")){
			sql = "select col.column_name columnName ,"
					+ "col.DATA_TYPE dataType, "
					+ "case when NULLABLE='Y' THEN 'YES' ELSE 'NO' END AS isNullable, "
					+ "CONCAT(col.DATA_TYPE,CONCAT(CONCAT('(',col.DATA_LENGTH),')')) AS columnType, "
					+ "col.DATA_PRECISION AS columnComment,"
					+ "case uc.constraint_type when 'P' then 'YES' else 'NO' end columnKey "
					+ "from user_tab_columns col left join user_cons_columns ucc "
					+ "on ucc.table_name=col.table_name and ucc.column_name=col.column_name "
					+ "left join user_constraints uc on uc.constraint_name = ucc.constraint_name "
					+ "where col.table_name = '"+tablename.toUpperCase()+"' ";
		}
		log.info(sql);
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
