package com.pascloud.bydr.tcp.fight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.manager.RoomManager;
import com.pascloud.bydr.struct.role.Role;
import com.pascloud.bydr.struct.room.Room;
import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.thread.ThreadType;
import com.pascloud.core.utils.TimeUtil;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.bydr.BydrFightMessage.FireRequest;
import com.pascloud.message.bydr.BydrFightMessage.FireResponse;

/**
 * 开炮
 *
 */
@HandlerEntity(mid = MID.FireReq_VALUE, msg = FireRequest.class, thread = ThreadType.ROOM)
public class FireHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(FireHandler.class);

	@Override
	public void run() {
		FireRequest req = getMsg();
		// LOGGER.info(req.toString());
		Role role = getPerson();

		Room room = RoomManager.getInstance().getRoom(role.getRoomId());
//		if (room == null) {
//			LOGGER.warn("{}所在房间{}未找到", role.getNick(), role.getRoomId());
//			sendServerMsg(ServerMsgId.login_state_lost);
//			return;
//		}
//		ConfigRoom cRoom = ConfigManager.getInstance().getConfigRoom(room.getType());
//
//		if (req.getGold() < cRoom.getMinGold() || req.getGold() > cRoom.getMaxGold()
//				|| req.getGold() % cRoom.getMinGold() != 0) {
//			//sendServerMsg("请求金币非法");
//			LOGGER.warn("房间{} {}_{}请求金币{}非法", room.getType(), role.getNick(), getRemoteAddr(), req.getGold());
//			return;
//		}

		if (role.getGold() < req.getGold()) {
//			sendServerMsg(ServerMsgId.not_enough_gold);
			return;
		}

		role.getFireGolds().add(req.getGold());
		role.changeGold(-req.getGold(), Reason.RoleFire);
		role.addBetGold(req.getGold());
		role.setFireTime(TimeUtil.currentTimeMillis());
		room.addAllFireCount();
		role.addFireCount();


		FireResponse.Builder builder = FireResponse.newBuilder();
		builder.setRid(role.getId());
		builder.setGold(req.getGold());
		builder.setAngleX(req.getAngleX());
		builder.setAngleY(req.getAngleY());
		builder.setTargetFishId(req.getTargetFishId());
		FireResponse response = builder.build();
		room.getRoles().values().forEach(roomRole -> {
			roomRole.sendMsg(response);
		});

	}

}
