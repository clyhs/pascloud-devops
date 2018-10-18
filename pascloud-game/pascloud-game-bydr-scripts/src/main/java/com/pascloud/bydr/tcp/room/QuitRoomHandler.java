package com.pascloud.bydr.tcp.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.bydr.BydrRoomMessage.QuitRoomRequest;

@HandlerEntity(mid=MID.QuitRoomReq_VALUE,msg=QuitRoomRequest.class)
public class QuitRoomHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuitRoomHandler.class);

	@Override
	public void run() {
		QuitRoomRequest request = getMsg();
	}
}