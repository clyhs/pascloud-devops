package com.pascloud.module.pas.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.bean.docker.ContainerVo;
import com.pascloud.bean.docker.NodeVo;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.docker.service.DockerService;
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

}
