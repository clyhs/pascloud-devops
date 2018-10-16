package com.pascloud.hall.script.packet;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.game.model.constant.Reason;
import com.pascloud.game.model.strut.Item;
import com.pascloud.game.model.strut.Role;
import com.pascloud.hall.script.IPacketScript;

/**
 * 添加道具
 */
public class AddItemScript implements IPacketScript {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddItemScript.class);

	@Override
	public Item addItem(Role role,int configId, int num, Reason reason, Consumer<Item> itemConsumer) {
		// TODO 具体逻辑,叠加方式等？
		Item item=new Item();
		item.setConfigId(configId);
		item.setNum(item.getNum()+num);
		item.saveToRedis(role.getId());
		return item;
	}

	
}