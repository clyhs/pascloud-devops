package com.pascloud.gate.server.handler;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.mina.handler.ClientProtocolHandler;
import com.pascloud.core.server.AbsService;
import com.pascloud.core.utils.MsgUtil;
import com.pascloud.game.model.constant.Config;
import com.pascloud.gate.struct.UserSession;

/**
 * udp消息处理器
 */
public class GateUdpUserServerHandler extends ClientProtocolHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger(GateUdpUserServerHandler.class);
	
	public GateUdpUserServerHandler() {
		super(8);
	}
	
	public GateUdpUserServerHandler(AbsService<MinaServerConfig> service){
		this();
		this.service=service;
	}

	/**
	 * 消息转发到大厅服或游戏服务器
	 * 
	 * @param bytes
	 *            前8字节分别为消息ID、protobuf长度
	 */
	@Override
	protected void forward(IoSession session, int msgID, byte[] bytes) {
		// 转发到大厅服
		if (msgID < Config.HALL_MAX_MID) {
			forwardToHall(session, msgID, bytes);
			return;
		}
		// 转发到游戏服
		Object attribute = session.getAttribute(Config.USER_SESSION);
		if (attribute != null) {
			UserSession userSession = (UserSession) attribute;
			if (userSession.getRoleId() > 0) {
				if (userSession.sendToGame(MsgUtil.clientToGame(msgID, bytes))) {
					return;
				} else {
					LOGGER.warn("角色[{}]没有连接游戏服务器,消息{}发送失败", userSession.getRoleId(),msgID);
					return;
				}
			}

		}
		LOGGER.warn("{}消息[{}]未找到玩家", MsgUtil.getIp(session), msgID);
	}

	/**
	 * 消息转发到大厅服务器
	 * 
	 * @author JiangZhiYong
	 * @QQ 359135103 2017年7月21日 上午10:14:44
	 * @param session
	 * @param msgID
	 * @param bytes
	 */
	private void forwardToHall(IoSession session, int msgID, byte[] bytes) {
		Object attribute = session.getAttribute(Config.USER_SESSION);
		if (attribute != null) {
			UserSession userSession = (UserSession) attribute;
			if (userSession.getRoleId() > 0) {
				if (!userSession.sendToHall(MsgUtil.clientToGame(msgID, bytes))) {
					LOGGER.warn("角色[{}]没有连接大厅服务器", userSession.getRoleId());
					return;
				}

			}
		}
		LOGGER.warn("[{}]消息未找到对应的处理方式", msgID);
	}

	@Override
	public AbsService<MinaServerConfig> getService() {
		return this.service;
	}

	@Override
	public void sessionOpened(IoSession session) {
		super.sessionOpened(session);
//		UserSession userSession = new UserSession(session);
//		session.setAttribute(Config.USER_SESSION, userSession);
		//TODO
	}

	@Override
	public void sessionClosed(IoSession session) {
		super.sessionClosed(session);
//		ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IUserScript.class,
//				script -> script.quit(session, Reason.SessionClosed));
		session.closeNow();	//TODO ?
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus idleStatus) {
		super.sessionIdle(session, idleStatus);
//		ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IUserScript.class,
//				script -> script.quit(session, Reason.SessionIdle));
		session.closeNow();	//TODO ?
	}
	
}
