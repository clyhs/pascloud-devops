package com.pascloud.gate.script;

import java.util.HashSet;
import java.util.Set;

import com.pascloud.core.script.IInitScript;
import com.pascloud.core.server.ServerType;
import com.pascloud.message.Mid.MID;

/**
 * 注册udp消息和udp服务器
 * 
 */
public class UdpMsgRegisterScript implements IGateServerScript, IInitScript {
	private Set<Integer> udpMsgIds = new HashSet<>(); // udp支持的消息
	private Set<ServerType> udpServers = new HashSet<>(); // udp支持的服务器

	@Override
	public void init() {
		// 注册udp游戏
		udpServers.add(ServerType.GAME_BYDR);

		// 注册udp消息,只需要注册返回消息
		udpMsgIds.add(MID.HeartRes_VALUE);
		udpMsgIds.add(MID.EnterRoomRes_VALUE);	
		udpMsgIds.add(MID.ChatRes_VALUE);
	}

	@Override
	public boolean isUdpMsg(ServerType serverType, int msgId) {
		if (serverType == null) {
			return false;
		}
		return udpServers.contains(serverType) && udpMsgIds.contains(msgId);
	}

}
