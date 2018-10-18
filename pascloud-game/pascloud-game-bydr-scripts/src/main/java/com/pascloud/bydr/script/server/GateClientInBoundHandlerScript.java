package com.pascloud.bydr.script.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.server.BydrServer;
import com.pascloud.core.mina.config.BaseServerConfig;
import com.pascloud.core.mina.message.IDMessage;
import com.pascloud.core.netty.config.NettyClientConfig;
import com.pascloud.core.netty.handler.DefaultClientInBoundHandler;
import com.pascloud.core.netty.handler.IChannelHandlerScript;
import com.pascloud.core.netty.service.MutilNettyTcpClientService;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.server.AbsService;
import com.pascloud.core.server.IMutilTcpClientService;
import com.pascloud.core.server.ServerState;
import com.pascloud.game.model.script.IGameServerCheckScript;
import com.pascloud.message.ServerMessage;
import com.pascloud.message.ServerMessage.ServerRegisterRequest;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;

/**
 * netty连接网关客户端消息处理器脚本
 * 
 */
public class GateClientInBoundHandlerScript implements IChannelHandlerScript {
	private static final Logger LOGGER = LoggerFactory.getLogger(GateClientInBoundHandlerScript.class);

	/**
	 * 向网关服注册channel 连接
	 */
	@Override
	public void channelActive(Class<? extends ChannelHandler> handlerClass, AbsService<? extends BaseServerConfig> service,
			Channel channel) {
		if (!handlerClass.isAssignableFrom(DefaultClientInBoundHandler.class)
				|| !(service instanceof MutilNettyTcpClientService)) {
			return;
		}
		// 向网关服注册session
		IMutilTcpClientService<? extends BaseServerConfig> client = BydrServer.getInstance().getBydr2GateClient();
		if (!(client instanceof MutilNettyTcpClientService)) {
			LOGGER.warn("未开启netty服务");
			return;
		}
		NettyClientConfig config = ((MutilNettyTcpClientService) client).getNettyClientConfig();
		ServerRegisterRequest.Builder builder = ServerRegisterRequest.newBuilder();
		ServerMessage.ServerInfo.Builder info = ServerMessage.ServerInfo.newBuilder();
		info.setId(config.getId());
		info.setIp("");
		info.setMaxUserCount(1000);
		info.setOnline(1);
		info.setName(config.getName());
		info.setState(ServerState.NORMAL.getState());
		info.setType(config.getType().getType());
		info.setWwwip("");
		ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IGameServerCheckScript.class,
				script -> script.buildServerInfo(info));
		builder.setServerInfo(info);
		channel.writeAndFlush(new IDMessage(channel, builder.build(), 0, 0));
	}

}
