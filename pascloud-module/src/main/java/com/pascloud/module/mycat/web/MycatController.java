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
import com.pascloud.module.server.service.ServerService;
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
	private MycatService m_mycatService;
	
	@Autowired
	private PasCloudConfig m_config;
	
	@Autowired
	private ContainerService m_containerService;
	
	@Autowired
	private ServerService m_serverService;
	
	
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
			@RequestParam(value="port",defaultValue="",required=true) Integer port,
			@RequestParam(value="name",defaultValue="",required=true) String name,
			@RequestParam(value="dbType",defaultValue="",required=true) String dbType,
			@RequestParam(value="database",defaultValue="",required=true) String database,
			@RequestParam(value="username",defaultValue="",required=true) String user,
			@RequestParam(value="password",defaultValue="",required=true) String password){
		
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		
		
		List<DataNodeVo> nodes = new ArrayList<>();
		nodes = m_mycatService.getDataNodes();
		if(nodes.size()>0){
			for(DataNodeVo n:nodes){
				if(n.getName().equals(name)){
					return new ResultCommon(PasCloudCode.ERROR.getCode(),"节点已经存在，不能添加重复节点");
				}
			}
		}
		
		m_mycatService.addDatanode(name, dbType, ip, user, password, database, port);
		return result;
		
	}
	
	@RequestMapping("delDatanode.json")
	@ResponseBody
	public ResultCommon delDatanode(HttpServletRequest request,
			@RequestParam(value="name",defaultValue="",required=true) String name){
		
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		
		if(name.equals("dn0")){
			return new ResultCommon(PasCloudCode.ERROR);
		}else{
			m_mycatService.delDatanode(name);
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
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		List<ContainerVo> containers = new ArrayList<>();
		//containers = m_dockerService.getContainer(dockerClient);
		String mycat_schema_path =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SCHEMA;
		String mycat_server_path =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_MYCAT_DIR()+File.separator+Constants.MYCAT_SERVER;
	
		containers = m_containerService.getContainers("pascloud_mycat");
		/****上传到复制的项目***/
		if(containers.size()>0){
			for(ContainerVo vo : containers){
				
				ServerVo server = m_serverService.getByIP(vo.getIp());
				
				ScpClientUtils scpClient = new ScpClientUtils(vo.getIp(), server.getUsername(), server.getPassword());
				String path = "/home/pascloud/mycat/conf/";
				scpClient.putFileToServer(mycat_schema_path, path);
				scpClient.putFileToServer(mycat_server_path, path);
				scpClient.close();
			}
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
