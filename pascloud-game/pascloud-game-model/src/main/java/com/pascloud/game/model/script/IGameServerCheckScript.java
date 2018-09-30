package com.pascloud.game.model.script;

import com.pascloud.core.script.IScript;
import com.pascloud.message.ServerMessage.ServerInfo;

/**
 *  游戏服务器状态监测脚本
 */
public interface IGameServerCheckScript extends IScript{

	/**
	 * 构建服务器状态信息
	 * @param builder
	 */
	public default void buildServerInfo(ServerInfo.Builder builder){
	}
}
