package com.pascloud.bydr.script.room;

import java.util.ArrayList;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.manager.RoomManager;
import com.pascloud.bydr.script.IFishScript;
import com.pascloud.bydr.struct.Fish;
import com.pascloud.bydr.struct.room.Room;
import com.pascloud.core.utils.MathUtil;


/**
 * 刷新房间鱼群
 * TODO 新刷新规则
 */
public class RoomFishRefreshScript implements IFishScript{
	private static final Logger LOGGER=LoggerFactory.getLogger(RoomFishRefreshScript.class);

	@Override
	public void fishRefresh(Room iRoom) {
//		LOGGER.warn("刷新鱼");
		Room room=(Room) iRoom;
		//TODO 测试，每秒刷新一条鱼
		Fish fish1 = bornFish(room, MathUtil.random(1, 5), null);
		Fish fish2 = bornFish(room, MathUtil.random(1, 5), null);
		Fish fish3 = bornFish(room, MathUtil.random(1, 5), null);
		Fish fish4 = bornFish(room, MathUtil.random(1, 5), null);
		Fish fish5 = bornFish(room, MathUtil.random(1, 5), null);
		
		RoomManager.getInstance().broadcastFishEnter(room, fish1,fish2,fish3,fish4,fish5/*,fish1,fish2,fish3,fish4,fish5*/);
		
	}
	
	/**
	 * 出生鱼
	 * @param configId
	 * @param fish
	 * @return
	 */
	private Fish bornFish(Room room, int configId,Consumer<Fish> fishConsumer) {
		Fish fish=new Fish();
		fish.setConfigId(configId);
		
		if(fishConsumer!=null) {
			fishConsumer.accept(fish);
		}
		fish.setRoom(room);
		fish.setTrackIds(new ArrayList<Integer>());
		room.getFishMap().put(fish.getId(), fish);
		return fish;
	}

}
