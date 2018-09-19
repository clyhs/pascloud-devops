package com.pascloud.module.passervice.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import com.pascloud.module.database.service.MysqlService;
import com.pascloud.module.docker.service.ContainerService;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.module.mycat.service.MycatJrdsThread;
import com.pascloud.module.mycat.service.MycatService;
import com.pascloud.module.pasdev.service.PasdevService;
import com.pascloud.module.server.service.ServerService;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.utils.RandomUtils;
import com.pascloud.utils.ScpClientUtils;
import com.pascloud.utils.xml.XmlParser;
import com.pascloud.vo.common.MapVo;
import com.pascloud.vo.database.DBInfo;
import com.pascloud.vo.docker.ContainerVo;
import com.pascloud.vo.docker.ImageVo;
import com.pascloud.vo.pass.MqVo;
import com.pascloud.vo.pass.MycatVo;
import com.pascloud.vo.pass.MysqlVo;
import com.pascloud.vo.pass.PasConfigVo;
import com.pascloud.vo.pass.PasTypeEnum;
import com.pascloud.vo.pass.RedisVo;
import com.pascloud.vo.pass.ZookeeperVo;
import com.pascloud.vo.result.ResultCommon;
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
	private PasdevService    m_pasdevService;
	@Autowired
	private MycatService     m_mycatService;
	@Autowired
	private MysqlService     m_mysqlService;
	
	
	public List<ContainerVo> getContainerWithMainService(){
		List<ContainerVo> containters = new ArrayList<>();
		containters = m_containerService.getContainers(Constants.PASCLOUD_SERVICE_DEMO_CONTAINER);
		return containters;
	}
	
	public List<ContainerVo> getContainerWithPaspmService(){
		List<ContainerVo> containters = new ArrayList<>();
		containters = m_containerService.getContainers(Constants.PASCLOUD_SERVICE_PASPM_CONTAINER);
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
	
	public String getTomcatHomePath(){
		String path = Constants.PASCLOUD_HOME+"tomcat";
		return path;
	}
	
	public String getMycatHomePath(){
		String path = Constants.PASCLOUD_HOME+"mycat";
		return path;
	}
	/**
	 * 添加服务
	 * @param ip
	 * @param type
	 * @param service
	 * @return
	 */
	public ResultCommon addPasService(String ip,Integer type,String service,
			String clientIp){
		Boolean flag = false;
		ResultCommon result = null;
		switch(type){
		case 1:
			log.info("添加paspm");
			result = copyPaspmServiceContainer(ip,"8202","8212");
			break;
		case 2:
			log.info("添加demo");
			result = copyMainServiceContainer(ip,"8201","8211");
			break;
		case 3:
			log.info("添加redis");
			result = addRedisContainer(ip,service);
			break;
		case 4:
			log.info("添加mycat");
			result = addMycatContainer(ip,service);
			break;
		case 5:
			log.info("添加mq");
			result = addMQContainer(ip,service);
			break;
		case 6:
			log.info("添加tomcat");
			result = addTomcatContainer(ip,service);
			break;
		case 7:
			log.info("添加zk");
			result = addZookeeperContainer(ip,service);
			break;
		case 8:
			log.info("添加mysql");
			result = addMysqlContainer(ip,service);
			break;
		case 9:
			log.info("添加ngnix");
			result = addNginxContainer(ip,service,clientIp);
			break;
		default:
			log.info("没有服务可以创建");
			result = new ResultCommon(PasCloudCode.SUCCESS);
			break;
		}
		return result;
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
	
	public Boolean checkDirIsExist(Connection conn,String dirPath){
		Boolean flag = false;
		Session session = null;
		InputStream stdout = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			log.info("检查目录");
			session = conn.openSession();
			session.execCommand(" [ -s "+dirPath+" ] && echo \"true\" || echo \"false\"");
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				} else {
					log.info(line);
					sb.append(line);
				}
			}
			if("true".equals(sb.toString())){
				flag = true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.error("检查目录异常");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			try {
				stdout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			session.close();
		}
		return flag;
	}
	
	public ResultCommon copyMainServiceContainer(String ip,String servicePort,String restPort){
        //String ip = "192.168.0.7";
		ResultCommon result = null;
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
			//return flag;
			result = new ResultCommon(PasCloudCode.ERROR.getCode(),imageName+"镜像不存在");
			return result;
		}
		
		
		
		Map<String,String> port = new HashMap<String,String>();
		port.put("8201", servicePort);
		port.put("8211", restPort);
		List<String> envs = new ArrayList<>();
		Connection conn = null;
		try{
			ServerVo vo = m_serverService.getByIP(ip);
			if(null!=vo){
				//*******复制PAS+文件
				
				
				conn = getScpClientConn(vo.getIp(),vo.getUsername(),vo.getPassword());
				
				if(!checkDirIsExist(conn,basePath)){
					//return flag;
					result = new ResultCommon(PasCloudCode.ERROR.getCode(),basePath+"目录不存在");
					return result;
				}
				
				log.info("上传pas+文件");
				//uploadPasdev(conn,ip);
				log.info("开始拷贝服务源码目录");
				copyFolder(conn,basePath,bindVolumeFrom);
				log.info("结束拷贝源码目录");
				
				uploadDubbofile(conn,bindVolumeFrom,containerName);
				uploadConfigfile(conn,bindVolumeFrom,false,containerName,ip);
				uploadZkfile(conn,bindVolumeFrom,containerName);
				uploadRedisfile(conn,bindVolumeFrom,containerName);
				uploadDbfile(conn,bindVolumeFrom,containerName);
				
				log.info("开始新建main容器");
				DefaultDockerClient client = DefaultDockerClient.builder()
						.uri("http://"+ip+":"+defaultPort).build();
				id = m_dockerService.addContainerWithNet(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs,
						"host");
				
				log.info("结束新建main容器");
				flag = true;
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
		}catch(Exception e){
			log.info("复制服务异常");
			log.error(e.getMessage());
			result = new ResultCommon(PasCloudCode.ERROR);
		}finally{
			conn.close();
		}
		return result;
	}
	
	public ResultCommon copyPaspmServiceContainer(String ip,String servicePort,String restPort){

		ResultCommon result = null;
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
			//return flag;
			result = new ResultCommon(PasCloudCode.ERROR.getCode(),imageName+"镜像不存在");
			return result;
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
				
				if(!checkDirIsExist(conn,basePath)){
					///return flag;
					result = new ResultCommon(PasCloudCode.ERROR.getCode(),basePath+"目录不存在");
					return result;
				}
				
				log.info("开始拷贝服务源码目录");
				copyFolder(conn,basePath,bindVolumeFrom);
				log.info("结束拷贝源码目录");
				uploadDubbofile(conn,bindVolumeFrom,containerName);
				uploadConfigfile(conn,bindVolumeFrom,true,containerName,ip);
				uploadZkfile(conn,bindVolumeFrom,containerName);
				uploadRedisfile(conn,bindVolumeFrom,containerName);
				uploadDbfile(conn,bindVolumeFrom,containerName);
				
				log.info("开始新建main容器");
				DefaultDockerClient client = DefaultDockerClient.builder()
						.uri("http://"+ip+":"+defaultPort).build();
				id = m_dockerService.addContainerWithNet(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs,
						"host");
				
				log.info("结束新建main容器");
				flag = true;
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
		}catch(Exception e){
			log.info("复制服务异常");
			log.error(e.getMessage());
			result = new ResultCommon(PasCloudCode.ERROR);
		}finally{
			conn.close();
		}
		return result;
	}
	
	public Boolean uploadConfigForStart(String ip,PasTypeEnum type,String containerName){
		Boolean flag = false;
		Connection conn = null;
		try{
			String serverPath = getServicePathWithContainerName(containerName);
			ServerVo vo = m_serverService.getByIP(ip);
			if(null!=vo){
				conn = getScpClientConn(vo.getIp(),vo.getUsername(),vo.getPassword());
				if(type.equals(PasTypeEnum.DEMO)){
					uploadDubbofile(conn,serverPath,containerName);
					uploadConfigfile(conn,serverPath,false,containerName,ip);
					uploadZkfile(conn,serverPath,containerName);
					uploadRedisfile(conn,serverPath,containerName);
					uploadDbfile(conn,serverPath,containerName);
					flag = true;
				}else if(type.equals(PasTypeEnum.PASPM)){
					log.info("paspm......");
					uploadDubbofile(conn,serverPath,containerName);
					uploadConfigfile(conn,serverPath,true,containerName,ip);
					uploadZkfile(conn,serverPath,containerName);
					uploadRedisfile(conn,serverPath,containerName);
					uploadDbfile(conn,serverPath,containerName);
					flag = true;
				}
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			conn.close();
		}
		return flag;
	}
	
	/**
	 * 设置并上传dubbo.properties
	 * @param conn
	 * @param serverPath
	 * @param containerName
	 * @return
	 */
	private synchronized Boolean uploadDubbofile(Connection conn,String serverPath,String containerName){
		Boolean flag = false;
		if(null!=conn){
			log.info("上传dubbo.properties文件");
			String dubbofilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+
					m_config.getPASCLOUD_SERVICE_DIR()+File.separator+"dubbo.properties";
			m_configService.setApplicationName(containerName);
			flag = putFileToServer(conn,dubbofilepath, serverPath+"/conf/");
			log.info("上传dubbo.properties文件完成");
		}
		return flag;
	}
	
	/**
	 * 设置并上传config.properties 的主目录和MQ的地址
	 * @param conn
	 * @param serverPath
	 * @param dev
	 * @param containerName
	 * @return
	 */
	private synchronized Boolean uploadConfigfile(Connection conn,String serverPath,Boolean dev,String containerName,String ip){
		Boolean flag = false;
		List<MqVo> mqs = new ArrayList<MqVo>();
		if(null!=conn){
			log.info("上传config.properties文件");
			String configfilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ 
					m_config.getPASCLOUD_SERVICE_DIR()+File.separator+"config.properties";
			//这个目录暂时不能设置为TRUE，因为DOCKER无法访问外面宿主的目录 
			if(dev){
				m_configService.setHomePath(Constants.PASCLOUD_HOME+Constants.PASCLOUD_SERVICE_DEMO);
				m_configService.setDev("true");
				m_configService.setPort("8202", "8212",ip);
			}else{
				m_configService.setHomePath(serverPath);
				m_configService.setDev("true"); ///新版不用本地初始化
				m_configService.setPort("8201", "8211",ip);
				
			}
			
			mqs = getMqServer();
			if(mqs.size()>0){
				MqVo vo = mqs.get(0);
				if(null!=vo){
					m_configService.setMQConfig(vo.getIp(), vo.getPort());
				}
			}
			log.info(configfilepath);
			log.info(serverPath+"/conf/");
			putFileToServer(conn,configfilepath, serverPath+"/conf/");
			log.info("上传config.properties文件完成");
		}
		return flag;
	}
	
	/**
	 * 设置并上传zk.properties的zookeeper的地址
	 * @param conn
	 * @param serverPath
	 * @param containerName
	 * @return
	 */
	private Boolean uploadZkfile(Connection conn,String serverPath,String containerName){
		Boolean flag = false;
		List<ZookeeperVo> zks = new ArrayList<ZookeeperVo>();
		if(null!=conn){
			log.info("上传zk.properties文件");
			String configfilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ 
					m_config.getPASCLOUD_SERVICE_DIR()+File.separator+"zk.properties";
			zks = getZkServer();
			if(zks.size()>0){
				ZookeeperVo vo = zks.get(0);
				if(null!=vo){
					log.info(vo.getIp());
					m_configService.setZookeeperConfig(vo.getIp(), vo.getPort());
				}
			}
			flag = putFileToServer(conn,configfilepath, serverPath+"/conf/");
			log.info("上传zk.properties文件完成");
		}
		return flag;
	}
	
	/**
	 * 设置并上传redis.properties的地址
	 * @param conn
	 * @param serverPath
	 * @param containerName
	 * @return
	 */
	private Boolean uploadRedisfile(Connection conn,String serverPath,String containerName){
		Boolean flag = false;
		List<RedisVo> rediss = new ArrayList<RedisVo>();
		if(null!=conn){
			log.info("上传redis.properties文件");
			String configfilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ 
					m_config.getPASCLOUD_SERVICE_DIR()+File.separator+"redis.properties";
			rediss = getRedisServer();
			if(rediss.size()>0){
				RedisVo vo = rediss.get(0);
				if(null!=vo){
					m_configService.setRedisConfig(vo.getIp(), vo.getPort(), "", "");
				}
			}
			flag = putFileToServer(conn,configfilepath, serverPath+"/conf/");
			log.info("上传redis.properties文件完成");
		}
		return flag;
	}
	
	/**
	 * 设置并上传db.properties dn0的MYSQL地址，和MYCAT的地址
	 * @param conn
	 * @param serverPath
	 * @param containerName
	 * @return
	 */
	private Boolean uploadDbfile(Connection conn,String serverPath,String containerName){
		Boolean flag = false;
		List<MysqlVo> mysql = new ArrayList<MysqlVo>();
		List<MycatVo> mycat = new ArrayList<MycatVo>();
		if(null!=conn){
			log.info("上传db.properties文件");
			String configfilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ 
					m_config.getPASCLOUD_SERVICE_DIR()+File.separator+"db.properties";
			
			mysql = getMysqlServer();
			mycat = getMycatServer();
			
			if(mysql.size()>0){
				MysqlVo myvo = mysql.get(0);
				if(null!=myvo){
					m_configService.setMysqlConfig(myvo.getIp(), myvo.getPort());
				}
			}
			if(mycat.size()>0){
				MycatVo mcvo = mycat.get(0);
				if(null!=mcvo){
					m_configService.setMycatConfig(mcvo.getIp(), "root", "root");
				}
			}
			flag = putFileToServer(conn,configfilepath, serverPath+"/conf/");
			log.info("上传db.properties文件完成");
		}
		return flag;
	}
	
	private Boolean uploadPasdev(Connection conn,String ip){
		Boolean flag = false;
		List<DBInfo> dbs = m_configService.getDBFromConfig();
		if(dbs.size()>0){
			for(DBInfo db:dbs){
				log.info("uploadPasdev="+db.getId());
				m_pasdevService.uploadPasfileWithIDToServerWithIp(db.getId(),ip);
			}
			flag = true;
		}
		return flag;
	}
	
	public Boolean uploadTomcatfile(String ip){
		Boolean flag = false;
		Connection conn = null;
		try{
			ServerVo vo = m_serverService.getByIP(ip);
			if(null!=vo){
				conn = getScpClientConn(vo.getIp(),vo.getUsername(),vo.getPassword());
				flag = uploadTomcatfile(conn);
			}
			
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			conn.close();
		}
		return flag;
	}
	
	public Boolean createDataAndImp(String ip){
		Boolean flag = false;
		String sqlPath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYSQL()+"/pascloud.sql";
		String funPath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYSQL()+"/functions.sql";
		log.info(sqlPath);
		List<MysqlVo> mysqls = new ArrayList<MysqlVo>();
		mysqls = getMysqlServer();
		if(mysqls.size()>0){
			MysqlVo vo = mysqls.get(0);
			if(null!=vo){
				log.info(vo.getIp());
				flag =m_mysqlService.createDatabaseAndImport("mysql", vo, sqlPath, funPath);
			}
		}
		return flag;
	}
	
	private Boolean uploadTomcatfile(Connection conn){
		Boolean flag = false;
		List<ZookeeperVo> zks = new ArrayList<ZookeeperVo>();
		List<RedisVo> rediss = new ArrayList<RedisVo>();
		if(null!=conn){
			log.info("上传tomcat config.properties文件");
			String configfilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+
					m_config.getPASCLOUD_TOMCAT()+File.separator+"config.properties";
			log.info(configfilepath);
			
			String tomcatConfigPath = getTomcatHomePath()+"/webapps/ROOT/WEB-INF/classes/";
			log.info(tomcatConfigPath);
			zks = getZkServer();
			rediss = getRedisServer();
			if(zks.size()>0 && rediss.size()>0){
				ZookeeperVo vo = zks.get(0);
				RedisVo redis = rediss.get(0);
				if(null!=vo && null!=redis){
					m_configService.setTomcatConfig(redis, vo);
				}
			}
			flag = putFileToServer(conn,configfilepath, tomcatConfigPath);
			log.info("上传tomcat config.properties文件完成");
		}
		return flag;
	}
	
	private Boolean uploadNginxfile(Connection conn,String clientIp){
		Boolean flag = false;
		if(null!=conn){
			log.info("上传nginx.conf文件");
			String configfilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+
					m_config.getPASCLOUD_NGINX()+File.separator+"nginx.conf";
			log.info(configfilepath);
			File f = new File(configfilepath);
			
			
			String configfiledir = Constants.PASCLOUD_HOME+"nginx/";
			log.info(configfiledir);
			
			if(!checkDirIsExist(conn,configfiledir)){
				//return flag;
				flag = execCommand(conn,"mkdir "+configfiledir);
			}else{
				flag = execCommand(conn,"rm -rf "+configfiledir+"nginx.conf");
			}
			
			if(flag){
				log.info("修改nginx:"+clientIp);
				flag = uploadNginxfileForWrite(configfilepath,clientIp);
			}else{
				flag = false;
			}
			if(flag){
				log.info("上传nginx:"+clientIp);
				flag = putFileToServer(conn,configfilepath, configfiledir);
			}else{
				flag =false;
			}
			//server @@:8170 max_fails=3 weight=3 fail_timeout=30s;
			
			log.info("上传nginx.conf文件完成");
		}
		return flag;
	}
	
	private Boolean uploadNginxfileForWrite(String filepath,String clientIP){
		Boolean flag = false;
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		InputStream input = null;
		BufferedReader bufferedReader2 = null;
		FileOutputStream fos = null;
		try{
			String ip = clientIP;
			String line="        server "+ip+":8170 max_fails=3 weight=3 fail_timeout=30s;";
			File file = new File(filepath);
			StringBuffer sb = new StringBuffer();
			StringBuffer sb2 = new StringBuffer();
			Integer start = 0;
			Integer end = 0;
			read = new InputStreamReader(new FileInputStream(file));
	        bufferedReader = new BufferedReader(read);
	        String lineTxt = null;
	        int i=0;
	        while ((lineTxt = bufferedReader.readLine()) != null) {
	        	sb.append(lineTxt).append("\n");
	        	if (lineTxt.contains("#@@#")){
	        		if(start == 0){
	        			start = i;
	        		}
	        		else{
	        			end = i;
	        		}
	            }
	        	i++;
	        }
	        //System.out.println(sb.toString());
	        if(start>0 && end>start){
	        	input = new ByteArrayInputStream(sb.toString().getBytes());
				bufferedReader2 = new BufferedReader(new InputStreamReader(input));
				int j=0;
				String line1 = null;
				while ((line1 = bufferedReader2.readLine())!=null) {
					if(j<start || j>end){
						sb2.append(line1).append("\n");
					}else{
						if(!flag){
							sb2.append("        #@@#").append("\n")
							.append(line).append("\n")
							.append("        #@@#").append("\n");
							flag = true;
						}
					}
					j++;
					
				}
	        }
	        log.info(sb2.toString());
	        //fos = new FileOutputStream(filepath);
	        
	        if(flag){
	        	fos = new FileOutputStream(file);
	            
	            fos.write(sb2.toString().getBytes());
	            fos.close();
	        }
	        
	        
		}catch(IOException e){
			log.error(e.getMessage());
		}finally{
			try {
				if(null!=fos){
					fos.close();
					fos = null;
				}
				if(null!=bufferedReader2){
					bufferedReader2.close();
					bufferedReader2 = null;
				}
				
				if(null!=input){
					input.close();
					input = null;
				}
				
				if(null!=bufferedReader){
					bufferedReader.close();
					bufferedReader = null;
				}
				if(null!=read){
					read.close();
					read = null;
				}
			} catch (IOException e) {
			}
		}
		return flag;
	}
	
	public Boolean uploadMycatfile(String ip){
		Boolean flag = false;
		Connection conn = null;
		try{
			ServerVo vo = m_serverService.getByIP(ip);
			if(null!=vo){
				conn = getScpClientConn(vo.getIp(),vo.getUsername(),vo.getPassword());
				flag = uploadMycatConfig(conn);
				MycatJrdsThread t = new MycatJrdsThread(vo.getIp(),m_config);
				t.start();
				
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			conn.close();
		}
		return flag;
	}
	
	private Boolean uploadMycatConfig(Connection conn){
		Boolean flag = false;
		List<MysqlVo> mysqls = new ArrayList<MysqlVo>();
		if(null!=conn){
			log.info("上传mycat schema.xml文件");
			String configfilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+
					m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SCHEMA;
			String serverfilepath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SCHEMA;
			
			log.info(configfilepath);
			String mycatConfigPath = getMycatHomePath()+"/conf/";
			log.info(mycatConfigPath);
			mysqls = getMysqlServer();
			if(mysqls.size()>0){
				
				MysqlVo vo = mysqls.get(0);
				if(null!=vo){
					m_mycatService.setDataHostWithDn0(vo.getIp(), "pascloud", vo.getPort(), "root", "root");
				}
				/*
				for(MysqlVo vo:mysqls){
					m_mycatService.setDataHostWithDn0(vo.getIp(), "pascloud", vo.getPort(), "root", "root");
				}*/
			}
			flag = putFileToServer(conn,configfilepath, mycatConfigPath);
			flag = putFileToServer(conn,serverfilepath, mycatConfigPath);
		}
		return flag;
	}
	
	
	
	
	public ResultCommon addRedisContainer(String ip,String service){
		Boolean flag = false;
		ResultCommon result=null;
		//String containerName = "pascloud_redis";
		String containerName = service;
		String bindVolumeFrom = "";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {};
		String imageName = "redis:latest";
		
		if(!checkImageExist(ip,imageName)){
			//return flag;
			result = new ResultCommon(PasCloudCode.ERROR.getCode(),imageName+"镜像不存在");
			return result;
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
		result = new ResultCommon(PasCloudCode.SUCCESS);
		return result;
		//return flag;
	}
	
	public ResultCommon addMysqlContainer(String ip,String service){
		Boolean flag = false;
		ResultCommon result=null;
		//String containerName = "pascloud_redis";
		String containerName = service;
		String bindVolumeFrom = "";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {"--lower_case_table_names=1"};
		String imageName = "mysql:5.7";
		
		if(!checkImageExist(ip,imageName)){
			result = new ResultCommon(PasCloudCode.ERROR.getCode(),imageName+"镜像不存在");
			return result;
		}
		Map<String,String> port = new HashMap<String,String>();
		port.put("3306", "3306");
		List<String> envs = new ArrayList<>();
		envs.add("MYSQL_ROOT_PASSWORD=root");
		//envs.add("lower_case_table_names=1");
        log.info("开始新建mysql容器");
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
		//System.out.println(id);
		if(!id.equals("")){
			flag = true;
		}
		log.info("结束新建mysql容器");
		
		
		
		result = new ResultCommon(PasCloudCode.SUCCESS);
		return result;
	}
	
	public ResultCommon addZookeeperContainer(String ip,String service){
		Boolean flag = false;
		//String containerName = "pascloud_zookeeper_admin";
		ResultCommon result=null;
		String containerName = service;
		String bindVolumeFrom = "";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {};
		String imageName = "pascloud/zk_dubbo:v1.1";
		
		if(!checkImageExist(ip,imageName)){
			result = new ResultCommon(PasCloudCode.ERROR.getCode(),imageName+"镜像不存在");
			return result;
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
		result = new ResultCommon(PasCloudCode.SUCCESS);
		return result;
	}
	
	public ResultCommon addMQContainer(String ip,String service){
		//String containerName = "pascloud_activemq";
		Boolean flag = false;
		ResultCommon result=null;
		//String containerName = "pascloud_zookeeper_admin";
		String containerName = service;
		String bindVolumeFrom = "";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {};
		String imageName = "webcenter/activemq:latest";
		
		if(!checkImageExist(ip,imageName)){
			result = new ResultCommon(PasCloudCode.ERROR.getCode(),imageName+"镜像不存在");
			return result;
		}
		
		Map<String,String> port = new HashMap<String,String>();
		port.put("61616", "61616");
		List<String> envs = new ArrayList<>();
		envs.add("ACTIVEMQ_ADMIN_LOGIN=admin");
		envs.add("ACTIVEMQ_ADMIN_PASSWORD=admin");
        log.info("开始新建mq容器");
		
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
		if(!id.equals("")){
			flag = true;
		}
		log.info("结束新建mq容器");
		result = new ResultCommon(PasCloudCode.SUCCESS);
		return result;
	}
	
	public ResultCommon addMycatContainer(String ip,String service){
		//String containerName = "pascloud_mycat";
		Boolean flag = false;
		ResultCommon result=null;
		//String containerName = "pascloud_zookeeper_admin";
		String containerName = service;
		String bindVolumeFrom = Constants.PASCLOUD_HOME+"/mycat";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {Constants.PASCLOUD_HOME+"/mycat/bin/mycat","console","&"};
		String imageName = "pascloud/jdk7:v1.0";
		
		if(!checkImageExist(ip,imageName)){
			result = new ResultCommon(PasCloudCode.ERROR.getCode(),imageName+"镜像不存在");
			return result;
		}
		
		Map<String,String> port = new HashMap<String,String>();
		port.put("8066", "8066");
		port.put("9066", "9066");
		List<String> envs = new ArrayList<>();
		
		Connection conn = null;
		try{
			ServerVo vo = m_serverService.getByIP(ip);
			if(null!=vo){
				conn = getScpClientConn(vo.getIp(),vo.getUsername(),vo.getPassword());
				if(!checkDirIsExist(conn,bindVolumeFrom)){
					result = new ResultCommon(PasCloudCode.ERROR.getCode(),bindVolumeFrom+"目录不存在");
					return result;
				}
				uploadMycatConfig(conn);
			}
			log.info("开始新建mycat容器");
			
			DefaultDockerClient client = DefaultDockerClient.builder()
					.uri("http://"+ip+":"+defaultPort).build();
			id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
			if(!id.equals("")){
				flag = true;
			}
			log.info("结束新建mycat容器");
			result = new ResultCommon(PasCloudCode.SUCCESS);
			
		}catch(Exception e){
			log.error(e.getMessage());
			result = new ResultCommon(PasCloudCode.EXCEPTION);
		}finally{
			conn.close();
		}
		
        
		return result;
	}
	
	public ResultCommon addTomcatContainer(String ip,String service){
    	//String containerName = "pascloud_tomcat";
		Boolean flag = false;
		ResultCommon result=null;
		//String containerName = "pascloud_zookeeper_admin";
		String containerName = service;
		String bindVolumeFrom = Constants.PASCLOUD_HOME+"/tomcat";
		String bindVolumeTo = bindVolumeFrom;
		String id = "";
		String[] cmd = {Constants.PASCLOUD_HOME+"/tomcat/bin/catalina.sh", "run"};
		String imageName = "pascloud/jdk7:v1.0";
		
		if(!checkImageExist(ip,imageName)){
			result = new ResultCommon(PasCloudCode.ERROR.getCode(),imageName+"镜像不存在");
			return result;
		}
		Map<String,String> port = new HashMap<String,String>();
		port.put("8170", "8170");
		List<String> envs = new ArrayList<>();
		
		Connection conn = null;
		try{
			ServerVo vo = m_serverService.getByIP(ip);
			if(null!=vo){
				conn = getScpClientConn(vo.getIp(),vo.getUsername(),vo.getPassword());
				if(!checkDirIsExist(conn,bindVolumeFrom)){
					result = new ResultCommon(PasCloudCode.ERROR.getCode(),bindVolumeFrom+"目录不存在");
					return result;
				}
				uploadTomcatfile(conn);
			}
			log.info("开始新建tomcat容器");
			
			DefaultDockerClient client = DefaultDockerClient.builder()
					.uri("http://"+ip+":"+defaultPort).build();
			id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
			if(!id.equals("")){
				flag = true;
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
			log.info("结束新建tomcat容器");
		}catch(Exception e){
			log.error(e.getMessage());
			result = new ResultCommon(PasCloudCode.EXCEPTION);
		}finally{
			conn.close();
		}
		return result;
	}
	
	public ResultCommon addNginxContainer(String ip,String service,String clientIp){
    	//String containerName = "pascloud_tomcat";
		Boolean flag = false;
		ResultCommon result=null;
		//String containerName = "pascloud_zookeeper_admin";
		String containerName = service;
		String bindVolumeFrom = Constants.PASCLOUD_HOME+"/nginx/nginx.conf";
		String bindVolumeTo = "/etc/nginx/nginx.conf";
		String id = "";
		String[] cmd = {};
		String imageName = "nginx";
		if(!checkImageExist(ip,imageName)){
			result = new ResultCommon(PasCloudCode.ERROR.getCode(),imageName+"镜像不存在");
			return result;
		}
		Map<String,String> port = new HashMap<String,String>();
		port.put("80", "80");
		List<String> envs = new ArrayList<>();
		Connection conn = null;
		try{
			ServerVo vo = m_serverService.getByIP(ip);
			if(null!=vo){
				conn = getScpClientConn(vo.getIp(),vo.getUsername(),vo.getPassword());
				uploadNginxfile(conn,clientIp);
			}
			log.info("开始新建nginx容器");
			
			DefaultDockerClient client = DefaultDockerClient.builder()
					.uri("http://"+ip+":"+defaultPort).build();
			id = m_dockerService.addContainer(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs);
			//id = m_dockerService.addContainerWithNet(client, port, bindVolumeFrom, bindVolumeTo, imageName, containerName,cmd,envs,
					//"host");
			
			if(!id.equals("")){
				flag = true;
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
			log.info("结束新建nginx容器");
		}catch(Exception e){
			log.error(e.getMessage());
			result = new ResultCommon(PasCloudCode.EXCEPTION);
		}finally{
			conn.close();
		}
		return result;
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
	
	public List<ZookeeperVo> getZkServer(){
		List<ZookeeperVo> zks = new ArrayList<ZookeeperVo>();
		List<ContainerVo> containers =m_containerService.getContainers(PasTypeEnum.ZK.getValue());
		if(null!=containers && containers.size()>0){
			for(ContainerVo vo:containers){
				ZookeeperVo v = new ZookeeperVo();
				v.setIp(vo.getIp());
				v.setPort(Integer.valueOf(Constants.PS_ZOOKEEPER_PORT));
				v.setStatus(vo.getState());
				zks.add(v);
			}
		}
		return zks;
	}
	
	public List<RedisVo> getRedisServer(){
		List<RedisVo> rediss = new ArrayList<RedisVo>();
		List<ContainerVo> containers =m_containerService.getContainers(PasTypeEnum.REDIS.getValue());
		if(null!=containers && containers.size()>0){
			for(ContainerVo vo:containers){
				RedisVo v = new RedisVo();
				v.setIp(vo.getIp());
				v.setPort(Integer.valueOf(Constants.PS_REDIS_PORT));
				v.setStatus(vo.getState());
				rediss.add(v);
			}
		}
		return rediss;
	}
	
	public List<MqVo> getMqServer(){
		List<MqVo> mqs = new ArrayList<MqVo>();
		List<ContainerVo> containers =m_containerService.getContainers(PasTypeEnum.MQ.getValue());
		if(null!=containers && containers.size()>0){
			for(ContainerVo vo:containers){
				MqVo v = new MqVo();
				v.setIp(vo.getIp());
				v.setPort(Integer.valueOf(Constants.PS_MQ_PORT));
				v.setStatus(vo.getState());
				mqs.add(v);
			}
		}
		return mqs;
	}
	
	public List<MycatVo> getMycatServer(){
		List<MycatVo> mycats = new ArrayList<MycatVo>();
		List<ContainerVo> containers =m_containerService.getContainers(PasTypeEnum.MYCAT.getValue());
		if(null!=containers && containers.size()>0){
			for(ContainerVo vo:containers){
				MycatVo v = new MycatVo();
				v.setIp(vo.getIp());
				v.setPort(Integer.valueOf(Constants.PS_MYCAT_PORT));
				v.setStatus(vo.getState());
				mycats.add(v);
			}
		}
		return mycats;
	}
	
	public List<MysqlVo> getMysqlServer(){
		List<MysqlVo> mysqls = new ArrayList<MysqlVo>();
		List<ContainerVo> containers =m_containerService.getContainers(PasTypeEnum.MYSQL.getValue());
		if(null!=containers && containers.size()>0){
			for(ContainerVo vo:containers){
				MysqlVo v = new MysqlVo();
				v.setIp(vo.getIp());
				v.setPort(Integer.valueOf(Constants.PS_MYSQL_PORT));
				v.setStatus(vo.getState());
				v.setUsername("root");
				v.setPassword("root");
				
				mysqls.add(v);
			}
		}
		return mysqls;
	}
	
	public Boolean restartPasService(){
		log.info("重启服务");
		Boolean flag = false;
		List<ContainerVo> containers =m_containerService.getContainers("pascloud_service");
		String status="";
		if(null!=containers && containers.size()>0){
			for(ContainerVo vo:containers){
				DefaultDockerClient client = DefaultDockerClient.builder()
						.uri("http://"+vo.getIp()+":"+defaultPort).build();
				log.info("重启服务"+vo.getName());
				status = m_dockerService.restartContainer(client, vo.getId());
			}
			flag = true;
		}
		log.info("重启服务完成");
		return flag;
	}
	
	private List<MapVo> convertZKsToList(){
		List<MapVo> map = new ArrayList<>();
		List<ZookeeperVo> zks = getZkServer();
		MapVo m1 = new MapVo();
		MapVo m2 = new MapVo();
		if(zks.size()>0){
			ZookeeperVo zk = zks.get(0);
			m1.setKey("zookeeper.ip");
			m1.setValue(zk.getIp());
			m1.setDesc("注册中心的IP地址");
			m2.setKey("zookeeper.port");
			m2.setValue(zk.getPort()+"");
			m2.setDesc("注册中心的端口");
		}else{
			m1.setKey("zookeeper.ip");
			m1.setValue("");
			m1.setDesc("注册中心的IP地址");
			m2.setKey("zookeeper.port");
			m2.setValue("");
			m2.setDesc("注册中心的端口");
			
		}
		map.add(m1);
		map.add(m2);
		return map;
		
	}
	
	private List<MapVo> convertRedisToList(){
		List<MapVo> map = new ArrayList<>();
		List<RedisVo> rediss = getRedisServer();
		MapVo m1 = new MapVo();
		MapVo m2 = new MapVo();
		if(rediss.size()>0){
			RedisVo redis = rediss.get(0);
			m1.setKey("redis.ip");
			m1.setValue(redis.getIp());
			m1.setDesc("redis的IP地址");
			m2.setKey("redis.port");
			m2.setValue(redis.getPort()+"");
			m2.setDesc("redis的端口");
		}else{
			m1.setKey("redis.ip");
			m1.setValue("");
			m1.setDesc("redis的IP地址");
			m2.setKey("redis.port");
			m2.setValue("");
			m2.setDesc("redis的端口");
		}
		map.add(m1);
		map.add(m2);
		return map;
		
	}
	
	private List<MapVo> convertMQToList(){
		List<MapVo> map = new ArrayList<>();
		List<MqVo> mqs = getMqServer();
		MapVo m1 = new MapVo();
		MapVo m2 = new MapVo();
		if(mqs.size()>0){
			MqVo mq = mqs.get(0);
			m1.setKey("mq.ip");
			m1.setValue(mq.getIp());
			m1.setDesc("mq的IP地址");
			m2.setKey("mq.port");
			m2.setValue(mq.getPort()+"");
			m2.setDesc("mq的端口");
		}else{
			m1.setKey("mq.ip");
			m1.setValue("");
			m1.setDesc("mq的IP地址");
			m2.setKey("mq.port");
			m2.setValue("");
			m2.setDesc("mq的端口");
		}
		map.add(m1);
		map.add(m2);
		return map;
		
	}
	
	private List<MapVo> convertMycatToList(){
		List<MapVo> map = new ArrayList<>();
		List<MycatVo> mycats = getMycatServer();
		MapVo m1 = new MapVo();
		MapVo m2 = new MapVo();
		if(mycats.size()>0){
			MycatVo mycat = mycats.get(0);
			m1.setKey("mycat.ip");
			m1.setValue(mycat.getIp());
			m1.setDesc("mycat的IP地址");
			m2.setKey("mycat.port");
			m2.setValue(mycat.getPort()+"");
			m2.setDesc("mycat的端口");
		}else{
			m1.setKey("mycat.ip");
			m1.setValue("");
			m1.setDesc("mycat的IP地址");
			m2.setKey("mycat.port");
			m2.setValue("");
			m2.setDesc("mycat的端口");
		}
		map.add(m1);
		map.add(m2);
		return map;
		
	}
	
	private List<MapVo> convertMysqlToList(){
		List<MapVo> map = new ArrayList<>();
		List<MysqlVo> mysqls = getMysqlServer();
		MapVo m1 = new MapVo();
		MapVo m2 = new MapVo();
		if(mysqls.size()>0){
			MysqlVo mysql = mysqls.get(0);
			m1.setKey("mysql.ip");
			m1.setValue(mysql.getIp());
			m1.setDesc("mysql的IP地址");
			m2.setKey("mysql.port");
			m2.setValue(mysql.getPort()+"");
			m2.setDesc("mysql的端口");
		}else{
			m1.setKey("mysql.ip");
			m1.setValue("");
			m1.setDesc("mysql的IP地址");
			m2.setKey("mysql.port");
			m2.setValue("");
			m2.setDesc("mysql的端口");
		}
		map.add(m1);
		map.add(m2);
		return map;
		
	}
	
	public List<MapVo> convertPasConfigToList(){
		List<MapVo> map = new ArrayList<>();
		map.addAll(convertZKsToList());
		map.addAll(convertRedisToList());
		map.addAll(convertMycatToList());
		map.addAll(convertMQToList());
		map.addAll(convertMysqlToList());
		return map;
	}
	
	public ResultCommon checkBaseService(){
		//ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		
		List<ZookeeperVo> zks = getZkServer();
		if(zks.size() == 0){
			return new ResultCommon(PasCloudCode.ERROR.getCode(),"请先开起zookeeper的服务");
		}else{
			ZookeeperVo vo = zks.get(0);
			log.info(vo.getStatus());
			if(!vo.getStatus().equals("running")){
				return new ResultCommon(PasCloudCode.ERROR.getCode(),"请先开起zookeeper的服务");
			}
		}
		List<MycatVo> mycats = getMycatServer();
		if(mycats.size() == 0){
			return new ResultCommon(PasCloudCode.ERROR.getCode(),"请先开起mycat的服务");
		}else{
			MycatVo vo = mycats.get(0);
			log.info(vo.getStatus());
			if(!vo.getStatus().equals("running")){
				return new ResultCommon(PasCloudCode.ERROR.getCode(),"请先开起mycat的服务");
			}
		}
		List<MqVo> mqs = getMqServer();
		if(mqs.size() == 0){
			return new ResultCommon(PasCloudCode.ERROR.getCode(),"请先开起mq的服务");
		}
		else{
			MqVo vo = mqs.get(0);
			log.info(vo.getStatus());
			if(!vo.getStatus().equals("running")){
				return new ResultCommon(PasCloudCode.ERROR.getCode(),"请先开起mq的服务");
			}
		}
		List<RedisVo> rediss = getRedisServer();
		if(rediss.size() == 0){
			return new ResultCommon(PasCloudCode.ERROR.getCode(),"请先开起redis的服务");
		}
		else{
			RedisVo vo = rediss.get(0);
			log.info(vo.getStatus());
			if(!vo.getStatus().equals("running")){
				return new ResultCommon(PasCloudCode.ERROR.getCode(),"请先开起redis的服务");
			}
		}
		
		List<MysqlVo> mysqls = getMysqlServer();
		if(mysqls.size() == 0){
			return new ResultCommon(PasCloudCode.ERROR.getCode(),"请先开起mysql的服务");
		}else{
			MysqlVo vo = mysqls.get(0);
			if(!vo.getStatus().equals("running")){
				return new ResultCommon(PasCloudCode.ERROR.getCode(),"请先开起mysql的服务");
			}
		}
		
		return result;
		
	}
	
	public ResultCommon checkMysqlService(){
		//ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		
		List<MysqlVo> mysqls = getMysqlServer();
		if(mysqls.size() == 0){
			return new ResultCommon(PasCloudCode.ERROR.getCode(),"请先开起mysql的服务");
		}else{
			MysqlVo vo = mysqls.get(0);
			if(!vo.getStatus().equals("running")){
				return new ResultCommon(PasCloudCode.ERROR.getCode(),"请先开起mysql的服务");
			}
		}
		
		return result;
		
	}
	
	public static void main(String[] args) throws IOException{
		String filepath = "D:/nginx.conf";
		String ip = "192.168.0.16";
		String line="        server "+ip+":8170 max_fails=3 weight=3 fail_timeout=30s;";
		File file = new File(filepath);
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();

		Integer start = 0;
		Integer end = 0;
		
		InputStreamReader read = new InputStreamReader(new FileInputStream(file));
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        int i=0;
        while ((lineTxt = bufferedReader.readLine()) != null) {
        	sb.append(lineTxt).append("\n");
        	if (lineTxt.contains("#@@#")){
        		if(start == 0){
        			start = i;
        		}
        		else{
        			end = i;
        		}
            }
        	i++;
        }
        //System.out.println(sb.toString());
        if(start>0 && end>start){
        	InputStream in = new ByteArrayInputStream(sb.toString().getBytes());
			BufferedReader br2 = new BufferedReader(new InputStreamReader(in));
			int j=0;
			Boolean flag = false;
			String line1 = null;
			while ((line1 = br2.readLine())!=null) {
				if(j<start || j>end){
					sb2.append(line1).append("\n");
				}else{
					if(!flag){
						sb2.append("        #@@#").append("\n")
						.append(line).append("\n")
						.append("        #@@#").append("\n");
						flag = true;
					}
				}
				j++;
				
			}
        }
        //System.out.println(sb2.toString());
        
        FileOutputStream fos = new FileOutputStream(filepath);
        
        fos.write(sb2.toString().getBytes());
        fos.close();
        
	}
	
	
}
