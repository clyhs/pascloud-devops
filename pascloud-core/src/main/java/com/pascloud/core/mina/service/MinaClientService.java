package com.pascloud.core.mina.service;

import java.util.concurrent.PriorityBlockingQueue;

import org.apache.mina.core.session.IoSession;

import com.pascloud.core.mina.config.MinaClientConfig;
import com.pascloud.core.server.AbsService;
import com.pascloud.core.server.ITcpClientService;
import com.pascloud.core.thread.ThreadPoolExecutorConfig;

public abstract class MinaClientService extends AbsService<MinaClientConfig> implements ITcpClientService<MinaClientConfig> {
	
	private MinaClientConfig minaClientConfig;
	
	/** 连接会话 */
	private final PriorityBlockingQueue<IoSession> sessions = new PriorityBlockingQueue<>(128,
			(IoSession o1, IoSession o2) -> {
				int res = o1.getScheduledWriteMessages() - o2.getScheduledWriteMessages();
				if (res == 0) {
					res = (int) (o1.getWrittenBytes() - o2.getWrittenBytes());
				}
				return res;
			});
	
	/**
	 * 无线程池
	 * @param minaClientConfig
	 */
	public MinaClientService(MinaClientConfig minaClientConfig) {
		this(null, minaClientConfig);
	}
	
	public MinaClientService(ThreadPoolExecutorConfig threadPoolExecutorConfig, MinaClientConfig minaClientConfig) {
		super(threadPoolExecutorConfig);
		this.minaClientConfig = minaClientConfig;
	}

	
	/**
	 * 连接建立
	 */
	public void onIoSessionConnect(IoSession session) {
		sessions.add(session);
	}

	/**
	 * 连接关闭移除
	 */
	public void onIoSessionClosed(IoSession session) {
		sessions.remove(session);
	}

	public boolean isSessionEmpty() {
		return sessions.isEmpty();
	}
	

	@Override
	public boolean sendMsg(Object object) {
		// TODO Auto-generated method stub
		IoSession session = getMostIdleIoSession();
		if (session != null) {
			session.write(object);
			return true;
		}
		return false;
	}

	
	
	/**
	 * 获取连接列表中最空闲的有效的连接
	 * 
	 * @return
	 */
	public IoSession getMostIdleIoSession() {
		IoSession session = null;
		while (session == null && !sessions.isEmpty()) {
			session = sessions.peek();
			if (session != null && session.isConnected()) {
				break;
			} else {
				sessions.poll();
			}
		}
		return session;
	}

	public MinaClientConfig getMinaClientConfig() {
		return minaClientConfig;
	}

	
}
