package com.pascloud.core.mina.service;

import java.util.Map;

import org.apache.mina.core.filterchain.IoFilter;

import com.pascloud.core.mina.TcpServer;
import com.pascloud.core.mina.code.ClientProtocolCodecFactory;
import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.mina.handler.ClientProtocolHandler;
import com.pascloud.core.server.AbsService;
import com.pascloud.core.thread.ServerThread;
import com.pascloud.core.thread.ThreadPoolExecutorConfig;
import com.pascloud.core.thread.ThreadType;
import com.pascloud.core.thread.timer.event.ServerHeartTimer;

/**
 * 游戏前端消息接收 服务
 * @author admin
 *
 */
public class ClientServerService extends AbsService<MinaServerConfig> {

	protected TcpServer tcpServer;
	protected MinaServerConfig minaServerConfig;
	protected ClientProtocolHandler clientProtocolHandler;

	
	/**
	 * 不创建默认IO线程池
	 * @param minaServerConfig
	 */
	public ClientServerService(MinaServerConfig minaServerConfig){
		this(null, minaServerConfig);
	}
	
	/**
	 * 使用默认消息处理器
	 * @param threadExcutorConfig
	 * @param minaServerConfig
	 */
	public ClientServerService(ThreadPoolExecutorConfig threadExcutorConfig, MinaServerConfig minaServerConfig) {
		this(threadExcutorConfig, minaServerConfig, new ClientProtocolHandler(8));
	}

	/**
	 * 
	 * @param threadExcutorConfig 线程池配置
	 * @param minaServerConfig 服务器配置
	 * @param ioHandler
	 *            消息处理器
	 */
	public ClientServerService(ThreadPoolExecutorConfig threadExcutorConfig, MinaServerConfig minaServerConfig,
			ClientProtocolHandler clientProtocolHandler) {
		super(threadExcutorConfig);
		this.minaServerConfig = minaServerConfig;
		this.clientProtocolHandler = clientProtocolHandler;
		tcpServer = new TcpServer(minaServerConfig, clientProtocolHandler, new ClientProtocolCodecFactory());
	}
	
	/**
	 * 
	 * @param threadExcutorConfig 线程池配置
	 * @param minaServerConfig 服务器配置
	 * @param ioHandler
	 *            消息处理器
	 */
	public ClientServerService(ThreadPoolExecutorConfig threadExcutorConfig, MinaServerConfig minaServerConfig,
			ClientProtocolHandler clientProtocolHandler,Map<String, IoFilter> filters) {
		super(threadExcutorConfig);
		this.minaServerConfig = minaServerConfig;
		this.clientProtocolHandler = clientProtocolHandler;
		tcpServer = new TcpServer(minaServerConfig, clientProtocolHandler, new ClientProtocolCodecFactory(),filters);
	}
	
	

	@Override
	protected void running() {
		clientProtocolHandler.setService(this);
		tcpServer.run();
		// 添加定时器 ,如果心跳配置为0，则没有定时器
		ServerThread syncThread = this.getExecutor(ThreadType.SYNC);
		if(syncThread!=null){
			syncThread.addTimerEvent(new ServerHeartTimer());
		}
	}

	@Override
	protected void onShutdown() {
		super.onShutdown();
		tcpServer.stop();

	}

	public MinaServerConfig getMinaServerConfig() {
		return minaServerConfig;
	}

	public TcpServer getTcpServer() {
		return tcpServer;
	}
}
