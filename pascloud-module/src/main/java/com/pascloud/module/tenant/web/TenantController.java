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
import com.pascloud.constant.Constants;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.database.service.DataBaseService;
import com.pascloud.module.docker.service.ContainerService;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.module.mycat.service.MycatService;
import com.pascloud.module.passervice.service.ConfigService;
import com.pascloud.module.passervice.service.PasService;
import com.pascloud.module.server.service.ServerService;
import com.pascloud.module.tenant.service.TenantService;
import com.pascloud.utils.DBUtils;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.utils.ScpClientUtils;
import com.pascloud.vo.database.DBInfo;
import com.pascloud.vo.docker.ContainerVo;
import com.pascloud.vo.docker.NodeVo;
import com.pascloud.vo.mycat.DataNodeVo;
import com.pascloud.vo.pass.PasTypeEnum;
import com.pascloud.vo.result.ResultBean;
import com.pascloud.vo.result.ResultCommon;
import com.pascloud.vo.server.ServerVo;
import com.pascloud.vo.tenant.KhdxHyVo;
import com.spotify.docker.client.DefaultDockerClient;

/**
 * 租户管理
 * @author chenly
 *
 */
@Controller
@RequestMapping("module/tenant")
public class TenantController extends BaseController {
	
	@Autowired
	private DataBaseService  m_dbService;
	@Autowired
	private ConfigService    m_configService;
	@Autowired
	private MycatService     m_mycatService;
	@Autowired
	private TenantService    m_tenantService;
	@Autowired
	private ContainerService m_containerService;
	@Autowired
	private PasService       m_pasService;
	@Autowired
	private PasCloudConfig   m_config;
	@Autowired
	private DockerService    m_dockerService;
	
	@RequestMapping("index.html")
	public String index(HttpServletRequest request){
		return "tenant/index";
	}
	
	@RequestMapping("dbs.json")
	@ResponseBody
	public List<DBInfo> getTenantDBs(HttpServletRequest request){
		
		List<DBInfo> result = new ArrayList<>();
		
		try {
			result = m_configService.getDBFromConfig();
			for(DBInfo dbf:result){
				
				dbf.setAlianame(dbf.getName());
				
				Connection conn = null;
				String driverClass = DBUtils.getDirverClassName(dbf.getDbType());
				DBUtils db = new DBUtils(driverClass, dbf.getUrl(), dbf.getUsername(), dbf.getPassword());
				conn = db.getConnection();
				Integer total = -1;
				String tableName = "khdx_hy";
				
				if(null!=conn){
					total = m_dbService.getDataCountsByConn(conn, tableName,true);
					dbf.setUserCount(total);
					String xmmc = m_dbService.getSimleColumnValueByConn(conn, "select csz from xtb_xtcs where csmc = 'SYS_XMMC' ", "");
				    if(null!=xmmc){
				    	dbf.setDesc(xmmc);
				    }
				}
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return result;
		
	}
	
//	@RequestMapping("addTenantDB.json")
//	@ResponseBody
//	public ResultCommon addTenantDB(HttpServletRequest request,
//			@RequestParam(value="ip",defaultValue="",required=true) String ip,
//			@RequestParam(value="port",defaultValue="",required=true) Integer port,
//			@RequestParam(value="name",defaultValue="",required=true) String name,
//			@RequestParam(value="dbType",defaultValue="",required=true) String dbType,
//			@RequestParam(value="database",defaultValue="",required=true) String database,
//			@RequestParam(value="username",defaultValue="",required=true) String user,
//			@RequestParam(value="password",defaultValue="",required=true) String password,
//			@RequestParam(value="cn",defaultValue="",required=true) String cn,
//			@RequestParam(value="en",defaultValue="",required=true) String en){
//		
//		ResultCommon result = null;
//		m_configService.addDBConfig(ip, port, user, password, dbType, name, database,en,cn);
//		result = new ResultCommon(PasCloudCode.SUCCESS);
//		return result;
//		
//	}
	/**
	 * 增加租户
	 * @param request
	 * @param name
	 * @return
	 */
	@RequestMapping("addTenantDBByName.json")
	@ResponseBody
	public ResultCommon addTenantDBByName(HttpServletRequest request,
			@RequestParam(value="name",defaultValue="",required=true) String name,
			@RequestParam(value="cn",defaultValue="",required=true) String cn,
			@RequestParam(value="en",defaultValue="",required=true) String en){
		
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		
		if(name.length()==0 || cn.length()==0 || en.length()==0){
			return  new ResultCommon(PasCloudCode.NULLDATA);
		}
		//从MYCAT人DATANODE获取节点，如果不存在，显示错误
		List<DataNodeVo> datanodes = new ArrayList<>();
		DataNodeVo datanode = null;
		datanodes = m_mycatService.getDataNodes();
		if(datanodes.size()>0){
			for(DataNodeVo d:datanodes){
				if(name.equals(d.getName())){
					datanode = d;
				}
			}
		}else{
			result = new ResultCommon(PasCloudCode.ERROR);
			return result;
		}
		
		if(null!=datanode){
			//添加到MYCAT的schema.xml server.xml
			m_configService.addDBConfig(datanode.getIp(),Integer.valueOf(datanode.getPort()), datanode.getUser(),
						datanode.getPassword(), datanode.getDbType(), name, datanode.getDatabase(),
						en,cn);
		}else{
			result = new ResultCommon(PasCloudCode.ERROR);
			return result;
		}
		
		return result;
		
	}
	/**
	 * 删除租户
	 * @param request
	 * @param name
	 * @return
	 */
	@RequestMapping("delTenantDB.json")
	@ResponseBody
	public ResultCommon delTenantDB(HttpServletRequest 
			request,@RequestParam(value="name",defaultValue="",required=true) String name){
		ResultCommon result = null;

		if(name.equals(Constants.PASCLOUD_PUBLIC_DB)){
			result = new ResultCommon(20000, "不能删除公共库，请重新选择");
			return result;
		}
		if(m_configService.delDBConfig(name)){
			result = new ResultCommon(PasCloudCode.SUCCESS);
			
		}else{
			result = new ResultCommon(PasCloudCode.ERROR);
		}
		return result;
	}
	/**
	 * 上传服务的config.properties文件
	 * @param request
	 * @return
	 */
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
		String status = "";
		if(containers.size()>0){
			for(ContainerVo vo : containers){
				
				if(vo.getName().contains(PasTypeEnum.DEMO.getValue())){
					m_pasService.uploadConfigForStart(vo.getIp(), PasTypeEnum.DEMO, vo.getName());
				}else if(vo.getName().contains(PasTypeEnum.PASPM.getValue())){
					m_pasService.uploadConfigForStart(vo.getIp(), PasTypeEnum.PASPM, vo.getName());
				}
				DefaultDockerClient client = DefaultDockerClient.builder()
						.uri("http://"+vo.getIp()+":"+defaultPort).build();
				log.info("重启服务"+vo.getName());
				status = m_dockerService.restartContainer(client, vo.getId());
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
		String xmmc = "";
		try {
			//conn = db.getConnection();
			if (db.canConnect()) {
				/*
				Integer total = -1;
				String tableName = "khdx_hy";
				total = m_dbService.getDataCountsByConn(conn, tableName,true);
				
				result = new ResultBean<Integer>(10000, "成功");
				result.setBean(total);
				result.setDesc("");*/
				result = new ResultBean<Integer>(10000, "成功");
			} else {
				result = new ResultBean<Integer>(20000, "失败");
			}
		} catch (Exception e) {
			result = new ResultBean<Integer>(20000, "失败");
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * 同步行员数据
	 * @param request
	 * @param id
	 * @param dbType
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
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
	
	/**
	 * 根据名称同步行员数据到公共库
	 * @param request
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "syscHyByName.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultCommon syscHyByName(HttpServletRequest request,
			@RequestParam(value = "name", defaultValue = "", required = true) String name,
			@RequestParam(value = "en", defaultValue = "", required = true) String en,
			@RequestParam(value = "cn", defaultValue = "", required = true) String cn) {

		ResultCommon result = null;
		
		if(name.length() == 0 || en.length()==0 || cn.length() == 0){
			return new ResultCommon(20000, "不能选择公共库进行同步，请重新选择");
		}
		
		if(name.equals(Constants.PASCLOUD_PUBLIC_DB)){
			result = new ResultCommon(PasCloudCode.NULLDATA);
			return result;
		}
		
		
		
		List<DataNodeVo> datanodes = new ArrayList<>();
		DataNodeVo datanode = null;
		datanodes = m_mycatService.getDataNodes();
		if(datanodes.size()>0){
			for(DataNodeVo d:datanodes){
				if(name.equals(d.getName())){
					datanode = d;
				}
			}
		}else{
			result = new ResultCommon(PasCloudCode.ERROR);
			return result;
		}
		
		if(null!=datanode){
			
		}else{
			result = new ResultCommon(PasCloudCode.ERROR);
			return result;
		}

		String driverClass = DBUtils.getDirverClassName(datanode.getDbType());
		DBUtils db = new DBUtils(driverClass, datanode.getUrl(), datanode.getUser(), datanode.getPassword());
		Connection sourceConn = null;
		Connection dn0Conn = null;
		int hynum = 0;
		try {
			sourceConn = db.getConnection();
			if (null!=sourceConn) {
				log.info("更新行员的前缀");
				hynum = m_tenantService.updateKhdxHy(sourceConn, en,cn);
				log.info("更新行员的前缀完成");
				log.info("获取租户的全部行员数据");
				List<KhdxHyVo> lists = m_tenantService.getAllHy(sourceConn);
				
				log.info("行员数="+lists.size());
				
				dn0Conn = getPublicDB();
				if(null!=dn0Conn && lists.size()>0){
					log.info("清除租户在公共库的全部行员数据");
					m_tenantService.deleteHyWithPublicDB(dn0Conn, name);
					log.info("导入租户全部行员数据到公共库");
					m_tenantService.addHyWithPublicDB(dn0Conn, name, lists);
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
	
	@RequestMapping(value = "checkDBConfigByName.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultCommon checkDBConfigByName(HttpServletRequest request,
			@RequestParam(value = "name", defaultValue = "", required = true) String name,
			@RequestParam(value="cn",defaultValue="",required=true) String cn,
			@RequestParam(value="en",defaultValue="",required=true) String en){
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		List<DBInfo> dbs = new ArrayList<>();
		
		if(name.length()==0 || cn.length()==0 || en.length()==0){
			return  new ResultCommon(PasCloudCode.NULLDATA);
		}
		try {
			
			Boolean flag = false;
			flag = m_configService.checkEnAndCn(name, en, cn);
			if(flag){
				return new ResultCommon(PasCloudCode.ERROR.getCode(),"中英文名已经存在");
			}else{
				dbs = m_configService.getDBFromConfig();
				if(dbs.size()>0){
					for(DBInfo d:dbs){
						if(d.getId().equals(name)){
							result = new ResultCommon(PasCloudCode.ERROR.getCode(),"数据库已经被使用，请选择其它数据节点");
						}
					}
				}
			}
			
			
		}catch(Exception e){
			result = new ResultCommon(PasCloudCode.ERROR);
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
