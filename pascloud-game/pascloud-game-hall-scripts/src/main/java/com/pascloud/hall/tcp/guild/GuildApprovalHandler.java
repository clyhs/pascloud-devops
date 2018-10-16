package com.pascloud.hall.tcp.guild;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallGuildMessage.GuildApprovalRequest;

@HandlerEntity(mid=MID.GuildApprovalReq_VALUE,msg=GuildApprovalRequest.class)
public class GuildApprovalHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GuildApprovalHandler.class);

	@Override
	public void run() {
		GuildApprovalRequest request = getMsg();
	}
}