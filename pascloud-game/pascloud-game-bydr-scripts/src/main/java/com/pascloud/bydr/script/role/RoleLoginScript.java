package com.pascloud.bydr.script.role;

import java.util.Map;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.manager.RoleManager;
import com.pascloud.bydr.script.IRoleScript;
import com.pascloud.bydr.struct.role.Role;
import com.pascloud.core.redis.jedis.JedisManager;
import com.pascloud.core.utils.JsonUtil;
import com.pascloud.game.model.constant.Config;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.game.model.redis.key.HallKey;

/**
 * 登录
 * 
 */
public class RoleLoginScript implements IRoleScript {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleLoginScript.class);

	@Override
	public void login(long roleId, Reason reason, Consumer<Role> roleConsumer) {
		Role role = RoleManager.getInstance().loadRoleData(roleId);
		if (role == null) {
			role = new Role();
			role.setId(roleId);
		}
		role.setGameId(Config.SERVER_ID);
		if (roleConsumer != null) {
			roleConsumer.accept(role);
		}
		RoleManager.getInstance().getOnlineRoles().put(roleId, role);

		// 同步大厅角色数据，昵称、头像等
		syncHallData(role);
		tempInit(role);
	}

	/**
	 * 同步大厅数据
	 * 
	 * @param role
	 */
	private void syncHallData(Role role) {
		//同步角色
		String key = HallKey.Role_Map_Info.getKey(role.getId());
		Map<String, String> hgetAll = JedisManager.getJedisCluster().hgetAll(key);
		if (hgetAll == null) {
			LOGGER.warn("{}为找到角色数据", key);
			return;
		}
		com.pascloud.game.model.strut.Role hallRole = new com.pascloud.game.model.strut.Role();
		JsonUtil.map2Object(hgetAll, hallRole);
		role.setNick(hallRole.getNick());
		role.setGold(hallRole.getGold());
		role.setLevel(hallRole.getLevel());
		RoleManager.getInstance().saveRoleData(role);

		
//		//加载大厅数据
//		// 道具
//		RMap<Long, Item> items = RedissonManager.getRedissonClient()
//				.getMap(HallKey.Role_Map_Packet.getKey(role.getId()),new FastJsonCodec(Long.class,Item.class));
//		role.setItems(items);

	}

	/**
	 * 临时初始化
	 * 
	 * @param role
	 */
	private void tempInit(Role role) {
		// 赠送金币
		if (role.getGold() < 100) {
			role.changeGold(100000, Reason.RoleFire);
		}
	}

}
