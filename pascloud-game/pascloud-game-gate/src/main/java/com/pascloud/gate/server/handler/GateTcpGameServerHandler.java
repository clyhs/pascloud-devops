package com.pascloud.gate.server.handler;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.mina.config.BaseServerConfig;
import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.mina.handler.DefaultProtocolHandler;
import com.pascloud.core.server.AbsService;
import com.pascloud.core.utils.MsgUtil;
import com.pascloud.gate.server.GateTcpGameServer;

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
