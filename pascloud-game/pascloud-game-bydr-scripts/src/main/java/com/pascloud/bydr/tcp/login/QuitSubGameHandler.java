package com.pascloud.bydr.tcp.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.manager.RoleManager;
import com.pascloud.bydr.manager.RoomManager;
import com.pascloud.bydr.struct.role.Role;
import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallLoginMessage.QuitSubGameRequest;
import com.pascloud.message.hall.HallLoginMessage.QuitSubGameResponse;

/**
 * 退出子游戏 TODO 数据持久化等处理
 * 
 */
@HandlerEntity(mid = MID.QuitSubGameReq_VALUE, msg = QuitSubGameRequest.class)
public class QuitSubGameHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuitSubGameHandler.class);

	@Override
	public void run() {
		QuitSubGameRequest req = getMsg();
		LOGGER.info("{}退出捕鱼", getRid());
		Role role = RoleManager.getInstance().getRole(getRid());
		if (role == null) {
			return;
		}
		// 退出房间
		if (role.getRoomId() > 0) {
			RoomManager.getInstance().quitRoom(role, role.getRoomId());
		}

		RoleManager.getInstance().quit(role, Reason.UserQuit);

		QuitSubGameResponse.Builder builder = QuitSubGameResponse.newBuilder();
		builder.setResult(0);
		sendIdMsg(builder.build());
	}

}
