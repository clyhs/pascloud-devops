package com.pascloud.module.common.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionInfo;
import ch.ethz.ssh2.SCPClient;

public abstract class AbstractBaseService {
	
	protected String defaultPort = "2375";
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	//protected SCPClient scpClient;
	
	protected Connection getScpClientConn(String ip,String user,String password){
		Connection conn = null;
		ConnectionInfo info = null;
		Boolean flag = false;
		try{
			conn = new Connection(ip);
			if(null!=conn){
				info = conn.connect();
				if(null!=info){
					log.info("连接成功");
					flag = conn.authenticateWithPassword(user, password);
				}
			}else{
				log.info("连接失败");
			}
			if(flag){
				log.info("验证成功");
				return conn;
			}else{
				log.info("验证失败");
			}
		}catch(Exception e){
			log.info("连接异常");
			log.error(e.getMessage());
		}
		return null;
	} 
	
	protected Boolean putFileToServer(Connection conn,String local, String server) {
		Boolean flag = false;
		if (null!=conn) {

			try {
				SCPClient scpClient = conn.createSCPClient();
				log.info("文件正在上传");
				scpClient.put(local, server);
				flag = true;
				log.info("文件上传完毕");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				log.info("文件上传异常");
			}
		}
		return flag;
	}

}
