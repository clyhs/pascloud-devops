package com.pascloud.hall.script.packet;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.game.model.constant.Reason;
import com.pascloud.game.model.strut.Item;
import com.pascloud.game.model.strut.Role;
import com.pascloud.hall.manager.PacketManager;
import com.pascloud.hall.script.IPacketScript;
import com.pascloud.message.hall.HallPacketMessage.UseItemResponse;

/**
 * 使用道具脚本
 */
public class UseItemScript implements IPacketScript {
	private static final Logger LOGGER = LoggerFactory.getLogger(UseItemScript.class);
	
	@Override
	public void useItem(Role role,long itemId, int num, Reason reason, Consumer<Item> itemConsumer) {
		Item item = PacketManager.getInstance().getItem(role, itemId);
		if(item==null) {
			return;
		}
		if(item.getNum()<num) {
			LOGGER.warn("道具数量{}，请求{}",item.getNum(),num);
			return;
		}
		
		item.setNum(item.getNum()-num);
		item.saveToRedis(role.getId());
		UseItemResponse.Builder builder=UseItemResponse.newBuilder();
		builder.setResult(0);
		role.sendMsg(builder.build());
	}

}
