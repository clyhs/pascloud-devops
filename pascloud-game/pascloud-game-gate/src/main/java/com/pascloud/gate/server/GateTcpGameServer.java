package com.pascloud.gate.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.mina.TcpServer;
import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.server.AbsService;
import com.pascloud.gate.server.handler.GateTcpGameServerHandler;

/**
 * 子游戏连接 服务
 * <p>游戏服、大厅服等内部共用的服务器</p>
 * @author admin
 *
 */
public class GateTcpGameServer extends AbsService<MinaServerConfig> {

	private static final Logger LOGGER = LoggerFactory.getLogger(GateTcpGameServer.class);
	private TcpServer tcpServer;
	private MinaServerConfig minaServerConfig;

	public GateTcpGameServer(MinaServerConfig minaServerConfig) {
		super(null);
		this.minaServerConfig = minaServerConfig;
		tcpServer = new TcpServer(minaServerConfig, new GateTcpGameServerHandler(this));
	}

	@Override
	protected void running() {
		tcpServer.run();
	}

	@Override
	protected void onShutdown() {
		super.onShutdown();
		tcpServer.stop();

	}

	public MinaServerConfig getMinaServerConfig() {
		return minaServerConfig;
	}
}
