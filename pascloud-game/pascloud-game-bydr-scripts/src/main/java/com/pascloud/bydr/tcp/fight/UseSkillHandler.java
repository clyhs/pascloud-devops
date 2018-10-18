package com.pascloud.bydr.tcp.fight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.bydr.BydrFightMessage.UseSkillRequest;

/**
 * 使用技能，道具
 */
@HandlerEntity(mid=MID.UseSkillReq_VALUE,msg=UseSkillRequest.class)
public class UseSkillHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplyAthleticsHandler.class);

	@Override
	public void run() {
		
	}

}
