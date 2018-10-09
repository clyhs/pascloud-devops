package com.pascloud.cluster.http;

import org.apache.mina.http.api.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.HttpHandler;

@HandlerEntity(path = "")
public class UnknowHttpRequestHandler extends HttpHandler {
	private static final Logger LOGGER=LoggerFactory.getLogger(UnknowHttpRequestHandler.class);

	@Override
	public void run() {
		LOGGER.info("{}请求页面错误",getSession().getRemoteAddress().toString());
		getParameter().setStatus(HttpStatus.CLIENT_ERROR_NOT_FOUND);
		responseWithStatus();
		
	}

}