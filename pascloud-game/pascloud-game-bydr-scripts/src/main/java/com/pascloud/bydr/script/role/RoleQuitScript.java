package com.pascloud.bydr.script.role;

import com.pascloud.bydr.manager.RoleManager;
import com.pascloud.bydr.script.IRoleScript;
import com.pascloud.bydr.struct.role.Role;
import com.pascloud.game.model.constant.Reason;

/**
 * 退出
 */
public class RoleQuitScript implements IRoleScript {

	@Override
	public void quit(Role role, Reason reason) {
		RoleManager roleManager = RoleManager.getInstance();
		
		role.setGameId(0);
		roleManager.getOnlineRoles().remove(role.getId());
		role.syncGold(Reason.UserQuit);
		roleManager.saveRoleData(role);
	}

	
}
