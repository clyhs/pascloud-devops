package com.pascloud.game.model.handler.http;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.HttpHandler;


@HandlerEntity(path="/favicon.ico")
public class GetFaviconHandler extends HttpHandler {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		sendMsg("favicon.ico");

	}

}
