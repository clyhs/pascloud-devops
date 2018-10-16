package com.pascloud.hall.script;

import java.util.function.Consumer;

import com.pascloud.core.script.IScript;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.game.model.strut.Role;


/**
 * 玩家脚本
 *
 */
public interface IRoleScript extends IScript {

	/**
	 * 登录游戏
	 */
	default public void login(Role role,Reason reason) {

	}

	/**
	 * 创建角色
	 * 
	 * @param userId
	 * @return
	 */
	default public Role createRole(long userId, Consumer<Role> roleConsumer) {

		return null;
	}
	
	/**
	 * 角色退出游戏
	 * @param role
	 */
	default void quit(Role role,Reason reason) {
		
	}
	
}
