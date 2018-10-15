package com.pascloud.gate.script;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.game.model.constant.Config;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.gate.manager.UserSessionManager;
import com.pascloud.gate.struct.UserSession;
import com.pascloud.message.hall.HallLoginMessage.QuitRequest;
import com.pascloud.message.hall.HallLoginMessage.QuitSubGameRequest;

public class UserQuitScript implements IUserScript {
	private static final Logger LOGGER=LoggerFactory.getLogger(UserQuitScript.class);

	@Override
	public void quit(IoSession session, Reason reason) {
		Object attribute = session.getAttribute(Config.USER_SESSION);
		if(attribute==null){
			LOGGER.warn("session 为空 ：{}",reason.toString());
			return;
		}
		
		UserSession userSession=(UserSession)attribute;
		
		
		//是否连接子游戏
		if(userSession.getGameSession()!=null){
			QuitSubGameRequest.Builder builder=QuitSubGameRequest.newBuilder();
			builder.setRid(userSession.getRoleId());
			userSession.sendToGame(builder.build());
			userSession.removeGame();
		}
		
		//是否连接大厅服
		if(userSession.getHallSession()!=null){
			QuitRequest.Builder builder=QuitRequest.newBuilder();
			builder.setRid(userSession.getRoleId());
			userSession.sendToHall(builder.build());
			userSession.removeHall();
		}
		if(Reason.SessionIdle==reason||Reason.GmTickRole==reason||Reason.ServerClose==reason){
			session.closeOnFlush();
			UserSessionManager.getInstance().quit(userSession, reason);
		}
		
	}

	
}