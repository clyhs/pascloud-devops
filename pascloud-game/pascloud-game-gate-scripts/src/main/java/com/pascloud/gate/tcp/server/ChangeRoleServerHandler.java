package com.pascloud.gate.tcp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.server.ServerInfo;
import com.pascloud.core.server.ServerType;
import com.pascloud.gate.manager.ServerManager;
import com.pascloud.gate.manager.UserSessionManager;
import com.pascloud.gate.struct.UserSession;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.ServerMessage.ChangeRoleServerRequest;
import com.pascloud.message.ServerMessage.ChangeRoleServerResponse;
import com.pascloud.message.hall.HallLoginMessage.LoginSubGameRequest;

/**
 * 修改角色在网关服所连接的其他服务器信息
 * 
 */
@HandlerEntity(mid = MID.ChangeRoleServerReq_VALUE, msg = ChangeRoleServerRequest.class)
public class ChangeRoleServerHandler extends TcpHandler {
	private static final Logger LOGGER=LoggerFactory.getLogger(ChangeRoleServerHandler.class);

	@Override
	public void run() {
		ChangeRoleServerRequest req = getMsg();
		ChangeRoleServerResponse.Builder builder = ChangeRoleServerResponse.newBuilder();
		builder.setResult(0);
		long roleId = req.getRoleId();
		UserSession userSession = UserSessionManager.getInstance().getUserSessionbyRoleId(roleId);
		LOGGER.debug("角色{}进行跨服",roleId);
		if (userSession == null) {
			builder.setResult(1);
		} else {
			ServerType serverType = ServerType.valueof(req.getServerType());
			ServerInfo serverInfo = null;
			if (req.getServerId() < 1) { // 找空闲服务器
				serverInfo = ServerManager.getInstance().getIdleGameServer(serverType,userSession);
			} else {
				serverInfo = ServerManager.getInstance().getGameServerInfo(serverType, req.getServerId());
			}
			if (serverInfo == null) {
				builder.setResult(2);
			} else {
				if (serverType == ServerType.HALL) {
					userSession.setHallServerId(serverInfo.getId());
					userSession.setHallSession(serverInfo.getMostIdleIoSession());
					//TODO 重发连接大厅消息
				} else {
					userSession.setGameSession(serverInfo.getMostIdleIoSession());
					userSession.setServerId(serverInfo.getId());
					userSession.setServerType(serverType);
					//发送登录消息
					LoginSubGameRequest.Builder loginGameBuilder=LoginSubGameRequest.newBuilder();
					loginGameBuilder.setGameType(serverType.getType());
					loginGameBuilder.setRid(roleId);
					loginGameBuilder.setType(2);
					userSession.sendToGame(loginGameBuilder.build());
					
				}
			}
		}
		LOGGER.debug("跨服结果：{}",builder.build().toString());
		sendIdMsg(builder.build());
	}

}
