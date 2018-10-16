package com.pascloud.hall.tcp.guild;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallGuildMessage.GuildInfoRequest;

@HandlerEntity(mid=MID.GuildInfoReq_VALUE,msg=GuildInfoRequest.class)
public class GuildInfoHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GuildInfoHandler.class);

	@Override
	public void run() {
		GuildInfoRequest request = getMsg();
	}
}