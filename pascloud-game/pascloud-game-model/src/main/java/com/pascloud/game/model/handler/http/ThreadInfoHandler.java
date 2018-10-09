package com.pascloud.game.model.handler.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.HttpHandler;
import com.pascloud.core.utils.SysUtil;
import com.pascloud.game.model.constant.Config;


@HandlerEntity(path = "/server/thread/info")
public class ThreadInfoHandler extends HttpHandler {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(ThreadInfoHandler.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String auth = getString("auth");
		if (!Config.SERVER_AUTH.equals(auth)) {
			sendMsg("验证失败");
			return;
		}
		String info = SysUtil.threadInfo("<br>");
		LOGGER.info(info);
		info=info.trim().replaceAll("/n", "").replaceAll("/t", "&nbsp;&nbsp;");
		sendMsg(info);
	}

}
