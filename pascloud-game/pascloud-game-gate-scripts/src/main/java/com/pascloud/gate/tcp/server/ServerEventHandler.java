package com.pascloud.gate.tcp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.gate.manager.UserSessionManager;
import com.pascloud.gate.script.IUserScript;
import com.pascloud.gate.struct.UserSession;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.ServerMessage.ServerEventRequest;

/**
 * 事件消息
 */
@HandlerEntity(mid=MID.ServerEventReq_VALUE,msg=ServerEventRequest.class)
public class ServerEventHandler extends TcpHandler {
	private static final Logger LOGGER=LoggerFactory.getLogger(ServerEventHandler.class);
	@Override
	public void run() {
		ServerEventRequest request=getMsg();
		switch (request.getType()) {
		case 1:	//gm踢玩家下线
			tickRole(request);
			break;

		default:
			break;
		}
		LOGGER.info("处理事件{}",request.toString());
	}
	
	/**
	 * 
	 * @param request
	 */
	private void tickRole(ServerEventRequest request) {
		UserSession userSession = UserSessionManager.getInstance().getUserSessionbyRoleId(request.getId());
		if(userSession==null) {
			return;
		}
		ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IUserScript.class, script->script.quit(userSession.getClientSession(), Reason.GmTickRole));
	}

}
