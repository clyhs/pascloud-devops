package com.pascloud.bydr.tcp.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.manager.RoleManager;
import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallLoginMessage.LoginSubGameRequest;
import com.pascloud.message.hall.HallLoginMessage.LoginSubGameResponse;

/**
 * 捕鱼达人登录
 * TODO 此次全用的是session写逻辑，用netty需要使用channel
 */
@HandlerEntity(mid = MID.LoginSubGameReq_VALUE, msg = LoginSubGameRequest.class)
public class BydrLoginHandler extends TcpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(BydrLoginHandler.class);

	@Override
	public void run() {
		LoginSubGameRequest req = getMsg();
		//LOGGER.info("角色[{}]登录游戏sessionId:{}", req.getRid(), getSession().getId());
		
		switch (req.getType()) {
		case 0:	//登录
			RoleManager.getInstance().login(req.getRid(), Reason.UserLogin, role->{
				role.setIoSession(getSession());
				role.setChannel(getChannel());
			});
			break;
		case 1: //重连
			RoleManager.getInstance().login(req.getRid(), Reason.UserReconnect, role->{
				role.setIoSession(getSession());
				role.setChannel(getChannel());
			});
			break;
		case 2:	//跨服登录
			RoleManager.getInstance().login(req.getRid(), Reason.CrossServer, role->{
				role.setIoSession(getSession());
				role.setChannel(getChannel());
			});
		default:
			break;
		}
		
		//TODO
		
		
		if(req.getType()==2){ //跨服不返消息
			LOGGER.debug("角色[{}]跨服登录",req.getRid());
			return ;
		}
		
		LoginSubGameResponse.Builder builder = LoginSubGameResponse.newBuilder();
		builder.setResult(1);
		sendIdMsg(builder.build());
	}
}
