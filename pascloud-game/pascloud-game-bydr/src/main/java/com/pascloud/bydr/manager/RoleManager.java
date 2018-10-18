package com.pascloud.bydr.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.script.IRoleScript;
import com.pascloud.bydr.struct.role.Role;
import com.pascloud.core.redis.jedis.JedisManager;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.utils.JsonUtil;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.game.model.redis.key.BydrKey;

public class RoleManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleManager.class);
	private static volatile RoleManager roleManager;

	private Map<Long, Role> onlineRoles = new ConcurrentHashMap<>(); // 在线的角色

	private RoleManager() {

	}

	public static RoleManager getInstance() {
		if (roleManager == null) {
			synchronized (RoleManager.class) {
				if (roleManager == null) {
					roleManager = new RoleManager();
				}
			}
		}
		return roleManager;
	}

	public Map<Long, Role> getOnlineRoles() {
		return onlineRoles;
	}

	public Role getRole(long roleId) {
		return onlineRoles.get(roleId);
	}

	/**
	 * 登录
	 * 
	 * @param roleId
	 * @param reason
	 * @return 0 成功
	 */
	public void login(long roleId, Reason reason, Consumer<Role> roleConsumer) {
		ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IRoleScript.class,
				script -> script.login(roleId, reason, roleConsumer));
	}

	/**
	 * 退出
	 * 
	 * @param role
	 * @param reason
	 * 原因
	 */
	public void quit(Role role, Reason reason) {
		ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IRoleScript.class,
				script -> script.quit(role, reason));
	}

	/**
	 * 加载角色数据
	 * 
	 * @param roleId
	 */
	public Role loadRoleData(long roleId) {
		Map<String, String> roleMap = JedisManager.getJedisCluster().hgetAll(BydrKey.Role_Map.getKey(roleId));
		if (roleMap == null || roleMap.size() < 1) {
			return null;
		}
		Role role = new Role();
		JsonUtil.map2Object(roleMap, role);

		// TODO 其他角色数据
		
//		//大厅角色数据
//		RMap<String, Object> hallRole = RedissonManager.getRedissonClient().getMap(HallKey.Role_Map_Info.getKey(roleId), new StringCodec());
//		role.setHallRole(hallRole);

		return role;
	}

	/**
	 * 存储角色数据
	 * 
	 * @param role
	 * 
	 *            TODO 存储到mongodb
	 */
	public void saveRoleData(Role role) {
		String key = BydrKey.Role_Map.getKey(role.getId());
		LOGGER.debug("{}存储数据", key);
		JedisManager.getJedisCluster().hmset(key, JsonUtil.object2Map(role));
		//
	}
}
