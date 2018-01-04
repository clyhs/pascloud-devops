package com.pascloud.module.database.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pascloud.utils.db.DataSourceUtils;
import com.pascloud.vo.database.DBColumnVo;
import com.pascloud.vo.database.DBTableVo;

@Service
public class DataBaseService extends AbstractDBService{
	
	public List<DBTableVo> getTables(String dsId){
		
		List<DBTableVo> tables = new ArrayList<>();
		ComboPooledDataSource dataSource = null;
		Connection conn = null;
		try {
			log.info("查询所有表");
			dataSource = DataSourceUtils.getDataSource(dsId);
			log.info(dataSource.getJdbcUrl());
			conn = dataSource.getConnection();
			String dsName = dataSource.getDataSourceName();
			Gson g = new Gson();
			//System.out.println(g.toJson(conn));
			QueryRunner qRunner = new QueryRunner();  
			String sql = "SELECT TABLE_NAME id, TABLE_NAME name  FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='"+dsName+"'";
					
			tables = (List<DBTableVo>) qRunner.query(conn,sql, new BeanListHandler(DBTableVo.class));
			
			System.out.println(g.toJson(tables));
			log.info("查询所有表完成");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tables;
	}
	
	public List<DBColumnVo> getColumns(String tableName,String dsId){
		List<DBColumnVo> columns = new ArrayList<>();
		ComboPooledDataSource dataSource = null;
		Connection conn = null;
		try {
			log.info("查询所有字段");
			dataSource = DataSourceUtils.getDataSource(dsId);
			conn = dataSource.getConnection();
			QueryRunner qRunner = new QueryRunner();  
			String dsName = dataSource.getDataSourceName();
			String sql = "select column_name columnName,data_type dataType from information_schema.columns where table_schema='"+dsName+"' and table_name='"+tableName+"'";
					
			columns = (List<DBColumnVo>) qRunner.query(conn,sql, new BeanListHandler(DBColumnVo.class));
			Gson g = new Gson();
			System.out.println(g.toJson(columns));
			log.info("查询所有字段完成");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return columns;
	}
	
	public List<Map<String, Object>> getDataList(String tableName,String dsId,
			Integer startRow,Integer pageSize){
		List<Map<String, Object>> result = new ArrayList<>();
		ComboPooledDataSource dataSource = null;
		Connection conn = null;
		String sql = "select * from "+tableName+" limit "+startRow+","+pageSize;
		try {
			log.info("查询所有数据");
			dataSource = DataSourceUtils.getDataSource(dsId);
			conn = dataSource.getConnection();
			QueryRunner qRunner = new QueryRunner();  
			result =  qRunner.query(conn,sql, new MapListHandler());
			Gson g = new Gson();
			System.out.println(g.toJson(result));
			log.info("查询所有数据完成");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public Integer getDataCounts(String tableName,String dsId){
		
		ComboPooledDataSource dataSource = null;
		Connection conn = null;
		Integer total = -1;
		String sql = "select count(1)  from "+tableName;
		try {
			log.info("查询所有数据");
			dataSource = DataSourceUtils.getDataSource(dsId);
			conn = dataSource.getConnection();
			QueryRunner qRunner = new QueryRunner();  
			total =  (int)(long) qRunner.query(conn,sql, new ScalarHandler());
			Gson g = new Gson();
			System.out.println(g.toJson(total));
			log.info("查询所有数据完成");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return total;
	}
	
	
	public List<Map<String, Object>> getDataListBySql(String dsId,
			Integer startRow,Integer pageSize,String sql){
		List<Map<String, Object>> result = new ArrayList<>();
		ComboPooledDataSource dataSource = null;
		Connection conn = null;
		//String sql = "select * from "+tableName+" limit "+startRow+","+pageSize;
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" select a.* from ( ")
		  .append(sql)
		  .append(" ) a ") 
		  .append(" limit "+startRow+","+pageSize);
		
		try {
			log.info("查询所有数据"+sb.toString());
			dataSource = DataSourceUtils.getDataSource(dsId);
			conn = dataSource.getConnection();
			QueryRunner qRunner = new QueryRunner();  
			result =  qRunner.query(conn,sb.toString(), new MapListHandler());
			Gson g = new Gson();
			System.out.println(g.toJson(result));
			log.info("查询所有数据完成");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
public Integer getDataCountsBySql(String dsId,String sql){
		
		ComboPooledDataSource dataSource = null;
		Connection conn = null;
		Integer total = -1;
		//String sql = "select count(1)  from "+tableName;
		StringBuffer sb = new StringBuffer();
		sb.append("select count(1)  from ( ")
		  .append(sql)
		  .append(" ) a ");
		try {
			log.info("查询所有数据"+sb.toString());
			dataSource = DataSourceUtils.getDataSource(dsId);
			conn = dataSource.getConnection();
			QueryRunner qRunner = new QueryRunner();  
			total =  (int)(long) qRunner.query(conn,sb.toString(), new ScalarHandler());
			Gson g = new Gson();
			System.out.println(g.toJson(total));
			log.info("查询所有数据完成");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return total;
	}
	

}
