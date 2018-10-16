package com.pascloud.bydr.thread.timer;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pascloud.bydr.script.IFishScript;
import com.pascloud.bydr.script.IRoomScript;
import com.pascloud.bydr.struct.room.Room;
import com.pascloud.bydr.thread.RoomThread;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.script.ScriptPool;
import com.pascloud.core.thread.timer.TimerEvent;

/**
 * 房间定时器
 * 
 */
public class RoomTimer extends TimerEvent {

	List<Room> rooms = Collections.synchronizedList(new ArrayList<>());
	private RoomThread roomThread; // 依赖的房间线程
	protected int hour = -1;
	protected int min = -1;
	protected int sec = -1;

	public RoomTimer(Room room, RoomThread roomThread) {
		super(Long.MAX_VALUE, -1);
		this.rooms.add(room);
		this.roomThread = roomThread;
	}

	public void addRoom(Room room) {
		this.rooms.add(room);
	}

	public void removeRoom(Room room) {
		rooms.remove(room);
	}

	@Override
	public void run() {
		LocalTime localTime = LocalTime.now();
		int _sec = localTime.getSecond();
		int _min = localTime.getMinute();
		int _hour = localTime.getHour();
		// 分成多个任务执行
		rooms.forEach(room -> {
			ScriptPool scriptPool = ScriptManager.getInstance().getBaseScriptEntry();

			// 鱼群刷新
			this.roomThread
					.execute(() -> scriptPool.executeScripts(IFishScript.class, script -> script.fishRefresh(room)));

			// 每秒执行
			if (sec != _sec) {
				sec=_sec;
				this.roomThread.execute(() -> scriptPool.executeScripts(IRoomScript.class,
						script -> script.secondHandler(room, localTime)));
			}
			
			//每分钟执行
			if(min!=_min) {
				min=_min;
				scriptPool.executeScripts(IRoomScript.class, script->script.minuteHandler(room, localTime));
			}
			
			if (hour != _hour) { // 每小时执行
				hour = _hour;
				scriptPool.executeScripts(IRoomScript.class, script->script.minuteHandler(room, localTime));
			}
		});

	}

}
