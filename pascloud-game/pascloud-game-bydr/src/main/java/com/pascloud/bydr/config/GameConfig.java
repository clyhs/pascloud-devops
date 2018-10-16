package com.pascloud.bydr.config;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.pascloud.game.model.constant.Config;

/**
 * 游戏配置
 */
@Root
public class GameConfig extends Config {

	//线程房间数
	@Element(required=false)
	private int thradRoomNum=4;

	// 房间位置大小
	@Element(required = false)
	private int roomSize = 4;

	// 机器人个数
	@Element(required = false)
	private int robotNum = 30;

	
	

	public int getThradRoomNum() {
		return thradRoomNum;
	}

	public void setThradRoomNum(int thradRoomNum) {
		this.thradRoomNum = thradRoomNum;
	}

	public int getRoomSize() {
		return roomSize;
	}

	public void setRoomSize(int roomSize) {
		this.roomSize = roomSize;
	}

	public int getRobotNum() {
		return robotNum;
	}

	public void setRobotNum(int robotNum) {
		this.robotNum = robotNum;
	}


}