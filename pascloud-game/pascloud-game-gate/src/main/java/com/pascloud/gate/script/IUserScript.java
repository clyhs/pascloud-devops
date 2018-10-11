package com.pascloud.gate.script;

import org.apache.mina.core.session.IoSession;

import com.pascloud.core.script.IScript;
import com.pascloud.game.model.constant.Reason;

public interface IUserScript extends IScript {
	
	/**
	 * 用户退出处理
	 * @param session 游戏客户端会话
	 * @param reason 原因
	 */
	default void quit(IoSession session,Reason reason){
		
	}
	
}