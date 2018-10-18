package com.pascloud.bydr.tcp.fight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.bydr.BydrFightMessage.GunLeveUpRequest;

@HandlerEntity(mid=MID.GunLeveUpReq_VALUE,msg=GunLeveUpRequest.class)
public class GunLeveUpHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GunLeveUpHandler.class);

	@Override
	public void run() {
		GunLeveUpRequest request = getMsg();
	}
}