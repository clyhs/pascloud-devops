package com.pascloud.hall.script.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.mq.IMQScript;

/**
 * MQ 消息接收
 */
public class MQMsgReceiveScript implements IMQScript {
	private static final Logger LOGGER=LoggerFactory.getLogger(MQMsgReceiveScript.class);

	@Override
	public void onMessage(String msg) {
		LOGGER.info(msg);
	}

	
}
