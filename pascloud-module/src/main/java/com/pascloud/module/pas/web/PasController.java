package com.pascloud.module.pas.web;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.bean.docker.ContainerVo;
import com.pascloud.bean.docker.NodeVo;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.module.pas.service.PasService;
import com.pascloud.utils.DBUtils;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.ScpClientUtils;
import com.pascloud.utils.xml.SpringXmlUtils;
import com.pascloud.utils.xml.XmlParser;
import com.pascloud.vo.common.DbInfoVo;
import com.pascloud.vo.result.ResultCommon;
import com.spotify.docker.client.DefaultDockerClient;

@Controller
@RequestMapping("module/pas")
public class PasController extends BaseController {
	
	@Autowired
	private DockerService m_dockerService;
	@Autowired
	private PasService    m_pasService;
	@Autowired
	private PasCloudConfig m_config;
	
	@RequestMapping("index.html")
	public String index(){
		return "pas/index";
	}
	
	@RequestMapping("containers.json")
	@ResponseBody
	public List<ContainerVo> getContainers(){
		List<ContainerVo> containers = new ArrayList<>();
		//containers = m_dockerService.getContainer(dockerClient);
		List<NodeVo> nodes = new ArrayList<>();
		
		nodes = m_dockerService.getNodes(dockerClient);
		
		for(NodeVo vo: nodes){
			DefaultDockerClient client = DefaultDockerClient.builder()
					.uri("http://"+vo.getAddr()+":"+defaultPort).build();
			List<ContainerVo> subContainers = m_dockerService.getContainer(client);
			if(null != subContainers && subContainers.size()>0){
				containers.addAll(subContainers);
			}
		}
		
		return containers;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="addContainer.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon addContainer(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip,
			@RequestParam(value="port",defaultValue="",required=true) String port,
			@RequestParam(value="name",defaultValue="",required=true) String name,
			@RequestParam(value="imageName",defaultValue="tomcat:7.0.82-jre8",required=true) String imageName){
		ResultCommon result = new ResultCommon(10000,"成功");
		
		String containerName = "paspb_"+name;
		String bindVolumeFrom = "/home/"+containerName;
		String id = "";
		
		Map param = new HashMap();
		param.put("ip", ip);
		param.put("port", port);
		param.put("name", name);
		param.put("imageName", imageName);
		
		log.info("开始拷贝源码目录");
		
		ScpClientUtils scpClient = new ScpClientUtils(ip, "root", "tccp@2012");
		scpClient.copyFolder(sourceFolder, bindVolumeFrom);
		scpClient.close();
		
		log.info("结束拷贝源码目录");
		
		log.info("开始新建容器");
		
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		id = m_dockerService.addContainer(client, port, bindVolumeFrom, "", imageName, containerName);
		System.out.println(id);
		
		log.info("结束新建容器");
		log.info(param.toString());
		
		return result;
	}
	
	@RequestMapping(value="startContainer.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon startContainer(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip){
		
		ResultCommon result = new ResultCommon(10000,"成功");
		String containerId = request.getParameter("containerId");
		String status = "";
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		status = m_dockerService.startContainer(client, containerId);
		System.out.println(status);
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
		
		ResultCommon result = new ResultCommon(10000,"成功");
		String containerId = request.getParameter("containerId");
		String status = "";
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		status = m_dockerService.stopContainer(client, containerId);
		System.out.println(status);
		return result;
	}
	
	@RequestMapping(value="restartContainer.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon restartContainer(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip){
		
		ResultCommon result = new ResultCommon(10000,"成功");
		String containerId = request.getParameter("containerId");
		String status = "";
		DefaultDockerClient client = DefaultDockerClient.builder()
				.uri("http://"+ip+":"+defaultPort).build();
		status = m_dockerService.restartContainer(client, containerId);
		System.out.println(status);
		return result;
	}
	@RequestMapping(value="readContainerSpringXml.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon readContainerSpringXml(HttpServletRequest request,
			@RequestParam(value="name",defaultValue="",required=true) String name,
			@RequestParam(value="addr",defaultValue="",required=true) String addr){
		ResultCommon result = new ResultCommon(10000,"成功");
		String res = m_pasService.readSpringXml(name, addr);
		result.setDesc(res);
		return result;
	}
	
	@RequestMapping(value="modifyContainerSpringXml.json",method=RequestMethod.POST)
	@ResponseBody
	public ResultCommon modifyContainerSpringXml(HttpServletRequest request,
			@RequestParam(value="name",defaultValue="",required=true) String name,
			@RequestParam(value="addr",defaultValue="",required=true) String addr,
			@RequestParam(value="driverClass",defaultValue="",required=true) String driverClass,
			@RequestParam(value="url",defaultValue="",required=true) String url,
			@RequestParam(value="username",defaultValue="",required=true) String username,
			@RequestParam(value="password",defaultValue="",required=true) String password){
		ResultCommon result = new ResultCommon(10000,"成功");
		
		System.out.println(m_config.getPASCLOUD_SPRINGXML_PATH());
		
		String config_dir = m_config.getPASCLOUD_SPRINGXML_DIR();
		String newfiledir = config_dir+"/"+name;
		String tmpfilepath = m_config.getPASCLOUD_SPRINGXML_PATH();
		String newfilepath = newfiledir+"/applicationContext_resources.xml";
		
		String serverPath  = "/home/"+name+"/pas_db2/WEB-INF/classes/";
		
		if( !FileUtils.isFileExists(newfiledir) ){
			FileUtils.createOrExistsDir(newfiledir);
		}
		
		Document doc = XmlParser.getDocument(tmpfilepath);
		Element  datasource = SpringXmlUtils.getElement(doc, "dataSource");
		Element  eDriver = SpringXmlUtils.getChildElement(datasource, "driverClassName");
		Element  eUrl = SpringXmlUtils.getChildElement(datasource, "url");
		Element  eUsername = SpringXmlUtils.getChildElement(datasource, "username");
		Element  ePassword = SpringXmlUtils.getChildElement(datasource, "password");
		
		SpringXmlUtils.modifyAttribute(eDriver, "value", driverClass);
		SpringXmlUtils.modifyAttribute(eUrl, "value", url);
		SpringXmlUtils.modifyAttribute(eUsername, "value", username);
		SpringXmlUtils.modifyAttribute(ePassword, "value", password);
		
		System.out.println(driverClass);
		SpringXmlUtils.wirteXml(newfilepath, doc);
		
		ScpClientUtils scpClient = new ScpClientUtils(addr, "root", "tccp@2012");
		scpClient.putFileToServer(newfilepath, serverPath);
		scpClient.close();
		return result;
	}
	
	@RequestMapping(value="getDbInfo.json",method=RequestMethod.GET)
	@ResponseBody
	public List<DbInfoVo> getDbInfo(HttpServletRequest request,
			@RequestParam(value="name",defaultValue="",required=true) String name,
			@RequestParam(value="addr",defaultValue="",required=true) String addr){
		List<DbInfoVo> result = new ArrayList<>();
		
		//String serverPath  = "/home/"+name+"/pas_db2/WEB-INF/classes/applicationContext_resources.xml";
		
		String res = m_pasService.readSpringXml(name, addr);
		SAXReader reader = new SAXReader(); 
		reader.setEncoding("utf-8");
		InputStream in = new ByteArrayInputStream(res.getBytes());
		try {
			Document doc = reader.read(in);
			Element  datasource = SpringXmlUtils.getElement(doc, "dataSource");
			Element  eDriver = SpringXmlUtils.getChildElement(datasource, "driverClassName");
			Element  eUrl = SpringXmlUtils.getChildElement(datasource, "url");
			Element  eUsername = SpringXmlUtils.getChildElement(datasource, "username");
			Element  ePassword = SpringXmlUtils.getChildElement(datasource, "password");
			
			String driverClass = SpringXmlUtils.getAttributeValue(eDriver, "value");
			String url = SpringXmlUtils.getAttributeValue(eUrl, "value");
			String username = SpringXmlUtils.getAttributeValue(eUsername, "value");
			String password = SpringXmlUtils.getAttributeValue(ePassword, "value");
			
			DbInfoVo vo1 = new DbInfoVo();
			vo1.setKey("数据库驱动");
			vo1.setValue(driverClass);
			
			DbInfoVo vo2 = new DbInfoVo();
			vo2.setKey("数据库url");
			vo2.setValue(url);
			
			DbInfoVo vo3 = new DbInfoVo();
			vo3.setKey("用户");
			vo3.setValue(username);
			
			DbInfoVo vo4 = new DbInfoVo();
			vo4.setKey("密码");
			vo4.setValue(password);
			
			result.add(vo1);
			result.add(vo2);
			result.add(vo3);
			result.add(vo4);
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return result;
		
	}
	
	@RequestMapping(value="connectDb.json",method=RequestMethod.GET)
	@ResponseBody
	public ResultCommon connectDb(HttpServletRequest request,
			@RequestParam(value="driverClass",defaultValue="",required=true) String driverClass,
			@RequestParam(value="url",defaultValue="",required=true) String url,
			@RequestParam(value="username",defaultValue="",required=true) String username,
			@RequestParam(value="password",defaultValue="",required=true) String password){
		
		ResultCommon result = null;
		
		DBUtils db = new DBUtils(driverClass,url,username,password);
		
		if(db.canConnect()){
			result = new ResultCommon(10000,"成功");
		}else{
			result = new ResultCommon(20000,"失败");
		}
		
		return result;
		
	}
	
	

}
