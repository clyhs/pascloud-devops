package com.pascloud.module.passervice.web;

import java.io.File;
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
import org.springframework.web.servlet.ModelAndView;

import com.pascloud.constant.Constants;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.docker.service.ContainerService;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.module.passervice.service.ConfigService;
import com.pascloud.module.passervice.service.PasService;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.utils.RandomUtils;
import com.pascloud.utils.ScpClientUtils;
import com.pascloud.vo.docker.ContainerVo;
import com.pascloud.vo.pass.PasTypeEnum;
import com.pascloud.vo.pass.PasTypeVo;
import com.pascloud.vo.result.ResultCommon;
import com.spotify.docker.client.DefaultDockerClient;

@Controller
@RequestMapping("module/pasService")
public class PasServiceController extends BaseController {
	
	@Autowired
	private DockerService    m_dockerService;
	@Autowired
	private ContainerService m_containerService;
	@Autowired
	private ConfigService    m_configService;
	@Autowired
	private PasCloudConfig   m_config;
	@Autowired
	private PasService       m_pasService;

	@RequestMapping("index.html")
	public String index(HttpServletRequest request){
		return "pasService/index";
	}
	
	/**
	 * 添加基础环境服务
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="addBaseContainer.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon addBaseContainer(HttpServletRequest request){
		ResultCommon result = new ResultCommon(10000,"成功");
		//addRedisContainer();
		//addZookeeperContainer();
		//addMQContainer();
		//addMycatContainer();
		return result;
	}
	
	/**
	 * 获取服务的全部类型
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getPasCloudServiceType.json",method=RequestMethod.POST)
	@ResponseBody
	public List<PasTypeVo> getPasCloudServiceType(HttpServletRequest request){
		 List<PasTypeVo> list = new ArrayList<>();
		 
		 PasTypeEnum[] pasTypes = PasTypeEnum.values();
		 for(PasTypeEnum p : pasTypes){
			 PasTypeVo vo = new PasTypeVo();
			 vo.setKey(p.getIndex()+"");
			 vo.setValue(p.getValue());
			 if(p.getIndex()>0){
				 list.add(vo);
			 }
		 }
		 
		 return list;
	}
	/**
	 * 按类型和IP新建新的服务
	 * @param request
	 * @param ip
	 * @param type
	 * @return
	 */
	@RequestMapping(value="addPasService.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon addPasService(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip,
			@RequestParam(value="type",defaultValue="0",required=true) Integer type
			){
		ResultCommon result = null;
		Boolean isExist = false;
		log.info("添加服务");
		if(ip.length() == 0 || type == 0){
			return new ResultCommon(PasCloudCode.PARAMEXCEPTION);
		}
		String service = PasTypeEnum.getValue(type);
		if(null == service || "".equals(service)){
			return new ResultCommon(PasCloudCode.PARAMEXCEPTION);
		}
		
		
		//判断基础服务是否已经启动
		
		if(service.contains(PasTypeEnum.DEMO.getValue()) 
				|| service.contains(PasTypeEnum.PASPM.getValue())
				|| service.contains(PasTypeEnum.TOMCAT.getValue())){
			result = m_pasService.checkBaseService();
			if(!result.getCode().equals(PasCloudCode.SUCCESS.getCode())){
				return result;
			}
		}
		
		if(service.contains(PasTypeEnum.MYCAT.getValue())){
			result = m_pasService.checkMysqlService();
			if(!result.getCode().equals(PasCloudCode.SUCCESS.getCode())){
				return result;
			}
		}
		
		
		//m_pasService.checkImageExist(ip, "redis:latest");
		List<ContainerVo> containers = m_containerService.getContainers(service);
		if(containers.size()>0){
			for(ContainerVo vo:containers){
				/*
				if(ip.equals(vo.getIp())){
					log.info("服务已经存在");
					isExist = true;
				}*/
				if(vo.getName().contains(PasTypeEnum.DEMO.getValue()) 
						|| vo.getName().contains(PasTypeEnum.PASPM.getValue())
						|| vo.getName().contains(PasTypeEnum.TOMCAT.getValue())){
					
					if(ip.equals(vo.getIp())){
						log.info("服务已经存在");
						isExist = true;
					}
				}else{
					log.info("service="+service+"已经存在");
					isExist = true;
				}
			}
		}
		if(isExist){
			return new ResultCommon(PasCloudCode.ISEXIST);
		}else{
			log.info("ip="+ip+",service="+service);
			if(m_pasService.addPasService(ip, type,service)){
				return new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result= new ResultCommon(PasCloudCode.ERROR);
			}
		}
		return result;
	}
	

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="addTomcatContainer.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon addTomcatContainer(HttpServletRequest request){
		ResultCommon result = new ResultCommon(10000,"成功");
		//addMainServiceContainer();
		addtomcatContainer();
		return result;
	}
	
	/**
	 * 复制管家服务
	 * @param request
	 * @param ip
	 * @param servicePort
	 * @param restPort
	 * @return
	 */
	@RequestMapping(value="copyPaspmServiceContainer.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon copyPaspmServiceContainer(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip,
			@RequestParam(value="servicePort",defaultValue="",required=true) String servicePort,
			@RequestParam(value="restPort",defaultValue="",required=true) String restPort){
		ResultCommon result = null;
		//addMainServiceContainer();
		//addPaspmServiceContainer();
		//copyPaspmServiceContainer();
		if("".equals(ip) || "".equals(servicePort) || "".equals(restPort)){
			return result = new ResultCommon(PasCloudCode.ERROR);
		}else{
			if(m_pasService.copyPaspmServiceContainer(ip, servicePort, restPort)){
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
		}
		return result;
	}
	
	/**
	 * 复制主服务
	 * @param request
	 * @param ip
	 * @param servicePort
	 * @param restPort
	 * @return
	 */
	@RequestMapping(value="copyMainServiceContainer.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon copyMainServiceContainer(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip,
			@RequestParam(value="servicePort",defaultValue="",required=true) String servicePort,
			@RequestParam(value="restPort",defaultValue="",required=true) String restPort){
		ResultCommon result = null;
		//addMainServiceContainer();
		//addPaspmServiceContainer();
		//copyMainServiceContainer();
		if("".equals(ip) || "".equals(servicePort) || "".equals(restPort)){
			return result = new ResultCommon(PasCloudCode.ERROR);
		}else{
			if(m_pasService.copyMainServiceContainer(ip, servicePort, restPort)){
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
		}
		return result;
	}
	

    private void addtomcatContainer(){
    	String containerName = "pascloud_tomcat";
		String bindVolumeFrom = "/home/pascloud/tomcat";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {"/home/pascloud/tomcat/bin/catalina.sh", "run"};
		String imageName = "pascloud/jdk7:v1.0";
		Map<String,String> port = new HashMap<String,String>();
		port.put("8170", "8170");
		List<String> envs = new ArrayList<>();
        log.info("开始新建tomcat容器");
		
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+"192.168.0.7"+":"+defaultPort).build();
		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
		System.out.println(id);
		log.info("结束新建tomcat容器");
	}
    
    
}
