package com.pascloud.core.thread.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author chenly
 *
 */

public abstract class ScheduledTask {
	
	
	private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
	private final ScheduledExecutorService scheduler;
	private final int period; // 周期

	/**
	 * 
	 * @param taskMaxTime
	 *            执行周期
	 */
	public ScheduledTask(int taskMaxTime) {
		
		
        scheduler = Executors.newScheduledThreadPool(1);
		
		Executors.newScheduledThreadPool(1, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread thread=new Thread(this.getClass().getSimpleName());
				
				return thread;
			}
		});
		period = taskMaxTime < 100 ? 100 : taskMaxTime;
	}

	/**
	 * 任务逻辑
	 */
	protected abstract void executeTask();

	/**
	 * 启动线程
	 */
	public void start() {
		scheduler.scheduleAtFixedRate(new Task(), 100, period, TimeUnit.MILLISECONDS);
	}

	/**
	 * 关闭调用
	 */
	public void shutdown() {
		scheduler.shutdown();
	}

	/**
	 * 任务
	 * 
	 */
	class Task implements Runnable {

		@Override
		public void run() {
			try {
				long begin = System.currentTimeMillis();
				executeTask();
				if (System.currentTimeMillis() - begin > period) {
					log.warn("定时器{}执行{}ms", this.getClass().getSimpleName(), System.currentTimeMillis() - begin);
				}
			} catch (Exception e) {
				log.error("定时任务", e);
			}
		}
	}

}
