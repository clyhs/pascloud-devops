package com.pascloud.core.mina.service;

import java.util.Map;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoHandler;

import com.pascloud.core.mina.client.MinaTcpClient;
import com.pascloud.core.mina.code.ProtocolCodecFactoryImpl;
import com.pascloud.core.mina.config.MinaClientConfig;
import com.pascloud.core.mina.handler.DefaultClientProtocolHandler;

/**
 * 单个tcp连接客户端
 *
 */
public class SingleMinaTcpClientService extends MinaClientService {
	private final MinaTcpClient tcpClient;
	
	
	public SingleMinaTcpClientService(MinaClientConfig minaClientConfig,ProtocolCodecFactoryImpl factory, IoHandler ioHandler,Map<String, IoFilter> filters) {
		super(minaClientConfig);
		tcpClient = new MinaTcpClient(this,  minaClientConfig, ioHandler,factory,filters);
	}

	
	/**
	 * 
	 * @param minaClientConfig
	 * @param ioHandler
	 *            消息处理器
	 */
	public SingleMinaTcpClientService(MinaClientConfig minaClientConfig,ProtocolCodecFactoryImpl factory, IoHandler ioHandler) {
		super(minaClientConfig);
		tcpClient = new MinaTcpClient(this,  minaClientConfig, ioHandler,factory);
	}

	/**
	 * 
	 * @param minaClientConfig
	 * @param ioHandler
	 *            消息处理器
	 */
	public SingleMinaTcpClientService(MinaClientConfig minaClientConfig, IoHandler ioHandler) {
		super(minaClientConfig);
		tcpClient = new MinaTcpClient(this, minaClientConfig, ioHandler);
	}

	/**
	 * 默认消息处理器
	 * 
	 * @param minaClientConfig
	 */
	public SingleMinaTcpClientService(MinaClientConfig minaClientConfig) {
		super(minaClientConfig);
		tcpClient = new MinaTcpClient(this, minaClientConfig, new DefaultClientProtocolHandler(this));
	}

	@Override
	protected void running() {
		tcpClient.run();

	}

	public MinaTcpClient getTcpClient() {
		return tcpClient;
	}

	@Override
	public void checkStatus() {
		tcpClient.checkStatus();
	}
	
	

}
