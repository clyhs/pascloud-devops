package com.pascloud.hall.script.role;

import java.util.function.Consumer;

import com.pascloud.game.model.mongo.hall.dao.HallInfoDao;
import com.pascloud.game.model.strut.Role;
import com.pascloud.hall.script.IRoleScript;


/**
 * 创建角色
 */
public class CreateRoleScript implements IRoleScript {

	@Override
	public Role createRole(long userId, Consumer<Role> roleConsumer) {
		Role role = new Role();
		role.setId(HallInfoDao.getRoleId());
		role.setUserId(userId);
		if (roleConsumer != null) {
			roleConsumer.accept(role);
		}
		return role;
	}

}
