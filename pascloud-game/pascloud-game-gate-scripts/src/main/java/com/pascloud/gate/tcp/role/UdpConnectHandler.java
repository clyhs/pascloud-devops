package com.pascloud.gate.tcp.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.utils.MsgUtil;
import com.pascloud.game.model.constant.Config;
import com.pascloud.gate.manager.UserSessionManager;
import com.pascloud.gate.struct.UserSession;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.system.SystemMessage.UdpConnectRequest;
import com.pascloud.message.system.SystemMessage.UdpConnectResponse;

/**
 * 请求进行udp连接
 * 
 */
@HandlerEntity(mid = MID.UdpConnectReq_VALUE, msg = UdpConnectRequest.class)
public class UdpConnectHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(UdpConnectHandler.class);

	@Override
	public void run() {
		UdpConnectRequest req = getMsg();
//		LOGGER.info("udp连接：{}", req.toString());
		UserSession userSession = UserSessionManager.getInstance().getUserSessionbyRoleId(req.getRid());
		UserSession userSession2 = UserSessionManager.getInstance().getUserSessionBySessionId(req.getSessionId());
		UdpConnectResponse.Builder builder = UdpConnectResponse.newBuilder();
		builder.setResult(0);
		if (userSession == null) {
			builder.setResult(1);
		} else if (!userSession.equals(userSession2)) {
			builder.setResult(2);
		}
		// 地址不正确
		if (!MsgUtil.getIp(session).equals(MsgUtil.getIp(userSession.getClientSession()))) {
			builder.setResult(3);
		}
		userSession.setClientUdpSession(getSession());
		session.setAttribute(Config.USER_SESSION, userSession);

		session.write(builder.build());
	}

}
