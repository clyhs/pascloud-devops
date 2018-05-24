package com.pascloud.module.docker.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.bean.docker.ContainerVo;
import com.pascloud.bean.docker.NodeVo;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.docker.service.ContainerService;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.vo.result.ResultCommon;
import com.spotify.docker.client.DefaultDockerClient;
@Controller
@RequestMapping("module/container")
public class ContainerController extends BaseController {
	
	@Autowired
	private DockerService m_dockerService;
	
	@Autowired
	private ContainerService m_containerService;
	
	@RequestMapping("containers.json")
	@ResponseBody
	public List<ContainerVo> getContainers(HttpServletRequest request,
			@RequestParam(value="str",defaultValue="",required=true) String str){
		List<ContainerVo> containers = new ArrayList<>();
		//containers = m_dockerService.getContainer(dockerClient);
		containers = m_containerService.getContainers(str);
		return containers;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="addContainer.json",method=RequestMethod.GET)
	@ResponseBody
	public ResultCommon addContainer(HttpServletRequest request){
		ResultCommon result = new ResultCommon(10000,"成功");
		// docker run --name pas_redis --restart=always  -d -p 6379:6379 redis:latest
		String containerName = "pascloud_redis";
		String bindVolumeFrom = "";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {};
		String imageName = "redis:latest";
		Map<String,String> port = new HashMap<String,String>();
		port.put("6379", "6379");
		List<String> envs = new ArrayList<>();
		
		//$ docker run --name pas_activemq -p 61616:61616 -e ACTIVEMQ_ADMIN_LOGIN=admin -e ACTIVEMQ_ADMIN_PASSWORD=admin --restart=always -d webcenter/activemq:latest
		/*
		String containerName = "pascloud_activemq";
		String bindVolumeFrom = "";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {};
		String imageName = "webcenter/activemq:latest";
		Map<String,String> port = new HashMap<String,String>();
		port.put("61616", "61616");
		List<String> envs = new ArrayList<>();
		envs.add("ACTIVEMQ_ADMIN_LOGIN=admin");
		envs.add("ACTIVEMQ_ADMIN_PASSWORD=admin");*/
		
		//docker run --name pas_zk_dubbo_admin  --restart=always  -d -p 8686:8686 -p 2181:2181 pascloud/zk_dubbo:v1.1
		/*		
		String containerName = "pascloud_zookeeper_admin";
		String bindVolumeFrom = "";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {};
		String imageName = "pascloud/zk_dubbo:v1.1";
		Map<String,String> port = new HashMap<String,String>();
		port.put("8686", "8686");
		port.put("2181", "2181");*/
		/*
		String containerName = "pascloud_mycat";
		String bindVolumeFrom = "/home/pascloud/mycat";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {"/home/pascloud/mycat/bin/mycat","console","&"};
		String imageName = "pascloud/jdk7:v1.0";
		Map<String,String> port = new HashMap<String,String>();
		port.put("8066", "8066");
		port.put("9066", "9066");*/
		
	    
		/*
		String containerName = "pascloud_service_main";
		String bindVolumeFrom = "/home/pascloud/pas-cloud-service-demo";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {"/home/pascloud/pas-cloud-service-demo/bin/start.sh"};
		String imageName = "pascloud/jdk7:v1.0";
		Map<String,String> port = new HashMap<String,String>();
		port.put("8201", "8201");
		port.put("8211", "8211");*/
		/*
		String containerName = "pascloud_service_paspm";
		String bindVolumeFrom = "/home/pascloud/pas-cloud-service-paspm";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {"/home/pascloud/pas-cloud-service-paspm/bin/start.sh"};
		String imageName = "pascloud/jdk7:v1.0";
		Map<String,String> port = new HashMap<String,String>();
		port.put("8202", "8202");
		port.put("8212", "8212");*/
		
		log.info("开始新建容器");
		
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+"192.168.0.7"+":"+defaultPort).build();
		
		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
		System.out.println(id);
		
		log.info("结束新建容器");
		
		return result;
	}
	
	@RequestMapping(value="startContainer.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon startContainer(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip){
		
		ResultCommon result = new ResultCommon(10000,"成功");
		String containerId = request.getParameter("containerId");
		String status = "";
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		status = m_dockerService.startContainer(client, containerId);
		System.out.println(status);
		return result;
	}
	
	@RequestMapping(value="pauseContainer.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon pauseContainer(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip){
		
		ResultCommon result = new ResultCommon(10000,"成功");
		String containerId = request.getParameter("containerId");
		String status = "";
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		status = m_dockerService.pauseContainer(client, containerId);
		System.out.println(status);
		return result;
	}
	
	@RequestMapping(value="unpauseContainer.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon unpauseContainer(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip){
		
		ResultCommon result = new ResultCommon(10000,"成功");
		String containerId = request.getParameter("containerId");
		String status = "";
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		status = m_dockerService.unpauseContainer(client, containerId);
		System.out.println(status);
		return result;
	}
	
	@RequestMapping(value="stopContainer.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon stopContainer(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip){
		
		ResultCommon result = new ResultCommon(10000,"成功");
		String containerId = request.getParameter("containerId");
		String status = "";
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		status = m_dockerService.stopContainer(client, containerId);
		System.out.println(status);
		return result;
	}
	
	@RequestMapping(value="restartContainer.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon restartContainer(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip){
		
		ResultCommon result = new ResultCommon(10000,"成功");
		String containerId = request.getParameter("containerId");
		String status = "";
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		status = m_dockerService.restartContainer(client, containerId);
		System.out.println(status);
		return result;
	}

	@RequestMapping(value="getContainerLog.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon getContainerLog(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip,
			@RequestParam(value="name",defaultValue="",required=true) String name){
		
		ResultCommon result = new ResultCommon(10000,"成功");
		String log = "";
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		log = m_dockerService.getLog(client, name);
		System.out.println(log);
		result.setDesc(log);
		return result;
	}
}
