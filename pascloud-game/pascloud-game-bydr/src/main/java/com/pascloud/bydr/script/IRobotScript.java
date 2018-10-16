package com.pascloud.bydr.script;

import java.util.function.Consumer;

import com.pascloud.core.script.IScript;
import com.pascloud.game.model.strut.Role;

/**
 * 机器人脚本
 *
 */
public interface IRobotScript extends IScript{

	/**
	 * 创建机器人
	 * @param roleConsumer
	 */
	default Role createRobot(Consumer<Role> roleConsumer){
		return null;
	}
	
	/**
	 * 检查机器人金币，并修正
	 * @param robot
	 */
	default void checkGold(Role robot){
		
	}
}
