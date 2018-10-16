package com.pascloud.hall.manager;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.redis.jedis.JedisManager;
import com.pascloud.core.redis.jedis.JedisPubSubMessage;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.utils.JsonUtil;
import com.pascloud.game.model.constant.Reason;
import com.pascloud.game.model.redis.channel.HallChannel;
import com.pascloud.game.model.redis.key.HallKey;
import com.pascloud.game.model.strut.Role;
import com.pascloud.hall.script.IRoleScript;

/**
 * 角色管理
 * 
 */
public class RoleManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleManager.class);
	private static volatile RoleManager roleManager;

	/** role 数据需要实时存数据库 */
	private Map<Long, Role> roles = new ConcurrentHashMap<>();

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

	/**
	 * 创建角色
	 * 
	 * @param userConsumer
	 * @return
	 */
	public Role createUser(long userId, Consumer<Role> roleConsumer) {
		Collection<IRoleScript> evts = ScriptManager.getInstance().getBaseScriptEntry().getEvts(IRoleScript.class);
		Iterator<IRoleScript> iterator = evts.iterator();
		while (iterator.hasNext()) {
			IRoleScript userScript = iterator.next();
			Role role = userScript.createRole(userId, roleConsumer);
			if (role != null) {
				return role;
			}
		}
		return null;
	}

	public Map<Long, Role> getRoles() {
		return roles;
	}

	public Role getRole(long id) {
		Role role = roles.get(id);
		Map<String, String> hgetAll = JedisManager.getJedisCluster().hgetAll(role.getRoleRedisKey());
		// 从redis读取最新数据
		if (hgetAll != null && role != null) {
			JsonUtil.map2Object(hgetAll, role);
		}
		return role;
	}

	/**
	 * 登陆
	 * 
	 * @param role
	 */
	public void login(Role role, Reason reason) {
		ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IRoleScript.class,
				script -> script.login(role, reason));
	}

	/**
	 * 退出
	 * 
	 * @param rid
	 * @param reason
	 */
	public void quit(long rid, Reason reason) {
		quit(getRole(rid), reason);
	}

	/**
	 * 退出游戏
	 * 
	 * @param role
	 * @param reason
	 */
	public void quit(Role role, Reason reason) {
		ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IRoleScript.class,
				script -> script.quit(role, reason));
	}

	/**
	 * 广播金币改变
	 * 
	 * @param roleId
	 * @param gold
	 *            金币改变量
	 */
	public void publishGoldChange(long roleId, int gold) {
		String gameIdStr = JedisManager.getJedisCluster().hget(HallKey.Role_Map_Info.getKey(roleId), "gameId");
		if (gameIdStr != null && !gameIdStr.equals("0")) {
			JedisPubSubMessage message = new JedisPubSubMessage(roleId, Integer.parseInt(gameIdStr), gold);
			JedisManager.getJedisCluster().publish(HallChannel.HallGoldChange.name(), message.toString());
		}
	}

}
