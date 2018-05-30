package com.pascloud.module.docker.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.utils.ScpClientUtils;
import com.pascloud.vo.common.MapVo;
import com.pascloud.vo.docker.NodeVo;
import com.pascloud.vo.server.SysServerInfo;

@Controller
@RequestMapping("module/docker")
public class DockerController extends BaseController {
	@Autowired
	private DockerService m_dockerService;

	@RequestMapping("getnodes")
	@ResponseBody
	public List<NodeVo> getNodes(){
		List<NodeVo> nodes = new ArrayList<>();
		nodes = m_dockerService.getNodes(getDockerClient());
		return nodes;
	}
	

}
