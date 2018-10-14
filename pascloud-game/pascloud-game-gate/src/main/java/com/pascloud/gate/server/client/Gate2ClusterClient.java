package com.pascloud.gate.server.client;

import com.pascloud.core.mina.config.MinaClientConfig;
import com.pascloud.core.mina.service.SingleMinaTcpClientService;

/**
 * 连接到集群管理
 *
 */
public class Gate2ClusterClient extends SingleMinaTcpClientService {

	public Gate2ClusterClient(MinaClientConfig minaClientConfig) {
		super(minaClientConfig);
	}

}