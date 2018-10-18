package com.pascloud.bydr.script.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.script.IRoleScript;
import com.pascloud.bydr.struct.role.Role;
import com.pascloud.core.redis.jedis.JedisManager;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.game.model.redis.key.HallKey;

/**
 * 修改角色金币
 * @author admin
 *
 */
public class RoleGoldChangeScript implements IRoleScript {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleGoldChangeScript.class);

	@Override
	public void changeGold(Role role, int add, Reason reason) {
		long gold = role.getGold() + add;
		if (gold < 0 || gold > Long.MAX_VALUE) {
			LOGGER.warn("玩家更新金币异常,{}+{}={}", role.getGold(), add, gold);
			role.setGold(0);
		}
		role.setGold(gold);
		if (reason == Reason.RoleFire) {
			role.setWinGold(role.getWinGold() + add);
		}
	}

	@Override
	public void syncGold(Role role, Reason reason) {
		if (role.getWinGold() != 0) {
			String key = HallKey.Role_Map_Info.getKey(role.getId());

			long addAndGet = JedisManager.getJedisCluster().hincrBy(key, "gold", role.getWinGold());
			role.setWinGold(0);
			LOGGER.debug("更新后金币为{}", addAndGet);
		}

	}

}