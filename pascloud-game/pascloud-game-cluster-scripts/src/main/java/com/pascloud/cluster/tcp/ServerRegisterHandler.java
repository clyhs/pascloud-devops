package com.pascloud.cluster.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.cluster.manager.ServerManager;
import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;

import com.pascloud.core.server.ServerType;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.ServerMessage.ServerInfo;
import com.pascloud.message.ServerMessage.ServerRegisterRequest;
import com.pascloud.message.ServerMessage.ServerRegisterResponse;

@HandlerEntity(mid = MID.ServerRegisterReq_VALUE, msg = ServerRegisterRequest.class)
public class ServerRegisterHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerRegisterHandler.class);

	@Override
	public void run() {
		ServerRegisterRequest req = getMsg();
		ServerInfo serverInfo = req.getServerInfo();
		LOGGER.info("服务器{}_{}注册", ServerType.valueof(serverInfo.getType()).toString(), serverInfo.getId());
	
		com.pascloud.core.server.ServerInfo info = ServerManager.getInstance().registerServer(serverInfo,getSession());
		ServerRegisterResponse.Builder builder = ServerRegisterResponse.newBuilder();
		ServerInfo.Builder infoBuilder=ServerInfo.newBuilder();
		infoBuilder.mergeFrom(serverInfo);
		//反向更新
		infoBuilder.setState(info.getState());
		
		builder.setServerInfo(infoBuilder);
		getSession().write(builder.build());
	}

}