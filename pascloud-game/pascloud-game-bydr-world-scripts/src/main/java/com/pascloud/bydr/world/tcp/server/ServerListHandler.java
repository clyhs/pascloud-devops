package com.pascloud.bydr.world.tcp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.ServerMessage.ServerListResponse;

/***
 * 返回服务器列表
 * 
 */
@HandlerEntity(mid = MID.ServerListRes_VALUE, msg = ServerListResponse.class)
public class ServerListHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerListHandler.class);

	@Override
	public void run() {
	}

}