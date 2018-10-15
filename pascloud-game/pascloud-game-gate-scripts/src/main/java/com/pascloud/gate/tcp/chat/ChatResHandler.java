package com.pascloud.gate.tcp.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.gate.manager.UserSessionManager;
import com.pascloud.gate.struct.UserSession;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallChatMessage.ChatResponse;


/**
 * 聊天消息返回
 * 
 */
@HandlerEntity(mid=MID.ChatRes_VALUE,msg=ChatResponse.class)
public class ChatResHandler extends TcpHandler {
	private static final Logger LOGGER=LoggerFactory.getLogger(ChatResHandler.class);
	@Override
	public void run() {
		ChatResponse res=getMsg();
		switch (res.getChatType()) {
		case PRIVATE:	//在当前网关则转发
			UserSession userSession = UserSessionManager.getInstance().getUserSessionbyRoleId(this.rid);
			if(userSession!=null) {
				userSession.sendToClient(res);
			}
			break;
		case WORLD:		//广播给所有玩家
		case PMD:
			UserSessionManager.getInstance().broadcast(res);
			break;
		default:
			break;
		}
	}

}
