package com.pascloud.module.paspb.service;

import org.springframework.stereotype.Service;

import com.pascloud.utils.ScpClientUtils;

@Service
public class PaspbService {
	
	public String readSpringXml(String name,String addr){
		StringBuffer sb = new StringBuffer();
		String path = "/home/"+name+"/pas_db2/WEB-INF/classes/applicationContext_resources.xml";
		ScpClientUtils scpClient = new ScpClientUtils(addr, "root", "tccp@2012");
		System.out.println(path);
		sb.append(scpClient.catFileToString(path));
		scpClient.close();
		return sb.toString();
	}

}
