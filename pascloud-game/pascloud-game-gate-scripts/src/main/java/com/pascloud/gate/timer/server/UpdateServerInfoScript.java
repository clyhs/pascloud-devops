package com.pascloud.gate.timer.server;

import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.script.ITimerEventScript;
import com.pascloud.core.server.ServerType;
import com.pascloud.gate.server.GateServer;
import com.pascloud.message.ServerMessage.ServerListRequest;
import com.pascloud.message.ServerMessage.ServerRegisterRequest;

/**
 * 更新服务器信息脚本
 *
 */
public class UpdateServerInfoScript implements ITimerEventScript {
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateServerInfoScript.class);

	@Override
	public void secondHandler(LocalTime localTime) {
		if (localTime.getSecond() % 5 == 0) { // 每5秒更新一次
			// 向服务器集群更新服务器信息
			MinaServerConfig minaServerConfig = GateServer.getInstance().getGateTcpUserServer().getMinaServerConfig();
			ServerRegisterRequest request = GateServer.getInstance().buildServerRegisterRequest(minaServerConfig);
			GateServer.getInstance().getClusterClient().sendMsg(request);
			// LOGGER.debug("更新服务器信息");
			// 重连服务器监测
			GateServer.getInstance().getClusterClient().getTcpClient().checkStatus();

			// 请求游戏服务器列表
			ServerListRequest.Builder builder = ServerListRequest.newBuilder();
			builder.setServerType(ServerType.GAME.getType());
			GateServer.getInstance().getClusterClient().sendMsg(builder.build());
		}
	}

}
