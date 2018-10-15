package com.pascloud.gate.tcp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.server.ServerInfo;
import com.pascloud.core.server.ServerType;
import com.pascloud.core.thread.ThreadType;
import com.pascloud.gate.manager.ServerManager;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.ServerMessage;
import com.pascloud.message.ServerMessage.ServerRegisterRequest;

/**
 * 游戏服循环注册更新
 *
 */
@HandlerEntity(mid = MID.ServerRegisterReq_VALUE, msg = ServerRegisterRequest.class, thread = ThreadType.SYNC)
public class ServerRegisterHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerRegisterHandler.class);

	@Override
	public void run() {
		ServerRegisterRequest req = getMsg();
		ServerMessage.ServerInfo serverInfo = req.getServerInfo();
		ServerInfo gameInfo = ServerManager.getInstance().getGameServerInfo(ServerType.valueof(serverInfo.getType()),
				serverInfo.getId());
		if (gameInfo != null) {
			gameInfo.onIoSessionConnect(session);
			LOGGER.info("服务器：{} 连接注册到网关服 {} ip:{}", gameInfo.getName(), getSession().getId(),
					getSession().getRemoteAddress().toString());
		} else {
			LOGGER.warn("网关服务没有{}服:{}",ServerType.valueof(serverInfo.getType()).toString(), serverInfo.getId());
		}

	}

}
