package com.pascloud.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionInfo;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class ScpClientUtils {
	
	private static final Logger log = LoggerFactory.getLogger(ScpClientUtils.class);
	
	
	
	private static Connection conn;
	
	private static String addr;
	
	private static String username;
	
	private static String password;
	
	private static Integer port;
	
	private ScpClientUtils(){}
	
	
	public  ScpClientUtils(String addr,String username,String password){
		this.addr = addr;
		this.username = username;
		this.password = password;
	}
	
	public  ScpClientUtils(String addr,String username,String password,Integer port){
		this.addr = addr;
		this.username = username;
		this.password = password;
		this.port = port;
	}
	
	private static Connection getConn(){
		conn = new Connection(addr);
		return conn;
	}
	
	private static Boolean isAutn(){
		Boolean flag = false;
		try {
			getConn();
			conn.connect();
			flag = conn.authenticateWithPassword(username, password);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return flag;
	}
	
	
	
	public static void copyFolder(String sourceFolder,String targetFolder){
		if(isAutn()){
			Session session = null;
			InputStream stdout = null;
			BufferedReader br = null;
			try {
				SCPClient scpClient = conn.createSCPClient();
				session = conn.openSession();
				session.execCommand("cp -r "+sourceFolder+" "+targetFolder);
				stdout = new StreamGobbler(session.getStdout());
				br = new BufferedReader(new InputStreamReader(stdout));

				while (true) {
					String line = br.readLine();
					if (line == null)
						break;
					System.out.println(line);
				}
				System.out.println("ExitCode: " + session.getExitStatus());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				log.error(e.getMessage());
			} finally{
				try {
					stdout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				session.close();
			}
		}else{
			log.info("ssh登录失败");
		}
	}
	
	public static void close(){
		conn.close();
	}
	
	
	public String catFileToString(String path){
		StringBuffer sb = new StringBuffer();
		if(isAutn()){
			Session session = null;
			InputStream stdout = null;
			BufferedReader br = null;
			try {
				SCPClient scpClient = conn.createSCPClient();
				session = conn.openSession();
				session.execCommand("cat "+path);
				stdout = new StreamGobbler(session.getStdout());
				br = new BufferedReader(new InputStreamReader(stdout));
				System.out.println(conn.getHostname());
				while (true) {
					String line = br.readLine();
					if (line == null)
						break;
					sb.append(line);
				}
				System.out.println(sb.toString());
				System.out.println("ExitCode: " + session.getExitStatus());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				log.error(e.getMessage());
			} finally{
				try {
					stdout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				session.close();
				conn.close();
				
			}
		}
		return sb.toString();
	}
	
	public static void putFileToServer(String local,String server){
		if(isAutn()){
			try {
				SCPClient scpClient = conn.createSCPClient();
				scpClient.put(local, server);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}
	

	public static void main(String[] args){
		System.out.println("host");
		String sourceFolder = "/home/webapps";
		String targetFolder = "/home/paspb_liao";
		//ScpClientUtils client = ScpClientUtils.getInstance("192.168.0.16", "root", "tccp@2012");
		//client.copyFolder(sourceFolder, targetFolder);
		//client.close();
	}
	
}
