package com.pascloud.gate.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.filter.firewall.BlacklistFilter;

import com.pascloud.core.mina.UdpServer;
import com.pascloud.core.mina.code.ClientProtocolCodecFactory;
import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.server.AbsService;
import com.pascloud.gate.script.IGateServerScript;
import com.pascloud.gate.server.handler.GateUdpUserServerHandler;

/**
 * 网关 用户udp 服务器
 * <p>
 * 1.在弱网条件下，udp效率更高，tcp存在阻塞重发，三次握手，消息重组等条件，速度很慢；如果消息丢包影响不大，实时性要求高，可以使用udp替换tcp
 * <br>
 * 2.网关服务器收到前端udp消息通过内部的tcp消息进行转发，当收到内部服务器的tcp消息为udp消息时，用udp返回给用户
 * <br>
 * 3.udp消息是不可靠的，只能辅助进行消息处理（可考虑实现可靠udp）
 * </p>
 * 
 */
public class GateUdpUserServer extends AbsService<MinaServerConfig>{
	private final UdpServer udpServer;
	private final MinaServerConfig minaServerConfig;
	Map<String, IoFilter> filters=new HashMap<>();
	private  BlacklistFilter blacklistFilter;	//IP黑名单过滤器

	public GateUdpUserServer(MinaServerConfig minaServerConfig) {
		super(null);
		this.minaServerConfig=minaServerConfig;
		blacklistFilter=new BlacklistFilter();
		filters.put("Blacklist", blacklistFilter);
		ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IGateServerScript.class, script->script.setIpBlackList(blacklistFilter));
		udpServer=new UdpServer(minaServerConfig, new GateUdpUserServerHandler(this),new ClientProtocolCodecFactory(),filters);
	}

	@Override
	protected void running() {
		udpServer.run();
		
	}

	@Override
	protected void onShutdown() {
		super.onShutdown();
		udpServer.stop();

	}

	public MinaServerConfig getMinaServerConfig() {
		return minaServerConfig;
	}
	
}
