package com.pascloud.hall.script;

import java.util.function.Consumer;

import com.pascloud.core.script.IScript;
import com.pascloud.game.model.strut.User;

/**
 * 用户接口
 * 
 */
public interface IUserScript extends IScript {

	/**
	 * 创建用户
	 * 
	 * @param userConsumer
	 * @return
	 */
	default public User createUser(Consumer<User> userConsumer) {
		return null;
	}
}
