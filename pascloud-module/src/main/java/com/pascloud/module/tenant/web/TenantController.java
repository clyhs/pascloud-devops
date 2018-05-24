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
import com.pascloud.bean.server.ServerVo;
import com.pascloud.bean.tenant.KhdxHyVo;
import com.pascloud.constant.Constants;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.database.service.DataBaseService;
import com.pascloud.module.docker.service.ContainerService;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.module.passervice.service.ConfigService;
import com.pascloud.module.passervice.service.PasService;
import com.pascloud.module.server.service.ServerService;
import com.pascloud.module.tenant.service.TenantService;
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
	private DataBaseService  m_dbService;
	@Autowired
	private ConfigService    m_configService;
	@Autowired
	private DockerService    m_dockerService;
	@Autowired
	private TenantService    m_tenantService;
	@Autowired
	private ContainerService m_containerService;
	@Autowired
	private ServerService    m_serverService;
	@Autowired
	private PasService       m_pasService;
	@Autowired
	private PasCloudConfig   m_config;
	
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

		if(name.equals(Constants.PASCLOUD_PUBLIC_DB)){
			result = new ResultCommon(20000, "不能删除公共库，请重新选择");
			return result;
		}
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
		containers = m_containerService.getContainers("pascloud_service");
		List<NodeVo> nodes = new ArrayList<>();
		
		/****上传到复制的项目***/
		if(containers.size()>0){
			for(ContainerVo vo : containers){
				ServerVo server = m_serverService.getByIP(vo.getIp());
				ScpClientUtils scpClient = new ScpClientUtils(vo.getIp(), server.getUsername(), server.getPassword());
				//scpClient.close();
				String dbfilepath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ m_config.getPASCLOUD_SERVICE_DIR()+File.separator+"db.properties";
				String path = m_pasService.getServiceConfPathWithContainerName(vo.getName());
				scpClient.putFileToServer(dbfilepath, path);
				
				String demopath = Constants.PASCLOUD_HOME+Constants.PASCLOUD_SERVICE_DEMO+"/conf/";
				scpClient.putFileToServer(dbfilepath, demopath);
				String paspmpath =Constants.PASCLOUD_HOME+Constants.PASCLOUD_SERVICE_PASPM+"/conf/";
				scpClient.putFileToServer(dbfilepath, paspmpath);
				
				scpClient.close();
			}
		}
		
		return result;
		
	}
	/**
	 * 检查租户数据库的健康状况，及查询行员数
	 * @param request
	 * @param id
	 * @param dbType
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
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
	
	@RequestMapping(value = "syscHy.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultCommon syscHy(HttpServletRequest request,
			@RequestParam(value = "id", defaultValue = "", required = true) String id,
			@RequestParam(value = "dbType", defaultValue = "", required = true) String dbType,
			@RequestParam(value = "url", defaultValue = "", required = true) String url,
			@RequestParam(value = "username", defaultValue = "", required = true) String username,
			@RequestParam(value = "password", defaultValue = "", required = true) String password) {

		ResultCommon result = null;
		
		if(id.equals(Constants.PASCLOUD_PUBLIC_DB)){
			result = new ResultCommon(20000, "不能选择公共库进行同步，请重新选择");
			return result;
		}

		String driverClass = DBUtils.getDirverClassName(dbType);
		DBUtils db = new DBUtils(driverClass, url, username, password);
		Connection sourceConn = null;
		Connection dn0Conn = null;
		try {
			sourceConn = db.getConnection();
			if (null!=sourceConn) {
				log.info("获取租户的全部行员数据");
				List<KhdxHyVo> lists = m_tenantService.getAllHy(sourceConn);
				dn0Conn = getPublicDB();
				if(null!=dn0Conn && lists.size()>0){
					log.info("清除租户在公共库的全部行员数据");
					m_tenantService.deleteHyWithPublicDB(dn0Conn, id);
					log.info("导入租户全部行员数据到公共库");
					m_tenantService.addHyWithPublicDB(dn0Conn, id, lists);
					log.info("导入租户全部行员数据到公共库完成");
					result = new ResultCommon(10000, "同步成功");
				}else{

					result = new ResultCommon(20000, "公共库连接失败或者租户的行员数为空");
				}
			} else {
				result = new ResultCommon(20000, "数据库连接失败");
			}
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
			result = new ResultBean<Integer>(20000, "同步异步，请联系管理员");
		}

		return result;
	}
	
	private Connection getPublicDB(){
		Connection conn = null;
        List<DBInfo> result = new ArrayList<>();
        DBInfo dn0 = null;
		try {
			result = m_configService.getDBFromConfig();
			if(result.size()>0){
				for(DBInfo d:result){
					if(d.getId().equals("dn0")){
						dn0 = d;
					}
				}
			}
			if(null!=dn0){
				String driverClass = DBUtils.getDirverClassName(dn0.getDbType());
				DBUtils db = new DBUtils(driverClass, dn0.getUrl(), dn0.getUsername(), dn0.getPassword());
				conn = db.getConnection();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return conn;
	}
	

}
