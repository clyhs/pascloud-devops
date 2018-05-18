package com.pascloud.module.common.web;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.constant.Constants;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.server.service.ServerService;
import com.spotify.docker.client.DefaultDockerClient;

import net.sf.json.JSONObject;


public abstract class BaseController {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected ServerService m_serverService;
	
	protected URI dockerEndpoint;

	protected DefaultDockerClient dockerClient;

	protected Integer defaultPort = 2375;
	
	protected String sourceFolder = "/home/webapps";
	
	protected String dockerApiVersion;
	
	protected String webappPath;
	
	{
		try{
			
			//DefaultDockerClient docker = DefaultDockerClient.builder().uri("http://192.168.0.16:"+defaultPort).build();
			//dockerClient = docker;
			//dockerEndpoint = docker.builder().uri();
			//dockerApiVersion = dockerClient.version().apiVersion();
			
			
		}catch(Exception e){
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public String exceptionProcess(HttpServletRequest request, HttpServletResponse response,
			RuntimeException ex) {
		JSONObject json = new JSONObject();
		json.put("isError", true);
		json.put("msg", ex.getMessage());
		ex.printStackTrace();
		return json.toString();
	}
	
	public DefaultDockerClient getDockerClient(){
		this.dockerClient = DefaultDockerClient.builder().uri(m_serverService.getMasterDockerUrl()).build();
		return dockerClient;
	}

}
