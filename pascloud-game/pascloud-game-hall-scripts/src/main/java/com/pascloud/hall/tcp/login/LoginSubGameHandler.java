package com.pascloud.hall.tcp.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallLoginMessage.LoginSubGameRequest;

@HandlerEntity(mid=MID.LoginSubGameReq_VALUE,msg=LoginSubGameRequest.class)
public class LoginSubGameHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginSubGameHandler.class);

	@Override
	public void run() {
		LoginSubGameRequest request = getMsg();
	}
}