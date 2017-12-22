package com.pascloud.module.pas.web;

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
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.utils.ScpClientUtils;
import com.pascloud.vo.result.ResultCommon;
import com.spotify.docker.client.DefaultDockerClient;

@Controller
@RequestMapping("module/pas")
public class PasController extends BaseController {
	
	@Autowired
	private DockerService m_dockerService;
	
	@RequestMapping("index.html")
	public String index(){
		return "pas/index";
	}
	
	@RequestMapping("containers.json")
	@ResponseBody
	public List<ContainerVo> getContainers(){
		List<ContainerVo> containers = new ArrayList<>();
		//containers = m_dockerService.getContainer(dockerClient);
		List<NodeVo> nodes = new ArrayList<>();
		
		nodes = m_dockerService.getNodes(dockerClient);
		
		for(NodeVo vo: nodes){
			DefaultDockerClient client = DefaultDockerClient.builder()
					.uri("http://"+vo.getAddr()+":"+defaultPort).build();
			List<ContainerVo> subContainers = m_dockerService.getContainer(client);
			if(null != subContainers && subContainers.size()>0){
				containers.addAll(subContainers);
			}
		}
		
		return containers;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="addContainer.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon addContainer(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip,
			@RequestParam(value="port",defaultValue="",required=true) String port,
			@RequestParam(value="name",defaultValue="",required=true) String name,
			@RequestParam(value="imageName",defaultValue="tomcat:7.0.82-jre8",required=true) String imageName){
		ResultCommon result = new ResultCommon(10000,"成功");
		
		String containerName = "paspb_"+name;
		String bindVolumeFrom = "/home/"+containerName;
		String id = "";
		
		Map param = new HashMap();
		param.put("ip", ip);
		param.put("port", port);
		param.put("name", name);
		param.put("imageName", imageName);
		
		log.info("开始拷贝源码目录");
		
		ScpClientUtils scpClient = ScpClientUtils.getInstance(ip, "root", "tccp@2012");
		scpClient.copyFolder(sourceFolder, bindVolumeFrom);
		scpClient.close();
		
		log.info("结束拷贝源码目录");
		
		log.info("开始新建容器");
		
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		id = m_dockerService.addContainer(client, port, bindVolumeFrom, "", imageName, containerName);
		System.out.println(id);
		
		log.info("结束新建容器");
		log.info(param.toString());
		
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
	@RequestMapping(value="readContainerSpringXml.json",method=RequestMethod.POST)
	@ResponseBody
	public String readContainerSpringXml(HttpServletRequest request){
		
		ResultCommon result = new ResultCommon(10000,"成功");
		String containerId = request.getParameter("containerId");
		String path = "/pas_db2/WEB-INF/classes/applicationContext_resources.xml";
		
		return null;
	}

}
