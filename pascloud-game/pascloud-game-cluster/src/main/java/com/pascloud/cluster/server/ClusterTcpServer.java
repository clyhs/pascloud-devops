package com.pascloud.cluster.server;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.mina.TcpServer;
import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.mina.handler.DefaultProtocolHandler;
import com.pascloud.core.server.AbsService;
import com.pascloud.core.thread.ServerThread;
import com.pascloud.core.thread.ThreadPoolExecutorConfig;
import com.pascloud.core.thread.ThreadType;
import com.pascloud.core.thread.timer.event.ServerHeartTimer;
import com.pascloud.core.utils.IntUtil;
import com.pascloud.core.utils.MsgUtil;
import com.pascloud.message.ServerMessage.ServerInfo;

public class ClusterTcpServer extends AbsService<MinaServerConfig> {
	
	private static final Logger log = LoggerFactory.getLogger(ClusterTcpServer.class);

	private final TcpServer minaServer;
	private final MinaServerConfig minaServerConfig;
	public static final String SERVER_INFO = "serverInfo"; // 服务器信息

	public ClusterTcpServer(ThreadPoolExecutorConfig threadExcutorConfig, MinaServerConfig minaServerConfig) {
		super(threadExcutorConfig);
		this.minaServerConfig = minaServerConfig;

		minaServer = new TcpServer(minaServerConfig, new ClusterTcpServerHandler(this));
	}


	@Override
	protected void running() {
		log.debug(" run ... ");
		minaServer.run();
		ServerThread syncThread = getExecutor(ThreadType.SYNC);
		syncThread.addTimerEvent(new ServerHeartTimer());
	}

	@Override
	protected void onShutdown() {
		super.onShutdown();
		log.debug(" stop ... ");
		minaServer.stop();
	}

	@Override
	public String toString() {
		return minaServerConfig.getName();
	}

	public int getId() {
		return minaServerConfig.getId();
	}

	public String getName() {
		return minaServerConfig.getName();
	}

	public String getWeb() {
		return minaServerConfig.getChannel();
	}
	
	public class ClusterTcpServerHandler extends DefaultProtocolHandler {
		private final AbsService<MinaServerConfig> service;

		public ClusterTcpServerHandler(AbsService<MinaServerConfig> service) {
			super(4); // 消息ID+消息内容
			this.service = service;
		}

		@Override
		public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) {
			MsgUtil.close(ioSession, "链接空闲:" + ioSession.toString() + " " + idleStatus.toString()); // 客户端长时间不发送请求，将断开链接LoginTcpServer->minaServerConfig->readerIdleTime
																									// 60
																									// 1分钟
		}

		@Override
		public void sessionClosed(IoSession ioSession) {
			super.sessionClosed(ioSession);
			ServerInfo serverInfo = (ServerInfo) ioSession.getAttribute(SERVER_INFO);
			if (serverInfo != null) {
				log.warn("服务器:" + serverInfo.getName() + "断开链接");
				//ServerManager.getInstance().removeServer(serverInfo);
			} else {
				log.error("未知游戏服务器断开链接");
			}
		}

		@Override
		protected void forward(IoSession session, int msgID, byte[] bytes) {
			log.warn("无法找到消息处理器：msgID{},bytes{}", msgID, IntUtil.BytesToStr(bytes));
		}

		@Override
		public AbsService<MinaServerConfig> getService() {
			return this.service;
		}

	}

}
