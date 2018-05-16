package com.pascloud.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.constant.Constants;

public class DBUtils {
	
	private static final Logger log = LoggerFactory.getLogger(DBUtils.class);
	
	private String driverClass;
	private String url;
	private String username;
	private String password;
	
	private DBUtils(){}
	
	public DBUtils(String driverClass, String url,String username, String password){
		this.driverClass = driverClass;
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public boolean  canConnect(){  
		Boolean flag = false;
        Connection conn = null;  
        try {  
        	Class.forName(driverClass); 
        	log.info("连接开始...");
            conn = DriverManager.getConnection(url, username, password);  
            if(null !=conn){
            	flag = true;
            	log.info("连接成功...");
            }
            log.info("连接结束...");
        } catch (SQLException | ClassNotFoundException e) {  
            System.out.println("connect failed!");  
            //e.printStackTrace();  
            log.info("连接失败...");
        } finally{
        	
        	try {
        		log.info("关闭连接...");
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
        }        
        return flag;  
    }
	
	public static String getDirverClassName(String dbType){
		switch(dbType.toLowerCase()){
		case "mysql":
			return Constants.DB_MYSQL_DIRVERCLASS;
		case "db2":
			return Constants.DB_DB2L_DIRVERCLASS;
		case "oracle":
		    return Constants.DB_ORACLE_DIRVERCLASS;
		default:
			return "";
		}
	}
	
	public static String getUrlByParams(String dbType,String ip,String database,Integer port){
		StringBuffer sb = new StringBuffer();
		
		switch(dbType.toLowerCase()){
		case "mysql":
			sb.append("jdbc:mysql://").append(ip+":"+port+"/"+database);
			break;
		case "db2":
			sb.append("jdbc:db2://").append(ip+":"+port+"/"+database);
			break;
		case "oracle":
			sb.append("jdbc:oracle:thin:@").append(ip+":"+port+":"+database);
		    break;
		default:
			break;
		}
		
		return sb.toString();
	}

}
