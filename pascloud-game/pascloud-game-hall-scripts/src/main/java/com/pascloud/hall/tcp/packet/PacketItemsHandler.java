package com.pascloud.hall.tcp.packet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.game.model.strut.Role;
import com.pascloud.hall.manager.PacketManager;
import com.pascloud.hall.manager.RoleManager;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallPacketMessage.PacketItemsRequest;
import com.pascloud.message.hall.HallPacketMessage.PacketItemsResponse;

/**
 * 背包物品列表
 * 
 */
@HandlerEntity(mid = MID.PacketItemsReq_VALUE, msg = PacketItemsRequest.class)
public class PacketItemsHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(PacketItemsHandler.class);

	@Override
	public void run() {
		Role role = RoleManager.getInstance().getRole(rid);
		if (role == null) {
			LOGGER.warn("角色{}未登陆", rid);
			return;
		}
		PacketItemsResponse.Builder builder = PacketItemsResponse.newBuilder();
		role.getItems().forEach((key, value) -> {
			builder.addItems(PacketManager.getInstance().buildPacketItem(value));
		});
		sendIdMsg(builder.build());

	}

}
