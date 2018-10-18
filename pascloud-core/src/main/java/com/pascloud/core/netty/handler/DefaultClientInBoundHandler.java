package com.pascloud.core.netty.handler;

import com.pascloud.core.mina.config.BaseServerConfig;
import com.pascloud.core.netty.service.NettyClientService;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.server.AbsService;
import com.pascloud.core.server.ServerInfo;

import io.netty.channel.ChannelHandlerContext;

/**
 * 内部客户端 默认消息
 * 
 */
public class DefaultClientInBoundHandler extends DefaultInBoundHandler {

	private NettyClientService netttyClientService;
	private ServerInfo serverInfo;

	public DefaultClientInBoundHandler(NettyClientService netttyClientService, ServerInfo serverInfo) {
		super();
		this.netttyClientService = netttyClientService;
		this.serverInfo = serverInfo;
	}

	public DefaultClientInBoundHandler(NettyClientService netttyClientService) {
		super();
		this.netttyClientService = netttyClientService;
	}

	@Override
	public AbsService<? extends BaseServerConfig> getService() {
		return netttyClientService;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		netttyClientService.channelActive(ctx.channel());
		if (this.serverInfo != null) {
			serverInfo.onChannelActive(ctx.channel());
		}
		ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IChannelHandlerScript.class,
				script -> script.channelActive(DefaultClientInBoundHandler.class, netttyClientService,ctx.channel()));
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		netttyClientService.channelInactive(ctx.channel());
	}

}
