package com.pascloud.hall.manager;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.script.ScriptManager;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.game.model.strut.Item;
import com.pascloud.game.model.strut.Role;
import com.pascloud.hall.script.IPacketScript;
import com.pascloud.message.hall.HallPacketMessage.PacketItem;

/**
 * 背包
 * 
 */
public class PacketManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(PacketManager.class);
	private static volatile PacketManager packetManager;

	private PacketManager() {

	}

	public static PacketManager getInstance() {
		if (packetManager == null) {
			synchronized (PacketManager.class) {
				if (packetManager == null) {
					packetManager = new PacketManager();
				}
			}
		}
		return packetManager;
	}

	/**
	 * 使用道具
	 * 
	 * @param id
	 * @param num
	 * @param reason
	 * @param itemConsumer
	 */
	public void useItem(Role role, long id, int num, Reason reason, Consumer<Item> itemConsumer) {
		ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IPacketScript.class,
				script -> script.useItem(role, id, num, reason, itemConsumer));
	}

	/**
	 * 添加道具
	 * 
	 * @param configId
	 * @param num
	 *            数量
	 * @param reason
	 * @param itemConsumer
	 */
	public Item addItem(Role role, int configId, int num, Reason reason, Consumer<Item> itemConsumer) {
		return ScriptManager.getInstance().getBaseScriptEntry().functionScripts(IPacketScript.class,
				(IPacketScript script) -> script.addItem(role, configId, num, reason, itemConsumer));
	}

	/**
	 * 构建
	 * 
	 * @param item
	 * @return
	 */
	public PacketItem buildPacketItem(Item item) {
		PacketItem.Builder builder = PacketItem.newBuilder();
		builder.setId(item.getId());
		builder.setConfigId(item.getConfigId());
		builder.setNum(item.getNum());
		return builder.build();
	}

	/**
	 * 获取物品
	 * 
	 * @param rid
	 * @param itemId
	 * @return
	 */
	public Item getItem(Role role, long itemId) {
		return role.getItem(itemId);
	}
}