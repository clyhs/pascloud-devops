package com.pascloud.core.redis.jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.pascloud.core.redis.IPubSubScript;
import com.pascloud.core.script.ScriptManager;

import redis.clients.jedis.JedisPubSub;

/**
 * Redis 监听事件
 * 
 */
public class JedisPubListener extends JedisPubSub implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(JedisPubListener.class);

	private String[] channels;

	public JedisPubListener(String... channels) {
		this.channels = channels;
	}

	@Override
	public void onMessage(String channel, String message) {
//		LOGGER.debug("JedisPubListener:{} --> {}", channel, message);
		try {
			JedisPubSubMessage jedisPubSubMessage = JSON.parseObject(message, JedisPubSubMessage.class);
			if (jedisPubSubMessage != null) {
				ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IPubSubScript.class,
						script -> script.onMessage(channel, jedisPubSubMessage));
			}

		} catch (Exception e) {
			LOGGER.error("JedisPubListener", e);
		}
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		super.onSubscribe(channel, subscribedChannels);
		LOGGER.info("onSubscribe：{},{}", channel, subscribedChannels);
	}

	public void start() {
		Thread thread = new Thread(this, "JedisPubSub");
		thread.start();
	}

	public void stop() {
		unsubscribe();
	}

	@Override
	public void run() {
		try {
			if (channels != null && channels.length > 0) {
				JedisManager.getJedisCluster().subscribe(this, this.channels);
			}
		} catch (Exception e) {
			LOGGER.error(null, e);
		}
	}

}
