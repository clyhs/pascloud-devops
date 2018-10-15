package com.pascloud.gate.tcp.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.gate.manager.UserSessionManager;
import com.pascloud.gate.struct.UserSession;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallLoginMessage.QuitResponse;

/**
 * 退出游戏返回<br>
 * 
 * @note 在请求消息时已经移除了角色的连接会话信息,所有返回消息会话是游戏内部会话，不能从session中获取属性,不能关闭
 */
@HandlerEntity(mid = MID.QuitRes_VALUE, msg = QuitResponse.class)
public class QuitResHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuitResHandler.class);

	@Override
	public void run() {
		QuitResponse res = getMsg();
		UserSession userSession=UserSessionManager.getInstance().getUserSessionbyRoleId(rid);
		if(userSession==null) {
			LOGGER.warn("角色{}会话已遗失",rid);
			return ;
		}

		if (userSession.getClientSession() != null) {
			userSession.sendToClient(res);
			userSession.getClientSession().closeOnFlush();
			LOGGER.info("{}退出游戏", userSession.getRoleId());
		}
		
		//返回结果再移除，防止一些消息得不到转发失败
		UserSessionManager.getInstance().quit(userSession, Reason.UserQuit);

	}

}
