package com.pascloud.gate.tcp.role;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.gate.script.IUserScript;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallLoginMessage.QuitRequest;

/**
 * 退出游戏
 */
@HandlerEntity(mid=MID.QuitReq_VALUE,msg=QuitRequest.class)
public class QuitReqHandler extends TcpHandler {

	@Override
	public void run() {
		ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IUserScript.class, script->script.quit(getSession(), Reason.UserQuit));

	}

}
