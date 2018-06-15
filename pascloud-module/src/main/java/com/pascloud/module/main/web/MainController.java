package com.pascloud.module.main.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pascloud.bean.system.User;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.module.main.service.TreeService;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.utils.ScpClientUtils;
import com.pascloud.utils.xml.XmlParser;
import com.pascloud.vo.common.TreeVo;
import com.pascloud.vo.docker.NodeVo;
import com.pascloud.vo.result.ResultCommon;
import com.pascloud.vo.server.ServerVo;
import com.pascloud.vo.server.SysServerInfo;
import com.spotify.docker.client.DefaultDockerClient;
import com.thoughtworks.xstream.XStream;

@Controller
@RequestMapping("module/main")
public class MainController extends BaseController {

	@Autowired
	private DockerService m_dockerService;
	@Autowired
	private TreeService   m_treeService;

	@RequestMapping("index.html")
	public ModelAndView index(HttpServletRequest request) {

		List<SysServerInfo> servers = new ArrayList<>();
		List<NodeVo> nodes = new ArrayList<>();
		nodes = m_dockerService.getNodes(getDockerClient());
		ModelAndView view = new ModelAndView("main/index");
		view.addObject("nodes", nodes);
		return view;
	}

	@RequestMapping("server.html")
	public ModelAndView server(HttpServletRequest request) {

		List<SysServerInfo> servers = new ArrayList<>();
		List<NodeVo> nodes = new ArrayList<>();
		nodes = m_dockerService.getNodes(getDockerClient());
		ModelAndView view = new ModelAndView("main/server");
		view.addObject("nodes", nodes);
		return view;
	}
	
	@RequestMapping("main.html")
	public ModelAndView main(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("main/main");
		
		List<NodeVo> nodes = new ArrayList<>();
		nodes = m_dockerService.getNodes(getDockerClient());
		view.addObject("nodes", nodes);
		return view;
	}

	@RequestMapping("trees.json")
	@ResponseBody
	public List<TreeVo> getLeftMenu(HttpServletRequest request) {
		List<TreeVo> trees = new ArrayList<>();
		trees = m_treeService.getTrees();
		return trees;
	}
	
	@RequestMapping("parentTrees.json")
	@ResponseBody
	public List<TreeVo> getParentTrees(HttpServletRequest request) {
		List<TreeVo> trees = new ArrayList<>();
		trees = m_treeService.getParentTrees();
		return trees;
	}
	
	@RequestMapping("childTrees.json")
	@ResponseBody
	public List<TreeVo> getChildTrees(HttpServletRequest request,
			@RequestParam(value = "pid", defaultValue = "0", required = true) String pid) {
		List<TreeVo> trees = new ArrayList<>();
		trees = m_treeService.getChildTrees(pid);
		return trees;
	}

	@RequestMapping("leaveSwarm.json")
	@ResponseBody
	public ResultCommon leaveSwarm(HttpServletRequest request,
			@RequestParam(value = "ip", defaultValue = "", required = true) String ip) {
		ResultCommon result = null;

		Boolean flag = m_dockerService.leaveSwarm(getDockerClient(), ip);
		if (!flag) {
			result = new ResultCommon(20000, "失败");
		} else {
			result = new ResultCommon(10000, "成功");
		}
		return result;

	}

	@RequestMapping("health.json")
	@ResponseBody
	public ResultCommon health(HttpServletRequest request) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		ResultCommon result = null;
		if (null != user) {
			result = new ResultCommon(PasCloudCode.SUCCESS);
		} else {
			user = new User();
			user.setId(10000);
			user.setName("admin");
			session.setAttribute("user", user);
			// result = new ResultCommon(PasCloudCode.ERROR);
			result = new ResultCommon(PasCloudCode.SUCCESS);
		}

		return result;
	}

	public static void main(String[] args) {
		String header = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		List<TreeVo> trees = new ArrayList<>();

		XStream xstream = new XStream();
		xstream.alias("tree", TreeVo.class);
		String xml = xstream.toXML(trees);
		xml = header + xml;

		String path = "d:/trees.xml";
		File file = new File(path);
		//trees =  (List<TreeVo>) xstream.fromXML(file);
		//System.out.println(trees.size());

		//File file = new File(path);

		//FileUtils.writeFileFromString(file, xml, false);
	}

}
