package com.pascloud.module.mycat.web;

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

import com.pascloud.bean.mycat.DataHostVo;
import com.pascloud.bean.mycat.DataNodeVo;
import com.pascloud.bean.pasdev.PasfileVo;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.mycat.service.MycatService;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.vo.result.ResultBean;
import com.pascloud.vo.result.ResultCommon;


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
	/*
	@RequestMapping("/datahosts.json")
	@ResponseBody
	public List<DataHostVo> getDataHosts(HttpServletRequest request){
		List<DataHostVo> result = new ArrayList<>();
		result = m_mycatService.getDataHosts();
		return result;
	}*/
	
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
		
		if(null!=name && !"".equals(name)){
			dn = getDataNodeByName(name);
			result.setBean(dn);
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
	
	

}
