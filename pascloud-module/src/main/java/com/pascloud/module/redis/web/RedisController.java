package com.pascloud.module.redis.web;

import java.io.File;
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
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.redis.service.RedisService;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.redis.JedisPoolUtils;
import com.pascloud.vo.common.TreeVo;
import com.pascloud.vo.database.DBColumnVo;
import com.pascloud.vo.redis.RedisInfo;
import com.pascloud.vo.redis.RedisServerDetailInfo;
import com.thoughtworks.xstream.XStream;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Controller
@RequestMapping("module/redis")
public class RedisController extends BaseController {
	
	@Autowired
	private RedisService m_redisService; 
	
	@Autowired
	private PasCloudConfig  m_config;

	@RequestMapping("index.html")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView view = new ModelAndView("redis/index");
		initRedisPool();
		List<String> servers = m_redisService.getRedisServers();
		view.addObject("servers", servers);
		view.addObject("redisServerId", servers.get(0));
		return view;
	}
	
	private void initRedisPool(){
		String redis_dir = m_config.getPASCLOUD_REDIS_DIR();
		
		if(null!=redis_dir){
			log.info("初始化redis--开始--");
			XStream xstream = new  XStream();
			xstream.alias("redisInfo", RedisInfo.class);
			File redis_file= new File(redis_dir);
			if(FileUtils.createOrExistsDir(redis_file)){
				List<File> files = FileUtils.listFilesInDirWithFilter(redis_file, "xml");
				if(files.size()>0){
					for(File file : files){
						try{
							RedisInfo info = (RedisInfo) xstream.fromXML(file);
							log.info(info.getAddr().trim()+":"+info.getPort());
							
							JedisPoolConfig config = new JedisPoolConfig();
					        config.setMaxTotal(10);
					        config.setMaxIdle(5);
					        config.setMaxWaitMillis(15000);
					        config.setTestOnBorrow(true);
					        String id = info.getAddr().trim()+":"+ info.getPort();
					        JedisPool jedisPool = new JedisPool(config, info.getAddr().trim(), info.getPort());
					        JedisPoolUtils.addJedisPool(id, jedisPool);
					        
						} catch (Exception e) {  
					        e.printStackTrace();  
					    } 
						
					}
				}
			}
			
			log.info("初始化redis--结束--");
		}
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
