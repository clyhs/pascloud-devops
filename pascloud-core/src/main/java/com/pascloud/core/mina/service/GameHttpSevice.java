package com.pascloud.core.mina.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.mina.HttpServer;
import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.mina.handler.HttpServerIoHandler;
import com.pascloud.core.server.AbsService;
import com.pascloud.core.thread.ThreadPoolExecutorConfig;

/**
 * 游戏服http服务器
 * @author admin
 *
 */
public class GameHttpSevice extends AbsService<MinaServerConfig> {

	private static final Logger log = LoggerFactory.getLogger(GameHttpSevice.class);

    private final HttpServer httpServer;
    private final MinaServerConfig minaServerConfig;

    public GameHttpSevice(ThreadPoolExecutorConfig threadExcutorConfig, MinaServerConfig minaServerConfig) {
        super(threadExcutorConfig);
        this.minaServerConfig = minaServerConfig;
        this.httpServer = new HttpServer(minaServerConfig, new GameHttpServerHandler(this));
    }
    
    public GameHttpSevice(MinaServerConfig minaServerConfig) {
        super(null);
        this.minaServerConfig = minaServerConfig;
        this.httpServer = new HttpServer(minaServerConfig, new GameHttpServerHandler(this));
    }

    @Override
    protected void running() {
        log.debug(" run ... ");
        httpServer.run();
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

	public MinaServerConfig getMinaServerConfig() {
		return minaServerConfig;
	}

}

class GameHttpServerHandler extends HttpServerIoHandler {

    //private static final Logger log = LoggerFactory.getLogger(ClusterHttpServerHandler.class);

    private AbsService<MinaServerConfig> service;

    public GameHttpServerHandler(AbsService<MinaServerConfig> service) {
        this.service=service;
    }

	@Override
	protected AbsService<MinaServerConfig> getSevice() {
		return this.service;
	}
}