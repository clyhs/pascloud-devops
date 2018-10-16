package com.pascloud.hall.script.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.redis.IPubSubScript;
import com.pascloud.core.redis.jedis.JedisPubSubMessage;


/**
 * 游戏服更新大厅金币
 * @deprecated 子游戏直接操作redis
 */
public class GoldUpdateScript implements IPubSubScript {
	private static final Logger LOGGER=LoggerFactory.getLogger(GoldUpdateScript.class);

	@Override
	public void onMessage(String channel, JedisPubSubMessage message) {
//		if(!HallChannel.GoldUpdat.name().equals(channel)) {
//			return;
//		}
//		if(message.getServer()!=Config.SERVER_ID) {
//			return;
//		}
//		String key = HallKey.Role_Map_Info.getKey(message.getId());
//		
//		Long finalGold = JedisManager.getJedisCluster().hincrBy(key, "gold", message.getTarget());
//		LOGGER.debug("{}更新{}-->{}",key,message.getTarget(),finalGold);
	}

}
