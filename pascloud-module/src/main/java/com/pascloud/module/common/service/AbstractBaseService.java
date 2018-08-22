package com.pascloud.module.common.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pascloud.module.server.service.ServerService;
import com.spotify.docker.client.DefaultDockerClient;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionInfo;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public abstract class AbstractBaseService {
	
	@Autowired
	protected ServerService    m_serverService;
	
	protected DefaultDockerClient dockerClient;
	
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
	
	protected Connection getScpClientConn(String ip,String user,String password,Integer port){
		Connection conn = null;
		ConnectionInfo info = null;
		Boolean flag = false;
		try{
			conn = new Connection(ip,port);
			
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
				Thread.sleep(1000*2);
				flag = true;
				log.info("文件上传完毕");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				log.info("文件上传异常");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				log.info("文件上传异常");
			}
		}
		return flag;
	}
	
	public Boolean execCommand(Connection conn,String cmd) {
		Boolean flag = false;
		StringBuffer sb = new StringBuffer();
		if (null!=conn) {
			Session session = null;
			InputStream stdout = null;
			BufferedReader br = null;
			try {
				session = conn.openSession();
				log.info("执行命令" + cmd);
				session.execCommand(cmd);
				stdout = new StreamGobbler(session.getStdout());
				br = new BufferedReader(new InputStreamReader(stdout));
				String line;
				while ((line = br.readLine()) != null) {
					//System.out.println(line);
					sb.append(line);
				}
				Thread.sleep(1000*1);
				flag = true;
				log.info("执行命令完毕");
				//System.out.println(sb.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.info("执行命令异常");
				log.error(e.getMessage());
			} finally {
				session.close();
			}
		}
		return flag;
	}
	
	public DefaultDockerClient getDockerClient(){
		this.dockerClient = DefaultDockerClient.builder().uri(m_serverService.getMasterDockerUrl()).build();
		return dockerClient;
	}
	
	protected String getRandomFileName() {
		 
		SimpleDateFormat simpleDateFormat;
 
		simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
 
		Date date = new Date();
 
		String str = simpleDateFormat.format(date);
 
		Random random = new Random();
 
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
 
		return  str +rannum ;// 当前时间
	}
	
	protected String getRandomFileName(Date now) {
		 
		SimpleDateFormat simpleDateFormat;
 
		simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = simpleDateFormat.format(now);
		Random random = new Random();
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
 
		return  str +rannum ;// 当前时间
	}
	

}
