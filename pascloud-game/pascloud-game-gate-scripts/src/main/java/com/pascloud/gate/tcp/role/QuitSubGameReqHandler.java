package com.pascloud.gate.tcp.role;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.redis.jedis.JedisManager;
import com.pascloud.core.server.ServerType;
import com.pascloud.core.utils.MsgUtil;
import com.pascloud.game.model.constant.Config;
import com.pascloud.game.model.redis.key.HallKey;
import com.pascloud.gate.struct.UserSession;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallLoginMessage.QuitSubGameRequest;

/**
 * 退出子游戏
 * // TODO 处理用户session 请求处理，还是返回处理，根据客户端需求
 */
@HandlerEntity(mid=MID.QuitSubGameReq_VALUE,msg=QuitSubGameRequest.class)
public class QuitSubGameReqHandler extends TcpHandler {
	private static final Logger LOGGER=LoggerFactory.getLogger(QuitSubGameReqHandler.class);

	@Override
	public void run() {
		QuitSubGameRequest req=getMsg();
		Object attribute = getSession().getAttribute(Config.USER_SESSION);
		if(attribute==null){
			LOGGER.warn("{} 无用户会话",MsgUtil.getIp(getSession()));
			return;
		}
		if(req==null){
			req=QuitSubGameRequest.newBuilder().build();
		}
		
		UserSession userSession=(UserSession)attribute;
		userSession.sendToGame(req);
		userSession.removeGame();
		
		//清空角色服务器ID，服务类型，网关统一处理，避免游戏服重复处理
		String key = HallKey.Role_Map_Info.getKey(userSession.getRoleId());
		Map<String, String> redisMap=new HashMap<String, String>();
		redisMap.put("gameType", ServerType.NONE.toString());
		redisMap.put("gameId", String.valueOf(0));
		JedisManager.getJedisCluster().hmset(key, redisMap);
	}

}
