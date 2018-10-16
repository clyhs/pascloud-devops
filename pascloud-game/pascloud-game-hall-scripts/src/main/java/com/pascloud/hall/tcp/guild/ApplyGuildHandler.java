package com.pascloud.hall.tcp.guild;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallGuildMessage.ApplyGuildRequest;

@HandlerEntity(mid = MID.ApplyGuildReq_VALUE, msg = ApplyGuildRequest.class)
public class ApplyGuildHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplyGuildHandler.class);

	@Override
	public void run() {
		ApplyGuildRequest request = getMsg();
	}
}