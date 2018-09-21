package com.pascloud.core.mina.handler;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
	public void sessionClosed(IoSession session) throws Exception {
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
	public void messageReceived(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		byte[] bytes = (byte[]) message;
		try {
			if (bytes.length < messageHeaderLength) {
				log.error("messageReceived:消息长度{}小于等于消息头长度{}", bytes.length, messageHeaderLength);
				return;
			}
			
			int offset = messageHeaderLength > 4 ? 8 : 0;
			
			//code...
			
			
			//forward(session, msgID, bytes);
		}catch(Exception e){
			
		}
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
}
