package com.pascloud.module.passervice.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pascloud.constant.Constants;
import com.pascloud.module.common.service.AbstractBaseService;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.docker.service.ContainerService;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.module.server.service.ServerService;
import com.pascloud.utils.RandomUtils;
import com.pascloud.utils.ScpClientUtils;
import com.pascloud.utils.xml.XmlParser;
import com.pascloud.vo.common.MapVo;
import com.pascloud.vo.docker.ContainerVo;
import com.pascloud.vo.docker.ImageVo;
import com.pascloud.vo.pass.MqVo;
import com.pascloud.vo.pass.MycatVo;
import com.pascloud.vo.pass.PasConfigVo;
import com.pascloud.vo.pass.RedisVo;
import com.pascloud.vo.pass.ZookeeperVo;
import com.pascloud.vo.server.ServerVo;
import com.spotify.docker.client.DefaultDockerClient;
import com.thoughtworks.xstream.XStream;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

@Service
public class PasService extends AbstractBaseService {
	
	private static Logger log = LoggerFactory.getLogger(PasService.class);
	
	@Autowired
	private ContainerService m_containerService;
	@Autowired
	private ConfigService    m_configService;
	
	@Autowired
	private PasCloudConfig   m_config;
	@Autowired
	private DockerService    m_dockerService;
	@Autowired
	private ServerService    m_serverService;
	
	
	public List<ContainerVo> getContainerWithMainService(){
		List<ContainerVo> containters = new ArrayList<>();
		containters = m_containerService.getContainers(Constants.PASCLOUD_SERVICE_DEMO_CONTAINER);
		return containters;
	}
	
	public String getServicePathWithContainerName(String name){
		name = name.replaceAll("_", "-");
		name = name.replaceAll("pascloud", "pas-cloud");
		String path = Constants.PASCLOUD_HOME+name;
		return path;
	}
	
	public String getServicePasdevPathWithContainerName(String name){
		name = name.replaceAll("_", "-");
		name = name.replaceAll("pascloud", "pas-cloud");
		String path = Constants.PASCLOUD_HOME+name+"/data/pasplus/config/";
		return path;
	}
	
	public String getServiceConfPathWithContainerName(String name){
		name = name.replaceAll("_", "-");
		name = name.replaceAll("pascloud", "pas-cloud");
		String path = Constants.PASCLOUD_HOME+name+"/conf/";
		return path;
	}
	/**
	 * 添加服务
	 * @param ip
	 * @param type
	 * @param service
	 * @return
	 */
	public Boolean addPasService(String ip,Integer type,String service){
		Boolean flag = false;
		
		switch(type){
		case 1:
			log.info("添加paspm");
			flag = copyPaspmServiceContainer(ip,"8202","8212");
			break;
		case 2:
			log.info("添加demo");
			flag = copyMainServiceContainer(ip,"8201","8211");
			break;
		case 3:
			log.info("添加redis");
			flag = addRedisContainer(ip,service);
			break;
		case 4:
			log.info("添加mycat");
			flag = addMycatContainer(ip,service);
			break;
		case 5:
			log.info("添加mq");
			flag = addMQContainer(ip,service);
			break;
		case 6:
			log.info("添加tomcat");
			flag = addtomcatContainer(ip,service);
			break;
		case 7:
			log.info("添加zk");
			flag = addZookeeperContainer(ip,service);
			break;
		case 8:
			log.info("添加mysql");
			flag = addMysqlContainer(ip,service);
			break;
		default:
			log.info("没有服务可以创建");
			break;
		}
		return flag;
	}
	
	public Boolean checkImageExist(String ip,String name){
		log.info("检查镜像");
		Boolean flag = false;
		List<ImageVo> images = new ArrayList<ImageVo>();
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		
		if(null!=client){
			images = m_dockerService.getImageByName(client, name);
			if(images.size()>0){
				for(ImageVo vo:images){
					log.info(vo.getRepoTags());
				}
				flag = true;
				log.info(name+"已经存在");
			}
		}
		
		return flag;
	}
	
	public Boolean copyMainServiceContainer(String ip,String servicePort,String restPort){
        //String ip = "192.168.0.7";
        Boolean flag = false;
		log.info("设置服务参数");
        String randomId = RandomUtils.generateLowerString(6);
        String containerName = "pascloud_service_demo_"+randomId;
        String basePath = Constants.PASCLOUD_HOME+Constants.PASCLOUD_SERVICE_DEMO;
		String bindVolumeFrom =basePath+"-"+randomId;
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {bindVolumeFrom+"/bin/start.sh"};
		String imageName = "pascloud/jdk7:v1.0";
		
		if(!checkImageExist(ip,imageName)){
			return flag;
		}
		
		Map<String,String> port = new HashMap<String,String>();
		port.put("8201", servicePort);
		port.put("8211", restPort);
		List<String> envs = new ArrayList<>();
		Connection conn = null;
		try{
			ServerVo vo = m_serverService.getByIP(ip);
			if(null!=vo){
				
				conn = getScpClientConn(vo.getIp(),vo.getUsername(),vo.getPassword());
				log.info("开始拷贝服务源码目录");
				copyFolder(conn,basePath,bindVolumeFrom);
				log.info("结束拷贝源码目录");
				//dubbo.properties
				log.info("上传dubbo.properties文件");
				String dubbofilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+
						m_config.getPASCLOUD_SERVICE_DIR()+File.separator+"dubbo.properties";
				m_configService.setApplicationName(containerName);
				putFileToServer(conn,dubbofilepath, bindVolumeFrom+"/conf/");
				log.info("上传config.properties文件");
				String configfilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ 
						m_config.getPASCLOUD_SERVICE_DIR()+File.separator+"config.properties";
				//这个目录暂时不能设置为TRUE，因为DOCKER无法访问外面宿主的目录 
				m_configService.setHomePath(bindVolumeFrom);
				m_configService.setDev("false");
				putFileToServer(conn,configfilepath, bindVolumeFrom+"/conf/");
				
				log.info("开始新建main容器");
				DefaultDockerClient client = DefaultDockerClient.builder()
						.uri("http://"+ip+":"+defaultPort).build();
				id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
				
				log.info("结束新建main容器");
				flag = true;
			}
		}catch(Exception e){
			log.info("复制服务异常");
			log.error(e.getMessage());
		}finally{
			conn.close();
		}
		return flag;
	}
	
	
	public Boolean copyPaspmServiceContainer(String ip,String servicePort,String restPort){

		Boolean flag = false;
        String randomId = RandomUtils.generateLowerString(6);
		
        String containerName = "pascloud_service_paspm_"+randomId;
        String basePath = Constants.PASCLOUD_HOME+Constants.PASCLOUD_SERVICE_PASPM;
		String bindVolumeFrom =basePath+ "-"+randomId;
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {bindVolumeFrom+"/bin/start.sh"};
		String imageName = "pascloud/jdk7:v1.0";
		
		if(!checkImageExist(ip,imageName)){
			return flag;
		}
		
		Map<String,String> port = new HashMap<String,String>();
		port.put("8202", servicePort);
		port.put("8212", restPort);
		List<String> envs = new ArrayList<>();
		
		Connection conn = null;
		try{
			ServerVo vo = m_serverService.getByIP(ip);
			if(null!=vo){
				
				conn = getScpClientConn(vo.getIp(),vo.getUsername(),vo.getPassword());
				log.info("开始拷贝服务源码目录");
				copyFolder(conn,basePath,bindVolumeFrom);
				log.info("结束拷贝源码目录");
				//dubbo.properties
				log.info("上传dubbo.properties文件");
				String dubbofilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+
						m_config.getPASCLOUD_SERVICE_DIR()+File.separator+"dubbo.properties";
				m_configService.setApplicationName(containerName);
				putFileToServer(conn,dubbofilepath, bindVolumeFrom+"/conf/");
				log.info("上传config.properties文件");
				String configfilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ 
						m_config.getPASCLOUD_SERVICE_DIR()+File.separator+"config.properties";

				m_configService.setHomePath(Constants.PASCLOUD_HOME+Constants.PASCLOUD_SERVICE_DEMO);
				m_configService.setDev("true");
				putFileToServer(conn,configfilepath, bindVolumeFrom+"/conf/");
				log.info("开始新建main容器");
				DefaultDockerClient client = DefaultDockerClient.builder()
						.uri("http://"+ip+":"+defaultPort).build();
				id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
				
				log.info("结束新建main容器");
				flag = true;
			}
		}catch(Exception e){
			log.info("复制服务异常");
			log.error(e.getMessage());
		}finally{
			conn.close();
		}
		return flag;
	}
	
	public Boolean addRedisContainer(String ip,String service){
		Boolean flag = false;
		//String containerName = "pascloud_redis";
		String containerName = service;
		String bindVolumeFrom = "";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {};
		String imageName = "redis:latest";
		
		if(!checkImageExist(ip,imageName)){
			return flag;
		}
		
		Map<String,String> port = new HashMap<String,String>();
		port.put("6379", "6379");
		List<String> envs = new ArrayList<>();
        log.info("开始新建reids容器");
		
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
		//System.out.println(id);
		if(!id.equals("")){
			flag = true;
		}
		log.info("结束新建reids容器");
		return flag;
	}
	
	public Boolean addMysqlContainer(String ip,String service){
		Boolean flag = false;
		//String containerName = "pascloud_redis";
		String containerName = service;
		String bindVolumeFrom = "";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {};
		String imageName = "mysql:5.7";
		
		if(!checkImageExist(ip,imageName)){
			return flag;
		}
		Map<String,String> port = new HashMap<String,String>();
		port.put("3306", "3306");
		List<String> envs = new ArrayList<>();
		envs.add("MYSQL_ROOT_PASSWORD=root");
        log.info("开始新建mysql容器");
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
		//System.out.println(id);
		if(!id.equals("")){
			flag = true;
		}
		log.info("结束新建mysql容器");
		return flag;
	}
	
	public Boolean addZookeeperContainer(String ip,String service){
		Boolean flag = false;
		//String containerName = "pascloud_zookeeper_admin";
		String containerName = service;
		String bindVolumeFrom = "";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {};
		String imageName = "pascloud/zk_dubbo:v1.1";
		
		if(!checkImageExist(ip,imageName)){
			return flag;
		}
		
		Map<String,String> port = new HashMap<String,String>();
		port.put("8686", "8686");
		port.put("2181", "2181");
		List<String> envs = new ArrayList<>();
        log.info("开始新建zk容器");
		
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
		if(!id.equals("")){
			flag = true;
		}
		log.info("结束新建zk容器");
		return flag;
	}
	
	public Boolean addMQContainer(String ip,String service){
		//String containerName = "pascloud_activemq";
		Boolean flag = false;
		//String containerName = "pascloud_zookeeper_admin";
		String containerName = service;
		String bindVolumeFrom = "";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {};
		String imageName = "webcenter/activemq:latest";
		
		if(!checkImageExist(ip,imageName)){
			return flag;
		}
		
		Map<String,String> port = new HashMap<String,String>();
		port.put("61616", "61616");
		List<String> envs = new ArrayList<>();
		envs.add("ACTIVEMQ_ADMIN_LOGIN=admin");
		envs.add("ACTIVEMQ_ADMIN_PASSWORD=admin");
        log.info("开始新建mq容器");
		
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+"192.168.0.7"+":"+defaultPort).build();
		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
		if(!id.equals("")){
			flag = true;
		}
		log.info("结束新建mq容器");
		return flag;
	}
	
	public Boolean addMycatContainer(String ip,String service){
		//String containerName = "pascloud_mycat";
		Boolean flag = false;
		//String containerName = "pascloud_zookeeper_admin";
		String containerName = service;
		String bindVolumeFrom = "/home/pascloud/mycat";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {"/home/pascloud/mycat/bin/mycat","console","&"};
		String imageName = "pascloud/jdk7:v1.0";
		
		if(!checkImageExist(ip,imageName)){
			return flag;
		}
		
		Map<String,String> port = new HashMap<String,String>();
		port.put("8066", "8066");
		port.put("9066", "9066");
		List<String> envs = new ArrayList<>();
        log.info("开始新建mycat容器");
		
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+"192.168.0.7"+":"+defaultPort).build();
		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
		if(!id.equals("")){
			flag = true;
		}
		log.info("结束新建mycat容器");
		return flag;
	}
	
	public Boolean addtomcatContainer(String ip,String service){
    	//String containerName = "pascloud_tomcat";
		Boolean flag = false;
		//String containerName = "pascloud_zookeeper_admin";
		String containerName = service;
		String bindVolumeFrom = "/home/pascloud/tomcat";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {"/home/pascloud/tomcat/bin/catalina.sh", "run"};
		String imageName = "pascloud/jdk7:v1.0";
		
		if(!checkImageExist(ip,imageName)){
			return flag;
		}
		
		Map<String,String> port = new HashMap<String,String>();
		port.put("8170", "8170");
		List<String> envs = new ArrayList<>();
        log.info("开始新建tomcat容器");
		
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+"192.168.0.7"+":"+defaultPort).build();
		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
		if(!id.equals("")){
			flag = true;
		}
		log.info("结束新建tomcat容器");
		return flag;
	}
	
	private Boolean copyFolder(Connection conn,String sourceFolder, String targetFolder) {
		Boolean flag = false;
		if (null!=conn) {
			Session session = null;
			InputStream stdout = null;
			BufferedReader br = null;
			try {
				session = conn.openSession();
				session.execCommand("cp -r " + sourceFolder + " " + targetFolder);
				stdout = new StreamGobbler(session.getStdout());
				br = new BufferedReader(new InputStreamReader(stdout));
				while (true) {
					String line = br.readLine();
					if (line == null)
						break;
					System.out.println(line);
				}
				flag = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			} finally {
				try {
					stdout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				session.close();
			}
		} else {
			log.info("ssh登录失败");
		}
		return flag;
		
	}
	
	
	public PasConfigVo getPasConfig(){
		String pasconfigpath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)
				+m_config.getPASCLOUD_PSCONFIG()+"/config.xml";
		PasConfigVo vo = null;
		try{
			log.info("读取"+pasconfigpath);
			File file = new File(pasconfigpath);
			XStream xstream = new XStream(); 
			xstream.alias("config", PasConfigVo.class);
			vo = (PasConfigVo) xstream.fromXML(file);
			
		}catch(Exception e){
			log.info("读取"+pasconfigpath+"异常");
			log.error("读取"+pasconfigpath+"异常");
		}
		return vo;
	}
	
	public List<MapVo> convertPasConfigToList(PasConfigVo config){
		List<MapVo> map = new ArrayList<>();
		if(null!=config){
			ZookeeperVo zk = config.getZookeeper();
			MapVo m1 = new MapVo();
			m1.setKey("zookeeper.ip");
			m1.setValue(zk.getIp());
			m1.setDesc("注册中心的IP地址");
			
			MapVo m2 = new MapVo();
			m2.setKey("zookeeper.port");
			m2.setValue(zk.getPort()+"");
			m2.setDesc("注册中心的端口");
			
			MycatVo mycat  = config.getMycat();
			
			MapVo m3 = new MapVo();
			m3.setKey("mycat.ip");
			m3.setValue(mycat.getIp());
			m3.setDesc("mycat的IP地址");
			
			MapVo m4 = new MapVo();
			m4.setKey("mycat.port");
			m4.setValue(mycat.getPort()+"");
			m4.setDesc("mycat的端口");
			
			RedisVo redis  = config.getRedis();
			
			MapVo m5 = new MapVo();
			m5.setKey("redis.ip");
			m5.setValue(redis.getIp());
			m5.setDesc("redis的IP地址");
			
			MapVo m6 = new MapVo();
			m6.setKey("redis.port");
			m6.setValue(redis.getPort()+"");
			m6.setDesc("redis的端口");
			
			MqVo mq        = config.getMq();
			MapVo m7 = new MapVo();
			m7.setKey("mq.ip");
			m7.setValue(mq.getIp());
			m7.setDesc("mq的IP地址");
			
			MapVo m8 = new MapVo();
			m8.setKey("mq.port");
			m8.setValue(mq.getPort()+"");
			m8.setDesc("mq的端口");
			map.add(m1);
			map.add(m2);
			map.add(m3);
			map.add(m4);
			map.add(m5);
			map.add(m6);
			map.add(m7);
			map.add(m8);
		}
		
		return map;
	}
	
	
	
}
