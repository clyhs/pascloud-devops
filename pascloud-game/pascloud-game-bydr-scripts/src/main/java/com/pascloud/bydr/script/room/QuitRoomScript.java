package com.pascloud.bydr.script.room;

import java.util.Iterator;

import com.pascloud.bydr.script.IRoomScript;
import com.pascloud.bydr.struct.role.Role;
import com.pascloud.bydr.struct.room.Room;

/**
 * 退出房间
 * @author admin
 *
 */
public class QuitRoomScript implements IRoomScript {

	@Override
	public void quitRoom(Role role, Room room) {
		Iterator<Role> iterator = room.getRoles().values().iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getId() == role.getId()) {
				iterator.remove();
			}
		}
		//TODO 
	}

}
