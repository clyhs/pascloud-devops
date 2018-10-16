package com.pascloud.hall.tcp.login;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.hall.manager.RoleManager;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallLoginMessage.QuitRequest;
import com.pascloud.message.hall.HallLoginMessage.QuitResponse;

/**
 * 退出游戏
 */
@HandlerEntity(mid=MID.QuitReq_VALUE,msg=QuitRequest.class)
public class QuitHandler extends TcpHandler {

	@Override
	public void run() {
		QuitRequest req=getMsg();
		RoleManager.getInstance().quit(rid, Reason.UserQuit);
		
		QuitResponse.Builder builder=QuitResponse.newBuilder();
		builder.setResult(0);
		sendIdMsg(builder.build());
	}

}