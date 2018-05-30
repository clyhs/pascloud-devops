package com.pascloud.module.database.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.server.service.ServerService;
import com.pascloud.vo.common.TreeVo;
import com.pascloud.vo.server.ServerVo;

@Controller
@RequestMapping("module/dbserver")
public class DBServerController extends BaseController {
	@Autowired
	private ServerService m_serverService;
	
	@RequestMapping("index.html")
	public String index() {
		return "dbserver/index";
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
			vo.setId(servers.get(i).getIp());
			vo.setText(servers.get(i).getIp());

			vo.setLeaf(true);
			vo.setUrl("#");
			vo.setIconCls("icon-folder");
			trees.add(vo);
		}
		return trees;
	}

}
