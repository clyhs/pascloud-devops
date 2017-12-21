package com.pascloud.module.docker.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.bean.docker.Node;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.docker.service.DockerService;

@Controller
@RequestMapping("module/docker")
public class DockerController extends BaseController {
	@Autowired
	private DockerService m_dockerService;

	@RequestMapping("getnodes")
	@ResponseBody
	public List<Node> getNodes(){
		List<Node> nodes = new ArrayList<>();
		nodes = m_dockerService.getNodes(dockerClient);
		return nodes;
	}
}
