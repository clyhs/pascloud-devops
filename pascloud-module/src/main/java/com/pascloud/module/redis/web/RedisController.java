package com.pascloud.module.redis.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.redis.service.RedisService;

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
		return view;
	}
	
}
