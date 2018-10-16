package com.pascloud.core.mq;

import com.pascloud.core.script.IScript;

/**
 * MQ 消息处理脚本
 */
public interface IMQScript extends IScript {

	/**
	 * ＭＱ消息接收处理
	 * @param msg
	 */
	default void onMessage(String msg){
		
	}
}
