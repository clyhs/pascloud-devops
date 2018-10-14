package com.pascloud.core.mina.handler;

import org.apache.mina.core.session.IoSession;

import com.pascloud.core.mina.service.MinaClientService;
import com.pascloud.core.utils.IntUtil;

/**
 * 默认内部客户端消息处理器
 *
 */
public class DefaultClientProtocolHandler extends DefaultProtocolHandler {

	private MinaClientService service;

	public DefaultClientProtocolHandler(MinaClientService service) {
		super(4);
		this.service = service;
	}
	
	
	

	public DefaultClientProtocolHandler(int messageHeaderLength, MinaClientService service) {
		super(messageHeaderLength);
		this.service = service;
	}




	@Override
	public void sessionOpened(IoSession session) {
		super.sessionOpened(session);
		getService().onIoSessionConnect(session);
	}

	@Override
	protected void forward(IoSession session, int msgID, byte[] bytes) {
		log.warn("无法找到消息处理器：msgID{},bytes{}", msgID, IntUtil.BytesToStr(bytes));
	}

	@Override
	public MinaClientService getService() {
		return this.service;
	}

	@Override
	public void sessionClosed(IoSession session) {
		super.sessionClosed(session);
		getService().onIoSessionClosed(session);
	}

}