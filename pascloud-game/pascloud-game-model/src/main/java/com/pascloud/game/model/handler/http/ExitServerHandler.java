package com.pascloud.game.model.handler.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.HttpHandler;
import com.pascloud.core.utils.MsgUtil;
import com.pascloud.game.model.constant.Config;

@HandlerEntity(path = "/server/exit")
public class ExitServerHandler extends HttpHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExitServerHandler.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String auth = getString("auth");
		if (!Config.SERVER_AUTH.equals(auth)) {
			sendMsg("验证失败");
			return;
		}
		String info = String.format("%s关闭服务器", MsgUtil.getIp(getSession()));
		LOGGER.info(info);
		sendMsg(info);

	}

}
