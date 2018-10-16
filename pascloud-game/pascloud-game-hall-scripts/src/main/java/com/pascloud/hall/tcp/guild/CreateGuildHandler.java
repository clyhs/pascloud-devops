package com.pascloud.hall.tcp.guild;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.game.model.mongo.hall.dao.GuildDao;
import com.pascloud.game.model.strut.Guild;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallGuildMessage.CreateGuildRequest;

/**
 * 创建公会
 */
@HandlerEntity(mid=MID.CreateGuildReq_VALUE,msg=CreateGuildRequest.class)
public class CreateGuildHandler extends TcpHandler {
	private static final Logger LOGGER=LoggerFactory.getLogger(CreateGuildHandler.class);

	@Override
	public void run() {
		CreateGuildRequest req=getMsg();
		Guild guild=new Guild();
		guild.setMasterId(rid);
		guild.getMembers().add(rid);
		guild.setCreateTime(new Date());
		GuildDao.saveGuild(guild);
	}

	
	
}
