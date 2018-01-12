package com.pascloud.module.redis.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.pascloud.utils.redis.JedisPoolUtils;
import com.pascloud.vo.redis.RedisInfo;

import redis.clients.jedis.JedisPool;

@Service
public class RedisService extends AbstractRedisService {
	
	public List<String> getRedisServers(){
		List<String> lists = new ArrayList<String>();
		
		ConcurrentHashMap<String,JedisPool> map = JedisPoolUtils.getJedisPoolMaps();
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			lists.add(key);
		}
		
		return lists;
	}

}
