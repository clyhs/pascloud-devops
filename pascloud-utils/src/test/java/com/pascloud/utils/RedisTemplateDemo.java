package com.pascloud.utils;

import java.util.List;

import org.springframework.data.redis.connection.SortParameters.Order;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

public class RedisTemplateDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(100);
		jedisPoolConfig.setMaxIdle(10);
		jedisPoolConfig.setMaxWaitMillis(10000);
	
		JedisConnectionFactory jedisFactory = new JedisConnectionFactory();
		jedisFactory.setHostName("192.168.0.7");
		jedisFactory.setPort(6379);
		jedisFactory.setPoolConfig(jedisPoolConfig);
		
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
		redisTemplate.setConnectionFactory(jedisFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		
		String key = "*";

		SortQueryBuilder<String> builder = SortQueryBuilder.sort(key);
		builder.alphabetical(true);
		builder.order(Order.DESC);
		builder.limit(0, 10);
		List<String> result = redisTemplate.sort(builder.build());
		for  (String item : result) {  
            System.out.println( "item..."  + item);  
        }
	}

}
