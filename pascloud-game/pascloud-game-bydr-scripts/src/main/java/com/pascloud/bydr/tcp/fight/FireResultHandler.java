package com.pascloud.bydr.tcp.fight;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.manager.RoomManager;
import com.pascloud.bydr.struct.Fish;
import com.pascloud.bydr.struct.role.Role;
import com.pascloud.bydr.struct.room.Room;
import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.thread.ThreadType;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.bydr.BydrFightMessage.FireResultRequest;
import com.pascloud.message.bydr.BydrFightMessage.FireResultResponse;

/**
 * 使用技能
 *
 */
@HandlerEntity(mid = MID.FireResultReq_VALUE, msg = FireResultRequest.class, thread = ThreadType.ROOM)
public class FireResultHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(FireResultHandler.class);

	@Override
	public void run() {
		FireResultRequest req = getMsg();
		// LOGGER.info(req.toString());
		Role role = getPerson();

		Room room = RoomManager.getInstance().getRoom(role.getRoomId());
		if (room == null) {
			LOGGER.info("{}所在房间{}未找到", role.getNick(), role.getRoomId());
			// sendServerMsg("您已退出房间");
			return;
		}
		if (role.getFireGolds().size() < 1 || !role.getFireGolds().contains(req.getFireGold())) {
			// sendServerMsg("请发射炮弹");
			LOGGER.info("{}  未发射炮弹{}，请求结算{}", role.getNick(), req.getFireGold(), role.getFireGolds().toString());
			return;
		}

		if (req.getTargetFishIdList() == null) {
			return;
		}
		FireResultResponse.Builder builder = FireResultResponse.newBuilder();
		builder.addAllDieFishId(req.getTargetFishIdList());

		room.addFireResultCount();

		
		role.getFireGolds().remove((Integer) req.getFireGold());
		if (builder.getDieFishIdCount() > 0) {
			getAward(role, room, builder, req.getFireGold());
			builder.setGold(role.getGold());
			builder.setSpecialFishId(req.getSpecialFishId());
			builder.setRid(role.getId());
			builder.setAccumulateGold(0);
			FireResultResponse response = builder.build();
			room.getRoles().values().forEach(roomRole -> {
				role.sendMsg(response);
			});
		}
	}

	

	@SuppressWarnings({ "unchecked" })
	private void fishDie(Room room, long fishId) {
		Fish fish = room.getFishMap().remove(fishId);
		if (fish == null) {
			return;
		}
	}

	
	/**
	 * 领取奖励
	 * 
	 * @param role
	 * @param room
	 * @param dieFishs
	 * @return -1 获得累积奖
	 */
	private int getAward(Role role, Room room, FireResultResponse.Builder builder, int fireGold) {
		List<Long> dieFishs = builder.getDieFishIdList();
		int result = 0;
		if (dieFishs.size() < 1) {
			return 0;
		}
		for (int i = 0; i < dieFishs.size(); i++) {
			Fish fish = room.getFishMap().get(dieFishs.get(i));
			if (fish == null) {
				// LOGGER.debug("房间{}—{}未找到，结算奖励失败", room.getType(),
				// room.getNum());
				continue;
			}
			if (i == 0) { // 统计打死鱼
				role.addFishDiesCount(fish.getConfigId());
			}
			
			fishDie(room, fish.getId());
		}
		builder.setMultiple(0);

		role.changeGold(fireGold*2, Reason.RoleFire);

		return result;
	}

	
}
