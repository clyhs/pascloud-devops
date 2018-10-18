package com.pascloud.core.netty.service;

import com.pascloud.core.netty.NettyTcpClient;
import com.pascloud.core.netty.config.NettyClientConfig;

/**
 * Netty 单链接客户端
 * 
 */
public class SingleNettyTcpClientService extends NettyClientService {
	private final NettyTcpClient nettyTcpClient;

	public SingleNettyTcpClientService(NettyClientConfig nettyClientConfig) {
		super(nettyClientConfig);
		nettyTcpClient = new NettyTcpClient(this);
	}

	@Override
	protected void running() {
		nettyTcpClient.run();
	}

	public NettyTcpClient getNettyTcpClient() {
		return nettyTcpClient;
	}
	
	@Override
	public void checkStatus() {
		nettyTcpClient.checkStatus();
	}

	
}