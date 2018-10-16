package com.pascloud.hall.script.server;

import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.game.model.script.IGameServerCheckScript;
import com.pascloud.hall.manager.RoleManager;
import com.pascloud.hall.server.HallServer;

/**
 * 服务器状态监测脚本
 */
public class GameServerCheckScript implements IGameServerCheckScript {

	@Override
	public void buildServerInfo(com.pascloud.message.ServerMessage.ServerInfo.Builder builder) {
		IGameServerCheckScript.super.buildServerInfo(builder);
		MinaServerConfig minaServerConfig = HallServer.getInstance().getHallHttpServer().getMinaServerConfig();
		builder.setHttpport(minaServerConfig.getHttpPort());
		builder.setIp(minaServerConfig.getIp());
		builder.setOnline(RoleManager.getInstance().getRoles().size());	//设置在线人数 TODO
	}

}
