package com.pascloud.module.tool.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.bean.docker.ContainerVo;
import com.pascloud.bean.docker.NodeVo;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.vo.common.TreeVo;
import com.spotify.docker.client.DefaultDockerClient;

@Controller
@RequestMapping("module/tool")
public class ToolController extends BaseController {
	
	@Autowired
	private DockerService m_dockerService;

	@RequestMapping("index.html")
	public String index(HttpServletRequest request){
		return "tool/index";
	}
	
	@RequestMapping("containerTree.json")
	@ResponseBody
	public List<TreeVo> getContainerTree(HttpServletRequest request){
		List<TreeVo> trees = new ArrayList<>();
		List<ContainerVo> containers = new ArrayList<>();
		//containers = m_dockerService.getContainer(dockerClient);
		List<NodeVo> nodes = new ArrayList<>();
		
		nodes = m_dockerService.getNodes(getDockerClient());
		
		for(NodeVo vo: nodes){
			DefaultDockerClient client = DefaultDockerClient.builder()
					.uri("http://"+vo.getAddr()+":"+defaultPort).build();
			List<ContainerVo> subContainers = m_dockerService.getContainer(client,"");
			if(null != subContainers && subContainers.size()>0){
				containers.addAll(subContainers);
			}
		}
		
		if(containers.size() > 0){
			for(ContainerVo vo : containers){
				TreeVo tree = new TreeVo();
				tree.setId(vo.getId());
				tree.setText(vo.getName());
				tree.setUrl("");
				trees.add(tree);
			}
		}
		
		return trees;
	}
}
