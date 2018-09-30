package com.pascloud.core.redis;

import com.pascloud.core.redis.jedis.JedisPubSubMessage;
import com.pascloud.core.script.IScript;

public interface IPubSubScript extends IScript {

    /**
     * 消息处理
     * @param channel
     * @param message
     */
    public void onMessage(String channel,JedisPubSubMessage message);
}
