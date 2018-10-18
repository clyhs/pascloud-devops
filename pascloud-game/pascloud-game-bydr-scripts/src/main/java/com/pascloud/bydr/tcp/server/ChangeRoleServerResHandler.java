package com.pascloud.bydr.tcp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.manager.RoleManager;
import com.pascloud.bydr.struct.role.Role;
import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.ServerMessage.ChangeRoleServerResponse;

/**
 * 改变服务器结果
 * 
 */
@HandlerEntity(mid = MID.ChangeRoleServerRes_VALUE, msg = ChangeRoleServerResponse.class)
public class ChangeRoleServerResHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChangeRoleServerResHandler.class);

	@Override
	public void run() {
		ChangeRoleServerResponse res = getMsg();
		Role role = RoleManager.getInstance().getRole(rid);
		if (res.getResult() == 0 && role != null) {
			RoleManager.getInstance().quit(role, Reason.CrossServer);
		}

		LOGGER.info("角色{}跨服退出{}", rid, res.getResult());

	}

}
