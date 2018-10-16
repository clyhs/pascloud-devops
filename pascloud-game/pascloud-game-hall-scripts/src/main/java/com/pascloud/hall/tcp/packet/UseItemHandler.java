package com.pascloud.hall.tcp.packet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.game.model.strut.Role;
import com.pascloud.hall.manager.PacketManager;
import com.pascloud.hall.manager.RoleManager;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallPacketMessage.UseItemRequest;

/**
 * 使用物品
 */
@HandlerEntity(mid=MID.UseItemReq_VALUE,msg=UseItemRequest.class)
public class UseItemHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(UseItemHandler.class);

	@Override
	public void run() {
		UseItemRequest req=getMsg();
		Role role = RoleManager.getInstance().getRole(rid);
		if(role==null) {
			return;
		}
		PacketManager.getInstance().useItem(role, req.getItemId(), 1, Reason.RoleUseItem, null);
	}

}
