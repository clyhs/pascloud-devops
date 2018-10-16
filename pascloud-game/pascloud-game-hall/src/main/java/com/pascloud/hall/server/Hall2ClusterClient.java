package com.pascloud.hall.server;

import com.pascloud.core.mina.config.MinaClientConfig;
import com.pascloud.core.mina.service.SingleMinaTcpClientService;

/**
 * 连接集群 tcp客户端
 */
public class Hall2ClusterClient extends SingleMinaTcpClientService{

	public Hall2ClusterClient(MinaClientConfig minaClientConfig) {
		super(minaClientConfig);
	}

	@Override
	protected void running() {
		super.running();
		// ServerThread executor = getExecutor(ThreadType.SYNC);
		// executor.addTimerEvent(new ServerHeartTimer()); //TODO 临时添加
	}
	
	

}
