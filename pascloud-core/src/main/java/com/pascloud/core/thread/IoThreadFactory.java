package com.pascloud.core.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class IoThreadFactory implements ThreadFactory {
	private static final AtomicInteger threadId = new AtomicInteger(1);
	private final ThreadGroup group;

	public IoThreadFactory() {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(group, r, ThreadType.IO.toString() + "-" + threadId.getAndIncrement(), 0);
		if (thread.isDaemon()) {
			thread.setDaemon(false);
		}
		if (thread.getPriority() != Thread.NORM_PRIORITY) {
			thread.setPriority(Thread.NORM_PRIORITY);
		}

		return thread;
	}

}
