package com.pascloud.cluster.http;


import com.pascloud.cluster.manager.ServerManager;
import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.HttpHandler;
import com.pascloud.core.server.ServerInfo;

/**
 * 
 * @author admin
 *
 */
@HandlerEntity(path = "/server/gate/ip")
public class GetGateIpHandler extends HttpHandler {

	@Override
	public void run() {
		try {
			String version = getString("version");
			ServerInfo serverInfo = ServerManager.getInstance().getIdleGate(version);
			if (serverInfo == null) {
				getParameter().appendBody("无可用网关服");
			} else {
				getParameter().appendBody(serverInfo.getIp() + ":" + serverInfo.getPort());
			}
		} finally {
			response();
		}
	}

}