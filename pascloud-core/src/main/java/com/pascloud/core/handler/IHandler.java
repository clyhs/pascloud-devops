package com.pascloud.core.handler;

import org.apache.mina.core.session.IoSession;

public interface IHandler extends Runnable {


	/**
	 * 会话
	 * @return
	 */
	IoSession getSession();

	/**
	 * 会话
	 * @param session
	 */
	void setSession(IoSession session);

	/**
	 * 请求消息
	 * @return
	 */
	Object getMessage();

	/**
	 * 消息
	 * @return
	 */
	void setMessage(Object message);

	/**
	 * 创建时间
	 * @param time
	 */
	void setCreateTime(long time);
	
	/**
	 * 创建时间
	 */
	long getCreateTime();
	
	/**
	 * http 参数
	 * @return
	 */
	Object getParameter();

	/**
	 * http 参数
	 * @return
	 */
    void setParameter(Object parameter);
}