package com.pascloud.bydr.tcp.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.manager.RoleManager;
import com.pascloud.bydr.manager.RoomManager;
import com.pascloud.bydr.struct.role.Role;
import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.bydr.BydrRoomMessage.EnterRoomRequest;

/**
 * 进入房间
 * 
 */
@HandlerEntity(mid = MID.EnterRoomReq_VALUE, msg = EnterRoomRequest.class)
public class EnterRoomHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(EnterRoomHandler.class);
//	private static AtomicInteger num = new AtomicInteger(0);

	@Override
	public void run() {
//		LOGGER.warn("{}", getMessage().toString());
		EnterRoomRequest req=getMsg();
		Role role = RoleManager.getInstance().getRole(rid);
		if(role==null) {
			LOGGER.warn("角色{}未登陆",rid);
			return;
		}
		RoomManager.getInstance().enterRoom(role, req.getType(), req.getRank());
//		EnterRoomResponse.Builder builder = EnterRoomResponse.newBuilder();
//		builder.setResult(num.getAndIncrement());
//		sendIdMsg(builder.build());

	}

}
