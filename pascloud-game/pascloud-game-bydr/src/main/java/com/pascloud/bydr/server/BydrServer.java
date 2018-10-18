package com.pascloud.bydr.server;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.AppBydr;
import com.pascloud.core.mina.config.BaseServerConfig;
import com.pascloud.core.mina.config.MinaClientConfig;
import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.mina.service.ClientServerService;
import com.pascloud.core.mina.service.GameHttpSevice;
import com.pascloud.core.netty.config.NettyClientConfig;
import com.pascloud.core.netty.service.SingleNettyTcpClientService;
import com.pascloud.core.redis.jedis.JedisPubListener;
import com.pascloud.core.server.AbsService;
import com.pascloud.core.server.IMutilTcpClientService;
import com.pascloud.core.server.ITcpClientService;
import com.pascloud.core.server.ServerInfo;
import com.pascloud.core.thread.ThreadPoolExecutorConfig;
import com.pascloud.core.thread.ThreadType;
import com.pascloud.core.utils.FileUtil;
import com.pascloud.game.model.constant.Config;
import com.pascloud.game.model.constant.NetPort;
import com.pascloud.game.model.redis.channel.BydrChannel;
import com.pascloud.game.model.timer.GameServerCheckTimer;
import com.pascloud.message.ServerMessage;

public class BydrServer implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(BydrServer.class);

	/** 连接网关 （接收网关转发过来的消息） */
	private IMutilTcpClientService<? extends BaseServerConfig> bydr2GateClient;

	/** 连接集群服 （获取各服务器信息） */
	private ITcpClientService<? extends BaseServerConfig> bydr2ClusterClient;

	/** 游戏前端消息服务 （消息直接从手机前端发来,如果没有直接注释掉，不经过大厅网关转发,暂时用engine封装类） */
	private ClientServerService bydrTcpServer;

	/** http服务器 */
	private BydrHttpServer gameHttpServer;

	/** 服务器状态监测 */
	private GameServerCheckTimer gameServerCheckTimer;

	/** redis订阅发布 */
	private final JedisPubListener bydrPubListener;

	public BydrServer(String configPath) {
		ThreadPoolExecutorConfig threadPoolExecutorConfig = FileUtil.getConfigXML(configPath,
				"threadPoolExecutorConfig.xml", ThreadPoolExecutorConfig.class);
		if (threadPoolExecutorConfig == null) {
			LOGGER.error("{}/threadPoolExecutorConfig.xml未找到", configPath);
			System.exit(0);
		}

		// 加载连接网关配置
		MinaClientConfig minaClientConfig_gate = FileUtil.getConfigXML(configPath, "minaClientConfig_gate.xml",
				MinaClientConfig.class);

		NettyClientConfig nettyClientConfig_gate = FileUtil.getConfigXML(configPath,"nettyClientConfig_gate.xml",NettyClientConfig.class);
		if (minaClientConfig_gate == null&&nettyClientConfig_gate==null ) {
			LOGGER.error("{}未配置网关连接客户端", configPath);
			System.exit(0);
		}

		// 加载连接集群配置
		MinaClientConfig minaClientConfig_cluster = FileUtil.getConfigXML(configPath, "minaClientConfig_cluster.xml",
				MinaClientConfig.class);

		NettyClientConfig nettyClinetConfig_cluster = FileUtil.getConfigXML(configPath, "nettyClientConfig_cluster.xml",NettyClientConfig.class);
		if (minaClientConfig_cluster == null && nettyClinetConfig_cluster==null) {
			LOGGER.error("{}未配置集群连接客户端", configPath);
			System.exit(0);
		}

		// HTTP通信
		MinaServerConfig minaServerConfig_http = FileUtil.getConfigXML(configPath, "minaServerConfig_http.xml",
				MinaServerConfig.class);

		gameHttpServer = new BydrHttpServer(minaServerConfig_http);

		// 游戏前端消息服务 配置为空，不开启，开启后消息可以不经过网关直接发送到本服务器
		MinaServerConfig minaServerConfig = FileUtil.getConfigXML(configPath, "minaServerConfig.xml",
				MinaServerConfig.class);
		if (minaServerConfig != null) {
			this.bydrTcpServer = new ClientServerService(minaServerConfig);
		}

		//如果netty 优先级高，使用Netty服务,一般不直接使用engine提供的类
		// 网关
		if (nettyClientConfig_gate != null && "NettyFirst".equalsIgnoreCase(nettyClientConfig_gate.getInfo())) {
			// TODO 需要重写channelActive 发送服务器注册消息 ，不然相当于当前客户端和网关只有一个channel连接
			this.bydr2GateClient = new Bydr2GateClientNetty(threadPoolExecutorConfig, nettyClientConfig_gate);
		} else {
			this.bydr2GateClient = new Bydr2GateClient(threadPoolExecutorConfig, minaClientConfig_gate);
		}

		// 集群
		if (nettyClinetConfig_cluster != null && "NettyFirst".equalsIgnoreCase(nettyClinetConfig_cluster.getInfo())) {
			bydr2ClusterClient = new SingleNettyTcpClientService(nettyClinetConfig_cluster);
		} else {
			this.bydr2ClusterClient = new Bydr2ClusterClient(minaClientConfig_cluster);
		}

		// 状态监控
		this.gameServerCheckTimer = new GameServerCheckTimer(bydr2ClusterClient, bydr2GateClient,
				bydr2GateClient instanceof Bydr2GateClient ? minaClientConfig_gate : nettyClientConfig_gate);
		//this.gameServerCheckTimer = new GameServerCheckTimer(bydr2ClusterClient, bydr2GateClient,minaClientConfig_gate);
		// 订阅发布
		this.bydrPubListener = new JedisPubListener(BydrChannel.getChannels());

		// 设置配置相关常量
		Config.SERVER_ID = minaClientConfig_gate.getId();
		Config.SERVER_NAME = minaClientConfig_gate.getName();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		new Thread(this.bydr2GateClient).start();
		new Thread(this.bydr2ClusterClient).start();

		if (bydrTcpServer != null) {
			new Thread(this.bydrTcpServer).start();
		}
		this.gameServerCheckTimer.start();
		new Thread(this.gameHttpServer).start();
		new Thread(bydrPubListener).start();
	}

	public static BydrServer getInstance() {
		return AppBydr.getBydrServer();
	}

	public IMutilTcpClientService<? extends BaseServerConfig> getBydr2GateClient() {
		return bydr2GateClient;
	}

	/**
	 * 获取线程 在连接网关服的service中获取
	 * 
	 * @param threadType
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends Executor> T getExecutor(ThreadType threadType) {
		return (T) ((AbsService) bydr2GateClient).getExecutor(threadType);
	}

	public ITcpClientService<? extends BaseServerConfig> getBydr2ClusterClient() {
		return bydr2ClusterClient;
	}

	public GameHttpSevice getGameHttpServer() {
		return gameHttpServer;
	}

	/**
	 * 更新可用网关服务器信息
	 * 
	 * @param info
	 */
	public void updateGateServerInfo(ServerMessage.ServerInfo info) {
		ServerInfo serverInfo = getBydr2GateClient().getServers().get(info.getId());
		if (serverInfo == null) {
			serverInfo = getServerInfo(info);
			if (getBydr2GateClient() instanceof Bydr2GateClient) {
				Bydr2GateClient service = (Bydr2GateClient) getBydr2GateClient();
				service.addTcpClient(serverInfo, NetPort.GATE_GAME_PORT,
						service.new MutilConHallHandler(serverInfo, service)); // TODO
																				// 暂时，网关服有多个tcp端口
			} else {
				getBydr2GateClient().addTcpClient(serverInfo, NetPort.GATE_GAME_PORT);
			}
		} else {
			serverInfo.setIp(info.getIp());
			serverInfo.setId(info.getId());
			serverInfo.setPort(info.getPort());
			serverInfo.setState(info.getState());
			serverInfo.setOnline(info.getOnline());
			serverInfo.setMaxUserCount(info.getMaxUserCount());
			serverInfo.setName(info.getName());
			serverInfo.setHttpPort(info.getHttpport());
			serverInfo.setWwwip(info.getWwwip());
		}
		getBydr2GateClient().getServers().put(info.getId(), serverInfo);
	}

	/**
	 * 消息转换
	 * 
	 * @param info
	 * @return
	 */
	private ServerInfo getServerInfo(ServerMessage.ServerInfo info) {
		ServerInfo serverInfo = new ServerInfo();
		serverInfo.setIp(info.getIp());
		serverInfo.setId(info.getId());
		serverInfo.setPort(info.getPort());
		serverInfo.setState(info.getState());
		serverInfo.setOnline(info.getOnline());
		serverInfo.setMaxUserCount(info.getMaxUserCount());
		serverInfo.setName(info.getName());
		serverInfo.setHttpPort(info.getHttpport());
		serverInfo.setWwwip(info.getWwwip());
		serverInfo.setFreeMemory(info.getFreeMemory());
		serverInfo.setTotalMemory(info.getTotalMemory());
		serverInfo.setVersion(info.getVersion());
		return serverInfo;
	}

}
