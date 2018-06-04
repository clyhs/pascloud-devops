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
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.module.passervice.service.ConfigService;
import com.pascloud.module.passervice.service.PasService;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.utils.RandomUtils;
import com.pascloud.utils.ScpClientUtils;
import com.pascloud.vo.pass.PasTypeVo;
import com.pascloud.vo.result.ResultCommon;
import com.spotify.docker.client.DefaultDockerClient;

@Controller
@RequestMapping("module/pasService")
public class PasServiceController extends BaseController {
	
	@Autowired
	private DockerService  m_dockerService;
	@Autowired
	private ConfigService  m_configService;
	@Autowired
	private PasCloudConfig m_config;
	@Autowired
	private PasService     m_pasService;

	@RequestMapping("index.html")
	public String index(HttpServletRequest request){
		return "pasService/index";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="addBaseContainer.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon addBaseContainer(HttpServletRequest request){
		ResultCommon result = new ResultCommon(10000,"成功");
		addRedisContainer();
		addZookeeperContainer();
		addMQContainer();
		addMycatContainer();
		return result;
	}
	
	@RequestMapping(value="getPasCloudServiceType.json",method=RequestMethod.POST)
	@ResponseBody
	public List<PasTypeVo> getPasCloudServiceType(){
		 List<PasTypeVo> list = new ArrayList<>();
		 PasTypeVo p1 = new PasTypeVo();
		 p1.setKey("pascloud_redis");
		 p1.setValue("pascloud_redis");
		 
		 PasTypeVo p2 = new PasTypeVo();
		 p2.setKey("pascloud_zookeeper_admin");
		 p2.setValue("pascloud_zookeeper_admin");
		 
		 PasTypeVo p3 = new PasTypeVo();
		 p3.setKey("pascloud_mycat");
		 p3.setValue("pascloud_mycat");
		 
		 PasTypeVo p4 = new PasTypeVo();
		 p4.setKey("pascloud_activemq");
		 p4.setValue("pascloud_activemq");
		 
		 PasTypeVo p5 = new PasTypeVo();
		 p5.setKey("pascloud_tomcat");
		 p5.setValue("pascloud_tomcat");
		 
		 list.add(p1);
		 list.add(p2);
		 list.add(p3);
		 list.add(p4);
		 list.add(p5);
		 
		 return list;
	}
	
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value="addMainServiceContainer.json",method=RequestMethod.POST)
//	@ResponseBody
//	public ResultCommon addMainServiceContainer(HttpServletRequest request){
//		ResultCommon result = new ResultCommon(10000,"成功");
//		//addMainServiceContainer();
//		//addPaspmServiceContainer();
//		return result;
//	}
//	
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value="addPaspmServiceContainer.json",method=RequestMethod.POST)
//	@ResponseBody
//	public ResultCommon addPaspmServiceContainer(HttpServletRequest request){
//		ResultCommon result = new ResultCommon(10000,"成功");
//		//addMainServiceContainer();
//		//addPaspmServiceContainer();
//		return result;
//	}
	
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
	
//	private void copyMainServiceContainer(){
//        String ip = "192.168.0.7";
//        
//        String randomId = RandomUtils.generateLowerString(6);
//		
//        String containerName = "pascloud_service_demo_"+randomId;
//		String bindVolumeFrom = "/home/pascloud/pas-cloud-service-demo-"+randomId;
//		String bindVolumeTo = bindVolumeFrom;
//		String id = "";
//		String[] cmd = {"/home/pascloud/pas-cloud-service-demo-"+randomId+"/bin/start.sh"};
//		String imageName = "pascloud/jdk7:v1.0";
//		Map<String,String> port = new HashMap<String,String>();
//		port.put("8201", "8301");
//		port.put("8211", "8311");
//		List<String> envs = new ArrayList<>();
//		
//		log.info("开始拷贝源码目录");
//		
//		ScpClientUtils scpClient = new ScpClientUtils(ip, "root", "tccp@2012");
//		scpClient.copyFolder("/home/pascloud/pas-cloud-service-demo", bindVolumeFrom);
//		//scpClient.close();
//		String dubbofilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ m_config.getPASCLOUD_SERVICE_DIR()+File.separator+"dubbo.properties";
//		System.out.println(dubbofilepath);
//		m_configService.setApplicationName(containerName);
//		scpClient.putFileToServer(dubbofilepath, bindVolumeFrom+"/conf/");
//		
//		String configfilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ m_config.getPASCLOUD_SERVICE_DIR()+File.separator+"config.properties";
//		m_configService.setHomePath(bindVolumeFrom);
//		scpClient.putFileToServer(configfilepath, bindVolumeFrom+"/conf/");
//		
//		scpClient.close();
//		log.info("结束拷贝源码目录");
//		
//        log.info("开始新建main容器");
//		
//		DefaultDockerClient client = DefaultDockerClient.builder()
//				.uri("http://"+ip+":"+defaultPort).build();
//		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
//		System.out.println(id);
//		log.info("结束新建main容器");
//	}
//	
//	private void copyPaspmServiceContainer(){
//        String ip = "192.168.0.7";
//        
//        String randomId = RandomUtils.generateLowerString(6);
//		
//        String containerName = "pascloud_service_paspm_"+randomId;
//		String bindVolumeFrom = "/home/pascloud/pas-cloud-service-paspm-"+randomId;
//		String bindVolumeTo = bindVolumeFrom;
//		String id = "";
//		String[] cmd = {"/home/pascloud/pas-cloud-service-paspm-"+randomId+"/bin/start.sh"};
//		String imageName = "pascloud/jdk7:v1.0";
//		Map<String,String> port = new HashMap<String,String>();
//		port.put("8202", "8202");
//		port.put("8212", "8212");
//		List<String> envs = new ArrayList<>();
//		
//		log.info("开始拷贝源码目录");
//		
//		ScpClientUtils scpClient = new ScpClientUtils(ip, "root", "tccp@2012");
//		scpClient.copyFolder("/home/pascloud/pas-cloud-service-paspm", bindVolumeFrom);
//		//scpClient.close();
//		String dubbofilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ m_config.getPASCLOUD_SERVICE_DIR()+File.separator+"dubbo.properties";
//		System.out.println(dubbofilepath);
//		m_configService.setApplicationName(containerName);
//		scpClient.putFileToServer(dubbofilepath, bindVolumeFrom+"/conf/");
//		
//		String configfilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ m_config.getPASCLOUD_SERVICE_DIR()+File.separator+"config.properties";
//		m_configService.setHomePath("/home/pascloud/pas-cloud-service-demo");
//		m_configService.setDev("true");
//		scpClient.putFileToServer(configfilepath, bindVolumeFrom+"/conf/");
//		
//		scpClient.close();
//		log.info("结束拷贝源码目录");
//		
//        log.info("开始新建main容器");
//		
//		DefaultDockerClient client = DefaultDockerClient.builder()
//				.uri("http://"+ip+":"+defaultPort).build();
//		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
//		System.out.println(id);
//		log.info("结束新建main容器");
//	}
	
	private void addRedisContainer(){
		String containerName = "pascloud_redis";
		String bindVolumeFrom = "";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {};
		String imageName = "redis:latest";
		Map<String,String> port = new HashMap<String,String>();
		port.put("6379", "6379");
		List<String> envs = new ArrayList<>();
        log.info("开始新建reids容器");
		
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+"192.168.0.7"+":"+defaultPort).build();
		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
		System.out.println(id);
		log.info("结束新建reids容器");
	}
	
	private void addZookeeperContainer(){
		String containerName = "pascloud_zookeeper_admin";
		String bindVolumeFrom = "";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {};
		String imageName = "pascloud/zk_dubbo:v1.1";
		Map<String,String> port = new HashMap<String,String>();
		port.put("8686", "8686");
		port.put("2181", "2181");
		List<String> envs = new ArrayList<>();
        log.info("开始新建zk容器");
		
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+"192.168.0.7"+":"+defaultPort).build();
		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
		System.out.println(id);
		log.info("结束新建zk容器");
	}
	
	private void addMQContainer(){
		String containerName = "pascloud_activemq";
		String bindVolumeFrom = "";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {};
		String imageName = "webcenter/activemq:latest";
		Map<String,String> port = new HashMap<String,String>();
		port.put("61616", "61616");
		List<String> envs = new ArrayList<>();
		envs.add("ACTIVEMQ_ADMIN_LOGIN=admin");
		envs.add("ACTIVEMQ_ADMIN_PASSWORD=admin");
        log.info("开始新建mq容器");
		
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+"192.168.0.7"+":"+defaultPort).build();
		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
		System.out.println(id);
		log.info("结束新建mq容器");
	}
	
	private void addMycatContainer(){
		String containerName = "pascloud_mycat";
		String bindVolumeFrom = "/home/pascloud/mycat";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {"/home/pascloud/mycat/bin/mycat","console","&"};
		String imageName = "pascloud/jdk7:v1.0";
		Map<String,String> port = new HashMap<String,String>();
		port.put("8066", "8066");
		port.put("9066", "9066");
		List<String> envs = new ArrayList<>();
        log.info("开始新建mycat容器");
		
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+"192.168.0.7"+":"+defaultPort).build();
		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
		System.out.println(id);
		log.info("结束新建mycat容器");
	}
	
//	private void addMainServiceContainer(){
//		String containerName = "pascloud_service_main";
//		String bindVolumeFrom = "/home/pascloud/pas-cloud-service-demo";
//		String bindVolumeTo = bindVolumeFrom;
//		String id = "";
//		String[] cmd = {"/home/pascloud/pas-cloud-service-demo/bin/start.sh"};
//		String imageName = "pascloud/jdk7:v1.0";
//		Map<String,String> port = new HashMap<String,String>();
//		port.put("8201", "8201");
//		port.put("8211", "8211");
//		List<String> envs = new ArrayList<>();
//        log.info("开始新建main容器");
//		
//		DefaultDockerClient client = DefaultDockerClient.builder()
//				.uri("http://"+"192.168.0.7"+":"+defaultPort).build();
//		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
//		System.out.println(id);
//		log.info("结束新建main容器");
//	}
//	
//    private void addPaspmServiceContainer(){
//    	String containerName = "pascloud_service_paspm";
//		String bindVolumeFrom = "/home/pascloud/pas-cloud-service-paspm";
//		String bindVolumeTo = bindVolumeFrom;
//		String id = "";
//		String[] cmd = {"/home/pascloud/pas-cloud-service-paspm/bin/start.sh"};
//		String imageName = "pascloud/jdk7:v1.0";
//		Map<String,String> port = new HashMap<String,String>();
//		port.put("8202", "8202");
//		port.put("8212", "8212");
//		List<String> envs = new ArrayList<>();
//        log.info("开始新建paspm容器");
//		
//		DefaultDockerClient client = DefaultDockerClient.builder()
//				.uri("http://"+"192.168.0.7"+":"+defaultPort).build();
//		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
//		System.out.println(id);
//		log.info("结束新建paspm容器");
//	}
    
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
