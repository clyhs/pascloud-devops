package com.pascloud.gate.tcp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.thread.ThreadType;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.ServerMessage.ServerRegisterResponse;


/**
 * 注册集群服返回
 *
 * @author JiangZhiYong
 * @date 2017-04-09 QQ:359135103
 */
@HandlerEntity(mid = MID.ServerRegisterRes_VALUE, msg = ServerRegisterResponse.class,thread=ThreadType.SYNC)
public class ServerRegisterResHandler extends TcpHandler {
	private static final Logger LOGGER=LoggerFactory.getLogger(ServerRegisterResHandler.class);

	@Override
	public void run() {
		
	
	}

}
