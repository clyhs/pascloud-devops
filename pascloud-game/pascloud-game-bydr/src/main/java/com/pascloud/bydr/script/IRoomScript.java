package com.pascloud.bydr.script;

import java.time.LocalTime;

import com.pascloud.bydr.struct.role.Role;
import com.pascloud.bydr.struct.room.Room;
import com.pascloud.core.script.IScript;
import com.pascloud.message.bydr.BydrRoomMessage.RoomType;

/**
 * 房间脚本
 *
 */
public interface IRoomScript extends IScript {


	/**
	 * 进入房间
	 * 
	 * @param role
	 * @param room
	 * @return
	 */
	default void enterRoom(Role role, Room room) {

	}

	/**
	 * 进入房间
	 * 
	 * @param role
	 * @param roomType
	 *            房间类型
	 * @param rank
	 *            级别
	 */
	default void enterRoom(Role role, RoomType roomType, int rank) {

	}

	/**
	 * 退出房间
	 * 
	 * @param role
	 * @param room
	 */
	default void quitRoom(Role role, Room room) {

	}

	/**
	 * 跑马灯
	 * <p>
	 * 没有辛运星
	 * </p>
	 * 
	 * 
	 * @param role
	 * @param baseGold
	 * @param accumulateGold
	 */
	default void sendPmd(Role role, int totalGold, int accumulateGold, int multiple, String fishName) {

	}

	/**
	 * 销毁房间
	 *
	 * @param room
	 */
	default void destoryRoom(Room iRoom) {

	}
	
	/**
	 * 每秒执行
	 * @param localTime
	 */
    default void secondHandler(Room room,LocalTime localTime) {

    }

	/**
	 * 每分钟执行
	 * @param localTime
	 */
	default void minuteHandler(Room room,LocalTime localTime) {

	}

	/**
	 * 每小时执行
	 * @param localTime
	 */
	default void hourHandler(Room room,LocalTime localTime) {

	}
}
