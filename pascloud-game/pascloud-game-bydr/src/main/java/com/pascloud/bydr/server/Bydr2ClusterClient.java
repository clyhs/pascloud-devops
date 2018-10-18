package com.pascloud.bydr.server;

import com.pascloud.core.mina.config.MinaClientConfig;
import com.pascloud.core.mina.service.SingleMinaTcpClientService;

public class Bydr2ClusterClient extends SingleMinaTcpClientService{

	public Bydr2ClusterClient(MinaClientConfig minaClientConfig) {
		super(minaClientConfig);
	}

	@Override
	protected void running() {
		super.running();
		// ServerThread executor = getExecutor(ThreadType.SYNC);
		// executor.addTimerEvent(new ServerHeartTimer()); //TODO 临时添加
	}
	
}