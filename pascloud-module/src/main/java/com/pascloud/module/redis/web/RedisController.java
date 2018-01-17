package com.pascloud.module.redis.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.redis.service.RedisService;
import com.pascloud.vo.common.TreeVo;
import com.pascloud.vo.database.DBColumnVo;
import com.pascloud.vo.redis.RedisServerDetailInfo;

@Controller
@RequestMapping("module/redis")
public class RedisController extends BaseController {
	
	@Autowired
	private RedisService m_redisService; 

	@RequestMapping("index.html")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView view = new ModelAndView("redis/index");
		List<String> servers = m_redisService.getRedisServers();
		view.addObject("servers", servers);
		view.addObject("redisServerId", servers.get(0));
		return view;
	}
	
	
	@RequestMapping("redisTrees.json")
	@ResponseBody
	public List<TreeVo> getRedisDBTree(){
		List<TreeVo> dbTrees = new ArrayList<TreeVo>();
		List<String> lists   = m_redisService.getRedisDB();
		for(String db : lists){
			TreeVo t = new TreeVo();
			t.setId(db.replace("db", ""));
			t.setText(db);
			t.setLeaf(true);
			t.setIconCls("icon-database");
			dbTrees.add(t);
		}
		return dbTrees;
	}
	
	
	@RequestMapping("table.html")
	public ModelAndView table(HttpServletRequest request,
			@RequestParam(value="redisServerId",defaultValue="",required=true) String redisServerId,
			@RequestParam(value="index",defaultValue="0",required=true) Integer index
			){
		ModelAndView view = new ModelAndView("redis/table");
		String url = "/module/redis/redisPageData.json?redisServerId="+redisServerId+"&index="+index;
		view.addObject("url", url);
		return view;
	}
	
	
	@RequestMapping("redisServerInfo.json")
	@ResponseBody
	public List<RedisServerDetailInfo> getRedisServerDetailInfo(HttpServletRequest request,
			@RequestParam(value="redisServerId",defaultValue="",required=true) String redisServerId){
		List<RedisServerDetailInfo> result = new ArrayList<RedisServerDetailInfo>();
		result = m_redisService.getRedisInfo(redisServerId);
		return result;
	}
	
	@RequestMapping("redisPageData.json")
	@ResponseBody
	public Map<String, Object> getPageData(HttpServletRequest request,
			@RequestParam(value="redisServerId",defaultValue="",required=true) String redisServerId,
			@RequestParam(value="index",defaultValue="0",required=true) Integer index,
			@RequestParam(value="selectKey",defaultValue="nokey",required=true) String selectKey){
		Map<String, Object> result = new HashMap<String, Object>();
		
		Integer page = (null == request.getParameter("page"))?1:Integer.parseInt(request.getParameter("page"));
		Integer pageSize = (null == request.getParameter("rows"))?50:Integer.parseInt(request.getParameter("rows"));
		Integer startRow = (page -1)*pageSize;
		
		result = m_redisService.getDBPageData(redisServerId, index, startRow, pageSize, selectKey);
		
		return result;
	}
	
	
	
}
