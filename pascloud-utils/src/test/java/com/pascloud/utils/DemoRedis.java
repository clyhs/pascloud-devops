package com.pascloud.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pascloud.utils.redis.RedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

public class DemoRedis {
	
	private static String ADDR = "192.168.0.7";
    
    //Redis的端口号
    private static int PORT = 6379;
     
    //访问密码
    private static String AUTH = "admin";
     
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_TOTAL = 1024;
     
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;
     
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;
     
    private static int TIMEOUT = 10000;
     
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
     
    private static JedisPool jedisPool = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		Jedis jedis = new Jedis("192.168.0.7");
		long size = jedis.dbSize();
		System.out.println(size);
		
		jedis.info();*/
		/*
		JedisPoolConfig jpc = new JedisPoolConfig();
		jpc.setMaxIdle(10);
		jpc.setMaxTotal(100);
		jpc.setMaxWaitMillis(10000);
		jpc.setTestOnBorrow(true);
		
		JedisShardInfo jsi = new JedisShardInfo("192.168.0.7",6379);
		List<JedisShardInfo> jsis = new ArrayList<>();
		jsis.add(jsi);
		ShardedJedisPool sJedisPool = new  ShardedJedisPool(jpc,jsis);
		
		RedisUtil redisUtil = new RedisUtil();
		redisUtil.setShardedJedisPool(sJedisPool);
		*/
		
		JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(MAX_TOTAL);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(15000);
        config.setTestOnBorrow(TEST_ON_BORROW);
        jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);

        Jedis J = DemoRedis.getJedis();
		
        //System.out.println(J.info());
        //J.select(15);
        //J.sadd("cly", "111");
        String str = J.info();
        String[] strs = str.split("\n");
        for(String s:strs){
        	if(s.indexOf(":")!=-1){
        		System.out.println(s);
        	}
        }
        

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
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

}
