package com.pascloud.gate.tcp.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.redis.jedis.JedisManager;
import com.pascloud.core.thread.ThreadType;
import com.pascloud.game.model.redis.key.HallKey;
import com.pascloud.gate.manager.ServerManager;
import com.pascloud.gate.manager.UserSessionManager;
import com.pascloud.gate.struct.UserSession;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallLoginMessage.LoginResponse;
import com.pascloud.message.system.SystemMessage.SystemErroCode;

/**
 * 登陆返回
 *<p>处理用户的连接session,设置UserSession用户ID，角色ID</p>
 */
@HandlerEntity(mid = MID.LoginRes_VALUE, desc = "登陆", thread = ThreadType.IO, msg = LoginResponse.class)
public class LoginResHandler extends TcpHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginResHandler.class);

	@Override
	public void run() {
		LoginResponse response = getMsg();
		UserSession userSession = UserSessionManager.getInstance().getUserSessionBySessionId(response.getSessionId());
		if (userSession == null) {
			session.write(
					ServerManager.getInstance().buildSystemErrorResponse(SystemErroCode.ConectReset, "连接会话已失效，请重连"));
			LOGGER.warn("连接会话已失效，请重连");
			return;
		}
		String key = HallKey.Role_Map_Info.getKey(userSession.getRoleId());
		JedisManager.getJedisCluster().hset(key, "hallId", String.valueOf(userSession.getHallServerId()));
		UserSessionManager.getInstance().loginHallSuccess(userSession, response.getUid(), response.getRid());
		userSession.sendToClient(response);
	}

	

}
