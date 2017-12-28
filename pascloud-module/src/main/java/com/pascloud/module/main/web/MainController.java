package com.pascloud.module.main.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pascloud.bean.docker.NodeVo;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.utils.ScpClientUtils;
import com.pascloud.vo.common.TreeVo;
import com.pascloud.vo.result.ResultCommon;
import com.pascloud.vo.server.SysServerInfo;
import com.spotify.docker.client.DefaultDockerClient;

@Controller
@RequestMapping("module/main")
public class MainController extends BaseController {
	
	@Autowired
	private DockerService m_dockerService;
	
	@RequestMapping("index.html")
	public ModelAndView index(HttpServletRequest request){
		
		List<SysServerInfo> servers = new ArrayList<>();
		List<NodeVo> nodes = new ArrayList<>();
		nodes = m_dockerService.getNodes(dockerClient);
		ModelAndView view = new ModelAndView("main/index");
		view.addObject("nodes", nodes);
		return view;
	}
	
	@RequestMapping("server.html")
	public ModelAndView server(HttpServletRequest request){
		
		List<SysServerInfo> servers = new ArrayList<>();
		List<NodeVo> nodes = new ArrayList<>();
		nodes = m_dockerService.getNodes(dockerClient);
		ModelAndView view = new ModelAndView("main/server");
		view.addObject("nodes", nodes);
		return view;
	}
	
	@RequestMapping("trees.json")
	@ResponseBody
	public List<TreeVo> getLeftMenu(){
		List<TreeVo> trees = new ArrayList<>();
		TreeVo t2 = new TreeVo();
		t2.setId("1");
		t2.setText("分行管理");
		t2.setLeaf(true);
		t2.setUrl("/module/pas/index.html");
		
		TreeVo t3 = new TreeVo();
		t3.setId("2");
		t3.setText("天维服务");
		t3.setLeaf(true);
		t3.setUrl("/module/pasService/index.html");
		
		TreeVo t4 = new TreeVo();
		t4.setId("3");
		t4.setText("天维工具");
		t4.setLeaf(true);
		t4.setUrl("/module/tool/index.html");
		
		//trees.add(t1);
		trees.add(t2);
		trees.add(t3);
		trees.add(t4);
		
		return trees;
	}
	@RequestMapping("leaveSwarm.json")
	@ResponseBody
	public ResultCommon leaveSwarm(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip){
		ResultCommon result = null;
		
		Boolean flag = m_dockerService.leaveSwarm(dockerClient,ip);
		if(!flag){
			result = new ResultCommon(20000,"失败");
		}else{
			result = new ResultCommon(10000,"成功");
		}
		return result;
		
	}

}
