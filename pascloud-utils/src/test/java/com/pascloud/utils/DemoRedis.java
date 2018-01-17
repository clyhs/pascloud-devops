package com.pascloud.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pascloud.utils.redis.RedisUtil;

import net.sf.json.groovy.GJson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

public class DemoRedis {

	private static String ADDR = "192.168.0.7";

	// Redis的端口号
	private static int PORT = 6379;

	// 访问密码
	private static String AUTH = "admin";

	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_TOTAL = 1024;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 200;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;

	private static int TIMEOUT = 10000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * Jedis jedis = new Jedis("192.168.0.7"); long size = jedis.dbSize();
		 * System.out.println(size);
		 * 
		 * jedis.info();
		 */
		/*
		 * JedisPoolConfig jpc = new JedisPoolConfig(); jpc.setMaxIdle(10);
		 * jpc.setMaxTotal(100); jpc.setMaxWaitMillis(10000);
		 * jpc.setTestOnBorrow(true);
		 * 
		 * JedisShardInfo jsi = new JedisShardInfo("192.168.0.7",6379);
		 * List<JedisShardInfo> jsis = new ArrayList<>(); jsis.add(jsi);
		 * ShardedJedisPool sJedisPool = new ShardedJedisPool(jpc,jsis);
		 * 
		 * RedisUtil redisUtil = new RedisUtil();
		 * redisUtil.setShardedJedisPool(sJedisPool);
		 */

		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(MAX_TOTAL);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxWaitMillis(15000);
		config.setTestOnBorrow(TEST_ON_BORROW);
		jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);

		Jedis J = DemoRedis.getJedis();
		// J.select(0);

		// System.out.println(J.info());
		// J.select(15);
		// J.sadd("cly", "111");
		SortingParams sortingParameters = new SortingParams();
		sortingParameters.limit(0, 20);
		sortingParameters.by("*");
		List<String> result = J.sort("*", sortingParameters);
		for (String s : result) {
			System.out.println(s);
		}
		// List<String> result = J.lrange("*", 0, 10);
		// J.
		// for (String item : result) {
		// System.out.println( "item..." + item);
		// }
		/*
		 * Set<String> sets = J.keys("*"); Iterator it = sets.iterator();
		 * 
		 * while (it.hasNext()) { String key = (String) it.next(); String value
		 * = J.get(key); System.out.println(key + value); }
		 */
		/*
		 * String cursor = ScanParams.SCAN_POINTER_START; List<String> list =
		 * new ArrayList<>(); ScanParams scanParams = new ScanParams();
		 * 
		 * scanParams.count(10);
		 * 
		 * scanParams.match("*");
		 * 
		 * ScanResult<String> scanResult = J.scan(cursor, scanParams);
		 * list.addAll(scanResult.getResult()); cursor =
		 * scanResult.getStringCursor();
		 * 
		 * for(String s :list){ System.out.println(s); String value = J.get(s);
		 * System.out.println(s + value); }
		 */
		// J.zrange("*", 0, -1).size(); /*
		/*
		 * System.out.println(J.zrange("parser_component*", 0, 10).size());
		 * 
		 * System.out.println(J.lrange("parser_component*", 0, 10).size());
		 */
		Map<String, Object> tempMap = new HashMap();

		List<Map<String, Object>> list = new ArrayList();
		int limitFrom = 0, pageSize = 50;
		String selectKey = "nokey";
		Long dbSize = J.dbSize();
		Set nodekeys = new HashSet();
		if (selectKey.equals("nokey")) {
			if (dbSize.longValue() > 1000L) {
				limitFrom = 0;
				for (int z = 0; z < pageSize; z++) {
					nodekeys.add(J.randomKey());
				}
			} else {
				nodekeys = J.keys("*");
			}
		} else {
			nodekeys = J.keys("*" + selectKey + "*");
		}
		Iterator it = nodekeys.iterator();
		int i = 1;
		String value = "";
		while (it.hasNext()) {
			if ((i >= limitFrom) && (i <= limitFrom + pageSize)) {
				Map<String, Object> map = new HashMap();
				String key = (String) it.next();
				System.out.println(key);
				String type = J.type(key);
				map.put("key", key);
				map.put("type", type);
				if (type.equals("string")) {
					value = J.get(key);
					if (value.length() > 80) {
						map.put("value", value.substring(0, 79) + "......");
					} else {
						map.put("value", value);
					}
				}
				if (type.equals("list")) {
					Long lon = J.llen(key);
					if (lon.longValue() > 20L) {
						lon = Long.valueOf(20L);
					}
					map.put("value", J.lrange(key, 0L, lon.longValue()));
				}
				if (type.equals("set")) {
					map.put("value", J.smembers(key).toString());
				}
				if (type.equals("zset")) {
					Long lon = J.zcard(key);
					if (lon.longValue() > 20L) {
						lon = Long.valueOf(20L);
					}
					Set<Tuple> set = J.zrangeWithScores(key, 0L, lon.longValue());
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
					map.put("value", J.hgetAll(key).toString());
				}
				list.add(map);
			} else {
				it.next();
			}
			i++;
		}
		if (selectKey.equals("nokey")) {
			tempMap.put("rowCount", Integer.valueOf(Integer.parseInt(dbSize.toString())));
		} else {
			tempMap.put("rowCount", Integer.valueOf(i));
		}
		tempMap.put("dataList", list);
		

	}

	public synchronized static Jedis getJedis() {
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

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

}
