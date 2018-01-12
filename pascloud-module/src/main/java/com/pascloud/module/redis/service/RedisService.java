package com.pascloud.module.redis.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSParser;

import com.pascloud.utils.redis.JedisPoolUtils;
import com.pascloud.vo.redis.RedisInfo;
import com.pascloud.vo.redis.RedisServerDetailInfo;

import redis.clients.jedis.Jedis;
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
	
	public List<String> getRedisDB(){
		List<String> lists = new ArrayList<String>();
		for(int i=0 ; i<defaultDBNum;i++){
			lists.add("db"+i);
		}
		return lists;
	}
	
	public List<RedisServerDetailInfo> getRedisInfo(String id){
		List<RedisServerDetailInfo> result = new ArrayList<RedisServerDetailInfo>();
		
		JedisPool jedisPool= JedisPoolUtils.getJedisPool(id);
		Jedis jedis= getJedis(jedisPool);
		
		String str = jedis.info();
		String[] lineStrs = str.split("\n");
		for(String line:lineStrs){
			if(line.indexOf(":")!=-1){
				RedisServerDetailInfo o = new RedisServerDetailInfo();
				String[] ls = line.split(":");
				o.setKey(ls[0]);
				o.setValue(ls[1]);
				result.add(o);
			}
		}
		returnResource(jedis,jedisPool);
		return result;
	}
	
	private synchronized Jedis getJedis(final JedisPool jedisPool) {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	private void returnResource(final Jedis jedis,final JedisPool jedisPool) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}
