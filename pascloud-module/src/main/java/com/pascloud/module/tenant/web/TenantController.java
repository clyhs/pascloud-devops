package com.pascloud.module.tenant.web;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pascloud.bean.docker.ContainerVo;
import com.pascloud.bean.docker.NodeVo;
import com.pascloud.constant.Constants;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.database.service.DataBaseService;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.module.passervice.service.ConfigService;
import com.pascloud.utils.DBUtils;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.utils.ScpClientUtils;
import com.pascloud.vo.database.DBInfo;
import com.pascloud.vo.result.ResultBean;
import com.pascloud.vo.result.ResultCommon;
import com.spotify.docker.client.DefaultDockerClient;

@Controller
@RequestMapping("module/tenant")
public class TenantController extends BaseController {
	
	@Autowired
	private DataBaseService m_dbService;
	
	@Autowired
	private ConfigService m_configService;
	
	@Autowired
	private DockerService m_dockerService;
	
	@Autowired
	private PasCloudConfig m_config;
	
	@RequestMapping("index.html")
	public String index(HttpServletRequest request){
		return "tenant/index";
	}
	
	@RequestMapping("dbs.json")
	@ResponseBody
	public List<DBInfo> getTenantDBs(HttpServletRequest request){
		
		List<DBInfo> result = new ArrayList<>();
		
		result = m_configService.getDBFromConfig();
		
		return result;
		
	}
	
	@RequestMapping("addTenantDB.json")
	@ResponseBody
	public ResultCommon addTenantDB(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip,
			@RequestParam(value="port",defaultValue="",required=true) Integer port,
			@RequestParam(value="name",defaultValue="",required=true) String name,
			@RequestParam(value="dbType",defaultValue="",required=true) String dbType,
			@RequestParam(value="database",defaultValue="",required=true) String database,
			@RequestParam(value="username",defaultValue="",required=true) String user,
			@RequestParam(value="password",defaultValue="",required=true) String password){
		
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		
		m_configService.addDBConfig(ip, port, user, password, dbType, name, database);
		
		return result;
		
	}
	
	@RequestMapping("delTenantDB.json")
	@ResponseBody
	public ResultCommon delTenantDB(HttpServletRequest 
			request,@RequestParam(value="name",defaultValue="",required=true) String name){
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		m_configService.delDBConfig(name);
		return result;
	}
	
	@RequestMapping("uploadConfig.json")
	@ResponseBody
	public ResultCommon uploadConfig(HttpServletRequest 
			request){
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		List<ContainerVo> containers = new ArrayList<>();
		//containers = m_dockerService.getContainer(dockerClient);
		List<NodeVo> nodes = new ArrayList<>();
		
		nodes = m_dockerService.getNodes(getDockerClient());
		/****查询运行的服务***/
		for(NodeVo vo: nodes){
			DefaultDockerClient client = DefaultDockerClient.builder()
					.uri("http://"+vo.getAddr()+":"+defaultPort).build();
			List<ContainerVo> subContainers = m_dockerService.getContainer(client,"pascloud_service");
			if(null != subContainers && subContainers.size()>0){
				containers.addAll(subContainers);
			}
		}
		/****上传到复制的项目***/
		if(containers.size()>0){
			for(ContainerVo vo : containers){
				ScpClientUtils scpClient = new ScpClientUtils(vo.getIp(), "root", "tccp@2012");
				//scpClient.close();
				String dbfilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ m_config.getPASCLOUD_SERVICE_DIR()+File.separator+"db.properties";
				System.out.println(dbfilepath);
				
				String name = vo.getName().replaceAll("_", "-");
				name = name.replaceAll("pascloud", "pas-cloud");
				String path = "/home/pascloud/"+name+"/conf/";
				scpClient.putFileToServer(dbfilepath, path);
				scpClient.close();
			}
		}
		/****上传到源码***/
		ScpClientUtils scpClient = new ScpClientUtils("192.168.0.7", "root", "tccp@2012");
		//scpClient.close();
		String dbfilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ m_config.getPASCLOUD_SERVICE_DIR()+File.separator+"db.properties";
		System.out.println(dbfilepath);
		String demopath = "/home/pascloud/pas-cloud-service-demo/conf/";
		scpClient.putFileToServer(dbfilepath, demopath);
		String paspmpath = "/home/pascloud/pas-cloud-service-paspm/conf/";
		scpClient.putFileToServer(dbfilepath, paspmpath);
		scpClient.close();
		
		return result;
		
	}
	
	@RequestMapping(value = "connectDbWidthUrl.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultBean<Integer> connectDbWithUrl(HttpServletRequest request,
			@RequestParam(value = "id", defaultValue = "", required = true) String id,
			@RequestParam(value = "dbType", defaultValue = "", required = true) String dbType,
			@RequestParam(value = "url", defaultValue = "", required = true) String url,
			@RequestParam(value = "username", defaultValue = "", required = true) String username,
			@RequestParam(value = "password", defaultValue = "", required = true) String password) {

		ResultBean<Integer> result = null;

		String driverClass = DBUtils.getDirverClassName(dbType);
		log.info(url);
		log.info(driverClass);
		DBUtils db = new DBUtils(driverClass, url, username, password);
		Connection conn = null;
		try {
			conn = db.getConnection();
			if (null!=conn) {
				Integer total = -1;
				String tableName = "khdx_hy";
				total = m_dbService.getDataCountsByConn(conn, tableName);
				result = new ResultBean<Integer>(10000, "成功");
				result.setBean(total);
			} else {
				result = new ResultBean<Integer>(20000, "失败");
			}
		} catch (Exception e) {
			result = new ResultBean<Integer>(20000, "失败");
		}

		return result;
	}
	

}
