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

import com.pascloud.constant.Constants;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.docker.service.ContainerService;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.module.passervice.service.PasService;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.vo.docker.ContainerVo;
import com.pascloud.vo.docker.NodeVo;
import com.pascloud.vo.pass.PasTypeEnum;
import com.pascloud.vo.result.ResultCommon;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.messages.ContainerInfo;
@Controller
@RequestMapping("module/container")
public class ContainerController extends BaseController {
	
	@Autowired
	private DockerService    m_dockerService;
	@Autowired
	private PasService       m_pasService;
	@Autowired
	private ContainerService m_containerService;
	
	@RequestMapping("containers.json")
	@ResponseBody
	public List<ContainerVo> getContainers(HttpServletRequest request,
			@RequestParam(value="str",defaultValue="",required=true) String str){
		List<ContainerVo> containers = new ArrayList<>();
		//containers = m_dockerService.getContainer(dockerClient);
		containers = m_containerService.getContainers(str);
		for(ContainerVo vo :containers){
			if(vo.getName().startsWith("pascloud_redis")){
				vo.setCnname("缓存服务");
			}else if(vo.getName().startsWith("pascloud_mycat")){
				vo.setCnname("数据库中间件");
			}else if(vo.getName().startsWith("pascloud_zookeeper_admin")){
				vo.setCnname("注册中心");
			}else if(vo.getName().startsWith("pascloud_tomcat")){
				vo.setCnname("前端服务");
			}else if(vo.getName().startsWith("pascloud_activemq")){
				vo.setCnname("消息服务");
			}else if(vo.getName().startsWith("pascloud_service_demo")){
				vo.setCnname("公共服务");
			}else if(vo.getName().startsWith("pascloud_service_paspm")){
				vo.setCnname("管家服务");
			}else{
				vo.setCnname("基础服务");
			}
		}
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
			@RequestParam(value="ip",defaultValue="",required=true) String ip,
			@RequestParam(value="name",defaultValue="",required=true) String name,
			@RequestParam(value="containerId",defaultValue="",required=true) String containerId){
		
        ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		
		if(ip.length()==0 || name.length()==0 || containerId.length()==0){
			return new ResultCommon(PasCloudCode.ERROR);
		}
		
		if(name.contains(PasTypeEnum.DEMO.getValue())){
			log.info("重新上传"+name+"配置");
			m_pasService.uploadConfigForStart(ip, PasTypeEnum.DEMO, name);
		}else if(name.contains(PasTypeEnum.PASPM.getValue())){
			log.info("重新上传"+name+"配置");
			m_pasService.uploadConfigForStart(ip, PasTypeEnum.PASPM, name);
		}else if(name.contains(PasTypeEnum.TOMCAT.getValue())){
			log.info("重新上传"+name+"配置");
			m_pasService.uploadTomcatfile(ip);
		}else if(name.contains(PasTypeEnum.MYCAT.getValue())){
			log.info("重新上传"+name+"配置");
			m_pasService.uploadMycatfile(ip);
		}else{
			log.info("不用重新上传配置");
		}
		
		
		String status = "";
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		status = m_dockerService.startContainer(client, containerId);
		System.out.println(status);
		if(status.equals("running")){
			if(name.contains(PasTypeEnum.MYSQL.getValue())){
				log.info("重新上传"+name+"配置");
				m_pasService.createDataAndImp(ip);
			}
		}
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
		
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		String containerId = request.getParameter("containerId");
		String status = "";
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		ContainerInfo info =m_dockerService.getContainerInfoById(client, containerId);
		if(null!=info){
			String name = info.name();
			if(null != name){
				if(name.startsWith("/")){
					name = name.substring(1, name.length());
				}
				if(name.contains(Constants.SHIPYARD_PROXY)){
					return new ResultCommon(PasCloudCode.NONEAUTH);
				}
			}
		}
		status = m_dockerService.stopContainer(client, containerId);
		
		System.out.println(status);
		return result;
	}
	
	@RequestMapping(value="restartContainer.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon restartContainer(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip,
			@RequestParam(value="name",defaultValue="",required=true) String name,
			@RequestParam(value="containerId",defaultValue="",required=true) String containerId){
		
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		
		if(ip.length()==0 || name.length()==0 || containerId.length()==0){
			return new ResultCommon(PasCloudCode.ERROR);
		}
		
		if(name.contains(PasTypeEnum.DEMO.getValue())){
			log.info("重新上传"+name+"配置");
			m_pasService.uploadConfigForStart(ip, PasTypeEnum.DEMO, name);
		}else if(name.contains(PasTypeEnum.PASPM.getValue())){
			log.info("重新上传"+name+"配置");
			m_pasService.uploadConfigForStart(ip, PasTypeEnum.PASPM, name);
		}else if(name.contains(PasTypeEnum.TOMCAT.getValue())){
			log.info("重新上传"+name+"配置");
			m_pasService.uploadTomcatfile(ip);
		}else if(name.contains(PasTypeEnum.MYCAT.getValue())){
			log.info("重新上传"+name+"配置");
			m_pasService.uploadMycatfile(ip);
		}else{
			log.info("不用重新上传配置");
		}
		
		//String containerId = request.getParameter("containerId");
		String status = "";
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		status = m_dockerService.restartContainer(client, containerId);
		System.out.println(status);
		if(status.equals("running")){
			if(name.contains(PasTypeEnum.MYSQL.getValue())){
				log.info("重新上传"+name+"配置");
				m_pasService.createDataAndImp(ip);
			}
		}
		return result;
	}
	
	@RequestMapping(value="removeContainer.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon removeContainer(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip){
		
		ResultCommon result = new ResultCommon(10000,"成功");
		String containerId = request.getParameter("containerId");
		String status = "";
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		ContainerInfo info =m_dockerService.getContainerInfoById(client, containerId);
		if(null!=info){
			String name = info.name();
			if(null != name){
				if(name.startsWith("/")){
					name = name.substring(1, name.length());
				}
				if(name.contains(Constants.SHIPYARD_PROXY)){
					return new ResultCommon(PasCloudCode.NONEAUTH);
				}
			}
		}
		status = m_dockerService.removeContainer(client, containerId);
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
