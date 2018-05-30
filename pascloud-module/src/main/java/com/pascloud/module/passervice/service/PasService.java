package com.pascloud.module.passervice.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pascloud.constant.Constants;
import com.pascloud.module.docker.service.ContainerService;
import com.pascloud.vo.docker.ContainerVo;
import com.pascloud.vo.server.ServerVo;

@Service
public class PasService {
	
	private static Logger log = LoggerFactory.getLogger(PasService.class);
	
	@Autowired
	private ContainerService m_containerService;
	
	
	public List<ContainerVo> getContainerWithMainService(){
		List<ContainerVo> containters = new ArrayList<>();
		containters = m_containerService.getContainers(Constants.PASCLOUD_SERVICE_DEMO_CONTAINER);
		return containters;
	}
	
	public String getServicePathWithContainerName(String name){
		name = name.replaceAll("_", "-");
		name = name.replaceAll("pascloud", "pas-cloud");
		String path = Constants.PASCLOUD_HOME+name;
		return path;
	}
	
	public String getServicePasdevPathWithContainerName(String name){
		name = name.replaceAll("_", "-");
		name = name.replaceAll("pascloud", "pas-cloud");
		String path = Constants.PASCLOUD_HOME+name+"/data/pasplus/config/";
		return path;
	}
	
	public String getServiceConfPathWithContainerName(String name){
		name = name.replaceAll("_", "-");
		name = name.replaceAll("pascloud", "pas-cloud");
		String path = Constants.PASCLOUD_HOME+name+"/conf/";
		return path;
	}
}
