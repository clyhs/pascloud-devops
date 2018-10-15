package com.pascloud.gate.tcp.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.utils.TimeUtil;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.system.SystemMessage.HeartRequest;
import com.pascloud.message.system.SystemMessage.HeartResponse;



/**
 * 心跳
 */
@HandlerEntity(mid=MID.HeartReq_VALUE,msg=HeartRequest.class)
public class HeartHandler extends TcpHandler{
	private static final Logger LOGGER=LoggerFactory.getLogger(HeartHandler.class);
	
	@Override
	public void run() {
		HeartResponse.Builder builder=HeartResponse.newBuilder();
		builder.setServerTime(TimeUtil.currentTimeMillis());
		session.write(builder.build());
		//LOGGER.info("{}心跳",MsgUtil.getIp(session));
	}

}
