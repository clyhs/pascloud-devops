package com.pascloud.utils;

import java.io.File;
import java.io.IOException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;

public class Demo {
	
	public static void main(String[] args){
		System.out.println("test");
		Connection con = new Connection("192.168.0.7");
		
		try {
			con.connect();
			boolean isAuthed = con.authenticateWithPassword("root","tccp@2012");
			//建立SCP客户端
			if(isAuthed){
				SCPClient scpClient = con.createSCPClient();
				
				//scpClient.get("/pascloud16/test/text.txt");
				//scpClient.put(remoteFile, length, remoteTargetDirectory, mode)
				//scpClient.get("/pascloud16/test/text.txt", "d:/usr/");
				scpClient.put("d:/usr/index.html", "/pascloud16/");
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//远程服务器的用户名密码
		
		//服务器端的文件下载到本地的目录下
		//scpClient.getFile("/home/oracle/RUNNING.txt", "C:/");
		
		//将本地文件上传到服务器端的目录下
		//scp.putFile("C:/RUNNING.txt", "/home/oracle");
	}

}
