package com.pascloud.gate.tcp.server;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.gate.manager.ServerManager;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.ServerMessage.ServerListResponse;

/**
 * 返回游戏服务器列表
 */
@HandlerEntity(mid = MID.ServerListRes_VALUE, msg = ServerListResponse.class)
public class ServerListHandler extends TcpHandler {

	@Override
	public void run() {
		ServerListResponse response = getMsg();
		if (response != null) {
			response.getServerInfoList().forEach(info -> {
				ServerManager.getInstance().updateServerInfo(info);
			});
		}

	}

}
