package com.pascloud.game.model.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.mina.config.BaseServerConfig;
import com.pascloud.core.mina.config.MinaClientConfig;
import com.pascloud.core.netty.config.NettyClientConfig;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.server.IMutilTcpClientService;
import com.pascloud.core.server.ITcpClientService;
import com.pascloud.core.server.ServerState;
import com.pascloud.core.server.ServerType;
import com.pascloud.core.thread.timer.ScheduledTask;
import com.pascloud.core.utils.SysUtil;
import com.pascloud.game.model.script.IGameServerCheckScript;
import com.pascloud.message.ServerMessage.ServerInfo;
import com.pascloud.message.ServerMessage.ServerListRequest;
import com.pascloud.message.ServerMessage.ServerRegisterRequest;
import com.pascloud.message.ServerMessage.ServerRegisterRequest.Builder;

/**
 * 游戏服务器 状态监测，重连线程
 * <p>
 * 每隔10秒监测一次
 * </p>
 * @author admin
 *
 */
public class GameServerCheckTimer extends ScheduledTask {
	private static final Logger LOGGER = LoggerFactory.getLogger(GameServerCheckTimer.class);
	private ITcpClientService<? extends BaseServerConfig> clusterService; // 集群连接服务
	private IMutilTcpClientService<? extends BaseServerConfig> gateService; // 网关连接服务
	private BaseServerConfig config; // 游戏服配置

	/**
	 * 
	 * @param clusterService 连接集群客户端
	 * @param gateService 连接网关客户端，可为null
	 * @param config
	 */
	public GameServerCheckTimer(ITcpClientService<? extends BaseServerConfig> clusterService,
			IMutilTcpClientService<? extends BaseServerConfig> gateService, BaseServerConfig config) {
		super(10000);
		this.clusterService = clusterService;
		this.gateService = gateService;
		this.config = config;
	}

	@Override
	protected void executeTask() {
		// 向网关和集群注册游戏服务器信息
		Builder registerRequestBuilder = buildServerRegisterRequest(this.config);
		ServerRegisterRequest serverRegisterRequest = registerRequestBuilder.build();

		// 集群服
		clusterService.sendMsg(serverRegisterRequest);
		clusterService.checkStatus();

		// 网关服 监测连接到其他服务器客户端状态
		if (gateService != null) {
			if (!gateService.broadcastMsg(serverRegisterRequest)) {
				LOGGER.warn("大厅服未连接");
			}
			gateService.checkStatus();
		}

		// 获取可连接的网格列表
		ServerListRequest.Builder builder = ServerListRequest.newBuilder();
		builder.setServerType(ServerType.GATE.getType());
		clusterService.sendMsg(builder.build());
		// LOGGER.warn("更新服务器信息");
	}

	/**
	 * 构建服务器更新注册信息
	 * 
	 * @param minaServerConfig
	 * @return
	 */
	private ServerRegisterRequest.Builder buildServerRegisterRequest(BaseServerConfig baseServerConfig) {
		ServerRegisterRequest.Builder builder = ServerRegisterRequest.newBuilder();
		ServerInfo.Builder info = ServerInfo.newBuilder();
		info.setId(baseServerConfig.getId());
		info.setIp("");
		info.setMaxUserCount(1000);
		info.setName(baseServerConfig.getName());
		info.setState(ServerState.NORMAL.getState());
		info.setWwwip("");
		info.setVersion(baseServerConfig.getVersion());
		info.setTotalMemory(SysUtil.totalMemory());
		info.setFreeMemory(SysUtil.freeMemory());
		if (baseServerConfig instanceof MinaClientConfig) {
			MinaClientConfig minaClientConfig = (MinaClientConfig) baseServerConfig;
			info.setType(minaClientConfig.getType().getType());
		}else if(baseServerConfig instanceof NettyClientConfig) {
			NettyClientConfig nettyClientConfig=(NettyClientConfig) baseServerConfig;
			info.setType(nettyClientConfig.getType().getType());
		} else {
			throw new RuntimeException("服务器配置未实现");
		}
		ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IGameServerCheckScript.class,
				script -> script.buildServerInfo(info));
		builder.setServerInfo(info);
		return builder;
	}

}