package com.pascloud.core.server;

import com.pascloud.core.mina.config.BaseServerConfig;

public interface ITcpClientService<T extends BaseServerConfig> extends Runnable {

	/**
	 * 发送消息
	 * @param object
	 * @return
	 */
	public  boolean sendMsg(Object object);
	
	/**
	 * 检测服务器状态
	 */
	public void checkStatus();
}
