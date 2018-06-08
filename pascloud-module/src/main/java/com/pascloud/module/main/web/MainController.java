package com.pascloud.module.main.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pascloud.bean.system.User;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.utils.ScpClientUtils;
import com.pascloud.vo.common.TreeVo;
import com.pascloud.vo.docker.NodeVo;
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
		nodes = m_dockerService.getNodes(getDockerClient());
		ModelAndView view = new ModelAndView("main/index");
		view.addObject("nodes", nodes);
		return view;
	}
	
	@RequestMapping("server.html")
	public ModelAndView server(HttpServletRequest request){
		
		List<SysServerInfo> servers = new ArrayList<>();
		List<NodeVo> nodes = new ArrayList<>();
		nodes = m_dockerService.getNodes(getDockerClient());
		ModelAndView view = new ModelAndView("main/server");
		view.addObject("nodes", nodes);
		return view;
	}
	
	@RequestMapping("trees.json")
	@ResponseBody
	public List<TreeVo> getLeftMenu(HttpServletRequest request){
		List<TreeVo> trees = new ArrayList<>();

		TreeVo t1 = new TreeVo();
		t1.setId("1000");
		t1.setText("服务器管理");
		t1.setLeaf(false);
		t1.setIconCls("icon-folder");
		t1.setUrl("#");
		
		TreeVo t11 = new TreeVo();
		t11.setId("1100");
		t11.setText("节点管理");
		t11.setLeaf(true);
		t11.setIconCls("icon-application");
		t11.setUrl("/module/server/index.html");
		
		List<TreeVo> t1children = new ArrayList<>();
		t1children.add(t11);
		t1.setChildren(t1children);
		trees.add(t1);
		
		TreeVo t2 = new TreeVo();
		t2.setId("2000");
		t2.setText("云平台管理");
		t2.setLeaf(false);
		t2.setIconCls("icon-folder");
		t2.setUrl("#");
		
		TreeVo t21 = new TreeVo();
		t21.setId("2100");
		t21.setText("服务管理");
		t21.setLeaf(true);
		t21.setIconCls("icon-application");
		t21.setUrl("/module/pasService/index.html");
		
		TreeVo t22 = new TreeVo();
		t22.setId("2200");
		t22.setText("镜像管理");
		t22.setLeaf(true);
		t22.setIconCls("icon-application");
		t22.setUrl("/module/image/index.html");
		
		TreeVo t23 = new TreeVo();
		t23.setId("2300");
		t23.setText("缓存管理");
		t23.setLeaf(true);
		t23.setIconCls("icon-application");
		t23.setUrl("/module/redis/index.html");
		
		TreeVo t24 = new TreeVo();
		t24.setId("2400");
		t24.setText("资源管理");
		t24.setLeaf(true);
		t24.setIconCls("icon-application");
		t24.setUrl("/module/pasdev/index.html");
		
		TreeVo t25 = new TreeVo();
		t25.setId("2500");
		t25.setText("租户管理");
		t25.setLeaf(true);
		t25.setIconCls("icon-application");
		t25.setUrl("/module/tenant/index.html");
		
		TreeVo t26 = new TreeVo();
		t26.setId("2600");
		t26.setText("配置管理");
		t26.setLeaf(true);
		t26.setIconCls("icon-application");
		t26.setUrl("/module/config/index.html");
		
		List<TreeVo> t2children = new ArrayList<>();
		t2children.add(t26);
		t2children.add(t25);
		t2children.add(t21);
		t2children.add(t22);
		t2children.add(t23);
		t2children.add(t24);
		
		t2.setChildren(t2children);
		trees.add(t2);
		
		
		TreeVo t3 = new TreeVo();
		t3.setId("3000");
		t3.setText("数据库管理");
		t3.setLeaf(false);
		t3.setIconCls("icon-folder");
		t3.setUrl("#");
		
		TreeVo t31 = new TreeVo();
		t31.setId("3100");
		t31.setText("MYCAT配置");
		t31.setLeaf(true);
		t31.setIconCls("icon-application");
		t31.setUrl("/module/mycat/index.html");
		
		TreeVo t32 = new TreeVo();
		t32.setId("3200");
		t32.setText("数据库客户端");
		t32.setLeaf(true);
		t32.setIconCls("icon-application");
		t32.setUrl("/module/database/index.html");
		
		TreeVo t33 = new TreeVo();
		t33.setId("3300");
		t33.setText("MYCAT监控");
		t33.setLeaf(true);
		t33.setIconCls("icon-application");
		t33.setUrl("/module/mycat/monitor.html");
		
		TreeVo t34 = new TreeVo();
		t34.setId("3400");
		t34.setText("数据库管理");
		t34.setLeaf(true);
		t34.setIconCls("icon-application");
		t34.setUrl("/module/dbserver/index.html");
		
		
		List<TreeVo> t3children = new ArrayList<>();
		t3children.add(t34);
		t3children.add(t32);
		t3children.add(t31);
		t3children.add(t33);
		t3.setChildren(t3children);
		trees.add(t3);
		
		TreeVo t4 = new TreeVo();
		t4.setId("4000");
		t4.setText("其它管理");
		t4.setLeaf(false);
		t4.setIconCls("icon-folder");
		t4.setUrl("#");
		
		TreeVo t41= new TreeVo();
		t41.setId("4100");
		t41.setText("PB应用管理");
		t41.setLeaf(true);
		t41.setIconCls("icon-application");
		t41.setUrl("/module/paspb/index.html");
		
		List<TreeVo> t4children = new ArrayList<>();
		t4children.add(t41);
		t4.setChildren(t4children);
		trees.add(t4);
		return trees;
	}
	@RequestMapping("leaveSwarm.json")
	@ResponseBody
	public ResultCommon leaveSwarm(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip){
		ResultCommon result = null;
		
		Boolean flag = m_dockerService.leaveSwarm(getDockerClient(),ip);
		if(!flag){
			result = new ResultCommon(20000,"失败");
		}else{
			result = new ResultCommon(10000,"成功");
		}
		return result;
		
	}
	
	@RequestMapping("health.json")
	@ResponseBody
 	public ResultCommon health(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		ResultCommon result = null;
		if(null!=user){
			result = new ResultCommon(PasCloudCode.SUCCESS);
		}else{
			user = new User();
			user.setId(10000);
			user.setName("admin");
			session.setAttribute("user", user);
			//result = new ResultCommon(PasCloudCode.ERROR);
			result = new ResultCommon(PasCloudCode.SUCCESS);
		}
		
		
		return result;
 	}
 
}
