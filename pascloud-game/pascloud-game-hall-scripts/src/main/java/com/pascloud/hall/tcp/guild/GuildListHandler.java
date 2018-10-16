package com.pascloud.hall.tcp.guild;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallGuildMessage.GuildListRequest;

@HandlerEntity(mid=MID.GuildListReq_VALUE,msg=GuildListRequest.class)
public class GuildListHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GuildListHandler.class);

	@Override
	public void run() {
		GuildListRequest request = getMsg();
	}
}