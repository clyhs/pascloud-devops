package com.pascloud.hall.script.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.redis.IPubSubScript;
import com.pascloud.core.redis.jedis.JedisPubSubMessage;
import com.pascloud.game.model.redis.channel.HallChannel;


/**
 * 登录大厅脚本
 * <p>
 * 监听玩家登录其他大厅服务器，移除在本服务器的相关信息
 * </p>
 * 
 */
public class LoginHallScript implements IPubSubScript {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginHallScript.class);

	@Override
	public void onMessage(String channel, JedisPubSubMessage message) {
		if (!HallChannel.LoginHall.name().equals(channel)) {
			return;
		}
		LOGGER.debug(message.toString());
	}

}
