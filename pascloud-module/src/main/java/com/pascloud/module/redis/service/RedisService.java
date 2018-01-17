package com.pascloud.module.redis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSParser;

import com.pascloud.utils.redis.JedisPoolUtils;
import com.pascloud.vo.redis.RedisInfo;
import com.pascloud.vo.redis.RedisServerDetailInfo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

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
	
	public Map<String, Object> getDBPageData(String id,Integer index,Integer startRow,Integer pageSize,
			String selectKey){
		Map<String, Object> tempMap = new HashMap();
		List<Map<String, Object>> list = new ArrayList();
		try{
			JedisPool jedisPool= JedisPoolUtils.getJedisPool(id);
			Jedis jedis= getJedis(jedisPool);
			jedis.select(index);
			//total
			Long total = jedis.dbSize();
			Set nodekeys = new HashSet();
			if (selectKey.equals("nokey")) {
				if (total.longValue() > 1000L) {
					startRow = 0;
					for (int z = 0; z < pageSize; z++) {
						nodekeys.add(jedis.randomKey());
					}
				} else {
					nodekeys = jedis.keys("*");
				}
			} else {
				nodekeys = jedis.keys("*" + selectKey + "*");
			}
			
			Iterator it = nodekeys.iterator();
			int i = 1;
			String value = "";
			while (it.hasNext()) {
				if ((i >= startRow) && (i <= startRow + pageSize)) {
					Map<String, Object> map = new HashMap();
					String key = (String) it.next();
					String type = jedis.type(key);
					map.put("key", key);
					map.put("type", type);
					if (type.equals("string")) {
						value = jedis.get(key);
						if (value.length() > 80) {
							map.put("value", value.substring(0, 79) + "......");
						} else {
							map.put("value", value);
						}
					}
					if (type.equals("list")) {
						Long lon = jedis.llen(key);
						if (lon.longValue() > 20L) {
							lon = Long.valueOf(20L);
						}
						map.put("value", jedis.lrange(key, 0L, lon.longValue()));
					}
					if (type.equals("set")) {
						map.put("value", jedis.smembers(key).toString());
					}
					if (type.equals("zset")) {
						Long lon = jedis.zcard(key);
						if (lon.longValue() > 20L) {
							lon = Long.valueOf(20L);
						}
						Set<Tuple> set = jedis.zrangeWithScores(key, 0L, lon.longValue());
						Iterator<Tuple> itt = set.iterator();
						String ss = "";
						while (itt.hasNext()) {
							Tuple str = (Tuple) itt.next();
							ss = ss + "[" + str.getScore() + "," + str.getElement() + "],";
						}
						ss = ss.substring(0, ss.length() - 1);
						map.put("value", "[" + ss + "]");
					}
					if (type.equals("hash")) {
						map.put("value", jedis.hgetAll(key).toString());
					}
					list.add(map);
				} else {
					it.next();
				}
				i++;
			}
			
			if (selectKey.equals("nokey")) {
				tempMap.put("total", Integer.valueOf(Integer.parseInt(total.toString())));
			} else {
				tempMap.put("total", Integer.valueOf(i));
			}
			tempMap.put("rows", list);
			returnResource(jedis,jedisPool);
		}catch (Exception e){
			log.error(e.getMessage());
		}finally{
			
		}
		
		return tempMap;
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
