package com.pascloud.bydr.script;

import java.util.function.Consumer;

import com.pascloud.bydr.struct.role.Role;
import com.pascloud.core.script.IScript;
import com.pascloud.game.model.constant.Reason;


/**
 * 角色
 */
public interface IRoleScript extends IScript{

	/**
	 * 登录
	 * @param roleId
	 * @param reason
	 * @param roleConsumer
	 */
	default void login(long roleId,Reason reason,Consumer<Role> roleConsumer){
		
	}
	
	/**
	 * 退出
	 * @param role
	 * @param reason
	 */
	default void quit(Role role,Reason reason){
		
	}
	
	/**
	 * 修改金币
	 * @param role
	 * @param gold
	 * @param reason
	 */
	default void changeGold(Role role,int gold,Reason reason) {
		
	}
	
	/**
	 * 奖金币同步大大厅
	 * @param role
	 * @param reason
	 */
	default void syncGold(Role role,Reason reason) {
		
	}
}
