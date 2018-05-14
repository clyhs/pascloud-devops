package com.pascloud.module.tenant.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.passervice.service.ConfigService;
import com.pascloud.vo.database.DBInfo;

@Controller
@RequestMapping("module/tenant")
public class TenantController extends BaseController {
	
	@Autowired
	private ConfigService m_configService;
	
	@RequestMapping("index.html")
	public String index(HttpServletRequest request){
		return "tenant/index";
	}
	
	@RequestMapping("dbs.json")
	@ResponseBody
	public List<DBInfo> getTenantDBs(){
		
		List<DBInfo> result = new ArrayList<>();
		
		result = m_configService.getDBFromConfig();
		
		return result;
		
	}

}
