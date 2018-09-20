package com.pascloud.timer;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.module.redis.service.RedisService;

public class RedisTimerTask extends TimerTask {
	
	private static final Logger log = LoggerFactory.getLogger(RedisTimerTask.class);
	
	private RedisService m_redisService;
	
	public RedisTimerTask(RedisService rs){
		this.m_redisService = rs;
	}

	@Override
	public void run() {
		log.info("初始化缓存服务");
		// TODO Auto-generated method stub
		if(null!=m_redisService){
			m_redisService.initRedisServer();
		}
		
	}

}
