package com.pascloud.core.mina.handler;

import java.util.concurrent.Executor;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message;
import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.IHandler;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.mina.config.BaseServerConfig;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.server.AbsService;
import com.pascloud.core.utils.MsgUtil;



public abstract class DefaultProtocolHandler implements IoHandler {
	
	protected static final Logger log = LoggerFactory.getLogger(DefaultProtocolHandler.class);
	
	// 消息头长度
	protected final int messageHeaderLength;
	
	public DefaultProtocolHandler(int messageHeaderLength){
		this.messageHeaderLength = messageHeaderLength;
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionClosed(IoSession session) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		session.closeNow();
	}

	@Override
	public void messageReceived(IoSession session, Object obj) throws Exception {
		// TODO Auto-generated method stub
		byte[] bytes = (byte[]) obj;
		try {
			if (bytes.length < messageHeaderLength) {
				log.error("messageReceived:消息长度{}小于等于消息头长度{}", bytes.length, messageHeaderLength);
				return;
			}
			
			int offset = messageHeaderLength > 4 ? 8 : 0;
			
			//code...
			
			int msgID = MsgUtil.getMessageID(bytes, offset); // 消息ID

			if (ScriptManager.getInstance().tcpMsgIsRegister(msgID)) {
				Class<? extends IHandler> handlerClass = ScriptManager.getInstance().getTcpHandler(msgID);
				HandlerEntity handlerEntity = ScriptManager.getInstance().getTcpHandlerEntity(msgID);
				if (handlerClass != null) {
					Message message = MsgUtil.buildMessage(handlerEntity.msg(), bytes, messageHeaderLength,
							bytes.length - messageHeaderLength);
					TcpHandler handler = (TcpHandler) handlerClass.newInstance();
					if (handler != null) {
						if (offset > 0) { // 偏移量大于0，又发玩家ID
							long rid = MsgUtil.getMessageRID(bytes, 0);
							handler.setRid(rid);
						}
						messageHandler(session, handlerEntity, message, handler);
						return;
					}
				}
			}
			forward(session, msgID, bytes);
			
			
			//forward(session, msgID, bytes);
		}catch(Exception e){
			log.error("messageReceived", e);
			int msgid = MsgUtil.getMessageID(bytes, 0);
			log.warn("尝试按0移位处理,id：{}", msgid);
		}
	}
	
	protected void messageHandler(IoSession session, HandlerEntity handlerEntity, Message message, TcpHandler handler) {
		handler.setMessage(message);
		handler.setSession(session);
		handler.setCreateTime(System.currentTimeMillis());
		Executor executor = getService().getExecutor(handlerEntity.thread());
		if (executor == null) {
			// log.warn("处理器{}没有分配线程", handler.getClass().getName());
			handler.run();
			return;
		}
		executor.execute(handler);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		session.closeNow();
	}

	
	protected abstract void forward(IoSession session, int msgID, byte[] bytes);
	
	public abstract AbsService<? extends BaseServerConfig> getService();
}
