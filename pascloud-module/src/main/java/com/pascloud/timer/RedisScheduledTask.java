package com.pascloud.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.thread.timer.ScheduledTask;
import com.pascloud.module.redis.service.RedisService;

public class RedisScheduledTask extends ScheduledTask {
	
private static final Logger log = LoggerFactory.getLogger(RedisScheduledTask.class);
	
	private RedisService m_redisService;

	public RedisScheduledTask(RedisService rs) {
		super(20*1000);
		// TODO Auto-generated constructor stub
		this.m_redisService = rs;
	}

	@Override
	protected void executeTask() {
		// TODO Auto-generated method stub
		//log.info("初始化缓存服务");
		// TODO Auto-generated method stub
		if(null!=m_redisService){
			m_redisService.initRedisServer();
		}

	}

}
