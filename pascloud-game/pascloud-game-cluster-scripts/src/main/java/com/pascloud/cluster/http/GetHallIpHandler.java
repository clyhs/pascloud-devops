package com.pascloud.cluster.http;

import java.util.Map;

import java.util.Optional;
import com.pascloud.cluster.manager.ServerManager;
import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.HttpHandler;
import com.pascloud.core.server.ServerInfo;
import com.pascloud.core.server.ServerState;
import com.pascloud.core.server.ServerType;

@HandlerEntity(path = "/server/hall/ip")
public class GetHallIpHandler extends HttpHandler {

	@Override
	public void run() {
		try {
			
			Map<Integer, ServerInfo> servers = ServerManager.getInstance().getServers(ServerType.HALL);
			if (servers == null||servers.size()<1) {
				getParameter().appendBody("无可用大厅服");
			} else {
				Optional<ServerInfo> findFirst = servers.values().stream()
						.filter(server -> server.getState() == ServerState.NORMAL.ordinal() && server.getSession() != null
								&& server.getSession().isConnected())
						.sorted((s1, s2) -> s1.getOnline() - s2.getOnline()).findFirst();
				if (findFirst.isPresent()) {
					getParameter().appendBody(findFirst.get().getIp() + ":" + findFirst.get().getHttpPort());
				}else {
					getParameter().appendBody("无可用大厅服");
				}
			}
		} finally {
			response();
		}
	}

}
