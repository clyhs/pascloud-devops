package com.pascloud.core.mina.handler;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.http.HttpRequestImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.IHandler;
import com.pascloud.core.utils.MsgUtil;



public abstract class HttpServerIoHandler implements IoHandler {
	
	private static final Logger log = LoggerFactory.getLogger(HttpServerIoHandler.class);


	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		log.debug("http请求建立 " + session);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		log.debug("http请求断开 " + session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub
		session.closeOnFlush();
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		session.closeOnFlush();
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		if (!(message instanceof HttpRequestImpl)) {
			log.warn("HttpServerIoHandler:" + message.getClass().getName());
			return;
		}
		long begin = System.currentTimeMillis();
		HttpRequestImpl httpRequest = (HttpRequestImpl) message;
		
		//code...
		/*
		Class<? extends IHandler> handlerClass = ScriptManager.getInstance()
				.getHttpHandler(httpRequest.getRequestPath());
		HandlerEntity handlerEntity = ScriptManager.getInstance().getHttpHandlerEntity(httpRequest.getRequestPath());
		*/
		
		
		long cost = System.currentTimeMillis() - begin;
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		if (!session.isClosing()) {
			MsgUtil.close(session, "http isClosing");
		}
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		log.error("http inputClosed " + session);
		MsgUtil.close(session, "http inputClosed");
	}
	
	

}
