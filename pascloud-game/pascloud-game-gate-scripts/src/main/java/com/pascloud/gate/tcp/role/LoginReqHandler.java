package com.pascloud.gate.tcp.role;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.mina.message.IDMessage;
import com.pascloud.core.server.ServerInfo;
import com.pascloud.core.server.ServerType;
import com.pascloud.core.utils.MsgUtil;
import com.pascloud.gate.manager.ServerManager;
import com.pascloud.gate.manager.UserSessionManager;
import com.pascloud.gate.server.GateServer;
import com.pascloud.gate.struct.UserSession;
import com.pascloud.message.hall.HallLoginMessage.LoginRequest;
import com.pascloud.message.hall.HallLoginMessage.LoginRequest.Builder;
import com.pascloud.message.system.SystemMessage.SystemErroCode;
import com.pascloud.message.system.SystemMessage.SystemErrorResponse;
import com.pascloud.message.Mid.MID;


/**
 * 登录请求
 * <p>
 * 保存用户session 设置UserSession 大厅ID，大厅session<br>
 * TODO 重连处理？？？？
 * </p>
 */
@HandlerEntity(mid = MID.LoginReq_VALUE, desc = "登陆", msg = LoginRequest.class)
public class LoginReqHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginReqHandler.class);

	@Override
	public void run() {
		LoginRequest request = getMsg();
		UserSession userSession = UserSessionManager.getInstance().getUserSessionBySessionId(session.getId());
		if (userSession == null) {
			session.write(
					ServerManager.getInstance().buildSystemErrorResponse(SystemErroCode.ConectReset, "连接会话已失效，请重连"));
			LOGGER.warn("连接会话已失效，请重连");
			return;
		}
		
		ServerInfo serverInfo = ServerManager.getInstance().getIdleGameServer(ServerType.HALL,userSession);
		if (serverInfo == null) {
			SystemErrorResponse.Builder sysBuilder = SystemErrorResponse.newBuilder();
			sysBuilder.setErrorCode(SystemErroCode.HallNotFind);
			sysBuilder.setMsg("未开启大厅服");
			getSession().write(sysBuilder.build());
			LOGGER.warn("大厅服不可用");
			return;
		}
		IoSession hallSession = serverInfo.getMostIdleIoSession();
		Builder builder = request.toBuilder();
		builder.setSessionId(session.getId());
		builder.setIp(MsgUtil.getIp(session));
		builder.setGateId(GateServer.getInstance().getGateTcpUserServer().getMinaServerConfig().getId());
		if (serverInfo == null || hallSession == null) {
			LOGGER.warn("大厅服务器未准备就绪");
			session.write(ServerManager.getInstance().buildSystemErrorResponse(SystemErroCode.HallNotFind, "没可用大厅服"));
			return;
		}
		
		userSession.setHallServerId(serverInfo.getId());
		userSession.setHallSession(hallSession);
		userSession.setVersion(request.getVersion());

		IDMessage idMessage = new IDMessage(hallSession, builder.build(), 0);
		idMessage.run();

	}

}
