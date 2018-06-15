package com.pascloud.module.mycat.web;

import java.beans.PropertyVetoException;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.pool.DruidDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pascloud.constant.Constants;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.database.service.DataBaseService;
import com.pascloud.module.docker.service.ContainerService;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.module.mycat.service.MycatService;
import com.pascloud.module.passervice.service.ConfigService;
import com.pascloud.module.server.service.ServerService;
import com.pascloud.utils.DBUtils;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.utils.ScpClientUtils;
import com.pascloud.vo.database.DBInfo;
import com.pascloud.vo.docker.ContainerVo;
import com.pascloud.vo.docker.NodeVo;
import com.pascloud.vo.mycat.DataNodeVo;
import com.pascloud.vo.mycat.DataSourceVo;
import com.pascloud.vo.result.ResultBean;
import com.pascloud.vo.result.ResultCommon;
import com.pascloud.vo.result.ResultListData;
import com.pascloud.vo.server.ServerVo;
import com.spotify.docker.client.DefaultDockerClient;


@Controller
@RequestMapping("module/mycat")
public class MycatController extends BaseController {
	@Autowired
	private MycatService     m_mycatService;
	
	@Autowired
	private PasCloudConfig   m_config;
	
	@Autowired
	private ContainerService m_containerService;
	@Autowired
	private ConfigService    m_configService;
	
	
	
	@RequestMapping("/index.html")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView view = new ModelAndView("mycat/index");
		return view;
	}
	
	@RequestMapping("/monitor.html")
	public ModelAndView monitor(HttpServletRequest request){
		ModelAndView view = new ModelAndView("mycat/monitor");
		return view;
	}
	
	@RequestMapping("/datanodes.json")
	@ResponseBody
	public List<DataNodeVo> getDataNodes(HttpServletRequest request){
		List<DataNodeVo> result = new ArrayList<>();
		result = m_mycatService.getDataNodes();
		return result;
	}
	
	@RequestMapping("/datanode.json")
	@ResponseBody
	public ResultBean<DataNodeVo> getDataNodeByName(HttpServletRequest request,
			@RequestParam(value="name",defaultValue="",required=true) String name){
		ResultBean<DataNodeVo> result = new ResultBean<>(PasCloudCode.SUCCESS);
		DataNodeVo dn = new DataNodeVo();
		
		log.info(name);
		
		if(null!=name && !"".equals(name)){
			dn = getDataNodeByName(name);
			result.setBean(dn);
		}else{
			return new ResultBean<>(PasCloudCode.NULLDATA);
		}
		return result;
	}
	
	@RequestMapping("addDatanode.json")
	@ResponseBody
	public ResultCommon addDatanode(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip,
			@RequestParam(value="port",defaultValue="0",required=true) Integer port,
			@RequestParam(value="name",defaultValue="",required=true) String name,
			@RequestParam(value="dbType",defaultValue="",required=true) String dbType,
			@RequestParam(value="database",defaultValue="",required=true) String database,
			@RequestParam(value="username",defaultValue="",required=true) String user,
			@RequestParam(value="password",defaultValue="",required=true) String password){
		
		ResultCommon result = null;
		log.info("添加节点");
		if(ip.length() == 0 || port == 0 || name.length() ==0 
				|| dbType.length()==0 || database.length() ==0 
				|| user.length() == 0 || password.length() ==0){
			result = new ResultCommon(PasCloudCode.PARAMEXCEPTION);
			return result;
		}
		
		List<DataNodeVo> nodes = new ArrayList<>();
		nodes = m_mycatService.getDataNodes();
		
		String url = DBUtils.getUrlByParams(dbType, ip, database, port);
		
		if(nodes.size()>0){
			for(DataNodeVo n:nodes){
				if(n.getName().equals(name)){
					return new ResultCommon(PasCloudCode.ERROR.getCode(),"节点已经存在，不能添加重复节点");
				}
				if(null!=url){
					if(n.getUrl().equals(url)){
						return new ResultCommon(PasCloudCode.ERROR.getCode(),"数据库地址已经被使用，请选其他数据库");
					}
				}
				
			}
		}
		
		
		if(m_mycatService.addDatanode(name, dbType, ip, user, password, database, port)){
			result = new ResultCommon(PasCloudCode.SUCCESS);
		}else{
			result = new ResultCommon(PasCloudCode.ERROR);
		}
		return result;
		
	}
	
	@RequestMapping("delDatanode.json")
	@ResponseBody
	public ResultCommon delDatanode(HttpServletRequest request,
			@RequestParam(value="name",defaultValue="",required=true) String name){
		log.info("删除节点");
		ResultCommon result = null;
		
		if(name.length() == 0){
			return new ResultCommon(PasCloudCode.PARAMEXCEPTION); 
		}

		List<DBInfo> dbs = new ArrayList<>();
		dbs = m_configService.getDBFromConfig();
		if(dbs.size()>0){
			for(DBInfo db:dbs){
				if(db.getId().equals(name)){
					return new ResultCommon(PasCloudCode.ERROR.getCode(),"该节点已经被租户使用，不能删除。"); 
				}
			}
		}
		
		if(name.equals("dn0")){
			return new ResultCommon(PasCloudCode.ERROR);
		}else{
			if(m_mycatService.delDatanode(name)){
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
		}
		
		
		return result;
		
	}
	
	private DataNodeVo getDataNodeByName(String name){
		List<DataNodeVo> result = new ArrayList<>();
		result = m_mycatService.getDataNodes();
		
		if(result.size()>0){
			Iterator<DataNodeVo> it = result.iterator();
			while(it.hasNext()){
				DataNodeVo o = (DataNodeVo) it.next();	
				if(o.getName().equals(name)){
					return o;
				}
			}
		}
		return null;
		
	}
	
	@RequestMapping("uploadConfig.json")
	@ResponseBody
	public ResultCommon uploadConfig(HttpServletRequest request){
		ResultCommon result = null;
		//containers = m_dockerService.getContainer(dockerClient);
		String mycat_schema_path =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SCHEMA;
		String mycat_server_path =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SERVER;
	
		if( m_mycatService.uploadConfigAndRestart(mycat_schema_path,mycat_server_path)){
			result = new ResultCommon(PasCloudCode.SUCCESS);
		}else{
			result = new ResultCommon(PasCloudCode.ERROR);
		}
		return result;
		
	}
	@RequestMapping("getDataSourceByName.json")
	@ResponseBody
	public ResultBean<DataSourceVo> getDataSourceByName(HttpServletRequest request,
			@RequestParam(value="name",defaultValue="",required=true) String name){
		ResultBean<DataSourceVo> result = new ResultBean<DataSourceVo>(PasCloudCode.SUCCESS);
		
		//ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
		List<DataSourceVo> ds = new ArrayList<>();
		DataSourceVo vo = null;
		try {
			ds = m_mycatService.getDataSource(getDockerClient(), defaultPort);
			if(ds.size()>0){
				for(DataSourceVo v:ds){
					if(v.getDatanode().equals(name)){
						vo = v;
					}
				}
			}
			if(null!=vo){
				result.setBean(vo);
			}else{
				result = new ResultBean<DataSourceVo>(PasCloudCode.ERROR);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			result = new ResultBean<DataSourceVo>(PasCloudCode.ERROR);
		}
		return result;
	}

}
