package com.pascloud.utils.redis;

import java.util.concurrent.ConcurrentHashMap;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import redis.clients.jedis.JedisPool;

public class JedisPoolUtils {
	
	private static  ConcurrentHashMap<String,JedisPool> jedisPoolMap = new ConcurrentHashMap<>();

	JedisPoolUtils(){}
	
	public static void addJedisPool(String id,JedisPool jedisPool){
		if(jedisPoolMap.get(id) == null && null != jedisPool){
			jedisPoolMap.put(id, jedisPool);
		}
	}
	
	public static JedisPool getJedisPool(String id){
		return jedisPoolMap.get(id);
	}
	
	public static ConcurrentHashMap getJedisPoolMaps(){
		return jedisPoolMap;
	}

}
