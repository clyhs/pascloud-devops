package com.pascloud.core.thread;

import java.util.concurrent.Executor;

public abstract class ExecutorPool implements Executor {

	/**
	 * 关闭线程
	 */
	public abstract void stop() ;
	

}
