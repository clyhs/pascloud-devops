package com.pascloud.module.server.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.bean.server.ServerVo;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.server.service.ServerService;
import com.pascloud.vo.database.DBInfo;

@Controller
@RequestMapping("module/server")
public class ServerController extends BaseController {
	
	@Autowired
	private ServerService m_serverService;
	
	@RequestMapping("index.html")
	public String index(HttpServletRequest request){
		return "server/index";
	}
	
	@RequestMapping("servers.json")
	@ResponseBody
	public List<ServerVo> getServers(){
		
		List<ServerVo> result = new ArrayList<>();
		
		result = m_serverService.getServers();
		
		return result;
		
	}

}
