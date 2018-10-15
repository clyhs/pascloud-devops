package com.pascloud.gate.tcp.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.gate.manager.UserSessionManager;
import com.pascloud.gate.struct.UserSession;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallLoginMessage.QuitSubGameResponse;

/**
 * 退出子游戏返回
 * 
 */
@HandlerEntity(mid = MID.QuitSubGameRes_VALUE, msg = QuitSubGameResponse.class)
public class QuitSubGameResHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuitSubGameResHandler.class);

	@Override
	public void run() {
		QuitSubGameResponse res = getMsg();
		UserSession userSession=UserSessionManager.getInstance().getUserSessionbyRoleId(rid);
		if(userSession==null) {
//			LOGGER.warn("角色{}会话已遗失",rid);
			return ;
		}
		
//		LOGGER.info("角色{}退出：{}", userSession.getRoleId(), userSession.getServerType().toString());
		if (userSession.getClientSession() != null) {
			userSession.sendToClient(res);
			userSession.removeGame();
		}

	}

}
