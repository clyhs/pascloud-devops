package com.pascloud.gate.server;


import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.mina.service.GameHttpSevice;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.game.model.handler.http.ExitServerHandler;
import com.pascloud.game.model.handler.http.GetFaviconHandler;
import com.pascloud.game.model.handler.http.JvmInfoHandler;
import com.pascloud.game.model.handler.http.ReloadConfigHandler;
import com.pascloud.game.model.handler.http.ReloadScriptHandler;
import com.pascloud.game.model.handler.http.ThreadInfoHandler;

/**
 * http服务器
 * @author admin
 *
 */
public class GateHttpServer extends GameHttpSevice {
	
	public GateHttpServer(MinaServerConfig minaServerConfig) {
		super(minaServerConfig);
	}

	@Override
	protected void running() {
		super.running();
		// 添加关服、脚本加载 等公用 handler
		ScriptManager.getInstance().addIHandler(ReloadScriptHandler.class);
		ScriptManager.getInstance().addIHandler(ExitServerHandler.class);
		ScriptManager.getInstance().addIHandler(ReloadConfigHandler.class);
		ScriptManager.getInstance().addIHandler(JvmInfoHandler.class);
		ScriptManager.getInstance().addIHandler(GetFaviconHandler.class);
		ScriptManager.getInstance().addIHandler(ThreadInfoHandler.class);
	}

}
