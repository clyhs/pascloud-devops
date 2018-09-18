package com.pascloud.module.mycat.service;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.gson.Gson;
import com.pascloud.constant.Constants;
import com.pascloud.module.common.service.AbstractBaseService;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.docker.service.ContainerService;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.module.passervice.service.PasService;
import com.pascloud.module.server.service.ServerService;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.JrdsUtils;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.utils.ScpClientUtils;
import com.pascloud.utils.db.DataSourceUtils;
import com.pascloud.utils.xml.MycatXmlUtils;
import com.pascloud.utils.xml.XmlParser;
import com.pascloud.vo.database.DBColumnVo;
import com.pascloud.vo.docker.ContainerVo;
import com.pascloud.vo.docker.NodeVo;
import com.pascloud.vo.mycat.DataHostVo;
import com.pascloud.vo.mycat.DataNodeVo;
import com.pascloud.vo.mycat.DataSourceVo;
import com.pascloud.vo.pass.MycatVo;
import com.pascloud.vo.pass.MysqlVo;
import com.pascloud.vo.result.ResultListData;
import com.pascloud.vo.server.ServerVo;
import com.spotify.docker.client.DefaultDockerClient;


@Service
public class MycatService extends AbstractBaseService {
	
	private static Logger log = LoggerFactory.getLogger(MycatService.class);
	
	@Autowired
	private PasCloudConfig   m_config;
	@Autowired
	private DockerService    m_dockerService;
	@Autowired
	private ContainerService m_containerService;
	@Autowired
	private ServerService    m_serverService;
	@Autowired
	private PasService       m_pasService;
	
	
	public List<DataHostVo> getDataHosts(){
		
		List<DataHostVo> result = new ArrayList<>();
		
		String mycat_schema_path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SCHEMA;
		Document doc = XmlParser.getDocument(mycat_schema_path);
		Element root = doc.getRootElement();
		List<Element> nodes = root.elements("dataHost");
		
		if(nodes.size()>0){
			Iterator<Element> it = nodes.iterator();
			while(it.hasNext()){
				Element e = it.next();
				DataHostVo vo = new DataHostVo();
				vo.setName(e.attributeValue("name"));
				vo.setDbType(e.attributeValue("dbType"));
				vo.setDbDriver(e.attributeValue("dbDriver"));
				parserWritehost(e,vo);
				
				result.add(vo);
			}
		}
		
		return result;
		
	}
	
	public List<DataNodeVo> getDataNodes(){
		List<DataNodeVo> result = new ArrayList<>();
		String mycat_schema_path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SCHEMA;
		Document doc = XmlParser.getDocument(mycat_schema_path);
		Element root = doc.getRootElement();
		List<Element> nodes = root.elements("dataNode");
		
		if(nodes.size()>0){
			Iterator<Element> it = nodes.iterator();
			while(it.hasNext()){
				Element e = it.next();
				DataNodeVo vo = new DataNodeVo();
				vo.setName(e.attributeValue("name"));
				vo.setDataHost(e.attributeValue("dataHost"));
				vo.setDatabase(e.attributeValue("database"));
				DataHostVo dvo = getDataHostByName(vo.getDataHost(),root);
				if(null != dvo){
					vo.setUrl(dvo.getUrl());
					vo.setPassword(dvo.getPassword());
					vo.setUser(dvo.getUser());
					vo.setDbType(dvo.getDbType());
					vo.setDbDriver(dvo.getDbDriver());
					vo.setPort(parserPort(dvo.getUrl(),dvo.getDbType()));
					vo.setIp(parserIP(dvo.getUrl(),dvo.getDbType()));
				}
				
				result.add(vo);
			}	
		}
		return result;
	}
	
	public synchronized String generateName(){
		String name="";
		List<DataNodeVo> nodes = getDataNodes();
		/*
		int i = 1;
		if(null!=nodes && nodes.size()>0){
			for(DataNodeVo vo:nodes){
				if(vo.getName().equals("dn"+i)){
					
				}else{
					name = "dn"+i;
					break;
				}
				i++;
			}
			if("".equals(name)){
				name = "dn"+(nodes.size()+1);
			}
		}else{
			name = "dn"+i; 
		}*/
		if(null!=nodes && nodes.size()>0){
			for(int i=1;i<=nodes.size();i++){
				String n1 = "dn"+i;
				if(checkName(n1,nodes)){
					continue;
				}else{
					name = n1;
					break;
				}
			}
			if(name.equals("")){
				name = "dn"+(nodes.size()+1);
			}
		}
		
		return name;
	}
	
	private Boolean checkName(String name,List<DataNodeVo> nodes){
		Boolean flag = false;
		if(null!=nodes && nodes.size()>0){
			for(DataNodeVo vo:nodes){
				if(vo.getName().equals(name)){
					flag = true;
				}
			}
		}
		
		
		return flag;
	}
	
	public synchronized Boolean addDatanode(String name,String dbType,String ip,String user,String password,String database,Integer port){
		Boolean flag = false;
		String mycat_schema_path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SCHEMA;
		String mycat_server_path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SERVER;
		log.info("添加节点");
		MycatXmlUtils.addSchemaAndNodeAndHost(mycat_schema_path, name, dbType, ip, user, password, database, port);
		MycatXmlUtils.addServer(mycat_server_path, name);
		//MycatXmlUtils.addSchemaAndNodeAndHost(mycat_schema_path,"dn22", "oracle", "192.168.0.16", "pas", "pas", "cpas", 1521);
		flag = uploadConfigAndRestart(mycat_schema_path,mycat_server_path);
		log.info("添加节点完成");
		return flag;
	 
	}
	
	public Boolean uploadConfigAndRestart(String schemePath,String serverPath){
		Boolean flag = false;
		List<ContainerVo> containers = new ArrayList<>();
		//containers = m_dockerService.getContainer(dockerClient);
		//String mycat_schema_path =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SCHEMA;
		//String mycat_server_path =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SERVER;
	
		containers = m_containerService.getContainers("pascloud_mycat");
		/****上传到复制的项目***/
		String status = "";
		if(containers.size()>0){
			for(ContainerVo vo : containers){
				ServerVo server = m_serverService.getByIP(vo.getIp());
				ch.ethz.ssh2.Connection conn = getScpClientConn(vo.getIp(), server.getUsername(), server.getPassword());
				String mycatConfigPath = m_pasService.getMycatHomePath()+"/conf/";
				log.info("上传mycat schema.xml");
				flag = putFileToServer(conn,schemePath, mycatConfigPath);
				flag = putFileToServer(conn,serverPath, mycatConfigPath);
				log.info("上传mycat schema.xml完成");
				if(flag){
					log.info("重启MYCAT容器");
					String containerId= vo.getId();
					DefaultDockerClient client = DefaultDockerClient.builder()
							.uri("http://"+vo.getIp()+":"+defaultPort).build();
					status = m_dockerService.restartContainer(client, containerId);
					System.out.println(status);
					log.info("重启MYCAT容器完成");
				}else{
					log.info("上传mycat schema.xml失败");
				}
			}
		} 
		return flag;
	}
	
	public synchronized Boolean delDatanode(String name){
		Boolean flag = false;
		String mycat_schema_path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SCHEMA;
		String mycat_server_path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SERVER;
		MycatXmlUtils.delSchemaAndNodeAndHost(mycat_schema_path, name);
		MycatXmlUtils.delServer(mycat_server_path, name);
		
		flag = uploadConfigAndRestart(mycat_schema_path,mycat_server_path);
		return flag;
	}
	
	public synchronized Boolean setDataHostWithDn0(String ip,String database,
			Integer port,String user,String password){
		Boolean flag = false;
		String mycat_server_path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SERVER;
		String mycat_schema_path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SCHEMA;
		flag = MycatXmlUtils.setDataHostWithDn0(mycat_schema_path, ip, database, port, user, password,mycat_server_path);
		return flag;
	}
	
	public synchronized Boolean setDataHostWithDn0(List<MysqlVo> vos){
		Boolean flag = false;
		String mycat_server_path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SERVER;
		String mycat_schema_path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SCHEMA;
		//flag = MycatXmlUtils.setDataHostWithDn0(mycat_schema_path, ip, database, port, user, password,mycat_server_path);
		return flag;
	}
	
	public synchronized Boolean addWriteHost(String dbSchema,String ip,String database,Integer port,String user,
			String password){
		Boolean flag = false;
		String mycat_schema_path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SCHEMA;
		
		
		return flag;
	} 
	
    private List<DataHostVo> getDataHosts(Element root){
		List<DataHostVo> result = new ArrayList<>();
		
		//String mycat_schema_path = m_config.getPASCLOUD_MYCAT_DIR()+File.separator+"schema.xml";
		//Document doc = XmlParser.getDocument(mycat_schema_path);
		//Element root = doc.getRootElement();
		List<Element> nodes = root.elements("dataHost");
		if(nodes.size()>0){
			Iterator<Element> it = nodes.iterator();
			while(it.hasNext()){
				Element e = it.next();
				DataHostVo vo = new DataHostVo();
				vo.setName(e.attributeValue("name"));
				vo.setDbType(e.attributeValue("dbType"));
				vo.setDbDriver(e.attributeValue("dbDriver"));
				parserWritehost(e,vo);
				//vo.setUrl(paserPort(vo.getUrl(),vo.getDbType()));
				result.add(vo);
			}
		}
		
		return result;
		
	}
    
    
    private DataHostVo getDataHostByName(String name,Element root){
		DataHostVo vo = null;
		List<DataHostVo> result = getDataHosts(root);
		if(null != result && result.size()>0){
			for(DataHostVo v : result){
				if(v.getName().equals(name)){
					vo = v;
				}
			}
		}
		return vo;
	}
	
	
	private void parserWritehost(Element e,DataHostVo vo){
		
		//Element children = (Element) e.selectSingleNode("writeHost");
		
		Element children = null;
		
		List<Element> els = e.elements("writeHost");
		if(null!=els && els.size()>0){
			children = els.get(0);
			vo.setUrl(children.attributeValue("url"));
			vo.setPassword(children.attributeValue("password"));
			vo.setUser(children.attributeValue("user"));
		}
		//System.out.println(children.asXML());
	}
	
	private String parserIP(String url,String dbType){
		String ip = "";
		if(url.length()>0){
			if(dbType.equals("mysql")){
				int index = url.lastIndexOf(":");
				//System.out.println(index);
				url = url.substring(0, index);
			}else if(dbType.equals("oracle")){
				int index = url.lastIndexOf("@");
				url = url.substring(index+1,url.length());
				index = url.indexOf(":");
				url = url.substring(0, index);
				//ip = url;
			}else if(dbType.equals("db2")){
				//int index = url.indexOf(":");
				url = url.replace("jdbc:db2://", "");
				url = url.substring(0, url.length());
				int index = url.indexOf(":");
				url = url.substring(0, index);
			}
			ip = url;
			//System.out.println(url);
		}
		return ip;
	}
	
	private String parserPort(String url,String dbType){
		//System.out.println(url+dbType);
		String port = "";
		
		if(url.length()>0){
			if(dbType.equals("mysql")){
				int index = url.lastIndexOf(":");
				//System.out.println(index);
				port = url.substring(index+1,url.length());
			}else if(dbType.equals("oracle")){
				int index = url.lastIndexOf(":");
				url = url.substring(0, index);
				index = url.lastIndexOf(":");
				port = url.substring(index+1,url.length());
			}else if(dbType.equals("db2")){
				int index = url.lastIndexOf("/");
				url = url.substring(0, index);
				index = url.lastIndexOf(":");
				port = url.substring(index+1,url.length());
			}
		}
		return port;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<DataSourceVo> getDataSourceList(DataSource dataSource){
		List<DataSourceVo> result = new ArrayList<>();
		log.info("查询中间件MYCAT的datasource");
		Connection conn = null;
		String sql = "show @@datasource";
		List<MycatVo> mycats = new ArrayList<MycatVo>();
		try {
			mycats = m_pasService.getMycatServer();
			if(mycats.size()>0){
				MycatVo vo = mycats.get(0);
				if(null!=vo && vo.getStatus().equals("running")){
					conn = dataSource.getConnection();
					if(null!=conn){
						QueryRunner qRunner = new QueryRunner();  
						result =  (List<DataSourceVo>)qRunner.query(conn,sql, new BeanListHandler(DataSourceVo.class));
					}
				}else{
					log.info("mycat服务没有启动");
				}
			}else{
				log.info("mycat服务没有创建");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		return result;
	}
	
	public List<DataSourceVo> getDataSource(DefaultDockerClient dockerClient,
			Integer defaultPort){
		
		//ComboPooledDataSource dataSource = new ComboPooledDataSource();
		DruidDataSource dataSource = new DruidDataSource();
		List<ContainerVo> containers = new ArrayList<>();
		List<DataSourceVo> ds = new ArrayList<>();
		
		try {
			containers = m_containerService.getContainers("pascloud_mycat");
			if(null != DataSourceUtils.getDataSource("mycat")){
				dataSource = (DruidDataSource) DataSourceUtils.getDataSource("mycat");
				ds = getDataSourceList(dataSource);
			}else{
				if(null != containers.get(0)){
					ContainerVo vo = containers.get(0);
					Locale.setDefault( Locale.US );
					dataSource.setUsername("root");
					//dataSource.setDataSourceName("alldb");
					//dataSource.setName("alldb");
					dataSource.setUrl("jdbc:mysql://"+vo.getIp()+":9066/alldb");
					dataSource.setPassword("root");
					dataSource.setDriverClassName(Constants.DB_MYSQL_DIRVERCLASS);
					ds = getDataSourceList(dataSource);
					DataSourceUtils.addDataSource("mycat", dataSource);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		return ds;
	}
	
	/**
	 * 
	 * @param ip
	 * @return
	 */
	public Boolean createMycatJrds(String ip){
		
		Boolean flag = false;
		try {

			Map<String, Object> jdbc = new HashMap<>();
			jdbc.put("mangerPort", "9066");
			jdbc.put("ip", ip);
			jdbc.put("username", "root");
			jdbc.put("password", "root");
			jdbc.put("dbName", "alldb");
			
			String jrdsconfg = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT) + "/WEB-INF/jrdsconf/hosts/";
			
			List<File> files = FileUtils.listFilesInDirWithFilter(jrdsconfg, ".xml", false);
			if(null!=files && files.size()>0){
				for(File f:files){
					log.info("删除文件："+f.getAbsolutePath());
					FileUtils.deleteFile(f.getAbsolutePath());
				}
			}
			jrdsconfg = jrdsconfg + "D_" + ip + "_" + jdbc.get("mangerPort") + ".xml";
			log.info("createMycatJrds:"+jrdsconfg);
			String templatepath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+
					m_config.getPASCLOUD_MYCAT()+"/template/mycatjrds.ftl";
			flag = JrdsUtils.getInstance().newJrdsFile(templatepath, jrdsconfg, jdbc);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return flag;
	}
	
	
	
	
	public static void main(String[] args ){
		
		MycatService s = new MycatService();
		
		List<DataNodeVo> result = new ArrayList<>();
		String mycat_schema_path = "D:/eclipse64/devops/pascloud-devops-parent/pascloud-webapps/src/main/webapp/static/resources/mycat/conf/schema.xml";
		Document doc = XmlParser.getDocument(mycat_schema_path);
		Element root = doc.getRootElement();
		List<Element> nodes = root.elements("dataNode");
		
		if(nodes.size()>0){
			Iterator<Element> it = nodes.iterator();
			while(it.hasNext()){
				Element e = it.next();
				DataNodeVo vo = new DataNodeVo();
				vo.setName(e.attributeValue("name"));
				vo.setDataHost(e.attributeValue("dataHost"));
				vo.setDatabase(e.attributeValue("database"));
				DataHostVo dvo = s.getDataHostByName(vo.getDataHost(),root);
				if(null != dvo){
					vo.setUrl(dvo.getUrl());
					vo.setPassword(dvo.getPassword());
					vo.setUser(dvo.getUser());
					vo.setDbType(dvo.getDbType());
					vo.setDbDriver(dvo.getDbDriver());
					vo.setPort(s.parserPort(dvo.getUrl(),dvo.getDbType()));
					vo.setIp(s.parserIP(dvo.getUrl(),dvo.getDbType()));
				}
				
				result.add(vo);
			}	
		}
		
		String name="";
		List<DataNodeVo> nodes1 = result;
		//int i = 1;
		if(null!=nodes && nodes1.size()>0){
			for(int i=1;i<=nodes1.size();i++){
				String n1 = "dn"+i;
				if(s.checkName(n1,nodes1)){
					continue;
				}else{
					name = n1;
					break;
				}
			}
			if(name.equals("")){
				name = "dn"+(nodes1.size()+1);
			}
		}
		System.out.println(name);
	}

}
