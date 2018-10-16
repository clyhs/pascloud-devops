package com.pascloud.bydr.script;

import com.pascloud.bydr.struct.room.Room;
import com.pascloud.core.script.IScript;

/**
 * 鱼脚本
 *
 */
public interface IFishScript extends IScript {

	/**
	 * 刷新鱼
	 * 
	 * @param room
	 */
	default void fishRefresh(Room room) {

	}

	/**
	 * 鱼死亡
	 * 
	 * @param room
	 */
	default void fishDie(Room room) {

	}


}
