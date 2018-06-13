package com.pascloud.module.database.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pascloud.module.common.service.AbstractBaseService;
import com.pascloud.utils.DBUtils;

import com.pascloud.vo.pass.MysqlVo;

@Service
public class MysqlService extends AbstractBaseService {
	
	public Boolean createDatabaseAndImport(String database,MysqlVo vo,String sqlPath,String funPath){
		log.info("创建数据库"+database);
		Boolean flag = false;
		ComboPooledDataSource dataSource = null;
		Connection conn = null;
		try{
			String sql_create = "CREATE DATABASE IF NOT EXISTS pascloud default charset utf8 COLLATE utf8_general_ci;";
			String sql_show="show databases like 'pascloud';";
			if(null!=vo){
				dataSource = new ComboPooledDataSource();
				log.info(vo.getUsername());
				dataSource.setUser(vo.getUsername().trim());
				dataSource.setDataSourceName(database);
				dataSource.setPassword(vo.getPassword().trim());
				String url = DBUtils.getUrlByParams("mysql", vo.getIp(), database, Integer.valueOf(vo.getPort()));
				log.info(url);
				dataSource.setJdbcUrl(url);
				dataSource.setDriverClass(DBUtils.getDirverClassName("mysql"));
				dataSource.setInitialPoolSize(1);
				dataSource.setMinPoolSize(1);
				dataSource.setMaxPoolSize(5);
				dataSource.setMaxStatements(0);
				dataSource.setMaxIdleTime(5);
				conn = dataSource.getConnection();
				//ResultSetHandler<List<Map<String,Object>>> re = new BeanListHandler<String>(String.class);
				if(null!=conn){
					QueryRunner qRunner = new QueryRunner(); 
					List<Map<String,Object>> list = qRunner.query(conn, sql_show,new MapListHandler());
					if(list.size()==0){
						log.info("pascloud数据库不存在，正在创建");
						Integer t = qRunner.update(conn, sql_create);
						log.info("t="+t);
						log.info("pascloud数据库创建完成");
						flag = impData("pascloud",vo,sqlPath);
						if(flag){
							flag = impFunctions("pascloud",vo,funPath);
						}
					}else{
						log.info("pascloud数据库已经存在");
					}
				}
			}
			
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	public Boolean createDatabase(String database,MysqlVo vo){
		log.info("创建数据库"+database);
		Boolean flag = false;
		ComboPooledDataSource dataSource = null;
		Connection conn = null;
		try{
			String sql_create = "CREATE DATABASE IF NOT EXISTS pascloud default charset utf8 COLLATE utf8_general_ci;";
			String sql_show="show databases like 'pascloud';";
			if(null!=vo){
				dataSource = new ComboPooledDataSource();
				log.info(vo.getUsername());
				dataSource.setUser(vo.getUsername().trim());
				dataSource.setDataSourceName(database);
				dataSource.setPassword(vo.getPassword().trim());
				String url = DBUtils.getUrlByParams("mysql", vo.getIp(), database, Integer.valueOf(vo.getPort()));
				log.info(url);
				dataSource.setJdbcUrl(url);
				dataSource.setDriverClass(DBUtils.getDirverClassName("mysql"));
				dataSource.setInitialPoolSize(1);
				dataSource.setMinPoolSize(1);
				dataSource.setMaxPoolSize(5);
				dataSource.setMaxStatements(0);
				dataSource.setMaxIdleTime(5);
				conn = dataSource.getConnection();
				//ResultSetHandler<List<Map<String,Object>>> re = new BeanListHandler<String>(String.class);
				if(null!=conn){
					QueryRunner qRunner = new QueryRunner(); 
					List<Map<String,Object>> list = qRunner.query(conn, sql_show,new MapListHandler());
					if(list.size()==0){
						log.info("pascloud数据库不存在，正在创建");
						Integer t = qRunner.update(conn, sql_create);
						log.info("t="+t);
						log.info("pascloud数据库创建完成");
					}else{
						log.info("pascloud数据库已经存在");
					}
				}
			}
			
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	public Boolean impData(String database,MysqlVo vo,String sqlPath){
		Boolean flag = false;
		log.info("导入pascloud的基础数据");
		ComboPooledDataSource dataSource = null;
		Connection conn = null;
		try{
			//String sql_imp="source "+sqlPath;
			if(null!=vo){
				String driver =DBUtils.getDirverClassName("mysql");
				String url = DBUtils.getUrlByParams("mysql", vo.getIp(), database, Integer.valueOf(vo.getPort()));
				url=url+"?useUnicode=true&characterEncoding=utf8";
				Class.forName(driver);  
		        conn = DriverManager.getConnection(url, vo.getUsername(), vo.getPassword()); 
		        ScriptRunner runner = new ScriptRunner(conn);  
	            //下面配置不要随意更改，否则会出现各种问题  
	            runner.setAutoCommit(true);//自动提交  
	            runner.setFullLineDelimiter(false);  
	            //runner.setDelimiter(";");////每条命令间的分隔符  
	            runner.setSendFullScript(false);  
	            runner.setStopOnError(false);  
	            runner.setLogWriter(null);
	            runner.runScript(new InputStreamReader(new FileInputStream(sqlPath),"UTF-8")); 
	            
	            log.info("导入pascloud的基础数据完成");
	            flag = true;
			}
			
		}catch(Exception e){
			//e.printStackTrace();
			log.error(e.getMessage());
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	public Boolean impFunctions(String database,MysqlVo vo,String sqlPath){
		Boolean flag = false;
		log.info("导入pascloud的函数");
		ComboPooledDataSource dataSource = null;
		Connection conn = null;
		try{
			//String sql_imp="source "+sqlPath;
			if(null!=vo){
				String driver =DBUtils.getDirverClassName("mysql");
				String url = DBUtils.getUrlByParams("mysql", vo.getIp(), database, Integer.valueOf(vo.getPort()));
				Class.forName(driver);  
		        conn = DriverManager.getConnection(url, vo.getUsername(), vo.getPassword()); 
		        com.pascloud.utils.db.ScriptRunner runner = new com.pascloud.utils.db.ScriptRunner(conn,false,false); 
		        runner.runScript(new BufferedReader(new FileReader(sqlPath)));
		        log.info("导入pascloud的函数完成");
		        flag = true;
			}
			
		}catch(Exception e){
			//e.printStackTrace();
			log.error(e.getMessage());
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return flag;
	}


	
	public static void main(String[] args){
		MysqlService s = new MysqlService();
		MysqlVo vo = new MysqlVo();
		vo.setIp("192.168.0.16");
		vo.setPassword("root");
		vo.setUsername("root");
		vo.setPort(3306);
		//s.createDatabase("mysql", vo);
		s.impData("pascloud", vo, "d:/pascloud.sql");
		//s.impFunctions("pascloud", vo, "d:/functions.sql");
	}
}
