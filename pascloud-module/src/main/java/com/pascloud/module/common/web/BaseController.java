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

import com.pascloud.module.config.PasCloudConfig;
import com.spotify.docker.client.DefaultDockerClient;

import net.sf.json.JSONObject;


public abstract class BaseController {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	protected URI dockerEndpoint;

	protected DefaultDockerClient dockerClient;

	protected Integer defaultPort = 2375;
	
	protected String sourceFolder = "/home/webapps";
	
	protected String dockerApiVersion;
	
	{
		try{
			
			DefaultDockerClient docker = DefaultDockerClient.builder().uri("http://192.168.0.16:"+defaultPort).build();
			dockerClient = docker;
			dockerEndpoint = docker.builder().uri();
			dockerApiVersion = dockerClient.version().apiVersion();
			
		}catch(Exception e){
			e.printStackTrace();
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
		return json.toString();
	}

}
