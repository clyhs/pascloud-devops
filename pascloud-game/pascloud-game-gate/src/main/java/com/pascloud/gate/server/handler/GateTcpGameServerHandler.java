package com.pascloud.gate.server.handler;

import java.util.Arrays;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.pascloud.core.mina.config.BaseServerConfig;
import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.mina.handler.DefaultProtocolHandler;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.server.AbsService;
import com.pascloud.core.utils.MsgUtil;
import com.pascloud.gate.manager.UserSessionManager;
import com.pascloud.gate.script.IGateServerScript;
import com.pascloud.gate.server.GateTcpGameServer;
import com.pascloud.gate.struct.UserSession;

/**
 * 游戏服、大厅服等内部共用的服务器
 */
public class GateTcpGameServerHandler extends DefaultProtocolHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GateTcpGameServerHandler.class);

	private AbsService<MinaServerConfig> service;

	public GateTcpGameServerHandler(AbsService<MinaServerConfig> service) {
		super(12);
		this.service = service;
	}

	/**
	 * 奖消息转发给游戏客户端
	 */
	@Override
	protected void forward(IoSession session, int msgID, byte[] bytes) {
		long rid = MsgUtil.getMessageRID(bytes, 0);
		if (rid > 0) {
			UserSession userSession = UserSessionManager.getInstance().getUserSessionbyRoleId(rid);
			if (userSession != null) {
//				LOGGER.debug("{} bytes:{}", bytes.length, bytes);
				//udp转发
				if(userSession.getClientUdpSession()!=null){
					if(ScriptManager.getInstance().getBaseScriptEntry().predicateScripts(IGateServerScript.class, (IGateServerScript script)->script.isUdpMsg(userSession.getServerType(),msgID))){
						userSession.sendToClientUdp(Arrays.copyOfRange(bytes, 8, bytes.length));
						return;
					}
				}
				
				//tcp返回
				userSession.sendToClient(Arrays.copyOfRange(bytes, 8, bytes.length)); // 前8字节为角色ID
				return;
			}
		}

		LOGGER.warn("消息[{}]未找到角色{}", msgID, rid);
	}

	@Override
	public AbsService<? extends BaseServerConfig> getService() {
		return this.service;
	}

	@Override
	public void sessionOpened(IoSession session) {
		super.sessionOpened(session);
	}

	
}
