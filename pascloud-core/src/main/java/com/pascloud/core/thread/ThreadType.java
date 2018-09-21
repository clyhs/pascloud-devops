package com.pascloud.core.thread;

/**
 * 线程类型
 *
 */
public enum ThreadType {
	/**耗时的线程池*/
	IO,
	/**全局同步线程*/
	SYNC,
	/**房间*/
	ROOM
	;
}
