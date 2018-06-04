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
import com.pascloud.vo.docker.ContainerVo;
import com.pascloud.vo.server.ServerVo;
import com.spotify.docker.client.DefaultDockerClient;

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
	
	
	
}
