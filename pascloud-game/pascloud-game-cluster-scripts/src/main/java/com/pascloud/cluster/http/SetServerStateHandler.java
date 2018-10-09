package com.pascloud.cluster.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.cluster.manager.ServerManager;
import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.HttpHandler;
import com.pascloud.core.server.ServerInfo;
import com.pascloud.core.server.ServerState;
import com.pascloud.core.server.ServerType;
import com.pascloud.core.utils.MsgUtil;
import com.pascloud.game.model.constant.Config;

@HandlerEntity(path = "/server/state")
public class SetServerStateHandler extends HttpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(SetServerStateHandler.class);

	@Override
	public void run() {
		String auth = getString("auth");
		if (!Config.SERVER_AUTH.equals(auth)) {
			sendMsg("验证失败");
			return;
		}

		int serverType = getInt("serverType");
		int serverId = getInt("serverId");
		int serverState = getInt("serverState");
		ServerInfo server = ServerManager.getInstance().getServer(ServerType.valueof(serverType), serverId);
		if (server == null) {
			sendMsg(String.format("服务器 %d %d 未启动", serverType, serverId));
			return;
		}
		server.setState(serverState);
		LOGGER.info("{}设置服务器{}_{} 状态：{}", MsgUtil.getIp(getSession()), serverType, serverId, serverState);
		sendMsg("服务器状态设置未：" + ServerState.valueOf(serverState));
	}
	
	

}