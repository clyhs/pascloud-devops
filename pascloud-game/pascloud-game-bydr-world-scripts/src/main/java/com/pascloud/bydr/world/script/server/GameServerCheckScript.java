package com.pascloud.bydr.world.script.server;

import com.pascloud.bydr.manager.RoleManager;
import com.pascloud.bydr.world.server.BydrWorldServer;
import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.game.model.script.IGameServerCheckScript;

/**
 * 服务器状态监测脚本
 */
public class GameServerCheckScript implements IGameServerCheckScript {

	@Override
	public void buildServerInfo(com.pascloud.message.ServerMessage.ServerInfo.Builder builder) {
		IGameServerCheckScript.super.buildServerInfo(builder);
		MinaServerConfig minaServerConfig = BydrWorldServer.getInstance().getGameHttpServer().getMinaServerConfig();
		builder.setHttpport(minaServerConfig.getHttpPort());
		builder.setIp(minaServerConfig.getIp());
		builder.setOnline(RoleManager.getInstance().getOnlineRoles().size());	//设置在线人数
	}

}
