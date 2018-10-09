package com.pascloud.cluster.tcp;

import java.util.Map;

import com.pascloud.cluster.manager.ServerManager;
import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.server.ServerInfo;
import com.pascloud.core.server.ServerType;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.ServerMessage;
import com.pascloud.message.ServerMessage.ServerListRequest;
import com.pascloud.message.ServerMessage.ServerListResponse;

/**
 * 
 * @author admin
 *
 */
@HandlerEntity(mid = MID.ServerListReq_VALUE, msg = ServerListRequest.class)
public class ServerListHandler extends TcpHandler {

	
	
	@Override
	public void run() {
		ServerListRequest req = getMsg();
		if (req.getServerType() != ServerType.GAME.getType()) {
			Map<Integer, ServerInfo> servers = ServerManager.getInstance()
					.getServers(ServerType.valueof(req.getServerType()));
			if (servers != null) {
				ServerListResponse.Builder builder = ServerListResponse.newBuilder();
				ServerMessage.ServerInfo.Builder infoBuilder = ServerMessage.ServerInfo.newBuilder();
				servers.forEach((id, info) -> {
					if (info != null && info.getSession() != null && info.getSession().isConnected()) {
						builder.addServerInfo(buildServerInfo(info, infoBuilder));
					}
				});
				getSession().write(builder.build());
			}
		} else {
			// 只获取游戏服务器,大厅服（网关服需要获取大厅+所有游戏服务器信息）
			ServerListResponse.Builder builder = ServerListResponse.newBuilder();
			ServerMessage.ServerInfo.Builder infoBuilder = ServerMessage.ServerInfo.newBuilder();
			ServerManager.getInstance().getServers().forEach((serverType, s) -> {
				if (serverType.getType() > 100 || serverType == ServerType.HALL) {
					s.forEach((id, server) -> {
						if (server != null && server.getSession() != null && server.getSession().isConnected()) {
							builder.addServerInfo(buildServerInfo(server, infoBuilder));
						}
					});
				}
			});
			getSession().write(builder.build());
		}

	}

	/**
	 * 服务器信息
	 * 
	 * @param info
	 * @param builder
	 * @return
	 */
	private ServerMessage.ServerInfo buildServerInfo(ServerInfo serverInfo,
			ServerMessage.ServerInfo.Builder infoBuilder) {

		infoBuilder.clear();
		infoBuilder.setId(serverInfo.getId());
		infoBuilder.setHttpport(serverInfo.getHttpPort());
		infoBuilder.setIp(serverInfo.getIp());
		infoBuilder.setMaxUserCount(serverInfo.getMaxUserCount());
		infoBuilder.setName(serverInfo.getName());
		infoBuilder.setOnline(serverInfo.getOnline());
		infoBuilder.setPort(serverInfo.getPort());
		infoBuilder.setState(serverInfo.getState());
		infoBuilder.setType(serverInfo.getType());
		infoBuilder.setWwwip(serverInfo.getWwwip());

		return infoBuilder.build();
	}

}