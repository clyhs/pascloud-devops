package com.pascloud.module.database.web;

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
import org.springframework.web.servlet.ModelAndView;

import com.pascloud.constant.Constants;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.database.service.DBServerService;
import com.pascloud.module.passervice.service.ConfigService;
import com.pascloud.module.server.service.ServerService;
import com.pascloud.utils.DBUtils;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.vo.common.TreeVo;
import com.pascloud.vo.database.DBInfo;
import com.pascloud.vo.result.ResultBean;
import com.pascloud.vo.result.ResultCommon;
import com.pascloud.vo.server.ServerVo;
import com.pascloud.vo.tenant.KhdxHyVo;

@Controller
@RequestMapping("module/dbserver")
public class DBServerController extends BaseController {
	@Autowired
	private ServerService   m_serverService;
	
	@Autowired
	private PasCloudConfig  m_config;
	
	@Autowired
	private DBServerService m_dbServerService;
	
	@Autowired
	private ConfigService   m_configService;
	
	@RequestMapping("index.html")
	public ModelAndView index(HttpServletRequest request) {
		
		List<ServerVo> servers = new ArrayList<>();
		servers = m_serverService.getDataBaseServers();
		ModelAndView view = new ModelAndView("dbserver/index");
		if(servers.size()>0){
			ServerVo vo = servers.get(0);
			view.addObject("defaultIp", vo.getIp());
		}
		return view;
	}
	
	/**
	 * 查询所有数据库服务器
	 * @param request
	 * @return
	 */
	@RequestMapping("/dbserverTree.json")
	@ResponseBody
	public List<TreeVo> getPasFileDirForTree(HttpServletRequest request){
		List<TreeVo> trees = new ArrayList<>();
		List<ServerVo> servers = new ArrayList<>();
		servers =m_serverService.getDataBaseServers();
		
		for(int i=0;i<servers.size();i++){
			TreeVo vo = new TreeVo();
			vo.setId((i+1)+"");
			vo.setText(servers.get(i).getIp());

			vo.setLeaf(true);
			vo.setUrl("#");
			vo.setIconCls("icon-folder");
			trees.add(vo);
		}
		return trees;
	}
	
	@RequestMapping("/getDBsWithServer.json")
	@ResponseBody
	public List<DBInfo> getDBsWithServer(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip){
		List<DBInfo> result = new ArrayList<DBInfo>();
		if(""==ip || ip.length() == 0){
			return result;
		}
		ServerVo server = m_serverService.getByIP(ip);
		if(null!=server){
			result = m_dbServerService.getOracleSidWithServer(server);
		}
		return result;
	}
	
	@RequestMapping("/createOracle.json")
	@ResponseBody
	public ResultCommon createOracle(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip,
			@RequestParam(value="sid",defaultValue="",required=true) String sid){
		ResultCommon result = null;
		String tnsnamePath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SCRIPT_ORACLE_DIR()+
				"/conf/tnsnames.ora";
		if("".equals(ip) || ip.length() == 0 || "".equals(sid) || sid.length()==0){
			return result = new ResultCommon(PasCloudCode.ERROR);
		}else{
			
			
			
			result = m_dbServerService.createOracleWithSid(ip, sid,tnsnamePath);
		}
		return result;
		
	}
	
	@RequestMapping("/impDmpWithSid.json")
	@ResponseBody
	public ResultCommon impDmpWithSid(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip,
			@RequestParam(value="sid",defaultValue="",required=true) String sid){
		ResultCommon result = null;
		
		if("".equals(ip) || ip.length() == 0 || "".equals(sid) || sid.length()==0){
			return result = new ResultCommon(PasCloudCode.ERROR);
		}else{
			if(m_dbServerService.checkLsnrctlStatus(ip, sid)){
				if(m_dbServerService.importDmpfileWithSid(ip, sid)){
					result = new ResultCommon(PasCloudCode.SUCCESS);
				}else{
					result = new ResultCommon(PasCloudCode.ERROR);
				}
			}else{
				result = new ResultCommon(PasCloudCode.ERROR.getCode(),"监听服务还没有启动");
			}	
		}
		return result;
		
	}
	
	@RequestMapping("/checkLsnrctlStatus.json")
	@ResponseBody
	public ResultCommon checkLsnrctlStatus(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip,
			@RequestParam(value="sid",defaultValue="",required=true) String sid){
		ResultCommon result = null;
		//log.info("ip:" + ip + ",sid:" + sid);
		if("".equals(ip) || ip.length() == 0 || "".equals(sid) || sid.length()==0){
			return result = new ResultCommon(PasCloudCode.ERROR);
		}else{
			if(m_dbServerService.checkLsnrctlStatus(ip, sid)){
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				return result = new ResultCommon(PasCloudCode.ERROR);
			}
		}
		return result;
		
	}
	
	@RequestMapping("/deleteOracle.json")
	@ResponseBody
	public ResultCommon deleteOracle(HttpServletRequest request,
			@RequestParam(value="ip",defaultValue="",required=true) String ip,
			@RequestParam(value="sid",defaultValue="",required=true) String sid){
		ResultCommon result = null;
		String oratabfilePath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SCRIPT_ORACLE_DIR()+
				"/conf/oratab";
		String tnsnamePath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SCRIPT_ORACLE_DIR()+
				"/conf/tnsnames.ora";
		List<DBInfo> dbs = new ArrayList<>();
		log.info(oratabfilePath);
		if("".equals(ip) || ip.length() == 0 || "".equals(sid) || sid.length()==0){
			return result = new ResultCommon(PasCloudCode.ERROR);
		}else{
			
			String url = DBUtils.getUrlByParams("oracle", ip, sid, 1521);
			if(null!=url){
				dbs = m_configService.getDBFromConfig();
				if(dbs.size()>0){
					for(DBInfo db:dbs){
						if(db.getUrl().equals(url)){
							return new ResultCommon(PasCloudCode.ERROR.getCode(),"该节点已经被租户使用，不能删除。"); 
						}
					}
				}
			}
			
			result=m_dbServerService.deleteOracleWithSid(ip, sid, oratabfilePath,tnsnamePath);
		}
		return result;
		
	}
	
	@RequestMapping(value = "updateKhdxHy.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultCommon updateKhdxHy(HttpServletRequest request,
			@RequestParam(value = "url", defaultValue = "", required = true) String url,
			@RequestParam(value = "preffix", defaultValue = "", required = true) String preffix) {

		ResultCommon result = null;
		int total = 0;
		try {
			total = m_dbServerService.updateKhdxHy("oracle", url, "pas", "pas",preffix );
			if(total>0){
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
			result = new ResultCommon(PasCloudCode.ERROR);
		}

		return result;
	}

}
