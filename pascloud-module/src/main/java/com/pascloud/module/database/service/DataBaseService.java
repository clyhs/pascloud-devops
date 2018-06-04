package com.pascloud.module.database.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

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
import com.pascloud.vo.result.ResultPageVo;

@Service
public class DataBaseService extends AbstractDBService{
	
	public List<DBTableVo> getTables(String dsId){
		
		List<DBTableVo> tables = new ArrayList<>();
		ComboPooledDataSource dataSource = null;
		Connection conn = null;
		try {
			log.info("查询数据库所有表--开始--");
			dataSource = (ComboPooledDataSource) DataSourceUtils.getDataSource(dsId);
			conn = dataSource.getConnection();
			String dsName = dataSource.getDataSourceName();
			Gson g = new Gson();
			//System.out.println(g.toJson(conn));
			QueryRunner qRunner = new QueryRunner();  
			
			//select name from sysibm.systables where type='T' and creator='DB2ADMIN' 
			
			//String sql = "SELECT TABLE_NAME id, TABLE_NAME name  FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='"+dsName+"'";
			String sql = getTableSqlByType(dataSource);
			tables = (List<DBTableVo>) qRunner.query(conn,sql, new BeanListHandler(DBTableVo.class));
			log.info("查询数据库所有表--完成--");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("查询数据库所有表--失败--");
			//e.printStackTrace();
			log.error(e.getMessage());
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
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
			dataSource = (ComboPooledDataSource) DataSourceUtils.getDataSource(dsId);
			conn = dataSource.getConnection();
			QueryRunner qRunner = new QueryRunner();  
			String dsName = dataSource.getDataSourceName();
			//select t.* from syscat.COLUMNS t where tabschema='PAS' and tabname=upper('csb_dmms')
			//String sql = "select column_name columnName,data_type dataType from information_schema.columns where table_schema='"+dsName+"' and table_name='"+tableName+"'";
			String sql = getColumnsByType(dataSource,tableName);		
			columns = (List<DBColumnVo>) qRunner.query(conn,sql, new BeanListHandler(DBColumnVo.class));
			//Gson g = new Gson();
			//System.out.println(g.toJson(columns));
			log.info("查询所有字段完成");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			//e.printStackTrace();
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
		//String sql = "select * from "+tableName+" limit "+startRow+","+pageSize;
		String sql = "select * from "+tableName;
		
		try {
			log.info("查询表所有数据--开始--");
			
			dataSource = (ComboPooledDataSource) DataSourceUtils.getDataSource(dsId);
			conn = dataSource.getConnection();
			QueryRunner qRunner = new QueryRunner();  
			sql = buildPageSql(sql,dataSource,startRow,pageSize);
			result =  qRunner.query(conn,sql, new MapListHandler());
			//Gson g = new Gson();
			//System.out.println(g.toJson(result));
			log.info("查询表所有数据--完成--");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("查询表所有数据--失败--");
			log.error(e.getMessage());
			//e.printStackTrace();
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
	
	public List<Map<String, Object>> getDataList(DataSource dataSource,String sql){
		List<Map<String, Object>> result = new ArrayList<>();
		
		Connection conn = null;
		//String sql = "select * from "+tableName+" limit "+startRow+","+pageSize;
		try {
			conn = dataSource.getConnection();
			QueryRunner qRunner = new QueryRunner();  
			result =  qRunner.query(conn,sql, new MapListHandler());
			Gson g = new Gson();
			System.out.println(g.toJson(result));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("查询表所有数据--失败--");
			log.error(e.getMessage());
			//e.printStackTrace();
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
			log.info("统计表总条数--开始--");
			dataSource = (ComboPooledDataSource) DataSourceUtils.getDataSource(dsId);
			conn = dataSource.getConnection();
			QueryRunner qRunner = new QueryRunner();  
			Number num =  (Number)qRunner.query(conn,sql, new ScalarHandler());
			//Gson g = new Gson();
			total = num.intValue();
			//System.out.println(g.toJson(total));
			log.info("统计表总条数--完成--");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("统计表总条数--失败--");
			log.error(e.getMessage());
			//e.printStackTrace();
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
	
    public Integer getDataCountsByConn(Connection conn,String tableName ,Boolean...flag){
		
		Integer total = -1;
		String sql = "select count(1)  from "+tableName;
		try {
			//log.info("统计表总条数--开始--");
			QueryRunner qRunner = new QueryRunner();  
			Number num =  (Number)qRunner.query(conn,sql, new ScalarHandler());
			//Gson g = new Gson();
			total = num.intValue();
			//System.out.println(g.toJson(total));
			//log.info("统计表总条数--完成--");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//log.info("统计表总条数--失败--");
			log.error(e.getMessage());
			//e.printStackTrace();
		}finally{
			if(flag == null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
		return total;
	}
    
    public String getSimleColumnValueByConn(Connection conn,String sql,String columnName){
		
		String value = "";
		
		try {
			//log.info("统计表总条数--开始--");
			QueryRunner qRunner = new QueryRunner();  
			Object v =  (String)qRunner.query(conn,sql, new ScalarHandler());
			//Gson g = new Gson();
			value = v.toString();
			//System.out.println(g.toJson(total));
			//log.info("统计表总条数--完成--");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//log.info("统计表总条数--失败--");
			log.error(e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return value;
	}
	
	
	public ResultPageVo getDataListBySql(String dsId,
			Integer startRow,Integer pageSize,String sql){
		
		ResultPageVo pageData = null;
		String desc = "";
		List<Map<String, Object>> result = new ArrayList<>();
		ComboPooledDataSource dataSource = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			pageData = new ResultPageVo(10000,"成功");
			log.info("根据SQL查询数据--开始---");
			dataSource = (ComboPooledDataSource) DataSourceUtils.getDataSource(dsId);
			sb.append(buildPageSql(sql,dataSource,startRow,pageSize));
			conn = dataSource.getConnection();
			QueryRunner qRunner = new QueryRunner();  
			long beginTime = System.currentTimeMillis();  
			log.info(sb.toString());
			result =  qRunner.query(conn,sb.toString().toUpperCase(), new MapListHandler());
			long endTime = System.currentTimeMillis(); 
			double longTime =(double)(endTime - beginTime)/1000;
			Gson g = new Gson();
			desc = execCallbackSuccess(sql,0,longTime,"无。");
			pageData.setRows(result);
			pageData.setDesc(desc);
			log.info("根据SQL查询数据--完成---");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("根据SQL查询数据--失败---");
			log.error(e.getMessage());
			pageData = new ResultPageVo(20000,"失败");
			desc = execCallbackError(e.getMessage());
			pageData.setDesc(desc);
			//e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pageData;
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
			log.info("根据SQL查询总条数--开始--");
			dataSource = (ComboPooledDataSource) DataSourceUtils.getDataSource(dsId);
			conn = dataSource.getConnection();
			QueryRunner qRunner = new QueryRunner();  
			Number num =  (Number)qRunner.query(conn,sb.toString(), new ScalarHandler());
			total = num.intValue();
			Gson g = new Gson();
			//System.out.println(g.toJson(total));
			log.info("根据SQL查询总条数--完成--");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("根据SQL查询总条数--失败--");
			log.error(e.getMessage());
			//e.printStackTrace();
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
    
    public List<String> getSqlColumnName(String dsId,String sql){
    	List<String> columnNames = new ArrayList<>();
    	ComboPooledDataSource dataSource = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			log.info("根据SQL查询表头--开始--");
			dataSource = (ComboPooledDataSource) DataSourceUtils.getDataSource(dsId);
			conn = dataSource.getConnection();
			//stmt = conn.prepareStatement(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData data = rs.getMetaData();
			log.info(data.getColumnCount()+"");
			for (int i = 1; i <= data.getColumnCount(); i++) {
				String columnName = data.getColumnName(i);
				columnNames.add(columnName);
			}
			log.info("根据SQL查询表头--完成--");
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("根据SQL查询表头--失败--");
			log.error(e.getMessage());
			//e.printStackTrace();
		}finally{
			try {
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				stmt.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
    	return columnNames;
    }
    
    public String execBySql(String dsId,String sql){
    	String result = "";
    	Integer num = 0;
    	ComboPooledDataSource dataSource = null;
		Connection conn = null;
		
		try {
			log.info("执行非查询SQL语句--开始--");
			dataSource = (ComboPooledDataSource) DataSourceUtils.getDataSource(dsId);
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			QueryRunner qRunner = new QueryRunner();  
			long beginTime = System.currentTimeMillis(); 
			num = qRunner.update(conn, sql);
			conn.commit();
			long endTime = System.currentTimeMillis(); 
			double longTime =(double)(endTime - beginTime)/1000;
			result = execCallbackSuccess(sql,num,longTime,"无。");
			log.info("执行非查询SQL语句--完成--");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			try {
				result = execCallbackError(e.getMessage());
				log.info("执行非查询SQL语句--失败--");
				conn.rollback();
				log.info("执行非查询SQL语句--回滚成功--");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				log.info("执行非查询SQL语句--回滚失败--");
			}
			//e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
    	
		return result;
    }
    
    
    private String execCallbackSuccess(String sql,Integer num,double ms,String msg){
    	StringBuffer sb = new StringBuffer();
    	sb.append("【SQL】 "+sql +"\r\n")
    	  .append(" 影响 "+num+" 行，" +"\r\n")
    	  .append(" 运行时长 "+ms+ "秒。" +"\r\n");
    	//sb.append("【其它】 "+msg);
    	return sb.toString();
    }
    
    private String execCallbackError(String msg){
    	StringBuffer sb = new StringBuffer();
    	
    	sb.append("【异常】 "+msg);
    	return sb.toString();
    }
	

}
