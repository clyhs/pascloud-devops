package com.pascloud.game.model.handler.http;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.pascloud.core.handler.HttpHandler;
import com.pascloud.core.script.IConfigScript;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.utils.MsgUtil;
import com.pascloud.core.utils.SymbolUtil;
import com.pascloud.game.model.constant.Config;

public class ReloadConfigHandler extends HttpHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReloadConfigHandler.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String auth = getString("auth");
		if (!Config.SERVER_AUTH.equals(auth)) {
			sendMsg("验证失败");
			return;
		}
		String tableStr = getString("table");
		String result = "";
		if (tableStr != null) {
			result = ScriptManager.getInstance().getBaseScriptEntry().functionScripts(IConfigScript.class,
					(IConfigScript script) -> script
							.reloadConfig(Arrays.asList(tableStr.split(SymbolUtil.DOUHAO_REG))));
		} else {
			result = ScriptManager.getInstance().getBaseScriptEntry().functionScripts(IConfigScript.class,
					(IConfigScript script) -> script.reloadConfig(null));
		}

		String info = String.format("%s加载配置：%s", MsgUtil.getIp(getSession()), result);
		LOGGER.info(info);
		
		sendMsg(info);
		//send email

	}

}
