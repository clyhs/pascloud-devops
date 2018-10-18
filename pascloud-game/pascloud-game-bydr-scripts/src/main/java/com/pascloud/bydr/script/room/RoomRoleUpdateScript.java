package com.pascloud.bydr.script.room;

import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.script.IRoomScript;
import com.pascloud.bydr.struct.room.Room;
import com.pascloud.game.model.constant.Reason;

/**
 * 房间角色监测更新
 */
public class RoomRoleUpdateScript implements IRoomScript{
	private static final Logger LOGGER=LoggerFactory.getLogger(RoomRoleUpdateScript.class);

	@Override
	public void secondHandler(Room room, LocalTime localTime) {
		room.getRoles().forEach((seat,role)->{
//			//TODO 测试
//			RMap<Long, Item> items = (RMap<Long, Item>)role.getItems();
//			if(items!=null) {
//				Item item = items.get(380220319399937L);
//				item.setNum(item.getNum()+1);
//				items.put(380220319399937L, item);
//				LOGGER.debug("道具：{}",item.toString());
//			}
		});
	}

	@Override
	public void minuteHandler(Room room, LocalTime localTime) {
		
		room.getRoles().forEach((seat,role)->{
			//更新金币
			if(role.getWinGold()!=0) {
				role.syncGold(Reason.RoleSync);
			}
		});
	}

}
