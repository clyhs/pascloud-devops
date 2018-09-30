package com.pascloud.cluster.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.mina.HttpServer;
import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.mina.handler.HttpServerIoHandler;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.server.AbsService;
import com.pascloud.core.thread.ThreadPoolExecutorConfig;
import com.pascloud.game.model.handler.http.ExitServerHandler;
import com.pascloud.game.model.handler.http.GetFaviconHandler;
import com.pascloud.game.model.handler.http.JvmInfoHandler;
import com.pascloud.game.model.handler.http.ReloadConfigHandler;
import com.pascloud.game.model.handler.http.ReloadScriptHandler;
import com.pascloud.game.model.handler.http.ThreadInfoHandler;

public class ClusterHttpServer extends AbsService<MinaServerConfig> {

	private static final Logger log = LoggerFactory.getLogger(ClusterHttpServer.class);

	private final HttpServer httpServer;
	private final MinaServerConfig minaServerConfig;

	public ClusterHttpServer(ThreadPoolExecutorConfig threadExcutorConfig, MinaServerConfig minaServerConfig) {
		super(threadExcutorConfig);
		this.minaServerConfig = minaServerConfig;
		this.httpServer = new HttpServer(minaServerConfig, new ClusterHttpServerHandler(this));
	}

	@Override
	protected void running() {
		// TODO Auto-generated method stub
		httpServer.run();
		ScriptManager.getInstance().addIHandler(ReloadScriptHandler.class);
		ScriptManager.getInstance().addIHandler(ExitServerHandler.class);
		ScriptManager.getInstance().addIHandler(ReloadConfigHandler.class);
		ScriptManager.getInstance().addIHandler(JvmInfoHandler.class);
		ScriptManager.getInstance().addIHandler(GetFaviconHandler.class);
		ScriptManager.getInstance().addIHandler(ThreadInfoHandler.class);
		
	}
	
	@Override
	protected void onShutdown() {
		super.onShutdown();
		log.debug(" stop ... ");
		httpServer.stop();
	}

	@Override
	public String toString() {
		return minaServerConfig.getName();
	}

}

class ClusterHttpServerHandler extends HttpServerIoHandler {

	// private static final Logger log =
	// LoggerFactory.getLogger(ClusterHttpServerHandler.class);

	private AbsService<MinaServerConfig> service;

	public ClusterHttpServerHandler(AbsService<MinaServerConfig> service) {
		this.service = service;
	}

	@Override
	protected AbsService<MinaServerConfig> getSevice() {
		return this.service;
	}

}
