package com.pascloud.bydr.world.tcp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.server.ServerState;
import com.pascloud.core.thread.ThreadType;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.ServerMessage.ServerRegisterResponse;

/**
 * 服务器注册消息返回
 * 
 */
@HandlerEntity(mid = MID.ServerRegisterRes_VALUE, msg = ServerRegisterResponse.class, thread = ThreadType.SYNC)
public class ServerRegisterHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerRegisterHandler.class);

	@Override
	public void run() {
		ServerRegisterResponse res = getMsg();
		if (res != null && res.getServerInfo() != null) {
			if (ServerState.MAINTAIN.getState() == res.getServerInfo().getState()) {
				LOGGER.warn("服务器状态变为维护");
			}
			// TODO 更新服务器状态信息，处理维护状态下的逻辑
		}
		// LOGGER.debug("更新服务器信息返回");
	}

}
