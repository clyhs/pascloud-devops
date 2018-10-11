package com.pascloud.gate.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.filter.firewall.BlacklistFilter;

import com.pascloud.core.mina.TcpServer;
import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.mina.websocket.WebSocketCodecFactory;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.server.AbsService;
import com.pascloud.gate.script.IGateServerScript;
import com.pascloud.gate.server.handler.GateWebSocketUserServerHandler;

/**
 * 网关 用户WebSocket 服务器（网页链接）
 */
public class GateWebSocketUserServer extends AbsService<MinaServerConfig> {
	private final TcpServer tcpServer;
	private final MinaServerConfig minaServerConfig;
	Map<String, IoFilter> filters=new HashMap<>();
	private  BlacklistFilter blacklistFilter;	//IP黑名单过滤器
	private GateWebSocketUserServerHandler gateWebSocketUserServerHandler;

	public GateWebSocketUserServer(MinaServerConfig minaServerConfig) {
		super(null);
		this.minaServerConfig=minaServerConfig;
		blacklistFilter=new BlacklistFilter();
		filters.put("Blacklist", blacklistFilter);
		ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IGateServerScript.class, script->script.setIpBlackList(blacklistFilter));
		gateWebSocketUserServerHandler=new GateWebSocketUserServerHandler();
		tcpServer=new TcpServer(minaServerConfig, gateWebSocketUserServerHandler, new WebSocketCodecFactory(), filters);
	}

	@Override
	protected void running() {
		gateWebSocketUserServerHandler.setService(this);
		tcpServer.run();
		
	}

	@Override
	protected void onShutdown() {
		super.onShutdown();
		tcpServer.stop();

	}

	public MinaServerConfig getMinaServerConfig() {
		return minaServerConfig;
	}
	
	
}
