package com.pascloud.bydr.script.room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.manager.RoomManager;
import com.pascloud.bydr.script.IRoomScript;
import com.pascloud.bydr.server.BydrServer;
import com.pascloud.bydr.struct.role.Role;
import com.pascloud.bydr.struct.room.ClassicsRoom;
import com.pascloud.bydr.struct.room.Room;
import com.pascloud.bydr.thread.RoomExecutor;
import com.pascloud.core.thread.ThreadType;
import com.pascloud.message.bydr.BydrRoomMessage.EnterRoomResponse;
import com.pascloud.message.bydr.BydrRoomMessage.RoomType;

/**
 * 进入房间脚本
 * 
 */
public class EnterRoomScript implements IRoomScript {
	private static final Logger LOGGER = LoggerFactory.getLogger(EnterRoomScript.class);

	private List<Integer> seats = new ArrayList<>(Arrays.asList(1, 2, 3, 4)); // 座位列表

	@Override
	public void enterRoom(Role role, Room room) {

		room.getRoomThread().execute(() -> { // 房间线程执行
			if (room.getRoles().containsValue(role)) {
				LOGGER.debug("角色{}已经在房间中", role.getNick());
				return;
			}
			int seatNum = randomSeat(room);
			room.getRoles().put(seatNum, role);
			role.setSeatNo(seatNum);
			role.setRoomId(room.getId());

			EnterRoomResponse.Builder builder = EnterRoomResponse.newBuilder();
			builder.setResult(0);
			builder.setRoomInfo(RoomManager.getInstance().buildRoomInfo(room));
			room.getRoles().values().forEach(r -> {
				builder.addRoleInfo(RoomManager.getInstance().buildRoomRoleInfo(r));
			});

			role.sendMsg(builder.build());

		});

	}

	/**
	 * 查找匹配房间
	 */
	@Override
	public void enterRoom(Role role, RoomType roomType, int rank) {
		List<Room> rooms = RoomManager.getInstance().getRooms(roomType);
		Room room = null;
		Optional<Room> optional = rooms.stream().filter(r -> r.getRank() == rank && r.getRoles().size() < 4).findAny();
		if (optional.isPresent()) {
			room = optional.get();
		} else {
			// 创建新房间
			switch (roomType) {
			case CLASSICS:
				room = new ClassicsRoom();
				room.setRank(rank);
				break;

			default:
				break;
			}
			RoomManager.getInstance().addRoom(room);
			RoomExecutor roomExecutor = BydrServer.getInstance().getExecutor(ThreadType.ROOM);
			if (roomExecutor != null) {
				roomExecutor.addRoom(room);
			}

		}
		enterRoom(role, room);
	}

	/**
	 * 随机一个空闲座位号
	 * 
	 * @note 并未真正随机
	 * @param room
	 * @return
	 */
	private int randomSeat(Room room) {
		if (room.getRoles().size() < 1) {
			return 1;
		}
		Set<Integer> seatSet = room.getRoles().keySet();
		for (int i = 1; i <= seats.size(); i++) {
			if (!seatSet.contains(i)) {
				return i;
			}
		}
		LOGGER.warn("房间{}_{}未找到空位", room.getType());
		return 0;
	}
}
