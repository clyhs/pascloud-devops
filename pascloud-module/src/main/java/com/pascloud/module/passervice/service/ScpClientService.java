package com.pascloud.module.passervice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pascloud.utils.ScpClientUtils;

@Service
public class ScpClientService {
	
	private static Logger log = LoggerFactory.getLogger(ScpClientService.class);
	
	public ScpClientUtils buildScpClient(String ip,String username,String password){
		ScpClientUtils scpClient = new ScpClientUtils(ip,username, password);
		return scpClient;
	}
	
	public void putFileToServer(ScpClientUtils scpClient,String localPath,String destPath){
		scpClient.putFileToServer(localPath, destPath);
	}

}
