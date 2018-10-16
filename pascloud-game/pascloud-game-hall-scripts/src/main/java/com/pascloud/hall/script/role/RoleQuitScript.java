package com.pascloud.hall.script.role;

import java.util.Map;

import com.pascloud.game.model.constant.Reason;
import com.pascloud.game.model.mongo.hall.dao.RoleDao;
import com.pascloud.game.model.strut.Role;
import com.pascloud.hall.manager.RoleManager;
import com.pascloud.hall.script.IRoleScript;

import redis.clients.jedis.Jedis;

/**
 * 角色退出游戏
 * 
 */
public class RoleQuitScript implements IRoleScript {

	@Override
	public void quit(Role role, Reason reason) {
		RoleManager.getInstance().getRoles().remove(role.getId());
		saveData(role);
	}

	/**
	 * 存储数据
	 * 
	 * @param role
	 */
	private void saveData(Role role) {
		// ----- mongodb -----
		RoleDao.saveRole(role);

		//----- redis-----
		
		//角色 数据不保存，需要实时存储
//		String key = HallKey.Role_Map_Info.getKey(role.getId());
//		Map<String, String> map = JsonUtil.object2Map(role);
//		JedisManager.getJedisCluster().hmset(key, map);
	}

}
