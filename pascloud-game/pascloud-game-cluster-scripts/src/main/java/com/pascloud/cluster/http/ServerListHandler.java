package com.pascloud.cluster.http;

import java.util.ArrayList;
import java.util.List;

import com.pascloud.cluster.manager.ServerManager;
import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.HttpHandler;
import com.pascloud.core.server.ServerInfo;

@HandlerEntity(path = "/server/list")
public class ServerListHandler extends HttpHandler {

	@Override
	public void run() {
		List<ServerInfo> servers = new ArrayList<>();
		ServerManager.getInstance().getServers().values().forEach(l -> l.forEach((id, serverInfo) -> {
			servers.add(serverInfo);
		}));
		sendMsg(servers);
	}

}