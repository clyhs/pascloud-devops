package com.pascloud.game.model.handler.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.HttpHandler;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.utils.MsgUtil;
import com.pascloud.game.model.constant.Config;


@HandlerEntity(path = "/server/reloadScript")
public class ReloadScriptHandler extends HttpHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReloadScriptHandler.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String auth = getString("auth");
		String scriptPath = getString("scriptPath");
		if (!Config.SERVER_AUTH.equals(auth)) {
			sendMsg("验证失败");
			return;
		}
		String loadClasss=null;
		if(scriptPath==null){
			loadClasss=ScriptManager.getInstance().init(null);
		}else{
			if(scriptPath.contains(",")){
				String[] split = scriptPath.split(",");
				loadClasss=ScriptManager.getInstance().loadJava(split);
			}else{
				loadClasss=ScriptManager.getInstance().loadJava(scriptPath);
			}
		}
		
		String info = String.format("%s加载脚本：%s", MsgUtil.getIp(getSession()),loadClasss);
		LOGGER.info(info);
		sendMsg(info);

	}

}
