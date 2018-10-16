package com.pascloud.hall.server;

import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.mina.service.GameHttpSevice;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.game.model.handler.http.ExitServerHandler;
import com.pascloud.game.model.handler.http.GetFaviconHandler;
import com.pascloud.game.model.handler.http.JvmInfoHandler;
import com.pascloud.game.model.handler.http.ReloadConfigHandler;
import com.pascloud.game.model.handler.http.ReloadScriptHandler;
import com.pascloud.game.model.handler.http.ThreadInfoHandler;

public class HallHttpServer extends GameHttpSevice {

	public HallHttpServer(MinaServerConfig minaServerConfig) {
		super(minaServerConfig);
	}

	@Override
	protected void running() {
		super.running();
		ScriptManager.getInstance().addIHandler(ReloadScriptHandler.class);
		ScriptManager.getInstance().addIHandler(ExitServerHandler.class);
		ScriptManager.getInstance().addIHandler(ReloadConfigHandler.class);
		ScriptManager.getInstance().addIHandler(JvmInfoHandler.class);
		ScriptManager.getInstance().addIHandler(GetFaviconHandler.class);
		ScriptManager.getInstance().addIHandler(ThreadInfoHandler.class);
	}

}
