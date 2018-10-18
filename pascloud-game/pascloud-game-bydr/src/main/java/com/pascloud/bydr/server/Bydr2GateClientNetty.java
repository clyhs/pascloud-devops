package com.pascloud.bydr.server;

import com.pascloud.bydr.thread.RoomExecutor;
import com.pascloud.core.netty.config.NettyClientConfig;
import com.pascloud.core.netty.service.MutilNettyTcpClientService;
import com.pascloud.core.thread.ServerThread;
import com.pascloud.core.thread.ThreadPoolExecutorConfig;
import com.pascloud.core.thread.ThreadType;
import com.pascloud.core.thread.timer.event.ServerHeartTimer;

/**
 * netty 连接网关服
 * 
 */
public class Bydr2GateClientNetty extends MutilNettyTcpClientService {

	public Bydr2GateClientNetty(NettyClientConfig nettyClientConfig) {
		super(nettyClientConfig);
	}

	public Bydr2GateClientNetty(ThreadPoolExecutorConfig threadPoolExecutorConfig,
			NettyClientConfig nettyClientConfig) {
		super(threadPoolExecutorConfig, nettyClientConfig);
	}

	@Override
	protected void initThread() {
		super.initThread();
		// 全局同步线程
		ServerThread syncThread = getExecutor(ThreadType.SYNC);
		syncThread.addTimerEvent(new ServerHeartTimer());

		// 添加房间线程池
		RoomExecutor roomExecutor = new RoomExecutor();
		getServerThreads().put(ThreadType.ROOM, roomExecutor);
	}

}