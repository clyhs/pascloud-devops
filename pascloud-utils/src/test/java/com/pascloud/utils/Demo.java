package com.pascloud.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class Demo {
	
	

	public static void main(String[] args) {
		System.out.println("test");
		Connection con = new Connection("192.168.0.16");

		try {
			con.connect();
			boolean isAuthed = con.authenticateWithPassword("root", "tccp@2012");
			// 建立SCP客户端
			if (isAuthed) {
				SCPClient scpClient = con.createSCPClient();

				// scpClient.get("/pascloud16/test/text.txt");
				// scpClient.put(remoteFile, length, remoteTargetDirectory,
				// mode)
				// scpClient.get("/pascloud16/test/text.txt", "d:/usr/");
				// scpClient.put("d:/usr/index.html", "/pascloud16/");
				Session session = con.openSession();
				//session.execCommand("uname -a && date && uptime && who");
				//session.execCommand("cp -r /home/webapps /home/paspb_jn");
				//session.execCommand("cat /home/webapps/pas_db2/WEB-INF/classes/applicationContext_resources.xml");
				//System.out.println("Here is some information about the remote host:");
		        //InputStream stdout = new StreamGobbler(session.getStdout());
				/*
				BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

				while (true) {
					String line = br.readLine();
					if (line == null)
						break;
					System.out.println(line);
				}*/

				System.out.println(new SysCpuInfo(session).getCPUInfo());
				System.out.println(new SysMemInfo(session).getMEMInfo());
				/* Show exit status, if available (otherwise "null") */

				System.out.println("ExitCode: " + session.getExitStatus());

				session.close();
				con.close();

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 远程服务器的用户名密码

		// 服务器端的文件下载到本地的目录下
		// scpClient.getFile("/home/oracle/RUNNING.txt", "C:/");

		// 将本地文件上传到服务器端的目录下
		// scp.putFile("C:/RUNNING.txt", "/home/oracle");
	}

}
