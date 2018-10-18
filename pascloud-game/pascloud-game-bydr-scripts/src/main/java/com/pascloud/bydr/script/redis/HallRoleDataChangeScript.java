package com.pascloud.bydr.script.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.manager.RoleManager;
import com.pascloud.bydr.struct.role.Role;
import com.pascloud.core.redis.IPubSubScript;
import com.pascloud.core.redis.jedis.JedisPubSubMessage;
import com.pascloud.game.model.constant.Config;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.game.model.redis.channel.BydrChannel;

/**
 * 大厅通知角色数据改变
 * 
 */
public class HallRoleDataChangeScript implements IPubSubScript {
	private static final Logger LOGGER=LoggerFactory.getLogger(HallRoleDataChangeScript.class);

	@Override
	public void onMessage(String channel, JedisPubSubMessage message) {
		if (!channel.startsWith("Hall")) { // channel必须以Hall开头
			return;
		}
		if(message.getServer()!=Config.SERVER_ID) {
			return;
		}
		Role role = RoleManager.getInstance().getRole(message.getId());
		if(role==null) {
			LOGGER.warn("角色[{}]已退出游戏:{} {}更新失败",message.getId(),channel,message.toString());
			return;
		}
		
		switch (BydrChannel.valueOf(channel)) {
		case HallGoldChange:
			role.changeGold(message.getTarget(), Reason.HallGoldChange);
			break;

		default:
			break;
		}

	}

	
}