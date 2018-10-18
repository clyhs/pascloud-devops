package com.pascloud.bydr.server;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.thread.RoomExecutor;
import com.pascloud.core.mina.config.MinaClientConfig;
import com.pascloud.core.mina.message.IDMessage;
import com.pascloud.core.mina.service.MinaClientService;
import com.pascloud.core.mina.service.MutilMinaTcpClientService;
import com.pascloud.core.mina.service.MutilMinaTcpClientService.MutilTcpProtocolHandler;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.server.ServerInfo;
import com.pascloud.core.server.ServerState;
import com.pascloud.core.thread.ServerThread;
import com.pascloud.core.thread.ThreadPoolExecutorConfig;
import com.pascloud.core.thread.ThreadType;
import com.pascloud.core.thread.timer.event.ServerHeartTimer;
import com.pascloud.core.utils.SysUtil;
import com.pascloud.game.model.script.IGameServerCheckScript;
import com.pascloud.message.ServerMessage;
import com.pascloud.message.ServerMessage.ServerRegisterRequest;

public class Bydr2GateClient extends MutilMinaTcpClientService {
	private static final Logger LOGGER = LoggerFactory.getLogger(Bydr2GateClient.class);

	public Bydr2GateClient(ThreadPoolExecutorConfig threadPoolExecutorConfig, MinaClientConfig minaClientConfig) {
		super(threadPoolExecutorConfig, minaClientConfig);
	}

	@Override
	protected void running() {
		// 全局同步线程
		ServerThread syncThread = getExecutor(ThreadType.SYNC);
		syncThread.addTimerEvent(new ServerHeartTimer());

		// 添加房间线程池
		RoomExecutor roomExecutor = new RoomExecutor();
		getServerThreads().put(ThreadType.ROOM, roomExecutor);
	}

	/**
	 * 消息处理器
	 * 
	 */
	public class MutilConHallHandler extends MutilTcpProtocolHandler {

		public MutilConHallHandler(ServerInfo serverInfo, MinaClientService service) {
			super(serverInfo, service);
		}

		@Override
		public void sessionOpened(IoSession session) {
			super.sessionOpened(session);
			// 向网关服注册session
			ServerRegisterRequest.Builder builder = ServerRegisterRequest.newBuilder();
			ServerMessage.ServerInfo.Builder info = ServerMessage.ServerInfo.newBuilder();
			info.setId(getMinaClientConfig().getId());
			info.setIp("");
			info.setMaxUserCount(1000);
			info.setOnline(1);
			info.setName(getMinaClientConfig().getName());
			info.setState(ServerState.NORMAL.getState());
			info.setType(getMinaClientConfig().getType().getType());
			info.setWwwip("");
			info.setTotalMemory(SysUtil.totalMemory());
			info.setFreeMemory(SysUtil.freeMemory());
			ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IGameServerCheckScript.class,
					script -> script.buildServerInfo(info));
			builder.setServerInfo(info);
			session.write(new IDMessage(session, builder.build(), 0));
		}

	}

}