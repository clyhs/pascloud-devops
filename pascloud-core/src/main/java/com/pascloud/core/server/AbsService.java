package com.pascloud.core.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.mina.config.BaseServerConfig;
import com.pascloud.core.thread.ThreadPoolExecutorConfig;
import com.pascloud.core.thread.ThreadType;

public abstract class AbsService<Conf extends BaseServerConfig> implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(AbsService.class);
	
	/**线程容器*/
	private final Map<ThreadType, Executor> serverThreads = new ConcurrentHashMap<>();
	
	public AbsService(ThreadPoolExecutorConfig threadPoolExecutorConfig) {
		// 初始化
		if (threadPoolExecutorConfig != null) {
			// IO默认线程池 客户端的请求,默认使用其执行
			ThreadPoolExecutor ioHandlerThreadExcutor = threadPoolExecutorConfig.newThreadPoolExecutor();
			serverThreads.put(ThreadType.IO, ioHandlerThreadExcutor);

			//全局sync线程
			/*
			ServerThread syncThread = new ServerThread(new ThreadGroup("全局同步线程"),
					"全局同步线程:" + this.getClass().getSimpleName(), threadPoolExecutorConfig.getHeart(),
					threadPoolExecutorConfig.getCommandSize());
			syncThread.start();
			serverThreads.put(ThreadType.SYNC, syncThread);*/
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 初始化线程
	 */
	protected void initThread() {
	}

	/**
	 * 运行中
	 */
	protected abstract void running();

	protected void onShutdown() {
		
	}
	
	public void stop(boolean flag) {
		onShutdown();
	}

	public Map<ThreadType, Executor> getServerThreads() {
		return serverThreads;
	}
	
	/**
	 * 获得线程
	 *
	 * @param threadType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Executor> T getExecutor(ThreadType threadType) {
		return (T) serverThreads.get(threadType);
	}
	
	/**
	 * 关服回调
	 *
	 * @author JiangZhiYong
	 * @date 2017-03-30 QQ:359135103
	 */
	private static final class CloseByExit implements Runnable {

		private final static Logger log = LoggerFactory.getLogger(CloseByExit.class);
		@SuppressWarnings("rawtypes")
		private final AbsService server;

		@SuppressWarnings("rawtypes")
		public CloseByExit(AbsService server) {
			this.server = server;
		}

		@Override
		public void run() {
			server.onShutdown();
			log.warn("服务{}已停止", server.getClass().getName());
		}
	}

}
