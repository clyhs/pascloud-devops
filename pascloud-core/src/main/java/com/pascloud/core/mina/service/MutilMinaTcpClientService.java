package com.pascloud.core.mina.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IoSession;

import com.pascloud.core.mina.client.MinaMultiTcpClient;
import com.pascloud.core.mina.config.MinaClientConfig;
import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.mina.handler.DefaultClientProtocolHandler;
import com.pascloud.core.mina.message.IDMessage;
import com.pascloud.core.server.IMutilTcpClientService;
import com.pascloud.core.server.ServerInfo;
import com.pascloud.core.server.ServerType;
import com.pascloud.core.thread.ThreadPoolExecutorConfig;
/**
 * 多tcp连接客户端
 * @author admin
 *
 */
public class MutilMinaTcpClientService extends MinaClientService implements IMutilTcpClientService<MinaServerConfig>{
	protected MinaMultiTcpClient multiTcpClient = new MinaMultiTcpClient();
	/** 网关服务器 */
	protected Map<Integer, ServerInfo> serverMap = new ConcurrentHashMap<>();

	public MutilMinaTcpClientService(MinaClientConfig minaClientConfig) {
		super(minaClientConfig);
	}

	public MutilMinaTcpClientService(ThreadPoolExecutorConfig threadPoolExecutorConfig, MinaClientConfig minaClientConfig) {
		super(threadPoolExecutorConfig, minaClientConfig);
	}

	@Override
	protected void running() {

	}

	/**
	 * 移除客户端
	 * 
	 * @param serverId
	 */
	public void removeTcpClient(int serverId) {
		multiTcpClient.removeTcpClient(serverId);
		serverMap.remove(serverId);
	}

	/**
	 * 添加连接服务器
	 * 
	 * @param serverInfo
	 */
	public void addTcpClient(ServerInfo serverInfo, int port) {
		addTcpClient(serverInfo, port, new MutilTcpProtocolHandler(serverInfo, this));
//		if (multiTcpClient.containsKey(serverInfo.getId())) {
//			return;
//		}
//		MinaClientConfig hallMinaClientConfig = createMinaClientConfig(serverInfo, port);
//		multiTcpClient.addTcpClient(this, hallMinaClientConfig, new MutilTcpProtocolHandler(serverInfo, this));
	}
	
	/**
	 * 添加连接大厅服务器
	 * 
	 * @param serverInfo
	 */
	public void addTcpClient(ServerInfo serverInfo, int port,MutilTcpProtocolHandler ioHandler) {
		if (multiTcpClient.containsKey(serverInfo.getId())) {
			return;
		}
		MinaClientConfig hallMinaClientConfig = createMinaClientConfig(serverInfo, port);
		multiTcpClient.addTcpClient(this, hallMinaClientConfig,ioHandler);
	}

	/**
	 * 创建连接网关配置文件
	 * 
	 * @param serverInfo
	 * @param port
	 * @return
	 */
	private MinaClientConfig createMinaClientConfig(ServerInfo serverInfo, int port) {
		MinaClientConfig conf = new MinaClientConfig();
		conf.setType(ServerType.GATE);
		conf.setId(serverInfo.getId());
		conf.setMaxConnectCount(this.getMinaClientConfig().getMaxConnectCount());
		conf.setOrderedThreadPoolExecutorSize(this.getMinaClientConfig().getOrderedThreadPoolExecutorSize());
		MinaClientConfig.MinaClienConnToConfig con = new MinaClientConfig.MinaClienConnToConfig();
		con.setHost(serverInfo.getIp());
		con.setPort(port);
		conf.setConnTo(con);
		return conf;
	}

	public Map<Integer, ServerInfo> getServers() {
		return serverMap;
	}

	/**
	 * 监测连接状态
	 */
	public void checkStatus() {
		multiTcpClient.getTcpClients().values().forEach(cl -> cl.checkStatus());
	}

	/**
	 * 广播所有服务器消息：注意，这里并不是向每个session广播，因为有可能有多个连接到同一个服务器
	 *
	 * @param obj
	 * @return
	 */
	public boolean broadcastMsg(Object obj) {
		if (multiTcpClient == null) {
			return false;
		}
		IDMessage idm = new IDMessage(null, obj, 0);
		serverMap.values().forEach(server -> {
			server.sendMsg(idm);
		});
		return true;
	}
	
	/**
	 * 广播所有服务器消息：注意，这里并不是向每个session广播，因为有可能有多个连接到同一个服务器
	 *
	 * @param obj
	 * @return
	 */
	public boolean broadcastMsg(Object obj,long rid) {
		if (multiTcpClient == null) {
			return false;
		}
		IDMessage idm = new IDMessage(null, obj, rid);
		serverMap.values().forEach(server -> {
			server.sendMsg(idm);
		});
		return true;
	}


	/**
	 * 发送消息
	 * 
	 * @param serverId
	 *            目标服务器ID
	 * @param msg
	 * @return
	 */
	public boolean sendMsg(Integer serverId, Object msg) {
		if (multiTcpClient == null) {
			return false;
		}
		IDMessage idm = new IDMessage(null, msg, 0);
		return multiTcpClient.sendMsg(serverId, idm);
	}

	/**
	 * 多连接消息处理器
	 * 
	 * @author JiangZhiYong
	 * @QQ 359135103 2017年7月4日 下午3:22:48
	 */
	public class MutilTcpProtocolHandler extends DefaultClientProtocolHandler {

		private ServerInfo serverInfo;

		public MutilTcpProtocolHandler(ServerInfo serverInfo, MinaClientService service) {
			super(12, service);
			this.serverInfo = serverInfo;
		}

		@Override
		public void sessionOpened(IoSession session) {
			super.sessionOpened(session);
			serverInfo.onIoSessionConnect(session);
		}

	}

}