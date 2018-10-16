package com.pascloud.hall.script;

import java.util.function.Consumer;

import com.pascloud.core.script.IScript;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.game.model.strut.Item;
import com.pascloud.game.model.strut.Role;

/**
 * 道具
 */
public interface IPacketScript extends IScript {

	/**
	 * 使用道具
	 * @param id 道具Id
	 * @param num 数量
	 * @param itemConsumer
	 */
	default void useItem(Role role,long id,int num,Reason reason,Consumer<Item> itemConsumer) {
		
	}
	
	/**
	 * 添加道具
	 * @param configId
	 * @param num 数量
	 * @param reason
	 * @param itemConsumer
	 */
	default Item addItem(Role role,int configId,int num,Reason reason,Consumer<Item> itemConsumer) {
		return null;
	}
}
