package com.pascloud.listen;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.pascloud.constant.Constants;
import com.pascloud.module.config.init.PasCloudInitConfig;
import com.pascloud.module.redis.service.RedisService;

public class PasCloudListener implements ServletContextListener {
	
	//private final String WEB_APP_ROOT_DEFAULT = "webapp.root";
	
	private static final Logger log = LoggerFactory.getLogger(PasCloudListener.class);
	
	private WebApplicationContext springContext;  
	
	private RedisService redisService;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		String prefix = sce.getServletContext().getRealPath("/");
		if (prefix.endsWith(File.separator)) {
			prefix = prefix.substring(0, prefix.length() - 1);
			prefix = prefix.replaceAll("\\\\", "/");
		}
		System.out.println(prefix);
		System.setProperty(Constants.WEB_APP_ROOT_DEFAULT, prefix);
		
		springContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());  
		
		if(null==redisService){
			log.info("获取RedisService");
			redisService = (RedisService) springContext.getBean("redisService");
			redisService.initRedisServer();
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
