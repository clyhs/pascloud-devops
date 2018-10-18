package com.pascloud.core.netty.handler;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message;
import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.IHandler;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.mina.config.BaseServerConfig;
import com.pascloud.core.mina.message.IDMessage;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.server.AbsService;
import com.pascloud.core.utils.MsgUtil;
import com.pascloud.core.utils.TimeUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 默认接收消息处理器 <br>
 * 消息直接用netty线程池处理，分发请重新实现messagehandler
 * 
 */
public abstract class DefaultInBoundHandler extends SimpleChannelInboundHandler<IDMessage> {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultInBoundHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, IDMessage msg) throws Exception {
		if (!ScriptManager.getInstance().tcpMsgIsRegister(msg.getMsgId())) {
			forward(msg);
			return;
		}
		Class<? extends IHandler> handlerClass = ScriptManager.getInstance().getTcpHandler(msg.getMsgId());
		TcpHandler handler = (TcpHandler) handlerClass.newInstance();
		handler.setCreateTime(TimeUtil.currentTimeMillis());
		HandlerEntity handlerEntity = ScriptManager.getInstance().getTcpHandlerEntity(msg.getMsgId());
		Message message = MsgUtil.buildMessage(handlerEntity.msg(), (byte[]) msg.getMsg());
		handler.setMessage(message);
		handler.setRid(msg.getUserID());
		handler.setChannel(ctx.channel());
		messagehandler(handler, handlerEntity);
	}
	
	




	/**
	 * 消息处理
	 * 
	 * @param handler
	 */
	protected void messagehandler(TcpHandler handler, HandlerEntity handlerEntity) {
		if (getService() != null) {
			Executor executor = getService().getExecutor(handlerEntity.thread());
			if (executor != null) {
				executor.execute(handler);
				return;
			}
		}
		handler.run();
	}

	/**
	 * 消息跳转
	 * 
	 * @param idMessage
	 */
	protected void forward(IDMessage msg) {
		LOGGER.info("消息{} 未实现", msg.getMsgId());
	}

	public abstract AbsService<? extends BaseServerConfig> getService() ;
	
}
