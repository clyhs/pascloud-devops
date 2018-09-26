package com.pascloud.core.mina.handler;

import java.util.concurrent.Executor;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.http.HttpRequestImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.IHandler;
import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.server.AbsService;
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
		
		Class<? extends IHandler> handlerClass = ScriptManager.getInstance()
				.getHttpHandler(httpRequest.getRequestPath());
		HandlerEntity handlerEntity = ScriptManager.getInstance().getHttpHandlerEntity(httpRequest.getRequestPath());
		
		if (handlerClass == null) {
			handlerClass = ScriptManager.getInstance().getHttpHandler("");
			handlerEntity = ScriptManager.getInstance().getHttpHandlerEntity("");
		}
		if (handlerClass == null) {
			log.error("Http 容器 未能找到 content = {} 的 httpMessageBean tostring: {}", httpRequest.getRequestPath(),
					session.getRemoteAddress().toString());
			return;
		}
		
		try {
			IHandler handler = handlerClass.newInstance();
			handler.setMessage(httpRequest);
			handler.setSession(session);
			handler.setCreateTime(System.currentTimeMillis());

			Executor executor = getSevice().getExecutor(handlerEntity.thread());
			if (executor != null) {
				executor.execute(handler);
			} else {
				handler.run();
//				LOG.error("{}指定的线程{}未开启", handlerClass.getName(), handlerEntity.thread().toString());
			}

		} catch (InstantiationException | IllegalAccessException ex) {
			log.error("messageReceived build message error !!! ", ex);
		}
		
		long cost = System.currentTimeMillis() - begin;
		if (cost > 30L) {
			log.error(String.format("\t messageReceived %s msgID[%s] builder[%s] cost %d ms",
					Thread.currentThread().toString(), httpRequest.getRequestPath(), httpRequest.toString(), cost));
		}
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
	
	
	protected abstract AbsService<MinaServerConfig> getSevice();
	

}
