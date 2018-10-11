package com.pascloud.gate.script;

import org.apache.mina.filter.firewall.BlacklistFilter;

import com.pascloud.core.script.IScript;
import com.pascloud.core.server.ServerType;


/**
 * 服务器脚本
 * 
 */
public interface IGateServerScript extends IScript {

	/**
	 * 是否为udp消息
	 * @param serverType 判断游戏类型是否支持udp
	 * @param msgId 消息ID
	 * @return
	 */
	default boolean isUdpMsg(ServerType serverType,int msgId){
		return false;
	}
	
	/**
	 * 设置IP黑名单
	 * @param filter
	 */
	default void setIpBlackList(BlacklistFilter filter){
		
	}
}
