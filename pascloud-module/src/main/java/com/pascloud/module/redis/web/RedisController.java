package com.pascloud.module.redis.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.redis.service.RedisService;
import com.pascloud.vo.common.TreeVo;
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
	@RequestMapping("redisServerInfo.json")
	@ResponseBody
	public List<RedisServerDetailInfo> getRedisServerDetailInfo(HttpServletRequest request,
			@RequestParam(value="redisServerId",defaultValue="",required=true) String redisServerId){
		List<RedisServerDetailInfo> result = new ArrayList<RedisServerDetailInfo>();
		result = m_redisService.getRedisInfo(redisServerId);
		return result;
	}
	
	
	
}
