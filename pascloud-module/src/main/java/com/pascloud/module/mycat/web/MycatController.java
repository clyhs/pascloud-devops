package com.pascloud.module.mycat.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pascloud.bean.mycat.DataHostVo;
import com.pascloud.bean.mycat.DataNodeVo;
import com.pascloud.bean.pasdev.PasfileVo;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.mycat.service.MycatService;


@Controller
@RequestMapping("module/mycat")
public class MycatController extends BaseController {
	@Autowired
	private MycatService m_mycatService;
	
	@RequestMapping("/index.html")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView view = new ModelAndView("mycat/index");
		return view;
	}
	
	@RequestMapping("/datahosts.json")
	@ResponseBody
	public List<DataHostVo> getDataHosts(){
		List<DataHostVo> result = new ArrayList<>();
		result = m_mycatService.getDataHosts();
		return result;
	}
	
	@RequestMapping("/datanodes.json")
	@ResponseBody
	public List<DataNodeVo> getDataNodes(){
		List<DataNodeVo> result = new ArrayList<>();
		result = m_mycatService.getDataNodes();
		
		if(result.size()>0){
			for(DataNodeVo node:result){
				DataHostVo vo = getDataHostByName(node.getDataHost());
				if(null != vo){
					node.setUrl(vo.getUrl());
					node.setPassword(vo.getPassword());
					node.setUser(vo.getUser());
					node.setDbType(vo.getDbType());
					node.setDbDriver(vo.getDbDriver());
				}
			}
		}
		
		
		return result;
	}
	
	private DataHostVo getDataHostByName(String name){
		DataHostVo vo = null;
		
		List<DataHostVo> result = m_mycatService.getDataHosts();
		if(null != result && result.size()>0){
			for(DataHostVo v : result){
				if(v.getName().equals(name)){
					vo = v;
				}
			}
		}
		return vo;
	}

}
